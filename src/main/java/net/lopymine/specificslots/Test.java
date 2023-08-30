package net.lopymine.specificslots;

import javax.print.DocFlavor;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Test {

    private final static Pattern filePattern = Pattern.compile("^[[a-zA-Z0-9А-Яа-яЁё_],\\s-]+\\.[A-Za-z]{4}$");

    public static void main(String[] args) {

        List<Integer> list = List.of(1, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18);
        System.out.println(list.size());
        int maxIntPerPage = 5;
        System.out.println(list.size() % 5);
        System.out.println(list.size() / 5);
        //String s = "t_iest_i.png";
        //System.out.println(s.substring(0,s.length()-4));
        //String name = "minecraft:test";
        //String mod = name.substring(0,name.indexOf(':'));
        //String path = name.substring(name.lastIndexOf(':')+1);
        //System.out.println(mod);
        //System.out.println(path);
        //File folder = new File("src/main/resources/assets/specificslots/textures/items");
        //File[] files = folder.listFiles();
//////////
//////
        //List<String> list = new ArrayList<>();
        //String example = "public static final Identifier ";
        //String example2 = " = new Identifier(SpecificSlots.getID(), \"textures/items/";
        //if (files != null) {
        //    for (File file : files) {
        //        String name = file.getName();
        //        if (!name.endsWith(".png")) continue;
        //        String nameWithoutPng = name.replaceAll(".png", "");
        //        if(!nameWithoutPng.contains("_i")) System.out.println(nameWithoutPng);
        //        list.add(example + nameWithoutPng.substring(0,nameWithoutPng.length()-2).toUpperCase() + example2 + nameWithoutPng + ".png\");");
        //    }
        //}
//////////
        //list.forEach(System.out::println);

        //try (InputStream inputStream = Files.newInputStream(Path.of("blocks1.txt")); BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
        //    String line;
        //    while ((line = reader.readLine()) != null) {
        //        if(line.endsWith("BANNER")){
        //            System.out.println(line + ",");
        //        }
        //    }
        //} catch (IOException e) {
        //    throw new RuntimeException(e);
        //}
    }
}