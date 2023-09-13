package net.lopymine.specificslots.utils.mixins;

import net.minecraft.screen.slot.*;

import net.lopymine.specificslots.modmenu.enums.SortMode;

public interface IShiftClickableScreen {
    boolean shiftClick(Slot slot, int slotId, int button, SlotActionType actionType, boolean isContainer);

    void setSortMode(SortMode sortMode);
}
