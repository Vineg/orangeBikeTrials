package ru.vineg.orangeBikeFree.screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import ru.vineg.orangeBikeFree.EditableLevel;
import ru.vineg.orangeBikeFree.LevelSource;
import ru.vineg.orangeBikeFree.LevelsSource;
import ru.vineg.orangeBikeFree.Motodroid;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Vineg
 * Date: 03.11.13
 * Time: 23:56
 * To change this template use File | Settings | File Templates.
 */
public class LevelsScreen extends UIStage {

    private final MainMenu menu;

    public LevelsSource getLevelsSource() {
        return levels;
    }

    private final LevelsSource levels;
    private ArrayList<Button> additionalPauseButtons=new ArrayList<Button>();
    private Table container;

    public LevelsScreen(final MainMenu menu, LevelsSource levels) {
        super();
        this.menu=menu;
        this.levels = levels;
        create();
    }

    private void create() {
        float scrollSize=40;


        final Skin skin = menu.skin;
        if(container!=null){
            container.remove();
        }
        container = new Table();
        container.setWidth(400);

        TextButton mainMenuButton = new TextButton(" main menu ", skin);

        container.row().fill(true, false).expand(true, false).center();
        container.add(mainMenuButton).width(350).spaceBottom(20).padLeft(scrollSize/2);

        container.row();
        Table scrollArea = new Table();
        Table levelsTable = new Table();
        scrollArea.add(levelsTable).width(350);
        levelsTable.defaults().pad(5);


        for(final LevelSource levelSource:levels.getLevelsList()){
            levelsTable.row().fillX().expandX();
            final String levelName = levelSource.getName();
            Button levelButton;
            if (!Motodroid.gamePreferences.isAllUnlocked()&&levelSource.isDefault()&&levelSource.getNumber()> levels.getUnlockedLevel()) {
                levelButton = new TextButton("locked", skin);
                levelButton.setDisabled(true);
                skin.setEnabled(levelButton,false);

            } else {
                levelButton = new TextButton(levelName, skin);
                levelButton.addListener(new GameClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        setLevel(levelSource);
                    }
                });
            }
            final Table row = new Table();
            row.add(levelButton).expandX().fillX();
            if(levelSource instanceof EditableLevel){
                TextButton editButton = ((EditableLevel)levelSource).getEditButton(skin);
                row.add(editButton);
                Button removeButton = ((EditableLevel)levelSource).getRemoveButton(skin);
                removeButton.addListener(new GameClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        row.remove();
                        super.clicked(event, x, y);
                    }
                });
                row.add(removeButton);
            }
            levelsTable.add(row).expandX().fillX();
        }

        mainMenuButton.addListener(new GameClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                menu.mainMenu();
                super.clicked(event, x, y);
            }
        });


        ScrollPane levelsScroll = new ScrollPane(scrollArea, skin);
        levelsScroll.setScrollingDisabled(true, false);
        levelsScroll.setFadeScrollBars(false);


        container.row();
        container.add(levelsScroll).height(220).width(400);
        container.pack();
        container.setPosition(getWidth()/2 - container.getWidth() / 2,   getHeight()/2- container.getHeight() / 2);

        addActor(container);
    }

    private void setLevel(LevelSource level) {
        LevelScreen levelScreen = menu.game.setLevel(level,this);
        levelScreen.addPauseButtons(additionalPauseButtons);
    }

    public void setLevel(int number) {
        setLevel(levels.get(number));
    }

    protected void addPauseButton(Button button){
        additionalPauseButtons.add(button);
    }

    @Override
    public void draw() {
//        Table.drawDebug(this);
        super.draw();
    }


    @Override
    public void show() {
        if(levels.isChanged()){
            create();
        }
        super.show();
    }

}
