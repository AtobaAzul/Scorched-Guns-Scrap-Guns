package net.atobaazul.scguns_sg.mixin;

import org.spongepowered.asm.mixin.Mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;

import net.atobaazul.scguns_sg.common.entity.PiglinJunker.PiglinJunkerEntity;
import net.atobaazul.scguns_sg.registries.ModEntities;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;
import top.ribs.scguns.config.RaidConfig;
import top.ribs.scguns.entity.raid.ActiveRaid;
import top.ribs.scguns.entity.raid.RaidManager;

@Mixin(value = RaidManager.class, remap = false, priority = 800)
public class RaidManagerMixin {
    @WrapMethod(method = "spawnBoss")
    private Mob atSpawnBoss(ActiveRaid raid, ServerLevel level, Vec3 spawnPos, Operation<Mob> original) {
        if (raid.getConfig().raidId().contentEquals("piglin")) {
            if(level.getRandom().nextFloat() <= 0.15f){
                RaidConfig.BossData bossData = raid.getConfig().boss();
                PiglinJunkerEntity boss = ModEntities.PIGLIN_JUNKER.get().create(level);
                boss.setPos(spawnPos.x, spawnPos.y, spawnPos.z);
                if (bossData.customName() != null) {
                    Component nameComponent;
                    if (bossData.customName().startsWith("translation:")) {
                        String translationKey = bossData.customName().substring(12);
                        nameComponent = Component.translatable(translationKey);
                    } else {
                        nameComponent = Component.literal(bossData.customName());
                    }
    
                    boss.setCustomName(nameComponent);
                    boss.setCustomNameVisible(true);
                }
    
                boss.addTag("RaidBoss");
                boss.addTag("RaidMember_" + raid.getRaidId());
                boss.addTag("MobGunner");
                boss.addTag("AI_" + bossData.aiType().name());
                boss.setPersistenceRequired();
    
                level.addFreshEntity(boss);

                return boss;
            }
        }
        return original.call(raid, level, spawnPos);
    }
}
