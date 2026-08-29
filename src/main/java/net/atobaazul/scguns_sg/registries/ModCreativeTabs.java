package net.atobaazul.scguns_sg.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import top.ribs.scguns.init.ModCreativeModeTabs;

import static net.atobaazul.scguns_sg.ScrapGuns.MOD_ID;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);


    public static final RegistryObject<CreativeModeTab> SCGUNS_CNC_TAB = CREATIVE_MODE_TABS.register("scguns_cnc_tab", () -> CreativeModeTab.builder().icon(() -> new ItemStack(Items.STRUCTURE_VOID)).title(Component.translatable("creativetab.scguns_sg_tab")).displayItems((pParameters, pOutput) -> {
        //addItemWithFullAmmo(pOutput, MORTICIAN.get());
        //addItem(pOutput, MALISON_GRENADE.get());


    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

    private static void addItem(CreativeModeTab.Output output, Item item) {
        output.accept(item);
    }

    private static void addItemWithFullAmmo(CreativeModeTab.Output output, Item item) {
        ModCreativeModeTabs.CreativeTabHelper.addItemWithFullAmmo(output, item);
    }
}