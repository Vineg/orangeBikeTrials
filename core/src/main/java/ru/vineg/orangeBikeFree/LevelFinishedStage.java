package ru.vineg.orangeBikeFree;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import ru.vineg.orangeBikeFree.screens.*;
import ru.vineg.orangeBikeFree.backend.MotodroidBackendApi;
import ru.vineg.orangeBikeFree.reflection.LevelReplay;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Vineg
 * Date: 04.11.13
 * Time: 1:54
 * To change this template use File | Settings | File Templates.
 */
public class LevelFinishedStage extends UIStage {
    public LevelFinishedStage(final MainMenu menu, final LevelsScreen levelsList, final LevelReplay replay, List<TextButtonData> additionalButtons) {
        super();
        final Skin skin = menu.skin;
        Table container = new Table();
        container.setWidth(400);

        container.row();
        Table levelsTable = new Table();
        levelsTable.defaults().spaceBottom(10);


        TextButton levelsButton = new TextButton(" all levels ", skin);

        TextButton viewReplay = new TextButton(" view replay ", skin);

        TextButton shareReplay = new TextButton(" share replay ", skin);


        levelsTable.row().fill(true, false).expand(true, false);


        for (TextButtonData but : additionalButtons) {
            TextButton button = new TextButton(but.label, skin);
            button.addListener(but.clickListener);
            levelsTable.add(button).width(350);
            levelsTable.row().fill(true, false).expand(true, false);
        }

        if (levelsList!=null) {
            levelsTable.add(levelsButton).width(350);
        }

        levelsTable.row().fill(true, false).expand(true, false);
        levelsTable.add(viewReplay).width(350);

        levelsTable.row().fill(true, false).expand(true, false);
        levelsTable.add(shareReplay).width(350);




        ScrollPane levelsScroll = new ScrollPane(levelsTable, skin);
        levelsScroll.setScrollingDisabled(true, false);
        levelsScroll.setFadeScrollBars(false);


        container.row();
        container.add(levelsScroll).height(220).width(400);
        container.pack();
        container.setPosition( getWidth()/2- container.getWidth() / 2, getHeight()/2- container.getHeight() / 2);


        levelsButton.addListener(new GameClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);    //To change body of overridden methods use File | Settings | File Templates.
                menu.setStage(levelsList);
            }
        });

        viewReplay.addListener(new GameClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                menu.game.levelReplay(replay);
            }
        });

        shareReplay.addListener(new GameClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                MotodroidBackendApi.submitReplay(replay);
            }
        });

        addActor(container);


    }
}
