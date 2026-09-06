package net.atobaazul.scguns_sg.common;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import static net.atobaazul.scguns_sg.ScrapGuns.MOD_ID;

public class ModTags {
    public static final TagKey<Item> SCRAP_GUN_TIER = createSCGunsItemTag("scrap_gun_tier");


    private static TagKey<Block> createBlockTag(String tagName) {
        return TagKey.create(Registries.BLOCK, new ResourceLocation(MOD_ID, tagName));
    }

    private static TagKey<Item> createItemTag(String tagName) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, tagName));
    }

    private static TagKey<Item> createSCGunsItemTag(String tagName) {
        return TagKey.create(Registries.ITEM, new ResourceLocation("scguns", tagName));
    }
}
