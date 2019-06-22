/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.shared.maps;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;
import javax.swing.filechooser.FileFilter;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.math.ColorRGBA;
import amara.applications.ingame.shared.maps.lights.*;
import amara.applications.ingame.shared.maps.terrain.*;
import amara.applications.ingame.shared.maps.visuals.*;
import amara.core.Util;
import amara.core.files.*;
import amara.libraries.physics.shapes.*;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author Carl
 */
public class MapFileHandler{
    
    private static final String VECTOR_COORDINATES_SEPARATOR = ",";
    public static String FILE_EXTENSION = "xml";
    public static FileFilter FILE_FILTER = new FileFilter(){

        @Override
        public boolean accept(File file){
            return (file.isDirectory() || file.getPath().toLowerCase().endsWith("" + FILE_EXTENSION));
        }

        @Override
        public String getDescription(){
            return "Amara map file (*." + FILE_EXTENSION + ")";
        }
    };
    
    public static void saveFile(Map map, File file){
        try{
            Element root = new Element("map");
            root.setAttribute("author", System.getProperty("user.name"));
            root.setAttribute("date", "" + System.currentTimeMillis());
            Class mapClass = map.getClass();
            if(mapClass == TestMapToWrite.class){
                mapClass = Class.forName("amara.applications.ingame.maps.Map_Testmap");
            }
            root.setAttribute("class", mapClass.getName());
            Document document = new Document(root);
            //Camera
            Element elementCamera = new Element("camera");
            elementCamera.setAttribute("initialPosition", "" + generateVectorText(map.getCamera().getInitialPosition()));
            elementCamera.setAttribute("initialDirection", "" + generateVectorText(map.getCamera().getInitialDirection()));
            Element elementLimit = new Element("limit");
            MapCamera_Limit cameraLimit = map.getCamera().getLimit();
            if(cameraLimit != null){
                elementLimit.setAttribute("minX", "" + cameraLimit.getMinimum().getX());
                elementLimit.setAttribute("minY", "" + cameraLimit.getMinimum().getY());
                elementLimit.setAttribute("maxX", "" + cameraLimit.getMaximum().getX());
                elementLimit.setAttribute("maxY", "" + cameraLimit.getMaximum().getY());
                elementCamera.addContent(elementLimit);
            }
            MapCamera_Zoom cameraZoom = map.getCamera().getZoom();
            Element elementZoom = new Element("zoom");
            elementZoom.setAttribute("interval", "" + cameraZoom.getInterval());
            elementZoom.setAttribute("initialDistance", "" + cameraZoom.getInitialDistance());
            if(cameraZoom.getMinimumDistance() != -1){
                elementZoom.setAttribute("minimumDistance", "" + cameraZoom.getMinimumDistance());
            }
            if(cameraZoom.getMaximumDistance() != -1){
                elementZoom.setAttribute("maximumDistance", "" + cameraZoom.getMaximumDistance());
            }
            elementCamera.addContent(elementZoom);
            root.addContent(elementCamera);
            //Lights
            Element elementLights = new Element("lights");
            for(MapLight mapLight : map.getLights().getMapLights()){
                Element elementLight = null;
                if(mapLight instanceof MapLight_Ambient){
                    MapLight_Ambient mapLight_Ambient = (MapLight_Ambient) mapLight;
                    elementLight = new Element("ambient");
                }
                else if(mapLight instanceof MapLight_Directional){
                    MapLight_Directional mapLight_Directional = (MapLight_Directional) mapLight;
                    elementLight = new Element("directional");
                    elementLight.setAttribute("direction", generateVectorText(mapLight_Directional.getDirection()));
                    MapLight_Directional_Shadows shadows = mapLight_Directional.getShadows();
                    if(shadows != null){
                        Element elementShadows = new Element("shadows");
                        elementShadows.setAttribute("intensity", "" + shadows.getIntensity());
                        elementLight.addContent(elementShadows);
                    }
                }
                if(elementLight != null){
                    elementLight.setAttribute("color", generateVectorText(mapLight.getColor().toVector3f()));
                    elementLights.addContent(elementLight);
                }
            }
            root.addContent(elementLights);
            //Terrain
            Element elementTerrain = new Element("terrain");
            TerrainSkin terrainSkin = map.getTerrainSkin();
            Element elementTerrainTextures = new Element("textures");
            for(TerrainSkin_Texture texture : terrainSkin.getTextures()){
                Element elementTexture = new Element("texture");
                elementTexture.setAttribute("scale", "" + texture.getScale());
                elementTexture.setText(texture.getFilePath());
                elementTerrainTextures.addContent(elementTexture);
            }
            elementTerrain.addContent(elementTerrainTextures);
            root.addContent(elementTerrain);
            //Minimap
            Element elementMinimap = new Element("minimap");
            elementMinimap.setAttribute("x", "" + map.getMinimapInformation().getX());
            elementMinimap.setAttribute("y", "" + map.getMinimapInformation().getY());
            elementMinimap.setAttribute("width", "" + map.getMinimapInformation().getWidth());
            elementMinimap.setAttribute("height", "" + map.getMinimapInformation().getHeight());
            root.addContent(elementMinimap);
            //Physics
            Element elementPhysics = new Element("physics");
            elementPhysics.setAttribute("width", "" + map.getPhysicsInformation().getWidth());
            elementPhysics.setAttribute("height", "" + map.getPhysicsInformation().getHeight());
            elementPhysics.setAttribute("heightmapScale", "" + map.getPhysicsInformation().getHeightmapScale());
            elementPhysics.setAttribute("groundHeight", "" + map.getPhysicsInformation().getGroundHeight());
            Element elementObstacles = new Element("obstacles");
            for(ConvexShape shape : map.getPhysicsInformation().getObstacles()){
                Element elementShape = generateElement(shape);
                if(elementShape != null){
                    elementObstacles.addContent(elementShape);
                }
            }
            elementPhysics.addContent(elementObstacles);
            root.addContent(elementPhysics);
            //Visuals
            Element elementVisuals = new Element("visuals");
            for(MapVisual mapVisual : map.getVisuals().getMapVisuals()){
                Element elementVisual = null;
                if(mapVisual instanceof ModelVisual){
                    ModelVisual modelVisual = (ModelVisual) mapVisual;
                    elementVisual = new Element("model");
                    elementVisual.setAttribute("modelSkinPath", modelVisual.getModelSkinPath());
                    elementVisual.setAttribute("position", generateVectorText(modelVisual.getPosition()));
                    elementVisual.setAttribute("direction", generateVectorText(modelVisual.getDirection()));
                    elementVisual.setAttribute("scale", "" + modelVisual.getScale());
                }
                else if(mapVisual instanceof WaterVisual){
                    WaterVisual waterVisual = (WaterVisual) mapVisual;
                    elementVisual = new Element("water");
                    elementVisual.setAttribute("position", generateVectorText(waterVisual.getPosition()));
                    elementVisual.setAttribute("size", generateVectorText(waterVisual.getSize()));
                }
                else if(mapVisual instanceof SnowVisual){
                    SnowVisual snowVisual = (SnowVisual) mapVisual;
                    elementVisual = new Element("snow");
                }
                if(elementVisual != null){
                    elementVisuals.addContent(elementVisual);
                }
            }
            root.addContent(elementVisuals);
            FileManager.putFileContent(file.getPath(), new XMLOutputter().outputString(document));
        }catch(Exception ex){
            System.err.println("Error while saving the map: " + ex.toString());
            ex.printStackTrace();
        }
    }

