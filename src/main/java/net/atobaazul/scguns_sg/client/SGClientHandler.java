package net.atobaazul.scguns_sg.client;

import net.atobaazul.scguns_sg.client.model.gun.*;
import net.atobaazul.scguns_sg.registries.ModItems;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import top.ribs.scguns.client.render.gun.ModelOverrides;

import static net.atobaazul.scguns_sg.ScrapGuns.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
public class SGClientHandler {
    public static void registerClientHandlers(IEventBus bus) {
        bus.addListener(SGClientHandler::onClientSetup);
    }

    private static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(SGClientHandler::setup);
    }

    public static void setup() {
        registerModelOverrides();
    }

    private static void registerModelOverrides() {
        ModelOverrides.register(ModItems.BLINKIE.get(), new BlinkieModel());
        ModelOverrides.register(ModItems.DAKKA.get(), new DakkaModel());
        ModelOverrides.register(ModItems.DRUMMA.get(), new DrummaModel());
        ModelOverrides.register(ModItems.PUNCHA.get(), new PunchaModel());
        ModelOverrides.register(ModItems.ROKKIT_LAUNCHA.get(), new RokkitLaunchaModel());
        ModelOverrides.register(ModItems.SHOOTA.get(), new ShootaModel());
        ModelOverrides.register(ModItems.SLUGGA.get(), new SluggaModel());
        ModelOverrides.register(ModItems.STONKA.get(), new StonkaModel());
        ModelOverrides.register(ModItems.TAKAH.get(), new TakahModel());
        ModelOverrides.register(ModItems.TURRET.get(), new TurretModel());
    }
}

