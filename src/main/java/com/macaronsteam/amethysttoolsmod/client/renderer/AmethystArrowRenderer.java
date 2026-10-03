package com.macaronsteam.amethysttoolsmod.client.renderer;

import com.macaronsteam.amethysttoolsmod.AmethystToolsMod;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.AbstractArrow;

public class AmethystArrowRenderer<T extends AbstractArrow> extends ArrowRenderer<T> {
  private final ResourceLocation texture;

  public AmethystArrowRenderer(EntityRendererProvider.Context context, String path) {
    super(context);
    this.texture = ResourceLocation.fromNamespaceAndPath(AmethystToolsMod.MODID, path);
  }

  @Override
  public ResourceLocation getTextureLocation(T entity) {
    return this.texture;
  }
}
