package net.lopymine.specificslots.gui.panels;

import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import io.github.cottonmc.cotton.gui.widget.data.VerticalAlignment;
import net.lopymine.specificslots.gui.widgets.WSimpleItemButton;
import net.lopymine.specificslots.gui.widgets.WSlot;
import net.lopymine.specificslots.gui.widgets.WSwitcher;
import net.lopymine.specificslots.textures.ShadowItems;
import net.minecraft.item.Item;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WItemsPanel extends WPlainPanel {
    private final int panelWidth;
    private final int panelHeight;
    private final int subPanelWidth;
    private final int subPanelHeight;
    private final int itemsCountY;
    private final int itemsCountX;
    private final int offsetX;
    private final int offsetY;
    private final int maxItemsPerPage;
    private final int pageCount;
    private int pageIndex = 0;
    private final WPlayerInventoryPanel playerPanel;
    private final WLabel label = new WLabel(Text.of(""));
    private final List<Item> items;

    public WItemsPanel(int screenWidth, int screenHeight, WPlayerInventoryPanel playerInventory, List<Item> items) {
        this.panelWidth = (screenWidth / 2) - (screenWidth / 4);
        this.panelHeight = screenHeight;
        this.playerPanel = playerInventory;
        this.items = items;

        this.subPanelWidth = panelWidth;
        this.subPanelHeight = panelHeight - 58;

        this.itemsCountY = subPanelHeight / 18;
        int k = 18 * itemsCountY;
        int d = subPanelHeight - k;
        this.offsetY = d / 2;

        this.itemsCountX = subPanelWidth / 18;
        int g = 18 * itemsCountX;
        int s = subPanelWidth - g;
        this.offsetX = s / 2;

        this.maxItemsPerPage = itemsCountX * itemsCountY;

        this.pageCount = items.size() / maxItemsPerPage;

        this.label.setText(Text.of("0/" + pageCount))
                .setVerticalAlignment(VerticalAlignment.CENTER)
                .setHorizontalAlignment(HorizontalAlignment.CENTER)
                .setColor(0xFFFFFFFF);

        this.createWidgets();
        this.createPage();
    }

    private void createWidgets() {
        int panel = panelWidth / 2;

        WTextField searchField = new WTextField(Text.translatable("specific_slots.search"))
                .setChangedListener(s -> {
                    clearItems();
                    createItemsPanel(getSearch(s, items));
                });

        this.add(searchField, (panelWidth / 2) - (panel / 2), 6, panel, 20);

        WSwitcher switcherRight = new WSwitcher(WSwitcher.Type.RIGHT)
                .setOnClick(this::nextPage);

        this.add(switcherRight, searchField.getWidth() + searchField.getX() + 5, 5);

        WSwitcher switcherLeft = new WSwitcher(WSwitcher.Type.LEFT)
                .setOnClick(this::backPage);

        this.add(switcherLeft, searchField.getX() - 5 - WSwitcher.switcherWidth, 5);

        this.add(label, 0, 28, panelWidth, 20);
    }

    private void createPage() {
        label.setText(Text.of(pageIndex + "/" + pageCount));
        int startIndex = maxItemsPerPage * pageIndex;

        int endIndex = startIndex + maxItemsPerPage;
        if (endIndex > items.size()) endIndex = items.size();

        this.clearItems();
        this.createItemsPanel(items.subList(startIndex, endIndex));
    }

    private void backPage() {
        this.pageIndex--;
        if (pageIndex == -1) pageIndex = pageCount;
        this.createPage();
    }

    private void nextPage() {
        this.pageIndex++;
        if (pageIndex > pageCount) pageIndex = 0;
        this.createPage();
    }

    private void clearItems() {
        List<WWidget> widgets = new ArrayList<>(children);

        for (WWidget widget : widgets) {
            if (widget instanceof WSimpleItemButton) {
                this.remove(widget);
            }
        }
    }

    private void createItemsPanel(List<Item> list) {
        int itemsX = 0;
        int itemsY = 0;

        for (Item item : list) {
            WSimpleItemButton itemButton = new WSimpleItemButton(item).setOnClick(() -> {
                Set<WSlot> select = new HashSet<>(playerPanel.getSelectedSlots());

                if (!select.isEmpty()) {
                    for (WSlot slot : select) {
                        if (slot.isArmor()) continue;

                        slot.setTexture(ShadowItems.getTexture(item))
                                .setItem(item)
                                .setToggle(false);

                        playerPanel.removeSelectedSlot(slot);
                    }
                }
            });

            this.add(itemButton, offsetX + (itemsX * 18), offsetY + (itemsY * 18) + 46);

            if (this.getHost() != null) itemButton.validate(this.getHost());
            itemsX++;

            if (itemsX >= itemsCountX) {
                itemsY++;
                itemsX = 0;
            }
        }
    }

    private List<Item> getSearch(String text, List<Item> items) {
        List<Item> itemSearch = new ArrayList<>();

        if (text.isEmpty()) {
            if (items.size() > maxItemsPerPage) items = items.subList(0, maxItemsPerPage);
            return items;
        }

        for (Item item : items) {
            if (item.getName().getString().toLowerCase().replaceAll("ё", "е").contains(text.toLowerCase().replaceAll("ё", "е"))) {
                itemSearch.add(item);
            }
        }

        if (itemSearch.size() > maxItemsPerPage) itemSearch = itemSearch.subList(0, maxItemsPerPage);
        return itemSearch;
    }

    public int getXPos(int screenWidth) {
        return screenWidth - getWidthPanel();
    }

    public int getYPos(int screenHeight) {
        return 0;
    }

    public int getWidthPanel() {
        return panelWidth;
    }

    public int getHeightPanel() {
        return panelHeight;
    }

}
