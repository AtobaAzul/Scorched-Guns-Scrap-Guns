package net.atobaazul.scguns_sg.util;

import net.minecraftforge.fml.loading.FMLLoader;

public class CompatManager {
    public static final boolean CREATE_ENABLED = getModEnabled("create");

    private static boolean getModEnabled(String name) {
        return FMLLoader.getLoadingModList().getModFileById(name) != null;
    }
}
