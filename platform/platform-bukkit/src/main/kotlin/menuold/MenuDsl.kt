package ru.snapix.library.menuold

//import com.cryptomorin.xseries.XMaterial
//import org.bukkit.entity.Player
//import org.bukkit.inventory.ItemFlag
//import ru.snapix.library.menu.Replacement
//import ru.snapix.library.menu.items.BaseItem
//import kotlin.time.Duration
//
//fun menu(builder: MenuBuilder.() -> Unit): BukkitInventory {
//    return MenuBuilder().apply(builder).build()
//}
//
//class MenuBuilder {
//    var title: String? = null
//    val items = mutableListOf<BaseItem>()
//    val layout = mutableListOf<String>()
//    val replacements = mutableListOf<Replacement>()
//    var update: Duration? = null
//
//    fun MenuBuilder.layout(setup: LayoutBuilder.() -> Unit) {
//        layout.addAll(LayoutBuilder().apply(setup).list)
//    }
//
//    fun MenuBuilder.items(setup: ItemsBuilder.() -> Unit) {
//        items.addAll(ItemsBuilder().apply(setup).list)
//    }
//
//    fun MenuBuilder.replacements(setup: ReplacementsBuilder.() -> Unit) {
//        replacements.addAll(ReplacementsBuilder().apply(setup).list)
//    }
//
//    class LayoutBuilder {
//        val list = mutableListOf<String>()
//
//        operator fun String.unaryMinus() {
//            list.add(this)
//        }
//    }
//
//    class ReplacementsBuilder {
//        val list = mutableListOf<Replacement>()
//
//        operator fun Pair<String, (Player) -> Any>.unaryMinus() {
//            list.add(this)
//        }
//    }
//
//    fun build(): BukkitInventory {
//        return BukkitInventory(title ?: "DefaultSnapTitle", items, layout, replacements, update)
//    }
//}
//
//class ItemsBuilder {
//    val list = mutableListOf<BaseItem>()
//
//    operator fun Char.invoke(setup: ItemBuilder.() -> Unit) {
//        list.add(ItemBuilder(this).apply(setup).build())
//    }
//
//    class ItemBuilder(val index: Char) {
//        var name: String? = null
//        var material: XMaterial = XMaterial.AIR
//        var amount: Int = 1
//        var lore = mutableListOf<String>()
//        var itemFlag = mutableListOf<ItemFlag>()
//        var actions: ClickAction? = null
//
//        fun lore(setup: LoreBuilder.() -> Unit) {
//            lore = LoreBuilder().apply(setup).list
//        }
//
//        fun itemFlag(setup: ItemFlagBuilder.() -> Unit) {
//            itemFlag = ItemFlagBuilder().apply(setup).list
//        }
//
//        fun actions(actions: ClickAction) {
//            this.actions = actions
//        }
//
//        class LoreBuilder {
//            val list = mutableListOf<String>()
//
//            operator fun String.unaryMinus() {
//                list.add(this)
//            }
//        }
//
//        class ItemFlagBuilder {
//            val list = mutableListOf<ItemFlag>()
//
//            operator fun ItemFlag.unaryMinus() {
//                list.add(this)
//            }
//        }
//
//        fun build(): BaseItem {
//            return BaseItem(index, name, material, amount, lore, itemFlag, actions)
//        }
//    }
//}
