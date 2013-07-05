/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem;

/**
 *
 * @author Philipp
 */
public class EntityWrapper
{
    private EntityComponentMap entityMap;
    private int entity;

    EntityWrapper(EntityComponentMap entityMap, int entity)
    {
        this.entityMap = entityMap;
        this.entity = entity;
    }

    public int getId()
    {
        return entity;
    }
    
    public <T> T getComponent(Class<T> componentClass)
    {
        return entityMap.getComponent(entity, componentClass);
    }
    
    public void setComponent(Object component)
    {
        entityMap.setComponent(entity, component);
    }
    
    public void removeComponent(Class componentClass)
    {
        entityMap.removeComponent(entity, componentClass);
    }
    
    public void clearComponents()
    {
        entityMap.clearComponents(entity);
    }

    @Override
    public String toString()
    {
        return "[Entity id=" + entity + "]";
    }

    @Override
    public int hashCode()
    {
        return entityMap.hashCode() ^ entity;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj == null || obj.getClass() != EntityWrapper.class) return false;
        EntityWrapper wrapper = (EntityWrapper)obj;
        return wrapper.entityMap.equals(entityMap) && wrapper.entity == entity;
    }
    
}