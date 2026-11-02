package net.atobaazul.scguns_sg.common.entity.PiglinJunker;

import com.mojang.serialization.Dynamic;

import net.atobaazul.scguns_sg.client.ExpandedAnimationController;
import net.atobaazul.scguns_sg.common.entity.ai.ModBrainMemories;
import net.atobaazul.scguns_sg.common.entity.gun.AttachedMountedGun;
import net.atobaazul.scguns_sg.common.entity.gun.BulletSpawnOffset;
import net.atobaazul.scguns_sg.common.entity.gun.CustomScorchedSimGun;
import net.atobaazul.scguns_sg.common.entity.gun.Gunner;
import net.atobaazul.scguns_sg.common.entity.gun.MountedGun;
import net.atobaazul.scguns_sg.common.entity.gun.SimulatedGun;
import net.atobaazul.scguns_sg.common.entity.util.BrainCommons;
import net.atobaazul.scguns_sg.common.entity.util.MobUtil;
import net.atobaazul.scguns_sg.common.entity.util.StunnableHog;
import net.atobaazul.scguns_sg.common.network.GunFlashMessage;
import net.atobaazul.scguns_sg.common.network.SGPacketHandler;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import top.ribs.scguns.ScorchedGuns;
import top.ribs.scguns.common.Gun;
import top.ribs.scguns.init.ModItems;

public class PiglinJunkerEntity extends Monster implements GeoEntity, Gunner, BulletSpawnOffset{

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("idle");
    private static final RawAnimation WALK = RawAnimation.begin().thenLoop("walk");
    private static final RawAnimation SHOOT_STANDING = RawAnimation.begin().thenPlay("shoot");
    private static final RawAnimation SHOOT_WALKING = RawAnimation.begin().thenPlay("shoot_walk");
    private static final RawAnimation MELEE = RawAnimation.begin().thenPlay("melee");
    private static final RawAnimation BREATH = RawAnimation.begin().thenPlay("breath");
    private static final RawAnimation STUN_START = RawAnimation.begin().thenPlay("stun_start");
    private static final RawAnimation STUN_MIDDLE = RawAnimation.begin().thenPlay("stun_middle");
    private static final RawAnimation STUN_END = RawAnimation.begin().thenPlay("stun_end");
    private static final RawAnimation DEATH = RawAnimation.begin().thenPlayAndHold("death");

    public static final Vec3 BAD_TEETHS = new Vec3(0 / 16.0D, 21D / 16.0D, -24D / 16.0D * -1D);

    public static final StunnableHog instance = new StunnableHog(
        9, 
        45, 
        35
    );

    public static int MELEE_DELAY = 15;
    public static int MELEE_DURATION = 31;

    public final Vec3 BarrelOffest = new Vec3(23f/16f * -1, 25f/16f, -51f/16f * -1);

    public final MountedGun barrel;

    public PiglinJunkerEntity(EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
        this.barrel = new AttachedMountedGun(this,
            new CustomScorchedSimGun.Builder(ModItems.BIRDFEEDER.get().getGun())
                .projectileDamage(3f)
                .fireRate(1)
                .maxRange(20)
                .idealRange(16)
                .ammoCapacity(6)
                .reloadTime(120)
                .projectileDamage(10)
                .gunIndex(0)
                .noGunFlash()
                .build(),
            BarrelOffest,
            (a) -> true
        );
    }

