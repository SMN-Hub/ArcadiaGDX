package net.smappz.arcadia.levels;

import com.badlogic.gdx.scenes.scene2d.Stage;

import net.smappz.arcadia.actors.AirEnemy;
import net.smappz.arcadia.actors.AirSquadron;
import net.smappz.arcadia.actors.Army;
import net.smappz.arcadia.util.Route;
import net.smappz.arcadia.util.TimeEvent;

public class Level_1 extends Level {

    public void create(Stage stage, Army army) {
        super.create(stage, army);
        createEnemies();
    }

    private void createEnemies() {
        TimeEvent event1 = new TimeEvent() {
            @Override
            public void trigger() {
                Route r1 = new Route();
                r1.addPoint(-30, 200).addPoint(600, 200).addPoint(900, 300).addPoint(1000, 400);
                r1.addPoint(900, 500).addPoint(800, 600).addPoint(700, 650).addPoint(600, 600);
                r1.addPoint(500, 500).addPoint(400, 300).addPoint(400, -30);
                AirSquadron a = new AirSquadron(army, r1, 1, 1, 3, 4, 1, 1);
                a.setCycle(true);
            }
        };
        timeline.addEvent(10, event1);

        TimeEvent event2 = new TimeEvent() {
            @Override
            public void trigger() {
                Route r2 = new Route();
                r2.addPoint(-30, 400).addPoint(1300, 400);
                AirEnemy e1 = new AirEnemy(2, r2);
                e1.setCycle(true);
                army.addActor(e1);
            }
        };
        timeline.addEvent(2, event2);
    }
}
