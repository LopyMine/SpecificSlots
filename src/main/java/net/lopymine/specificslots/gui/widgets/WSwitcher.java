package net.lopymine.specificslots.gui.widgets;

import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import io.github.cottonmc.cotton.gui.widget.data.InputResult;
import net.lopymine.specificslots.SpecificSlots;
import net.lopymine.specificslots.utils.DrawUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class WSwitcher extends WWidget {
    public static final int switcherWidth = 13;
    public static final int switcherHeight = 22;
    public static final Identifier VANILLA_TEXTURE = new Identifier("textures/gui/resource_packs.png");
    public static final Identifier DARK_TEXTURE = new Identifier(SpecificSlots.ID(), "textures/gui/switchers/resource_packs_dark.png");
    private final Type type;
    private Runnable onClick;

    public WSwitcher(Type type) {
        this.type = type;
    }

    @Override
    public void setSize(int x, int y) {
        super.setSize(switcherWidth, switcherHeight);
    }

    @Override
    public boolean canResize() {
        return true;
    }

    @Override
    public boolean canFocus() {
        return true;
    }

    @Override
    public void paint(DrawContext context, int x, int y, int mouseX, int mouseY) {
        int i = 5;
        int d = 10;

        float px = 1 / 256f;

        float buttonLeft = (type == Type.RIGHT ? d : 34) * px;
        float buttonTop = (isHovered() || isFocused() ? 37 : i) * px;
        float buttonWidth = 14 * px;
        float buttonHeight = 22 * px;
        ScreenDrawing.texturedRect(context, x, y, 14, 22, DrawUtils.getTexture(DARK_TEXTURE, VANILLA_TEXTURE, shouldRenderInDarkMode()), buttonLeft, buttonTop, buttonLeft + buttonWidth, buttonTop + buttonHeight, 0xFFFFFFFF);
    }

    @Override
    public InputResult onClick(int x, int y, int button) {
        MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        if (onClick != null) onClick.run();
        return InputResult.PROCESSED;
    }

    public WSwitcher setOnClick(@Nullable Runnable onClick) {
        this.onClick = onClick;
        return this;
    }

    public enum Type {
        RIGHT,
        LEFT
    }
}
