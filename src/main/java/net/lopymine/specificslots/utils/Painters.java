package net.lopymine.specificslots.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import me.shedaniel.math.Color;
import net.lopymine.specificslots.autosort.SlotInfoImpl;
import net.lopymine.specificslots.config.SpecificConfig;
import net.lopymine.specificslots.gui.tooltip.WarningTooltipData;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.tooltip.BundleTooltipComponent;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import io.github.cottonmc.cotton.gui.client.*;

import net.lopymine.specificslots.SpecificSlots;

import static io.github.cottonmc.cotton.gui.client.BackgroundPainter.createNinePatch;

public class Painters {

    public static final BackgroundPainter CONFIG_BACKGROUND_PAINTER = BackgroundPainter.createLightDarkVariants(
            createNinePatch(new Identifier(SpecificSlots.ID, "textures/gui/background_painters/list_background.png")),
            createNinePatch(new Identifier(SpecificSlots.ID, "textures/gui/background_painters/list_background_dark.png"))
    );
    public static final Identifier DARK_PLAYER_BACKGROUND = new Identifier(SpecificSlots.ID(),"textures/gui/inventory/inventory_dark.png");
    public static final Identifier DARK_SLOT = new Identifier(SpecificSlots.ID, "textures/gui/slot/bundle_dark.png");
    public static final Identifier selectedSlot = new Identifier(SpecificSlots.ID, "textures/gui/slot/slot_select.png");
    public static final Identifier IMPORT_INVENTORY = new Identifier(SpecificSlots.ID, "textures/gui/buttons/import_inventory.png");
    public static final Identifier CLEAR_CONFIG = new Identifier(SpecificSlots.ID, "textures/gui/buttons/clear_config.png");
    public static final Identifier CLEAR_CONFIG_DARK = new Identifier(SpecificSlots.ID, "textures/gui/buttons/clear_config_dark.png");
    public static final Identifier PIN = new Identifier(SpecificSlots.ID(), "textures/gui/buttons/pin.png");
    public static final Identifier PINNED = new Identifier(SpecificSlots.ID(), "textures/gui/buttons/pinned.png");
    public static void drawSlot(DrawContext context, int x, int y, boolean isDarkMode) {
        float px = 1 / 128f;

        float buttonLeft = 0 * px;
        float buttonTop = 0 * px;
        float buttonWidth = 18 * px;
        float buttonHeight = 18 * px;
        ScreenDrawing.texturedRect(context, x, y, 18, 18, getTexture(DARK_SLOT, BundleTooltipComponent.TEXTURE, isDarkMode), buttonLeft, buttonTop, buttonLeft + buttonWidth, buttonTop + buttonHeight, 0xFFFFFFFF);
    }

    public static void drawPlayerBackground(DrawContext context, int x, int y, boolean isDarkMode) {
        int h = 7;
        int d = 25;

        float px = 1 / 256f;

        float buttonLeft = d * px;
        float buttonTop = h * px;
        float buttonWidth = 51 * px;
        float buttonHeight = 72 * px;
        ScreenDrawing.texturedRect(context, x, y, 51, 72, getTexture(DARK_PLAYER_BACKGROUND, InventoryScreen.BACKGROUND_TEXTURE, isDarkMode), buttonLeft, buttonTop, buttonLeft + buttonWidth, buttonTop + buttonHeight, 0xFFFFFFFF);
    }

    public static Identifier getTexture(Identifier v1, Identifier v2, boolean bl) {
        return bl ? v1 : v2;
    }

    public static void highlightSlot(DrawContext context, SlotInfoImpl slotInfo, int x, int y, SpecificConfig config) {
        if (!slotInfo.hasInventoryItem() && slotInfo.hasConfigItem()) {
//            RenderSystem.enableDepthTest();
            float[] shaderColor = RenderSystem.getShaderColor();
            float[] temp = new float[]{shaderColor[0], shaderColor[1], shaderColor[2], shaderColor[3]};
            Color color = Color.ofTransparent(config.ghostItemsColor);
            context.setShaderColor(((float)color.getRed()) / 255F, ((float)color.getGreen()) / 255F, ((float)color.getBlue()) / 255F, (float)config.ghostItemsAlpha / 100);
//            context.setShaderColor(0.5F,0.5F,0.5F,0.25F);
            context.drawItem(slotInfo.getConfigItem().getDefaultStack(), x, y);
            context.setShaderColor(temp[0], temp[1], temp[2], temp[3]);
            if (config.enableHighlightEmptySlots) {
                context.fill(x, y, x + 16, y + 16, config.getEmptyHighlightColor());
            }
//            context.drawItem(configItem.getDefaultStack(), x, y, 0 , 0);
//            context.fill(x, y, x + 16, y + 16, 10, config.getEmptyHighlightColor());
        } else if (slotInfo.isWrong() && config.enableHighlightWrongSlots) {
            context.fill(x, y, x + 16, y + 16, config.getWrongHighlightColor());
        } else if (slotInfo.getConfigItem() == slotInfo.getInventoryItem() && slotInfo.getConfigItem() != Items.AIR && config.enableHighlightRightSlots) {
            context.fill(x, y, x + 16, y + 16, config.getRightHighlightColor());
        }
    }
}
