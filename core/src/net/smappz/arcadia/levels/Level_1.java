package net.smappz.arcadia.levels;

import com.badlogic.gdx.scenes.scene2d.Stage;

import net.smappz.arcadia.AbstractScreen;
import net.smappz.arcadia.actors.AirSquadron;
import net.smappz.arcadia.actors.Army;
import net.smappz.arcadia.actors.SquadronWave;
import net.smappz.arcadia.util.Formation;
import net.smappz.arcadia.util.Route;
import net.smappz.arcadia.util.RouteDriver;
import net.smappz.arcadia.util.TimeEvent;

public class Level_1 extends Level {

    public void create(Stage stage, Army army) {
        super.create(stage, army);
        createEnemies();
    }

    private void createEnemies() {
        final Route loopFromLeft = new Route();
        loopFromLeft.addPoint(-30, 200).addPoint(600, 200).addPoint(900, 300).addPoint(1000, 400);
        loopFromLeft.addPoint(900, 500).addPoint(800, 600).addPoint(700, 650).addPoint(600, 600);
        loopFromLeft.addPoint(500, 500).addPoint(400, 300).addPoint(400, -30);

        final Route loopFromRight = new Route(loopFromLeft);
        RouteDriver.reverse(loopFromRight, AbstractScreen.HEIGHT);

        final Route fromLeft = new Route();
        fromLeft.addPoint(100, -30).addPoint(200, 70).addPoint(300, 120).addPoint(1300, 200);

        final Route fromRight = new Route(fromLeft);
        RouteDriver.reverse(fromRight, AbstractScreen.HEIGHT);

        final Route horizontal = new Route();
        horizontal.addPoint(200, -30).addPoint(200, 750);

        final Route middle = new Route();
        middle.addPoint(-30, 360).addPoint(1300, 360);

        final Route middleLeft = new Route(middle);
        RouteDriver.translate(middleLeft, 0, -3*Formation.DECAL_Y);

        final Route middleRight = new Route(middle);
        RouteDriver.translate(middleRight, 0, 2*Formation.DECAL_Y);

        RouteDriver.translate(middle, 0, -2*Formation.DECAL_Y);

        TimeEvent wave1 = new SquadronWave() {
            @Override
            public AirSquadron init() {
                return new AirSquadron(army, fromLeft, Formation.Vertical, 1, 1, 3, 1);
            }
        };
        TimeEvent wave2 = new SquadronWave() {
            @Override
            public AirSquadron init() {
                return new AirSquadron(army, fromRight, Formation.Vertical, 1, 1, 3, 1);
            }
        };
        TimeEvent wave3 = new SquadronWave() {
            @Override
            public AirSquadron init() {
                return new AirSquadron(army, middleLeft, Formation.Square2, 1, 1, 1, 1, 1, 1, 1, 1);
            }
        };
        TimeEvent wave4 = new SquadronWave() {
            @Override
            public AirSquadron init() {
                return new AirSquadron(army, middleRight, Formation.Square2, 1, 1, 1, 1, 1, 1, 1, 1);
            }
        };
        TimeEvent wave5 = new SquadronWave() {
            @Override
            public AirSquadron init() {
                return new AirSquadron(army, middle, Formation.Square4, 1, 1, 1, 1, 1, 1, 1, 1);
            }
        };
        TimeEvent waveLoopLeft = new SquadronWave() {
            @Override
            public AirSquadron init() {
                return new AirSquadron(army, loopFromLeft, Formation.Vertical, 1, 1, 3, 4, 1, 1);
            }
        };
        TimeEvent waveLoopRight = new SquadronWave() {
            @Override
            public AirSquadron init() {
                return new AirSquadron(army, loopFromRight, Formation.Vertical, 1, 1, 3, 4, 1, 1);
            }
        };
        TimeEvent waveH = new SquadronWave() {
            @Override
            public AirSquadron init() {
                AirSquadron h = new AirSquadron(army, horizontal, Formation.Horizontal, 2, 2);
                h.setCycle(true);
                return h;
            }
        };

        timeline.addEvent(2, waveH);
        timeline.addEvent(5, wave1);
        timeline.addEvent(10, wave2);
        timeline.addEvent(15, wave2);
        timeline.addEvent(20, wave1);
        timeline.addEvent(25, wave1);
        timeline.addEvent(30, wave3);
        timeline.addEvent(35, wave4);
        timeline.addEvent(40, waveLoopLeft);
        timeline.addEvent(45, wave5);
        timeline.addEvent(50, waveLoopRight);
        timeline.addEvent(55, wave5);

        restartTimelineAt(60);
    }
}
