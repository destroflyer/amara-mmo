/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.maps;

import java.util.ArrayList;
import com.jme3.math.Vector3f;
import amara.game.entitysystem.*;
import amara.game.entitysystem.systems.physics.shapes.*;
import amara.game.maps.visuals.*;

/**
 *
 * @author Carl
 */
public class TestMapToWrite extends Map{

    public TestMapToWrite(){
        ArrayList<Shape> obstacles = new ArrayList<Shape>();
        obstacles.add(new SimpleConvex(new Vector2D(0, 0), new Vector2D(100, 0)));
        obstacles.add(new SimpleConvex(new Vector2D(100, 0), new Vector2D(100, 100)));
        obstacles.add(new SimpleConvex(new Vector2D(100, 100), new Vector2D(0, 100)));
        obstacles.add(new SimpleConvex(new Vector2D(0, 100), new Vector2D(0, 0)));
        obstacles.add(new Rectangle(10, 7, 5, 8));
        obstacles.add(new Circle(30, 25, 3));
        physicsInformation = new MapPhysicsInformation(100, 100, 0.0225f, obstacles);
        for(int x=0;x<10;x++){
            for(int y=0;y<5;y++){
                visuals.addVisual(new ModelVisual("Models/tree/skin.xml", new Vector3f(50 + x*3, 2, 10 + y*3), new Vector3f(0, 0, -1), 1));
                visuals.addVisual(new ModelVisual("Models/tree_2/skin.xml", new Vector3f(50 + x*3, 2, 30 + y*3), new Vector3f(0, 0, -1), 1));
            }
        }
    }

    @Override
    public void load(EntityWorld entityWorld){
        
    }

    @Override
    public void spawn(EntityWorld entityWorld, int playerEntity){
        
    }
}
