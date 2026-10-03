package com.macaronsteam.amethysttoolsmod.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public class MaterialItem extends Item {

  public MaterialItem(Properties properties) {
    super(properties);
  }

  @Override
  public boolean isFoil(ItemStack itemstack) {
    return itemstack.getRarity() == Rarity.UNCOMMON;
  }
}
