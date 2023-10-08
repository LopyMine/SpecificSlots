package net.lopymine.specificslots.mixin;

import net.lopymine.specificslots.SpecificSlots;
import net.lopymine.specificslots.autosort.SlotInfoImpl;
import net.lopymine.specificslots.autosort.SwapManagerImpl;
import net.lopymine.specificslots.config.SpecificConfig;
import net.lopymine.specificslots.config.inventory.InventoryConfig;
import net.lopymine.specificslots.gui.SpecificGui;
import net.lopymine.specificslots.gui.screen.SpecificScreen;
import net.lopymine.specificslots.gui.tooltip.WarningTooltipData;
import net.lopymine.specificslots.gui.widgets.vanilla.WarningWidget;
import net.lopymine.specificslots.handlers.HandledScreenHelper;
import net.lopymine.specificslots.modmenu.enums.SortMode;
import net.lopymine.specificslots.utils.ItemUtils;
import net.lopymine.specificslots.utils.mixins.IShiftClickableScreen;
import net.lopymine.specificslots.utils.mixins.ISpecificScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Mixin(HandledScreen.class)
public abstract class HandledScreenMixin<T extends ScreenHandler> extends Screen implements ISpecificScreen, IShiftClickableScreen {
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
    private final SpecificConfig config = SpecificSlots.config;
    @Unique
    private InventoryConfig inventoryConfig = SpecificSlots.inventoryConfig;
    @Unique
    private SortMode sortMode = SortMode.CONTAINER;
    @Unique
    private final WarningWidget warningWidget = new WarningWidget(0, 0);
    @Unique
    private final ButtonWidget configButton = ButtonWidget.builder(
            Text.of("Specific Slots"),
            (button1 -> MinecraftClient.getInstance().setScreen(new SpecificScreen(new SpecificGui(inventoryConfig, this)))
            )).dimensions(5, this.height - 5, 80, 20).build();
    @Unique
    private List<Item> configHotBar = inventoryConfig.getHotBar().stream().flatMap(id -> Stream.of(ItemUtils.getItemByName(id))).toList();
    @Unique
    private List<Item> configInventory = inventoryConfig.getInventory().stream().flatMap(id -> Stream.of(ItemUtils.getItemByName(id))).toList();
    @Unique
    private SwapManagerImpl swapManager = null;
    @Unique
    private HandledScreenHelper handledScreenHelper = null;

