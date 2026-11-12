package top.ribs.scguns.common.item.scrapgun;

import net.atobaazul.scguns_sg.client.render.gun.BlinkieGunRenderer;
import net.atobaazul.scguns_sg.client.render.gun.RokkitGunRenderer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.registries.ForgeRegistries;
import top.ribs.scguns.Config;
import top.ribs.scguns.client.KeyBinds;
import top.ribs.scguns.client.render.gun.animated.AnimatedGunRenderer;
import top.ribs.scguns.common.Gun;
import top.ribs.scguns.common.ReloadType;
import top.ribs.scguns.init.ModTags;
import top.ribs.scguns.item.animated.AnimatedGunItem;
import top.ribs.scguns.util.GunEnchantmentHelper;
import top.ribs.scguns.util.GunModifierHelper;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

public class AnimatedScrapGunItem extends AnimatedGunItem {
    private final String gunID;
    private Boolean isVolley;
    private Integer volleyAmount;
    private float meleeKnockback = 0;

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
        if (gunID.matches("rokkit_launcha")) {
            return new RokkitGunRenderer(new ResourceLocation("scguns", this.gunID));
        } else if (gunID.matches("blinkie")) {
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

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flag) {
        Gun modifiedGun = this.getModifiedGun(stack);

        ResourceLocation advantage = modifiedGun.getProjectile().getAdvantage();
        float baseDamage = modifiedGun.getProjectile().getDamage();
        baseDamage = GunModifierHelper.getModifiedProjectileDamage(stack, baseDamage);
        baseDamage = GunEnchantmentHelper.getAcceleratorDamage(stack, baseDamage);
        baseDamage = GunEnchantmentHelper.getHeavyShotDamage(stack, baseDamage);

        baseDamage *= Config.COMMON.gameplay.globalDamageMultiplier.get().floatValue();
        String additionalDamageText = "";
        String volleyText = "";
        CompoundTag tagCompound = stack.getTag();
        if (tagCompound != null) {
            if (tagCompound.contains("AdditionalDamage", Tag.TAG_ANY_NUMERIC)) {
                float additionalDamage = tagCompound.getFloat("AdditionalDamage");
                additionalDamage += GunModifierHelper.getAdditionalDamage(stack, false);

                if (additionalDamage > 0) {
                    additionalDamageText = ChatFormatting.GREEN + " +" + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(additionalDamage);
                } else if (additionalDamage < 0) {
                    additionalDamageText = ChatFormatting.RED + " " + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(additionalDamage);
                }
            }

            if (this.volleyAmount > 0) {
                volleyText = ChatFormatting.AQUA + "×" + this.volleyAmount;
            }
        }

        tooltip.add(Component.translatable("info.scguns.damage").append(": ").withStyle(ChatFormatting.GRAY).append(Component.literal(ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(baseDamage) + additionalDamageText + volleyText).withStyle(ChatFormatting.WHITE)));
        float baseArmorPen = modifiedGun.getProjectile().getArmorPen();
        float puncturingPen = GunEnchantmentHelper.getPuncturingArmorBypass(stack);
        float totalArmorPen = baseArmorPen + puncturingPen;

        if (totalArmorPen > 0) {
            String armorPenText = ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(totalArmorPen);
            tooltip.add(Component.translatable("info.scguns.armor_penetration").append(": ").withStyle(ChatFormatting.GRAY).append(Component.literal(armorPenText).withStyle(ChatFormatting.YELLOW)));
        }

        if (!advantage.equals(ModTags.Entities.NONE.location())) {
            tooltip.add(Component.translatable("info.scguns.advantage").withStyle(ChatFormatting.GRAY).append(Component.translatable("advantage." + advantage).withStyle(ChatFormatting.GOLD)));
        }
        String fireMode = modifiedGun.getGeneral().getFireMode().id().toString();
        tooltip.add(Component.translatable("info.scguns.fire_mode").withStyle(ChatFormatting.GRAY).append(Component.translatable("fire_mode." + fireMode).withStyle(ChatFormatting.WHITE)));

        Item ammo = modifiedGun.getProjectile().getItem();
        Item reloadItem = modifiedGun.getReloads().getReloadItem();
        if (modifiedGun.getReloads().getReloadType() == ReloadType.SINGLE_ITEM) {
            ammo = reloadItem;
        }

        if (tagCompound != null) {
            if (tagCompound.getBoolean("IgnoreAmmo")) {
                tooltip.add(Component.translatable("info.scguns.ignore_ammo").withStyle(ChatFormatting.AQUA));
            } else {
                int ammoCount = tagCompound.getInt("AmmoCount");
                tooltip.add(Component.translatable("info.scguns.ammo").append(": ").withStyle(ChatFormatting.GRAY).append(Component.literal(ammoCount + "/" + GunModifierHelper.getModifiedAmmoCapacity(stack, modifiedGun)).withStyle(ChatFormatting.WHITE)));
            }
        }
        float totalMeleeDamage = getTotalMeleeDamage(stack);
        if (totalMeleeDamage > 0) {
            String meleeDamageText = (totalMeleeDamage % 1.0 == 0) ? String.format("%d", (int) totalMeleeDamage) : String.format("%.1f", totalMeleeDamage);
            tooltip.add(Component.translatable("info.scguns.melee_damage").append(": ").withStyle(ChatFormatting.GRAY).append(Component.literal(meleeDamageText).withStyle(ChatFormatting.WHITE)));
        }
        ResourceLocation effectLocation = modifiedGun.getProjectile().getImpactEffect();
        if (effectLocation != null) {
            MobEffect effect = ForgeRegistries.MOB_EFFECTS.getValue(effectLocation);
            if (effect != null) {
                tooltip.add(Component.translatable("info.scguns.impact_effect").withStyle(ChatFormatting.GRAY).append(": ").append(Component.translatable(effect.getDescriptionId()).withStyle(ChatFormatting.BLUE)));
            }
        }

        Gun.WeaponType weaponType = modifiedGun.getGeneral().getWeaponType();
        if (weaponType != null) {
            String weaponTypeKey = "desc.scguns." + weaponType.name().toLowerCase();
            tooltip.add(Component.translatable("info.scguns.weapon_type").withStyle(ChatFormatting.GRAY).append(": ").append(Component.translatable(weaponTypeKey).withStyle(ChatFormatting.AQUA)));
        }
        if (ammo != null) {
            tooltip.add(Component.translatable("info.scguns.ammo_type", Component.translatable(ammo.getDescriptionId()).withStyle(ChatFormatting.WHITE)).withStyle(ChatFormatting.GRAY));
        }

        if (modifiedGun.getGeneral().allowsAmmoChange() && !modifiedGun.getGeneral().getAvailableAmmoTypes().isEmpty()) {
            if (net.minecraft.client.gui.screens.Screen.hasShiftDown()) {
                tooltip.add(Component.translatable("info.scguns.available_ammo_types").withStyle(ChatFormatting.GRAY));

                int currentIndex = modifiedGun.getGeneral().getCurrentAmmoTypeIndex();
                List<String> ammoTypes = modifiedGun.getGeneral().getAvailableAmmoTypes();

                for (int i = 0; i < ammoTypes.size(); i++) {
                    String ammoType = ammoTypes.get(i);
                    ResourceLocation ammoLocation = new ResourceLocation(ammoType.contains(":") ? ammoType : top.ribs.scguns.Reference.MOD_ID + ":" + ammoType);
                    Item ammoItem = ForgeRegistries.ITEMS.getValue(ammoLocation);

                    if (ammoItem != null) {
                        Component ammoName = Component.translatable(ammoItem.getDescriptionId());
                        if (i == currentIndex) {
                            tooltip.add(Component.literal("  • ").withStyle(ChatFormatting.YELLOW).append(ammoName.copy().withStyle(ChatFormatting.YELLOW)).append(Component.literal(" ✓").withStyle(ChatFormatting.GREEN)));
                        } else {
                            tooltip.add(Component.literal("  • ").withStyle(ChatFormatting.GRAY).append(ammoName.copy().withStyle(ChatFormatting.WHITE)));
                        }
                    }
                }
            } else {
                tooltip.add(Component.translatable("info.scguns.ammo_swap_available").withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));
            }
        }
        tooltip.add(Component.translatable("info.scguns.attachment_help", KeyBinds.KEY_ATTACHMENTS.getTranslatedKeyMessage().getString().toUpperCase(Locale.ENGLISH)).withStyle(ChatFormatting.YELLOW));
    }

    public float getMeleeKnockback() {
        return this.meleeKnockback;
    }

    public AnimatedScrapGunItem setMeleeKnockback(int value) {
        this.meleeKnockback = value;
        return this;
    }
}
