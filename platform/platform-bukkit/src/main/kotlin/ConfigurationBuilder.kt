package ru.snapix.library

import space.arim.dazzleconf.ConfigurationOptions
import space.arim.dazzleconf.serialiser.ValueSerialiser
import space.arim.dazzleconf.sorter.ConfigurationSorter
import space.arim.dazzleconf.validator.ValueValidator

fun configurationOptions(block: ConfigurationBuilder.() -> Unit): ConfigurationOptions {
    val config = ConfigurationBuilder()
    config.apply(block)
    return config.build()
}

class ConfigurationBuilder {
    var serializers = mutableListOf<ValueSerialiser<*>>()
    var validators = mutableMapOf<String, ValueValidator>()
    var sorter: ConfigurationSorter? = null
    var strictParseEnums = false
    var createSingleElementCollections = false

    operator fun plusAssign(serializer: ValueSerialiser<*>) {
        serializers.add(serializer)
    }

    fun build(): ConfigurationOptions {
        val builder = ConfigurationOptions.Builder()
        with(builder) {
            if (serializers.isNotEmpty()) {
                addSerialisers(serializers)
            }
            if (validators.isNotEmpty()) {
                addValidators(validators)
            }
            if (sorter != null) {
                sorter(sorter)
            }
        }
        return builder.build()
    }
}
