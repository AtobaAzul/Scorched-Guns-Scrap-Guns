package net.atobaazul.scguns_sg.common.entity.PiglinJunker;

import com.google.common.collect.ImmutableMap;

import net.atobaazul.scguns_sg.common.entity.ai.ModBrainMemories;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.ForgeEventFactory;
import top.ribs.scguns.init.ModItems;

public class SummonBrutes extends Behavior<Mob>{
    private int cooldown = 0;
    private final int cooldownDuration;
    private final int piglinCount;
    public SummonBrutes(int cooldownDuration, int piglinCount) {
        super(ImmutableMap.of(
                MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT
        ));
        this.cooldownDuration = cooldownDuration;
        this.piglinCount = piglinCount;
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel p_22538_, Mob entity) {
        if(this.cooldown>0)this.cooldown--;
        return this.cooldown <= 0 && !entity.getBrain().hasMemoryValue(ModBrainMemories.DELAYED_MELEE.get()) && entity.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET) && (entity.getBrain().hasMemoryValue(ModBrainMemories.BREATHING.get()) ? !entity.getBrain().getMemory(ModBrainMemories.BREATHING.get()).get() : true);
    }

    @Override
    protected void start(ServerLevel level, Mob mob, long gameTime) {
        this.cooldown = this.cooldownDuration;
        for(int i = 0; i < this.piglinCount; i++){
            PiglinBrute brute = EntityType.PIGLIN_BRUTE.spawn(level, mob.blockPosition(), MobSpawnType.EVENT);
            ForgeEventFactory.onFinalizeSpawn(
                brute, level,
                level.getCurrentDifficultyAt(mob.blockPosition()),
                MobSpawnType.EVENT, null, null
            );
            brute.setItemSlot(EquipmentSlot.HEAD, new ItemStack(ModItems.ANTHRALITE_RESPIRATOR.get()));
        }
    }
}
