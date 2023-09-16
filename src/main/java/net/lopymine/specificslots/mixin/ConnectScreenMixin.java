package net.lopymine.specificslots.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.*;
import net.minecraft.client.network.*;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.lopymine.specificslots.SpecificSlots;
import net.lopymine.specificslots.config.SpecificConfigManager;
import net.lopymine.specificslots.config.inventory.InventoryConfigManager;
import net.lopymine.specificslots.gui.config.SaveConfigGui.ServerInventoryConfig;

import java.util.Set;

@Mixin(ConnectScreen.class)
public abstract class ConnectScreenMixin extends Screen {
    protected ConnectScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("HEAD"), method = "connect(Lnet/minecraft/client/MinecraftClient;Lnet/minecraft/client/network/ServerAddress;Lnet/minecraft/client/network/ServerInfo;)V")
    private void connect(MinecraftClient client, ServerAddress address, ServerInfo info, CallbackInfo ci) {
        if(info == null) return;

        Set<ServerInventoryConfig> serverInventoryConfigs = SpecificConfigManager.getConfig().serverInventoryConfigs;

        for(ServerInventoryConfig config : serverInventoryConfigs){
            if(config.ip().equals(info.address)){
                SpecificSlots.inventoryConfig = InventoryConfigManager.read(config.inventoryConfig());
            }
        }
    }
}
