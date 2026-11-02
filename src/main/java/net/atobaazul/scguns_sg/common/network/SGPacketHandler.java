package net.atobaazul.scguns_sg.common.network;

import com.mrcrayfish.framework.api.FrameworkAPI;
import com.mrcrayfish.framework.api.network.FrameworkNetwork;
import com.mrcrayfish.framework.api.network.LevelLocation;
import com.mrcrayfish.framework.api.network.MessageDirection;
import com.mrcrayfish.framework.network.message.IMessage;

import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;
/**
 * @author: Krei(github: rekales)
 */
public class SGPacketHandler {

    private static FrameworkNetwork playChannel;

    public static void init() {
        playChannel = FrameworkAPI.createNetworkBuilder(ResourceLocation.fromNamespaceAndPath("scguns_sg", "play"), 1)
                .registerPlayMessage(GunFlashMessage.class, MessageDirection.PLAY_CLIENT_BOUND)
                .build();
    }

    public static FrameworkNetwork getPlayChannel() {
        return playChannel;
    }

    // For convenience and to minimize code footprint
    public static void sendToNearbyPlayers(Supplier<LevelLocation> levelLocation, IMessage<?> message) {
        playChannel.sendToNearbyPlayers(levelLocation, message);
    }

    public static void sendToServer(IMessage<?> message) {
        playChannel.sendToServer(message);
    }
}
