package com.checkers.support.locale;

import com.badlogic.gdx.Gdx;
import com.checkers.server.beans.User;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by forrana on 01/08/14.
 */
public class Localization {
    private static Map<String,Map<String,String>> fields;
    public Localization(){
        ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
        try {
            fields = mapper.readValue(new File("../assets/data/localization/login_stage_rus.json"), Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String,String> getFields(){

        return fields.get("LoginPage");
    }


}
