package net.atobaazul.scguns_sg.registries;


import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import top.ribs.scguns.init.ModSounds;
import top.ribs.scguns.item.animated.AnimatedGunItem;

import static net.atobaazul.scguns_sg.ScrapGuns.MOD_ID;
import static net.atobaazul.scguns_sg.util.CompatManager.CREATE_ENABLED;

public class ModItems {
    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

    //gun example

    // !!! IMPORTANT !!! FOR CUSTOM GUN ITEM CLASSES, THEY NEED TO BE UNDER THE top.ribs.common.item.gun PACKAGE. OTHERWISE RELOADS WILL NOT WORK.
    public static final RegistryObject<AnimatedGunItem> STONKA = REGISTER.register("stonka",
            () -> new AnimatedGunItem(
                    new Item.Properties().stacksTo(1).durability(600),
                    "stonka", // Model path
                    ModSounds.MAG_OUT.get(),        // Reload sound mag out
                    ModSounds.MAG_IN.get(),         // Reload sound mag in
                    ModSounds.RELOAD_END.get(),           // Reload sound end
                    ModSounds.COPPER_GUN_JAM.get(),      // Ejector sound pull
                    ModSounds.COPPER_GUN_JAM.get()    // Ejector sound release
            )
    );

    public static final RegistryObject<AnimatedGunItem> SLUGGA = REGISTER.register("slugga",
            () -> new AnimatedGunItem(
                    new Item.Properties().stacksTo(1).durability(600),
                    "slugga", // Model path
                    ModSounds.MAG_OUT.get(),        // Reload sound mag out
                    ModSounds.MAG_IN.get(),         // Reload sound mag in
                    ModSounds.RELOAD_END.get(),           // Reload sound end
                    ModSounds.COPPER_GUN_JAM.get(),      // Ejector sound pull
                    ModSounds.COPPER_GUN_JAM.get()    // Ejector sound release
            )
    );

    public static final RegistryObject<AnimatedGunItem> SHOOTA= REGISTER.register("shoota",
            () -> new AnimatedGunItem(
                    new Item.Properties().stacksTo(1).durability(600),
                    "shoota", // Model path
                    ModSounds.MAG_OUT.get(),        // Reload sound mag out
                    ModSounds.MAG_IN.get(),         // Reload sound mag in
                    ModSounds.RELOAD_END.get(),           // Reload sound end
                    ModSounds.COPPER_GUN_JAM.get(),      // Ejector sound pull
                    ModSounds.COPPER_GUN_JAM.get()    // Ejector sound release
            )
    );

    public static final RegistryObject<AnimatedGunItem> BLINKIE= REGISTER.register("blinkie",
            () -> new AnimatedGunItem(
                    new Item.Properties().stacksTo(1).durability(600),
                    "blinkie", // Model path
                    ModSounds.MAG_OUT.get(),        // Reload sound mag out
                    ModSounds.MAG_IN.get(),         // Reload sound mag in
                    ModSounds.RELOAD_END.get(),           // Reload sound end
                    ModSounds.COPPER_GUN_JAM.get(),      // Ejector sound pull
                    ModSounds.COPPER_GUN_JAM.get()    // Ejector sound release
            )
    );

    public static final RegistryObject<AnimatedGunItem> DAKKA= REGISTER.register("dakka",
            () -> new AnimatedGunItem(
                    new Item.Properties().stacksTo(1).durability(600),
                    "dakka", // Model path
                    ModSounds.MAG_OUT.get(),        // Reload sound mag out
                    ModSounds.MAG_IN.get(),         // Reload sound mag in
                    ModSounds.RELOAD_END.get(),           // Reload sound end
                    ModSounds.COPPER_GUN_JAM.get(),      // Ejector sound pull
                    ModSounds.COPPER_GUN_JAM.get()    // Ejector sound release
            )
    );

    public static final RegistryObject<AnimatedGunItem> TAKAH= REGISTER.register("takah",
            () -> new AnimatedGunItem(
                    new Item.Properties().stacksTo(1).durability(600),
                    "takah", // Model path
                    ModSounds.MAG_OUT.get(),        // Reload sound mag out
                    ModSounds.MAG_IN.get(),         // Reload sound mag in
                    ModSounds.RELOAD_END.get(),           // Reload sound end
                    ModSounds.COPPER_GUN_JAM.get(),      // Ejector sound pull
                    ModSounds.COPPER_GUN_JAM.get()    // Ejector sound release
            )
    );

