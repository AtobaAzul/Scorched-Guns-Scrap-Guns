package net.atobaazul.scguns_sg.datagen.providers.server;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static net.atobaazul.scguns_sg.ScrapGuns.MOD_ID;

public class ModEntityTagsProvider extends EntityTypeTagsProvider {
    public ModEntityTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pProvider, MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        /*this.tag(NOT_DEFLECTED_BY_TIN).add(ModEntities.KRAHG_ROUND_PROJECTILE.get())
                .add(ModEntities.OSBORNE_SLUG_PROJECTILE.get())
                .add(ModEntities.PLASMA_PROJECTILE.get())
                .add(ModEntities.ROCKET.get())
                .add(ModEntities.MICROJET.get())
                .add(ModEntities.SHATTER_ROUND_PROJECTILE.get());*/
    }
}
