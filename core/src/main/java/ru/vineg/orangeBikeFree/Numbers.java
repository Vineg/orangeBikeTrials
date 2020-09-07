package ru.vineg.orangeBikeFree;

/**
 * Created by Vineg on 06.03.14.
 */
public class Numbers {
    public static int getInt(String s) {
        try {
            return Integer.parseInt(s.replaceAll("[^\\d](.*)", ""));
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
