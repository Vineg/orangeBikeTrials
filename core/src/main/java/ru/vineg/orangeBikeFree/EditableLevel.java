package ru.vineg.orangeBikeFree;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import ru.vineg.orangeBikeFree.screens.GameClickListener;
import ru.vineg.orangeBikeFree.screens.TextButtonData;

/**
 * Created by Vineg on 24.03.14.
 */
public class EditableLevel extends LevelSource {

    private FileHandle source;

    public EditableLevel(FileHandle file, int number){
        super(file,number);
        isDefault=false;
        source=file;
    }

    public Button getRemoveButton(Skin skin) {
        TextButton removeButton = new TextButton("remove", skin);

        removeButton.addListener(new GameClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                source.delete();
            }
        });
        return removeButton;
    }

    public FileHandle getSource() {
        return source;
    }


    public TextButton getEditButton(Skin skin){
        TextButtonData data = getEditButtonData();
        TextButton button = new TextButton(data.label,skin);
        button.addListener(data.clickListener);
        return button;
    }

    public TextButtonData getEditButtonData() {
        if(getSource()==null){
            return null;
        }

        TextButtonData editButton = new TextButtonData("edit");

        editButton.addListener(new GameClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Motodroid.setEditorScreen(EditableLevel.this);
                super.clicked(event, x, y);
            }
        });
        return editButton;
    }
}
