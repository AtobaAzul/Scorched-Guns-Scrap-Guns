package net.atobaazul.scguns_sg.common.entity.goal;

import java.lang.ref.WeakReference;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
/*
 * @author: Krei(github: rekales)
 */
public class MoveToRaidTargetGoal extends Goal{
    private final Mob mob;
    private final WeakReference<LivingEntity> targetRef;
    
    public MoveToRaidTargetGoal(Mob mob, LivingEntity target) {
       this.mob = mob;
       this.targetRef = new WeakReference<>(target);
    }
    
    public boolean canUse() {
       if (!this.mob.isRemoved() && !this.mob.isDeadOrDying()) {
          LivingEntity target = (LivingEntity)this.targetRef.get();
          if (target != null && !target.isRemoved() && !target.isDeadOrDying()) {
             return !this.mob.getBrain().hasMemoryValue(MemoryModuleType.WALK_TARGET);
          } else {
             return false;
          }
       } else {
          return false;
       }
    }
    
    public void tick() {
       LivingEntity target = (LivingEntity)this.targetRef.get();
       if (target != null) {
          this.mob.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(target, 1.0F, 4));
       }
    }
    
    public boolean requiresUpdateEveryTick() {
       return true;
    }
}