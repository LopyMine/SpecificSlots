package net.lopymine.specificslots.autosort;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;

import static net.lopymine.specificslots.SpecificSlots.logger;

public class SwapManagerImpl implements ISwapManager {
    private final ClientPlayerInteractionManager interactionManager;
    private final ScreenHandler handler;
    private final PlayerEntity player;
    private int startIndex = 9;

    public SwapManagerImpl(ClientPlayerInteractionManager interactionManager, ScreenHandler handled, PlayerEntity player) {
        this.interactionManager = interactionManager;
        this.handler = handled;
        this.player = player;
    }

    public void swap(int one, int two) {
        if (one == two) return;
        click(one);
        click(two);
        click(one);
    }

    public void click(int index) {
        if (interactionManager == null) return;
        if (player == null) return;
        interactionManager.clickSlot(handler.syncId, index + startIndex, 0, SlotActionType.PICKUP, player);
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }
}
