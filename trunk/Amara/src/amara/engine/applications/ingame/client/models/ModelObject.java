/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.models;

import com.jme3.animation.*;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import amara.engine.applications.DisplayApplication;
import amara.engine.settings.Settings;

/**
 *
 * @author Carl
 */
public class ModelObject extends Node implements AnimEventListener{

    public ModelObject(DisplayApplication mainApplication, String skinResourcePath){
        this.mainApplication = mainApplication;
        loadSkin(new ModelSkin(skinResourcePath));
    }
    private DisplayApplication mainApplication;
    private Spatial modelSpatial;
    private AnimChannel animationChannel;
    
    private void loadSkin(ModelSkin skin){
        modelSpatial = skin.loadSpatial();
        for(ModelModifier modelModifier : skin.getModelModifiers()){
            modelModifier.modify(this);
        }
        attachChild(modelSpatial);
        AnimControl animationControl = modelSpatial.getControl(AnimControl.class);
        if(animationControl != null){
            animationControl.addListener(this);
            animationChannel = animationControl.createChannel();
        }
        SkeletonControl skeletonControl = modelSpatial.getControl(SkeletonControl.class);
        if(skeletonControl != null){
            skeletonControl.setHardwareSkinningPreferred(Settings.getBoolean("hardware_skinning"));
        }
    }

    public void applyModelTransformTo(Spatial spatial){
        spatial.setLocalTranslation(modelSpatial.getLocalTranslation());
        spatial.setLocalRotation(modelSpatial.getLocalRotation());
        spatial.setLocalScale(modelSpatial.getLocalScale());
    }
    
    public void playAnimation(String animationName, float loopDuration){
        playAnimation(animationName, loopDuration, true);
    }
    
    public void playAnimation(String animationName, float loopDuration, boolean isLoop){
        if(animationChannel != null){
            try{
                if(!animationName.equals(animationChannel.getAnimationName())){
                    animationChannel.setAnim(animationName);
                }
                animationChannel.setSpeed(animationChannel.getAnimMaxTime() / loopDuration);
                animationChannel.setLoopMode(isLoop?LoopMode.Loop:LoopMode.DontLoop);
            }catch(IllegalArgumentException ex){
                stopAndRewindAnimation();
            }
        }
    }
    
    public void stopAndRewindAnimation(){
        mainApplication.enqueueTask(new Runnable(){

            @Override
            public void run(){
                animationChannel.reset(true);
            }
        });
    }

    @Override
    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animationName){
        
    }

    @Override
    public void onAnimChange(AnimControl control, AnimChannel channel, String animationName){
        
    }
}
