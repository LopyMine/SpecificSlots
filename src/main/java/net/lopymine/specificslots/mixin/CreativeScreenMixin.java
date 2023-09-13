package net.lopymine.specificslots.mixin;

import net.minecraft.client.gui.screen.ingame.*;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen.CreativeScreenHandler;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemGroup;
import net.minecraft.screen.slot.*;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.lopymine.specificslots.modmenu.enums.SortMode;
import net.lopymine.specificslots.utils.mixins.IShiftClickableScreen;

@Mixin(CreativeInventoryScreen.class)
public abstract class CreativeScreenMixin extends AbstractInventoryScreen<CreativeScreenHandler> {

    @Shadow
    private static ItemGroup selectedTab;

    public CreativeScreenMixin(CreativeInventoryScreen.CreativeScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
        super(screenHandler, playerInventory, text);
    }

    @Inject(at = @At("HEAD"), method = "onMouseClick(Lnet/minecraft/screen/slot/Slot;IILnet/minecraft/screen/slot/SlotActionType;)V", cancellable = true)
    private void mouseClick(Slot slot, int slotId, int button, SlotActionType actionType, CallbackInfo ci) {
        if(selectedTab.getType() == ItemGroup.Type.INVENTORY && this instanceof IShiftClickableScreen shiftClickableScreen){
            shiftClickableScreen.setSortMode(SortMode.INVENTORY);
            if(shiftClickableScreen.shiftClick(slot, slot == null ? slotId : slot.getIndex(), button, actionType, false)) ci.cancel();
        }

    }
}