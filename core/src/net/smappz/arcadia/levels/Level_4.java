package net.smappz.arcadia.levels;

import net.smappz.arcadia.actors.InvaderSquadron;
import net.smappz.arcadia.actors.Squadron;
import net.smappz.arcadia.actors.SquadronWave;
import net.smappz.arcadia.util.Formation;
import net.smappz.arcadia.util.Route;
import net.smappz.arcadia.util.TimeEvent;

import static net.smappz.arcadia.AbstractScreen.HEIGHT;
import static net.smappz.arcadia.util.Formation.DECAL_Y;

public class Level_4 extends Level {

    @Override
    public String getTitle() {
        return "Classic invaders";
    }

    @Override
    protected void buildTimeLine() {
        createEnemies();
    }

    private void createEnemies() {
        final Route route = new Route();
        route.addPoint(0, HEIGHT - DECAL_Y);

        TimeEvent waveH = new SquadronWave() {
            @Override
            public Squadron init() {
                return new InvaderSquadron(army, route, Formation.Square5, 11, 11, 13, 11, 11,
                        16, 15, 16, 15, 16,
                        14, 17, 19, 17, 14);
            }
        };

        timeline.addEvent(0, waveH);
    }
}
