package net.smappz.arcadia.levels;

import net.smappz.arcadia.actors.AirSquadron;
import net.smappz.arcadia.actors.SquadronWave;
import net.smappz.arcadia.util.Formation;
import net.smappz.arcadia.util.Route;
import net.smappz.arcadia.util.TimeEvent;

import static net.smappz.arcadia.AbstractScreen.HEIGHT;
import static net.smappz.arcadia.AbstractScreen.WIDTH;
import static net.smappz.arcadia.util.Formation.DECAL_X;
import static net.smappz.arcadia.util.Formation.DECAL_Y;

public class Level_2 extends Level {

    @Override
    protected void buildTimeLine() {
        createEnemies();
    }

    private void createEnemies() {
        final Route route = new Route();
        route.addPoint(0, HEIGHT+ DECAL_Y).addPoint(0, HEIGHT- DECAL_Y);
        for (int line=(int)(HEIGHT- DECAL_Y); line > 0; line -= DECAL_Y) {
            route.addPoint(0, line).addPoint(WIDTH - DECAL_X, line);
            route.addPoint(WIDTH - DECAL_X, line-DECAL_Y/2).addPoint(0, line-DECAL_Y/2);
        }

        TimeEvent waveH = new SquadronWave() {
            @Override
            public AirSquadron init() {
                return new AirSquadron(army, route, Formation.Horizontal, 13, 15, 16);
            }
        };

        timeline.addEvent(2, waveH);
    }
}
