package com.macaronsteam.amethysttoolsmod.recipe;

import com.macaronsteam.amethysttoolsmod.init.ItemsInit;
import com.macaronsteam.amethysttoolsmod.init.RecipesInit;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class TippedAmethystArrowRecipe extends CustomRecipe {

  public TippedAmethystArrowRecipe(CraftingBookCategory category) {
    super(category);
  }

  @Override
  public boolean matches(CraftingInput input, Level level) {
    if (input.width() == 3 && input.height() == 3) {
      for (int i = 0; i < input.height(); i++) {
        for (int j = 0; j < input.width(); j++) {
          ItemStack itemstack = input.getItem(j, i);
          if (itemstack.isEmpty()) {
            return false;
          }
          if (j == 1 && i == 1) {
            if (!itemstack.is(Items.LINGERING_POTION)) {
              return false;
            }
          } else if (!itemstack.is(ItemsInit.ITEM_AMETHYST_ARROW.get())) {
            return false;
          }
        }
      }
      return true;
    }
    return false;
  }

  @Override
  public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
    ItemStack itemstack = input.getItem(1, 1);
    if (!itemstack.is(Items.LINGERING_POTION)) {
      return ItemStack.EMPTY;
    }
    ItemStack itemstack1 = new ItemStack(ItemsInit.ITEM_AMETHYST_TIPPED_ARROW.get(), 8);
    itemstack1.set(DataComponents.POTION_CONTENTS, itemstack.get(DataComponents.POTION_CONTENTS));
    return itemstack1;
  }

  @Override
  public boolean canCraftInDimensions(int width, int height) {
    return width >= 3 && height >= 3;
  }

  @Override
  public RecipeSerializer<?> getSerializer() {
    return RecipesInit.RECIPE_AMETHYST_TIPPED_ARROW.get();
  }
}
