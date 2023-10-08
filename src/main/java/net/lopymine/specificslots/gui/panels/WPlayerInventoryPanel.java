package net.lopymine.specificslots.gui.panels;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.*;
import net.minecraft.text.Text;
import net.minecraft.util.*;

import io.github.cottonmc.cotton.gui.GuiDescription;
import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.icon.TextureIcon;

import net.lopymine.specificslots.config.SpecificConfig;
import net.lopymine.specificslots.config.inventory.InventoryConfig;
import net.lopymine.specificslots.gui.config.*;
import net.lopymine.specificslots.gui.screen.SpecificScreen;
import net.lopymine.specificslots.gui.widgets.*;
import net.lopymine.specificslots.utils.*;

import java.util.*;
import java.util.stream.Stream;

public class WPlayerInventoryPanel extends WPlainPanel {
    private final MinecraftClient client = MinecraftClient.getInstance();
    private final SpecificConfig config;
    private final Set<WSlot> selectedSlots = new HashSet<>();
    private final Screen parent;
    private ArrayList<WSlot> inventory;
    private ArrayList<WSlot> hotBar;

    public WPlayerInventoryPanel(InventoryConfig inventoryConfig, Screen parent, SpecificConfig config) {
        this.config = config;
        this.parent = parent;

        this.inventory = createSlots(inventoryConfig.getInventory().stream().flatMap(s -> Stream.of(ItemUtils.getItemByName(s))).toList(), 0);
        this.createInventorySlots();

        this.hotBar = createSlots(inventoryConfig.getHotBar().stream().flatMap(s -> Stream.of(ItemUtils.getItemByName(s))).toList(), 27);
        this.createHotBarSlots();

        this.setBackgroundPainter(BackgroundPainter.VANILLA);
        this.createWidgets();
    }

    @Override
    public void setSize(int x, int y) {
        super.setSize(176, 166);
    }

    @Override
    public void paint(DrawContext context, int x, int y, int mouseX, int mouseY) {
        super.paint(context, x, y, mouseX, mouseY);
        int h = 7;
        int d = 25;

        Painters.drawPlayerBackground(context, x + d, y + h, shouldRenderInDarkMode());

        int i = 51;
        int l = 75;

        if (client.player != null)
            InventoryScreen.drawEntity(context, x + i, y + l, 30, (float) -mouseX + 50, (float) -mouseY + 25, client.player);

        if (host == null) return;

        WWidget focus = host.getFocus();

        if (focus != null) {
            if (focus instanceof WSlot slot) {
                this.addSelectedSlot(slot);
                slot.setToggle(true);
            }
        }
    }

    private ArrayList<WSlot> createSlots(List<Item> inventory, int startIndex) {
        ArrayList<WSlot> list = new ArrayList<>();

        if (inventory == null) return list;

        for (int i = 0; i < inventory.size(); i++) {
            Item item = inventory.get(i);
            WSlot slot = new WSlot(startIndex + i) {
                @Override
                public void resetSlot() {
                    super.resetSlot();

                    if(!Screen.hasShiftDown()) return;

                    Set<WSlot> slots = new HashSet<>(selectedSlots);

                    for (WSlot slot : slots) {
                        slot.setItem(Items.AIR).setToggle(false);

                        removeSelectedSlot(slot);
                    }
                }
            }.setItem(item).setDepth(config.depth);

            list.add(slot);
        }
        return list;
    }

    private void createHotBarSlots() {
        if (hotBar.size() > 9) hotBar = new ArrayList<>(hotBar.subList(0, 9));
        int x = 7;
        int y = 141;

        for (WSlot slot : hotBar) {
            this.add(setClickAction(slot), x, y);
            x += 18;
        }
    }

    private void createInventorySlots() {
        if (inventory.size() > 27) inventory = new ArrayList<>(inventory.subList(0, 27));
        int x = 7;
        int y = 83;

        for (WSlot slot : inventory) {
            this.add(setClickAction(slot), x, y);
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
                this.removeSelectedSlot(slot);
                if (Screen.hasShiftDown()) {
                    Set<WSlot> slots = new HashSet<>(selectedSlots);

                    for (WSlot wSlot : slots) {
                        wSlot.setToggle(false);

                        this.removeSelectedSlot(wSlot);
                    }
                }
            }
            slot.setToggle(on);
        });
    }

    private void createWidgets() {
        WButton saveConfig = new WButton(Text.translatable("specific_slots.buttons.save"))
                .setOnClick(() -> client.setScreen(new SpecificScreen(new SaveConfigGui(client.currentScreen, inventory, hotBar, config))));
        this.add(saveConfig, 82, 35, 85, 20);

        WButton loadConfig = new WButton(Text.translatable("specific_slots.buttons.load"))
                .setOnClick(() -> client.setScreen(new SpecificScreen(new LoadConfigGui(client.currentScreen, parent, config))));
        this.add(loadConfig, 82, 7, 85, 20);

        WButton importInventory = new WButton(new TextureIcon(Painters.IMPORT_INVENTORY)) {
            @Override
            public void addTooltip(TooltipBuilder tooltip) {
                tooltip.add(Text.translatable("specific_slots.buttons.load_from_inventory"));
            }
        }.setOnClick(() -> {
            clearSelecting();
            this.selectedSlots.clear();
            this.clearChildren();

            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            if (player == null) return;

            List<Item> hotBarList = player.getInventory().main.subList(0, 9).stream().flatMap(itemStack -> Stream.of(itemStack.getItem())).toList();
            List<Item> inventoryList = player.getInventory().main.subList(9, 36).stream().flatMap(itemStack -> Stream.of(itemStack.getItem())).toList();

            this.inventory = createSlots(inventoryList, 0);
            this.createInventorySlots();

            this.hotBar = createSlots(hotBarList, 27);
            this.createHotBarSlots();

            this.createWidgets();
            GuiDescription host = getHost();

            if (host != null) this.validate(host);
        });
        this.add(importInventory, 100, 60, 20, 20);


        TextureIcon icon = new TextureIcon(config.isDarkMode ? Painters.CLEAR_CONFIG_DARK : Painters.CLEAR_CONFIG);
        WButton clearConfig = new WButton(icon){
            @Override
            public void addTooltip(TooltipBuilder tooltip) {
                tooltip.add(Text.translatable("specific_slots.clear_config").formatted(Formatting.RED));
            }

        }.setOnClick(this::clearSlotsPanel);

        this.add(clearConfig, 125, 60, 20, 20);

        int y = 0;
        int x = 0;

        for (ArmorSlot.ArmorSlotType type : ArmorSlot.ArmorSlotType.values()) {

            WSlot slot = new WSlot(-1)
                    .setArmorType(type)
                    .setDepth(config.depth);
            this.add(setClickAction(slot), 7 + x, 7 + y);

            y += 18;
            if (y >= 72) {
                x += 69;
                y -= 18;
            }
        }
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

    public Set<WSlot> getSelectedSlots() {
        return selectedSlots;
    }

    private void addSelectedSlot(WSlot slot) {
        if (!Screen.hasShiftDown()) {
            clearSelecting();
            selectedSlots.clear();
        }

        selectedSlots.add(slot);
    }

    public void removeSelectedSlot(WSlot select) {
        this.selectedSlots.remove(select);
    }

    public void clearSlotsPanel() {
        clearSelecting();
        selectedSlots.clear();
        for (WSlot slot : inventory) {
            slot.resetSlot();
        }
        for (WSlot slot : hotBar) {
            slot.resetSlot();
        }
    }
}
