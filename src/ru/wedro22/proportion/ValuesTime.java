package ru.wedro22.proportion;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import ru.olegJa.IDraw;
import ru.olegJa.IMouseOver;

import java.util.Arrays;

/**
 * Класс-обертка для массива
 * Предполагается хранить уже просчитанные значения
 */
public class ValuesTime implements IDraw, IMouseOver {
    private float[] val;
    public final int START, END;

    public ValuesTime(int start, int end){
        START=start;
        END=end;
        val = new float[END-START+1];
        Arrays.fill(val, 0);
    }

    public void add(int minute, float value){
        if (minute<START | minute>END) {
            System.err.println("ValuesTime add неверное время: START<" + minute + "<END");
            return;
        }
        val[minute-START]=value;
    }

    public float get(int minute){
        if (minute < START || minute > END)
            return 0;
        return val[minute-START];
    }

    @Override
    public void draw(Graphics g, float x, float y, float width, float height) {
        g.setColor(Color.green);
        g.drawRect(x,y,width,height);
        float x1,y1,x2,y2,c;
        x1=x;
        y1=y+height;
        x2=x;
        c=height/0.03f;
        y2=y1-val[0]*c;
        float m=width/val.length;
        for (int i = 1; i < val.length; i++) {
            g.drawLine(x1,y1,x2,y2);
            x1=x1+m;
            x2=x1;
            y2=y1-val[i]*c;


        }

    }

    @Override
    public void mouseOver(float x, float y, float width, float height, float mouseX, float mouseY) {

    }
}
