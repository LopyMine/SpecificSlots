package net.lopymine.specificslots.config;

import java.util.List;
import java.util.Objects;

public class SpecificConfig {
    private final String author;
    private final List<String> inventory;
    private final List<String> hotBar;
    private String name;
    public SpecificConfig(String author, List<String> inventory, List<String> hotBar) {
        this.author = author;
        this.inventory = inventory;
        this.hotBar = hotBar;
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

    public String getFileName() {
        return name;
    }

    public String getName() {
        if(this.name != null) return name.replaceAll(".json","");
        return "default";
    }

    public SpecificConfig setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpecificConfig config = (SpecificConfig) o;
        return author.equals(config.author) && inventory.equals(config.inventory) && hotBar.equals(config.hotBar) && Objects.equals(name, config.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, inventory, hotBar, name);
    }
}
