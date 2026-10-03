# Amethyst Tools Mod

**Version:** 2.0.0-1.21.1-NeoForge  
**Loader:** NeoForge  
**Minecraft:** 1.21.1  
**License:** Apache License, Version 2.0

---

## Overview

Amethyst Tools Mod is a Minecraft mod that adds amethyst-infused upgrades for existing iron, diamond, and netherite equipment, along with new ammunition types and a throwable trident. All content is integrated into the vanilla progression through smithing and crafting.

This project is a port of the original *Amethyst Tools Mod* by Kitoglav (© 2022, Apache License 2.0) to Minecraft 1.21.1 on the NeoForge loader, carried out by **Accidey**. The original design and implementation are entirely credited to its author, Kitoglav.

## Features

- **Amethyst equipment** — Upgraded variants of iron, diamond, and netherite swords, pickaxes, axes, shovels, hoes, helmets, chestplates, leggings, and boots.
- **New materials** — Amethyst Dust, Amethyst Cluster, and Amethyst Cluster (Level 2), used across crafting and smithing recipes.
- **New ammunition** — Amethyst Arrow, Amethyst Tipped Arrow, and Amethyst Spectral Arrow, each with distinct combat properties.
- **Amethyst Trident** — A throwable trident upgrade with significantly increased damage.
- **Comprehensive configuration** — All features can be individually toggled or tuned through the configuration file.

## Amethyst Equipment

Amethyst equipment is obtained by upgrading existing iron, diamond, or netherite items at a smithing table, using a Netherite Upgrade Smithing Template, the base item, and an Amethyst Cluster.

Compared with their base counterparts, amethyst equipment provides:

- Increased durability (default multiplier: ×1.2)
- Increased mining speed (default bonus: +1.0)
- Increased attack damage (default bonus: +2.0)
- Increased enchantability (default bonus: +5)
- Improved protection (default bonus: +1 armor point; +1.0 armor toughness where applicable)

Netherite-based amethyst items retain their inherent fire resistance. Amethyst equipment is listed in the Ingredients, Combat, and Tools & Utilities creative tabs.

## Amethyst Arrows

- **Amethyst Arrow** — Deals additional damage (default: +3.0). Crafted from Amethyst Dust and vanilla arrows.
- **Amethyst Tipped Arrow** — Combines the damage of an amethyst arrow with the effect of a lingering potion; obtained through a dedicated 3×3 crafting recipe.
- **Amethyst Spectral Arrow** — Applies the Glowing effect to the target for a configurable duration (default: 300 ticks, i.e., 15 seconds).

All amethyst arrows support dispenser behavior and are compatible with the Infinity enchantment.

## Amethyst Trident

The Amethyst Trident is a throwable weapon created by upgrading a vanilla trident at a smithing table with an Amethyst Cluster (Level 2). It deals notably increased damage (default base damage: 8.0 + 3.0 = 11.0) and supports all standard trident enchantments.

## Configuration

The mod exposes an extensive set of configuration options:

| Option | Default | Description |
| --- | --- | --- |
| `enableIron` / `enableDiamond` / `enableNetherite` | `true` | Whether amethyst items for the respective material are created (startup-only) |
| `durabilityMultiplier` | `1.2` | Multiplies the durability of amethyst items (startup-only) |
| `extraDigSpeed` | `1.0` | Bonus mining speed (startup-only) |
| `extraAttackDamage` | `2.0` | Bonus attack damage for weapons and the trident (hot-reloadable) |
| `extraEnchantability` | `5` | Bonus enchantability (hot-reloadable) |
| `extraArmor` | `1` | Bonus armor points (hot-reloadable) |
| `extraToughness` | `1.0` | Bonus armor toughness, only for materials that already possess toughness (hot-reloadable) |
| `extraKR` | `0.0` | Bonus knockback resistance; `0.1` equals +1 knockback resistance attribute (hot-reloadable) |
| `enableAmethystArrows` | `true` | Whether amethyst arrows are created (startup-only) |
| `enableExtraArrows` | `true` | Whether the tipped and spectral arrow variants are created (startup-only) |
| `arrowExtraDamage` | `3.0` | Bonus damage for amethyst arrows and the amethyst trident (hot-reloadable) |
| `glowingDuration` | `300` | Duration of the Glowing effect from spectral arrows, in ticks (hot-reloadable) |
| `enableAmethystTrident` | `true` | Whether the amethyst trident is created (startup-only) |

## Requirements

- Minecraft **1.21.1**
- NeoForge **21.1.249** or later

## Credits

This mod is a port of the original *Amethyst Tools Mod* by **Kitoglav**, released under the Apache License, Version 2.0. We extend our sincere gratitude to the original author for creating this mod and for making it available under an open-source license.

