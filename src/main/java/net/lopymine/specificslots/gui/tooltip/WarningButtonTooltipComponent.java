package net.lopymine.specificslots.gui.tooltip;

import net.lopymine.specificslots.utils.DrawUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.joml.Matrix4f;

public class WarningButtonTooltipComponent implements TooltipComponent {
    private final ItemStack requiredItem;
    private final ItemStack providedItem;
    private final int index;
    private final Text text1 = Text.translatable("specific_slots.warning_button_tooltip.text1");
    private final Text text2 = Text.translatable("specific_slots.warning_button_tooltip.text2");
    private final Text text3;
    private final int d;
    private final int l;
    private final int k;

    public WarningButtonTooltipComponent(ItemStack requiredItem, ItemStack providedItem, int index) {
        this.requiredItem = requiredItem;
        this.providedItem = providedItem;
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        this.text3 = Text.translatable("specific_slots.warning_button_tooltip.text3", index);
        this.d = textRenderer.getWidth(text1);
        this.l = textRenderer.getWidth(text2);
        this.k = textRenderer.getWidth(text3);
        this.index = index;
    }

    @Override
    public int getHeight() {
        return 19;
    }

    @Override
    public int getWidth(TextRenderer textRenderer) {
        return d + l + k + (18 * 2) + 2;
    }

    @Override
    public void drawText(TextRenderer textRenderer, int x, int y, Matrix4f matrix, VertexConsumerProvider.Immediate vertexConsumers) {
        textRenderer.draw(this.text1, (float) x, (float) y + 4, -1, true, matrix, vertexConsumers, TextRenderer.TextLayerType.NORMAL, 0, 15728880);
        textRenderer.draw(this.text2, (float) x + d + 18, (float) y + 4, -1, true, matrix, vertexConsumers, TextRenderer.TextLayerType.NORMAL, 0, 15728880);
        textRenderer.draw(this.text3, (float) x + d + l + (18 * 2), (float) y + 4, -1, true, matrix, vertexConsumers, TextRenderer.TextLayerType.NORMAL, 0, 15728880);
    }

    @Override
    public void drawItems(TextRenderer textRenderer, int x, int y, MatrixStack matrices, ItemRenderer itemRenderer) {
        matrices.push();
        matrices.translate(x, y - 1, 300);

        DrawUtils.drawSlot(matrices, d, 0,false);
        DrawUtils.drawSlot(matrices, d + l + 18, 0,false);
        matrices.translate(0, 0, 50);
        itemRenderer.renderInGui(matrices, requiredItem, d + 1, 1);
        itemRenderer.renderInGui(matrices, providedItem, d + l + 18 + 1, 1);
        matrices.pop();
    }

}
