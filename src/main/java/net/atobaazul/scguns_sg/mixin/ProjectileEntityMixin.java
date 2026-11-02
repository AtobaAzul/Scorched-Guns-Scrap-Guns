package net.atobaazul.scguns_sg.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;

import net.atobaazul.scguns_sg.common.entity.ai.ModBrainMemories;
import net.atobaazul.scguns_sg.common.entity.parts.WeakPointBox;
import net.atobaazul.scguns_sg.common.entity.parts.WeakPointBoxManager;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.ribs.scguns.Config;
import top.ribs.scguns.entity.projectile.ProjectileEntity;
import top.ribs.scguns.util.math.ExtendedEntityRayTraceResult;

import java.util.List;
import java.util.Optional;

/**
 * @author Krei(github: rekales)
 */

@Mixin(value = ProjectileEntity.class, remap = false)
public class ProjectileEntityMixin {

    @WrapOperation(method = "onHit", at = @At(value = "INVOKE", target = "Ltop/ribs/scguns/entity/projectile/ProjectileEntity;onHitEntity(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;Z)V"))
    private void atOnHit(ProjectileEntity instance, Entity entity, Vec3 hitVec, Vec3 startVec, Vec3 endVec,
            boolean headshot,
            Operation<Void> original, @Local(name = "entityHitResult") ExtendedEntityRayTraceResult entityHitResult) {
        ProjectileEntity self = (ProjectileEntity) (Object) this;

        if (headshot) {
            SCGE$handleHeadshot(entityHitResult.getEntity(), self);
        }

        original.call(instance, entity, hitVec, startVec, endVec, headshot);
    }

    @Unique
    private static void SCGE$handleHeadshot(Entity hitEntity, ProjectileEntity projectile) {
        if (hitEntity instanceof LivingEntity livingEntity) {
            Brain<?> brain = livingEntity.getBrain();
            if (brain.checkMemory(ModBrainMemories.HEADSHOT_COUNT.get(), MemoryStatus.REGISTERED)) {
                int headshotCount = brain.getMemory(ModBrainMemories.HEADSHOT_COUNT.get()).orElse(0);
                brain.setMemory(ModBrainMemories.HEADSHOT_COUNT.get(), ++headshotCount);
            }
        }
    }

    @Inject(method = "getHitResult", at = @At(value = "INVOKE", target = "Ltop/ribs/scguns/common/BoundingBoxManager;getHeadshotBoxes(Lnet/minecraft/world/entity/EntityType;)Ltop/ribs/scguns/interfaces/IHeadshotBox;"), remap = false)
    private void beforeGetHeadshotBoxes(Entity entity, Vec3 startVec, Vec3 endVec,
            CallbackInfoReturnable<ProjectileEntity.EntityResult> cir, @Local(name = "boundingBox") AABB boundingBox,
            @Local(name = "hitPos") Vec3 hitPos, @Local(name = "headshot") LocalBooleanRef headshot) {
        List<WeakPointBox<LivingEntity>> weakPointBoxList = WeakPointBoxManager.getWeakPointBoxesList(entity.getType());

        if (weakPointBoxList != null) {
            for (WeakPointBox<LivingEntity> weakPointBox : weakPointBoxList) {
                AABB box = weakPointBox.getBoundingBox((LivingEntity) entity);
                if (box != null) {
                    box = box.move(boundingBox.getCenter().x, boundingBox.minY, boundingBox.getCenter().z);
                    Optional<Vec3> headshotHitPos = box.clip(startVec, endVec);
                    if (headshotHitPos.isEmpty()) {
                        box = box.inflate(Config.COMMON.gameplay.growBoundingBoxAmount.get(), 0.0,
                                Config.COMMON.gameplay.growBoundingBoxAmount.get());
                        headshotHitPos = box.clip(startVec, endVec);
                    }

                    if (headshotHitPos.isPresent()
                            && (hitPos == null || headshotHitPos.get().distanceTo(hitPos) < 0.5)) {
                        hitPos = headshotHitPos.get();
                        headshot.set(true);
                    }
                }
            }
        }
    }
}