package com.macaronsteam.amethysttoolsmod.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class AmethystToolsModConfig {
  public static final ModConfigSpec SPEC;

  public static final ModConfigSpec.BooleanValue enableIron;
  public static final ModConfigSpec.BooleanValue enableDiamond;
  public static final ModConfigSpec.BooleanValue enableNetherite;
  public static final ModConfigSpec.DoubleValue durabilityMultiplier;
  public static final ModConfigSpec.DoubleValue extraDigSpeed;
  public static final ModConfigSpec.DoubleValue extraAttackDamage;
  public static final ModConfigSpec.IntValue extraEnchantability;
  public static final ModConfigSpec.IntValue extraArmor;
  public static final ModConfigSpec.DoubleValue extraToughness;
  public static final ModConfigSpec.DoubleValue extraKR;
  public static final ModConfigSpec.BooleanValue enableAmethystArrows;
  public static final ModConfigSpec.BooleanValue enableExtraArrows;
  public static final ModConfigSpec.DoubleValue arrowExtraDamage;
  public static final ModConfigSpec.IntValue glowingDuration;
  public static final ModConfigSpec.BooleanValue enableAmethystTrident;

  static {
    ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

    enableIron = builder.comment("", "是否创建铁质紫水晶物品？仅在启动时读取。", "Should iron amethyst items be created? Only read at startup.")
        .define("enableIron", true);
    enableDiamond = builder.comment("", "是否创建钻石紫水晶物品？仅在启动时读取。", "Should diamond amethyst items be created? Only read at startup.")
        .define("enableDiamond", true);
    enableNetherite = builder.comment("", "是否创建下界合金紫水晶物品？仅在启动时读取。", "Should netherite amethyst items be created? Only read at startup.")
        .define("enableNetherite", true);
    durabilityMultiplier = builder.comment("", "紫水晶物品的耐久倍率，作用于耐久上限。仅在启动时读取。",
        "Multiplies the durability of amethyst items. Only read at startup.")
        .defineInRange("durabilityMultiplier", 1.2, 0.1, Double.MAX_VALUE);
    extraDigSpeed = builder.comment("", "紫水晶工具的额外挖掘速度。仅在启动时读取。", "Adds harvest speed to amethyst tools. Only read at startup.")
        .defineInRange("extraDigSpeed", 1.0, 0.0, Double.MAX_VALUE);
    extraAttackDamage = builder.comment("", "紫水晶武器与三叉戟的额外攻击伤害。热重载生效。", "Adds attack damage to amethyst weapons and the trident. Supports hot reload.")
        .defineInRange("extraAttackDamage", 2.0, 0.0, Double.MAX_VALUE);
    extraEnchantability = builder.comment("", "紫水晶物品的额外附魔能力。热重载生效。", "Adds enchantability to amethyst items. Supports hot reload.")
        .defineInRange("extraEnchantability", 5, 0, Integer.MAX_VALUE);
    extraArmor = builder.comment("", "紫水晶盔甲的额外护甲值。热重载生效。", "Adds armor points to amethyst armor. Supports hot reload.")
        .defineInRange("extraArmor", 1, 0, Integer.MAX_VALUE);
    extraToughness = builder.comment("", "紫水晶盔甲的额外盔甲韧性（仅对本身有韧性的材质生效）。热重载生效。",
        "Adds armor toughness to amethyst armor (only for materials that already have toughness). Supports hot reload.")
        .defineInRange("extraToughness", 1.0, 0.0, Double.MAX_VALUE);
    extraKR = builder.comment("", "紫水晶盔甲的额外击退抗性；0.1 = +1 点击退抗性。热重载生效。",
        "Adds knockback resistance to amethyst armor; 0.1 = +1 knockback resistance attribute. Supports hot reload.")
        .defineInRange("extraKR", 0.0, 0.0, Double.MAX_VALUE);

    enableAmethystArrows = builder.comment("", "是否创建紫水晶箭？仅在启动时读取。", "Should any amethyst arrows be created? Only read at startup.")
        .define("enableAmethystArrows", true);
    enableExtraArrows = builder.comment("", "是否创建药水箭与光灵箭变种？仅在启动时读取。", "Should extra amethyst arrows (tipped/spectral) be created? Only read at startup.")
        .define("enableExtraArrows", true);
    arrowExtraDamage = builder.comment("", "紫水晶箭与紫水晶三叉戟的额外伤害。热重载生效。", "Adds damage to amethyst arrows and the amethyst trident. Supports hot reload.")
        .defineInRange("arrowExtraDamage", 3.0, 0.0, Double.MAX_VALUE);
    glowingDuration = builder.comment("", "紫水晶光灵箭发光效果的持续时间（tick，20 tick = 1 秒）。热重载生效。",
        "Duration of the glowing effect from amethyst spectral arrows (ticks, 20 = 1 second). Supports hot reload.")
        .defineInRange("glowingDuration", 300, 0, Integer.MAX_VALUE);
    enableAmethystTrident = builder.comment("", "是否创建紫水晶三叉戟？仅在启动时读取。", "Should the amethyst trident be created? Only read at startup.")
        .define("enableAmethystTrident", true);

    SPEC = builder.build();
  }
}
