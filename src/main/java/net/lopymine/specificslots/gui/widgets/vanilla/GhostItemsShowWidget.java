package net.lopymine.specificslots.gui.widgets.vanilla;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.text.Text;

import io.github.cottonmc.cotton.gui.client.ScreenDrawing;

import net.lopymine.specificslots.utils.Painters;

public class GhostItemsShowWidget extends ButtonWidget {

    public GhostItemsShowWidget(int x, int y) {
        super(x, y, 20, 20, Text.of(""), (button) -> {
        }, ButtonWidget.DEFAULT_NARRATION_SUPPLIER);
    }

    @Override
    public void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
        super.renderButton(context, mouseX, mouseY, delta);

        if (isHovered())
            ScreenDrawing.texturedRect(context, getX() + 2, getY() + 2, 16, 16, Painters.SHOW_ITEMS_FOCUS, 0xFFFFFFFF);
        else ScreenDrawing.texturedRect(context, getX() + 2, getY() + 2, 16, 16, Painters.SHOW_ITEMS, 0xFFFFFFFF);
    }

    @Override
    public void onClick(double mouseX, double mouseY) {

    }

    @Override
    public void playDownSound(SoundManager soundManager) {

    }
}
