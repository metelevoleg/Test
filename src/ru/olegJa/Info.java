package ru.olegJa;

import org.newdawn.slick.*;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;
import ru.olegJa.allcolor.Spector;
import ru.olegJa.allcolor.Texture;

/**
 * Created by олег on 26.09.2018.
 */
public class Info {
    float x,y,width,height;
    Color color1,color2;
    Image image;
    float max,min,optimal,current;
    Rectangle rect;
    Texture texture;
    public void setCurrent(float c){
        current=c;
        float proz;
        Color cl=new Color(color1);
        if (current>optimal){
            proz=(current-optimal)/(max-optimal);

        }
        else {
            proz=(optimal-current)/optimal;
        }
        proz=proz*0.8f;
        cl= Spector.mix(color1,color2,proz);
        Color cl2=new Color(cl);
        cl2=Spector.mix(cl, color2,proz);
        texture.verticalFill(cl, cl2);
    }
    public void draw(Graphics g){
        g.texture(rect,texture.getImage(),true);


        image.draw(x,y,width,height);
    }
    public Info(float x,float y,float width,float height,String image,Color color1,Color color2,float max,float min,float optimal){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        try {
            this.image=new Image(image);
        } catch (SlickException e) {
            e.printStackTrace();
        }
        this.max=max;
        this.min=min;
        this.optimal=optimal;
        this.color1=color1;
        this.color2=color2;
        rect=new Rectangle(x,y,width,height);
        texture=new Texture ((int)width,(int)height);
        texture.verticalFill(color1,color2);

    }
}
