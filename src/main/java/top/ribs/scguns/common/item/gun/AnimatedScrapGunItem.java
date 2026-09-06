package top.ribs.scguns.common.item.gun;

import net.atobaazul.scguns_sg.ScrapGuns;
import net.atobaazul.scguns_sg.client.render.BlinkieGunRenderer;
import net.atobaazul.scguns_sg.client.render.RokkitGunRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import top.ribs.scguns.client.render.gun.animated.AnimatedGunRenderer;
import top.ribs.scguns.entity.projectile.RocketEntity;
import top.ribs.scguns.item.animated.AnimatedGunItem;

import java.util.function.Consumer;

public class AnimatedScrapGunItem extends AnimatedGunItem {
    private final String gunID;
    private Boolean isVolley;
    private Integer volleyAmount;

    public AnimatedScrapGunItem(Properties properties, String path, SoundEvent reloadSoundMagOut, SoundEvent reloadSoundMagIn, SoundEvent reloadSoundEnd, SoundEvent boltPullSound, SoundEvent boltReleaseSound) {
        super(properties, path, reloadSoundMagOut, reloadSoundMagIn, reloadSoundEnd, boltPullSound, boltReleaseSound);
        this.isVolley = false;
        this.volleyAmount = 0;
        this.gunID = path;
    }

    public AnimatedScrapGunItem(Properties properties, String path, Boolean isVolley, Integer volleyAmount, SoundEvent reloadSoundMagOut, SoundEvent reloadSoundMagIn, SoundEvent reloadSoundEnd, SoundEvent boltPullSound, SoundEvent boltReleaseSound) {
        super(properties, path, reloadSoundMagOut, reloadSoundMagIn, reloadSoundEnd, boltPullSound, boltReleaseSound);
        this.isVolley = isVolley;
        this.volleyAmount = volleyAmount;
        this.gunID = path;
    }

    public boolean isVolley() {
        return this.isVolley;
    }

    public void setVolley(boolean volley) {
        this.isVolley = volley;
    }

    public int getVolleyAmount() {
        return this.volleyAmount;
    }

    public void setVolleyAmount(int amount) {
        this.volleyAmount = amount;
    }

    @OnlyIn(Dist.CLIENT)
    private AnimatedGunRenderer getRenderer() {
        ScrapGuns.LOGGER.info("GET RENDERER FOR: {}", this.gunID);
        if (gunID.matches("rokkit_launcha")) {
            ScrapGuns.LOGGER.info("rokkit");

            return new RokkitGunRenderer(new ResourceLocation("scguns", this.gunID));
        } else if (gunID.matches("blinkie")) {
            ScrapGuns.LOGGER.info("blinkie");

            return new BlinkieGunRenderer(new ResourceLocation("scguns", this.gunID));
        }
        return new AnimatedGunRenderer(new ResourceLocation("scguns", this.gunID));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {

        consumer.accept(new IClientItemExtensions() {
            private AnimatedGunRenderer renderer;

            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null) {
                    this.renderer = getRenderer();
                }
                return this.renderer;
            }
        });
    }
}
