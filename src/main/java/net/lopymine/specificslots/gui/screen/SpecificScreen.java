package net.lopymine.specificslots.gui.screen;

import io.github.cottonmc.cotton.gui.GuiDescription;
import io.github.cottonmc.cotton.gui.client.CottonClientScreen;
import io.github.cottonmc.cotton.gui.widget.data.InputResult;

public class SpecificScreen extends CottonClientScreen {
    public SpecificScreen(GuiDescription description) {
        super(description);
    }

    @Override
    public boolean keyPressed(int ch, int keyCode, int modifiers) {
        if(description.getRootPanel().onKeyPressed(ch,keyCode,modifiers) == InputResult.PROCESSED) return true;
        return super.keyPressed(ch, keyCode, modifiers);
    }
}
