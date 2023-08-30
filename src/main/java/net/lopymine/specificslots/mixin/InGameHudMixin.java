package net.lopymine.specificslots.mixin;

import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import net.lopymine.specificslots.SpecificSlots;
import net.lopymine.specificslots.config.defalt.DefaultSpecificConfig;
import net.lopymine.specificslots.config.defalt.DefaultSpecificConfigManager;
import net.lopymine.specificslots.config.SpecificConfig;
import net.lopymine.specificslots.textures.GhostItems;
import net.lopymine.specificslots.utils.ItemUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(InGameHud.class)
public class InGameHudMixin extends DrawableHelper {
    @Shadow
    private int scaledWidth;
    @Shadow
    private int scaledHeight;
    private SpecificConfig config = SpecificSlots.config;
    private DefaultSpecificConfig defaultConfig = DefaultSpecificConfigManager.getDefaultConfig();

    @Inject(method = "renderHotbar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;pop()V", shift = At.Shift.AFTER))
    private void render(float tickDelta, MatrixStack matrices, CallbackInfo ci) {
        this.config = SpecificSlots.config;
        this.defaultConfig = SpecificSlots.defaultConfig;

        int k = 0;
        int x = (this.scaledWidth / 2) - 88;
        int y = this.scaledHeight - 19;

        for (int d = 0; d < 9; d++) {
            int xx = d * 20;
            this.drawHotBar(matrices, xx + x, y, k);
            k++;
        }

    }

    private void drawHotBar(MatrixStack matrices, int x, int y, int slot) {
        if (slot >= config.getHotBar().size()) {
            return;
        }

        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return;
        PlayerInventory inventory = player.getInventory();

        List<ItemStack> hotBar = inventory.main.subList(0, 9);
        if (!defaultConfig.renderSlotWithItem && hotBar.get(slot).getItem() != Items.AIR) return;

        Item item = ItemUtils.getItemByName(config.getHotBar().get(slot));
        Identifier texture = GhostItems.getTexture(item);

        if (texture != null) {
            for (int i = 0; i < defaultConfig.depth; i++) {
                ScreenDrawing.texturedRect(matrices, x, y, 16, 16, texture, 0xFFFFFFFF);
            }
        }

    }

    @Inject(method = "setSubtitle", at = @At(value = "HEAD"))
    private void setSubtitle(Text subtitle, CallbackInfo ci) {
        System.out.println(subtitle + "1");
    }
    @Inject(method = "setTitle", at = @At(value = "HEAD"))
    private void setTitle(Text title, CallbackInfo ci) {
        System.out.println(title + "2");
    }

    @Inject(method = "setOverlayMessage", at = @At(value = "HEAD"))
    private void setTitle(Text message, boolean tinted, CallbackInfo ci) {
        System.out.println(message + "3");
    }

}
