package ru.vineg.orangeBikeFree;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.view.KeyEvent;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class MainActivity extends AndroidApplication {
    public static Activity instance;
    private Motodroid game;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance=this;

        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
//        if (Preferences.smoothMap) {
//            cfg.numSamples = 2;
//        }

        game = new Motodroid(new Preferences(this));
        initialize(game, cfg);
        onNewIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Uri uri1 = intent.getData();

//        Uri.Builder builder = new Uri.Builder();
//        builder.authority(uri1.getAuthority());
//        builder.scheme(uri1.getScheme());
//        builder.path("api/"+uri1.getPath());
//        uri1=builder.build();

        if (uri1 != null) {
            System.out.println(uri1.toString());
        }

        if (uri1 != null) {
            game.setUrl(uri1.toString());
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_MENU) {
            Debug.stopMethodTracing();
            game.backButtonHandle();
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Debug.stopMethodTracing();
    }
}