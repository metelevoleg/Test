package ru.olegJa;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

/**
 * Created by олег on 07.10.2018.
 */
public class Button1 extends Button {
    public Button1(float x, float y, float width, float height, String image) {
        super(x, y, width, height, image);
        but1=new Color(1f,0f,0f,0f);
        re=new Rectangle(x, y, width, height);
    }
    Color but1;
    Rectangle re;
    public void setActive(float active){
        but1.a=active;
    }

    @Override
    public void draw(Graphics g) {
        g.fill(re,Utilite.ColorFill.getFill(but1));

        super.draw(g);
    }
}
