package net.atobaazul.scguns_sg.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import javax.annotation.ParametersAreNonnullByDefault;

import static net.atobaazul.scguns_sg.ScrapGuns.MOD_ID;


@JeiPlugin
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class JEISGPlugin implements IModPlugin {
    public static MutableComponent getTranslation(String key, Object... args) {
        return Component.translatable("scguns_sg." + key, args);
    }

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(MOD_ID, "jei_plugin");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        assert Minecraft.getInstance().level != null;
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();
        //registration.addIngredientInfo(new ItemStack(ModItems.GRAVEKEEPER_BLUEPRINT.get()), VanillaTypes.ITEM_STACK, getTranslation("jei.info.found_in_gravekeeper_structures"));
        //registration.addIngredientInfo(new ItemStack(ModItems.ANATHEMA.get()), VanillaTypes.ITEM_STACK, getTranslation("jei.info.found_in_gravekeeper_structures"));

    }
}
