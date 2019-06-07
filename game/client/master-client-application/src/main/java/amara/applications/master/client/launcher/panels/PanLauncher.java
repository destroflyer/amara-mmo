/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PanLauncher.java
 *
 * Created on 03.08.2012, 15:42:37
 */
package amara.applications.master.client.launcher.panels;

import java.awt.Graphics;
import java.awt.Image;
import amara.core.files.FileAssets;

/**
 *
 * @author Carl
 */
public class PanLauncher extends javax.swing.JPanel{

    public PanLauncher(){
        initComponents();
        backgroundImage = FileAssets.getImage("Interface/client/panels/launcher.jpg");
    }
    private Image backgroundImage;

    @Override
    protected void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        graphics.drawImage(backgroundImage, 0, 0, this);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 252, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}