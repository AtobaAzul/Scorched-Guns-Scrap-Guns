package net.atobaazul.scguns_sg.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import top.ribs.scguns.init.ModCreativeModeTabs;

import static net.atobaazul.scguns_sg.ScrapGuns.MOD_ID;
import static net.atobaazul.scguns_sg.registries.ModItems.*;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);


    public static final RegistryObject<CreativeModeTab> SCGUNS_SG_TAB = CREATIVE_MODE_TABS.register("scguns_sg_tab", () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.DAKKA.get())).title(Component.translatable("creativetab.scguns_sg_tab")).displayItems((pParameters, output) -> {
        addItemWithFullAmmo(output, STONKA);
        addItemWithFullAmmo(output, SLUGGA);
        addItemWithFullAmmo(output, SHOOTA);
        addItemWithFullAmmo(output, BLINKIE);
        addItemWithFullAmmo(output, DAKKA);
        addItemWithFullAmmo(output, TAKAH);
        addItemWithFullAmmo(output, DRUMMA);
        addItemWithFullAmmo(output, TURRET);
        addItemWithFullAmmo(output, PUNCHA);
        addItemWithFullAmmo(output, ROKKIT_LAUNCHA);

        addItem(output, SCRAP_BLUEPRINT);
        addItem(output, SCRAP_CORE);
    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

    private static void addItem(CreativeModeTab.Output output, RegistryObject<? extends Item> item) {
        output.accept(item.get());
    }



    private static void addItemWithFullAmmo(CreativeModeTab.Output output, RegistryObject<? extends Item> item) {
        ModCreativeModeTabs.CreativeTabHelper.addItemWithFullAmmo(output, item.get());
    }
}