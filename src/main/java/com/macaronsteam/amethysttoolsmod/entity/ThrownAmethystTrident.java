package com.macaronsteam.amethysttoolsmod.entity;

import java.lang.reflect.Field;

import javax.annotation.Nullable;

import com.macaronsteam.amethysttoolsmod.config.AmethystToolsModConfig;
import com.macaronsteam.amethysttoolsmod.init.EntitiesInit;
import com.macaronsteam.amethysttoolsmod.init.ItemsInit;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class ThrownAmethystTrident extends ThrownTrident {
  private static final EntityDataAccessor<Byte> ID_LOYALTY = synchedAccessor("ID_LOYALTY");
  private static final EntityDataAccessor<Boolean> ID_FOIL = synchedAccessor("ID_FOIL");

  public ThrownAmethystTrident(EntityType<? extends ThrownAmethystTrident> entityType, Level level) {
    super(entityType, level);
  }

  public ThrownAmethystTrident(Level level, LivingEntity shooter, ItemStack trident) {
    super(EntitiesInit.ENTITY_AMETHYST_TRIDENT.get(), level);
    this.setPickupItemStack(trident.copy());
    this.setCustomName(trident.get(DataComponents.CUSTOM_NAME));
    this.setPos(shooter.getX(), shooter.getEyeY() - 0.1F, shooter.getZ());
    this.setOwner(shooter);
    this.entityData.set(ID_LOYALTY, this.getLoyaltyFromItem(trident));
    this.entityData.set(ID_FOIL, trident.hasFoil());
  }

  private byte getLoyaltyFromItem(ItemStack trident) {
    return this.level() instanceof ServerLevel serverlevel
        ? (byte) Mth.clamp(EnchantmentHelper.getTridentReturnToOwnerAcceleration(serverlevel, trident, this), 0, 127)
        : 0;
  }

  @Override
  protected void onHitEntity(EntityHitResult hitresult) {
    Entity entity = hitresult.getEntity();
    float f = 8.0F + AmethystToolsModConfig.arrowExtraDamage.get().floatValue();
    Entity owner = this.getOwner();
    DamageSource damagesource = this.damageSources().trident(this, (Entity) (owner == null ? this : owner));
    if (this.level() instanceof ServerLevel serverlevel) {
      f = EnchantmentHelper.modifyDamage(serverlevel, this.getWeaponItem(), entity, damagesource, f);
    }

    this.dealtDamage = true;
    if (entity.hurt(damagesource, f)) {
      if (entity.getType() == EntityType.ENDERMAN) {
        return;
      }

      if (this.level() instanceof ServerLevel serverlevel1) {
        EnchantmentHelper.doPostAttackEffectsWithItemSource(serverlevel1, entity, damagesource, this.getWeaponItem());
      }

      if (entity instanceof LivingEntity livingentity) {
        this.doKnockback(livingentity, damagesource);
        this.doPostHurtEffects(livingentity);
      }
    }

    this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01, -0.1, -0.01));
    this.playSound(SoundEvents.TRIDENT_HIT, 1.0F, 1.0F);
  }

  @Override
  protected ItemStack getDefaultPickupItem() {
    return new ItemStack(ItemsInit.ITEM_AMETHYST_TRIDENT.get());
  }

  private static <T> EntityDataAccessor<T> synchedAccessor(String name) {
    try {
      Field field = ThrownTrident.class.getDeclaredField(name);
      field.setAccessible(true);
      return (EntityDataAccessor<T>) field.get(null);
    } catch (ReflectiveOperationException exception) {
      throw new IllegalStateException("Failed to access ThrownTrident." + name, exception);
    }
  }
}
