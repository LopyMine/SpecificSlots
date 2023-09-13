package net.lopymine.specificslots.textures;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import net.minecraft.item.Item;
import net.minecraft.resource.*;
import net.minecraft.util.Identifier;

import net.fabricmc.loader.api.FabricLoader;

import net.lopymine.specificslots.utils.ItemUtils;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

import static net.lopymine.specificslots.SpecificSlots.logger;

public class SpecificAdditionLoader {
    private final static Set<String> list = new HashSet<>(FabricLoader.getInstance().getAllMods().stream().flatMap(mod -> Stream.of(mod.getMetadata().getId())).toList());
    private static final Gson gson = new GsonBuilder().setLenient().create();

    public static void load(List<ResourcePack> packs) {
        boolean found = false;
        for (ResourcePack pack : packs) {
            if (pack.getName().replaceAll("file/", "").replaceAll(".zip", "").equals("Specific Addition")) {
                logger.info("Specific Addition has been found!");

                Set<String> namespaces = pack.getNamespaces(ResourceType.CLIENT_RESOURCES);

                for (String name : namespaces) {
                    if (!list.contains(name) && !name.equals("vanilla")) continue;
                    logger.info("Registering {}", name);

                    ResourcePack.ResultConsumer resultConsumer = (identifier, inputStreamInputSupplier) -> {
                        String path = identifier.getPath();
                        if (path.endsWith("items.json")) {
                            try (InputStream inputStream = inputStreamInputSupplier.get(); BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                                JsonReader jsonReader = new JsonReader(reader);
                                jsonReader.setLenient(true);


                                JsonObject jsonObject = gson.fromJson(jsonReader, JsonObject.class);
                                jsonReader.close();

                                JsonArray itemArray = jsonObject.getAsJsonArray("items");
                                if (itemArray != null) {
                                    for (JsonElement o : itemArray.asList()) {

                                        String texture = null;
                                        HashSet<Item> itemsSet = new HashSet<>();

                                        JsonObject item = o.getAsJsonObject();
                                        JsonArray itemsIdArray = item.getAsJsonArray("id");
                                        if (itemsIdArray != null) {
                                            for (JsonElement id : itemsIdArray.asList()) {
                                                String s = id.getAsString();
                                                if (s != null) itemsSet.add(ItemUtils.getItemByName(name, s));
                                            }
                                        } else {
                                            logger.error("Failed to get items IDs from {} config", name);
                                        }

                                        JsonElement textureElement = item.get("texture");
                                        if (textureElement != null) texture = textureElement.getAsString();

                                        if (texture != null && !itemsSet.isEmpty()) {
                                            String texturePath = "textures/" + texture;
                                            if (texture.replace(".png", "").endsWith("_i")) {
                                                logger.info("Putting {} items with {} texture from {}", itemsSet, texturePath, name);
                                                ShadowItems.MODS_TEXTURES.put(itemsSet, new Identifier(name, texturePath));
                                            } else logger.error("Failed to get texture from {} config, because the texture name along the {} path is missing the '_i' suffix", name, texturePath);
                                        }
                                    }
                                } else {
                                    logger.error("Failed to get items from {} config", name);
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };


                    pack.findResources(ResourceType.CLIENT_RESOURCES, name, "config", resultConsumer);
                }
                found = true;
                break;
            }
        }

        if (!found) ShadowItems.MODS_TEXTURES.clear();

    }
}
