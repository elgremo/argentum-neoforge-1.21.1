package com.gremo.argentum.item;

import com.gremo.argentum.Argentum;
import com.gremo.argentum.block.ModBlocks;
import com.gremo.argentum.item.custom.*;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Argentum.MOD_ID);

    public static final DeferredItem<Item> ACEITE = ITEMS.register("aceite",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BALA = ITEMS.register("bala",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> BARAJA_SELLADA = ITEMS.register("baraja_sellada",
            () -> new BarajaSelladaItem(new Item.Properties()));

    public static final DeferredItem<Item> BATATA = ITEMS.register("batata",
            () -> new ItemNameBlockItem(ModBlocks.BATATA_PLANTA.get(), new Item.Properties()));

    public static final DeferredItem<Item> BIFE_ASADO = ITEMS.register("bife_asado",
            () -> new CocidoItem(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(8)
                            .saturationModifier(0.8f)
                            .build())
                    ));

    public static final DeferredItem<Item> BIFE_CRUDO = ITEMS.register("bife_crudo",
            () -> new CrudoItem(new Item.Properties()
                    .stacksTo(64) // o 64 según prefieras
                    .food(new net.minecraft.world.food.FoodProperties.Builder()
                            .nutrition(4)      // puntos de hambre que restaura
                            .saturationModifier(0.3f)
                            .build()
                    )
            ));

    public static final DeferredItem<Item> BOMBILLA = ITEMS.register("bombilla",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> CALABAZA_MATE = ITEMS.register("calabaza_mate",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> CARNE_CORTADA_CRUDA = ITEMS.register("carne_cortada_cruda",
            () -> new CrudoItem(new Item.Properties()
                    .stacksTo(64) // o 64 según prefieras
                    .food(new net.minecraft.world.food.FoodProperties.Builder()
                            .nutrition(4)      // puntos de hambre que restaura
                            .saturationModifier(0.3f)
                            .build()
                    )
            ));

    public static final DeferredItem<Item> CARTA_COMODIN = ITEMS.register("carta_comodin",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CARTA_COPA_1 = ITEMS.register("carta_copa_1",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CARTA_COPA_10 = ITEMS.register("carta_copa_10",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CARTA_COPA_11 = ITEMS.register("carta_copa_11",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CARTA_COPA_12 = ITEMS.register("carta_copa_12",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CARTA_COPA_2 = ITEMS.register("carta_copa_2",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CARTA_COPA_3 = ITEMS.register("carta_copa_3",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CARTA_COPA_4 = ITEMS.register("carta_copa_4",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CARTA_COPA_5 = ITEMS.register("carta_copa_5",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CARTA_COPA_6 = ITEMS.register("carta_copa_6",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CARTA_COPA_7 = ITEMS.register("carta_copa_7",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CARTA_COPA_8 = ITEMS.register("carta_copa_8",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CARTA_COPA_9 = ITEMS.register("carta_copa_9",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CARTA_ESPADA_1 = ITEMS.register("carta_espada_1",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CARTA_ESPADA_10 = ITEMS.register("carta_espada_10",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CARTA_ESPADA_11 = ITEMS.register("carta_espada_11",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CARTA_ESPADA_12 = ITEMS.register("carta_espada_12",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CARTA_ESPADA_2 = ITEMS.register("carta_espada_2",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CARTA_ESPADA_3 = ITEMS.register("carta_espada_3",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CARTA_ESPADA_4 = ITEMS.register("carta_espada_4",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CARTA_ESPADA_5 = ITEMS.register("carta_espada_5",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CARTA_ESPADA_6 = ITEMS.register("carta_espada_6",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CARTA_ESPADA_7 = ITEMS.register("carta_espada_7",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CARTA_ESPADA_8 = ITEMS.register("carta_espada_8",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CARTA_ESPADA_9 = ITEMS.register("carta_espada_9",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CARTA_ORO_1 = ITEMS.register("carta_oro_1",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CARTA_ORO_10 = ITEMS.register("carta_oro_10",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CARTA_ORO_11 = ITEMS.register("carta_oro_11",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CARTA_ORO_12 = ITEMS.register("carta_oro_12",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CARTA_ORO_2 = ITEMS.register("carta_oro_2",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CARTA_ORO_3 = ITEMS.register("carta_oro_3",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CARTA_ORO_4 = ITEMS.register("carta_oro_4",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CARTA_ORO_5 = ITEMS.register("carta_oro_5",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CARTA_ORO_6 = ITEMS.register("carta_oro_6",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CARTA_ORO_7 = ITEMS.register("carta_oro_7",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CARTA_ORO_8 = ITEMS.register("carta_oro_8",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CARTA_ORO_9 = ITEMS.register("carta_oro_9",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CARTA_PALO_1 = ITEMS.register("carta_palo_1",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CARTA_PALO_10 = ITEMS.register("carta_palo_10",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CARTA_PALO_11 = ITEMS.register("carta_palo_11",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CARTA_PALO_12 = ITEMS.register("carta_palo_12",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CARTA_PALO_2 = ITEMS.register("carta_palo_2",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CARTA_PALO_3 = ITEMS.register("carta_palo_3",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CARTA_PALO_4 = ITEMS.register("carta_palo_4",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CARTA_PALO_5 = ITEMS.register("carta_palo_5",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CARTA_PALO_6 = ITEMS.register("carta_palo_6",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CARTA_PALO_7 = ITEMS.register("carta_palo_7",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CARTA_PALO_8 = ITEMS.register("carta_palo_8",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CARTA_PALO_9 = ITEMS.register("carta_palo_9",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CHINCHULIN_ASADO = ITEMS.register("chinchulin_asado",
            () -> new CocidoItem(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(8)         // Igual que un Cooked Beef
                            .saturationModifier(0.8f)  // Buena saturación
                            .build()
                    )
            ));

    public static final DeferredItem<Item> CHINCHULIN_CRUDO = ITEMS.register("chinchulin_crudo",
            () -> new CrudoItem(new Item.Properties()
                    .stacksTo(64) // o 64 según prefieras
                    .food(new net.minecraft.world.food.FoodProperties.Builder()
                            .nutrition(4)      // puntos de hambre que restaura
                            .saturationModifier(0.3f)
                            .build()
                    )
            ));

    public static final DeferredItem<Item> CHORRO_SPAWN_EGG = ITEMS.register("chorro_spawn_egg",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> CHURRO_CRUDO = ITEMS.register("churro_crudo",
            () -> new CrudoItem(new Item.Properties()
                    .stacksTo(64) // o 64 según prefieras
                    .food(new net.minecraft.world.food.FoodProperties.Builder()
                            .nutrition(4)      // puntos de hambre que restaura
                            .saturationModifier(0.3f)
                            .build()
                    )
            ));

    public static final DeferredItem<Item> CHURRO_FRITO = ITEMS.register("churro_frito",
            () -> new FritoItem(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(8)
                            .saturationModifier(0.8f)
                            .build())
            ));

    public static final DeferredItem<Item> CHURRO_FRITO_DULCE = ITEMS.register("churro_frito_dulce",
            () -> new FritoItem(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(9)
                            .saturationModifier(0.9f)
                            .build())
            ));

    public static final DeferredItem<Item> COSTILLA_ASADA = ITEMS.register("costilla_asada",
            () -> new CocidoItem(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(8)         // Igual que un Cooked Beef
                            .saturationModifier(0.8f)  // Buena saturación
                            .build()
                    )
            ));

    public static final DeferredItem<Item> COSTILLA_CRUDA = ITEMS.register("costilla_cruda",
            () -> new CrudoItem(new Item.Properties()
                    .stacksTo(64) // o 64 según prefieras
                    .food(new net.minecraft.world.food.FoodProperties.Builder()
                            .nutrition(4)      // puntos de hambre que restaura
                            .saturationModifier(0.3f)
                            .build()
                    )
            ));

    public static final DeferredItem<Item> CUADRO_ARGENTO = ITEMS.register("cuadro_argento",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> CUCHILLO = ITEMS.register("cuchillo",
            () -> new Item(new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.cuchillo.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> DULCE_BATATA = ITEMS.register("dulce_batata",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> DULCE_LECHE = ITEMS.register("dulce_leche",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> DULCE_MEMBRILLO = ITEMS.register("dulce_membrillo",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> EMPANADA_CRUDA = ITEMS.register("empanada_cruda",
            () -> new CrudoItem(new Item.Properties()
                    .stacksTo(64) // o 64 según prefieras
                    .food(new net.minecraft.world.food.FoodProperties.Builder()
                            .nutrition(4)      // puntos de hambre que restaura
                            .saturationModifier(0.3f)
                            .build()
                    )
            ));

    public static final DeferredItem<Item> EMPANADA_FRITA = ITEMS.register("empanada_frita",
            () -> new FritoItem(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(8)
                            .saturationModifier(0.8f)
                            .build())
            ));

    public static final DeferredItem<Item> ENTRANA_ASADA = ITEMS.register("entrana_asada",
            () -> new CocidoItem(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(8)         // Igual que un Cooked Beef
                            .saturationModifier(0.8f)  // Buena saturación
                            .build()
                    )
            ));

    public static final DeferredItem<Item> ENTRANA_CRUDA = ITEMS.register("entrana_cruda",
            () -> new CrudoItem(new Item.Properties()
                    .stacksTo(64) // o 64 según prefieras
                    .food(new net.minecraft.world.food.FoodProperties.Builder()
                            .nutrition(4)      // puntos de hambre que restaura
                            .saturationModifier(0.3f)
                            .build()
                    )
            ));

    public static final DeferredItem<Item> FERNET = ITEMS.register("fernet",
            () -> new FernetItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(3)
            )
    );

    public static final DeferredItem<Item> FICHA_CASINO_1 = ITEMS.register("ficha_casino_1",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> FICHA_CASINO_32 = ITEMS.register("ficha_casino_32",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> FICHA_CASINO_5 = ITEMS.register("ficha_casino_5",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> FICHA_CASINO_64 = ITEMS.register("ficha_casino_64",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> FICHA_CASINO_ESPECIAL = ITEMS.register("ficha_casino_especial",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> FICHA_CASINO_OTRA = ITEMS.register("ficha_casino_otra",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> GRASA = ITEMS.register("grasa",
            () -> new CrudoItem(new Item.Properties()
                    .stacksTo(64) // o 64 según prefieras
                    .food(new net.minecraft.world.food.FoodProperties.Builder()
                            .nutrition(4)      // puntos de hambre que restaura
                            .saturationModifier(0.3f)
                            .build()
                    )
            ));

    public static final DeferredItem<Item> HARINA = ITEMS.register("harina",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> LECHE = ITEMS.register("leche",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> LOMO_ASADO = ITEMS.register("lomo_asado",
            () -> new CocidoItem(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(8)         // Igual que un Cooked Beef
                            .saturationModifier(0.8f)  // Buena saturación
                            .build()
                    )
            ));

    public static final DeferredItem<Item> LOMO_CRUDO = ITEMS.register("lomo_crudo",
            () -> new CrudoItem(new Item.Properties()
                    .stacksTo(64) // o 64 según prefieras
                    .food(new net.minecraft.world.food.FoodProperties.Builder()
                            .nutrition(4)      // puntos de hambre que restaura
                            .saturationModifier(0.3f)
                            .build()
                    )
            ));

    public static final DeferredItem<Item> MANTECA = ITEMS.register("manteca",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> MATAMBRE_ASADO = ITEMS.register("matambre_asado",
            () -> new CocidoItem(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(8)         // Igual que un Cooked Beef
                            .saturationModifier(0.8f)  // Buena saturación
                            .build()
                    )
            ));

    public static final DeferredItem<Item> MATAMBRE_CRUDO = ITEMS.register("matambre_crudo",
            () -> new CrudoItem(new Item.Properties()
                    .stacksTo(64) // o 64 según prefieras
                    .food(new net.minecraft.world.food.FoodProperties.Builder()
                            .nutrition(4)      // puntos de hambre que restaura
                            .saturationModifier(0.3f)
                            .build()
                    )
            ));

    public static final DeferredItem<Item> MATE = ITEMS.register("mate",
            () -> new MateItem(new Item.Properties()
                    .stacksTo(1)
                    .food(new net.minecraft.world.food.FoodProperties.Builder()
                            .nutrition(8)
                            .saturationModifier(0.8f)
                            .build())
            )
    );


    public static final DeferredItem<Item> MATE_LISTO_AMARILLO = ITEMS.register("mate_listo_amarillo",
            () -> new MateListoItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(10)
                    .food(new FoodProperties.Builder()
                            .nutrition(8)
                            .saturationModifier(0.8f)
                            .alwaysEdible()
                            .build())
            )
    );

    public static final DeferredItem<Item> MATE_LISTO_ARGENTO = ITEMS.register("mate_listo_argento",
            () -> new MateListoItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(10)
                    .food(new FoodProperties.Builder()
                            .nutrition(8)
                            .saturationModifier(0.8f)
                            .alwaysEdible()
                            .build())
            )
    );

    public static final DeferredItem<Item> MATE_LISTO_AZUL = ITEMS.register("mate_listo_azul",
            () -> new MateListoItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(10)
                    .food(new FoodProperties.Builder()
                            .nutrition(8)
                            .saturationModifier(0.8f)
                            .alwaysEdible()
                            .build())
            )
    );

    public static final DeferredItem<Item> MATE_LISTO_BLANCO = ITEMS.register("mate_listo_blanco",
            () -> new MateListoItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(10)
                    .food(new FoodProperties.Builder()
                            .nutrition(8)
                            .saturationModifier(0.8f)
                            .alwaysEdible()
                            .build())
            )
    );

    public static final DeferredItem<Item> MATE_LISTO_CELESTE = ITEMS.register("mate_listo_celeste",
            () -> new MateListoItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(10)
                    .food(new FoodProperties.Builder()
                            .nutrition(8)
                            .saturationModifier(0.8f)
                            .alwaysEdible()
                            .build())
            )
    );

    public static final DeferredItem<Item> MATE_LISTO_CYAN = ITEMS.register("mate_listo_cyan",
            () -> new MateListoItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(10)
                    .food(new FoodProperties.Builder()
                            .nutrition(8)
                            .saturationModifier(0.8f)
                            .alwaysEdible()
                            .build())
            )
    );

    public static final DeferredItem<Item> MATE_LISTO_GRIS1 = ITEMS.register("mate_listo_gris1",
            () -> new MateListoItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(10)
                    .food(new FoodProperties.Builder()
                            .nutrition(8)
                            .saturationModifier(0.8f)
                            .alwaysEdible()
                            .build())
            )
    );

    public static final DeferredItem<Item> MATE_LISTO_GRIS2 = ITEMS.register("mate_listo_gris2",
            () -> new MateListoItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(10)
                    .food(new FoodProperties.Builder()
                            .nutrition(8)
                            .saturationModifier(0.8f)
                            .alwaysEdible()
                            .build())
            )
    );

    public static final DeferredItem<Item> MATE_LISTO_MAGENTA = ITEMS.register("mate_listo_magenta",
            () -> new MateListoItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(10)
                    .food(new FoodProperties.Builder()
                            .nutrition(8)
                            .saturationModifier(0.8f)
                            .alwaysEdible()
                            .build())
            )
    );

    public static final DeferredItem<Item> MATE_LISTO_MARRON = ITEMS.register("mate_listo_marron",
            () -> new MateListoItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(10)
                    .food(new FoodProperties.Builder()
                            .nutrition(8)
                            .saturationModifier(0.8f)
                            .alwaysEdible()
                            .build())
            )
    );

    public static final DeferredItem<Item> MATE_LISTO_NARANJA = ITEMS.register("mate_listo_naranja",
            () -> new MateListoItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(10)
                    .food(new FoodProperties.Builder()
                            .nutrition(8)
                            .saturationModifier(0.8f)
                            .alwaysEdible()
                            .build())
            )
    );

    public static final DeferredItem<Item> MATE_LISTO_NEGRO = ITEMS.register("mate_listo_negro",
            () -> new MateListoItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(10)
                    .food(new FoodProperties.Builder()
                            .nutrition(8)
                            .saturationModifier(0.8f)
                            .alwaysEdible()
                            .build())
            )
    );

    public static final DeferredItem<Item> MATE_LISTO_ROJO = ITEMS.register("mate_listo_rojo",
            () -> new MateListoItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(10)
                    .food(new FoodProperties.Builder()
                            .nutrition(8)
                            .saturationModifier(0.8f)
                            .alwaysEdible()
                            .build())
            )
    );

    public static final DeferredItem<Item> MATE_LISTO_ROSA = ITEMS.register("mate_listo_rosa",
            () -> new MateListoItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(10)
                    .food(new FoodProperties.Builder()
                            .nutrition(8)
                            .saturationModifier(0.8f)
                            .alwaysEdible()
                            .build())
            )
    );

    public static final DeferredItem<Item> MATE_LISTO_VERDE1 = ITEMS.register("mate_listo_verde1",
            () -> new MateListoItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(10)
                    .food(new FoodProperties.Builder()
                            .nutrition(8)
                            .saturationModifier(0.8f)
                            .alwaysEdible()
                            .build())
            )
    );

    public static final DeferredItem<Item> MATE_LISTO_VERDE2 = ITEMS.register("mate_listo_verde2",
            () -> new MateListoItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(10)
                    .food(new FoodProperties.Builder()
                            .nutrition(8)
                            .saturationModifier(0.8f)
                            .alwaysEdible()
                            .build())
            )
    );

    public static final DeferredItem<Item> MATE_LISTO_VIOLETA = ITEMS.register("mate_listo_violeta",
            () -> new MateListoItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(10)
                    .food(new FoodProperties.Builder()
                            .nutrition(8)
                            .saturationModifier(0.8f)
                            .alwaysEdible()
                            .build())
            )
    );

    //public static final DeferredItem<Item> MAZO = ITEMS.register("mazo",
    //        () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> MEDIALUNA_COCINADA = ITEMS.register("medialuna_cocinada",
            () -> new Item(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(8)
                            .saturationModifier(0.8f)
                            .build())
            ));

    public static final DeferredItem<Item> MEDIALUNA_CRUDA = ITEMS.register("medialuna_cruda",
            () -> new CrudoItem(new Item.Properties()
                    .stacksTo(64) // o 64 según prefieras
                    .food(new net.minecraft.world.food.FoodProperties.Builder()
                            .nutrition(4)      // puntos de hambre que restaura
                            .saturationModifier(0.3f)
                            .build()
                    )
            ));

    public static final DeferredItem<Item> MEMBRILLO = ITEMS.register("membrillo",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> MEMBRILLO_SEMILLA = ITEMS.register("membrillo_semilla",
            () -> new ItemNameBlockItem(ModBlocks.MEMBRILLO_PLANTA.get(), new Item.Properties()));

    public static final DeferredItem<Item> MILANESA_CRUDA = ITEMS.register("milanesa_cruda",
            () -> new CrudoItem(new Item.Properties()
                    .stacksTo(64) // o 64 según prefieras
                    .food(new net.minecraft.world.food.FoodProperties.Builder()
                            .nutrition(4)      // puntos de hambre que restaura
                            .saturationModifier(0.3f)
                            .build()
                    )
            ));

    public static final DeferredItem<Item> MILANESA_FRITA = ITEMS.register("milanesa_frita",
            () -> new FritoItem(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(8)
                            .saturationModifier(0.8f)
                            .build())
            ));

    public static final DeferredItem<Item> MOLLEJA_ASADA = ITEMS.register("molleja_asada",
            () -> new CocidoItem(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(8)         // Igual que un Cooked Beef
                            .saturationModifier(0.8f)  // Buena saturación
                            .build()
                    )
            ));

    public static final DeferredItem<Item> MOLLEJA_CRUDA = ITEMS.register("molleja_cruda",
            () -> new CrudoItem(new Item.Properties()
                    .stacksTo(64) // o 64 según prefieras
                    .food(new net.minecraft.world.food.FoodProperties.Builder()
                            .nutrition(4)      // puntos de hambre que restaura
                            .saturationModifier(0.3f)
                            .build()
                    )
            ));

    //public static final DeferredItem<Item> OLLA = ITEMS.register("olla",
    //        () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> PAN_RALLADO = ITEMS.register("pan_rallado",
            () -> new Item(new Item.Properties()));

    //public static final DeferredItem<Item> PARRILLA = ITEMS.register("parrilla",
    //        () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> PASTELITO_BATATA_CRUDO = ITEMS.register("pastelito_batata_crudo",
            () -> new CrudoItem(new Item.Properties()
                    .stacksTo(64) // o 64 según prefieras
                    .food(new net.minecraft.world.food.FoodProperties.Builder()
                            .nutrition(4)      // puntos de hambre que restaura
                            .saturationModifier(0.3f)
                            .build()
                    )
            ));

    public static final DeferredItem<Item> PASTELITO_BATATA_FRITO = ITEMS.register("pastelito_batata_frito",
            () -> new FritoItem(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(8)
                            .saturationModifier(0.8f)
                            .build())
            ));

    public static final DeferredItem<Item> PASTELITO_MEMBRILLO_CRUDO = ITEMS.register("pastelito_membrillo_crudo",
            () -> new CrudoItem(new Item.Properties()
                    .stacksTo(64) // o 64 según prefieras
                    .food(new net.minecraft.world.food.FoodProperties.Builder()
                            .nutrition(4)      // puntos de hambre que restaura
                            .saturationModifier(0.3f)
                            .build()
                    )
            ));

    public static final DeferredItem<Item> PASTELITO_MEMBRILLO_FRITO = ITEMS.register("pastelito_membrillo_frito",
            () -> new FritoItem(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(8)
                            .saturationModifier(0.8f)
                            .build())
            ));

    public static final DeferredItem<Item> PELOTA = ITEMS.register("pelota",
            () -> new PelotaItem(new Item.Properties().stacksTo(1))
    );

    public static final DeferredItem<Item> PISTOLA = ITEMS.register("pistola",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> SAQUITO_MATECOCIDO = ITEMS.register("saquito_matecocido",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> SAQUITO_TE = ITEMS.register("saquito_te",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> TAPAS_EMPANADA = ITEMS.register("tapas_empanada",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> TAPAS_HOJALDRE = ITEMS.register("tapas_hojaldre",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> TAZA = ITEMS.register("taza",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> TAZA_DE_ARCILLA = ITEMS.register("taza_de_arcilla",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> TAZA_DE_MATECOCIDO = ITEMS.register("taza_de_matecocido",
            () -> new InfusionItem(new Item.Properties()
                    .stacksTo(1) // Solo 1 item en la mano
                    .food(new FoodProperties.Builder()
                            .nutrition(8)
                            .saturationModifier(0.8f)
                            .build())
            )
    );

    public static final DeferredItem<Item> TAZA_DE_TE = ITEMS.register("taza_de_te",
            () -> new InfusionItem(new Item.Properties()
                    .stacksTo(1) // Solo 1 item en la mano
                    .food(new FoodProperties.Builder()
                            .nutrition(8)
                            .saturationModifier(0.8f)
                            .build())
            )
    );

    public static final DeferredItem<Item> TE = ITEMS.register("te",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> TE_SEMILLA = ITEMS.register("te_semilla",
            () -> new ItemNameBlockItem(ModBlocks.TE_PLANTA.get(), new Item.Properties()));

    public static final DeferredItem<Item> TERMO = ITEMS.register("termo",
            () -> new Item(new Item.Properties()
                    .stacksTo(1)
                    .durability(10) // también 10 usos (ajustá si querés otra durabilidad)
            )
    );


    public static final DeferredItem<Item> TERMO_ARGENTO = ITEMS.register("termo_argento",
            () -> new Item(new Item.Properties()
                    .stacksTo(1)
                    .durability(15) // también 10 usos (ajustá si querés otra durabilidad)
            )
    );


    public static final DeferredItem<Item> TORTAFRITA = ITEMS.register("tortafrita",
            () -> new FritoItem(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(8)
                            .saturationModifier(0.8f)
                            .build())
            ));

    public static final DeferredItem<Item> TORTAFRITA_CRUDA = ITEMS.register("tortafrita_cruda",
            () -> new CrudoItem(new Item.Properties()
                    .stacksTo(64) // o 64 según prefieras
                    .food(new net.minecraft.world.food.FoodProperties.Builder()
                            .nutrition(4)      // puntos de hambre que restaura
                            .saturationModifier(0.3f)
                            .build()
                    )
            ));

    public static final DeferredItem<Item> YERBA = ITEMS.register("yerba",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> YERBA_SEMILLA = ITEMS.register("yerba_semilla",
            () -> new ItemNameBlockItem(ModBlocks.YERBA_PLANTA.get(), new Item.Properties()));





    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
