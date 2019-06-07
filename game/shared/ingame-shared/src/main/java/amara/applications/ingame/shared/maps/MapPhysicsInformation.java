/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.shared.maps;

import java.util.ArrayList;
import amara.libraries.physics.PolyHelper;
import amara.libraries.physics.intersectionHelper.PolyMapManager;
import amara.libraries.physics.shapes.ConvexShape;

/**
 *
 * @author Carl
 */
public class MapPhysicsInformation{

    public MapPhysicsInformation(float width, float height, float heightmapScale, float groundHeight, ArrayList<ConvexShape> obstacles){
        this.width = width;
        this.height = height;
        this.heightmapScale = heightmapScale;
        this.groundHeight = groundHeight;
        this.obstacles = obstacles;
        polyMapManager = new PolyMapManager(PolyHelper.fromConvexShapes(obstacles), width, height);
    }
    private float width;
    private float height;
    private float heightmapScale;
    private float groundHeight;
    private ArrayList<ConvexShape> obstacles;
    private PolyMapManager polyMapManager;

    public float getWidth(){
        return width;
    }

    public float getHeight(){
        return height;
    }

    public float getGroundHeight(){
        return groundHeight;
    }

    public float getHeightmapScale(){
        return heightmapScale;
    }

    public ArrayList<ConvexShape> getObstacles(){
        return obstacles;
    }

    public PolyMapManager getPolyMapManager(){
        return polyMapManager;
    }
}