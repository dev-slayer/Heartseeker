package net.slayer;

import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.BinomialLootNumberProvider;
import net.minecraft.loot.provider.number.LootNumberProviderTypes;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.slayer.item.HeartSeekerItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.SplittableRandom;
import java.util.UUID;

import static net.minecraft.server.command.CommandManager.literal;

public class HeartseekerMain implements ModInitializer {

	public static final String MOD_ID = "heartseeker";
	public static final String VERSION = "0.1.2";
	public static final UUID heartseekerModificationID = UUID.fromString("235391e3-4686-484b-90ed-f604e57a1225");
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Identifier HEARTSEEKER_UPDATED = new Identifier(MOD_ID, "heartseeker_updated");

	@Override
	public void onInitialize() {
		HeartSeekerItems.registerItems();
		MidnightConfig.init(HeartseekerMain.MOD_ID, Config.class);

		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
			if (source.isBuiltin() && LootTables.DESERT_PYRAMID_CHEST.equals(id) || LootTables.ANCIENT_CITY_ICE_BOX_CHEST.equals(id) || LootTables.BASTION_TREASURE_CHEST.equals(id) || LootTables.END_CITY_TREASURE_CHEST.equals(id) || LootTables.BURIED_TREASURE_CHEST.equals(id) || LootTables.SPAWN_BONUS_CHEST.equals(id) || LootTables.STRONGHOLD_LIBRARY_CHEST.equals(id) || LootTables.JUNGLE_TEMPLE_CHEST.equals(id) || LootTables.IGLOO_CHEST_CHEST.equals(id)) {
				LootPool.Builder poolBuilder = LootPool.builder().rolls(BinomialLootNumberProvider.create(1, 0.05f)).with(ItemEntry.builder(HeartSeekerItems.LIFE_CRYSTAL));

				tableBuilder.pool(poolBuilder);
			}
		});
	}

	public static void setHearts(ServerPlayerEntity player, int value, boolean set) {

		PlayerData playerState = StateSaverAndLoader.getPlayerState(player);
		if (set) {
			playerState.seekedHearts = value;
		} else {
			playerState.seekedHearts = playerState.seekedHearts + value;
		}
		if (playerState.seekedHearts < Config.minHearts) {
			playerState.seekedHearts = Config.minHearts;
		} else if (playerState.seekedHearts > Config.maxHearts) {
			playerState.seekedHearts = Config.maxHearts;
		}
	}

	public static int getHearts(ServerPlayerEntity player) {
		PlayerData playerState = StateSaverAndLoader.getPlayerState(player);
		return playerState.seekedHearts;
	}
}