    private static final EntityDataAccessor<Long> TICKS_REMAINING = SynchedEntityData.defineId(PiglinJunkerEntity.class, EntityDataSerializers.LONG);

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }

    @Override
    public void registerControllers(ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "main", 4, state -> {
            if(state.getAnimatable().isStunned()){
                if(PiglinJunkerEntity.instance.isStart(state.getAnimatable().getStunTicksRemaining())){
                    state.setAnimation(STUN_START);
                }else if(PiglinJunkerEntity.instance.isMiddle(state.getAnimatable().getStunTicksRemaining())){
                    state.setAnimation(STUN_MIDDLE);
                }else if(PiglinJunkerEntity.instance.isEnd(state.getAnimatable().getStunTicksRemaining())){
                    state.setAnimation(STUN_END);
                }
            }
            else if(!state.isCurrentAnimation(BREATH) && !state.isCurrentAnimation(SHOOT_WALKING) && !state.isCurrentAnimation(SHOOT_STANDING)){
                if (state.isMoving()) {
                    state.setAnimation(WALK);
                } else {
                    state.setAnimation(IDLE);
                }
            }
            return PlayState.CONTINUE;
        }));

        controllers.add(new AnimationController<>(this, "death", 2, state -> {
            if (state.getAnimatable().isDeadOrDying()) {
                return state.setAndContinue(DEATH);
            }
            return PlayState.STOP;
        }));

        controllers.add(new ExpandedAnimationController<>(this, "behavior", 0, state -> {
            return PlayState.CONTINUE;
        })
            .triggerableAnim("melee", MELEE)
            .triggerableAnim("breathe", BREATH)
            .triggerableAnim("shoot", (ctr)->{
                if (((this.getX() - this.xo)*(this.getX() - this.xo))+((this.getZ() - this.zo)*(this.getZ() - this.zo))>0.0002) {
                    return SHOOT_WALKING;
                } else {
                    return SHOOT_STANDING;
                }
            })
        );
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
        .add(Attributes.ATTACK_KNOCKBACK, 3.0D)
        .add(Attributes.KNOCKBACK_RESISTANCE, 0.9D)
        .add(Attributes.ARMOR, 6.0D)
        .add(Attributes.MAX_HEALTH, 1000.0D);
    }
    public boolean isStunned(){
        return this.entityData.get(TICKS_REMAINING) > 0;
    }
    public long getStunTicksRemaining(){
        return this.entityData.get(TICKS_REMAINING);
    }

    public void setStunTicksRemaining(long l){
        this.entityData.set(TICKS_REMAINING, l);
    }

    @Override
    protected void tickDeath() {
        MobUtil.tickDeath(this, 20);
    }

    @Override
    public void tick() {
        super.tick();
        if(!this.level().isClientSide()){
            if (brain.getTimeUntilExpiry(ModBrainMemories.DELAYED_MELEE.get()) == MELEE_DURATION) {
                this.triggerAnim("behavior", "melee");
            }
            if(this.getBrain().hasMemoryValue(ModBrainMemories.STUNNED.get())){
                this.setStunTicksRemaining(this.getBrain().getTimeUntilExpiry(ModBrainMemories.STUNNED.get()));
            }else{
                this.setStunTicksRemaining(Long.valueOf(-1));
            }
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TICKS_REMAINING, Long.valueOf(-1));
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> dynamic) {
        return PiglinJunkerAi.makeBrain(this, this.brainProvider().makeBrain(dynamic));
    }

    @SuppressWarnings("unchecked")
    @Override
    public Brain<PiglinJunkerEntity> getBrain() {
        return (Brain<PiglinJunkerEntity>) super.getBrain();
    }

    @Override
    protected Brain.Provider<PiglinJunkerEntity> brainProvider() {
        return PiglinJunkerAi.brainProvider();
    }

    @Override
    protected void customServerAiStep() {
        this.level().getProfiler().push("piglinJunkerBrain");
        this.getBrain().tick((ServerLevel)this.level(), this);
        PiglinJunkerAi.updateActivity(this);
        BrainCommons.updateMaxRangeAggressive(this);
        this.level().getProfiler().pop();
        super.customServerAiStep();
    }

    @Override
    public void onGunFire(SimulatedGun gun, Vec3 targetPos) {
        Gun.Display.Flash flash = ModItems.BIRDFEEDER.get().getGun().getDisplay().getFlash();
        if (flash == null) return;
        ResourceLocation flashTexture = 
            ResourceLocation.fromNamespaceAndPath(
                ScorchedGuns.MODID, 
                "textures/effect/" + flash.getTextureLocation() + ".png"
            );
        SGPacketHandler.sendToNearbyPlayers(
            () -> MobUtil.levelLocationFromEntity(this), 
            new GunFlashMessage(
                this.getId(), 
                0, 
                flashTexture, 
                false, 
                1.2F
            )
        );
    }
}
