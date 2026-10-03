package com.macaronsteam.amethysttoolsmod.client;

import com.macaronsteam.amethysttoolsmod.AmethystToolsMod;
import com.macaronsteam.amethysttoolsmod.client.renderer.AmethystArrowRenderer;
import com.macaronsteam.amethysttoolsmod.client.renderer.AmethystTridentBEWLR;
import com.macaronsteam.amethysttoolsmod.client.renderer.AmethystTridentRenderer;
import com.macaronsteam.amethysttoolsmod.init.EntitiesInit;
import com.macaronsteam.amethysttoolsmod.init.ItemsInit;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.alchemy.PotionContents;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@EventBusSubscriber(modid = AmethystToolsMod.MODID, value = Dist.CLIENT)
public class AmethystToolsModClient {

  @SubscribeEvent
  public static void onClientSetup(FMLClientSetupEvent event) {
    event.enqueueWork(() -> {
      if (EntitiesInit.ENTITY_AMETHYST_ARROW.isBound()) {
        EntityRenderers.register(EntitiesInit.ENTITY_AMETHYST_ARROW.get(),
            context -> new AmethystArrowRenderer<>(context, "textures/entity/amethyst_arrow.png"));
      }
      if (EntitiesInit.ENTITY_AMETHYST_SPECTRAL_ARROW.isBound()) {
        EntityRenderers.register(EntitiesInit.ENTITY_AMETHYST_SPECTRAL_ARROW.get(),
            context -> new AmethystArrowRenderer<>(context, "textures/entity/amethyst_spectral_arrow.png"));
      }
      if (EntitiesInit.ENTITY_AMETHYST_TRIDENT.isBound()) {
        EntityRenderers.register(EntitiesInit.ENTITY_AMETHYST_TRIDENT.get(), AmethystTridentRenderer::new);
      }
      if (ItemsInit.ITEM_AMETHYST_TRIDENT.isBound()) {
        ItemProperties.register(ItemsInit.ITEM_AMETHYST_TRIDENT.get(), ResourceLocation.withDefaultNamespace("throwing"),
            (itemstack, level, livingentity, seed) -> livingentity != null && livingentity.isUsingItem() && livingentity.getUseItem() == itemstack ? 1.0F : 0.0F);
      }
    });
  }

  @SubscribeEvent
  public static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
    if (ItemsInit.ITEM_AMETHYST_TRIDENT.isBound()) {
      event.registerItem(new IClientItemExtensions() {
        @Override
        public BlockEntityWithoutLevelRenderer getCustomRenderer() {
          return AmethystTridentBEWLR.INSTANCE;
        }
      }, ItemsInit.ITEM_AMETHYST_TRIDENT.get());
    }
  }

  @SubscribeEvent
  public static void onRegisterClientReloadListeners(RegisterClientReloadListenersEvent event) {
    if (ItemsInit.ITEM_AMETHYST_TRIDENT.isBound()) {
      event.registerReloadListener(AmethystTridentBEWLR.INSTANCE);
    }
  }

  @SubscribeEvent
  public static void onRegisterColorHandlers(RegisterColorHandlersEvent.Item event) {
    if (ItemsInit.ITEM_AMETHYST_TIPPED_ARROW.isBound()) {
      event.register(
          (itemstack, layer) -> layer == 0 ? -1 : itemstack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).getColor(),
          ItemsInit.ITEM_AMETHYST_TIPPED_ARROW.get());
    }
  }
}
