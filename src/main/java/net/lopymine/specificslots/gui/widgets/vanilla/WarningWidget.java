package net.lopymine.specificslots.gui.widgets.vanilla;

import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import net.lopymine.specificslots.gui.tooltip.TooltipComponentsData;
import net.lopymine.specificslots.gui.tooltip.WarningTooltipData;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.sound.SoundEvents;
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
    private boolean dragging = false;
    private final Identifier WARNING_BUTTON_TEXTURE = new Identifier("textures/gui/world_selection.png");

    public final ArrayList<WarningTooltipData> data = new ArrayList<>();
    public ArrayList<WarningTooltipData> subData = new ArrayList<>();
    private final ReleaseDragging releaseDragging;

    public WarningWidget(int x, int y, ReleaseDragging releaseDragging) {
        super(x, y, width, height, Text.of(""));
        this.releaseDragging = releaseDragging;
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        int i = 5;
        int d = 102;

        float px = 1 / 256f;

        float buttonLeft = d * px;
        float buttonTop = (data.isEmpty() ? i : 37) * px;
        float buttonWidth = width * px;
        float buttonHeight = height * px;
        ScreenDrawing.texturedRect(matrices, getX(), getY(), width, height, WARNING_BUTTON_TEXTURE, buttonLeft, buttonTop, buttonLeft + buttonWidth, buttonTop + buttonHeight, 0xFFFFFFFF);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (this.visible) {
            this.hovered = mouseX >= this.getX() && mouseY >= this.getY() && mouseX < this.getX() + width && mouseY < this.getY() + height;
            this.renderButton(matrices, mouseX, mouseY, delta);
            if(dragging){
                this.setPosition(mouseX,mouseY);
                return;
            }
            this.renderTooltip(matrices, mouseX, mouseY);
        }
        data.clear();
    }

    public void renderTooltip(MatrixStack matrices, int x, int y) {
        if (!data.isEmpty() && hovered) {
            int endIndex = 5;
            if (endIndex > data.size()) {
                endIndex = data.size();
            }

            subData = new ArrayList<>(data.subList(0, endIndex));
            if (subData.isEmpty()) return;
            Screen screen = MinecraftClient.getInstance().currentScreen;
            if (screen != null) {

                screen.renderTooltip(matrices, List.of(Text.translatable("specific_slots.mod_menu.wrong_slots").formatted(Formatting.RED).append(":")), Optional.of(new TooltipComponentsData(subData.stream().toList())), x, y);
            }
        }
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

    //@Override
    //protected void onDrag(double mouseX, double mouseY, double deltaX, double deltaY) {
    //    this.dragging = true;
    //    super.onDrag(mouseX, mouseY, deltaX, deltaY);
    //}
//
    //@Override
    //public void onRelease(double mouseX, double mouseY) {
    //    this.dragging = false;
    //    MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    //    this.releaseDragging.release(this);
    //    super.onRelease(mouseX, mouseY);
    //}
//
    //@Override
    //public boolean mouseReleased(double mouseX, double mouseY, int button) {
    //    if(!dragging) return false;
    //    return super.mouseReleased(mouseX, mouseY, button);
    //}
}

