/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.display.ingame.models.modifiers;

import com.jme3.effect.ParticleEmitter;
import com.jme3.scene.Node;
import amara.libraries.applications.display.models.*;

/**
 *
 * @author Carl
 */
public class ModelModifier_Ganfaul_Ult extends ModelModifier{

    @Override
    public void modify(RegisteredModel registeredModel){
        Node node = registeredModel.getNode();
        ParticleEmitter particleEmitter1 = (ParticleEmitter) node.getChild(0);
        ParticleEmitter particleEmitter2 = (ParticleEmitter) node.getChild(1);
        particleEmitter1.updateLogicalState(10);
        particleEmitter2.updateLogicalState(10);
    }
}
