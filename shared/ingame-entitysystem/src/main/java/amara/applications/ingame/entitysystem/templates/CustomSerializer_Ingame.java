/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.templates;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.jme3.math.Vector2f;
import amara.applications.ingame.entitysystem.components.effects.physics.*;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.spawns.*;
import amara.applications.ingame.entitysystem.components.spells.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.libraries.entitysystem.EntityWorld;
import amara.libraries.entitysystem.synchronizing.*;
import amara.libraries.entitysystem.synchronizing.fieldserializers.*;
import amara.libraries.entitysystem.templates.*;
import amara.libraries.physics.shapes.*;
import amara.libraries.physics.shapes.PolygonMath.*;
import org.jdom2.Element;

/**
 *
 * @author Carl
 */
public class CustomSerializer_Ingame{
    
    public static void registerClasses(){
        BitstreamClassManager.getInstance().register(
            ArrayList.class,
            //physics/HitboxComponent
            Circle.class,
            Rectangle.class,
            RegularCyclic.class,
            Shape.class,
            ConvexShape.class,
            SimpleConvexPolygon.class,
            PointShape.class,
            Transform2D.class,
            Vector2D.class,
            PolygonShape.class,
                BoundRectangle.class,
                Polygon.class,
                    SetPolygon.class,
                    HolePolygon.class,
                    SimplePolygon.class,
            //units/DamageHistoryComponent
            DamageHistoryComponent.DamageHistoryEntry.class
        );
        ComponentsRegistrator.registerComponents();
        try{
            ComponentSerializer.registerFieldSerializer(new Field[]{ 
                Vector2f.class.getDeclaredField("x"),
                Vector2f.class.getDeclaredField("y")
            }, new FieldSerializer_Float(20, 8));
            ComponentSerializer.registerFieldSerializer(new Field[]{ 
                Vector2D.class.getDeclaredField("x"),
                Vector2D.class.getDeclaredField("y"),
                Transform2D.class.getDeclaredField("scalecos"),
                Transform2D.class.getDeclaredField("scalesin"),
                Transform2D.class.getDeclaredField("x"),
                Transform2D.class.getDeclaredField("y"),
                Circle.class.getDeclaredField("localRadius"),
            }, new FieldSerializer_DoubleAsFloat(20, 8));
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        XMLTemplateManager xmlTemplateManager = XMLTemplateManager.getInstance();
        //effects/physics
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<AddCollisionGroupsComponent>("addCollisionGroups"){

            @Override
            public AddCollisionGroupsComponent construct(XMLTemplateReader templateReader, EntityWorld entityWorld, Element element){
                long targetOf = getCollisionGroupBitMask(element.getAttributeValue("targetOf"));
                long targets = getCollisionGroupBitMask(element.getAttributeValue("targets"));
                return new AddCollisionGroupsComponent(targetOf, targets);
            }
        });
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<RemoveCollisionGroupsComponent>("removeCollisionGroups"){

            @Override
            public RemoveCollisionGroupsComponent construct(XMLTemplateReader templateReader, EntityWorld entityWorld, Element element){
                long targetOf = getCollisionGroupBitMask(element.getAttributeValue("targetOf"));
                long targets = getCollisionGroupBitMask(element.getAttributeValue("targets"));
                return new RemoveCollisionGroupsComponent(targetOf, targets);
            }
        });
        //physics
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<HitboxComponent>("hitbox"){

            @Override
            public HitboxComponent construct(XMLTemplateReader templateReader, EntityWorld entityWorld, Element element){
                Shape shape = null;
                Element childElement = element.getChildren().get(0);
                String shapeType = childElement.getName();
                double x = 0;
                String xText = childElement.getAttributeValue("x");
                if(xText != null){
                    x = Double.parseDouble(xText);
                }
                double y = 0;
                String yText = childElement.getAttributeValue("y");
                if(yText != null){
                    y = Double.parseDouble(yText);
                }
                if(shapeType.equals("regularCyclic")){
                    int edges = Integer.parseInt(templateReader.parseValue(entityWorld, childElement.getAttributeValue("edges")));
                    double radius = Double.parseDouble(templateReader.parseValue(entityWorld, childElement.getAttributeValue("radius")));
                    shape = new RegularCyclic(edges, radius);
                }
                else if(shapeType.equals("circle")){
                    double radius = Double.parseDouble(templateReader.parseValue(entityWorld, childElement.getAttributeValue("radius")));
                    shape = new Circle(x, y, radius);
                }
                else if(shapeType.equals("rectangle")){
                    double width = Double.parseDouble(templateReader.parseValue(entityWorld, childElement.getAttributeValue("width")));
                    double height = Double.parseDouble(templateReader.parseValue(entityWorld, childElement.getAttributeValue("height")));
                    shape = new Rectangle(x, y, width, height);
                }
                else if(shapeType.equals("point")){
                    Vector2D localPoint = new Vector2D();
                    String[] positionCoordinates = element.getText().split(",");
                    if(positionCoordinates.length > 1){
                        double localPointX = Double.parseDouble(templateReader.parseValue(entityWorld, positionCoordinates[0]));
                        double localPointY = Double.parseDouble(templateReader.parseValue(entityWorld, positionCoordinates[1]));
                        localPoint = new Vector2D(localPointX, localPointY);
                    }
                    shape = new PointShape(localPoint);
                }
                if(shape == null){
                    throw new UnsupportedOperationException("Unsupported shape type '" + shapeType + "'.");
                }
                return new HitboxComponent(shape);
            }
        });
        //spawns
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<SpawnTemplateComponent>("spawnTemplate"){

            @Override
            public SpawnTemplateComponent construct(XMLTemplateReader templateReader, EntityWorld entityWorld, Element element){
                String[] templates = element.getText().split("\\|");
                for(int i=0;i<templates.length;i++){
                    templates[i] = templateReader.parseTemplate(entityWorld, templates[i]).getText();
                }
                return new SpawnTemplateComponent(templates);
            }
        });
        //spells
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<CastTypeComponent>("castType"){

            @Override
            public CastTypeComponent construct(XMLTemplateReader templateReader, EntityWorld entityWorld, Element element){
                return new CastTypeComponent(CastTypeComponent.CastType.valueOf(element.getText().toUpperCase()));
            }
        });
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<RangeComponent>("range"){

            @Override
            public RangeComponent construct(XMLTemplateReader templateReader, EntityWorld entityWorld, Element element){
                RangeComponent.RangeType type = RangeComponent.RangeType.valueOf(element.getAttributeValue("type").toUpperCase());
                float distance = Float.parseFloat(templateReader.parseValue(entityWorld, element.getAttributeValue("distance")));
                return new RangeComponent(type, distance);
            }
        });
        //units
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<CollisionGroupComponent>("collisionGroup"){

            @Override
            public CollisionGroupComponent construct(XMLTemplateReader templateReader, EntityWorld entityWorld, Element element){
                long targetOf = getCollisionGroupBitMask(element.getAttributeValue("targetOf"));
                long targets = getCollisionGroupBitMask(element.getAttributeValue("targets"));
                return new CollisionGroupComponent(targetOf, targets);
            }
        });
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<HealthBarStyleComponent>("healthBarStyle"){

            @Override
            public HealthBarStyleComponent construct(XMLTemplateReader templateReader, EntityWorld entityWorld, Element element){
                return new HealthBarStyleComponent(HealthBarStyleComponent.HealthBarStyle.valueOf(element.getText().toUpperCase()));
            }
        });
    }
    
    private static long getCollisionGroupBitMask(String text){
        long bitMask = 0;
        String[] groupNames = text.split("\\|");
        for(String groupName : groupNames){
            bitMask |= getCollisionGroup(groupName);
        }
        return bitMask;
    }
    
    private static long getCollisionGroup(String name){
        if(name.equals("none")){
            return CollisionGroupComponent.NONE;
        }
        else if(name.equals("map")){
            return CollisionGroupComponent.MAP;
        }
        else if(name.equals("units")){
            return CollisionGroupComponent.UNITS;
        }
        else if(name.equals("spells")){
            return CollisionGroupComponent.SPELLS;
        }
        else if(name.equals("spell_targets")){
            return CollisionGroupComponent.SPELL_TARGETS;
        }
        throw new UnsupportedOperationException("Unsupported collision group name '" + name + "'.");
    }
}
