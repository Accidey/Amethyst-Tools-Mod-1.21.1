package com.macaronsteam.amethysttoolsmod.event;

import com.macaronsteam.amethysttoolsmod.AmethystToolsMod;
import com.macaronsteam.amethysttoolsmod.config.AmethystToolsModConfig;
import com.macaronsteam.amethysttoolsmod.init.ItemsInit;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;

@EventBusSubscriber(modid = AmethystToolsMod.MODID)
public class ModEvents {

  @SubscribeEvent
  public static void onItemAttributeModifiers(ItemAttributeModifierEvent event) {
    Item item = event.getItemStack().getItem();
    if (!ItemsInit.isAmethystEquipment(item)) {
      return;
    }
    if (item instanceof ArmorItem armor) {
      ArmorItem.Type type = armor.getType();
      ResourceLocation id = ResourceLocation.withDefaultNamespace("armor." + type.getName());
      EquipmentSlotGroup group = EquipmentSlotGroup.bySlot(type.getSlot());
      event.replaceModifier(Attributes.ARMOR,
          new AttributeModifier(id, armor.getDefense() + AmethystToolsModConfig.extraArmor.get(), AttributeModifier.Operation.ADD_VALUE), group);
      float toughness = armor.getToughness();
      if (toughness > 0.0F) {
        event.replaceModifier(Attributes.ARMOR_TOUGHNESS,
            new AttributeModifier(id, toughness + AmethystToolsModConfig.extraToughness.get().floatValue(), AttributeModifier.Operation.ADD_VALUE), group);
      }
      float knockbackResistance = armor.getMaterial().value().knockbackResistance() + AmethystToolsModConfig.extraKR.get().floatValue();
      if (knockbackResistance > 0.0F) {
        event.replaceModifier(Attributes.KNOCKBACK_RESISTANCE,
            new AttributeModifier(id, knockbackResistance, AttributeModifier.Operation.ADD_VALUE), group);
      }
    } else {
      for (ItemAttributeModifiers.Entry entry : event.getDefaultModifiers().modifiers()) {
        if (entry.attribute().is(Attributes.ATTACK_DAMAGE) && entry.slot() == EquipmentSlotGroup.MAINHAND) {
          event.replaceModifier(Attributes.ATTACK_DAMAGE,
              new AttributeModifier(entry.modifier().id(), entry.modifier().amount() + AmethystToolsModConfig.extraAttackDamage.get(),
                  AttributeModifier.Operation.ADD_VALUE),
              EquipmentSlotGroup.MAINHAND);
          break;
        }
      }
    }
  }
}
