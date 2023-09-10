package net.lopymine.specificslots.gui.panels.list;

import io.github.cottonmc.cotton.gui.widget.TooltipBuilder;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class WConfigListPanel extends WPlainPanel {
    public WButton button;
    public WButton remove;
    private String tooltip;

    public WConfigListPanel() {
        this.button = new WButton() {
            @Override
            public void addTooltip(TooltipBuilder tooltip) {
                tooltip.add(Text.of(WConfigListPanel.this.tooltip));
            }
        };
        this.add(button, 5, 0, 130, 20);

        this.remove = new WButton(Text.of("x")) {
            @Override
            public void addTooltip(TooltipBuilder tooltip) {
                tooltip.add(Text.translatable("specific_slots.delete_config").formatted(Formatting.RED));
            }
        };
        this.add(remove, 140, 0, 20, 20);
    }

    public void setButtonTooltip(String text) {
        this.tooltip = text;
    }
}
