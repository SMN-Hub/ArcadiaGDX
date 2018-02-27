package net.smappz.arcadia.levels;

import com.badlogic.gdx.scenes.scene2d.Stage;

import net.smappz.arcadia.actors.Army;
import net.smappz.arcadia.actors.Scroller;
import net.smappz.arcadia.util.TimeEvent;
import net.smappz.arcadia.util.Timeline;

public abstract class Level {
    protected Stage stage;
    protected Army army;
    private Scroller scroller;
    final Timeline timeline = new Timeline();

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

    void restartTimelineAt(float instant) {
        timeline.addEvent(instant, new TimeEvent() {
            @Override
            public void trigger() {
                timeline.restart();
            }
        });
    }
}
