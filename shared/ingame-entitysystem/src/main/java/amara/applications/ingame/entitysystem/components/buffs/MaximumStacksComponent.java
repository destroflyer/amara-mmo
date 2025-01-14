package amara.applications.ingame.entitysystem.components.buffs;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

@Serializable
public class MaximumStacksComponent {

    public MaximumStacksComponent() {

    }

    public MaximumStacksComponent(int stacks) {
        this.stacks = stacks;
    }
    @ComponentField(type=ComponentField.Type.STACKS)
    private int stacks;

    public int getStacks() {
        return stacks;
    }
}
