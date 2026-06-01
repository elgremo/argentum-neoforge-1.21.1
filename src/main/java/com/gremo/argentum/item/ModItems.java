package com.gremo.argentum.item;

import com.gremo.argentum.Argentum;
import net.minecraft.world.item.Item;
import net.neoforged.bus.EventBus;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Argentum.MOD_ID);

    public static final DeferredItem<Item> ACEITE = ITEMS.register("aceite",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BALA = ITEMS.register("bala",
            () -> new Item(new Item.Properties()));












    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
