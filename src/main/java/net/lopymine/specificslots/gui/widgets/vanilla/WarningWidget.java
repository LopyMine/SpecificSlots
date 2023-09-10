package net.lopymine.specificslots.gui.widgets.vanilla;

import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import net.lopymine.specificslots.gui.tooltip.utils.TooltipComponentsData;
import net.lopymine.specificslots.gui.tooltip.WarningTooltipData;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WarningWidget extends ClickableWidget {
    private static final int width = 6;
    private static final int height = 22;
    public static final Identifier WARNING_BUTTON_TEXTURE = new Identifier("textures/gui/world_selection.png");

    public final ArrayList<WarningTooltipData> data = new ArrayList<>();
    public ArrayList<WarningTooltipData> subData = new ArrayList<>();

    public WarningWidget(int x, int y) {
        super(x, y, width, height, Text.of(""));
    }

    @Override
    public void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
        int d = 102;

        float px = 1 / 256f;

        float buttonLeft = d * px;
        float buttonTop = (data.isEmpty() ? 5 : 37) * px;
        float buttonWidth = width * px;
        float buttonHeight = height * px;
        ScreenDrawing.texturedRect(context, getX(), getY(), width, height, WARNING_BUTTON_TEXTURE, buttonLeft, buttonTop, buttonLeft + buttonWidth, buttonTop + buttonHeight, 0xFFFFFFFF);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (this.visible) {
            this.hovered = mouseX >= this.getX() && mouseY >= this.getY() && mouseX < this.getX() + width && mouseY < this.getY() + height;
            this.renderButton(context, mouseX, mouseY, delta);
            this.renderTooltip(context, mouseX, mouseY);
        }
        data.clear();
    }

    public void renderTooltip(DrawContext context, int x, int y) {

        if (!data.isEmpty() && hovered) {
            int endIndex = 5;
            if (endIndex > data.size()) endIndex = data.size();

            subData = new ArrayList<>(data.subList(0, endIndex));
            if (subData.isEmpty()) return;

            context.drawTooltip(MinecraftClient.getInstance().textRenderer, List.of(Text.translatable("specific_slots.mod_menu.wrong_slots").formatted(Formatting.RED).append(":")), Optional.of(new TooltipComponentsData(subData.stream().toList())), x, y);
        }
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
    }

    @Override
    public void setTooltip(@Nullable Tooltip tooltip) {
    }

    @Override
    public void playDownSound(SoundManager soundManager) {
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
    }
}

