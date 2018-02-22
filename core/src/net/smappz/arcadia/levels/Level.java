package net.smappz.arcadia.levels;

import com.badlogic.gdx.scenes.scene2d.Stage;

import net.smappz.arcadia.actors.Army;
import net.smappz.arcadia.actors.Scroller;
import net.smappz.arcadia.util.TimeEvent;
import net.smappz.arcadia.util.TimeEventListener;
import net.smappz.arcadia.util.Timeline;

public abstract class Level implements TimeEventListener {
    protected Stage stage;
    protected Army army;
    private Scroller scroller;
    final Timeline timeline = new Timeline(this);

    public void create(Stage stage, Army army) {
        this.stage = stage;
        this.army = army;

        scroller = new Scroller();
        stage.addActor(scroller);
        scroller.toBack();
    }

    public void act(float delta) {
        timeline.act(delta);
    }

    @Override
    public void onEvent(TimeEvent event) {
        event.trigger();//may be triggered by timeline directly if no override needed
    }
}
