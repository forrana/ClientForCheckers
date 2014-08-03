package com.checkers.support.locale;

import com.badlogic.gdx.Gdx;
import com.checkers.server.beans.User;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

/**
 * Created by forrana on 01/08/14.
 */
public class Localization {
    private static Map<String,Map<String,String>> fields;
    public Localization(String locale){
        ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
        File file;
           file = new File("../assets/data/localization/"+locale+".json");
        if(!file.exists())
           file = new File("../assets/data/localization/default.json");
        try {
            fields = mapper.readValue(file, Map.class);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public static Map<String,String> getFields(String page){

        return fields.get(page);
    }


}
