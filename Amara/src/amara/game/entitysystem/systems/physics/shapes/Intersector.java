/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes;

/**
 *
 * @author Philipp
 */
public interface Intersector {
    boolean intersect(Object a, Object b);
    Vector2D resolveVector(Object a, Object b);
    Class getClassA();
    Class getClassB();
}