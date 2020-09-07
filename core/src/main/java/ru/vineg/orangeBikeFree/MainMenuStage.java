package ru.vineg.orangeBikeFree;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import ru.vineg.orangeBikeFree.screens.GameClickListener;
import ru.vineg.orangeBikeFree.screens.MainMenu;
import ru.vineg.orangeBikeFree.screens.UIStage;

/**
 * Created with IntelliJ IDEA.
 * User: Vineg
 * Date: 04.11.13
 * Time: 0:19
 * To change this template use File | Settings | File Templates.
 */
public class MainMenuStage extends UIStage {


    public MainMenuStage(final MainMenu menu) {
        final Skin skin = menu.skin;

        int buttonWidth = 350;
        Table container = new Table();
        container.setWidth(400);
//        container.debug();

        container.row();
        Table table = new Table();
        table.defaults().spaceBottom(10).fill(true, false).expand(true, false);


        TextButton settingsButton = new TextButton(" settings ", skin);
        TextButton levelsButton = new TextButton(" levels ", skin);


        table.row();
        table.add(levelsButton).width(buttonWidth);

        table.row();
        table.add(settingsButton).width(buttonWidth);

//        if (Motodroid.isDebugging()) {
            TextButton editorButton = new TextButton(" editor ", skin);
            table.row();
            table.add(editorButton).width(buttonWidth);

            editorButton.addListener(new GameClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    menu.game.setEditorScreen();
                }
            });

            TextButton myLevelsButton = new TextButton(" my levels ", skin);
            table.row();
            table.add(myLevelsButton).width(buttonWidth);

            myLevelsButton.addListener(new GameClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    menu.myLevelsSelectMenu();
                }
            });

//        }

        ScrollPane levelsScroll = new ScrollPane(table, skin);
        levelsScroll.setScrollingDisabled(true, false);
        levelsScroll.setFadeScrollBars(false);


        container.row();
        container.add(levelsScroll).height(220).width(400);
        container.pack();
        container.setPosition(getWidth()/2-container.getWidth()/2,getHeight()/2-container.getHeight()/2);

        settingsButton.addListener(new GameClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                menu.settingsMenu();
            }
        });

        levelsButton.addListener(new GameClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);    //To change body of overridden methods use File | Settings | File Templates.
                menu.levelsSelectMenu();
            }
        });



        addActor(container);
//        addActor(new Label("qweqqq",skin));
//        addActor(Motodroid.manager.getLoadingAnimation());
    }

    @Override
    public void draw() {
        super.draw();
    }
}
