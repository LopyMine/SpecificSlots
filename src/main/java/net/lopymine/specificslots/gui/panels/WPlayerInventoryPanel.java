package net.lopymine.specificslots.gui.panels;

import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.widget.TooltipBuilder;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WPanelWithInsets;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import io.github.cottonmc.cotton.gui.widget.icon.TextureIcon;
import net.lopymine.specificslots.config.defalt.DefaultSpecificConfig;
import net.lopymine.specificslots.config.SpecificConfig;
import net.lopymine.specificslots.gui.screen.SpecificScreen;
import net.lopymine.specificslots.utils.DrawUtils;
import net.lopymine.specificslots.gui.widgets.WGhostItemsShow;
import net.lopymine.specificslots.gui.config.LoadConfigGui;
import net.lopymine.specificslots.gui.config.SaveConfigGui;
import net.lopymine.specificslots.gui.widgets.WSlot;
import net.lopymine.specificslots.textures.GhostItems;
import net.lopymine.specificslots.utils.ItemUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class WPlayerInventoryPanel extends WPanelWithInsets {
    public static final Identifier HELMET = new Identifier("minecraft", "textures/item/empty_armor_slot_helmet.png");
    public static final Identifier CHESTPLATE = new Identifier("minecraft", "textures/item/empty_armor_slot_chestplate.png");
    public static final Identifier LEGS = new Identifier("minecraft", "textures/item/empty_armor_slot_leggings.png");
    public static final Identifier BOOTS = new Identifier("minecraft", "textures/item/empty_armor_slot_boots.png");
    public static final Identifier OFFHAND = new Identifier("minecraft", "textures/item/empty_armor_slot_shield.png");
    private static final List<Identifier> list = List.of(HELMET, CHESTPLATE, LEGS, BOOTS, OFFHAND);
    private static final MinecraftClient mc = MinecraftClient.getInstance();
    private final DefaultSpecificConfig defaultConfig;
    private ArrayList<WSlot> inventory;
    private ArrayList<WSlot> hotBar;
    private final Set<WSlot> selectedSlots = new HashSet<>();
    private final Set<WSlot> focusedSlots = new HashSet<>();
    private final Screen parent;
    private final WGhostItemsShow showWidget;

    public WPlayerInventoryPanel(SpecificConfig config, Screen parent, WGhostItemsShow showWidget, DefaultSpecificConfig defaultConfig) {
        this.defaultConfig = defaultConfig;
        this.parent = parent;
        this.showWidget = showWidget;
        this.inventory = createSlotButtonsByStringList(config.getInventory(), 0);
        createInventorySlots();
        this.hotBar = createSlotButtonsByStringList(config.getHotBar(), 27);
        createHotBarSlots();
        createWidgets();

    }

    @Override
    public void setSize(int x, int y) {
        super.setSize(176, 166);
    }

    @Override
    public void paint(MatrixStack matrices, int x, int y, int mouseX, int mouseY) {
        BackgroundPainter.VANILLA.paintBackground(matrices, x, y, this);

        DrawUtils.drawPlayerBackground(matrices, x, y, shouldRenderInDarkMode());

        for (WWidget child : children) {
            child.paint(matrices, x + child.getX(), y + child.getY(), mouseX - child.getX(), mouseY - child.getY());
        }

        int i = 51;
        int l = 75;

        if (mc.player != null)
            InventoryScreen.drawEntity(matrices, x + i, y + l, 30, (float) -mouseX + 50, (float) -mouseY + 25, mc.player);

        if (host == null) return;

        WWidget focus = host.getFocus();

        if(focus != null){
            if(focus instanceof WSlot slot){
                this.addFocusedSlot(slot);
                slot.setToggle(true);
            }
        }

    }

    @Override
    public WPlayerInventoryPanel setInsets(Insets insets) {
        super.setInsets(insets);
        return this;
    }

    private ArrayList<WSlot> createSlotButtonsByStringList(List<String> inventory, int startIndex) {
        ArrayList<WSlot> list = new ArrayList<>();

        if (inventory == null) return list;

        for (int i = 0; i < inventory.size(); i++) {
            Item item = ItemUtils.getItemByName(inventory.get(i));
            WSlot slot = new WSlot(startIndex + i).setItem(item).setTexture(GhostItems.getTexture(item));
            slot.setDepth(defaultConfig.depth);
            slot.setShowWidget(showWidget);
            list.add(slot);
        }
        return list;
    }

    private ArrayList<WSlot> createSlotButtonsByItemList(List<Item> inventory, int startIndex) {
        ArrayList<WSlot> list = new ArrayList<>();

        if (inventory == null) return list;

        for (int i = 0; i < inventory.size(); i++) {
            Item item = inventory.get(i);
            WSlot slot = new WSlot(startIndex + i).setItem(item).setTexture(GhostItems.getTexture(item));
            slot.setDepth(defaultConfig.depth);
            slot.setShowWidget(showWidget);
            list.add(slot);
        }
        return list;
    }

    private void createHotBarSlots() {
        if (hotBar.size() > 9) hotBar = new ArrayList<>(hotBar.subList(0, 9));
        int x = 7;
        int y = 141;
        for (WSlot slot : hotBar) {
            add(setClickAction(slot), x, y);
            x += 18;
        }
    }

    private void createInventorySlots() {
        if (inventory.size() > 27) inventory = new ArrayList<>(inventory.subList(0, 27));
        int x = 7;
        int y = 83;
        for (WSlot slot : inventory) {
            add(setClickAction(slot), x, y);
            x += 18;
            if (x > 151) {
                y += 18;
                x = 7;
            }
        }
    }

    private WSlot setClickAction(WSlot slot) {
        return slot.setOnToggle((on) -> {
            if (on) {
                this.addSelectedSlot(slot);
            } else {
                this.selectedSlots.remove(slot);
            }
            slot.setToggle(on);
        });
    }

    private void createWidgets() {
        WButton saveConfig = new WButton(Text.translatable("specific_slots.buttons.save")) {
            @Override
            public boolean shouldRenderInDarkMode() {
                return WPlayerInventoryPanel.this.shouldRenderInDarkMode();
            }
        };
        saveConfig.setSize(85, 20);
        saveConfig.setOnClick(() -> {
            mc.setScreen(new SpecificScreen(new SaveConfigGui(mc.currentScreen, inventory, hotBar, defaultConfig)));
        });
        add(saveConfig, 82, 35);
//
        WButton loadConfig = new WButton(Text.translatable("specific_slots.buttons.load")) {
            @Override
            public boolean shouldRenderInDarkMode() {
                return WPlayerInventoryPanel.this.shouldRenderInDarkMode();
            }
        };
        loadConfig.setSize(85, 20);
        loadConfig.setOnClick(() -> {
            mc.setScreen(new SpecificScreen(new LoadConfigGui(mc.currentScreen, parent, defaultConfig)));
        });
        add(loadConfig, 82, 7);
//
        WButton importInventory = new WButton(new TextureIcon(DrawUtils.importInventory)) {
            @Override
            public boolean shouldRenderInDarkMode() {
                return WPlayerInventoryPanel.this.shouldRenderInDarkMode();
            }

            @Override
            public void addTooltip(TooltipBuilder tooltip) {
                tooltip.add(Text.translatable("specific_slots.buttons.load_from_inventory"));
            }
        };
        importInventory.setSize(20, 20);
        importInventory.setOnClick(() -> {
            clearSelecting();
            this.selectedSlots.clear();
            this.focusedSlots.clear();
            clearChildren();
            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            if (player == null) return;
            List<Item> hotBarList = player.getInventory().main.subList(0, 9).stream().flatMap(itemStack -> Stream.of(itemStack.getItem())).toList();
            List<Item> inventoryList = player.getInventory().main.subList(9, 36).stream().flatMap(itemStack -> Stream.of(itemStack.getItem())).toList();
            this.inventory = createSlotButtonsByItemList(inventoryList, 0);
            createInventorySlots();
            this.hotBar = createSlotButtonsByItemList(hotBarList, 27);
            createHotBarSlots();
            createWidgets();
            if (this.host != null) this.validate(this.host);
        });
        this.add(importInventory, 100, 60);
//
        int y = 0;
        int x = 0;
        for (Identifier identifier : list) {
            WSlot slot = new WSlot(-1)
                    .setArmor(true)
                    .setTexture(identifier)
                    .setArmor(true)
                    .setItem(Items.AIR)
                    .setDepth(defaultConfig.depth)
                    .setShowWidget(this.showWidget);

            add(setClickAction(slot), 7 + x, 7 + y);
            y += 18;
            if (y >= 72) {
                x += 69;
                y -= 18;
            }
        }
    }

    public void add(WWidget w, int x, int y) {
        children.add(w);
        w.setParent(this);
        w.setLocation(insets.left() + x, insets.top() + y);

        expandToFit(w, insets);
    }

    private void clearSelecting() {
        for (WSlot slotButton : inventory) {
            slotButton.setToggle(false);
        }
        for (WSlot slotButton : hotBar) {
            slotButton.setToggle(false);
        }
    }

    private void clearChildren() {
        children.clear();
    }

    public Set<WSlot> getSelectSlots() {
        Set<WSlot> slots = focusedSlots;
        slots.addAll(selectedSlots);

        return slots;
    }

    public void addSelectedSlot(WSlot select) {
        if (!Screen.hasShiftDown()) {
            clearSelecting();
            selectedSlots.clear();
            focusedSlots.clear();
        }

        selectedSlots.add(select);
    }

    private void addFocusedSlot(WSlot slot) {
        if (!Screen.hasShiftDown()) {
            clearSelecting();
            selectedSlots.clear();
            focusedSlots.clear();
        }

        focusedSlots.add(slot);
    }

    public void removeSelect(WSlot select) {
        this.selectedSlots.remove(select);
        this.focusedSlots.remove(select);
    }
}
