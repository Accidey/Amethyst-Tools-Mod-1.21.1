package com.macaronsteam.amethysttoolsmod.item;

import com.macaronsteam.amethysttoolsmod.config.AmethystToolsModConfig;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

public class AmethystArmorItem extends ArmorItem {

  public AmethystArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
    super(material, type, properties);
  }

  @Override
  public int getEnchantmentValue() {
    return super.getEnchantmentValue() + AmethystToolsModConfig.extraEnchantability.get();
  }
}
