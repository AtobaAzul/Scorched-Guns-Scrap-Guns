package net.atobaazul.scguns_sg.common.entity.PiglinJunker;

import java.util.List;
import java.util.Optional;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;

import net.atobaazul.scguns_sg.common.entity.ai.AimWhenNotWalking;
import net.atobaazul.scguns_sg.common.entity.ai.ApproachTargetIfCannotAim;
import net.atobaazul.scguns_sg.common.entity.ai.AttackLastHurtIfNear;
import net.atobaazul.scguns_sg.common.entity.ai.BreakBlocksToTarget;
import net.atobaazul.scguns_sg.common.entity.ai.DelayedMeleeAttackWithEffects;
import net.atobaazul.scguns_sg.common.entity.ai.ModBrainActivities;
import net.atobaazul.scguns_sg.common.entity.ai.ModBrainMemories;
import net.atobaazul.scguns_sg.common.entity.ai.ModBrainSensors;
import net.atobaazul.scguns_sg.common.entity.util.BrainCommons;
import net.atobaazul.scguns_sg.common.entity.util.BrainUtils;
import net.atobaazul.scguns_sg.common.entity.util.MobUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromAttackTargetIfTargetOutOfReach;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
import net.minecraft.world.entity.ai.behavior.StartAttacking;
import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class PiglinJunkerAi {
    private static final ImmutableList<? extends SensorType<? extends Sensor<? super PathfinderMob>>> SENSOR_TYPES = ImmutableList.of(
        SensorType.NEAREST_LIVING_ENTITIES,
        ModBrainSensors.EXTREMELY_LONG_RANGE_PLAYER.get(),
        SensorType.HURT_BY
    );

    private static final ImmutableList<? extends MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(
        MemoryModuleType.ATTACK_TARGET,
        MemoryModuleType.NEAREST_LIVING_ENTITIES,
        MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
        MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER,
        MemoryModuleType.LOOK_TARGET,
        MemoryModuleType.WALK_TARGET,
        MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
        MemoryModuleType.PATH,
        MemoryModuleType.ATTACK_COOLING_DOWN,
        ModBrainMemories.AIM_TICKS.get(),
        ModBrainMemories.WEAPON_IDEAL_RANGE.get(),
        ModBrainMemories.WEAPON_MAX_RANGE.get(),
        ModBrainMemories.STUNNED.get(),
        ModBrainMemories.STUNNED_COOLING_DOWN.get(),
        ModBrainMemories.HEADSHOT_COUNT.get(),
        ModBrainMemories.DELAYED_MELEE.get(),
        ModBrainMemories.BREATHING.get()
    );

    public static <T extends PathfinderMob> Brain<?> makeBrain(T mob, Brain<T> brain) {
        BrainCommons.initCoreWithStunActivity(brain, PiglinJunkerEntity.instance.getStunDuration());
        initIdleActivity(brain);
        BrainCommons.initStunnedActivity(brain);
        initFightActivity(mob, brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }

    public static final TagKey<EntityType<?>> tag = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("scgextra", "factions/piglin"));

    public static <T extends PathfinderMob> Brain.Provider<T> brainProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    public static <T extends PathfinderMob> void initFightActivity(T mob, Brain<T> brain) {
        brain.addActivityAndRemoveMemoriesWhenStopped(Activity.FIGHT, BrainUtils.createPriorityPairs(10,
            ImmutableList.of(
                StopAttackingIfTargetInvalid.create(target -> !BrainUtils.isTargetStillValidNonFriendlies(mob, target, false)),
                AttackLastHurtIfNear.create((self, target) -> true, false),
                SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(1.0F),
                new ApproachTargetIfCannotAim(1F),
                new AimWhenNotWalking(),
                new DelayedMeleeAttackWithEffects(PiglinJunkerEntity.MELEE_DELAY, PiglinJunkerEntity.MELEE_DURATION, 4f, 20, List.of(
                    MobEffects.MOVEMENT_SLOWDOWN
                ),
                List.of(
                    2400
                )){
                    @Override
                    protected boolean checkExtraStartConditions(ServerLevel level, Mob mob) {
                        return super.checkExtraStartConditions(level, mob) && (mob.getBrain().hasMemoryValue(ModBrainMemories.BREATHING.get()) ? !mob.getBrain().getMemory(ModBrainMemories.BREATHING.get()).get() : true);
                    }

                    @Override
                    protected boolean canStillUse(ServerLevel level, Mob mob, long gameTime) {
                        return super.canStillUse(level, mob, gameTime) && (mob.getBrain().hasMemoryValue(ModBrainMemories.BREATHING.get()) ? !mob.getBrain().getMemory(ModBrainMemories.BREATHING.get()).get() : true);
                    }
                },
                new BreakBlocksToTarget(PiglinJunkerAi::canBreak, 20),
                new MountedgunShoot(160, 2, 4),
                new Breathe(300, 8),
                new SummonBrutes(400, 6)
            )), ImmutableSet.of(
                Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT)
            ), ImmutableSet.of(
                ModBrainMemories.AIM_TICKS.get()
            )
        );
    }

    public static void updateActivity(PiglinJunkerEntity mob) {
        Brain<PiglinJunkerEntity> brain = mob.getBrain();
        Activity oldActivity = brain.getActiveNonCoreActivity().orElse(null);
        brain.setActiveActivityToFirstValid(ImmutableList.of(
            ModBrainActivities.STUNNED.get(),
            Activity.AVOID,
            Activity.FIGHT,
            Activity.IDLE
        ));

        Activity newActivity = brain.getActiveNonCoreActivity().orElse(null);
        if (oldActivity != newActivity
                && (newActivity == ModBrainActivities.STUNNED.get())) {
            brain.stopAll((ServerLevel) mob.level(), mob);
            mob.getNavigation().stop();
        }
    }
    private static boolean canBreak(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (state.isAir()) return false;

        float hardness = state.getDestroySpeed(level, pos);
        if (hardness < 0) return false;  // Unbreakable
        if (hardness >= 8) return false;  // Ex. Iron Block: 5, Stone: 1.5

        return !state.is(BlockTags.NEEDS_DIAMOND_TOOL);
    }

    public static void initIdleActivity(Brain<? extends PathfinderMob> brain) {
        brain.addActivity(Activity.IDLE, 10, ImmutableList.of(
            StartAttacking.create(PiglinJunkerAi::getHurtByNonFriendlies),
            StartAttacking.create(BrainUtils::findNearestVisibleAttackablePlayer),
            StartAttacking.create(PiglinJunkerAi::findNearestAttackableFactionEnemy),
            new RunOne<>(ImmutableList.of(
                    Pair.of(RandomStroll.stroll(1.0F), 2),
                    Pair.of(SetWalkTargetFromLookTarget.create(1.0F, 3), 2),
                    Pair.of(new DoNothing(30, 60), 1)
            ))
        ));
    }
    public static Optional<? extends LivingEntity> findNearestAttackableFactionEnemy(LivingEntity mob) {
        Brain<? extends LivingEntity> brain = mob.getBrain();
        Optional<NearestVisibleLivingEntities> optional = brain.getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES);
        return optional.flatMap(entities -> entities.findClosest(
                entity -> mob.canAttack(entity) && (MobUtil.isEnemyToPiglin(entity) ? true : entity.getType() == EntityType.WITHER_SKELETON) && !entity.getType().is(tag)));
    }
    public static Optional<LivingEntity> getHurtByNonFriendlies(LivingEntity mob) {
        return mob.getBrain()
                .getMemory(MemoryModuleType.HURT_BY_ENTITY)
                .filter(entity -> mob != entity)
                .filter(entity -> (MobUtil.isEnemyToPiglin(entity) ? true : entity.getType() == EntityType.WITHER_SKELETON) && !entity.getType().is(tag))
                .filter(mob::canAttack)
                .filter(entity -> isWithinRange(mob, entity));
    }
    private static boolean isWithinRange(LivingEntity entity, LivingEntity target) {
        double range = Math.max(entity.getAttributeValue(Attributes.FOLLOW_RANGE), 2.0D);
        return entity.closerThan(target, range);
    }
}
