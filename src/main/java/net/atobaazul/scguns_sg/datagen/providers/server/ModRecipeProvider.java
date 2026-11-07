package net.atobaazul.scguns_sg.datagen.providers.server;

import net.atobaazul.scguns_sg.registries.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Consumer;

@ParametersAreNonnullByDefault
public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> writer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.SCRAP_BLUEPRINT.get())
                .pattern("BPB")
                .pattern("PCP")
                .pattern("BPB")
                .define('P', Items.PAPER)
                .define('C', ModItems.SCRAP_CORE.get())
                .define('B', top.ribs.scguns.init.ModItems.BLUEPRINT_SCRAP.get())
                .unlockedBy("has_ingredient", has(ModItems.SCRAP_CORE.get()))
                .showNotification(false)
                .save(writer);
    }


}