    protected HandledScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("RETURN"), method = "init")
    private void init(CallbackInfo ci) {
        Screen currentScreen = MinecraftClient.getInstance().currentScreen;

        handledScreenHelper = HandledScreenHelper.of(handler, currentScreen);
        swapManager = new SwapManagerImpl(MinecraftClient.getInstance().interactionManager, handler, MinecraftClient.getInstance().player);

        if (handledScreenHelper != null) {
            handledScreenHelper.updateSlotPos();
        }

        int y = this.height - 25;

        this.configButton.setPosition(5, y);
        this.warningWidget.setPosition(this.x + this.backgroundWidth + 5, this.y + this.backgroundHeight - 27);

        this.addDrawableChild(configButton);
        if (config.enableHighlightWrongSlots) {
            this.addDrawableChild(warningWidget);
        }
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;render(Lnet/minecraft/client/gui/DrawContext;IIF)V"))
    private void render(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        this.warningWidget.setPosition(this.x + this.backgroundWidth + 5, this.y + this.backgroundHeight - 27);
        if (handledScreenHelper == null) {
            return;
        }
        if (handledScreenHelper.shouldDrawInventory()) this.drawInventory(context);
        if (handledScreenHelper.shouldDrawHotBar()) this.drawHotBar(context);
    }

    @Unique
    public void drawInventory(DrawContext context) {
        int k = 0;
        int p = handledScreenHelper.getInventoryX() + this.x;
        int l = handledScreenHelper.getInventoryY() + this.y;
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++) {
                int yy = y * 18;
                int xx = x * 18;
                this.drawSlot(context, xx + p, yy + l, k, false);
                k++;
            }
        }
    }

    @Unique
    public void drawHotBar(DrawContext context) {
        int k = 0;
        int p = handledScreenHelper.getHotBarX() + this.x;
        int l = handledScreenHelper.getHotBarY() + this.y;

        for (int x = 0; x < 9; x++) {
            int xx = x * 18;
            this.drawSlot(context, xx + p, l, k, true);
            k++;
        }
    }

    @Unique
    private void drawSlot(DrawContext context, int x, int y, int slot, boolean isHotBar) {
        List<Item> configInv = isHotBar ? configHotBar : configInventory;
        if (slot >= configInv.size()) {
            return;
        }

        Item configItem = configInv.get(slot);
        SlotInfoImpl slotInfo = SlotInfoImpl.getSlotInfo(isHotBar, configItem, slot, MinecraftClient.getInstance().player);
        if (slotInfo == null) {
            return;
        }

        if (!slotInfo.hasInventoryItem() && slotInfo.hasConfigItem() && config.enableHighlightEmptySlots) {
            context.fill(x, y, x + 16, y + 16, config.getEmptyHighlightColor());
            context.drawItem(configItem.getDefaultStack(), x, y);
        } else if (slotInfo.isWrong() && config.enableHighlightWrongSlots) {
            WarningTooltipData data = new WarningTooltipData(slotInfo.getConfigItem().getDefaultStack(), slotInfo.getInventoryItem().getDefaultStack(), slot + (isHotBar ? 27 : 0));
            if (!warningWidget.data.contains(data)) {
                warningWidget.data.add(data);
            }
            context.fill(x, y, x + 16, y + 16, config.getWrongHighlightColor());
        } else if (slotInfo.getConfigItem() == slotInfo.getInventoryItem() && slotInfo.getConfigItem() != Items.AIR && config.enableHighlightRightSlots) {
            context.fill(x, y, x + 16, y + 16, config.getRightHighlightColor());
        }
    }

    @Override
    public InventoryConfig getInventoryConfig() {
        return inventoryConfig;
    }

    @Override
    public void setInventoryConfig(InventoryConfig inventoryConfig) {
        this.inventoryConfig = inventoryConfig;
        this.configHotBar = inventoryConfig.getHotBar().stream().flatMap(id -> Stream.of(ItemUtils.getItemByName(id))).toList();
        this.configInventory = inventoryConfig.getInventory().stream().flatMap(id -> Stream.of(ItemUtils.getItemByName(id))).toList();
    }

    @Override
    public void setSortMode(SortMode sortMode) {
        this.sortMode = sortMode;
    }

    @Inject(at = @At("HEAD"), method = "onMouseClick(Lnet/minecraft/screen/slot/Slot;IILnet/minecraft/screen/slot/SlotActionType;)V", cancellable = true)
    private void mouseClick(Slot slot, int slotId, int button, SlotActionType actionType, CallbackInfo ci) {
        if (shiftClick(slot, slot == null ? slotId : slot.getIndex(), button, actionType, true)) ci.cancel();
    }

    @Override
    public boolean shiftClick(Slot slot, int slotId, int button, SlotActionType actionType, boolean isContainer) {
        if (!config.enableSpecificShiftSort || (config.sortMode != SortMode.CONTAINER && sortMode != config.sortMode && config.sortMode != SortMode.ALL)) {
            return false;
        }

        if (button != 1 || actionType != SlotActionType.QUICK_MOVE || slot == null) {
            return false;
        }

        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) {
            return false;
        }

        PlayerInventory inventory = player.getInventory();
        ItemStack fromStack = slot.getStack();
        if (fromStack.isEmpty()) {
            return false;
        }

        LinkedList<Item> configInventory = new LinkedList<>(this.configInventory);
        configInventory.addAll(this.configHotBar);
        LinkedList<ItemStack> playerInventory = ItemUtils.transformMainInventory(inventory.main);
        boolean isSlotInOtherInventory = (slot.inventory != inventory && isContainer) || (!isContainer && slot.inventory == inventory);
        int startIndex = isContainer ? handler.slots.stream().filter(slot1 -> slot1.inventory != inventory).toList().size() : 9;

        if (!configInventory.contains(fromStack.getItem())) {
            if (isSlotInOtherInventory) {
                return sortableShiftClick(slot, slotId, actionType, isContainer, configInventory, playerInventory, startIndex);
            } else {
                return false;
            }
        }

        if (isContainer && slot.inventory == inventory) {
            slotId = (slotId > 8) ? (slotId - 9 + startIndex) : (slotId + 27 + startIndex);
        }

        for (int i = 0; i < configInventory.size(); i++) {
            Item item = configInventory.get(i);
            if (item == Items.AIR) {
                continue;
            }
            if (item != fromStack.getItem()) {
                continue;
            }
            if (i + startIndex == slotId) {
                return false;
            }

            ItemStack toStack = playerInventory.get(i);
            if (!toStack.isEmpty() && (!equalsNbt(fromStack, toStack) || toStack.getCount() == toStack.getMaxCount())){
                continue;
            }


            swapManager.swap(slotId, i + startIndex);
            return true;
        }

        if (isSlotInOtherInventory)
            return sortableShiftClick(slot, slotId, actionType, isContainer, configInventory, playerInventory, startIndex);

        return false;
    }

    public boolean equalsNbt(ItemStack stack, ItemStack otherStack) {
        boolean equals = Objects.equals(stack.getNbt(), otherStack.getNbt());
        System.out.println(equals);
        return equals;
    }

    @Unique
    private boolean sortableShiftClick(Slot slot, int slotId, SlotActionType actionType, boolean isContainer, LinkedList<Item> configInventory, LinkedList<ItemStack> playerInventory, int startIndex) {
        for (int i = playerInventory.size() - 1; i > 0; i--) {
            ItemStack playerItem = playerInventory.get(i);
            ItemStack configItem = configInventory.get(i).getDefaultStack();

            if (playerItem == slot.getStack()) {
                return false;
            }

            if (playerItem.isEmpty() && configItem.isEmpty() || (ItemStack.canCombine(playerItem, slot.getStack()) && playerItem.getCount() != playerItem.getMaxCount())) {
                swapManager.swap(slotId, i + startIndex);
                return true;
            }
        }

        return false;
    }
}