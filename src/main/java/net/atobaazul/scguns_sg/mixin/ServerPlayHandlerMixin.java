package net.atobaazul.scguns_sg.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mrcrayfish.framework.api.network.LevelLocation;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import top.ribs.scguns.Config;
import top.ribs.scguns.common.Gun;
import top.ribs.scguns.common.ProjectileManager;
import top.ribs.scguns.common.network.ServerPlayHandler;
import top.ribs.scguns.entity.projectile.ProjectileEntity;
import top.ribs.scguns.init.ModEnchantments;
import top.ribs.scguns.interfaces.IProjectileFactory;
import top.ribs.scguns.item.GunItem;
import top.ribs.scguns.common.item.gun.AnimatedScrapGunItem;
import top.ribs.scguns.network.PacketHandler;
import top.ribs.scguns.network.message.S2CMessageBulletTrail;
import top.ribs.scguns.util.GunEnchantmentHelper;


@Mixin(ServerPlayHandler.class)
public class ServerPlayHandlerMixin {


    @WrapMethod(method = "fireProjectiles", remap = false)
    private static void scguns_sg$fireProjectiles(Level world, ServerPlayer player, ItemStack heldItem, GunItem item, Gun modifiedGun, Operation<Void> original) {
        if (heldItem != null && heldItem.getItem() instanceof AnimatedScrapGunItem scrapGun && scrapGun.isVolley()) {
            CompoundTag tag = heldItem.getOrCreateTag();
            int currentAmmo = player.isCreative() ? modifiedGun.getProjectile().getProjectileAmount() : tag.getInt("AmmoCount");
            int count = Math.min(currentAmmo, scrapGun.getVolleyAmount()) * modifiedGun.getProjectile().getProjectileAmount();
            Gun.Projectile projectileProps = modifiedGun.getProjectile(heldItem);
            ProjectileEntity[] spawnedProjectiles = new ProjectileEntity[count];

            for (int i = 0; i < count; i++) {
                IProjectileFactory factory = ProjectileManager.getInstance().getFactory(ForgeRegistries.ITEMS.getKey(projectileProps.getItem()));
                ProjectileEntity projectileEntity = factory.create(world, player, heldItem, item, modifiedGun);
                projectileEntity.setWeapon(heldItem);
                projectileEntity.setAdditionalDamage(Gun.getAdditionalDamage(heldItem));
                world.addFreshEntity(projectileEntity);
                spawnedProjectiles[i] = projectileEntity;
                projectileEntity.tick();
            }

            if (!projectileProps.shouldHideProjectile()) {
                scguns_sg$sendProjectileTrail(player, spawnedProjectiles, projectileProps, false);
            }
        }
        original.call(world, player, heldItem, item, modifiedGun);
    }

    @Unique
    private static void scguns_sg$sendProjectileTrail(ServerPlayer player, ProjectileEntity[] projectiles, Gun.Projectile projectileProps, boolean b) {
        if (projectileProps.shouldHideTrail()) {
            return;
        }

        double spawnX = player.getX();
        double spawnY = player.getY() + 1.0;
        double spawnZ = player.getZ();
        double radius = Config.COMMON.network.projectileTrackingRange.get();
        ParticleOptions data = GunEnchantmentHelper.getParticle(player.getMainHandItem());

        S2CMessageBulletTrail messageBulletTrail = new S2CMessageBulletTrail(projectiles, projectileProps, player.getId(), data, true);

        PacketHandler.getPlayChannel().sendToNearbyPlayers(() -> LevelLocation.create(player.level(), spawnX, spawnY, spawnZ, radius), messageBulletTrail);
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
                    //seperate chance from phantom rounds, so both can be stacked.
                    if (!tag.getBoolean("IgnoreAmmo") && player.level().random.nextInt(4 - Mth.clamp(3, 1, 2)) != 0) {
                        int level = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.RECLAIMED.get(), heldItem);
                        if (level == 0 || player.level().random.nextInt(4 - Mth.clamp(level, 1, 2)) != 0) {
                            tag.putInt("AmmoCount", Math.max(0, currentAmmo - count));
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
