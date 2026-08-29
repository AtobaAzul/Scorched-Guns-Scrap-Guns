package net.atobaazul.scguns_sg.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

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
        //ModelOverrides.register(ModItems.HANGMAN_CARBINE.get(), new HangmanCarbineModel());
        //EntityRenderers.register(ModEntities.GRAVEKEEPER_GHOUL.get(), GravekeeperGhoulEntityRenderer::new);
    }
}