- **Original author:** Kitoglav
- **Textures (original):** Muffch
- **Original team:** Macarons Team, 2022
- **Original project:** [Amethyst Tools Mod on CurseForge](https://www.curseforge.com/minecraft/mc-mods/amethyst-tools-mod)
- **Original source:** [GitHub – Kitoglav/Amethyst-Tools-Mod](https://github.com/Kitoglav/Amethyst-Tools-Mod)
- **Contact (original):** Discord – Kitoglav#0262

The Minecraft 1.21.1 / NeoForge port was carried out by **Accidey**.

Original work Copyright © 2022 Kitoglav

## License

This project is a derivative work of the original *Amethyst Tools Mod* and is licensed under the [Apache License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0). Modified source files are marked as such, and all copyright, patent, trademark, and attribution notices from the original work are retained. The original author does not endorse or otherwise take responsibility for this port.

---

# 紫水晶工具模组

**版本：** 2.0.0-1.21.1-NeoForge  
**加载器：** NeoForge  
**游戏版本：** Minecraft 1.21.1  
**许可证：** Apache License 2.0

---

## 模组简介

《紫水晶工具模组》为 Minecraft 添加了铁质、钻石与下界合金装备的紫水晶强化变体，并新增了多种箭矢与一件可投掷的三叉戟。模组的全部内容均通过锻造与合成融入原版游戏进程。

本项目为 Kitoglav 所著《Amethyst Tools Mod》（© 2022，Apache License 2.0）移植至 Minecraft 1.21.1 / NeoForge 加载器的版本。原始模组的设计与实现完全归功于其作者 Kitoglav。

## 主要特性

- **紫水晶装备**：铁、钻石、下界合金材质的剑、镐、斧、锹、锄，以及头盔、胸甲、护腿与靴子的强化变体。
- **全新材料**：紫水晶粉、紫水晶簇与二级紫水晶簇，用于各类合成与锻造配方。
- **全新箭矢**：紫水晶箭、紫水晶药水箭与紫水晶光灵箭，各自具有不同的战斗特性。
- **紫水晶三叉戟**：伤害显著提升的可投掷三叉戟。
- **全面的配置**：所有特性均可通过配置文件单独启用、禁用或调整。

## 紫水晶装备

紫水晶装备通过在锻造台上，以「下界合金升级锻造模板」+ 基础物品 +「紫水晶簇」对现有的铁、钻石或下界合金物品进行升级而获得。

与基础物品相比，紫水晶装备具有：

- 更高的耐久（默认倍率：×1.2）
- 更快的挖掘速度（默认加成：+1.0）
- 更高的攻击伤害（默认加成：+2.0）
- 更高的附魔能力（默认加成：+5）
- 更强的防护（默认护甲值 +1；对适用材质额外提供 +1.0 盔甲韧性）

下界合金材质的紫水晶物品保留其固有的抗火特性。紫水晶装备分别收录于「材料」「战斗」与「工具与实用物品」创造模式物品栏标签页。

## 紫水晶箭矢

- **紫水晶箭**：造成额外伤害（默认：+3.0）。由紫水晶粉与原版箭合成。
- **紫水晶药水箭**：兼具紫水晶箭的伤害与滞留药水的效果，通过专属的 3×3 合成配方获得。
- **紫水晶光灵箭**：使目标获得发光效果，持续时间可配置（默认：300 tick，即 15 秒）。

所有紫水晶箭矢均支持发射器，并兼容「无限」附魔。

## 紫水晶三叉戟

紫水晶三叉戟是一种可投掷武器，通过在锻造台上以「二级紫水晶簇」升级原版三叉戟而成。其基础伤害显著提高（默认基础伤害：8.0 + 3.0 = 11.0），并支持全部原版三叉戟附魔。

## 配置

模组提供了丰富的配置选项：

| 选项 | 默认值 | 说明 |
| --- | --- | --- |
| `enableIron` / `enableDiamond` / `enableNetherite` | `true` | 是否为对应材质创建紫水晶物品（仅启动时读取） |
| `durabilityMultiplier` | `1.2` | 紫水晶物品的耐久倍率（仅启动时读取） |
| `extraDigSpeed` | `1.0` | 额外挖掘速度（仅启动时读取） |
| `extraAttackDamage` | `2.0` | 武器与三叉戟的额外攻击伤害（热重载生效） |
| `extraEnchantability` | `5` | 额外附魔能力（热重载生效） |
| `extraArmor` | `1` | 额外护甲值（热重载生效） |
| `extraToughness` | `1.0` | 额外盔甲韧性，仅对本身具备韧性的材质生效（热重载生效） |
| `extraKR` | `0.0` | 额外击退抗性；`0.1` 等于 +1 击退抗性属性（热重载生效） |
| `enableAmethystArrows` | `true` | 是否创建紫水晶箭（仅启动时读取） |
| `enableExtraArrows` | `true` | 是否创建药水箭与光灵箭变种（仅启动时读取） |
| `arrowExtraDamage` | `3.0` | 紫水晶箭与紫水晶三叉戟的额外伤害（热重载生效） |
| `glowingDuration` | `300` | 光灵箭发光效果的持续时间，单位为 tick（热重载生效） |
| `enableAmethystTrident` | `true` | 是否创建紫水晶三叉戟（仅启动时读取） |

## 运行环境

- Minecraft **1.21.1**
- NeoForge **21.1.249** 或更高版本

## 致谢

本模组为 **Kitoglav** 所著《Amethyst Tools Mod》（Apache License 2.0）的移植版本。我们衷心感谢原作者创作了这一模组，并通过开源许可使其可供他人移植与再分发。

- **原作者：** Kitoglav
- **原版材质：** Muffch
- **原开发团队：** Macarons 团队，2022
- **原项目：** [Amethyst Tools Mod（CurseForge）](https://www.curseforge.com/minecraft/mc-mods/amethyst-tools-mod)
- **原源代码：** [GitHub – Kitoglav/Amethyst-Tools-Mod](https://github.com/Kitoglav/Amethyst-Tools-Mod)
- **原作者联系方式：** Discord – Kitoglav#0262

Minecraft 1.21.1 / NeoForge 版本的移植工作由 **Accidey** 完成。

原作版权声明：Copyright © 2022 Kitoglav

## 许可证

本项目为《Amethyst Tools Mod》的衍生作品，依据 [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0) 授权。被修改的源文件均已标记为已修改，并保留原作品的全部版权、专利、商标及归属声明。原作者未对本移植版本表示认可，亦不对其承担责任。