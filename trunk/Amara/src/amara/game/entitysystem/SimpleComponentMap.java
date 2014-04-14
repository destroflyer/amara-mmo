/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Philipp
 */
class SimpleComponentMap implements EntityComponentMap, EntityComponentMapReadonly
{
    private ConcurrentHashMap<Class, ConcurrentHashMap<Integer, Object>> componentMaps = new ConcurrentHashMap<Class, ConcurrentHashMap<Integer, Object>>();
    private Comparator<Class> mapSizeComparator = new Comparator<Class>() {
                public int compare(Class o1, Class o2)
                {
                    return getComponentMap(o1).size() - getComponentMap(o2).size();
                }
            };
    
    public boolean isEmpty()
    {
        for (Class key : componentMaps.keySet())
        {
            if(!componentMaps.get(key).isEmpty()) return false;
        }
        return true;
    }
    
    private ConcurrentHashMap<Integer, Object> getComponentMap(Class componentClass)
    {
        ConcurrentHashMap<Integer, Object> componentMap = componentMaps.get(componentClass);
        if(componentMap == null)
        {
            componentMap = new ConcurrentHashMap<Integer, Object>();
            componentMaps.put(componentClass, componentMap);
        }
        return componentMap;
    }

    protected ConcurrentHashMap<Class, ConcurrentHashMap<Integer, Object>> getComponentMaps() {
        return componentMaps;
    }
    
    public <T> T getComponent(int entity, Class<T> componentClass)
    {
        return (T)getComponentMap(componentClass).get(entity);
    }

    public boolean hasComponent(int entity, Class componentClass)
    {
        return getComponentMap(componentClass).get(entity) != null;
    }

    public Object setComponent(int entity, Object component)
    {
        return getComponentMap(component.getClass()).put(entity, component);
    }

    public Object removeComponent(int entity, Class componentClass)
    {
        return getComponentMap(componentClass).remove(entity);
    }

    public Set<Object> getComponents(int entity) {
        HashSet<Object> components = new HashSet<Object>();
        Object component;
        for(ConcurrentHashMap<Integer, Object> componentMap: componentMaps.values())
        {
            component = componentMap.get(entity);
            if(component != null)
            {
                components.add(component);
            }
        }
        return components;
    }

    public void clearComponents(int entity)
    {
        for(Object component: getComponents(entity))
        {
            removeComponent(entity, component.getClass());
        }
    }

    public boolean hasAllComponents(int entity, Class... componentsClasses)
    {
        for(Class componentClass: componentsClasses)
        {
            if(!getComponentMap(componentClass).containsKey(entity))
            {
                return false;
            }
        }
        return true;
    }

    public boolean hasAnyComponent(int entity, Class... componentsClasses)
    {
        for(Class componentClass: componentsClasses)
        {
            if(getComponentMap(componentClass).containsKey(entity))
            {
                return true;
            }
        }
        return false;
    }

    public Set<Integer> getEntitiesWithAll(Class... componentsClasses)
    {
        HashSet<Integer> entitySet = new HashSet<Integer>();
        if(componentsClasses.length == 0)
        {
            for(ConcurrentHashMap<Integer, Object> map: componentMaps.values())
            {
                entitySet.addAll(map.keySet());
            }
            return entitySet;
        }
        
        Arrays.sort(componentsClasses, mapSizeComparator);
        
        for(int entity: getComponentMap(componentsClasses[0]).keySet())
        {
            if(hasAllComponents(entity, componentsClasses))
            {
                entitySet.add(entity);
            }
        }
        return entitySet;
    }

    public Set<Integer> getEntitiesWithAny(Class... componentsClasses)
    {
        HashSet<Integer> entitySet = new HashSet<Integer>();
        for(Class componentClass: componentsClasses)
        {
            for(int entity: getComponentMap(componentClass).keySet())
            {
                if(hasAnyComponent(entity, componentsClasses))
                {
                    entitySet.add(entity);
                }
            }
        }
        return entitySet;
    }

    public void clear()
    {
        for(ConcurrentHashMap<Integer, Object> map: componentMaps.values())
        {
            map.clear();
        }
    }
    
}
