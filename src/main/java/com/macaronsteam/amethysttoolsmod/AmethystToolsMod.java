package com.macaronsteam.amethysttoolsmod;

import com.macaronsteam.amethysttoolsmod.config.AmethystToolsModConfig;
import com.macaronsteam.amethysttoolsmod.event.ModEvents;
import com.macaronsteam.amethysttoolsmod.init.EntitiesInit;
import com.macaronsteam.amethysttoolsmod.init.ItemsInit;
import com.macaronsteam.amethysttoolsmod.init.RecipesInit;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraft.world.item.CreativeModeTabs;
import org.slf4j.Logger;

@Mod(AmethystToolsMod.MODID)
public class AmethystToolsMod {
  public static final String MODID = "amethysttoolsmod";
  public static final Logger LOGGER = LogUtils.getLogger();

  public AmethystToolsMod(IEventBus modEventBus, ModContainer modContainer) {
    modContainer.registerConfig(ModConfig.Type.STARTUP, AmethystToolsModConfig.SPEC);
    ItemsInit.register();
    EntitiesInit.ENTITIES.register(modEventBus);
    ItemsInit.ITEMS.register(modEventBus);
    RecipesInit.RECIPE_SERIALIZERS.register(modEventBus);
    modEventBus.addListener(this::commonSetup);
    modEventBus.addListener(this::addCreative);
    NeoForge.EVENT_BUS.register(ModEvents.class);
  }

  private void commonSetup(FMLCommonSetupEvent event) {
    event.enqueueWork(ItemsInit::registerBehavior);
    event.enqueueWork(ItemsInit::initEquipmentItems);
  }

  private void addCreative(BuildCreativeModeTabContentsEvent event) {
    if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
      ItemsInit.acceptIfBound(event, ItemsInit.ITEM_AMETHYST_DUST, ItemsInit.ITEM_AMETHYST_CLUSTER, ItemsInit.ITEM_AMETHYST_CLUSTER_LV2);
    } else if (event.getTabKey() == CreativeModeTabs.COMBAT) {
      ItemsInit.acceptIfBound(event, ItemsInit.ITEM_AMETHYST_ARROW, ItemsInit.ITEM_AMETHYST_TIPPED_ARROW,
          ItemsInit.ITEM_AMETHYST_SPECTRAL_ARROW, ItemsInit.ITEM_AMETHYST_TRIDENT);
      ItemsInit.acceptEquipment(event, name -> name.endsWith("_sword") || name.endsWith("_helmet") || name.endsWith("_chestplate")
          || name.endsWith("_leggings") || name.endsWith("_boots"));
    } else if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
      ItemsInit.acceptEquipment(event, name -> name.endsWith("_pickaxe") || name.endsWith("_axe") || name.endsWith("_shovel") || name.endsWith("_hoe"));
    }
  }
}
