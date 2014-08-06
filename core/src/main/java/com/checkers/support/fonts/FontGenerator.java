package com.checkers.support.fonts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Created by forrana on 7/26/14.
 */
public class FontGenerator {
    private static final String FONT_CHARACTERS = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя" +
            "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ" +
            "abcdefghijklmnopqrstuvwxyz" +
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "0123456789][_!$%#@|\\/?-+=()*&.;,{}\"´`'<>";
    private BitmapFont font;

    public FontGenerator(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/font/font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        float size = 0.18f;
        parameter.size = Gdx.graphics.getHeight() / (int)(100*size);
        parameter.characters = FONT_CHARACTERS;
        font = generator.generateFont(parameter);
        generator.dispose();
    }

    public FontGenerator(float size){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/font/font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        size = 32/size;
        parameter.size = Gdx.graphics.getHeight() / (int)(18*size);
        parameter.characters = FONT_CHARACTERS;
        font = generator.generateFont(parameter);
        generator.dispose();
    }
    public BitmapFont getFont(){
       return font;
    }
}
