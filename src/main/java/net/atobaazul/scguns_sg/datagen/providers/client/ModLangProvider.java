package net.atobaazul.scguns_sg.datagen.providers.client;

import net.minecraft.ChatFormatting;
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
        addItem(DRUMMA, "Drumma");
        addItem(PUNCHA, "Puncha");
        addItem(TAKAH, "Head Takah");
        addItem(ROKKIT_LAUNCHA, "Rokkit Launcha");
        addItem(TURRET, "Looted Turret");
        addItem(SCRAP_BLUEPRINT, "Scrap Blueprint");
        addItem(SCRAP_CORE, "Scrap Core");
        //addEntityType(ModEntities.GRAVEKEEPER_GHOUL, "Gravekeeper Ghoul");

        add("creativetab.scguns_sg_tab", "Scorched Guns: Scrap Guns");
        //add("tooltip.hex_round.magic_damage", "Deals %s of the bullet damage as magic damage.");

        addGunDescription("stonka", "Smaller version of the Slugga, yet punches just as hard and heavy, somehow. §4So cute!");
        addGunDescription("slugga", "Blocky and heavily built magnum made for sidearm role, bit heavy for it though. §4Perfect §4pocket gun!");
        addGunDescription("shoota", "Held together by hopes and dreams, surprisingly effective but high maintenance. §4Proppa §4gun fo proppa gunna!");
        addGunDescription("dakka", "Perfect for sustained suppressing fire, not so well for accuracy. Feels like you can shoot forever. §4DAKKA §4DAKKA DAKKA!");
        addGunDescription("blinkie", "Crudely made high tech machine that barely works, seems like there’s no protection from radiation leaks. §4OOHHH Shiny!");
        addGunDescription("drumma", "True testament of grip strength, consistent fire power makes it up for awkward handling. §4Hear da drum of §4WARRR!");
        addGunDescription("puncha", "Designed fit for a brute, yet you can deny its raw power in a battlefield. §4Give me all your §4teefz!");
        addGunDescription("takah", "Failed attempt at making sniper rifle, seems like they just designed longer machine gun. §4Take em Eadz Quickly!");
        addGunDescription("rokkit_launcha", "Engineering marvel of a device that is closer to metal pipe than a weapon, gauge seems broken. §4Rokkit go KABOOM!");
        addGunDescription("turret", "Seems like brutes using turrets got bored of their defensive position and had the idea to just take the big gun and walk into the fray. §4Big Dakka is da §4Best DAKKA!");
    }

    private void addGunDescription(String name, String description) {
        this.add("scguns.desc." + name, description);

    }


}
