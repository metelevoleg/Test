package ru.olegJa.allcolor;

import org.newdawn.slick.Color;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created by олег on 01.09.2018.
 */
public class RadialFill implements ShapeFill {
    Shape shape;
    Color color;
    @Override
    public Color colorAt(Shape shape, float x, float y) {
        System.out.print(x+"  ");
        System.out.println(y);
        float pol=shape.getHeight()/2;
        float y1= shape.getCenterY()-y;
        if (y1<0) return new Color(1f,1f,1f,1f);
        float pr=y1/pol;
//        float y2=shape.getCenterY()+y;
//        float pro=y2/pol;
        Color out=Spector.warmes(color,pr);
        return out;



    }

    @Override
    public Vector2f getOffsetAt(Shape shape, float x, float y) {
        Vector2f v = new Vector2f(0, 0);
        return v;
    }

    public RadialFill(Shape shape,Color color){
        this.shape=shape;
        this.color=color;


    }
}
