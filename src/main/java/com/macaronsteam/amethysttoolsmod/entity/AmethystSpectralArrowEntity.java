package com.macaronsteam.amethysttoolsmod.entity;

import javax.annotation.Nullable;

import com.macaronsteam.amethysttoolsmod.config.AmethystToolsModConfig;
import com.macaronsteam.amethysttoolsmod.init.EntitiesInit;
import com.macaronsteam.amethysttoolsmod.init.ItemsInit;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class AmethystSpectralArrowEntity extends AbstractArrow {

  public AmethystSpectralArrowEntity(EntityType<? extends AmethystSpectralArrowEntity> entityType, Level level) {
    super(entityType, level);
  }

  public AmethystSpectralArrowEntity(Level level, LivingEntity shooter, ItemStack pickupItem, @Nullable ItemStack weapon) {
    super(EntitiesInit.ENTITY_AMETHYST_SPECTRAL_ARROW.get(), shooter, level, pickupItem, weapon);
    this.setBaseDamage(this.getBaseDamage() + AmethystToolsModConfig.arrowExtraDamage.get());
  }

  public AmethystSpectralArrowEntity(Level level, double x, double y, double z, ItemStack pickupItem) {
    super(EntitiesInit.ENTITY_AMETHYST_SPECTRAL_ARROW.get(), x, y, z, level, pickupItem, null);
  }

  @Override
  public void tick() {
    super.tick();
    if (this.level().isClientSide && !this.inGround) {
      this.level().addParticle(net.minecraft.core.particles.ParticleTypes.INSTANT_EFFECT, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
    }
  }

  @Override
  protected void doPostHurtEffects(LivingEntity target) {
    super.doPostHurtEffects(target);
    MobEffectInstance glowing = new MobEffectInstance(MobEffects.GLOWING, AmethystToolsModConfig.glowingDuration.get(), 0);
    target.addEffect(glowing, this.getEffectSource());
  }

  @Override
  protected ItemStack getDefaultPickupItem() {
    return new ItemStack(ItemsInit.ITEM_AMETHYST_SPECTRAL_ARROW.get());
  }
}
