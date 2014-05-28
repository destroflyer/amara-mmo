/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.systems.physics.intersectionHelper.*;
import amara.game.entitysystem.systems.physics.shapes.*;
import amara.game.entitysystem.systems.physics.shapes.PolygonMath.*;
import com.jme3.math.Vector2f;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Philipp
 */
public final class MapIntersectionSystem implements EntitySystem
{
    private MapGrid<MapObstacle> mapGrid;
    private ArrayList<Shape> shapes = new ArrayList<Shape>();
    private int cellSize = 10;
    private Polygon mapPoly;

    public MapIntersectionSystem(int mapWidth, int mapHeight, List<Shape> shapes)
    {
        this(mapWidth, mapHeight);
        if(shapes.isEmpty()) return;
        AddObstacles(shapes);
    }
    public MapIntersectionSystem(int mapWidth, int mapHeight)
    {
         mapGrid = new MapGrid(mapWidth / cellSize + 1, mapHeight / cellSize + 1, cellSize);
         PolygonBuilder builder = new PolygonBuilder();
         builder.nextOutline(true);
         builder.add(0, 0);
         builder.add(mapWidth, 0);
         builder.add(mapWidth, mapHeight);
         builder.add(0, mapHeight);
         mapPoly = builder.build(true);
         assert mapPoly.isInfinite();
    }
    
    public void update(EntityWorld entityWorld, float deltaSeconds)
    {
        for (EntityWrapper entity : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(HitboxComponent.class, HitboxActiveComponent.class, CollisionGroupComponent.class, PositionComponent.class)))
        {
            CollisionGroupComponent filterComp = entity.getComponent(CollisionGroupComponent.class);
            if(CollisionGroupComponent.groupsCollide(CollisionGroupComponent.COLLISION_GROUP_MAP, filterComp.getCollidesWithGroups()))
            {
                solveIntersections(entity);
            }
        }
    }
    
    private void solveIntersections(EntityWrapper entity)
    {
        Shape previous = entity.getComponent(HitboxComponent.class).getShape();
        List<MapObstacle> obstacles = mapGrid.getAllIntersectionPartners(previous);
        if(obstacles.isEmpty()) return;
        
        Vector2f position = entity.getComponent(PositionComponent.class).getPosition();
        Vector2D delta = new Vector2D();
        
        if(obstacles.size() == 1)
        {
            delta.add(previous.getResolveVector(obstacles.get(0).getShape()));
            delta.addLength(1e-6d);
        }
        Vector2D newPosition = new Vector2D(position.x + delta.getX(), position.y + delta.getY());
        
        Shape shape = previous.clone();
        shape.getTransform().setPosition(newPosition.getX(), newPosition.getY());
        if(!mapGrid.getAllIntersectionPartners(shape).isEmpty())
        {
            Polygon poly = PolyHelper.fromShape(shape);
            Point2D overlap = PolyHelper.stepwiseOverlap(mapPoly, poly);
            newPosition = new Vector2D(position.x + overlap.getX(), position.y + overlap.getY());
            shape.getTransform().setPosition(newPosition.getX(), newPosition.getY());
        }
        assert mapGrid.getAllIntersectionPartners(shape).isEmpty();
        
        entity.setComponent(new PositionComponent(new Vector2f((float)newPosition.getX(), (float)newPosition.getY())));
    }
    
    public void AddObstacles(List<Shape> shapes)
    {
        if(shapes.isEmpty()) return;
        //System.out.println("generating map polygon...");
        //PolygonBuilder builder = new PolygonBuilder();
        //Polygon inf = builder.build(true);
        
        ArrayList<Polygon> polys = new ArrayList<Polygon>();
        
        //int remaining = shapes.size();
        for (Shape shape : shapes)
        {
            mapGrid.addObstacle(new MapObstacle(shape));
            Polygon p = PolyHelper.fromShape(shape);
            polys.add(p);
            //mapPoly = mapPoly.union(p);
            //if(mapPoly.equals(inf)) throw new Error();
            //System.out.println("" + --remaining);
        }
        this.shapes.addAll(shapes);
        //System.out.println("" + mapPoly);
        //long millis = System.currentTimeMillis();
        
        mapPoly = Polygon.preSortedUnion(polys).union(mapPoly);
//////        mapPoly.writeToFile("map");
//////        
//////        Polygon pathPoly = mapPoly.inverse();
//////        ArrayList<Point2D> tris = pathPoly.triangles();
//////        ArrayList<Polygon> polyTris = new ArrayList<Polygon>();
//////        PolygonBuilder builder = new PolygonBuilder();
//////        for (int i = 0; i < tris.size(); i += 3)
//////        {
//////            builder.nextOutline(false);
//////            builder.add(tris.get(i));
//////            builder.add(tris.get(i + 1));
//////            builder.add(tris.get(i + 2));
//////            polyTris.add(builder.build(false));
//////            builder.reset();
//////        }
//////        
//////        Polygon nextPathPolys = Polygon.preSortedUnion(polyTris);
//////        nextPathPolys.writeToFile("path");
//////        //System.out.println("map polygon generation - " + (System.currentTimeMillis() - millis) + "ms");
//////        //mapPoly.writeToFile("mapPoly");
//////        //System.out.println("wrote mapPoly");
    }
    
    public ArrayList<Shape> getObstacles()
    {
        return shapes;
    }
}
