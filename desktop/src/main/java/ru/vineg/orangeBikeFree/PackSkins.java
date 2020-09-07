package ru.vineg.orangeBikeFree;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.utils.Json;

import java.io.*;

/**
 * @author Mats Svensson
 */
public class PackSkins {

    public static void main(String[] args) {
        TexturePacker.Settings s = new TexturePacker.Settings();
        s.filterMag= Texture.TextureFilter.MipMapLinearLinear;
        s.filterMin= Texture.TextureFilter.MipMapLinearLinear;
        s.format= Pixmap.Format.RGBA4444;
        process(s, "android/assets-src/workfiles/finished", "android/assets/data", "loading");
        process(s, "android/assets-src/game", "android/assets/data", "game");

//        process(s, "android/assets-src/motorbike", "android/assets/data", "bike.pack");


        File levelsDir = new File("android/assets/levels");
        String[] levelsNames = levelsDir.list();

        PrintWriter fileWriter = null;
        try {
            fileWriter = new PrintWriter(new File("android/assets/levelNames.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Json json = new Json();
        fileWriter.println(json.toJson(levelsNames));
        fileWriter.close();

    }

    private static void process(TexturePacker.Settings s, String inputDir, String outDir, String outFile) {
        new File(outDir + "/" + outFile).delete();
        TexturePacker.process(s, inputDir, outDir, outFile);

    }

}
