package com.macaronsteam.amethysttoolsmod.entity;

import javax.annotation.Nullable;

import com.macaronsteam.amethysttoolsmod.config.AmethystToolsModConfig;
import com.macaronsteam.amethysttoolsmod.init.EntitiesInit;
import com.macaronsteam.amethysttoolsmod.init.ItemsInit;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;

public class AmethystArrowEntity extends AbstractArrow {
  private static final EntityDataAccessor<Integer> ID_EFFECT_COLOR = SynchedEntityData.defineId(AmethystArrowEntity.class, EntityDataSerializers.INT);

  public AmethystArrowEntity(EntityType<? extends AmethystArrowEntity> entityType, Level level) {
    super(entityType, level);
  }

  public AmethystArrowEntity(Level level, LivingEntity shooter, ItemStack pickupItem, @Nullable ItemStack weapon) {
    super(EntitiesInit.ENTITY_AMETHYST_ARROW.get(), shooter, level, pickupItem, weapon);
    this.setBaseDamage(this.getBaseDamage() + AmethystToolsModConfig.arrowExtraDamage.get());
  }

  public AmethystArrowEntity(Level level, double x, double y, double z, ItemStack pickupItem) {
    super(EntitiesInit.ENTITY_AMETHYST_ARROW.get(), x, y, z, level, pickupItem, null);
  }

  private PotionContents getPotionContents() {
    return this.getPickupItemStackOrigin().getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
  }

  private void updateColor() {
    PotionContents potioncontents = this.getPotionContents();
    this.entityData.set(ID_EFFECT_COLOR, potioncontents.equals(PotionContents.EMPTY) ? -1 : potioncontents.getColor());
  }

  @Override
  protected void setPickupItemStack(ItemStack stack) {
    if (stack.is(Items.ARROW))
      stack = new ItemStack(ItemsInit.ITEM_AMETHYST_ARROW.get());
    super.setPickupItemStack(stack);
    this.updateColor();
  }

  public void addEffect(MobEffectInstance effect) {
    this.getPickupItemStackOrigin().set(DataComponents.POTION_CONTENTS, this.getPotionContents().withEffectAdded(effect));
    this.updateColor();
  }

  @Override
  protected void defineSynchedData(SynchedEntityData.Builder builder) {
    super.defineSynchedData(builder);
    builder.define(ID_EFFECT_COLOR, -1);
  }

  @Override
  public void tick() {
    super.tick();
    if (this.level().isClientSide) {
      if (this.inGround) {
        if (this.inGroundTime % 5 == 0) {
          this.makeParticle(1);
        }
      } else {
        this.makeParticle(2);
      }
    } else if (this.inGround && this.inGroundTime != 0 && !this.getPotionContents().equals(PotionContents.EMPTY) && this.inGroundTime >= 600) {
      this.level().broadcastEntityEvent(this, (byte) 0);
      this.setPickupItemStack(new ItemStack(ItemsInit.ITEM_AMETHYST_ARROW.get()));
    }
  }

  private void makeParticle(int count) {
    int color = this.getColor();
    if (color != -1 && count > 0) {
      for (int j = 0; j < count; j++) {
        this.level().addParticle(
            ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT, color),
            this.getRandomX(0.5),
            this.getRandomY(),
            this.getRandomZ(0.5),
            0.0,
            0.0,
            0.0);
      }
    }
  }

  public int getColor() {
    return this.entityData.get(ID_EFFECT_COLOR);
  }

  @Override
  protected void doPostHurtEffects(LivingEntity target) {
    super.doPostHurtEffects(target);
    Entity owner = this.getEffectSource();
    PotionContents potioncontents = this.getPotionContents();
    if (potioncontents.potion().isPresent()) {
      for (MobEffectInstance mobeffectinstance : potioncontents.potion().get().value().getEffects()) {
        target.addEffect(
            new MobEffectInstance(
                mobeffectinstance.getEffect(),
                Math.max(mobeffectinstance.mapDuration(duration -> duration / 8), 1),
                mobeffectinstance.getAmplifier(),
                mobeffectinstance.isAmbient(),
                mobeffectinstance.isVisible()),
            owner);
      }
    }
    for (MobEffectInstance mobeffectinstance : potioncontents.customEffects()) {
      target.addEffect(mobeffectinstance, owner);
    }
  }

  @Override
  public void handleEntityEvent(byte id) {
    if (id == 0) {
      int color = this.getColor();
      if (color != -1) {
        float r = (float) (color >> 16 & 0xFF) / 255.0F;
        float g = (float) (color >> 8 & 0xFF) / 255.0F;
        float b = (float) (color >> 0 & 0xFF) / 255.0F;
        for (int j = 0; j < 20; j++) {
          this.level().addParticle(
              ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT, r, g, b),
              this.getRandomX(0.5),
              this.getRandomY(),
              this.getRandomZ(0.5),
              0.0,
              0.0,
              0.0);
        }
      }
    } else {
      super.handleEntityEvent(id);
    }
  }

  @Override
  protected ItemStack getDefaultPickupItem() {
    return new ItemStack(ItemsInit.ITEM_AMETHYST_ARROW.get());
  }
}
