package ru.olegJa;

import org.newdawn.slick.Color;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created by олег on 04.06.2017.
 */
public class Utilite {


    public static class ColorFill {
        public static ShapeFill getFill(Color color) {
            ShapeFill fill = new ShapeFill() {
                @Override
                public Color colorAt(Shape shape, float x, float y) {
                    return color;
                }

                @Override
                public Vector2f getOffsetAt(Shape shape, float x, float y) {
                    Vector2f v = new Vector2f(0, 0);
                    return v;
                }
            };
            return fill;
        }

    }


}


