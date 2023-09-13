package net.lopymine.specificslots.autosort;

import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;

public class SwapManagerImpl implements ISwapManager {
    private final ClientPlayerInteractionManager interactionManager;
    private final ScreenHandler handler;
    private final PlayerEntity player;
    private int startIndex = 0;

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
        click(index, SlotActionType.PICKUP);
    }

    public void click(int index, SlotActionType actionType){
        if (interactionManager == null) return;
        if (player == null) return;
        if(this.handler instanceof CreativeInventoryScreen.CreativeScreenHandler) player.playerScreenHandler.onSlotClick(index + startIndex, 0, actionType, player);
        else interactionManager.clickSlot(handler.syncId, index + startIndex, 0, actionType, player);
    }

    public SwapManagerImpl setStartIndex(int startIndex) {
        this.startIndex = startIndex;
        return this;
    }

}
