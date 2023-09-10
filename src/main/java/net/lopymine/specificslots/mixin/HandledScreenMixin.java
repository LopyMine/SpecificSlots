package net.lopymine.specificslots.mixin;

import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import net.lopymine.specificslots.autosort.SlotInfoImpl;
import net.lopymine.specificslots.config.SpecificConfig;
import net.lopymine.specificslots.config.SpecificConfigManager;
import net.lopymine.specificslots.config.inventory.InventoryConfig;
import net.lopymine.specificslots.config.inventory.InventoryConfigManager;
import net.lopymine.specificslots.gui.SpecificSlotsGui;
import net.lopymine.specificslots.gui.screen.SpecificScreen;
import net.lopymine.specificslots.gui.tooltip.WarningTooltipData;
import net.lopymine.specificslots.gui.widgets.vanilla.GhostItemsShowWidget;
import net.lopymine.specificslots.gui.widgets.vanilla.WarningWidget;
import net.lopymine.specificslots.handlers.DrawableHandler;
import net.lopymine.specificslots.textures.ShadowItems;
import net.lopymine.specificslots.utils.ItemUtils;
import net.lopymine.specificslots.utils.mixins.ISpecificHandledScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(HandledScreen.class)
public abstract class HandledScreenMixin<T extends ScreenHandler> extends Screen implements ISpecificHandledScreen {
    @Shadow
    protected int x;
    @Shadow
    protected int y;
    @Shadow
    @Final
    protected T handler;

    @Shadow
    protected abstract void init();

    @Shadow
    protected int backgroundHeight;
    @Shadow
    protected int backgroundWidth;
    @Unique
    private final SpecificConfig config = SpecificConfigManager.getConfig();
    @Unique
    private InventoryConfig inventoryConfig = InventoryConfigManager.readFromConfig(config);
    @Unique
    private final WarningWidget warningWidget = new WarningWidget(0, 0);
    @Unique
    private final ButtonWidget configButton = ButtonWidget.builder(
            Text.of("Specific Slots"),
            (button1 -> MinecraftClient.getInstance().setScreen(new SpecificScreen(new SpecificSlotsGui(inventoryConfig, this)))
            )).dimensions(5, this.height - 5, 80, 20).build();
    @Unique
    private final GhostItemsShowWidget showWidget = new GhostItemsShowWidget(0, 0);
    @Unique
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
        this.addDrawableChild(showWidget);
        if (config.enableHighlightWrongSlots) this.addDrawableChild(warningWidget);
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;render(Lnet/minecraft/client/gui/DrawContext;IIF)V"))
    private void render(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        this.warningWidget.setPosition(this.x + this.backgroundWidth + 5, this.y + this.backgroundHeight - 27);
        if (drawableHandler == null) return;
        if (drawableHandler.shouldDrawInventory()) this.drawInventory(context);
        if (drawableHandler.shouldDrawHotBar()) this.drawHotBar(context);
    }

    @Unique
    public void drawInventory(DrawContext context) {
        int k = 0;
        int p = drawableHandler.getInventoryX() + this.x;
        int l = drawableHandler.getInventoryY() + this.y;
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++) {
                int yy = y * 18;
                int xx = x * 18;
                this.drawInventory(context, xx + p, yy + l, k);
                k++;
            }
        }
    }

    @Unique
    public void drawHotBar(DrawContext context) {
        int k = 0;
        int p = drawableHandler.getHotBarX() + this.x;
        int l = drawableHandler.getHotBarY() + this.y;

        for (int x = 0; x < 9; x++) {
            int xx = x * 18;
            this.drawHotBar(context, xx + p, l, k);
            k++;
        }
    }

    @Unique
    private void drawInventory(DrawContext context, int x, int y, int slot) {
        if (slot >= inventoryConfig.getInventory().size()) {
            return;
        }

        Item item = ItemUtils.getItemByName(inventoryConfig.getInventory().get(slot));

        SlotInfoImpl slotInfo = getSlotInfo(false, item, slot);
        if (slotInfo == null) return;

        if (slotInfo.isWrong() && config.enableHighlightWrongSlots) {
            WarningTooltipData data = new WarningTooltipData(slotInfo.getConfigItem().getDefaultStack(), slotInfo.getInventoryItem().getDefaultStack(), slot);
            if (!warningWidget.data.contains(data)) warningWidget.data.add(data);
            context.fill(x, y, x + 16, y + 16, config.getColor());
        }
        if (!config.renderSlotWithItem && slotInfo.hasInventoryItem()) return;

        if (showWidget.isHovered()) {
            context.drawItem(item.getDefaultStack(), x, y);
        } else {
            Identifier texture = ShadowItems.getTexture(item);
            if (texture == null) return;
            for (int i = 0; i < config.depth; i++) {
                ScreenDrawing.texturedRect(context, x, y, 16, 16, texture, 0xFFFFFFFF);
            }
        }
    }

    @Unique
    private void drawHotBar(DrawContext context, int x, int y, int slot) {
        if (slot >= inventoryConfig.getHotBar().size()) {
            return;
        }

        Item item = ItemUtils.getItemByName(inventoryConfig.getHotBar().get(slot));

        SlotInfoImpl slotInfo = getSlotInfo(true, item, slot);
        if (slotInfo == null) return;

        if (slotInfo.isWrong() && config.enableHighlightWrongSlots) {
            WarningTooltipData data = new WarningTooltipData(slotInfo.getConfigItem().getDefaultStack(), slotInfo.getInventoryItem().getDefaultStack(), slot + 27);
            if (!warningWidget.data.contains(data)) warningWidget.data.add(data);
            context.fill(x, y, x + 16, y + 16, config.getColor());
        }
        if (!config.renderSlotWithItem && slotInfo.hasInventoryItem()) return;

        if (showWidget.isHovered()) {
            context.drawItem(item.getDefaultStack(), x, y);
        } else {
            Identifier texture = ShadowItems.getTexture(item);
            if (texture == null) return;
            for (int i = 0; i < config.depth; i++) {
                ScreenDrawing.texturedRect(context, x, y, 16, 16, texture, 0xFFFFFFFF);
            }
        }
    }

    @Unique
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

    @Override
    public InventoryConfig getInventoryConfig() {
        return inventoryConfig;
    }

    @Override
    public void setInventoryConfig(InventoryConfig inventoryConfig) {
        this.inventoryConfig = inventoryConfig;
    }
}
