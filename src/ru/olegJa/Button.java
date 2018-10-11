package ru.olegJa;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Created by олег on 04.10.2018.
 */
public class Button {
    float width,height,x,y;
    Image image;
    public Button(float x,float y,float width,float height,String image){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        try {
            this.image=new Image(image);
        } catch (SlickException e) {
            e.printStackTrace();
        }

    }
    public void draw(Graphics g){
        image.draw(x,y,width,height);
    }
}
