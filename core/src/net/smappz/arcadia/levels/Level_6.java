package net.smappz.arcadia.levels;

import net.smappz.arcadia.actors.AirSquadron;
import net.smappz.arcadia.actors.SquadronWave;
import net.smappz.arcadia.util.Formation;
import net.smappz.arcadia.util.Route;
import net.smappz.arcadia.util.RouteDriver;
import net.smappz.arcadia.util.TimeEvent;

import static net.smappz.arcadia.AbstractScreen.WIDTH;

public class Level_6 extends Level {

    @Override
    public String getTitle() {
        return "Scroll invaders";
    }

    @Override
    protected void buildTimeLine() {
        createEnemies();
    }

    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    private void createEnemies() {
        final Route horizontal = new Route();
        horizontal.addPoint(-60, 1100).addPoint(WIDTH + 60, 1100);

        final Route middle = new Route();
        middle.addPoint(WIDTH/2, 1300).addPoint(WIDTH/2, -30);

        final Route middleLeft = middle.clone();
        RouteDriver.translate(middleLeft, -3*Formation.DECAL_X, 0);

        final Route middleRight = middle.clone();
        RouteDriver.translate(middleRight, 2*Formation.DECAL_X, 0);

        RouteDriver.translate(middle, -1.5f*Formation.DECAL_X, 0);

        TimeEvent wave1 = new SquadronWave() {
            @Override
            public AirSquadron init() {
                return new AirSquadron(army, middleLeft, Formation.Horizontal, 11, 11, 13, 11);
            }
        };
        TimeEvent wave2 = new SquadronWave() {
            @Override
            public AirSquadron init() {
                return new AirSquadron(army, middleRight, Formation.Vertical, 11, 11, 13, 11);
            }
        };
        TimeEvent wave3 = new SquadronWave() {
            @Override
            public AirSquadron init() {
                return new AirSquadron(army, middleLeft, Formation.Square2, 11, 11, 11, 11, 11, 11, 11, 11);
            }
        };
        TimeEvent wave4 = new SquadronWave() {
            @Override
            public AirSquadron init() {
                return new AirSquadron(army, middleRight, Formation.Square2, 11, 11, 11, 11, 11, 11, 11, 11);
            }
        };
        TimeEvent wave5 = new SquadronWave() {
            @Override
            public AirSquadron init() {
                return new AirSquadron(army, middle, Formation.Square4, 11, 11, 11, 11, 11, 11, 11, 11);
            }
        };
        TimeEvent waveH = new SquadronWave() {
            @Override
            public AirSquadron init() {
                return new AirSquadron(army, horizontal, Formation.Horizontal, 12, 12);
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
        timeline.addEvent(40, waveH);
        timeline.addEvent(45, wave5);
        timeline.addEvent(50, waveH);
        timeline.addEvent(55, wave5);
    }
}
