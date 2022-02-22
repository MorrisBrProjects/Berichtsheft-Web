package de.morrisbr.berichtshelfer.devices.services;


import com.mongodb.util.JSON;
import de.morrisbr.berichtshelfer.devices.bericht.Bericht;
import de.morrisbr.berichtshelfer.devices.database.MongoDatabaseHandler;
import de.morrisbr.berichtshelfer.devices.utils.JsonConverter;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.mongojack.JacksonMongoCollection;


import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;

//mongodb://37.114.47.77:27017/?readPreference=primary&appname=MongoDB%20Compass&directConnection=true&ssl=false

public class BerichtService {

    private MongoDatabaseHandler databaseHandler;


    public BerichtService(String connectionString, String databaseName) {
        this.databaseHandler = new MongoDatabaseHandler(connectionString, databaseName);
    }

    public List<String> getAllBerichteAsJson() {
        List<String> berichte = new ArrayList<>();
        for (Document document : databaseHandler.getAllDocuments("Berichte")) {
            berichte.add(document.getString("data"));
        }
        return berichte;
    }

    public ArrayList<Bericht> getAllBerichteAsObject() {
        ArrayList<Bericht> berichte = new ArrayList<>();
        for (String jsonBericht : getAllBerichteAsJson()) {
            System.out.println(jsonBericht);
            berichte.add((Bericht) JsonConverter.jsonStringToObject(jsonBericht, Bericht.class));
        }
        return berichte;
    }

    //name oder id ist besser
    public void createBericht(Bericht bericht) {
        //System.out.println(JSON.parse(JsonConverter.objectToJsonString(bericht)));
        Document document = new Document("documentName", bericht.getTitle());
        document.append("data", JsonConverter.objectToJsonString(bericht));
        databaseHandler.getDatabase("BerichtHelfer").getCollection("Berichte").insertOne(document);
    }

    public void deleteBericht(String name) {
        databaseHandler.deleteOne("Berichte", name);
    }

    public String getBerichtAsJson(String name) {
        return databaseHandler.getDocument("Berichte", name).getString("data");
    }

    public Bericht getBerichtAsObject(String name) {
        Bericht bericht = (Bericht) JsonConverter.jsonStringToObject(getBerichtAsJson(name), Bericht.class);
        return bericht;
    }

    public boolean isBerichtExist(String name) {
        return databaseHandler.getDocument("Berichte", name) != null;
    }

}
