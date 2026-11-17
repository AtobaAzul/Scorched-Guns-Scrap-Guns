package net.atobaazul.scguns_sg.client.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.atobaazul.scguns_sg.ScrapGuns.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public enum SGSpecialModels {
    //Blinkie
    BLINKIE_MAIN("blinkie/main"),
    BLINKIE_COIL("blinkie/coil"),
    BLINKIE_ANTHRALITE_BAYONET("blinkie/anthralite_bayonet"),
    BLINKIE_DIAMOND_BAYONET("blinkie/diamond_bayonet"),
    BLINKIE_IRON_BAYONET("blinkie/iron_bayonet"),
    BLINKIE_LIGHT_GRIP("blinkie/light_grip"),
    BLINKIE_NETHERITE_BAYONET("blinkie/netherite_bayonet"),
    BLINKIE_TACTICAL_GRIP("blinkie/tact_grip"),

    //Dakka
    DAKKA_MAIN("dakka/main"),
    DAKKA_ANTHRALITE_BAYONET("dakka/anthralite_bayonet"),
    DAKKA_DIAMOND_BAYONET("dakka/diamond_bayonet"),
    DAKKA_IRON_BAYONET("dakka/iron_bayonet"),
    DAKKA_LIGHT_GRIP("dakka/light_grip"),
    DAKKA_NETHERITE_BAYONET("dakka/netherite_bayonet"),
    DAKKA_TACTICAL_GRIP("dakka/tact_grip"),

    //Drumma
    DRUMMA_MAIN("drumma/main"),

    //Puncha
    PUNCHA_MAIN("puncha/main"),

    //Rokkit Launcha
    ROKKIT_LAUNCHA_MAIN("rokkit_launcha/main"),
    ROKKIT_LAUNCHA_ROCKET("rokkit_launcha/rocket"),

    //Shoota
    SHOOTA_MAIN("shoota/main"),
    SHOOTA_STAN_MAGAZINE("shoota/stand_mag"),
    SHOOTA_FAST_MAGAZINE("shoota/fast_mag"),
    SHOOTA_EXT_MAGAZINE("shoota/ext_mag"),
    SHOOTA_STAN_BARREL("shoota/stan_barrel"),
    SHOOTA_EXT_BARREL("shoota/ext_barrel"),
    SHOOTA_STOCK_WEIGHTED("shoota/heavy_stock"),
    SHOOTA_STOCK_LIGHT("shoota/light_stock"),
    SHOOTA_STOCK_WOODEN("shoota/wooden_stock"),
    SHOOTA_SILENCER("shoota/silencer"),
    SHOOTA_ADVANCED_SILENCER("shoota/advanced_silencer"),
    SHOOTA_MUZZLE_BRAKE("shoota/muzzle_brake"),

    //Slugga
    SLUGGA_MAIN("slugga/main"),
    SLUGGA_STAN_MAGAZINE("slugga/stand_mag"),
    SLUGGA_FAST_MAGAZINE("slugga/fast_mag"),
    SLUGGA_EXT_MAGAZINE("slugga/ext_mag"),
    SLUGGA_STAN_BARREL("slugga/stan_barrel"),
    SLUGGA_EXT_BARREL("slugga/ext_barrel"),
    SLUGGA_STOCK_WEIGHTED("slugga/heavy_stock"),
    SLUGGA_STOCK_LIGHT("slugga/light_stock"),
    SLUGGA_STOCK_WOODEN("slugga/wooden_stock"),
    SLUGGA_SILENCER("slugga/silencer"),
    SLUGGA_ADVANCED_SILENCER("slugga/advanced_silencer"),
    SLUGGA_MUZZLE_BRAKE("slugga/muzzle_brake"),

    //Stonka
    STONKA_MAIN("stonka/main"),
    STONKA_STAN_MAGAZINE("stonka/stand_mag"),
    STONKA_FAST_MAGAZINE("stonka/fast_mag"),
    STONKA_EXT_MAGAZINE("stonka/ext_mag"),
    STONKA_STAN_BARREL("stonka/stan_barrel"),
    STONKA_EXT_BARREL("stonka/ext_barrel"),
    STONKA_SILENCER("stonka/silencer"),
    STONKA_ADVANCED_SILENCER("stonka/advanced_silencer"),
    STONKA_MUZZLE_BRAKE("stonka/muzzle_brake"),

    //Turret
    TURRET_MAIN("turret/main"),

    //Takah
    TAKAH_MAIN("takah/main"),
    TAKAH_STOCK_WOODEN("takah/wooden_stock"),
    TAKAH_STOCK_LIGHT("takah/light_stock"),
    TAKAH_STOCK_WEIGHTED("takah/heavy_stock"),
    TAKAH_EXT_MAGAZINE("takah/ext_mag"),
    TAKAH_FAST_MAGAZINE("takah/fast_mag"),
    TAKAH_STAN_MAGAZINE("takah/stand_mag");

    /**
     * The location of an item model in the [MOD_ID]/models/special/[NAME] folder
     */
    private final ResourceLocation modelLocation;

    /**
     * Cached model
     */
    private BakedModel cachedModel;

    /**
     * Sets the model's location
     *
     * @param modelName name of the model file
     */
    SGSpecialModels(String modelName) {
        this.modelLocation = new ResourceLocation(MOD_ID, "special/" + modelName);
    }

    /**
     * Registers the special models into the Forge Model Bakery. This is only called once on the
     * load of the game.
     */
    @SubscribeEvent
    public static void registerAdditional(ModelEvent.RegisterAdditional event) {
        for (SGSpecialModels model : values()) {
            event.register(model.modelLocation);
        }
    }

    /**
     * Clears the cached BakedModel since it's been rebuilt. This is needed since the models may
     * have changed when a resource pack was applied, or if resources are reloaded.
     */
    @SubscribeEvent
    public static void onBake(ModelEvent.BakingCompleted event) {
        for (SGSpecialModels model : values()) {
            model.cachedModel = null;
        }
    }

    /**
     * Gets the model
     *
     * @return isolated model
     */
    public BakedModel getModel() {
        if (this.cachedModel == null) {
            this.cachedModel = Minecraft.getInstance().getModelManager().getModel(this.modelLocation);
        }
        return this.cachedModel;
    }
}
