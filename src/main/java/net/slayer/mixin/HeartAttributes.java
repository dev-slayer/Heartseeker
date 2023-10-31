package net.slayer.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import net.slayer.Config;
import net.slayer.HeartseekerMain;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class HeartAttributes extends LivingEntity {

	protected HeartAttributes(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(at = @At("HEAD"), method = "tick")
	private void sanguinareTick(CallbackInfo info) {
		if (!this.getWorld().isClient) {
			ServerPlayerEntity player = this.getServer().getPlayerManager().getPlayer(this.getUuid());

			if (player == null) {
				return;
			}

			float playerHearts = HeartseekerMain.getHearts(player) * 2;

			if (playerHearts < Config.minHearts) {
				HeartseekerMain.setHearts(player, Config.minHearts, true);
				player.setHealth(player.getMaxHealth());
			}
			EntityAttributeInstance entityAttributeInstance = this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
			if (entityAttributeInstance != null) {
				if (entityAttributeInstance.getModifier(HeartseekerMain.heartseekerModificationID) != null) {
					entityAttributeInstance.removeModifier(HeartseekerMain.heartseekerModificationID);
				}
			}
			entityAttributeInstance.addTemporaryModifier(new EntityAttributeModifier(HeartseekerMain.heartseekerModificationID, "Heartseeker Max Health", playerHearts - 20, EntityAttributeModifier.Operation.ADDITION));
		}
	}
}