package net.atobaazul.scguns_sg.common.entity.PiglinJunker;

import net.atobaazul.scguns_sg.common.entity.gun.BulletSpawnOffset;
import net.atobaazul.scguns_sg.common.entity.gun.Gunner;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class PiglinJunkerEntity extends Monster implements GeoEntity, Gunner, BulletSpawnOffset{

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    public final Vec3 BarrelOffest = new Vec3(17.5f/16f, 29f/16f, -54f/16f * -1);


    public PiglinJunkerEntity(EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }

    @Override
    public void registerControllers(ControllerRegistrar arg0) {
    }

    @Override
    public Vec3 getBulletSpawnOffset(int gunIndex) {
        return this.BarrelOffest.yRot(-this.yHeadRot * Mth.DEG_TO_RAD);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
        .add(Attributes.FOLLOW_RANGE, 128.0D)
        .add(Attributes.MOVEMENT_SPEED, 0.3F)
        .add(Attributes.ATTACK_DAMAGE, 30.0D)
        .add(Attributes.KNOCKBACK_RESISTANCE, 0.9D)
        .add(Attributes.ARMOR, 6.0D)
        .add(Attributes.MAX_HEALTH, 1000.0D);
    }
}
