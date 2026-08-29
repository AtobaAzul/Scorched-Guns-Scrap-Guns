package net.atobaazul.scguns_sg.datagen.providers.server;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static net.atobaazul.scguns_sg.ScrapGuns.MOD_ID;

public class ModBlockTagProvider extends BlockTagsProvider {
    //ArrayList<RegistryObject<Block>> FRAGILE_BLOCKS = new ArrayList<>();

    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        //FRAGILE_BLOCKS.add(CCBlocks.CUPRIC_LANTERN);


        //FRAGILE_BLOCKS.forEach(this::addFragileBlock);
    }

    /*private void addFragileBlock(RegistryObject<Block> block) {
        this.tag(ModTags.Blocks.FRAGILE).add(block.get());
    }*/
}
