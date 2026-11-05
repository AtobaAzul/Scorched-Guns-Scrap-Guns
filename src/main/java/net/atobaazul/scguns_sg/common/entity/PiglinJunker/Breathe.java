package net.atobaazul.scguns_sg.common.entity.PiglinJunker;

import java.util.List;

import com.google.common.collect.ImmutableMap;

import net.atobaazul.scguns_sg.common.entity.ai.ModBrainMemories;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.phys.Vec3;
import top.ribs.scguns.init.ModEffects;
import top.ribs.scguns.init.ModParticleTypes;
import top.ribs.scguns.init.ModTags;

public class Breathe extends Behavior<Mob> {
    private int cooldown = 0;
    private final int cooldownDuration;
    double range = 0;
    public Breathe(int cooldownDuration) {
        super(ImmutableMap.of(
                MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT
        ), 70);
        this.cooldownDuration = cooldownDuration;
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
        this.range = 0;
        if(entity instanceof PiglinJunkerEntity pe)pe.triggerAnim("behavior", "breathe");
        entity.getBrain().setMemory(ModBrainMemories.BREATHING.get(), true);
    }

    @Override
    protected void stop(ServerLevel p_22548_, Mob p_22549_, long p_22550_) {
        p_22549_.getBrain().setMemory(ModBrainMemories.BREATHING.get(), false);
    }

    @Override
    protected void tick(ServerLevel level, Mob entity, long gameTime) {
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
            Vec3 lookAngle = owner.getLookAngle();
            Vec3 eyePosition = owner.getEyePosition();
            this.range+=0.2285714286;
            double coneAngle = Math.toRadians(20);
            double step = 0.5;
            for (double distance = 0; distance <= range; distance += step) {
                double radius = Math.tan(coneAngle) * distance;
                double rad = radius * Math.sqrt(Math.random());
                for (double angle = 0; angle < Math.PI * 2; angle += 0.2) {
                    Vec3 right = lookAngle.cross(new Vec3(0, 1, 0)).normalize();
                    double offsetX = Math.cos(angle) * rad;
                    Vec3 forwardPoint = eyePosition.add(lookAngle.scale(distance)).add(right.scale(offsetX));
                    level.sendParticles(ModParticleTypes.SULFUR_SMOKE.get(), forwardPoint.x, forwardPoint.y-0.5, forwardPoint.z, 1, 0, 0, 0, 0.1);
                    List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, owner.getBoundingBox().inflate(16), e -> !e.is(owner));
                    Vec3 pos = new Vec3(forwardPoint.x, forwardPoint.y-0.8, forwardPoint.z);
                    for(LivingEntity le : list){
                        if(le.distanceToSqr(pos)<=2){
                            if(!le.getType().is(ModTags.Entities.IGNORES_SULFUR_GAS)){
                                le.addEffect(new MobEffectInstance(ModEffects.SULFUR_POISONING.get(), 200));
                            }
                        }
                    }
                }
            }
        }
    }
}
