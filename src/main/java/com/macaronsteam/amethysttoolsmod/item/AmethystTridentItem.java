package com.macaronsteam.amethysttoolsmod.item;

import com.macaronsteam.amethysttoolsmod.config.AmethystToolsModConfig;
import com.macaronsteam.amethysttoolsmod.entity.ThrownAmethystTrident;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class AmethystTridentItem extends TridentItem {

  public AmethystTridentItem(Item.Properties properties) {
    super(properties);
  }

  @Override
  public int getEnchantmentValue() {
    return super.getEnchantmentValue() + AmethystToolsModConfig.extraEnchantability.get();
  }

  @Override
  public void releaseUsing(ItemStack itemstack, Level level, LivingEntity livingentity, int p) {
    if (livingentity instanceof Player player) {
      int i = this.getUseDuration(itemstack, livingentity) - p;
      if (i >= 10) {
        float f = EnchantmentHelper.getTridentSpinAttackStrength(itemstack, player);
        if (!(f > 0.0F) || player.isInWaterOrRain()) {
          if (!isTooDamagedToUse(itemstack)) {
            Holder<SoundEvent> holder = EnchantmentHelper.pickHighestLevel(itemstack, EnchantmentEffectComponents.TRIDENT_SOUND)
                .orElse(SoundEvents.TRIDENT_THROW);
            if (!level.isClientSide) {
              itemstack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(livingentity.getUsedItemHand()));
              if (f == 0.0F) {
                ThrownAmethystTrident throwntrident = new ThrownAmethystTrident(level, player, itemstack);
                throwntrident.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F, 1.0F);
                if (player.hasInfiniteMaterials()) {
                  throwntrident.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                }

                level.addFreshEntity(throwntrident);
                level.playSound((Player) null, throwntrident, holder.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
                if (!player.hasInfiniteMaterials()) {
                  player.getInventory().removeItem(itemstack);
                }
              }
            }

            player.awardStat(Stats.ITEM_USED.get(this));
            if (f > 0.0F) {
              float f7 = player.getYRot();
              float f1 = player.getXRot();
              float f2 = -Mth.sin(f7 * (float) (Math.PI / 180.0)) * Mth.cos(f1 * (float) (Math.PI / 180.0));
              float f3 = -Mth.sin(f1 * (float) (Math.PI / 180.0));
              float f4 = Mth.cos(f7 * (float) (Math.PI / 180.0)) * Mth.cos(f1 * (float) (Math.PI / 180.0));
              float f5 = Mth.sqrt(f2 * f2 + f3 * f3 + f4 * f4);
              float f6 = 3.0F * ((1.0F + f) / 4.0F);
              f2 *= f6 / f5;
              f3 *= f6 / f5;
              f4 *= f6 / f5;
              player.push((double) f2, (double) f3, (double) f4);
              player.startAutoSpinAttack(20, 8.0F, itemstack);
              if (player.onGround()) {
                player.move(MoverType.SELF, new Vec3(0.0, 1.1999999F, 0.0));
              }

              level.playSound((Player) null, player, holder.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
            }
          }
        }
      }
    }
  }

  @Override
  public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
    ItemStack itemstack = player.getItemInHand(hand);
    if (isTooDamagedToUse(itemstack)) {
      return InteractionResultHolder.fail(itemstack);
    } else if (EnchantmentHelper.getTridentSpinAttackStrength(itemstack, player) > 0.0F && !player.isInWaterOrRain()) {
      return InteractionResultHolder.fail(itemstack);
    } else {
      player.startUsingItem(hand);
      return InteractionResultHolder.consume(itemstack);
    }
  }

  private static boolean isTooDamagedToUse(ItemStack itemstack) {
    return itemstack.getDamageValue() >= itemstack.getMaxDamage() - 1;
  }
}
