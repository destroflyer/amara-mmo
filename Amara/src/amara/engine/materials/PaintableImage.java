/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.materials;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import com.jme3.texture.Image;
import com.jme3.texture.Image.Format;
import com.jme3.util.BufferUtils;
import amara.Util;
 
public class PaintableImage{
    
    public PaintableImage(String imageResourcePath){
        BufferedImage loadedImage = Util.getResourceImage(imageResourcePath);
        setSize(loadedImage.getWidth(), loadedImage.getHeight());
        for(int x=0;x<width;x++){
            for(int y=0;y<height;y++){
                setPixel(x, y, new Color(loadedImage.getRGB(x, y)));
            }
        }
    }
    
    public PaintableImage(int width, int height){
        setSize(width, height);
    }
    private int width;
    private int height;
    private byte[] data;
    private Image image;
    
    private void setSize(int width, int height){
        this.width = width;
        this.height = height;
        //Create black image
        data = new byte[width * height * 4];       
        setBackground(new Color(0, 0, 0, 255));
        //Set data to texture
        ByteBuffer buffer = BufferUtils.createByteBuffer(data);
        image = new Image(Format.RGBA8, width, height, buffer);
    }
    
    public Image getImage(){
        ByteBuffer buffer = BufferUtils.createByteBuffer(data);
        image.setData(buffer);
        return image;
    }
 
    public void setBackground(Color color){
        for(int i=0;i<(width * height * 4);i += 4){
            data[i] = (byte) color.getRed();
            data[i + 1] = (byte) color.getGreen();
            data[i + 2] = (byte) color.getBlue();
            data[i + 3] = (byte) color.getAlpha();
        }
    }
 
    public void setBackground_Alpha(int colorValue){
        for(int x=0;x<width;x++){
            for(int y=0;y<width;y++){
                setPixel_Alpha(x, y, colorValue);
            }
        }
    }
 
    public void setPixel(int x, int y, Color color){
        setPixel(x, y, color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }
 
    public void setPixel(int x, int y, int red, int green, int blue, int alpha){
        setPixel_Red(x, y, red);
        setPixel_Green(x, y, green);
        setPixel_Blue(x, y, blue);
        setPixel_Alpha(x, y, alpha);
    }
 
    public void setPixel_Red(int x, int y, int colorValue){
        setPixel_ColorValue(x, y, colorValue, 0);
    }
 
    public void setPixel_Green(int x, int y, int colorValue){
        setPixel_ColorValue(x, y, colorValue, 1);
    }
 
    public void setPixel_Blue(int x, int y, int colorValue){
        setPixel_ColorValue(x, y, colorValue, 2);
    }
 
    public void setPixel_Alpha(int x, int y, int colorValue){
        setPixel_ColorValue(x, y, colorValue, 3);
    }
 
    private void setPixel_ColorValue(int x, int y, int colorValue, int bufferIndexOffset){
        int index = (bufferIndexOffset + ((x + y * width) * 4));
        if((index >= 0) && (index < data.length)){
            data[index] = (byte) colorValue;
        }
    }
 
    public Color getPixel(int x, int y){
        return new Color(getPixel_Red(x, y), getPixel_Green(x, y), getPixel_Blue(x, y), getPixel_Alpha(x, y));
    }
 
    public int getPixel_Red(int x, int y){
        return getPixel_ColorValue(x, y, 0);
    }
 
    public int getPixel_Green(int x, int y){
        return getPixel_ColorValue(x, y, 1);
    }
 
    public int getPixel_Blue(int x, int y){
        return getPixel_ColorValue(x, y, 2);
    }
 
    public int getPixel_Alpha(int x, int y){
        return getPixel_ColorValue(x, y, 3);
    }
 
    private int getPixel_ColorValue(int x, int y, int bufferIndexOffset){
        int i = (x + y * width) * 4;
        return (data[i + bufferIndexOffset] & 0xFF);
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public byte[] getData(){
        return data;
    }

    public void setData(byte[] data){
        for(int i=0;i<this.data.length;i++){
            this.data[i] = data[i];
        }
    }
}