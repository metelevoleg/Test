package ru.olegJa.allcolor;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.ImageBuffer;

/**
 * Created by олег on 13.09.2018.
 */
public class Texture {
    private ImageBuffer buf;
    public Texture(int width,int height){
        buf=new ImageBuffer(width, height);
    }


    public void verticalFill(Color c1,Color c2){
        float centery=buf.getHeight()/2;
        float pr=0;
        Color c=new Color(0,0,0,0);
        for (int y=0; y<buf.getHeight();y++){
            for (int x=0;x<buf.getWidth();x++){
                pr=Math.abs(centery-y)/centery;
                mixColor(c, c1, c2, pr);
                buf.setRGBA(x,y,c.getRed(),c.getGreen(),c.getBlue(),c.getAlpha());
            }
        }
    }

    public void radialFill(Color c1,Color c2){
        float centery=buf.getHeight()/2;
        float centerx=buf.getWidth()/2;
        float pr, gipMax, gipPoint;
        Color c=new Color(1f,1f,1f,1f);
        for (int y=0; y<buf.getHeight();y++){
            for (int x=0;x<buf.getWidth();x++){
                gipMax= (buf.getWidth()*buf.getWidth()+buf.getHeight()*buf.getHeight())/4;
                gipPoint= (centerx-x)*(centerx-x)+(centery-y)*(centery-y);
                pr= gipPoint/gipMax;
//                System.out.println(pr);
                mixColor(c, c1, c2, pr);
                buf.setRGBA(x,y,c.getRed(),c.getGreen(),c.getBlue(),c.getAlpha());
            }
        }
    }

    public Image getImage(){
        return buf.getImage();
    }

    /**
     * @param c итоговый цвет
     * @param c1 цвет 1
     * @param c2 цвет 2
     * @param proportion пропорция, чем больше, тем ближе к второму цвету
     */
    void mixColor(Color c, Color c1, Color c2, float proportion){
        c.r=c1.r*(1-proportion)+c2.r*proportion;
        c.b=c1.b*(1-proportion)+c2.b*proportion;
        c.g=c1.g*(1-proportion)+c2.g*proportion;
        c.a=c1.a*(1-proportion)+c2.a*proportion;
    }
}
