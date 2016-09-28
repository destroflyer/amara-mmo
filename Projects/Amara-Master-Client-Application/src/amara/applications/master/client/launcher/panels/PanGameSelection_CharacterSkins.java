/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.launcher.panels;

import java.awt.Dimension;
import amara.applications.master.network.messages.objects.*;

/**
 *
 * @author Carl
 */
public class PanGameSelection_CharacterSkins extends javax.swing.JPanel{

    public PanGameSelection_CharacterSkins(PanGameSelection panGameSelection, OwnedGameCharacter ownedCharacter){
        initComponents();
        this.panGameSelection = panGameSelection;
        int padding = 5;
        int containerWidth = 575;
        int x = padding;
        int y = padding;
        GameCharacterSkin[] characterSkins = ownedCharacter.getCharacter().getSkins();
        panSkins = new PanGameSelection_CharacterSkin[characterSkins.length + 1];
        for(int i=-1;i<characterSkins.length;i++){
            if((x + PanGameSelection_CharacterSkin.PANEL_WIDTH + padding) > containerWidth){
                x = padding;
                y += PanGameSelection_CharacterSkin.PANEL_HEIGHT;
            }
            PanGameSelection_CharacterSkin panSkin;
            int characterSkinID = ((i == -1)?0:characterSkins[i].getID());
            panSkin = new PanGameSelection_CharacterSkin(this, ownedCharacter, characterSkinID);
            panSkin.setLocation(x, y);
            panSkin.setSize(PanGameSelection_CharacterSkin.PANEL_WIDTH, PanGameSelection_CharacterSkin.PANEL_HEIGHT);
            panCharacterSkinsContainer.add(panSkin);
            panSkins[i + 1] = panSkin;
            x += (PanGameSelection_CharacterSkin.PANEL_WIDTH + padding);
        }
        y += (PanGameSelection_CharacterSkin.PANEL_HEIGHT + padding);
        panCharacterSkinsContainer.setPreferredSize(new Dimension(containerWidth, y));
    }
    private PanGameSelection panGameSelection;
    private PanGameSelection_CharacterSkin[] panSkins;
    
    public void resetScroll(){
        scrCharacterSkins.getVerticalScrollBar().setValue(0);
    }
    
    public void updateCharacterSkinsPanels(){
        for(PanGameSelection_CharacterSkin panSkin : panSkins){
            panSkin.updateBorder();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrCharacterSkins = new javax.swing.JScrollPane();
        panCharacterSkinsContainer = new javax.swing.JPanel();

        scrCharacterSkins.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrCharacterSkins.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        panCharacterSkinsContainer.setBackground(new java.awt.Color(30, 30, 30));

        javax.swing.GroupLayout panCharacterSkinsContainerLayout = new javax.swing.GroupLayout(panCharacterSkinsContainer);
        panCharacterSkinsContainer.setLayout(panCharacterSkinsContainerLayout);
        panCharacterSkinsContainerLayout.setHorizontalGroup(
            panCharacterSkinsContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 296, Short.MAX_VALUE)
        );
        panCharacterSkinsContainerLayout.setVerticalGroup(
            panCharacterSkinsContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 198, Short.MAX_VALUE)
        );

        scrCharacterSkins.setViewportView(panCharacterSkinsContainer);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrCharacterSkins, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrCharacterSkins, javax.swing.GroupLayout.Alignment.TRAILING)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panCharacterSkinsContainer;
    private javax.swing.JScrollPane scrCharacterSkins;
    // End of variables declaration//GEN-END:variables
}
