package net.atobaazul.scguns_sg;

import com.mojang.logging.LogUtils;
import net.atobaazul.scguns_sg.client.SGClientHandler;
import net.atobaazul.scguns_sg.registries.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import top.ribs.scguns.client.screen.BlueprintScreen;
import top.ribs.scguns.entity.player.GunTierRegistry;

import java.util.List;

@Mod(ScrapGuns.MOD_ID)
public class ScrapGuns {
    public static final String MOD_ID = "scguns_sg";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ScrapGuns() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);

        ModItems.REGISTER.register(modEventBus);
        ModEntities.REGISTER.register(modEventBus);
        ModSoundEvents.REGISTER.register(modEventBus);
        ModCreativeTabs.register(modEventBus);
        ModParticleTypes.PARTICLE_TYPES.register(modEventBus);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            SGClientHandler.registerClientHandlers(modEventBus);
            //example blueprint
            //BlueprintScreen.registerLoreOnlyItem(new ResourceLocation(MOD_ID, "gravekeeper_blueprint"), "anathema");
            //BlueprintScreen.registerGunOrder(List.of(
            //        "mortician", "bella", "rehearse",
            //        "hangman_carbine", "gallows", "cacophony",
            //        "keteriya", "necrosis",
            //        "silverlining", "ribcage", "dead_draft", "exhumer", "anathema"
            //));
            //ModItems.setupTabEditors();
        });
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        //GunTierRegistry.register("gravekeeper", 6, "gravekeeper_gun_tier", 4);
        //ProjectileManager.getInstance().registerFactory(ModItems.SILVER_BULLET.get(), (worldIn, entity, weapon, item, modifiedGun) -> new HexRoundProjectileEntity(ModEntities.HEX_ROUND_PROJECTILE.get(), worldIn, entity, weapon, item, modifiedGun));
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }
    }
}
