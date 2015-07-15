/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package amara.game.entitysystem.templates;

import java.util.LinkedList;
import java.util.List;
import amara.Util;
import amara.game.entitysystem.EntityWorld;
import org.jdom.Element;

/**
 *
 * @author Carl
 */
public abstract class XMLComponentConstructor<T>{

    public XMLComponentConstructor(String elementName){
        this.elementName = elementName;
    }
    private String elementName;
    protected XMLTemplateManager xmlTemplateManager;
    private EntityWorld entityWorld;
    protected Element element;
    
    public void prepare(XMLTemplateManager xmlTemplateManager, EntityWorld entityWorld, Element element){
        this.xmlTemplateManager = xmlTemplateManager;
        this.entityWorld = entityWorld;
        this.element = element;
    }
    
    public abstract T construct();

    protected int[] createChildEntities(int offset, String parameterName){
        LinkedList<Integer> childEntities = new LinkedList<Integer>();
        List children = element.getChildren();
        if(children.size() > 0){
            for(int i=0;i<children.size();i++){
                childEntities.add(xmlTemplateManager.createEntity(entityWorld, (Element) children.get(i)));
            }
        }
        else if(element.getText().length() > 0){
            childEntities.add(parseEntity(element.getText()));
        }
        int parameterIndex = 0;
        String attributeValue;
        while((attributeValue = element.getAttributeValue(parameterName + parameterIndex)) != null){
            childEntities.add(parseEntity(attributeValue));
            parameterIndex++;
        }
        return Util.convertToArray(childEntities);
    }

    protected int createChildEntity(int index, String parameterName){
        List children = element.getChildren();
        if(children.size() > 0){
            if(index < children.size()){
                return xmlTemplateManager.createEntity(entityWorld, (Element) children.get(index));
            }
        }
        else if((index == 0) && (element.getText().length() > 0)){
            return parseEntity(element.getText());
        }
        return parseEntity(element.getAttributeValue(parameterName));
    }
    
    private int parseEntity(String text){
        return Integer.parseInt(xmlTemplateManager.parseValue(text));
    }
    
    public String getElementName(){
        return elementName;
    }
}