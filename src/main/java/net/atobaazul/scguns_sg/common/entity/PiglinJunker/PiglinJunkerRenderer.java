package net.atobaazul.scguns_sg.common.entity.PiglinJunker;

import java.util.Map;

import net.atobaazul.scguns_sg.client.render.BaseEntityRenderer;
import net.atobaazul.scguns_sg.client.render.BoneGunFlashGeoLayer;
import software.bernie.geckolib.model.GeoModel;

import net.minecraft.client.renderer.entity.EntityRendererProvider;


public class PiglinJunkerRenderer<T extends PiglinJunkerEntity> extends BaseEntityRenderer<T>{

    public PiglinJunkerRenderer(EntityRendererProvider.Context context, GeoModel<T> model) {
        super(context, model);
        this.addRenderLayer(new BoneGunFlashGeoLayer<>(this, Map.of(
                0, "flash"
        )));
    }

}