package net.atobaazul.scguns_sg.registries;


import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import top.ribs.scguns.init.ModSounds;
import top.ribs.scguns.item.BlueprintItem;
import top.ribs.scguns.item.animated.AnimatedGunItem;
import top.ribs.scguns.common.item.gun.AnimatedScrapGunItem;
import top.ribs.scguns.util.Constants;

import static net.atobaazul.scguns_sg.ScrapGuns.MOD_ID;
import static net.atobaazul.scguns_sg.util.CompatManager.CREATE_ENABLED;
import static top.ribs.scguns.util.Constants.PIGLISH;

public class ModItems {
    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

    //gun example

    // !!! IMPORTANT !!! FOR CUSTOM GUN ITEM CLASSES, THEY NEED TO BE UNDER THE top.ribs.common.item.gun PACKAGE. OTHERWISE RELOADS WILL NOT WORK.
    public static final RegistryObject<AnimatedGunItem> STONKA = REGISTER.register("stonka",
            () -> new AnimatedGunItem(
                    new Item.Properties().stacksTo(1).durability(600).rarity(PIGLISH),
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
                    new Item.Properties().stacksTo(1).durability(600).rarity(PIGLISH),
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
                    new Item.Properties().stacksTo(1).durability(600).rarity(PIGLISH),
                    "shoota", // Model path
                    ModSounds.MAG_OUT.get(),        // Reload sound mag out
                    ModSounds.MAG_IN.get(),         // Reload sound mag in
                    ModSounds.RELOAD_END.get(),           // Reload sound end
                    ModSounds.COPPER_GUN_JAM.get(),      // Ejector sound pull
                    ModSounds.COPPER_GUN_JAM.get()    // Ejector sound release
            )
    );

    public static final RegistryObject<AnimatedGunItem> BLINKIE= REGISTER.register("blinkie", () -> new AnimatedScrapGunItem(
                    new Item.Properties().stacksTo(1).durability(600).rarity(PIGLISH),
                    "blinkie", // Model path
                    ModSounds.MAG_OUT.get(),        // Reload sound mag out
                    ModSounds.MAG_IN.get(),         // Reload sound mag in
                    ModSounds.RELOAD_END.get(),           // Reload sound end
                    ModSounds.COPPER_GUN_JAM.get(),      // Ejector sound pull
                    ModSounds.COPPER_GUN_JAM.get()    // Ejector sound release
            )
    );

    public static final RegistryObject<AnimatedGunItem> DAKKA= REGISTER.register("dakka",
            () -> new AnimatedScrapGunItem(
                    new Item.Properties().stacksTo(1).durability(600).rarity(PIGLISH),
                    "dakka", // Model path
                    true,
                    3,
                    ModSounds.MAG_OUT.get(),        // Reload sound mag out
                    ModSounds.MAG_IN.get(),         // Reload sound mag in
                    ModSounds.RELOAD_END.get(),           // Reload sound end
                    ModSounds.COPPER_GUN_JAM.get(),      // Ejector sound pull
                    ModSounds.COPPER_GUN_JAM.get()    // Ejector sound release
            )
    );

    public static final RegistryObject<AnimatedGunItem> TAKAH= REGISTER.register("takah",
            () -> new AnimatedGunItem(
                    new Item.Properties().stacksTo(1).durability(600).rarity(PIGLISH),
                    "takah", // Model path
                    ModSounds.MAG_OUT.get(),        // Reload sound mag out
                    ModSounds.MAG_IN.get(),         // Reload sound mag in
                    ModSounds.RELOAD_END.get(),           // Reload sound end
                    ModSounds.COPPER_GUN_JAM.get(),      // Ejector sound pull
                    ModSounds.COPPER_GUN_JAM.get()    // Ejector sound release
            )
    );

    public static final RegistryObject<AnimatedGunItem> DRUMMA= REGISTER.register("drumma",
            () -> new AnimatedScrapGunItem(
                    new Item.Properties().stacksTo(1).durability(600).rarity(PIGLISH),
                    "drumma", // Model path
                    true,
                    4,
                    ModSounds.MAG_OUT.get(),        // Reload sound mag out
                    ModSounds.MAG_IN.get(),         // Reload sound mag in
                    ModSounds.RELOAD_END.get(),           // Reload sound end
                    ModSounds.COPPER_GUN_JAM.get(),      // Ejector sound pull
                    ModSounds.COPPER_GUN_JAM.get()    // Ejector sound release
            )
    );

    public static final RegistryObject<AnimatedGunItem> TURRET = REGISTER.register("turret",
            () -> new AnimatedScrapGunItem(
                    new Item.Properties().stacksTo(1).durability(600).rarity(PIGLISH),
                    "turret", // Model path
                    true,
                    2,
                    ModSounds.MAG_OUT.get(),        // Reload sound mag out
                    ModSounds.MAG_IN.get(),         // Reload sound mag in
                    ModSounds.RELOAD_END.get(),           // Reload sound end
                    ModSounds.COPPER_GUN_JAM.get(),      // Ejector sound pull
                    ModSounds.COPPER_GUN_JAM.get()    // Ejector sound release
            )
    );
    public static final RegistryObject<AnimatedGunItem> PUNCHA = REGISTER.register("puncha",
            () -> new AnimatedScrapGunItem(
                    new Item.Properties().stacksTo(1).durability(600).rarity(PIGLISH),
                    "puncha", // Model path
                    true,
                    2,
                    ModSounds.MAG_OUT.get(),        // Reload sound mag out
                    ModSounds.MAG_IN.get(),         // Reload sound mag in
                    ModSounds.RELOAD_END.get(),           // Reload sound end
                    ModSounds.COPPER_GUN_JAM.get(),      // Ejector sound pull
                    ModSounds.COPPER_GUN_JAM.get()    // Ejector sound release
            )
    );
    public static final RegistryObject<AnimatedGunItem> ROKKIT_LAUNCHA = REGISTER.register("rokkit_launcha",
            () -> new AnimatedScrapGunItem(
                    new Item.Properties().stacksTo(1).durability(600).rarity(PIGLISH),
                    "rokkit_launcha", // Model path
                    ModSounds.MAG_OUT.get(),        // Reload sound mag out
                    ModSounds.MAG_IN.get(),         // Reload sound mag in
                    ModSounds.RELOAD_END.get(),           // Reload sound end
                    ModSounds.COPPER_GUN_JAM.get(),      // Ejector sound pull
                    ModSounds.COPPER_GUN_JAM.get()    // Ejector sound release
            )
    );





    public static final RegistryObject<Item> SCRAP_BLUEPRINT = REGISTER.register("scrap_blueprint", () -> new BlueprintItem(new Item.Properties().stacksTo(1).rarity(PIGLISH)));

    //other items
    public static final RegistryObject<Item> SCRAP_CORE = REGISTER.register("scrap_core", () -> new Item(new Item.Properties()));

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