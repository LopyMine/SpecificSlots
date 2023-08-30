package net.lopymine.specificslots.mixin;

import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import net.lopymine.specificslots.SpecificSlots;
import net.lopymine.specificslots.autosort.SlotInfoImpl;
import net.lopymine.specificslots.config.defalt.DefaultSpecificConfig;
import net.lopymine.specificslots.config.defalt.DefaultSpecificConfigManager;
import net.lopymine.specificslots.config.SpecificConfig;
import net.lopymine.specificslots.gui.SpecificSlotsGui;
import net.lopymine.specificslots.gui.screen.SpecificScreen;
import net.lopymine.specificslots.gui.tooltip.WarningTooltipData;
import net.lopymine.specificslots.gui.widgets.vanilla.GhostItemsShowWidget;
import net.lopymine.specificslots.gui.widgets.vanilla.WarningWidget;
import net.lopymine.specificslots.handlers.DrawableHandler;
import net.lopymine.specificslots.textures.GhostItems;
import net.lopymine.specificslots.utils.ItemUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.EnchantmentScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(HandledScreen.class)
public abstract class HandledScreenMixin<T extends ScreenHandler> extends Screen {
    @Shadow
    protected int x;
    @Shadow
    protected int y;
    @Shadow
    @Final
    protected T handler;

    @Shadow
    protected abstract void init();

    @Shadow protected int backgroundHeight;
    @Shadow protected int backgroundWidth;
    private final SpecificConfig config = SpecificSlots.config;
    private final DefaultSpecificConfig defaultConfig = DefaultSpecificConfigManager.getDefaultConfig();
    private final WarningWidget warningWidget = new WarningWidget(0, 0, widget -> {});
    private final GhostItemsShowWidget showWidget = new GhostItemsShowWidget(0, 0);
    private final ButtonWidget configButton = ButtonWidget.builder(Text.of("Specific Slots"), (button1 -> MinecraftClient.getInstance().setScreen(new SpecificScreen(new SpecificSlotsGui(DefaultSpecificConfigManager.getInventoryConfig(), this))))).dimensions(5, this.height - 5, 80, 20).build();
    private final ItemRenderer renderer = MinecraftClient.getInstance().getItemRenderer();
    private DrawableHandler drawableHandler = null;

