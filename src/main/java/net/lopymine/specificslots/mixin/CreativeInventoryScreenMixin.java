package net.lopymine.specificslots.mixin;

import net.lopymine.specificslots.SpecificSlots;
import net.lopymine.specificslots.config.SpecificConfig;
import net.lopymine.specificslots.gui.widgets.vanilla.AutoSortWidget;
import net.lopymine.specificslots.gui.widgets.vanilla.WarningWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemGroup;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeInventoryScreen.class)
public abstract class CreativeInventoryScreenMixin extends AbstractInventoryScreen<CreativeInventoryScreen.CreativeScreenHandler> {
    @Shadow
    private static ItemGroup selectedTab;
    private final SpecificConfig config = SpecificSlots.config;
    private AutoSortWidget autoSort;

    public CreativeInventoryScreenMixin(CreativeInventoryScreen.CreativeScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
        super(screenHandler, playerInventory, text);
    }

    @Inject(at = @At("RETURN"), method = "init")
    private void init(CallbackInfo ci) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        int y = this.height - 25;

        if (player != null) {
            this.autoSort = new AutoSortWidget(109, y, this, config, player);
            this.addDrawableChild(autoSort);
        }

    }

    @Inject(at = @At("RETURN"), method = "render")
    private void render(CallbackInfo ci) {
        if (this.autoSort == null) return;

        this.autoSort.active = selectedTab.getType() == ItemGroup.Type.INVENTORY;
    }

}
