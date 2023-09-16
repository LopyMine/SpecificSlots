package net.lopymine.specificslots.mixin;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.lopymine.specificslots.SpecificSlots;
import net.lopymine.specificslots.config.SpecificConfigManager;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {

    @Inject(at = @At("HEAD"), method = "disconnect()V")
    private void disconnect(CallbackInfo ci) {
        SpecificSlots.inventoryConfig = SpecificConfigManager.getInventoryConfig();
    }
}
