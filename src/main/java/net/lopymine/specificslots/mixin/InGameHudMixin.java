package net.lopymine.specificslots.mixin;

import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import net.lopymine.specificslots.SpecificSlots;
import net.lopymine.specificslots.config.inventory.InventoryConfig;
import net.lopymine.specificslots.config.SpecificConfig;
import net.lopymine.specificslots.textures.ShadowItems;
import net.lopymine.specificslots.utils.ItemUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Shadow
    private int scaledWidth;
    @Shadow
    private int scaledHeight;
    @Unique
    private SpecificConfig config = SpecificSlots.config;
    @Unique
    private InventoryConfig inventoryConfig = SpecificSlots.inventoryConfig;

    @Inject(method = "renderHotbar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;pop()V", shift = At.Shift.AFTER))
    private void render(float tickDelta, DrawContext context, CallbackInfo ci) {
        this.config = SpecificSlots.config;
        this.inventoryConfig = SpecificSlots.inventoryConfig;

        int k = 0;
        int x = (this.scaledWidth / 2) - 88;
        int y = this.scaledHeight - 19;

        for (int d = 0; d < 9; d++) {
            int xx = d * 20;
            this.drawHotBar(context, xx + x, y, k);
            k++;
        }

    }

    @Unique
    private void drawHotBar(DrawContext context, int x, int y, int slot) {
        if (slot >= inventoryConfig.getHotBar().size()) {
            return;
        }

        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return;
        PlayerInventory inventory = player.getInventory();

        List<ItemStack> hotBar = inventory.main.subList(0, 9);
        if (!config.renderSlotWithItem && hotBar.get(slot).getItem() != Items.AIR) return;

        Item item = ItemUtils.getItemByName(inventoryConfig.getHotBar().get(slot));
        Identifier texture = ShadowItems.getTexture(item);

        if (texture != null) {
            for (int i = 0; i < config.depth; i++) {
                ScreenDrawing.texturedRect(context, x, y, 16, 16, texture, 0xFFFFFFFF);
            }
        }

    }
}
