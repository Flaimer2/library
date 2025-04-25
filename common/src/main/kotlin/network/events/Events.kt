package ru.snapix.library.network.events

import io.github.crackthecodeabhi.kreds.connection.AbstractKredsSubscriber
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.modules.*
import ru.snapix.library.network.messenger.Action
import ru.snapix.library.utils.async
import ru.snapix.library.utils.redisClient
import ru.snapix.library.utils.subscribe

object Events {
    private val module = SerializersModule {
        polymorphic(Action::class) {
            subclass(PlayerDisconnectEvent::class)
        }
    }
    val json = Json {
        serializersModule = module
        encodeDefaults = true
        ignoreUnknownKeys = true
    }

    fun enable() {
        subscribe(object : AbstractKredsSubscriber() {
            override fun onMessage(channel: String, message: String) {
                val action = json.decodeFromString<Action>(message)
                action.executeIncomingMessage()
            }

            override fun onSubscribe(channel: String, subscribedChannels: Long) {
                println("Success subscribed to channel: $channel")
            }

            override fun onUnsubscribe(channel: String, subscribedChannels: Long) {
                println("Success unsubscribed to channel: $channel")
            }

            override fun onException(ex: Throwable) {
                println("Exception while handling subscription to redis: ${ex.stackTrace}")
            }
        }, KEY_REDIS_EVENTS)
    }

    fun sendEvent(action: Action) {
        redisClient.async {
            publish(KEY_REDIS_EVENTS, json.encodeToString(action))
        }
    }
}

const val KEY_REDIS_EVENTS = "events-messenger"