package ru.olegJa.allcolor;
import org.newdawn.slick.Color;

/**
 * Created by олег on 18.08.2018.
 */
public class Spector {
    public static Color darknes(Color into){
        Color out=into.darker();
        return out;
    }

    public static Color darknes(Color into,float prozent){
        Color out=into.darker(prozent);
        return out;
    }


    public static Color lightes(Color into){
        Color out=into.brighter();
        return out;
    }
    public static Color lightes(Color into, float prozent){
        Color out=into.brighter(prozent);
        return out;
    }
    public static Color holdes(Color into){
        return holdes(into, 0.2f);
    }

    public static Color holdes(Color into,float prozent){
        Color out=new Color(into);
        float cf=prozent;
        out.b=out.b*(cf+1);
        out.r=out.r*(1-cf);
        return out;
    }
    public static Color warmes(Color into){
        return warmes(into, 0.2f);
    }

    public static Color warmes(Color into,float prozent) {
        Color out = new Color(into);
        float cf = prozent;
        out.b = out.b * (1 - cf);
        out.r = out.r * (cf + 1);
        return out;
    }
    public static Color alerts(Color into){
        return alerts(into,  0.2f);
    }
    public static Color alerts(Color into,float prozent){
        Color out=new Color (into);
        float cf=prozent;
        out.g=out.g*(1-cf);
        out.b=out.b*(1-cf);
        out.r=out.r*(cf+1);
        return out;
    }
    public static Color friends(Color into){
        return friends(into,0.2f);
    }
    public static Color friends(Color into,float prozent){
        Color out=new Color(into);
        float cf=prozent;
        out.r=out.r*(1-cf);
        out.g=out.g*(cf+1);
        return out;
    }
    public static Color transparent(Color into){
        return transaparent(into,0.2f);
    }
    public static Color transaparent(Color into,float prozent){
        Color out=new Color(into);
        float cf=prozent;
        out.a=out.a*(1-cf);
        if (out.a>1)out.a=1;
        else if (out.a<0)out.a=0;
        return out;
    }
    public static Color mix(Color into1,Color into2 ){
        float r,g,b,a;
        r=(into1.r+into2.r)/2;
        g=(into1.g+into2.g)/2;
        b=(into1.b+into2.b)/2;
        a=(into1.a+into2.a)/2;
        Color out=new Color (r,g,b,a);
        return out;
    }
    public static Color mix(Color into2, Color into1,float proz){
        float r,g,b,a;
        r=into1.r*proz+into2.r*(1-proz);
        g=into1.g*proz+into2.g*(1-proz);
        b=into1.b*proz+into2.b*(1-proz);
        a=into1.a*proz+into2.a*(1-proz);
        Color out=new Color(r,g,b,a);
        return out;

    }


}
