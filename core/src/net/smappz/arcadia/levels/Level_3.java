package net.smappz.arcadia.levels;

import net.smappz.arcadia.AbstractScreen;
import net.smappz.arcadia.actors.AirSquadron;
import net.smappz.arcadia.actors.SquadronWave;
import net.smappz.arcadia.util.Formation;
import net.smappz.arcadia.util.Route;
import net.smappz.arcadia.util.RouteDriver;
import net.smappz.arcadia.util.TimeEvent;

public class Level_3 extends Level {

    @Override
    protected void buildTimeLine() {
        createEnemies();
    }

    @Override
    public String getTitle() {
        return "Plane attack";
    }

    private void createEnemies() {
        final Route loopFromLeft = new Route();
        loopFromLeft.addPoint(200, 1310).addPoint(200, 680).addPoint(300, 380).addPoint(400, 280);
        loopFromLeft.addPoint(500, 380).addPoint(600, 480).addPoint(650, 580).addPoint(600, 680);
        loopFromLeft.addPoint(500, 780).addPoint(300, 880).addPoint(-30, 880);

        final Route loopFromRight = loopFromLeft.clone();
        RouteDriver.reverse(loopFromRight, AbstractScreen.WIDTH);

        final Route fromLeft = new Route();
        fromLeft.addPoint(-30, 1200).addPoint(70, 1100).addPoint(120, 1000).addPoint(200, -30);

        final Route fromRight = fromLeft.clone();
        RouteDriver.reverse(fromRight, AbstractScreen.WIDTH);

        final Route horizontal = new Route();
        horizontal.addPoint(-30, 1100).addPoint(750, 1100);

        final Route middle = new Route();
        middle.addPoint(360, 1300).addPoint(360, -30);

        final Route middleLeft = middle.clone();
        RouteDriver.translate(middleLeft, -3*Formation.DECAL_X, 0);

        final Route middleRight = middle.clone();
        RouteDriver.translate(middleRight, 2*Formation.DECAL_X, 0);

        RouteDriver.translate(middle, -1.5f*Formation.DECAL_X, 0);

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
                return new AirSquadron(army, horizontal, Formation.Horizontal, 2, 2);
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
