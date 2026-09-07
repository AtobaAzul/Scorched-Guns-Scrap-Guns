package net.atobaazul.scguns_sg.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.atobaazul.scguns_sg.ScrapGuns;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.cache.object.GeoBone;
import top.ribs.scguns.client.render.gun.animated.AnimatedGunRenderer;
import top.ribs.scguns.common.Gun;
import top.ribs.scguns.item.animated.AnimatedGunItem;

import java.util.HashMap;
import java.util.Map;

public class RokkitGunRenderer extends AnimatedGunRenderer {
    private ItemStack currentRenderStack;

    public RokkitGunRenderer(ResourceLocation path) {
        super(path);
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext transformType, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        super.renderByItem(stack, transformType, poseStack, bufferSource, packedLight, packedOverlay);
        this.currentRenderStack = stack;
    }

    private void handleRocketBoneVisibility(GeoBone bone) {
        if (this.currentRenderStack != null && bone.getName().matches("rocket")) {
            float currentAmmo = Gun.getAmmoCount(this.currentRenderStack);
            float scale = currentAmmo <= 0 ? 0f : 1f;

            //We can't hide bones due to scguns' bone visibility caching, so we scale it down to 0 instead.
            bone.updateScale(scale, scale, scale);
        }
    }



    @Override
    public void renderRecursively(PoseStack poseStack, AnimatedGunItem animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        handleRocketBoneVisibility(bone);
    }
}
