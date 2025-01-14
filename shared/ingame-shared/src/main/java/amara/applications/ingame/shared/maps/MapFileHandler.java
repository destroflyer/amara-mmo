package amara.applications.ingame.shared.maps;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;
import javax.swing.filechooser.FileFilter;

import amara.applications.ingame.shared.maps.cameras.*;
import amara.applications.ingame.shared.maps.filters.*;
import amara.applications.ingame.shared.maps.lights.*;
import amara.applications.ingame.shared.maps.obstacles.*;
import amara.applications.ingame.shared.maps.terrain.*;
import amara.applications.ingame.shared.maps.visuals.*;
import amara.core.Util;
import amara.core.files.*;
import amara.libraries.physics.shapes.*;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.math.ColorRGBA;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

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

    public static void saveFile(Map map, File file) {
        try {
            Element root = new Element("map");
            root.setAttribute("author", System.getProperty("user.name"));
            root.setAttribute("date", "" + System.currentTimeMillis());
            root.setAttribute("class", map.getClass().getName());
            Document document = new Document(root);
            // Camera
            Element elementCamera = new Element("camera");
            MapCamera camera = map.getCamera();
            elementCamera.setAttribute("type", camera.getType());
            MapCamera_Zoom cameraZoom = camera.getZoom();
            Element elementZoom = new Element("zoom");
            elementZoom.setAttribute("interval", "" + cameraZoom.getInterval());
            elementZoom.setAttribute("initialDistance", "" + cameraZoom.getInitialDistance());
            if (cameraZoom.getMinimumDistance() != -1) {
                elementZoom.setAttribute("minimumDistance", "" + cameraZoom.getMinimumDistance());
            }
            if (cameraZoom.getMaximumDistance() != -1) {
                elementZoom.setAttribute("maximumDistance", "" + cameraZoom.getMaximumDistance());
            }
            elementCamera.addContent(elementZoom);
            if (camera instanceof MapCamera_TopDown camera_TopDown) {
                elementCamera.setAttribute("initialPosition", "" + generateVectorText(camera_TopDown.getInitialPosition()));
                elementCamera.setAttribute("initialDirection", "" + generateVectorText(camera_TopDown.getInitialDirection()));
                Element elementLimit = new Element("limit");
                MapCamera_Limit cameraLimit = camera_TopDown.getLimit();
                if (cameraLimit != null) {
                    elementLimit.setAttribute("minX", "" + cameraLimit.getMinimum().getX());
                    elementLimit.setAttribute("minY", "" + cameraLimit.getMinimum().getY());
                    elementLimit.setAttribute("maxX", "" + cameraLimit.getMaximum().getX());
                    elementLimit.setAttribute("maxY", "" + cameraLimit.getMaximum().getY());
                    elementCamera.addContent(elementLimit);
                }
            } else if (camera instanceof MapCamera_3rdPerson camera_3rdPerson) {
                elementCamera.setAttribute("initialRotationHorizontal", "" + camera_3rdPerson.getInitialRotationHorizontal());
                elementCamera.setAttribute("initialRotationVertical", "" + camera_3rdPerson.getInitialRotationVertical());
            }
            root.addContent(elementCamera);
            // Lights
            Element elementLights = new Element("lights");
            for (MapLight mapLight : map.getLights()) {
                Element elementLight = null;
                if (mapLight instanceof MapLight_Ambient) {
                    elementLight = new Element("ambient");
                } else if (mapLight instanceof MapLight_Directional mapLight_Directional) {
                    elementLight = new Element("directional");
                    elementLight.setAttribute("direction", generateVectorText(mapLight_Directional.getDirection()));
                    MapLight_Directional_Shadows shadows = mapLight_Directional.getShadows();
                    if(shadows != null){
                        Element elementShadows = new Element("shadows");
                        elementShadows.setAttribute("intensity", "" + shadows.getIntensity());
                        elementLight.addContent(elementShadows);
                    }
                }
                if (elementLight != null) {
                    elementLight.setAttribute("color", generateVectorText(mapLight.getColor().toVector3f()));
                    elementLights.addContent(elementLight);
                }
            }
            root.addContent(elementLights);
            // Filters
            Element elementFilters = new Element("filters");
            for (MapFilter mapFilter : map.getFilters()) {
                Element elementFilter = null;
                if (mapFilter instanceof MapFilter_SSAO mapFilter_SSAO) {
                    elementFilter = new Element("ssao");
                    elementFilter.setAttribute("sampleRadius", "" + mapFilter_SSAO.getSampleRadius());
                    elementFilter.setAttribute("intensity", "" + mapFilter_SSAO.getIntensity());
                    elementFilter.setAttribute("scale", "" + mapFilter_SSAO.getScale());
                    elementFilter.setAttribute("bias", "" + mapFilter_SSAO.getBias());
                }
                if (elementFilter != null) {
                    elementFilters.addContent(elementFilter);
                }
            }
            root.addContent(elementFilters);
            //Terrain
            Element elementTerrain = new Element("terrain");
            TerrainSkin terrainSkin = map.getTerrainSkin();
            Element elementTerrainTextures = new Element("textures");
            for (TerrainSkin_Texture texture : terrainSkin.getTextures()) {
                Element elementTexture = new Element("texture");
                elementTexture.setAttribute("scale", "" + texture.getScale());
                elementTexture.setText(texture.getFilePath());
                elementTerrainTextures.addContent(elementTexture);
            }
            elementTerrain.addContent(elementTerrainTextures);
            root.addContent(elementTerrain);
            // Minimap
            Element elementMinimap = new Element("minimap");
            elementMinimap.setAttribute("x", "" + map.getMinimapInformation().getX());
            elementMinimap.setAttribute("y", "" + map.getMinimapInformation().getY());
            elementMinimap.setAttribute("width", "" + map.getMinimapInformation().getWidth());
            elementMinimap.setAttribute("height", "" + map.getMinimapInformation().getHeight());
            root.addContent(elementMinimap);
            // Physics
            Element elementPhysics = new Element("physics");
            elementPhysics.setAttribute("width", "" + map.getPhysicsInformation().getWidth());
            elementPhysics.setAttribute("height", "" + map.getPhysicsInformation().getHeight());
            elementPhysics.setAttribute("heightmapScale", "" + map.getPhysicsInformation().getHeightmapScale());
            elementPhysics.setAttribute("groundHeight", "" + map.getPhysicsInformation().getGroundHeight());
            Element elementObstacles = new Element("obstacles");
            for (MapObstacle mapObstacle : map.getPhysicsInformation().getObstacles()) {
                Element elementObstacle = generateElement(mapObstacle);
                if (elementObstacle != null){
                    elementObstacles.addContent(elementObstacle);
                }
            }
            elementPhysics.addContent(elementObstacles);
            root.addContent(elementPhysics);
            //Visuals
            Element elementVisuals = new Element("visuals");
            for (MapVisual mapVisual : map.getVisuals()) {
                Element elementVisual = null;
                if (mapVisual instanceof ModelVisual modelVisual) {
                    elementVisual = new Element("model");
                    elementVisual.setAttribute("modelSkinPath", modelVisual.getModelSkinPath());
                    elementVisual.setAttribute("position", generateVectorText(modelVisual.getPosition()));
                    elementVisual.setAttribute("direction", generateVectorText(modelVisual.getDirection()));
                    elementVisual.setAttribute("scale", "" + modelVisual.getScale());
                }
                else if (mapVisual instanceof WaterVisual waterVisual) {
                    elementVisual = new Element("water");
                    elementVisual.setAttribute("position", generateVectorText(waterVisual.getPosition()));
                    elementVisual.setAttribute("size", generateVectorText(waterVisual.getSize()));
                } else if (mapVisual instanceof SnowVisual) {
                    elementVisual = new Element("snow");
                }
                if (elementVisual != null) {
                    elementVisuals.addContent(elementVisual);
                }
            }
            root.addContent(elementVisuals);
            FileManager.putFileContent(file.getPath(), new XMLOutputter().outputString(document));
        } catch (Exception ex) {
            System.err.println("Error while saving the map: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static Map load(String mapName) {
        return load(mapName, true);
    }

    public static Map load(String mapName, boolean loadContents) {
        try {
            InputStream inputStream = FileAssets.getInputStream("Maps/" + mapName + "/map.xml");
            Document document = new SAXBuilder().build(inputStream);
            Element root = document.getRootElement();
            Map map = createMapObject(root, mapName);
            if (loadContents) {
                load(map, root);
            }
            return map;
        } catch(Exception ex) {
            System.err.println("Error while loading the map: " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    private static Map load(Map map, Element root) {
        try {
            // Camera
            MapCamera camera = null;
            Element elementCamera = root.getChild("camera");

            Element elementZoom = elementCamera.getChild("zoom");
            float zoomInterval = elementZoom.getAttribute("interval").getFloatValue();
            float zoomInitialDistance = elementZoom.getAttribute("initialDistance").getFloatValue();
            float zoomMinimumDistance = -1;
            Attribute attributeZoomMinimumDistance = elementZoom.getAttribute("minimumDistance");
            if (attributeZoomMinimumDistance != null) {
                zoomMinimumDistance = attributeZoomMinimumDistance.getFloatValue();
            }
            float zoomMaximumDistance = -1;
            Attribute attributeZoomMaximumDistance = elementZoom.getAttribute("maximumDistance");
            if (attributeZoomMaximumDistance != null) {
                zoomMaximumDistance = attributeZoomMaximumDistance.getFloatValue();
            }
            MapCamera_Zoom zoom = new MapCamera_Zoom(zoomInterval, zoomInitialDistance, zoomMinimumDistance, zoomMaximumDistance);

            String cameraType = elementCamera.getAttributeValue("type");
            switch (cameraType) {
                case MapCamera_TopDown.TYPE -> {
                    Vector3f initialPosition = generateVector3f(elementCamera.getAttributeValue("initialPosition"));
                    Vector3f initialDirection = generateVector3f(elementCamera.getAttributeValue("initialDirection"));
                    MapCamera_Limit limit = null;
                    Element elementLimit = elementCamera.getChild("limit");
                    if (elementLimit != null) {
                        Vector2f limitMinimum = new Vector2f(elementLimit.getAttribute("minX").getFloatValue(), elementLimit.getAttribute("minY").getFloatValue());
                        Vector2f limitMaximum = new Vector2f(elementLimit.getAttribute("maxX").getFloatValue(), elementLimit.getAttribute("maxY").getFloatValue());
                        limit = new MapCamera_Limit(limitMinimum, limitMaximum);
                    }
                    camera = new MapCamera_TopDown(zoom, initialPosition, initialDirection, limit);
                }
                case MapCamera_3rdPerson.TYPE -> {
                    float initialRotationHorizontal = elementCamera.getAttribute("initialRotationHorizontal").getFloatValue();
                    float initialRotationVertical = elementCamera.getAttribute("initialRotationVertical").getFloatValue();
                    camera = new MapCamera_3rdPerson(zoom, initialRotationHorizontal, initialRotationVertical);
                }
            }
            map.setCamera(camera);

            // Lights
            Element elementLights = root.getChild("lights");
            for (Element elementLight : elementLights.getChildren()) {
                MapLight mapLight = null;
                ColorRGBA color = generateColorRGBA(elementLight.getAttributeValue("color"));
                if (elementLight.getName().equals("ambient")) {
                    mapLight = new MapLight_Ambient(color);
                } else if (elementLight.getName().equals("directional")) {
                    Vector3f direction = generateVector3f(elementLight.getAttributeValue("direction"));
                    MapLight_Directional mapLight_Directional = new MapLight_Directional(color, direction);
                    Element elementShadows = elementLight.getChild("shadows");
                    if (elementShadows != null) {
                        float intensity = elementShadows.getAttribute("intensity").getFloatValue();
                        MapLight_Directional_Shadows shadows = new MapLight_Directional_Shadows(intensity);
                        mapLight_Directional.setShadows(shadows);
                    }
                    mapLight = mapLight_Directional;
                }
                if (mapLight != null) {
                    map.addLight(mapLight);
                }
            }
            // Filters
            Element elementFilters = root.getChild("filters");
            for (Element elementFilter : elementFilters.getChildren()) {
                MapFilter mapFilter = null;
                if (elementFilter.getName().equals("ssao")) {
                    float sampleRadius = elementFilter.getAttribute("sampleRadius").getFloatValue();
                    float intensity = elementFilter.getAttribute("intensity").getFloatValue();
                    float scale = elementFilter.getAttribute("scale").getFloatValue();
                    float bias = elementFilter.getAttribute("bias").getFloatValue();
                    mapFilter = new MapFilter_SSAO(sampleRadius, intensity, scale, bias);
                }
                if (mapFilter != null) {
                    map.addFilter(mapFilter);
                }
            }
            // Terrain
            Element elementTerrain = root.getChild("terrain");
            Element elementTerrainTextures = elementTerrain.getChild("textures");
            List<Element> elementTerrainTexturesChildren = elementTerrainTextures.getChildren();
            TerrainSkin_Texture[] terrainSkinTextures = new TerrainSkin_Texture[elementTerrainTexturesChildren.size()];
            for (int i = 0; i < terrainSkinTextures.length; i++) {
                Element elementTexture = elementTerrainTexturesChildren.get(i);
                String filePath = elementTexture.getText();
                float scale = elementTexture.getAttribute("scale").getFloatValue();
                terrainSkinTextures[i] = new TerrainSkin_Texture(filePath, scale);
            }
            TerrainSkin terrainSkin = new TerrainSkin(terrainSkinTextures);
            map.setTerrainSkin(terrainSkin);
            // Minimap
            Element elementMinimap = root.getChild("minimap");
            float minimapX = elementMinimap.getAttribute("x").getFloatValue();
            float minimapY = elementMinimap.getAttribute("y").getFloatValue();
            float minimapWidth = elementMinimap.getAttribute("width").getFloatValue();
            float minimumHeight = elementMinimap.getAttribute("height").getFloatValue();
            MapMinimapInformation minimapInformation = new MapMinimapInformation(minimapX, minimapY, minimapWidth, minimumHeight);
            map.setMinimapInformation(minimapInformation);
            // Physics
            Element elementPhysics = root.getChild("physics");
            float width = elementPhysics.getAttribute("width").getFloatValue();
            float height = elementPhysics.getAttribute("height").getFloatValue();
            float heightmapScale = elementPhysics.getAttribute("heightmapScale").getFloatValue();
            float groundHeight = elementPhysics.getAttribute("groundHeight").getFloatValue();
            Element elementObstacles = elementPhysics.getChild("obstacles");
            ArrayList<MapObstacle> obstacles = new ArrayList<>();
            for (Element elementObstacle : elementObstacles.getChildren()) {
                MapObstacle obstacle = generateObstacle(elementObstacle);
                obstacles.add(obstacle);
            }
            MapPhysicsInformation physicsInformation = new MapPhysicsInformation(width, height, heightmapScale, groundHeight, obstacles);
            map.setPhysicsInformation(physicsInformation);
            // Visuals
            Element elementVisuals = root.getChild("visuals");
            for (Element elementVisual : elementVisuals.getChildren()) {
                MapVisual mapVisual = null;
                if (elementVisual.getName().equals("model")) {
                    String modelSkinPath = elementVisual.getAttributeValue("modelSkinPath");
                    Vector3f position = generateVector3f(elementVisual.getAttributeValue("position"));
                    Vector3f direction = generateVector3f(elementVisual.getAttributeValue("direction"));
                    float scale = elementVisual.getAttribute("scale").getFloatValue();
                    mapVisual = new ModelVisual(modelSkinPath, position, direction, scale);
                }
                else if (elementVisual.getName().equals("water")) {
                    Vector3f position = generateVector3f(elementVisual.getAttributeValue("position"));
                    Vector2f size = generateVector2f(elementVisual.getAttributeValue("size"));
                    mapVisual = new WaterVisual(position, size);
                }
                else if (elementVisual.getName().equals("snow")) {
                    mapVisual = new SnowVisual();
                }
                if (mapVisual != null) {
                    map.addVisual(mapVisual);
                }
            }
            return map;
        } catch (Exception ex) {
            System.err.println("Error while loading the map contents: " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    private static Map createMapObject(Element root, String mapName) {
        Map map = Util.createObjectByClassName(root.getAttributeValue("class"));
        map.setName(mapName);
        return map;
    }

    private static Element generateElement(MapObstacle mapObstacle){
        Element element = null;
        if(mapObstacle instanceof MapObstacle_Circle){
            MapObstacle_Circle mapObstacle_Circle = (MapObstacle_Circle) mapObstacle;
            Circle circle = (Circle) mapObstacle_Circle.getConvexedOutline().getConvexShapes().get(0);
            element = new Element("circle");
            element.setAttribute("x", "" + circle.getTransform().extractX());
            element.setAttribute("y", "" + circle.getTransform().extractY());
            element.setAttribute("radius", "" + circle.getGlobalRadius());
        }
        else if(mapObstacle instanceof MapObstacle_Polygon){
            MapObstacle_Polygon mapObstacle_Polygon = (MapObstacle_Polygon) mapObstacle;
            ArrayList<Vector2D> outline = mapObstacle_Polygon.getConvexedOutline().getCcwOutline();
            element = new Element("polygon");
            for(Vector2D point : outline){
                Element elementPoint = new Element("point");
                elementPoint.setAttribute("x", "" + point.getX());
                elementPoint.setAttribute("y", "" + point.getY());
                element.addContent(elementPoint);
            }
        }
        return element;
    }
    
    private static MapObstacle generateObstacle(Element element){
        if(element.getName().equals("circle")){
            double x = Double.parseDouble(element.getAttributeValue("x"));
            double y = Double.parseDouble(element.getAttributeValue("y"));
            double radius = Double.parseDouble(element.getAttributeValue("radius"));
            return new MapObstacle_Circle(new Vector2D(x, y), radius);
        }
        else if(element.getName().equals("polygon")){
            List<Element> elementChildren = element.getChildren();
            ArrayList<Vector2D> outline = new ArrayList<>(elementChildren.size());
            for(Element elementPoint : elementChildren){
                double x = Double.parseDouble(elementPoint.getAttributeValue("x"));
                double y = Double.parseDouble(elementPoint.getAttributeValue("y"));
                outline.add(new Vector2D(x, y));
            }
            return new MapObstacle_Polygon(outline);
        }
        throw new UnsupportedOperationException();
    }
    
    private static String generateVectorText(Vector2f vector2f){
        return (vector2f.getX() + VECTOR_COORDINATES_SEPARATOR + vector2f.getY());
    }
    
    private static String generateVectorText(Vector3f vector3f){
        return (vector3f.getX() + VECTOR_COORDINATES_SEPARATOR + vector3f.getY() + VECTOR_COORDINATES_SEPARATOR + vector3f.getZ());
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
