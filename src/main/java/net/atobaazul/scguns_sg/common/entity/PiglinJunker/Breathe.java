package net.atobaazul.scguns_sg.common.entity.PiglinJunker;

import java.util.List;

import com.google.common.collect.ImmutableMap;

import net.atobaazul.scguns_sg.common.entity.ai.ModBrainMemories;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.phys.Vec3;
import top.ribs.scguns.init.ModEffects;
import top.ribs.scguns.init.ModParticleTypes;

public class Breathe extends Behavior<Mob> {
    private int ticks = 0;
    private int cooldown = 0;
    private final int cooldownDuration;
    private final int startAnimDuration;
    public Breathe(int cooldownDuration, int startAnimDuration) {
        super(ImmutableMap.of(
                MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT
        ), 70);
        this.cooldownDuration = cooldownDuration;
        this.startAnimDuration = startAnimDuration;
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, Mob entity) {
        if(this.cooldown>0)this.cooldown--;
        return this.cooldown <= 0 && !entity.getBrain().hasMemoryValue(ModBrainMemories.DELAYED_MELEE.get()) && entity.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET) && entity.distanceToSqr(entity.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).get())<=256;
    }

    @Override
    protected boolean canStillUse(ServerLevel level, Mob entity, long gameTime) {
        return entity.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET) && !entity.getBrain().hasMemoryValue(ModBrainMemories.DELAYED_MELEE.get());
    }

    @Override
    protected void start(ServerLevel level, Mob entity, long gameTime) {
        this.cooldown = this.cooldownDuration;
        if(entity instanceof PiglinJunkerEntity pe)pe.triggerAnim("behavior", "breathe");
        this.ticks = 0;
        entity.getBrain().setMemory(ModBrainMemories.BREATHING.get(), true);
    }

    @Override
    protected void stop(ServerLevel p_22548_, Mob p_22549_, long p_22550_) {
        p_22549_.getBrain().setMemory(ModBrainMemories.BREATHING.get(), false);
    }

    @Override
    protected void tick(ServerLevel level, Mob entity, long gameTime) {
        this.ticks++;
        if(entity instanceof PiglinJunkerEntity owner){
            owner.setDeltaMovement(Vec3.ZERO);
            LivingEntity target = owner.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).get();
            double dX = target.getX() - owner.getX();
            double dY = target.getEyeY() - owner.getEyeY();
            double dZ = target.getZ() - owner.getZ();
            double dist = Math.sqrt(dX * dX + dZ * dZ);
            float yaw = (float) (Math.atan2(dZ, dX) * (180D / Math.PI)) - 90.0F;
            float pitch = (float) -(Math.atan2(dY, dist) * (180D / Math.PI));
            owner.setYRot(yaw);
            owner.setXRot(pitch);
            owner.setYHeadRot(yaw);
            if(this.ticks>this.startAnimDuration){
                Vec3 mouth_pos = owner.position().add(PiglinJunkerEntity.BAD_TEETHS.yRot(-owner.yBodyRot * Mth.DEG_TO_RAD));
                level.sendParticles(ModParticleTypes.SULFUR_SMOKE.get(), mouth_pos.x, mouth_pos.y, mouth_pos.z, 20, 3, -0.01, 3, 0.1);
                List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, owner.getBoundingBox().inflate(16, 0, 16), (e)->!e.is(owner));
                for(LivingEntity le : list){
                    le.addEffect(new MobEffectInstance(ModEffects.SULFUR_POISONING.get(), 200));
                }
            }
        }
    }
}
