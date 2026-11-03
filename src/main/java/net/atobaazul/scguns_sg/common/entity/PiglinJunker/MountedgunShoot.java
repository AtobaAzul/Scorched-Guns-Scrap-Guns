package net.atobaazul.scguns_sg.common.entity.PiglinJunker;

import com.google.common.collect.ImmutableMap;

import java.util.function.BiFunction;

import net.atobaazul.scguns_sg.common.entity.ai.AccuracyBuildup;
import net.atobaazul.scguns_sg.common.entity.ai.ModBrainMemories;
import net.atobaazul.scguns_sg.common.entity.ai.PatchedEntityTracker;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.phys.Vec3;

public class MountedgunShoot extends Behavior<Mob>{

    private int ticks = 0;
    private int cooldown = 0;
    private final int cooldownDuration;
    private final int startAnimDuration;
    private final int shootEvery;
    private int numberOfShots = 0;
    private int timer = 0;

    private final BiFunction<LivingEntity, Boolean, Float> accuracyFunc = new AccuracyBuildup(0.8f, 2.4f, 0.05f);

    public MountedgunShoot(int cooldownDuration, int startAnimDuration, int shootEvery) {
        super(ImmutableMap.of(
                MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT
        ), 60);
        this.cooldownDuration = cooldownDuration;
        this.startAnimDuration = startAnimDuration;
        this.shootEvery = shootEvery;
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, Mob entity) {
        if(this.cooldown>0)this.cooldown--;
        return this.cooldown <= 0 && !entity.getBrain().hasMemoryValue(ModBrainMemories.DELAYED_MELEE.get()) && entity.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET) && (entity.getBrain().hasMemoryValue(ModBrainMemories.BREATHING.get()) ? !entity.getBrain().getMemory(ModBrainMemories.BREATHING.get()).get() : true);
    }

    @Override
    protected boolean canStillUse(ServerLevel level, Mob entity, long gameTime) {
        return entity.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET) && !entity.getBrain().hasMemoryValue(ModBrainMemories.DELAYED_MELEE.get()) && (entity.getBrain().hasMemoryValue(ModBrainMemories.BREATHING.get()) ? !entity.getBrain().getMemory(ModBrainMemories.BREATHING.get()).get() : true);
    }

    @Override
    protected void start(ServerLevel level, Mob entity, long gameTime) {
        this.cooldown = this.cooldownDuration;
        if(entity instanceof PiglinJunkerEntity pe)pe.triggerAnim("behavior", "shoot");
        this.ticks = 0;
        this.timer = 0;
        this.numberOfShots = 0;
    }

    @Override
    protected void tick(ServerLevel level, Mob entity, long gameTime) {
        this.ticks++;
        if(this.numberOfShots>=20)return;
        if(entity instanceof PiglinJunkerEntity owner){
            LivingEntity target = owner.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).get();
            owner.getLookControl().setLookAt(target);
            owner.lookAt(target, 360, 360);
            owner.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new PatchedEntityTracker(target, true));
            if(this.ticks>this.startAnimDuration){
                if(this.timer>0)this.timer--;
                if(this.timer <= 0){
                    this.numberOfShots++;
                    this.timer = this.shootEvery;
                    owner.barrel.tick(target, this.accuracyFunc.apply(entity, true));
                    Vec3 ex_pos_1 = owner.position().add(PiglinJunkerEntity.EXHAUST_BARREL.yRot(-owner.yBodyRot * Mth.DEG_TO_RAD));
                    level.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, ex_pos_1.x, ex_pos_1.y, ex_pos_1.z, 3, 0, 0, 0, 0.1);
                }
            }
        }
    }
}
