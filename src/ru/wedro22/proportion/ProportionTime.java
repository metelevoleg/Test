package ru.wedro22.proportion;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import ru.olegJa.IDraw;
import ru.olegJa.IMouseOver;

public class ProportionTime implements IDraw, IMouseOver {
    private float[] val, valCalc=null;
    public final int START, END;
    public float amount;
    private int maxValCalcIndex;

//    private String strMaxVal="";

    public ProportionTime(int minuteStart, int minuteEnd){
        if (minuteStart<=minuteEnd){
            START=minuteStart;
            END=minuteEnd;
        } else {
            START=minuteEnd;
            END=minuteStart;
        }
        val = new float[END-START+1];
        for (int i = 1; i < val.length-1; i++) {
            val[i]=-1f;
        }
        val[0]=0;
        val[val.length-1]=0;
        calculate();
    }

    public ProportionTime add(int minute, float value){
        if (minute<START | minute>END) {
            System.err.println("GraphTime add неверное время: START<" + minute + "<END");
            return null;
        }
        val[minute-START]=value;
        calculate();
        return this;
    }

    private void calculate(){
        valCalc=new float[val.length];
        System.arraycopy(val,0,valCalc,0,val.length);

        int first=0;
        int two;
        while ((two=getNextValId(first))>0) {
            if (two>first+1)
                smoothInterval(first,two);
            first=two;
        }

        amount=0;
        maxValCalcIndex=0;
        for (int i = 0; i < valCalc.length; i++) {
            amount+=valCalc[i];
            if (valCalc[maxValCalcIndex]<valCalc[i])
                maxValCalcIndex=i;
        }
    }

    private int getNextValId(int start){
        if (start>=valCalc.length-1)
            return -1;
        for (int i=start+1; i<valCalc.length; i++){
            if (valCalc[i]>=0)
                return i;
        }
        return -1;
    }

    private void smoothInterval(int first, int two) {
        int minutes=two-first;
        if (minutes<=1)
            return;
        float difference=valCalc[two]-valCalc[first];
        float step=difference/minutes;
        for (int i=(first+1); i<two; i++){
            valCalc[i]=valCalc[i-1]+step;
        }
    }

    /**
     * @param minute
     * @return получение значения по времени от общего значения=1
     */
    public float getValue(int minute) {
        return getValue(minute, 1f);
    }

    /**
     * @param minute
     * @param total общее значение
     * @return получение значения по времени  от общего значения
     */
    public float getValue(int minute, float total){
        if (minute < START || minute > END)
            return 0;
        if (amount == 0) return 0;
        float value = valCalc[minute - START] / amount * total;
        return value;
    }

    public static ProportionTime summProportionTime(ProportionTime gt1, float prop1, ProportionTime gt2, float prop2){
        int start   =   gt1.START   <   gt2.START ? gt1.START : gt2.START;
        int end     =   gt1.END     >   gt2.END   ? gt1.END   : gt2.END;
        ProportionTime gt = new ProportionTime(start, end);
        float koef1=prop1/(prop1+prop2);
        float koef2=prop2/(prop1+prop2);
        float summ;
        for (int i = 0; i < gt.val.length-1; i++) {
            summ=0f;
            if (gt1.START<=i & i<=gt1.END){
                summ+=gt1.valCalc[i]*koef1;
            }
            if (gt2.START<=i & i<=gt2.END){
                summ+=gt2.valCalc[i]*koef2;
            }
            gt.val[i]=summ;
        }
        gt.calculate();
        return gt;
    }
    public static ProportionTime summProportionTime(ProportionTime[] gts, float[] props){
        if (gts.length!=props.length){
            System.err.println("SummaryGraphTime gts lenght != props lenght");
            return null;
        }
        int start = gts[0].START;
        int end =   gts[0].END;
        float counts=0f;
        for (int i = 0; i < gts.length; i++) {
            if (gts[i]==null) continue;
            if (gts[i].START<start)
                start=gts[i].START;
            if (end<gts[i].END)
                end=gts[i].END;
            counts+=props[i];
        }
        ProportionTime gt = new ProportionTime(start,end);
        float summ;
        for (int i = 0; i < gt.val.length-1; i++) {
            summ=0f;
            for (int j = 0; j < gts.length; j++) {
                if (gts[j]==null) continue;
                if (gts[j].START<=i & i<=gts[j].END)
                    summ+=gts[j].valCalc[i]*props[j]/counts;
            }
            gt.val[i]=summ;
        }
        gt.calculate();
        return gt;
    }


    public void print(){
        System.out.printf("%2s%4s%10s%16s%16s%5s%16f%n", " ","i:","min","val","valCalc","=",1f);
        String s=" ";
        for (int i = 0; i < val.length; i++) {
            s=(valCalc[i]==val[i] & val[i]>=0)?"*":" ";
            System.out.printf("%2s%4d%10d%16f%16f%5s%16f%n", "#",i,i+START,val[i],valCalc[i],s,
                    getValue(i+START));
        }
    }


    /**
     * ОТРИСОВКА С ЗНАЧЕНИЕМ
     * @param g      ссылка на графику
     * @param x      левый край
     * @param y      верхний край
     * @param width  длина
     * @param height высота
     * @param total  ЗНАЧЕНИЕ
     */
    public void draw(Graphics g, float x, float y, float width, float height, float total) {
        g.drawRect(x,y,width,height);

        float d_x=width/(END-START+1);    //графическая еденица длины (минута)
        float hei=valCalc[maxValCalcIndex];   //неграфическая высота
        float d_y=height/hei; //графическая еденица высоты (доля)

        float l_x1=x;           //линия начало
        float l_y1=y+height;    //линия начало
        float l_x2, l_y2;       //линия конец

        for (int i=START; i<=END; i++) {   //i = минута
            l_x2=l_x1+d_x;
            l_y2=y+height-valCalc[i-START]*d_y;
            g.setColor(Color.red);
            g.drawLine(l_x1,l_y1,l_x2,l_y2);
            g.setColor(Color.white);
            l_x1=l_x2;
            l_y1=l_y2;
        }

//        strMaxVal=String.valueOf(getValue(maxValCalcIndex+START, total));
//        g.drawString(strMaxVal, x-g.getFont().getWidth(strMaxVal)-3, y);
    }

    /**
     * ОТРИСОВКА С ЗНАЧЕНИЕМ = 1
     * @param g      ссылка на графику
     * @param x      левый край
     * @param y      верхний край
     * @param width  длина
     * @param height высота
     */
    @Override
    public void draw(Graphics g, float x, float y, float width, float height){
        draw(g, x, y, width, height, 1f);
    }

    @Override
    public void mouseOver(float x, float y, float width, float height, float mouseX, float mouseY) {

    }

    public static void main(String[] args){
        ProportionTime gt1 = new ProportionTime(0,6)
                .add(3,3);
        ProportionTime gt2 = new ProportionTime(0,6);
        ProportionTime gt = summProportionTime(gt1,1,gt2,3);
        gt1.print();
        gt2.print();
        gt.print();
    }
}
