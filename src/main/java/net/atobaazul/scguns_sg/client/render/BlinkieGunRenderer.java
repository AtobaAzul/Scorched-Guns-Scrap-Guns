package net.atobaazul.scguns_sg.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.cache.object.GeoBone;
import top.ribs.scguns.client.render.gun.animated.AnimatedGunRenderer;
import top.ribs.scguns.common.ChargeHandler;
import top.ribs.scguns.item.animated.AnimatedGunItem;

public class BlinkieGunRenderer extends AnimatedGunRenderer {
    private ItemStack currentRenderStack;

    public BlinkieGunRenderer(ResourceLocation path) {
        super(path);
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext transformType, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        super.renderByItem(stack, transformType, poseStack, bufferSource, packedLight, packedOverlay);
        this.currentRenderStack = stack;
    }

    @Override
    public void renderRecursively(PoseStack poseStack, AnimatedGunItem animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Minecraft client = Minecraft.getInstance();
        if (bone.getName().matches("coil") && this.currentRenderStack != null) {
            float chargeProgress = ChargeHandler.getChargeProgress(client.player, this.currentRenderStack);

            int blockLight = LightTexture.block(packedLight);
            int skyLight = LightTexture.sky(packedLight);

            int light = (int) Math.floor(Mth.lerp(chargeProgress, 5, 15));
            packedLight = LightTexture.pack(Mth.clamp(light + blockLight, 0, 15), Mth.clamp(light + skyLight, 0, 15));
        }

        super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
