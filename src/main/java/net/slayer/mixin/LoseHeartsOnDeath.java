package net.slayer.mixin;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.slayer.Config;
import net.slayer.HeartseekerMain;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEvents.class)
public class LoseHeartsOnDeath {
    @Inject(at = @At("HEAD"), method = "lambda$static$2")
    private static void applySolarImmunityOnSanguinareRespawn(ServerPlayerEvents.AfterRespawn[] callbacks, ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive, CallbackInfo ci) {
        if (Config.resetHeartsOnDeath) {
            HeartseekerMain.setHearts(oldPlayer,Config.minHearts,true);
        } else if (Config.loseHeartsOnDeath) {
            HeartseekerMain.setHearts(oldPlayer,-1,false);
        }
    }
}
