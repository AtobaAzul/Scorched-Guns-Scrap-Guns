package net.atobaazul.scguns_sg.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import top.ribs.scguns.Config;
import top.ribs.scguns.common.Gun;
import top.ribs.scguns.common.item.gun.AnimatedScrapGunItem;
import top.ribs.scguns.common.network.ServerPlayHandler;
import top.ribs.scguns.init.ModEnchantments;
import top.ribs.scguns.item.GunItem;

@Mixin(ServerPlayHandler.class)
public class ServerPlayHandlerMixin {


    @WrapMethod(method = "fireProjectiles", remap = false)
    private static void scguns_sg$fireProjectiles(Level world, ServerPlayer player, ItemStack heldItem, GunItem item, Gun modifiedGun, Operation<Void> original) {
        if (heldItem != null && heldItem.getItem() instanceof AnimatedScrapGunItem scrapGun && scrapGun.isVolley()) {
            CompoundTag tag = heldItem.getOrCreateTag();
            int currentAmmo = player.isCreative() ? modifiedGun.getReloads().getMaxAmmo() : tag.getInt("AmmoCount");
            int count = Math.min(currentAmmo, scrapGun.getVolleyAmount());

            for (int i = 0; i < count; i++) {
                original.call(world, player, heldItem, item, modifiedGun);
            }
        } else {
            original.call(world, player, heldItem, item, modifiedGun);
        }
    }



    }

    @WrapMethod(method = "consumeAmmo", remap = false)
    private static void scguns_sg$consumeAmmo(ServerPlayer player, ItemStack heldItem, Operation<Void> original) {
        if (heldItem != null & heldItem.getItem().asItem() instanceof AnimatedScrapGunItem) {
            AnimatedScrapGunItem gunItem = (AnimatedScrapGunItem) heldItem.getItem();

            if (gunItem.isVolley() && gunItem.isVolley()) {
                CompoundTag tag = heldItem.getOrCreateTag();
                int currentAmmo = tag.getInt("AmmoCount");
                int count = Math.min(currentAmmo, gunItem.getVolleyAmount());

                if (!player.isCreative()) {
                    if (!tag.getBoolean("IgnoreAmmo")) {
                        for (int i = 0; i < count+1; i++) {
                            tag = heldItem.getOrCreateTag();
                            currentAmmo = tag.getInt("AmmoCount");
                            count = Math.min(currentAmmo, gunItem.getVolleyAmount());
                            //seperate chance from phantom rounds, so both can be stacked.
                            if (player.level().random.nextInt(4 - Mth.clamp(3, 1, 2)) != 0) {
                                int level = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.RECLAIMED.get(), heldItem);
                                if (level == 0 || player.level().random.nextInt(4 - Mth.clamp(level, 1, 2)) != 0) {
                                    tag.putInt("AmmoCount", Math.max(0, currentAmmo - 1));
                                }
                            }
                        }
                    }
                }
            } else {
                //seperate chance from phantom rounds, so both can be stacked.
                if (player.level().random.nextInt(4 - Mth.clamp(3, 1, 2)) != 0) {
                    original.call(player, heldItem);
                }
            }
        } else {
            original.call(player, heldItem);
        }
    }
}
