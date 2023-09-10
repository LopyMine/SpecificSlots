package net.lopymine.specificslots.utils;

import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import net.lopymine.specificslots.SpecificSlots;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.tooltip.BundleTooltipComponent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static io.github.cottonmc.cotton.gui.client.BackgroundPainter.createNinePatch;

public class DrawUtils {
    public static BackgroundPainter listBackgroundPainter = BackgroundPainter.createLightDarkVariants(
            createNinePatch(new Identifier(SpecificSlots.ID, "textures/gui/background_painters/list_background.png")),
            createNinePatch(new Identifier(SpecificSlots.ID, "textures/gui/background_painters/list_background_dark.png"))
    );
    public static final Identifier darkPlayerBackground = new Identifier(SpecificSlots.ID(),"textures/gui/inventory/inventory_dark.png");
    public static Identifier darkSlot = new Identifier(SpecificSlots.ID, "textures/gui/slot/bundle_dark.png");
    public static Identifier selectedSlot = new Identifier(SpecificSlots.ID, "textures/gui/slot/slot_select.png");
    public static Identifier showItemsFocus = new Identifier(SpecificSlots.ID, "textures/gui/buttons/show_items_focus.png");
    public static Identifier showItems = new Identifier(SpecificSlots.ID, "textures/gui/buttons/show_items.png");
    public static final Identifier importInventory = new Identifier(SpecificSlots.ID, "textures/gui/buttons/import_inventory.png");
    public static Identifier lock = new Identifier(SpecificSlots.ID, "textures/gui/buttons/lock.png");
    public static Identifier unlock = new Identifier(SpecificSlots.ID, "textures/gui/buttons/unlock.png");

    public static void drawSlot(DrawContext context, int x, int y, boolean isDarkMode) {
        float px = 1 / 128f;

        float buttonLeft = 0 * px;
        float buttonTop = 0 * px;
        float buttonWidth = 18 * px;
        float buttonHeight = 18 * px;
        ScreenDrawing.texturedRect(context, x, y, 18, 18, getTexture(darkSlot, BundleTooltipComponent.TEXTURE, isDarkMode), buttonLeft, buttonTop, buttonLeft + buttonWidth, buttonTop + buttonHeight, 0xFFFFFFFF);
    }

    public static void drawPlayerBackground(DrawContext context, int x, int y, boolean isDarkMode) {
        int h = 7;
        int d = 25;

        float px = 1 / 256f;

        float buttonLeft = d * px;
        float buttonTop = h * px;
        float buttonWidth = 51 * px;
        float buttonHeight = 72 * px;
        ScreenDrawing.texturedRect(context, x, y, 51, 72, getTexture(darkPlayerBackground, InventoryScreen.BACKGROUND_TEXTURE, isDarkMode), buttonLeft, buttonTop, buttonLeft + buttonWidth, buttonTop + buttonHeight, 0xFFFFFFFF);
    }

    public static Text cutString(String text, int length) {
        if (text.length() <= length) {
            return Text.of(text);
        } else {
            String cutText = text.substring(0, length);
            if (cutText.endsWith(" ")) {
                cutText = cutText.substring(0, cutText.length() - 1);
            }
            return Text.of(cutText + "...");
        }
    }

    public static Identifier getTexture(Identifier v1, Identifier v2, boolean bool) {
        return bool ? v1 : v2;
    }

}
