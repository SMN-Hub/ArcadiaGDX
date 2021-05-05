package net.smappz.arcadia.levels;

import net.smappz.arcadia.actors.AirSquadron;
import net.smappz.arcadia.actors.InvaderSquadron;
import net.smappz.arcadia.actors.Squadron;
import net.smappz.arcadia.actors.SquadronWave;
import net.smappz.arcadia.util.Formation;
import net.smappz.arcadia.util.Route;
import net.smappz.arcadia.util.TimeEvent;

import static net.smappz.arcadia.AbstractScreen.HEIGHT;
import static net.smappz.arcadia.util.Formation.DECAL_Y;

public class Level_5 extends Level {

    @Override
    public String getTitle() {
        return "Short invaders";
    }

    @Override
    protected void buildTimeLine() {
        createEnemies();
    }

    private void createEnemies() {
        final Route horizontal = new Route();
        horizontal.addPoint(-30, 1100).addPoint(750, 1100);

        final Route route = new Route();
        route.addPoint(0, HEIGHT - DECAL_Y);

        TimeEvent wave = new SquadronWave() {
            @Override
            public Squadron init() {
                return new InvaderSquadron(army, route, Formation.Horizontal, 13, 15, 16);
            }
        };
        TimeEvent waveH = new SquadronWave() {
            @Override
            public AirSquadron init() {
                return new AirSquadron(army, horizontal, Formation.Horizontal, 12);
            }
        };

        timeline.addEvent(0, wave);
        timeline.addEvent(10, waveH);

        timeline.addEvent(25, wave);
        timeline.addEvent(30, waveH);
    }
}