    public static Map load(String mapName){
        return load(mapName, true);
    }

    public static Map load(String mapName, boolean loadContents){
        try{
            InputStream inputStream = FileAssets.getInputStream("Maps/" + mapName + "/map.xml");
            Document document = new SAXBuilder().build(inputStream);
            Element root = document.getRootElement();
            Map map = createMapObject(root, mapName);
            if(loadContents){
                load(map, root);
            }
            return map;
        }catch(Exception ex){
            System.err.println("Error while loading the map: " + ex.toString());
            ex.printStackTrace();
        }
        return null;
    }

    private static Map load(Map map, Element root){
        try{
            //Camera
            Element elementCamera = root.getChild("camera");
            Vector3f initialPosition = generateVector3f(elementCamera.getAttributeValue("initialPosition"));
            Vector3f initialDirection = generateVector3f(elementCamera.getAttributeValue("initialDirection"));
            MapCamera camera = new MapCamera(initialPosition, initialDirection);
            Element elementLimit = elementCamera.getChild("limit");
            if(elementLimit != null){
                Vector2f limitMinimum = new Vector2f(elementLimit.getAttribute("minX").getFloatValue(), elementLimit.getAttribute("minY").getFloatValue());
                Vector2f limitMaximum = new Vector2f(elementLimit.getAttribute("maxX").getFloatValue(), elementLimit.getAttribute("maxY").getFloatValue());
                camera.setLimit(new MapCamera_Limit(limitMinimum, limitMaximum));
            }
            Element elementZoom = elementCamera.getChild("zoom");
            float zoomInterval = elementZoom.getAttribute("interval").getFloatValue();
            float zoomInitialDistance = elementZoom.getAttribute("initialDistance").getFloatValue();
            float zoomMinimumDistance = -1;
            Attribute attributeZoomMinimumDistance = elementZoom.getAttribute("minimumDistance");
            if(attributeZoomMinimumDistance != null){
                zoomMinimumDistance = attributeZoomMinimumDistance.getFloatValue();
            }
            float zoomMaximumDistance = -1;
            Attribute attributeZoomMaximumDistance = elementZoom.getAttribute("maximumDistance");
            if(attributeZoomMaximumDistance != null){
                zoomMaximumDistance = attributeZoomMaximumDistance.getFloatValue();
            }
            camera.setZoom(new MapCamera_Zoom(zoomInterval, zoomInitialDistance, zoomMinimumDistance, zoomMaximumDistance));
            map.setCamera(camera);
            //Lights
            Element elementLights = root.getChild("lights");
            for(Object elementLightObject : elementLights.getChildren()){
                Element elemenLight = (Element) elementLightObject;
                MapLight mapLight = null;
                ColorRGBA color = generateColorRGBA(elemenLight.getAttributeValue("color"));
                if(elemenLight.getName().equals("ambient")){
                    mapLight = new MapLight_Ambient(color);
                }
                else if(elemenLight.getName().equals("directional")){
                    Vector3f direction = generateVector3f(elemenLight.getAttributeValue("direction"));
                    MapLight_Directional mapLight_Directional = new MapLight_Directional(color, direction);
                    Element elementShadows = elemenLight.getChild("shadows");
                    if(elementShadows != null){
                        float intensity = elementShadows.getAttribute("intensity").getFloatValue();
                        MapLight_Directional_Shadows shadows = new MapLight_Directional_Shadows(intensity);
                        mapLight_Directional.setShadows(shadows);
                    }
                    mapLight = mapLight_Directional;
                }
                if(mapLight != null){
                    map.getLights().addLight(mapLight);
                }
            }
            //Terrain
            Element elementTerrain = root.getChild("terrain");
            Element elementTerrainTextures = elementTerrain.getChild("textures");
            List elementTerrainTexturesChildren = elementTerrainTextures.getChildren();
            TerrainSkin_Texture[] terrainSkinTextures = new TerrainSkin_Texture[elementTerrainTexturesChildren.size()];
            for(int i=0;i<terrainSkinTextures.length;i++){
                Element elementTexture = (Element) elementTerrainTexturesChildren.get(i);
                String filePath = elementTexture.getText();
                float scale = elementTexture.getAttribute("scale").getFloatValue();
                terrainSkinTextures[i] = new TerrainSkin_Texture(filePath, scale);
            }
            TerrainSkin terrainSkin = new TerrainSkin(terrainSkinTextures);
            map.setTerrainSkin(terrainSkin);
            //Minimap
            Element elementMinimap = root.getChild("minimap");
            float minimapX = elementMinimap.getAttribute("x").getFloatValue();
            float minimapY = elementMinimap.getAttribute("y").getFloatValue();
            float minimapWidth = elementMinimap.getAttribute("width").getFloatValue();
            float minimumHeight = elementMinimap.getAttribute("height").getFloatValue();
            MapMinimapInformation minimapInformation = new MapMinimapInformation(minimapX, minimapY, minimapWidth, minimumHeight);
            map.setMinimapInformation(minimapInformation);
            //Physics
            Element elementPhysics = root.getChild("physics");
            float width = elementPhysics.getAttribute("width").getFloatValue();
            float height = elementPhysics.getAttribute("height").getFloatValue();
            float heightmapScale = elementPhysics.getAttribute("heightmapScale").getFloatValue();
            float groundHeight = elementPhysics.getAttribute("groundHeight").getFloatValue();
            Element elementObstacles = elementPhysics.getChild("obstacles");
            ArrayList<ConvexShape> obstacles = new ArrayList<ConvexShape>();
            for(Object elementShapeObject : elementObstacles.getChildren()){
                ConvexShape shape = generateShape((Element) elementShapeObject);
                obstacles.add(shape);
            }
            MapPhysicsInformation physicsInformation = new MapPhysicsInformation(width, height, heightmapScale, groundHeight, obstacles);
            map.setPhysicsInformation(physicsInformation);
            //Visuals
            Element elementVisuals = root.getChild("visuals");
            for(Object elementVisualObject : elementVisuals.getChildren()){
                Element elementVisual = (Element) elementVisualObject;
                MapVisual mapVisual = null;
                if(elementVisual.getName().equals("model")){
                    String modelSkinPath = elementVisual.getAttributeValue("modelSkinPath");
                    Vector3f position = generateVector3f(elementVisual.getAttributeValue("position"));
                    Vector3f direction = generateVector3f(elementVisual.getAttributeValue("direction"));
                    float scale = elementVisual.getAttribute("scale").getFloatValue();
                    mapVisual = new ModelVisual(modelSkinPath, position, direction, scale);
                }
                else if(elementVisual.getName().equals("water")){
                    Vector3f position = generateVector3f(elementVisual.getAttributeValue("position"));
                    Vector2f size = generateVector2f(elementVisual.getAttributeValue("size"));
                    mapVisual = new WaterVisual(position, size);
                }
                else if(elementVisual.getName().equals("snow")){
                    mapVisual = new SnowVisual();
                }
                if(mapVisual != null){
                    map.getVisuals().addVisual(mapVisual);
                }
            }
            return map;
        }catch(Exception ex){
            System.err.println("Error while loading the map contents: " + ex.toString());
            ex.printStackTrace();
        }
        return null;
    }

