/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem;

import java.util.List;

/**
 *
 * @author Philipp
 */
public interface EntityComponentMapReadonly
{
    public boolean hasComponent(int entity, Class componentClass);
    public boolean hasAllComponents(int entity, Class... componentsClasses);
    public boolean hasAnyComponent(int entity, Class... componentsClasses);
    public List<Integer> getEntitiesWithAll(Class... componentsClasses);
    public List<Integer> getEntitiesWithAny(Class... componentsClasses);
}
