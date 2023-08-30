package net.lopymine.specificslots.gui.widgets.vanilla;

import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import net.lopymine.specificslots.utils.DrawUtils;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class GhostItemsShowWidget extends ButtonWidget {

    public GhostItemsShowWidget(int x, int y) {
        super(x, y, 20, 20, Text.of(""), (button) -> {
        }, ButtonWidget.DEFAULT_NARRATION_SUPPLIER);
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.renderButton(matrices, mouseX, mouseY, delta);

        if (isHovered()) ScreenDrawing.texturedRect(matrices, getX() + 2, getY() + 2, 16, 16, DrawUtils.showItemsFocus, 0xFFFFFFFF);
        else ScreenDrawing.texturedRect(matrices, getX() + 2, getY() + 2, 16, 16, DrawUtils.showItems, 0xFFFFFFFF);

    }

    @Override
    public void onClick(double mouseX, double mouseY) {

    }
}
