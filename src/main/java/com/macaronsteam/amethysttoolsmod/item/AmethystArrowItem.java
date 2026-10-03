package com.macaronsteam.amethysttoolsmod.item;

import javax.annotation.Nullable;

import com.macaronsteam.amethysttoolsmod.entity.AmethystArrowEntity;
import com.macaronsteam.amethysttoolsmod.entity.AmethystSpectralArrowEntity;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Position;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpectralArrowItem;
import net.minecraft.world.item.TippedArrowItem;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class AmethystArrowItem extends ArrowItem {

  public AmethystArrowItem(Item.Properties properties) {
    super(properties);
  }

  @Override
  public AbstractArrow createArrow(Level level, ItemStack itemstack, LivingEntity livingentity, @Nullable ItemStack weapon) {
    return new AmethystArrowEntity(level, livingentity, itemstack.copyWithCount(1), weapon);
  }

  @Override
  public Projectile asProjectile(Level level, Position position, ItemStack itemstack, Direction direction) {
    AmethystArrowEntity arrow = new AmethystArrowEntity(level, position.x(), position.y(), position.z(), itemstack.copyWithCount(1));
    arrow.pickup = AbstractArrow.Pickup.ALLOWED;
    return arrow;
  }

  @Override
  public boolean isInfinite(ItemStack ammo, ItemStack bow, LivingEntity livingentity) {
    for (Object2IntMap.Entry<Holder<net.minecraft.world.item.enchantment.Enchantment>> entry : bow.getTagEnchantments().entrySet()) {
      if (entry.getKey().is(Enchantments.INFINITY)) {
        return true;
      }
    }
    return false;
  }

  public static class TippedItem extends TippedArrowItem {

    public TippedItem(Item.Properties properties) {
      super(properties);
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack itemstack, LivingEntity livingentity, @Nullable ItemStack weapon) {
      return new AmethystArrowEntity(level, livingentity, itemstack.copyWithCount(1), weapon);
    }

    @Override
    public Projectile asProjectile(Level level, Position position, ItemStack itemstack, Direction direction) {
      AmethystArrowEntity arrow = new AmethystArrowEntity(level, position.x(), position.y(), position.z(), itemstack.copyWithCount(1));
      arrow.pickup = AbstractArrow.Pickup.ALLOWED;
      return arrow;
    }

    @Override
    public boolean isInfinite(ItemStack ammo, ItemStack bow, LivingEntity livingentity) {
      return AmethystArrowItem.isInfinity(bow);
    }
  }

  public static class SpectralItem extends SpectralArrowItem {

    public SpectralItem(Item.Properties properties) {
      super(properties);
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack itemstack, LivingEntity livingentity, @Nullable ItemStack weapon) {
      return new AmethystSpectralArrowEntity(level, livingentity, itemstack.copyWithCount(1), weapon);
    }

    @Override
    public Projectile asProjectile(Level level, Position position, ItemStack itemstack, Direction direction) {
      AmethystSpectralArrowEntity arrow = new AmethystSpectralArrowEntity(level, position.x(), position.y(), position.z(), itemstack.copyWithCount(1));
      arrow.pickup = AbstractArrow.Pickup.ALLOWED;
      return arrow;
    }

    @Override
    public boolean isInfinite(ItemStack ammo, ItemStack bow, LivingEntity livingentity) {
      return AmethystArrowItem.isInfinity(bow);
    }
  }

  static boolean isInfinity(ItemStack bow) {
    for (Object2IntMap.Entry<Holder<net.minecraft.world.item.enchantment.Enchantment>> entry : bow.getTagEnchantments().entrySet()) {
      if (entry.getKey().is(Enchantments.INFINITY)) {
        return true;
      }
    }
    return false;
  }
}
