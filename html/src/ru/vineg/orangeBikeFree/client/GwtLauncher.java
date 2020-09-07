package ru.vineg.orangeBikeFree.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.badlogic.gdx.backends.gwt.preloader.Preloader;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import ru.vineg.orangeBikeFree.Motodroid;


public class GwtLauncher extends GwtApplication {
    private Motodroid game;

    @Override
    public GwtApplicationConfiguration getConfig() {
        GwtApplicationConfiguration cfg = new GwtApplicationConfiguration(780, 620);
        return cfg;
    }

    @Override
    public ApplicationListener getApplicationListener() {
        game = new Motodroid(new Preferences(false));
        game.setUrl(Window.Location.getHref());
        //game.setUrl("http://192.168.0.2:8000/api/replay/1");
        return game;
    }

    @Override
    public void onModuleLoad() {
//        Window.alert("test");
        super.onModuleLoad();
    }

    @Override
    public Preloader.PreloaderCallback getPreloaderCallback() {
        final Panel preloaderPanel = new VerticalPanel();
        preloaderPanel.setStyleName("gdx-preloader");
        final Image logo = new Image("assets/logo.png");
        logo.setStyleName("logo");
        preloaderPanel.add(logo);
        final Panel meterPanel = new SimplePanel();
        meterPanel.setStyleName("gdx-meter");
        meterPanel.addStyleName("yellow");
        meterPanel.addStyleName("bg-orange");
        meterPanel.setWidth("410 px");
        final InlineHTML meter = new InlineHTML();
        final Style meterStyle = meter.getElement().getStyle();
        meterStyle.setWidth(0, Style.Unit.PCT);
        meterPanel.add(meter);
        preloaderPanel.add(meterPanel);
        preloaderPanel.setWidth("410 px");
        meterPanel.getElement().getStyle().setBackgroundColor("#ffeb7c");
        getRootPanel().add(preloaderPanel);
        return new Preloader.PreloaderCallback() {

            @Override
            public void error(String file) {
                System.out.println("error: " + file);
            }

            @Override
            public void update(Preloader.PreloaderState state) {
                meterStyle.setWidth(410 * state.getProgress(), Style.Unit.PX);
                //meterStyle.setWidth(410 * 1f, Style.Unit.PX);
            }


        };
    }
}