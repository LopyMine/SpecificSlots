package net.lopymine.specificslots.mixin;

import net.lopymine.specificslots.SpecificSlots;
import net.lopymine.specificslots.autosort.SlotInfoImpl;
import net.lopymine.specificslots.config.SpecificConfig;
import net.lopymine.specificslots.config.inventory.InventoryConfig;
import net.lopymine.specificslots.utils.ItemUtils;
import net.lopymine.specificslots.utils.Painters;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
    private void renderHotbar(float tickDelta, DrawContext context, CallbackInfo ci) {
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

        Item configItem = ItemUtils.getItemByName(inventoryConfig.getHotBar().get(slot));
        SlotInfoImpl slotInfo = SlotInfoImpl.getSlotInfo(true, configItem, slot, MinecraftClient.getInstance().player);
        if (slotInfo == null) {
            return;
        }

        Painters.highlightSlot(context, slotInfo, x, y, config);
    }
}
