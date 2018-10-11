package ru.olegJa;

import org.newdawn.slick.Graphics;
import ru.wedro22.proportion.ProportionTime;
import ru.wedro22.database.DBSqlite;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Comparator;

import static ru.olegJa.Data.*;

/**
 * Created by олег on 29.10.2017.
 */
public class Food implements IMouseOver, IDraw{
    private float ug, bel, zh;
    private String name;

    /**
     * @param gramm количество грамм
     * @return общая энергия продукта
     */
    public float getEnergy(float gramm){
        return (getBel(gramm)*BEL_ENERGY+getZh(gramm)*ZH_ENERGY+getUg(gramm)*UG_ENERGY);
    }
    public float getEnergy(float gramm, int min){
        return getBel(gramm,min)*BEL_ENERGY+getZh(gramm, min)*ZH_ENERGY+getUg(gramm, min)*UG_ENERGY;
    }

    public float getBel(float gramm) {
        return bel*gramm/100;
    }
    public float getBel(float gramm, int min){
        return Data.graphBel.getValue(min, getBel(gramm));
    }

    public float getUg(float gramm) {
        return ug*gramm/100;
    }
    public float getUg(float gramm, int min){
        return Data.graphUg.getValue(min, getUg(gramm));
    }

    public float getZh(float gramm) {
        return zh*gramm/100;
    }
    public float getZh(float gramm, int min){
        return Data.graphZhir.getValue(min, getZh(gramm));
    }

    public Food(String name, float uglevod, float belok, float zhir){
        this.ug=uglevod;
        this.bel=belok;
        this.zh=zhir;
        this.name=name;
    }

    public String getName(){
        return new String(name);
    }

    /**
     * сколько требуется грамм для получения нужного количества белка
     */
    /*public float gramsOfBel(float belok){
        return 100*belok/bel;
    }*/

    /**
     * сколько требуется грамм для получения нужного количества жира
     */
    /*public float gramsOfZh(float zhir){
        return 100*zhir/zh;
    }*/

    /**
     * сколько требуется грамм для получения нужного количества углеводов
     */
    /*public float gramsOfUg(float uglevod){
        return 100*uglevod/ug;
    }*/

    /**
     * сколько требуется грамм для получения нужного количества энергии
     */
    /*public float gramsOfEn(float energy){
        return 100*energy/energy();
    }*/

    /**
     * list.sort(COMPARATOR_UG)
     */
    public static final Comparator<Food> COMPARATOR_UG=new Comparator<Food>() {
        @Override
        public int compare(Food o1, Food o2) {
            return (int) (o1.ug-o2.ug);
        }
    };
    public static final Comparator<Food> COMPARATOR_BEL=new Comparator<Food>() {
        @Override
        public int compare(Food o1, Food o2) {
            return (int) (o1.bel-o2.bel);
        }
    };
    public static final Comparator<Food> COMPARATOR_ZH=new Comparator<Food>() {
        @Override
        public int compare(Food o1, Food o2) {
            return (int) (o1.zh-o2.zh);
        }
    };
    public static final Comparator<Food> COMPARATOR_EN=new Comparator<Food>() {
        @Override
        public int compare(Food o1, Food o2) {
            return (int) (o1.getEnergy(1)-o2.getEnergy(1));
        }
    };

    public String toString(){
        String s=name+ " en:"+getEnergy(100)+ "  ug:"+ug+"  bel:"+bel+"  zh:"+zh;
        return s;
    }

    public void draw(Graphics g,float x,float y,float width,float height){
        //graphEnergy.draw(g, x, y, width, height);
    }

    public void mouseOver(float x, float y, float width, float height, float mouseX, float mouseY){
        //graphEnergy.mouseOver(x,y,width,height,mouseX,mouseY);
    }

    /**
     * Получение списка еды из бд
     * @param sql сырой запрос к бд
     * @return ArrayList, заполненный/пустой(напр. при ошибке)
     */
    public static ArrayList<Food> getFoodList(String sql){
        ArrayList<Food> array = new ArrayList<>(32);
        ResultSet rs = DBSqlite.getRawQuerry(sql);
        if (rs!=null) {
            try {
                while (rs.next()) {
                    array.add(new Food(
                            rs.getString("name"),
                            rs.getFloat("uglevod"),
                            rs.getFloat("belok"),
                            rs.getFloat("zhir")
                    ));
                }
            } catch (java.sql.SQLException e){
                e.printStackTrace();
            }
        }
        return array;
    }

    /**
     * Получение одного экземпляра еды из бд
     * @param sql сырой запрос к бд
     * @return может возвратить null
     */
    public static Food getFood(String sql){
        ArrayList<Food> list = getFoodList(sql);
        if (list==null) return null;
        if (list.get(0)==null) return null;
        return getFoodList(sql).get(0);
    }

}
