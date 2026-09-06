package net.atobaazul.scguns_sg.datagen.providers.client;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

import static net.atobaazul.scguns_sg.ScrapGuns.MOD_ID;
import static net.atobaazul.scguns_sg.registries.ModItems.*;

public class ModLangProvider extends LanguageProvider {
    public ModLangProvider(PackOutput output) {
        super(output, MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        //items
        //addItem(ModItems.BLUNTSHOT, "Bluntshot");
        addItem(STONKA, "Stonka");
        addItem(SLUGGA, "Slugga");
        addItem(SHOOTA, "Shoota");
        addItem(BLINKIE, "Blinkie");
        addItem(DAKKA, "Da Big Dakka");
        addItem(TAKAH, "Head Takah");
        addItem(ROKKIT_LAUNCHA, "Rokkit Launcha");
        addItem(TURRET, "Looted Turret");
        addItem(SCRAP_BLUEPRINT, "Scrap Blueprint");
        addItem(SCRAP_CORE, "Scrap Core");
        //addEntityType(ModEntities.GRAVEKEEPER_GHOUL, "Gravekeeper Ghoul");

        add("creativetab.scguns_sg_tab", "Scorched Guns: Scrap Guns");
        //add("tooltip.hex_round.magic_damage", "Deals %s of the bullet damage as magic damage.");

        addGunDescription("stonka", "Smaller version of the Slugga, yet punches just as hard and heavy, somehow.    So cute!");
    }

    private void addGunDescription(String name, String description) {
        this.add("scguns.desc." + name, description);
    }
}
