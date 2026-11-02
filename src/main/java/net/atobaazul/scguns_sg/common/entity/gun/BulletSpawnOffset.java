package net.atobaazul.scguns_sg.common.entity.gun;

import net.minecraft.world.phys.Vec3;

/**
 * @author: Krei(github: rekales)
 */
public interface BulletSpawnOffset {

    Vec3 getBulletSpawnOffset(int gunIndex);

    default Vec3 getBulletSpawnOffset() {
        return this.getBulletSpawnOffset(0);
    }
}
