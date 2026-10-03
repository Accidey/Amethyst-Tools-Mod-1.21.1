package com.macaronsteam.amethysttoolsmod.init;

import com.macaronsteam.amethysttoolsmod.AmethystToolsMod;
import com.macaronsteam.amethysttoolsmod.config.AmethystToolsModConfig;
import com.macaronsteam.amethysttoolsmod.entity.AmethystArrowEntity;
import com.macaronsteam.amethysttoolsmod.entity.AmethystSpectralArrowEntity;
import com.macaronsteam.amethysttoolsmod.entity.ThrownAmethystTrident;
import com.google.common.primitives.Booleans;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EntitiesInit {
  public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, AmethystToolsMod.MODID);

  public static final DeferredHolder<EntityType<?>, EntityType<AmethystArrowEntity>> ENTITY_AMETHYST_ARROW = register("amethyst_arrow_entity",
      () -> EntityType.Builder.<AmethystArrowEntity>of(AmethystArrowEntity::new, MobCategory.MISC)
          .sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build("amethysttoolsmod:amethyst_arrow_entity"),
      AmethystToolsModConfig.enableAmethystArrows.get());
  public static final DeferredHolder<EntityType<?>, EntityType<AmethystSpectralArrowEntity>> ENTITY_AMETHYST_SPECTRAL_ARROW = register(
      "amethyst_spectral_arrow_entity",
      () -> EntityType.Builder.<AmethystSpectralArrowEntity>of(AmethystSpectralArrowEntity::new, MobCategory.MISC)
          .sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build("amethysttoolsmod:amethyst_spectral_arrow_entity"),
      AmethystToolsModConfig.enableAmethystArrows.get(), AmethystToolsModConfig.enableExtraArrows.get());
  public static final DeferredHolder<EntityType<?>, EntityType<ThrownAmethystTrident>> ENTITY_AMETHYST_TRIDENT = register("amethyst_trident_entity",
      () -> EntityType.Builder.<ThrownAmethystTrident>of(ThrownAmethystTrident::new, MobCategory.MISC)
          .sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build("amethysttoolsmod:amethyst_trident_entity"),
      AmethystToolsModConfig.enableAmethystTrident.get());

  private static <T extends net.minecraft.world.entity.Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(String name,
      java.util.function.Supplier<EntityType<T>> supplier, boolean... condition) {
    if (!Booleans.contains(condition, false))
      return ENTITIES.register(name, supplier);
    return DeferredHolder.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(AmethystToolsMod.MODID, name));
  }
}