    public static final RegistryObject<AnimatedGunItem> DRUMMA= REGISTER.register("drumma",
            () -> new AnimatedGunItem(
                    new Item.Properties().stacksTo(1).durability(600),
                    "drumma", // Model path
                    ModSounds.MAG_OUT.get(),        // Reload sound mag out
                    ModSounds.MAG_IN.get(),         // Reload sound mag in
                    ModSounds.RELOAD_END.get(),           // Reload sound end
                    ModSounds.COPPER_GUN_JAM.get(),      // Ejector sound pull
                    ModSounds.COPPER_GUN_JAM.get()    // Ejector sound release
            )
    );

    public static final RegistryObject<AnimatedGunItem> TURRET = REGISTER.register("turret",
            () -> new AnimatedGunItem(
                    new Item.Properties().stacksTo(1).durability(600),
                    "turret", // Model path
                    ModSounds.MAG_OUT.get(),        // Reload sound mag out
                    ModSounds.MAG_IN.get(),         // Reload sound mag in
                    ModSounds.RELOAD_END.get(),           // Reload sound end
                    ModSounds.COPPER_GUN_JAM.get(),      // Ejector sound pull
                    ModSounds.COPPER_GUN_JAM.get()    // Ejector sound release
            )
    );
    public static final RegistryObject<AnimatedGunItem> PUNCHA = REGISTER.register("puncha",
            () -> new AnimatedGunItem(
                    new Item.Properties().stacksTo(1).durability(600),
                    "puncha", // Model path
                    ModSounds.MAG_OUT.get(),        // Reload sound mag out
                    ModSounds.MAG_IN.get(),         // Reload sound mag in
                    ModSounds.RELOAD_END.get(),           // Reload sound end
                    ModSounds.COPPER_GUN_JAM.get(),      // Ejector sound pull
                    ModSounds.COPPER_GUN_JAM.get()    // Ejector sound release
            )
    );
    public static final RegistryObject<AnimatedGunItem> ROKKIT_LAUNCHA = REGISTER.register("rokkit_launcha",
            () -> new AnimatedGunItem(
                    new Item.Properties().stacksTo(1).durability(600),
                    "rokkit_launcha", // Model path
                    ModSounds.MAG_OUT.get(),        // Reload sound mag out
                    ModSounds.MAG_IN.get(),         // Reload sound mag in
                    ModSounds.RELOAD_END.get(),           // Reload sound end
                    ModSounds.COPPER_GUN_JAM.get(),      // Ejector sound pull
                    ModSounds.COPPER_GUN_JAM.get()    // Ejector sound release
            )
    );





    //blueprint example
    //public static final RegistryObject<Item> GRAVEKEEPER_BLUEPRINT = REGISTER.register("gravekeeper_blueprint", () -> new BlueprintItem(new Item.Properties().stacksTo(1).rarity(ModRarity.EXSANGUINATED)));

    //rounds example
    //public static final RegistryObject<Item> PULSE_CORE = REGISTER.register("pulse_core", () -> new AmmoItem(new Item.Properties()));

    //other items
    //public static final RegistryObject<Item> NECROMIUM_GUN_FRAME = REGISTER.register("necromium_gun_frame", () -> new Item(new Item.Properties()));

    //create sequenced assembly item example
    //public static final RegistryObject<Item> UNFINISHED_HEX_ROUND = sequencedIngredient("unfinished_hex_round");


    //raid flare example
    //public static final RegistryObject<Item> GRAVEKEEPER_FLARE = REGISTER.register("gravekeeper_flare",
    //        () -> new RaidFlareItem(new Item.Properties().stacksTo(16), "gravekeeper"));


    //mob spawn eggs example
    //public static final RegistryObject<Item> GRAVEKEEPER_GHOUL_SPAWN_EGG = REGISTER.register("gravekeeper_ghoul_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.GRAVEKEEPER_GHOUL, 0x4f6655, 0xa2acc2, new Item.Properties()));

    //we don't want to not register the item when create isn't loaded, so we register it as a basic item instead.
    private static RegistryObject<Item> sequencedIngredient(String name) {
        if (CREATE_ENABLED) {
            return REGISTER.register(name, () -> ModCompatItems.getSequencedItem(name));
        }
        return REGISTER.register(name, () -> new Item(new Item.Properties()));
    }

    public static class ModRarity {
        //public static final Rarity EXSANGUINATED = Rarity.create("scguns_cnc.exsanguinated", style -> style.withColor(0x584f7c));
    }
}