package com.macaronsteam.amethysttoolsmod.init;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

import com.macaronsteam.amethysttoolsmod.AmethystToolsMod;
import com.macaronsteam.amethysttoolsmod.config.AmethystToolsModConfig;
import com.macaronsteam.amethysttoolsmod.item.AmethystArrowItem;
import com.macaronsteam.amethysttoolsmod.item.AmethystTridentItem;
import com.macaronsteam.amethysttoolsmod.item.MaterialItem;
import com.google.common.primitives.Booleans;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.block.DispenserBlock;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemsInit {
  public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AmethystToolsMod.MODID);

  public static DeferredItem<Item> ITEM_AMETHYST_DUST;
  public static DeferredItem<Item> ITEM_AMETHYST_CLUSTER;
  public static DeferredItem<Item> ITEM_AMETHYST_CLUSTER_LV2;
  public static DeferredItem<Item> ITEM_AMETHYST_ARROW;
  public static DeferredItem<Item> ITEM_AMETHYST_TIPPED_ARROW;
  public static DeferredItem<Item> ITEM_AMETHYST_SPECTRAL_ARROW;
  public static DeferredItem<Item> ITEM_AMETHYST_TRIDENT;
  public static final List<DeferredItem<Item>> AMETHYST_EQUIPMENT = new ArrayList<>();

  private static volatile Set<Item> equipmentItems = Collections.emptySet();
  private static final Map<String, Holder<ArmorMaterial>> ARMOR_MATERIALS = new IdentityHashMap<>();
  private static final Map<String, Tier> TOOL_TIERS = new IdentityHashMap<>();

  public static void register() {
    registerAmethystEquipment();
    ITEM_AMETHYST_DUST = ITEMS.register("amethyst_dust", () -> new MaterialItem(new Item.Properties()));
    ITEM_AMETHYST_CLUSTER = ITEMS.register("amethyst_cluster", () -> new MaterialItem(new Item.Properties()));
    ITEM_AMETHYST_CLUSTER_LV2 = ITEMS.register("amethyst_cluster_lv2", () -> new MaterialItem(new Item.Properties().rarity(Rarity.UNCOMMON)));
    ITEM_AMETHYST_ARROW = register("amethyst_arrow", p -> new AmethystArrowItem(p), AmethystToolsModConfig.enableAmethystArrows.get());
    ITEM_AMETHYST_TIPPED_ARROW = register("amethyst_tipped_arrow", p -> new AmethystArrowItem.TippedItem(p),
        AmethystToolsModConfig.enableAmethystArrows.get(), AmethystToolsModConfig.enableExtraArrows.get());
    ITEM_AMETHYST_SPECTRAL_ARROW = register("amethyst_spectral_arrow", p -> new AmethystArrowItem.SpectralItem(p),
        AmethystToolsModConfig.enableAmethystArrows.get(), AmethystToolsModConfig.enableExtraArrows.get());
    ITEM_AMETHYST_TRIDENT = register("amethyst_trident", p -> new AmethystTridentItem(p), AmethystToolsModConfig.enableAmethystTrident.get());
    if (ITEM_AMETHYST_TRIDENT.isBound())
      AMETHYST_EQUIPMENT.add(ITEM_AMETHYST_TRIDENT);
  }

  private static DeferredItem<Item> register(String name, ItemFactory factory, boolean... condition) {
    if (!Booleans.contains(condition, false))
      return ITEMS.register(name, () -> factory.create(new Item.Properties()));
    return DeferredItem.createItem(ResourceLocation.fromNamespaceAndPath(AmethystToolsMod.MODID, name));
  }

  private interface ItemFactory {
    Item create(Item.Properties properties);
  }

  private static boolean materialEnabled(String material) {
    return switch (material) {
      case "iron" -> AmethystToolsModConfig.enableIron.get();
      case "diamond" -> AmethystToolsModConfig.enableDiamond.get();
      case "netherite" -> AmethystToolsModConfig.enableNetherite.get();
      default -> false;
    };
  }

  public static void registerAmethystEquipment() {
    String[] types = {"sword", "pickaxe", "axe", "shovel", "hoe", "boots", "leggings", "chestplate", "helmet"};
    for (String material : new String[] {"iron", "diamond", "netherite"}) {
      if (!materialEnabled(material))
        continue;
      for (String type : types) {
        String baseName = material + "_" + type;
        Item base = BuiltInRegistries.ITEM.getOptional(ResourceLocation.withDefaultNamespace(baseName)).orElse(null);
        if (base == null) {
          AmethystToolsMod.LOGGER.warn("Base item {} not found, skipping amethyst variant", baseName);
          continue;
        }
        if (!canCreate(base))
          continue;
        AMETHYST_EQUIPMENT.add(ITEMS.register(baseName + "_amethyst", () -> Objects.requireNonNull(tryToCreate(base, material))));
      }
    }
  }

  private static boolean canCreate(Item input) {
    return input instanceof ArmorItem || input instanceof SwordItem || input instanceof PickaxeItem || input instanceof AxeItem
        || input instanceof ShovelItem || input instanceof HoeItem;
  }

  public static Item tryToCreate(Item input, String material) {
    Item.Properties properties = new Item.Properties();
    if (material.equals("netherite"))
      properties.fireResistant();
    if (input instanceof ArmorItem armor) {
      int baseMaxDamage = armor.getDefaultInstance().getMaxDamage();
      return new com.macaronsteam.amethysttoolsmod.item.AmethystArmorItem(
          buildArmorMaterial(armor.getMaterial(), material), armor.getType(), properties.durability((int) (baseMaxDamage * AmethystToolsModConfig.durabilityMultiplier.get())));
    }
    if (input instanceof TieredItem tiered) {
      Tier amethystTier = buildTier(tiered.getTier(), material);
      float baseDamage = baseAttackDamage(tiered);
      float baseSpeed = baseAttackSpeed(tiered);
      if (input instanceof SwordItem)
        return new SwordItem(amethystTier, properties.attributes(SwordItem.createAttributes(amethystTier, baseDamage, baseSpeed)));
      if (input instanceof PickaxeItem)
        return new PickaxeItem(amethystTier, properties.attributes(DiggerItem.createAttributes(amethystTier, baseDamage, baseSpeed)));
      if (input instanceof AxeItem)
        return new AxeItem(amethystTier, properties.attributes(DiggerItem.createAttributes(amethystTier, baseDamage, baseSpeed)));
      if (input instanceof ShovelItem)
        return new ShovelItem(amethystTier, properties.attributes(DiggerItem.createAttributes(amethystTier, baseDamage, baseSpeed)));
      if (input instanceof HoeItem)
        return new HoeItem(amethystTier, properties.attributes(DiggerItem.createAttributes(amethystTier, baseDamage, baseSpeed)));
    }
    return null;
  }

  private static float baseAttackDamage(TieredItem item) {
    for (ItemAttributeModifiers.Entry entry : item.getDefaultInstance().getAttributeModifiers().modifiers()) {
      if (entry.attribute().is(Attributes.ATTACK_DAMAGE) && entry.slot() == EquipmentSlotGroup.MAINHAND)
        return (float) (entry.modifier().amount() - item.getTier().getAttackDamageBonus());
    }
    return 1.0F;
  }

  private static float baseAttackSpeed(TieredItem item) {
    for (ItemAttributeModifiers.Entry entry : item.getDefaultInstance().getAttributeModifiers().modifiers()) {
      if (entry.attribute().is(Attributes.ATTACK_SPEED) && entry.slot() == EquipmentSlotGroup.MAINHAND)
        return (float) entry.modifier().amount();
    }
    return -2.4F;
  }

  private static Holder<ArmorMaterial> buildArmorMaterial(Holder<ArmorMaterial> base, String material) {
    return ARMOR_MATERIALS.computeIfAbsent(material, key -> {
      ArmorMaterial m = base.value();
      Map<ArmorItem.Type, Integer> defense = new EnumMap<>(ArmorItem.Type.class);
      for (ArmorItem.Type type : ArmorItem.Type.values()) {
        int value = m.getDefense(type);
        if (value > 0)
          defense.put(type, value);
      }
      ResourceLocation asset = ResourceLocation.fromNamespaceAndPath(AmethystToolsMod.MODID, key + "_amethyst");
      ArmorMaterial amethyst = new ArmorMaterial(
          Map.copyOf(defense),
          m.enchantmentValue(),
          m.equipSound(),
          m.repairIngredient(),
          List.of(new ArmorMaterial.Layer(asset)),
          m.toughness(),
          m.knockbackResistance());
      return Holder.direct(amethyst);
    });
  }

  private static Tier buildTier(Tier base, String material) {
    return TOOL_TIERS.computeIfAbsent(material, key -> new Tier() {
      @Override
      public int getUses() {
        return (int) (base.getUses() * AmethystToolsModConfig.durabilityMultiplier.get());
      }

      @Override
      public float getSpeed() {
        return base.getSpeed() + AmethystToolsModConfig.extraDigSpeed.get().floatValue();
      }

      @Override
      public float getAttackDamageBonus() {
        return base.getAttackDamageBonus();
      }

      @Override
      public net.minecraft.tags.TagKey<net.minecraft.world.level.block.Block> getIncorrectBlocksForDrops() {
        return base.getIncorrectBlocksForDrops();
      }

      @Override
      public int getEnchantmentValue() {
        return base.getEnchantmentValue() + AmethystToolsModConfig.extraEnchantability.get();
      }

      @Override
      public net.minecraft.world.item.crafting.Ingredient getRepairIngredient() {
        return base.getRepairIngredient();
      }
    });
  }

  public static void initEquipmentItems() {
    Set<Item> set = Collections.newSetFromMap(new IdentityHashMap<>());
    for (DeferredItem<Item> holder : AMETHYST_EQUIPMENT) {
      if (holder.isBound())
        set.add(holder.get());
    }
    equipmentItems = Set.copyOf(set);
  }

  public static boolean isAmethystEquipment(Item item) {
    return equipmentItems.contains(item);
  }

  public static void registerBehavior() {
    registerDispenseBehavior(ITEM_AMETHYST_ARROW);
    registerDispenseBehavior(ITEM_AMETHYST_TIPPED_ARROW);
    registerDispenseBehavior(ITEM_AMETHYST_SPECTRAL_ARROW);
  }

  private static void registerDispenseBehavior(DeferredItem<Item> item) {
    if (item.isBound())
      DispenserBlock.registerBehavior(item.get(), new net.minecraft.core.dispenser.ProjectileDispenseBehavior(item.get()));
  }

  public static void acceptIfBound(BuildCreativeModeTabContentsEvent event, DeferredItem<Item>... items) {
    for (DeferredItem<Item> item : items) {
      if (item.isBound())
        event.accept(item.get());
    }
  }

  public static void acceptEquipment(BuildCreativeModeTabContentsEvent event, Predicate<String> filter) {
    for (DeferredItem<Item> holder : AMETHYST_EQUIPMENT) {
      if (!holder.isBound())
        continue;
      String name = BuiltInRegistries.ITEM.getKey(holder.get()).getPath();
      if (filter.test(name))
        event.accept(holder.get());
    }
  }
}
