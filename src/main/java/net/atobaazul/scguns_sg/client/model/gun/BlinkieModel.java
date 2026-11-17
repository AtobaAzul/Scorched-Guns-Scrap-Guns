package net.atobaazul.scguns_sg.client.model.gun;

import com.mojang.blaze3d.vertex.PoseStack;
import net.atobaazul.scguns_sg.client.model.SGSpecialModels;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import top.ribs.scguns.client.render.gun.IOverrideModel;
import top.ribs.scguns.client.util.RenderUtil;
import top.ribs.scguns.common.Gun;
import top.ribs.scguns.init.ModItems;
import top.ribs.scguns.item.attachment.IAttachment;

public class BlinkieModel implements IOverrideModel {
    @SuppressWarnings("resource")
    @Override
    public void render(float partialTicks, ItemDisplayContext transformType, ItemStack stack, ItemStack parent, LivingEntity entity, PoseStack matrixStack, MultiBufferSource buffer, int light, int overlay) {
        // Renders the static parts of the model.
        RenderUtil.renderModel(SGSpecialModels.BLINKIE_MAIN.getModel(), stack, matrixStack, buffer, light, overlay);

        int blockLight = LightTexture.block(light);
        int skyLight = LightTexture.sky(light);
        int packedLight = LightTexture.pack(Mth.clamp(5 + blockLight, 0, 15), Mth.clamp(5 + skyLight, 0, 15));

        RenderUtil.renderModel(SGSpecialModels.BLINKIE_COIL.getModel(), stack, matrixStack, buffer, packedLight, overlay);


        // Render under-barrel attachments
        renderUnderBarrelAttachments(stack, matrixStack, buffer, light, overlay);

    }



    private void renderUnderBarrelAttachments(ItemStack stack, PoseStack matrixStack, MultiBufferSource buffer, int light, int overlay) {
        if (Gun.hasAttachmentEquipped(stack, IAttachment.Type.UNDER_BARREL)) {
            if (Gun.getAttachment(IAttachment.Type.UNDER_BARREL, stack).getItem() == ModItems.VERTICAL_GRIP.get())
                RenderUtil.renderModel(SGSpecialModels.BLINKIE_TACTICAL_GRIP.getModel(), stack, matrixStack, buffer, light, overlay);
            else if (Gun.getAttachment(IAttachment.Type.UNDER_BARREL, stack).getItem() == ModItems.LIGHT_GRIP.get())
                RenderUtil.renderModel(SGSpecialModels.BLINKIE_LIGHT_GRIP.getModel(), stack, matrixStack, buffer, light, overlay);
            else if (Gun.getAttachment(IAttachment.Type.UNDER_BARREL, stack).getItem() == ModItems.IRON_BAYONET.get())
                RenderUtil.renderModel(SGSpecialModels.BLINKIE_IRON_BAYONET.getModel(), stack, matrixStack, buffer, light, overlay);
            else if (Gun.getAttachment(IAttachment.Type.UNDER_BARREL, stack).getItem() == ModItems.ANTHRALITE_BAYONET.get())
                RenderUtil.renderModel(SGSpecialModels.BLINKIE_ANTHRALITE_BAYONET.getModel(), stack, matrixStack, buffer, light, overlay);
            else if (Gun.getAttachment(IAttachment.Type.UNDER_BARREL, stack).getItem() == ModItems.DIAMOND_BAYONET.get())
                RenderUtil.renderModel(SGSpecialModels.BLINKIE_ANTHRALITE_BAYONET.getModel(), stack, matrixStack, buffer, light, overlay);
            else if (Gun.getAttachment(IAttachment.Type.UNDER_BARREL, stack).getItem() == ModItems.NETHERITE_BAYONET.get())
                RenderUtil.renderModel(SGSpecialModels.BLINKIE_NETHERITE_BAYONET.getModel(), stack, matrixStack, buffer, light, overlay);
        }
    }
}
