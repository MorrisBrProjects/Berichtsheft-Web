package de.morrisbr.witzlecraft.objects;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BerichtService {

    public static List<String> getAllBerichteAsJson() {
        File folder = new File("resources/OnlineBanking/berichte");
        File[] listOfFiles = folder.listFiles();
        ArrayList<String> berichte = new ArrayList<>();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                berichte.add(JsonConverter.readJSONFromFile(listOfFiles[i].getPath()));
                System.out.println("File " + listOfFiles[i].getName());
                System.out.println("FGFGF: " + JsonConverter.readJSONFromFile(listOfFiles[i].getPath()));
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }

        return berichte;
    }

    public static ArrayList<Bericht> getAllBerichteAsObject() {
        File folder = new File("resources/OnlineBanking/berichte");
        File[] listOfFiles = folder.listFiles();
        ArrayList<Bericht> berichte = new ArrayList<>();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                berichte.add((Bericht) JsonConverter.jsonFileToObject(listOfFiles[i].getPath(), Bericht.class));
                System.out.println("File " + listOfFiles[i].getName());
                System.out.println("FGFGF: " + JsonConverter.readJSONFromFile(listOfFiles[i].getPath()));
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }

        return berichte;
    }

    public static String getBerichtAsJson(String name) {
        for (String json : getAllBerichteAsJson()) {
            Bericht bericht = (Bericht) JsonConverter.jsonStringToObject(json, Bericht.class);
            if(bericht.getTitle().equalsIgnoreCase(name)) {
                return json;
            }
        }
        return "Not found!";
    }

    public static Bericht getBerichtAsObject(String name) {
        Bericht bericht = (Bericht) JsonConverter.jsonStringToObject(getBerichtAsJson(name), Bericht.class);
        return bericht;
    }

    public static boolean isBerichtExist(String name) {
        return getBerichtAsJson(name).equalsIgnoreCase("Not found!");
    }

}