    protected HandledScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("RETURN"), method = "init")
    private void init(CallbackInfo ci) {

        Screen currentScreen = MinecraftClient.getInstance().currentScreen;

        drawableHandler = DrawableHandler.getInstance(handler, currentScreen);
        if (drawableHandler != null) drawableHandler.updateSlotPos();

        int y = this.height - 25;

        this.configButton.setPosition(5, y);
        this.showWidget.setPosition(87, y);
        this.warningWidget.setPosition(this.x + this.backgroundWidth + 5, this.y + this.backgroundHeight - 27);
        this.addDrawableChild(configButton);
        this.addDrawableChild(warningWidget);
        this.addDrawableChild(showWidget);
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;render(Lnet/minecraft/client/util/math/MatrixStack;IIF)V"))
    private void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        this.warningWidget.setPosition(this.x + this.backgroundWidth + 5, this.y + this.backgroundHeight - 27);
        if (drawableHandler == null) return;
        if (drawableHandler.shouldDrawInventory()) this.drawInventory(matrices);
        if (drawableHandler.shouldDrawHotBar()) this.drawHotBar(matrices);
    }

    public void drawInventory(MatrixStack matrices) {
        int k = 0;
        int p = drawableHandler.getInventoryX() + this.x;
        int l = drawableHandler.getInventoryY() + this.y;
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++) {
                int yy = y * 18;
                int xx = x * 18;
                this.drawInventory(matrices, xx + p, yy + l, k);
                k++;
            }
        }
    }

    public void drawHotBar(MatrixStack matrices) {
        int k = 0;
        int p = drawableHandler.getHotBarX() + this.x;
        int l = drawableHandler.getHotBarY() + this.y;

        for (int x = 0; x < 9; x++) {
            int xx = x * 18;
            this.drawHotBar(matrices, xx + p, l, k);
            k++;
        }
    }

    private void drawInventory(MatrixStack matrices, int x, int y, int slot) {
        if (slot >= config.getInventory().size()) {
            return;
        }

        Item item = ItemUtils.getItemByName(config.getInventory().get(slot));

        SlotInfoImpl slotInfo = getSlotInfo(false, item, slot);
        if (slotInfo == null) return;

        if (slotInfo.isWrong() && defaultConfig.enableHighlightWrongSlots) {
            WarningTooltipData data = new WarningTooltipData(slotInfo.getConfigItem().getDefaultStack(), slotInfo.getInventoryItem().getDefaultStack(), slot);
            if (!warningWidget.data.contains(data)) warningWidget.data.add(data);
            DrawableHelper.fill(matrices, x, y, x + 16, y + 16, defaultConfig.getColor());
        }
        if (!defaultConfig.renderSlotWithItem && slotInfo.hasInventoryItem()) return;

        if (showWidget.isHovered()) {
            renderer.renderInGui(matrices, item.getDefaultStack(), x, y);
        } else {
            Identifier texture = GhostItems.getTexture(item);
            if (texture == null) return;
            for (int i = 0; i < defaultConfig.depth; i++) {
                ScreenDrawing.texturedRect(matrices, x, y, 16, 16, texture, 0xFFFFFFFF);
            }
        }
    }

    private void drawHotBar(MatrixStack matrices, int x, int y, int slot) {
        if (slot >= config.getHotBar().size()) {
            return;
        }

        Item item = ItemUtils.getItemByName(config.getHotBar().get(slot));

        SlotInfoImpl slotInfo = getSlotInfo(true, item, slot);
        if (slotInfo == null) return;

        if (slotInfo.isWrong() && defaultConfig.enableHighlightWrongSlots) {
            WarningTooltipData data = new WarningTooltipData(slotInfo.getConfigItem().getDefaultStack(), slotInfo.getInventoryItem().getDefaultStack(), slot + 27);
            if (!warningWidget.data.contains(data)) warningWidget.data.add(data);
            DrawableHelper.fill(matrices, x, y, x + 16, y + 16, defaultConfig.getColor());
        }
        if (!defaultConfig.renderSlotWithItem && slotInfo.hasInventoryItem()) return;

        if (showWidget.isHovered()) {
            renderer.renderInGui(matrices, item.getDefaultStack(), x, y);
        } else {
            Identifier texture = GhostItems.getTexture(item);
            if (texture == null) return;
            for (int i = 0; i < defaultConfig.depth; i++) {
                ScreenDrawing.texturedRect(matrices, x, y, 16, 16, texture, 0xFFFFFFFF);
            }
        }
    }

    @Nullable
    private SlotInfoImpl getSlotInfo(boolean hotBar, Item configItem, int slot) {
        SlotInfoImpl slotInfo = new SlotInfoImpl();
        slotInfo.setConfigItem(configItem, slot);

        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return null;
        PlayerInventory inventory = player.getInventory();

        if (hotBar) {
            List<ItemStack> hotBarList = inventory.main.subList(0, 9);
            Item item = hotBarList.get(slot).getItem();
            slotInfo.setInventoryItem(item, slot);
        } else {
            List<ItemStack> inventoryList = inventory.main.subList(9, 36);
            Item item = inventoryList.get(slot).getItem();
            slotInfo.setInventoryItem(item, slot);
        }
        return slotInfo;
    }

    //@Inject(at = @At("HEAD"), method = "mouseDragged", cancellable = true)
    //private void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY, CallbackInfoReturnable<Boolean> cir) {
    //    for (Element element : children()) {
    //        if (element instanceof WarningWidget) {
    //            if (!warningWidget.isMouseOver(mouseX, mouseY)) return;
    //
    //            if (warningWidget.mouseDragged(mouseX, mouseY, button, deltaX, deltaY)) {
    //                cir.setReturnValue(true);
    //                cir.cancel();
    //            }
    //        }
    //    }
    //}
//
    //@Inject(at = @At("HEAD"), method = "mouseReleased", cancellable = true)
    //private void mouseReleased(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
    //    for (Element element : children()) {
    //        if (element instanceof WarningWidget) {
    //            if (warningWidget.mouseReleased(mouseX, mouseY, button)) {
    //                cir.setReturnValue(true);
    //                cir.cancel();
    //            }
    //        }
    //    }
    //}

}
