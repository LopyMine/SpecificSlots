package net.lopymine.specificslots.gui.widgets.vanilla;

import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import net.lopymine.specificslots.SpecificSlots;
import net.lopymine.specificslots.autosort.SwapManagerImpl;
import net.lopymine.specificslots.config.SpecificConfig;
import net.lopymine.specificslots.utils.ItemUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Stream;

public class AutoSortWidget extends ButtonWidget {

    private final Identifier autoSort = new Identifier(SpecificSlots.ID, "textures/gui/buttons/autosort.png");
    private final Identifier autoSortPress = new Identifier(SpecificSlots.ID, "textures/gui/buttons/autosort_pressed.png");
    private final List<Item> playerItems = new ArrayList<>();
    private final Map<Item, List<Integer>> configMap;
    private final PlayerEntity player;
    private final SwapManagerImpl swapManager;
    private boolean isPress = false;
    private PlayerInventory inventory;

    public AutoSortWidget(int x, int y, Screen screen, SpecificConfig config, @NotNull PlayerEntity player) {
        super(x, y, 20, 20, Text.of(""), (button) -> {
        }, ButtonWidget.DEFAULT_NARRATION_SUPPLIER);
        this.setTooltip(Tooltip.of(Text.translatable("specific_slots.buttons.auto_sort")));

        this.configMap = getItems(config);
        this.player = player;

        ScreenHandler screenHandler = ((ScreenHandlerProvider<?>) screen).getScreenHandler();
        this.swapManager = new SwapManagerImpl(MinecraftClient.getInstance().interactionManager, screenHandler, player);
        updateInventory();
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.renderButton(matrices, mouseX, mouseY, delta);
        if (isPress) {
            if (isMouseOver(mouseX, mouseY))
                ScreenDrawing.texturedRect(matrices, getX() + 2, getY() + 2, 16, 16, autoSortPress, 0xFFFFFFFF);
            else isPress = false;
        }
        if (!isPress) ScreenDrawing.texturedRect(matrices, getX() + 2, getY() + 2, 16, 16, autoSort, 0xFFFFFFFF);
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        this.isPress = true;
        updateInventory();
        startSwapping();
    }

    private void startSwapping() {
        Map<Item, List<Integer>> map = new HashMap<>(configMap);

        int lastIndex = 0;
        int lastTo = 0;
//
        for (int i = 0; i < playerItems.size(); i++) {
            Item item = playerItems.get(i); // получает предмет по индексу
            if (item == Items.AIR) { // если воздух то пропускаем
                continue;
            }
//
            List<Integer> list = map.get(item);
            if (list == null) { // если предмета нету в конфиге то пропускаем
                continue;
            }
            if (list.size() == 0) { // если список пустой то пропускаем
                continue;
            }
            list = new LinkedList<>(list);
//
            if (list.contains(i)) { // представим что предмет лежит в 1 слоте, в списке есть такие позиции [1,2,6], тогда выходит что предмет уже лежит в одной из позиций списка, то есть на своём месте
                continue;
            }
//
            for (Integer toFromList : list) { // запускаем цикл для всех позиций предмета
                int to = toFromList;
                if (i == to) { // не даём пойти туда откуда и стоим
                    continue;
                }
//
                Item toItem = playerItems.get(to); // получаем предмет с инвентаря игрока с позиции
                if (toItem == item) { // если в этой позиции верный предмет то пропускаем
                    continue;
                }
                if (lastIndex == to && lastTo == i) { // проверяем меняли ли мы этот же предмет до этого, чтобы не отменить перемещение
                    continue;
                }
                // сохраняем текущую позицию перемещения для проверки выше
                lastIndex = i;
                lastTo = to;
                swapManager.swap(i, to);
//
                list.remove((Object) to); //
                map.replace(item, list);
                break;
            }
        }
    }

    //private void startSwapping() {
//
    //    List<Item> p_items = new ArrayList<>(player_items);
    //    List<Item> c_items = new ArrayList<>(config_items);
//
    //    for (int i = 0; i < 36; i++) {
    //        if(i >= p_items.size() || i >= c_items.size()) continue;
    //        Item item = p_items.get(i);
//
    //        if(item == Items.AIR) continue;
    //        if(!c_items.contains(item)) continue;
//
    //        int k = c_items.indexOf(item);
    //        int d = c_items.lastIndexOf(item);
    //        boolean bl = k == d;
//
    //        int g = p_items.indexOf(item);
    //        int h = p_items.lastIndexOf(item);
    //        boolean bl1 = g == h;
//
    //        if(!bl1 && !bl){
    //            for (int j = g; j < h; j++) {
    //                if(p_items.get(j) == item){
    //                    for (int l = g; l < h; l++) {
    //                        if(c_items.get(l) == item){
    //                             swapSlots(j,l);
    //                             p_items.set(j,Items.AIR);
    //                             c_items.set(l,Items.AIR);
    //                        }
    //                    }
    //                }
    //            }
    //        }
    //        if(bl1) swapSlots(i,k);
//
    //        updateInventory();
    //    }
    //}

    private void updateInventory() {
        PlayerInventory inventory = player.getInventory();
        this.inventory = inventory;
        playerItems.clear();
        this.playerItems.addAll(inventory.main.subList(9, 36).stream().flatMap(stack -> Stream.of(stack.getItem())).toList());
        this.playerItems.addAll(inventory.main.subList(0, 9).stream().flatMap(stack -> Stream.of(stack.getItem())).toList());
    }

    private Map<Item, List<Integer>> getItems(SpecificConfig config) {
        Map<Item, List<Integer>> map = new HashMap<>();
        List<Item> items = ItemUtils.getItemsFromConfig(config);

        for (int i = 0; i < items.size(); i++) {
            List<Integer> list = new ArrayList<>();
            Item item = items.get(i);

            for (int j = 0; j < items.size(); j++) {
                Item it = items.get(j);

                if (item == it){
                    list.add(j);
                }
            }

            map.put(item, list);
        }

        return map;
    }

}
