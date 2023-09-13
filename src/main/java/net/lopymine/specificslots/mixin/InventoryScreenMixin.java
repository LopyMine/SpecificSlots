package net.lopymine.specificslots.mixin;

import net.minecraft.client.gui.screen.ingame.*;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.slot.*;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.lopymine.specificslots.modmenu.enums.SortMode;
import net.lopymine.specificslots.utils.mixins.IShiftClickableScreen;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends AbstractInventoryScreen<PlayerScreenHandler> implements RecipeBookProvider {

    public InventoryScreenMixin(PlayerScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
        super(screenHandler, playerInventory, text);
    }

    @Inject(at = @At("HEAD"), method = "onMouseClick(Lnet/minecraft/screen/slot/Slot;IILnet/minecraft/screen/slot/SlotActionType;)V", cancellable = true)
    private void mouseClick(Slot slot, int slotId, int button, SlotActionType actionType, CallbackInfo ci) {
        if(this instanceof IShiftClickableScreen shiftClickableScreen){
            shiftClickableScreen.setSortMode(SortMode.INVENTORY);
            if(shiftClickableScreen.shiftClick(slot, slotId, button, actionType, false)) ci.cancel();
        }
    }
}