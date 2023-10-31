package net.slayer.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.slayer.Config;
import net.slayer.HeartseekerMain;

public class LifeCrystalItem extends Item {
    public LifeCrystalItem(Item.Settings settings) {
        super(settings);
    }

    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        super.finishUsing(stack, world, user);
        if (HeartseekerMain.getHearts((ServerPlayerEntity) user) < Config.maxHearts) {
            HeartseekerMain.setHearts((ServerPlayerEntity) user, 1, false);
            stack.decrement(1);
        }
        return stack;
    }

    public int getMaxUseTime(ItemStack stack) {
        return Config.heartConsumptionTime;
    }

    public Rarity getRarity(ItemStack stack) {
        return Rarity.RARE;
    }

    public SoundEvent getDrinkSound() {
        return SoundEvents.BLOCK_AMETHYST_CLUSTER_HIT;
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }


    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }
}
