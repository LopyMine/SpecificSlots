package net.lopymine.specificslots.config.inventory;

import java.util.*;

public class InventoryConfig {
    private final String author;
    private final List<String> inventory;
    private final List<String> hotBar;
    private String name;
    public InventoryConfig(String author, List<String> inventory, List<String> hotBar, String name) {
        this.author = author;
        this.inventory = inventory;
        this.hotBar = hotBar;
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public List<String> getHotBar() {
        return hotBar;
    }

    public List<String> getInventory() {
        return inventory;
    }

    public String getName() {
        return name;
    }

    public InventoryConfig setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventoryConfig config = (InventoryConfig) o;

        return author.equals(config.author) && inventory.equals(config.inventory) && hotBar.equals(config.hotBar) && Objects.equals(name, config.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, inventory, hotBar, name);
    }
}