    private static Map createMapObject(Element root, String mapName){
        Map map = Util.createObjectByClassName(root.getAttributeValue("class"), Map.class);
        map.setName(mapName);
        return map;
    }
    
    private static Vector2D generateVector2D(String text){
        float[] coordinates = Util.parseToFloatArray(text.split(VECTOR_COORDINATES_SEPARATOR));
        return new Vector2D(coordinates[0], coordinates[1]);
    }
    
    private static Element generateElement(ConvexShape shape){
        Element element = null;
        if(shape instanceof Circle){
            Circle circle = (Circle) shape;
            element = new Element("circle");
            element.setAttribute("radius", "" + circle.getGlobalRadius());
        }
        else if(shape instanceof SimpleConvexPolygon){
            SimpleConvexPolygon simpleConvex = (SimpleConvexPolygon) shape;
            element = new Element("simpleConvex");
            Element elementBase = new Element("base");
            for(Vector2D point : simpleConvex.getLocalPoints()){
                Element elementPoint = new Element("point");
                elementPoint.setText(generateVectorText(point));
                elementBase.addContent(elementPoint);
            }
            element.addContent(elementBase);
        }
        Element elementTransform = new Element("transform");
        elementTransform.setAttribute("x", "" + shape.getTransform().extractX());
        elementTransform.setAttribute("y", "" + shape.getTransform().extractY());
        elementTransform.setAttribute("direction", "" + shape.getTransform().extractRadians());
        elementTransform.setAttribute("scale", "" + shape.getTransform().extractScale());
        element.addContent(elementTransform);
        return element;
    }
    
