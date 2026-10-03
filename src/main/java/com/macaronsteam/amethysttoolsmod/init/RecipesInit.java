package com.macaronsteam.amethysttoolsmod.init;

import com.macaronsteam.amethysttoolsmod.AmethystToolsMod;
import com.macaronsteam.amethysttoolsmod.config.AmethystToolsModConfig;
import com.macaronsteam.amethysttoolsmod.recipe.TippedAmethystArrowRecipe;
import com.google.common.primitives.Booleans;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RecipesInit {
  public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER,
      AmethystToolsMod.MODID);

  public static final DeferredHolder<RecipeSerializer<?>, SimpleCraftingRecipeSerializer<TippedAmethystArrowRecipe>> RECIPE_AMETHYST_TIPPED_ARROW = register(
      "crafting_special_amethysttippedarrow", () -> new SimpleCraftingRecipeSerializer<>(TippedAmethystArrowRecipe::new),
      AmethystToolsModConfig.enableAmethystArrows.get(), AmethystToolsModConfig.enableExtraArrows.get());

  private static <T extends RecipeSerializer<?>> DeferredHolder<RecipeSerializer<?>, T> register(String name,
      java.util.function.Supplier<T> supplier, boolean... condition) {
    if (!Booleans.contains(condition, false))
      return RECIPE_SERIALIZERS.register(name, supplier);
    return DeferredHolder.create(Registries.RECIPE_SERIALIZER, ResourceLocation.fromNamespaceAndPath(AmethystToolsMod.MODID, name));
  }
}
