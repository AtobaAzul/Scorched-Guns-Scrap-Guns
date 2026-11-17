package net.atobaazul.scguns_sg.client.model.gun;

import com.mojang.blaze3d.vertex.PoseStack;
import net.atobaazul.scguns_sg.client.model.SGSpecialModels;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import top.ribs.scguns.client.render.gun.IOverrideModel;
import top.ribs.scguns.client.util.RenderUtil;

public class PunchaModel implements IOverrideModel {
    @SuppressWarnings("resource")
    @Override
    public void render(float partialTicks, ItemDisplayContext transformType, ItemStack stack, ItemStack parent, LivingEntity entity, PoseStack matrixStack, MultiBufferSource buffer, int light, int overlay) {
        // Renders the static parts of the model.
        RenderUtil.renderModel(SGSpecialModels.PUNCHA_MAIN.getModel(), stack, matrixStack, buffer, light, overlay);
    }
}
