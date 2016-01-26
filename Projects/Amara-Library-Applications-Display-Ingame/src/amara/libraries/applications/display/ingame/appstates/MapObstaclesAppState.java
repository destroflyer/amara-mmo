/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.display.ingame.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.scene.Spatial;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import amara.applications.ingame.shared.maps.Map;
import amara.libraries.applications.display.DisplayApplication;
import amara.libraries.applications.display.appstates.BaseDisplayAppState;
import amara.libraries.applications.display.materials.MaterialFactory;
import amara.libraries.applications.display.meshes.*;
import amara.libraries.physics.shapes.*;
import amara.libraries.physics.shapes.PolygonMath.Polygon;

/**
 *
 * @author Carl
 */
public class MapObstaclesAppState extends BaseDisplayAppState<DisplayApplication> implements ActionListener{

    private Node node = new Node();
    private Node obstaclesNode = new Node();
    private boolean displayObstacles = true;
    
    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        mainApplication.getInputManager().addMapping("toggle_hitboxes", new KeyTrigger(KeyInput.KEY_H));
        mainApplication.getInputManager().addListener(this, new String[]{
            "toggle_hitboxes"
        });
        node.setLocalTranslation(0, 1, 0);
        node.attachChild(obstaclesNode);
        mainApplication.getRootNode().attachChild(node);
        update();
    }
    
    public void update(){
        obstaclesNode.detachAllChildren();
        Map map = getAppState(MapAppState.class).getMap();
        for(Shape shape : map.getPhysicsInformation().getObstacles()){
            Geometry collisionMeshGeometry = generateGeometry(shape);
            collisionMeshGeometry.setLocalTranslation((float) shape.getTransform().extractX(), 0, (float) shape.getTransform().extractY());
            obstaclesNode.attachChild(collisionMeshGeometry);
        }
    }

    @Override
    public void onAction(String name, boolean isPressed, float lastTimePerFrame){
        if(name.equals("toggle_hitboxes") && isPressed){
            setDisplayObstacles(!displayObstacles);
        }
    }

    public Node getObstaclesNode(){
        return obstaclesNode;
    }

    public void setDisplayObstacles(boolean displayObstacles){
        this.displayObstacles = displayObstacles;
        obstaclesNode.setCullHint(displayObstacles?Spatial.CullHint.Inherit: Spatial.CullHint.Always);
    }

    public boolean areObstaclesDisplayed(){
        return displayObstacles;
    }
    
    public static Geometry generateGeometry(Shape shape){
        return generateGeometry(shape, true);
    }
    
    public static Geometry generateGeometry(Shape shape, boolean isActive){
        Mesh collisionMesh;
        ColorRGBA meshColor = (isActive?ColorRGBA.Blue:ColorRGBA.LightGray);
        if(shape instanceof Circle){
            Circle circle = (Circle) shape;
            Vector3f center = new Vector3f((float) circle.getLocalPosition().getX(), 0, (float) circle.getLocalPosition().getY());
            collisionMesh = new CircleMesh(center, (float) circle.getGlobalRadius(), 64);
        }
        else if(shape instanceof SimpleConvexPolygon){
            SimpleConvexPolygon simpleConvex = (SimpleConvexPolygon) shape;
            collisionMesh = new ConnectedPointsMesh((float) simpleConvex.getTransform().extractX(), (float) simpleConvex.getTransform().extractY(), simpleConvex.getGlobalPoints());
        }
        else if(shape instanceof PolygonShape){
            PolygonShape polygonShape = (PolygonShape) shape;
            Polygon polygon = polygonShape.getGlobalPolygon();
            collisionMesh = new LinesMesh((float) polygonShape.getTransform().extractX(), (float) polygonShape.getTransform().extractY(), polygon.outlines());
        }
        else if(shape instanceof ConvexShape){
            ConvexShape convexShape = (ConvexShape) shape;
            collisionMesh = new CircleMesh((float) convexShape.getBoundCircle().getGlobalRadius(), 64);
            meshColor = ColorRGBA.Red;
        }
        else{
            throw new UnsupportedOperationException();
        }
        return generateGeometry(collisionMesh, meshColor);
    }
    
    public static Geometry generateGeometry(Mesh mesh, ColorRGBA color){
        Geometry collisionMeshGeometry = new Geometry("", mesh);
        collisionMeshGeometry.setMaterial(MaterialFactory.generateUnshadedMaterial(color));
        collisionMeshGeometry.getMaterial().getAdditionalRenderState().setDepthTest(false);
        collisionMeshGeometry.setUserData("layer", 999);
        return collisionMeshGeometry;
    }
}