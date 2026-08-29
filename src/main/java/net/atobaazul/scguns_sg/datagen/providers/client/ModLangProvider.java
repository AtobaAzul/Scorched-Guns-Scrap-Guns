package net.atobaazul.scguns_sg.datagen.providers.client;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

import static net.atobaazul.scguns_sg.ScrapGuns.MOD_ID;

public class ModLangProvider extends LanguageProvider {
    public ModLangProvider(PackOutput output) {
        super(output, MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        //items
        //addItem(ModItems.BLUNTSHOT, "Bluntshot");

        //addEntityType(ModEntities.GRAVEKEEPER_GHOUL, "Gravekeeper Ghoul");

        //add("tooltip.hex_round.magic_damage", "Deals %s of the bullet damage as magic damage.");

    }
}