    private static ConvexShape generateShape(Element element){
        ConvexShape shape = null;
        if(element.getName().equals("circle")){
            double radius = Double.parseDouble(element.getAttributeValue("radius"));
            shape = new Circle(radius);
        }
        else if(element.getName().equals("simpleConvex")){
            Element elementBase = element.getChild("base");
            List elementBaseChildren = elementBase.getChildren();
            Vector2D[] base = new Vector2D[elementBaseChildren.size()];
            for(int i=0;i<elementBaseChildren.size();i++){
                Element elementPoint = (Element) elementBaseChildren.get(i);
                base[i] = generateVector2D(elementPoint.getText());
            }
            shape = new SimpleConvexPolygon(base);
        }
        else throw new UnsupportedOperationException();
        Element elementTransform = element.getChild("transform");
        shape.setTransform(new Transform2D(
            Double.parseDouble(elementTransform.getAttributeValue("scale")),
            Double.parseDouble(elementTransform.getAttributeValue("direction")),
            Double.parseDouble(elementTransform.getAttributeValue("x")),
            Double.parseDouble(elementTransform.getAttributeValue("y"))
        ));
        return shape;
    }
    
    private static String generateVectorText(Vector2f vector2f){
        return (vector2f.getX() + VECTOR_COORDINATES_SEPARATOR + vector2f.getY());
    }
    
    private static String generateVectorText(Vector3f vector3f){
        return (vector3f.getX() + VECTOR_COORDINATES_SEPARATOR + vector3f.getY() + VECTOR_COORDINATES_SEPARATOR + vector3f.getZ());
    }
    
    private static String generateVectorText(Vector2D vector2D){
        return (vector2D.getX() + VECTOR_COORDINATES_SEPARATOR + vector2D.getY());
    }
    
    private static Vector2f generateVector2f(String text){
        float[] coordinates = Util.parseToFloatArray(text.split(VECTOR_COORDINATES_SEPARATOR));
        return new Vector2f(coordinates[0], coordinates[1]);
    }
    
    private static ColorRGBA generateColorRGBA(String text){
        Vector3f colorVector = generateVector3f(text);
        return new ColorRGBA(colorVector.getX(), colorVector.getY(), colorVector.getZ(), 1);
    }
    
    private static Vector3f generateVector3f(String text){
        float[] coordinates = Util.parseToFloatArray(text.split(VECTOR_COORDINATES_SEPARATOR));
        return new Vector3f(coordinates[0], coordinates[1], coordinates[2]);
    }
}
