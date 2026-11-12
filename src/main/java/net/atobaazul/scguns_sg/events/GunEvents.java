package net.atobaazul.scguns_sg.events;

import net.atobaazul.scguns_sg.registries.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.ribs.scguns.Config;
import top.ribs.scguns.common.Gun;
import top.ribs.scguns.common.item.scrapgun.AnimatedScrapGunItem;
import top.ribs.scguns.event.GunEventBus;
import top.ribs.scguns.event.GunFireEvent;

import java.util.UUID;

import static net.atobaazul.scguns_sg.ScrapGuns.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class GunEvents {

    @SubscribeEvent
    public static void postShoot(GunFireEvent.Post event) {
        Player player = event.getEntity();
        ItemStack heldItem = player.getMainHandItem();
        CompoundTag tag = heldItem.getOrCreateTag();
        Level level = player.level();

        if (heldItem.getItem() instanceof AnimatedScrapGunItem gunItem && gunItem.isVolley()) {
            Gun modifiedGun = gunItem.getModifiedGun(heldItem);

            if (Config.COMMON.gameplay.spawnCasings.get()) {
                if (modifiedGun.getProjectile().ejectsCasing() && !modifiedGun.getProjectile().ejectDuringReload()) {
                    int currentAmmo = player.isCreative() ? modifiedGun.getReloads().getMaxAmmo() : tag.getInt("AmmoCount");
                    int count = Math.min(currentAmmo, gunItem.getVolleyAmount());

                    //starting from 1 because we already eject the first one by default.
                    for (int i = 1; i < count; i++) {
                        GunEventBus.ejectCasing(level, player, false);
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onItemModify(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();
        EquipmentSlot slot = event.getSlotType();

        if (stack.is(ModItems.TURRET.get()) && slot == EquipmentSlot.MAINHAND) {
            event.addModifier(Attributes.ARMOR, new AttributeModifier(UUID.fromString("f368aa47-0f52-45a9-aa20-de6a6b928af4"), "Turret Armor", 3, AttributeModifier.Operation.ADDITION));
        }
    }
}