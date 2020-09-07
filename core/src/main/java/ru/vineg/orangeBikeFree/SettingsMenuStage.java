package ru.vineg.orangeBikeFree;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import ru.vineg.orangeBikeFree.screens.GameClickListener;
import ru.vineg.orangeBikeFree.screens.MainMenu;
import ru.vineg.orangeBikeFree.screens.UIStage;

/**
 * Created with IntelliJ IDEA.
 * User: Vineg
 * Date: 04.11.13
 * Time: 0:30
 * To change this template use File | Settings | File Templates.
 */
public class SettingsMenuStage extends UIStage {
    public SettingsMenuStage(final MainMenu menu) {
        super();
        final Skin skin = menu.skin;
        final SettingsMenuStage uiStage = this;
        Table container = new Table();
        container.setWidth(400);
//        container.debug();

        container.row();
        Table levelsTable = new Table();
        levelsTable.defaults().spaceBottom(10);


        TextButton resetButton = new TextButton(" reset data ", skin);
        TextButton mainMenuButton = new TextButton(" back ", skin);
        final TextButton changeQualityButton = new TextButton(getQualityButtonName(), skin);


        levelsTable.row().fill(true, false).expand(true, false);
        levelsTable.add(mainMenuButton).width(350);

        levelsTable.row().fill(true, false).expand(true, false);
        levelsTable.add(resetButton).width(350);

        levelsTable.row().fill(true, false).expand(true, false);
        levelsTable.add(changeQualityButton).width(350);



        ScrollPane levelsScroll = new ScrollPane(levelsTable, skin);
        levelsScroll.setScrollingDisabled(true, false);
        levelsScroll.setFadeScrollBars(false);


        container.row();
        container.add(levelsScroll).height(220).width(400);
        container.pack();
        container.setPosition(getWidth()/2 - container.getWidth() / 2,  getHeight()/2- container.getHeight() / 2);



        resetButton.addListener(new GameClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                new Dialog("  ", skin, "dialog-dim") {
                    protected void result (Object object) {
                        if((Boolean)object){
                            Preferences prefs = LevelsSource.getPrefs();
                            prefs.clear();
                            prefs.flush();
                        }
                    }
                }.text("   Are you sure want to reset all data?   ").button("  Yes  ", true).button("  No  ", false).key(Input.Keys.ENTER, true)
                        .key(Input.Keys.ESCAPE, false).show(uiStage);



            }
        });

        mainMenuButton.addListener(new GameClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                menu.mainMenu();
                super.clicked(event, x, y);    //To change body of overridden methods use File | Settings | File Templates.

            }
        });

        changeQualityButton.addListener(new GameClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(GamePreferences.getQuality()== GamePreferences.Quality.high){
                    GamePreferences.setQuality(GamePreferences.Quality.low);
                }else{
                    GamePreferences.setQuality(GamePreferences.Quality.high);
                }
                changeQualityButton.setText(getQualityButtonName());

                super.clicked(event, x, y);    //To change body of overridden methods use File | Settings | File Templates.

            }
        });


        addActor(container);

    }

    private String getQualityButtonName(){
        switch(GamePreferences.getQuality()){
            case high:
                return " set low quality ";
            case low:
                return " set high quality ";

        }
        return "";
    }

}
