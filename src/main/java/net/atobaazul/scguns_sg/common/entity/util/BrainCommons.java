package net.atobaazul.scguns_sg.common.entity.util;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;

import net.atobaazul.scguns_sg.common.entity.ai.CheckHeadshotStun;
import net.atobaazul.scguns_sg.common.entity.ai.ModBrainActivities;
import net.atobaazul.scguns_sg.common.entity.ai.ModBrainMemories;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.schedule.Activity;

import java.util.Optional;

/**
 * @author: Krei(github: rekales)
 */
public final class BrainCommons {

    public static void initCoreWithStunActivity(Brain<? extends Mob> brain, int stunDuration) {
        brain.addActivity(Activity.CORE, 0, ImmutableList.of(
                new LookAtTargetSink(45, 90),
                new MoveToTargetSink(),
                new CheckHeadshotStun(5, stunDuration, 200)
        ));
    }

    // Not for flying and swimming mobs
    public static void initIdleActivity(Brain<? extends PathfinderMob> brain) {
        brain.addActivity(Activity.IDLE, 10, ImmutableList.of(
                StartAttacking.create(BrainUtils::getHurtByNonFriendlies),
                StartAttacking.create(BrainUtils::findNearestVisibleAttackablePlayer),
                StartAttacking.create(BrainUtils::findNearestAttackableFactionEnemy),
                new RunOne<>(ImmutableList.of(
                        Pair.of(RandomStroll.stroll(1.0F), 2),
                        Pair.of(SetWalkTargetFromLookTarget.create(1.0F, 3), 2),
                        Pair.of(new DoNothing(30, 60), 1)
                ))
        ));
    }

    public static void initStunnedActivity(Brain<? extends Mob> brain) {
        brain.addActivityAndRemoveMemoryWhenStopped(ModBrainActivities.STUNNED.get(), 10, ImmutableList.of(
                        new HandleStunnedVisuals()
                ), ModBrainMemories.STUNNED.get()
        );
    }

    public static void updateActivity(Mob mob) {
        mob.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
    }

    public static void updateAimingAggressive(Mob mob) {
        boolean aggressive = mob.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).isPresent()
                && mob.getBrain().getMemory(ModBrainMemories.AIM_TICKS.get()).filter(aim -> aim > 5).isPresent();
        if (mob.isAggressive() != aggressive) {
            mob.setAggressive(aggressive);
        }
    }

    public static void updateHasTargetAggressive(Mob mob) {
        boolean aggressive = mob.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).isPresent();
        if (mob.isAggressive() != aggressive) {
            mob.setAggressive(aggressive);
        }
    }

    public static void updateMaxRangeAggressive(Mob mob) {
        boolean aggressive;
        Optional<LivingEntity> target = mob.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET);
        //noinspection OptionalIsPresent
        if (target.isPresent()) {
            aggressive = mob.closerThan(target.get(), mob.getBrain().getMemory(ModBrainMemories.WEAPON_MAX_RANGE.get()).orElse(16F));
        } else {
            aggressive = false;
        }

        if (mob.isAggressive() != aggressive) {
            mob.setAggressive(aggressive);
        }
    }
}
