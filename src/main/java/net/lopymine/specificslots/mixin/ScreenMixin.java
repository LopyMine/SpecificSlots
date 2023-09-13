package net.lopymine.specificslots.mixin;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipData;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.lopymine.specificslots.utils.mixins.IWarningScreen;

import java.util.*;

@Mixin(Screen.class)
public class ScreenMixin implements IWarningScreen {

    @Shadow protected TextRenderer textRenderer;
    @Unique
    private TooltipData  data;

    @Inject(at = @At(value = "RETURN"), method = "renderWithTooltip")
    private void init(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if(this.data != null){
            context.drawTooltip(this.textRenderer, List.of(Text.translatable("specific_slots.mod_menu.wrong_slots").formatted(Formatting.RED).append(":")), Optional.of(data), mouseX, mouseY);
            this.data = null;
        }
    }

    @Override
    public void setTooltipData(TooltipData data) {
        this.data = data;
    }
}
