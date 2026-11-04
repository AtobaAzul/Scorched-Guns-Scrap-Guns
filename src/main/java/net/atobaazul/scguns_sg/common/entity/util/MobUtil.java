package net.atobaazul.scguns_sg.common.entity.util;

import com.mrcrayfish.framework.api.network.LevelLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import top.ribs.scguns.Config;
import top.ribs.scguns.item.GunItem;
import top.ribs.scguns.item.attachment.IAttachment;

import javax.annotation.Nullable;

/**
 * @author: Krei(github: rekales)
 */
public class MobUtil {

    // Common Constants
    public static final int DEFAULT_STUN_DURATION = 60;
    public static final TagKey<EntityType<?>> PIGLIN_FACTION = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("scgextra", "factions/piglin"));
    public static final TagKey<EntityType<?>> ASGHARIAN_FACTION = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("scgextra", "factions/asgharian"));
    public static final TagKey<EntityType<?>> COG_FACTION = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("scgextra", "factions/cog"));
    public static final TagKey<EntityType<?>> FAC_FACTION = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("scgextra", "factions/fac"));
    public static final TagKey<EntityType<?>> RRC_FACTION = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("scgextra", "factions/rrc"));
    public static final TagKey<EntityType<?>> WHALER_FACTION = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("scgextra", "factions/whaler"));
    public static final TagKey<EntityType<?>> WRECKERS_FACTION = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("scgextra", "factions/wreckers"));

    public static void addGunAttachment(ItemStack gunStack, ItemStack attachmentStack) {
        if (gunStack.getItem() instanceof GunItem gunItem
                && attachmentStack.getItem() instanceof IAttachment<?> attachment
                && gunItem.getGun().canAttachType(attachment.getType())
                && attachment.canAttachTo(gunStack)) {

            CompoundTag tag = gunStack.getOrCreateTag();
            CompoundTag attachments;
            if (tag.contains("Attachments")) {
                attachments = tag.getCompound("Attachments");
            } else {
                attachments = new CompoundTag();
            }

            attachments.put(attachment.getType().getTagKey(), attachmentStack.save(new CompoundTag()));

            tag.put("Attachments", attachments);
        }
    }

    public static Vec3 lerpVec(Vec3 a, Vec3 b, double t) {
        return new Vec3(
                Mth.lerp(t, a.x, b.x),
                Mth.lerp(t, a.y, b.y),
                Mth.lerp(t, a.z, b.z)
        );
    }

    public static SoundEvent getSound(RandomSource random, SoundEvent... sounds){
        if(sounds.length<=0){
            return SoundEvents.ALLAY_HURT;//cause why not
        }
        return sounds[random.nextInt(sounds.length)];
    }

    public static LevelLocation levelLocationFromEntity(LivingEntity entity) {
        return LevelLocation.create(
                entity.level(),
                entity.getX(),
                entity.getY(),
                entity.getZ(),
                Config.COMMON.network.projectileTrackingRange.get()
        );
    }

    /**
     * Used to gradually turn an entity to a direction. Intended to be invoked every tick when being used.
     */
    public static void turnEntityToYaw(LivingEntity entity, float yaw, float turnSpeed) {
        entity.setYRot(Mth.approachDegrees(entity.getYRot(), yaw, turnSpeed));
        entity.setYHeadRot(entity.getYRot());
        entity.setYBodyRot(entity.getYRot());
    }

    public static void putBlockPosToTag(BlockPos blockPos, String prefix, CompoundTag tag) {
        tag.putInt(prefix + "X", blockPos.getX());
        tag.putInt(prefix + "Y", blockPos.getY());
        tag.putInt(prefix + "Z", blockPos.getZ());
    }

    public static @Nullable BlockPos getBlocKPosFromTag(String prefix, CompoundTag tag) {
        if (tag.contains(prefix+"X")) {
            return new BlockPos(tag.getInt(prefix+"X"), tag.getInt(prefix+"Y"), tag.getInt(prefix+"Z"));
        }
        return null;
    }

    public static Vec3 vecFromRot(float yRot) {
        float yaw = (float) Math.toRadians(yRot);
        float x = -Mth.sin(yaw);
        float z = Mth.cos(yaw);
        return new Vec3(x, 0, z);
    }

    public static float rotFromVec(Vec3 vec) {
        return (float) Mth.atan2(-vec.x, vec.z) * Mth.RAD_TO_DEG;
    }

    public static Vec3 toVec(float yaw, float pitch) {
        double yawRad   = Math.toRadians(yaw);
        double pitchRad = Math.toRadians(pitch);
        double x = -Math.sin(yawRad) * Math.cos(pitchRad);
        double y = -Math.sin(pitchRad);
        double z =  Math.cos(yawRad) * Math.cos(pitchRad);

        return new Vec3(x, y, z);
    }

    public static void tickDeath(LivingEntity entity, int tick) {
        // Override to only extend death time
        ++entity.deathTime;
        if (entity.deathTime >= tick && !entity.level().isClientSide() && !entity.isRemoved()) {
            entity.level().broadcastEntityEvent(entity, (byte)60);
            entity.remove(RemovalReason.KILLED);
        }
    }

    public static boolean isEnemyToPiglin(LivingEntity enemy){
        return 
        (ForgeRegistries.ENTITY_TYPES.tags().getTag(WRECKERS_FACTION)!=null && enemy.getType().is(WRECKERS_FACTION)) ||
        (ForgeRegistries.ENTITY_TYPES.tags().getTag(WHALER_FACTION)!=null && enemy.getType().is(WHALER_FACTION)) ||
        (ForgeRegistries.ENTITY_TYPES.tags().getTag(RRC_FACTION)!=null && enemy.getType().is(RRC_FACTION)) ||
        (ForgeRegistries.ENTITY_TYPES.tags().getTag(FAC_FACTION)!=null && enemy.getType().is(FAC_FACTION)) ||
        (ForgeRegistries.ENTITY_TYPES.tags().getTag(COG_FACTION)!=null && enemy.getType().is(COG_FACTION)) ||
        (ForgeRegistries.ENTITY_TYPES.tags().getTag(ASGHARIAN_FACTION)!=null && enemy.getType().is(ASGHARIAN_FACTION));
    }
}
