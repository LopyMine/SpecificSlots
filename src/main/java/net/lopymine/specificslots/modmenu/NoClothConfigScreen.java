package net.lopymine.specificslots.modmenu;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

import net.fabricmc.fabric.api.util.TriState;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.*;

public class NoClothConfigScreen extends LightweightGuiDescription {
    public NoClothConfigScreen(Screen screen) {
        WPlainPanel root = new WPlainPanel();
        root.setInsets(Insets.ROOT_PANEL);

        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.currentScreen == null) mc.setScreen(null);

        int w = mc.currentScreen.width;
        int h = mc.currentScreen.height;
        root.setSize(w, h);

        WLabel label = new WLabel(Text.translatable("specific_slots.no_cloth_config"))
                .setColor(0xFFFFFFFF)
                .setHorizontalAlignment(HorizontalAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.CENTER);

        root.add(label, 0, 0, w, h);

        WButton button = new WButton(ScreenTexts.BACK)
                .setOnClick(() -> mc.setScreen(screen));

        root.add(button, w - 63, 4, 60, 20);

        setRootPanel(root);
        root.validate(this);
    }

    @Override
    public void addPainters() {

    }

    @Override
    public TriState isDarkMode() {
        return TriState.TRUE;
        //return TriState.of(SpecificConfigManager.getDefaultConfig().isDarkMode);
    }
}
