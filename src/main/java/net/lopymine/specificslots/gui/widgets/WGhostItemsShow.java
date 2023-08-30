package net.lopymine.specificslots.gui.widgets;

import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.data.InputResult;
import io.github.cottonmc.cotton.gui.widget.icon.TextureIcon;
import net.lopymine.specificslots.utils.DrawUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.sound.SoundEvents;

public class WGhostItemsShow extends WButton {
    private boolean isOn = false;

    public WGhostItemsShow() {
        super(new TextureIcon(DrawUtils.showItems));
        this.setSize(20, 20);
    }

    @Override
    public void paint(MatrixStack matrices, int x, int y, int mouseX, int mouseY) {
        super.paint(matrices, x, y, mouseX, mouseY);
        if (this.isOn) setHovered(true);
        if (isHovered() || isFocused()) {
            this.setIcon(new TextureIcon(DrawUtils.showItemsFocus));
        } else {
            this.setIcon(new TextureIcon(DrawUtils.showItems));
        }

        ScreenDrawing.texturedRect(matrices, x + 15, y - 4, 9, 9, (isOn ? DrawUtils.lock : DrawUtils.unlock), 0xFFFFFFFF);
    }

    @Override
    public InputResult onClick(int x, int y, int button) {
        MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        this.isOn = !this.isOn;
        return InputResult.PROCESSED;
    }
}
