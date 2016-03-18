package locationsholder.manager;

import locationsholder.LocationsHolder;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Felipe de Lucca on 18/03/2016.
 */
public class VariableManager {

    private static VariableManager instance = new VariableManager();

    public static VariableManager getInstance() { return instance; }

    /** SEPARATOR **/

    private File pluginDir;
    private File variablesFile;
    private JSONParser parser;

    public void setup(Plugin plugin) {
        this.pluginDir = plugin.getDataFolder();
        this.variablesFile = new File(this.pluginDir, "variables.json");
        this.parser = new JSONParser();

        if (!this.pluginDir.exists())
            this.pluginDir.mkdir();

        if (!this.variablesFile.exists())
            try {
                this.variablesFile.createNewFile();
                this.createVariables();
            } catch (IOException e) {
                e.printStackTrace();
            }
        else
            this.loadVariables();
    }

    public void createVariables() {
        JSONArray variablesArray = new JSONArray();

        try {
            FileWriter fw = new FileWriter(this.variablesFile);
            fw.write(variablesArray.toJSONString());
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadVariables() {
        JSONArray variablesArray;
        JSONObject variableObject;

        try {
            variablesArray = (JSONArray) parser.parse(new FileReader(this.variablesFile));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }

        String key;
        Object value;

        for (int i = 0; i < variablesArray.size(); i++) {
            variableObject = (JSONObject) variablesArray.get(i);

            key = variableObject.get("key").toString();
            value = variableObject.get("value").toString();

            if (key.startsWith("location:")) {
                Location location = Location.deserialize((Map<String, Object>) value);

                LocationsHolder.variables.put(key, location);
            }
            else
                LocationsHolder.variables.put(key, value);

            System.out.println("Variable loaded: key=" + key + "/value=" + value.toString());
        }
    }

    public void saveVariables() {
        JSONArray variablesArray = new JSONArray();
        JSONObject variableObject;

        for (Map.Entry<String, Object> kv : LocationsHolder.variables.entrySet()) {
            variableObject = new JSONObject();

            if (kv.getValue() instanceof Location) {
                variableObject.put("key", kv.getKey());
                variableObject.put("value", ((Location) kv.getValue()).serialize());
            }
            else {
                variableObject.put("key", kv.getKey());
                variableObject.put("value", kv.getValue());
            }

            variablesArray.add(variableObject);
        }

        try {
            FileWriter fw = new FileWriter(this.variablesFile);
            fw.write(variablesArray.toJSONString());
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
