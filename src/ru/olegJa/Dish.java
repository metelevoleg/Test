package ru.olegJa;

import org.newdawn.slick.Graphics;
import ru.wedro22.proportion.ValuesTime;

import java.util.ArrayList;

/**
 * Created by олег on 01.11.2017.
 */
public class Dish implements IMouseOver, IDraw{
    public String name;
    private ArrayList<FoodCount> list= new ArrayList<>(1);
    private ValuesTime values;

    public Dish(String name){
        this.name=name;
        values = new ValuesTime(0,Data.ASSIMILATION);
    }

    /**
     * @param gramm количество грамм
     * @return общая энергия блюда
     */
    public float getEnergy(float gramm){
        float en=0;
        float counts=0;
        for (FoodCount f : list) {
            counts+=f.count;
        }
        for (FoodCount f : list) {
            en+=f.food.getEnergy(f.count/counts*gramm);
        }
        return en;
    }
    public float getEnergy(float gramm, int min){
        return values.get(min)*gramm;
    }

    /**
     * @param gramm количество грамм
     * @return общее количество углеводов блюда
     */
    public float getUg(float gramm){
        float ug=0;
        float counts=0;
        for (FoodCount f : list) {
            counts+=f.count;
        }
        for (FoodCount f : list) {
            ug+=f.food.getUg(f.count/counts*gramm);
        }
        return ug;
    }

    /**
     * @param gramm количество грамм
     * @return общее количество белка блюда
     */
    public float getBel(float gramm){
        float bel=0;
        float counts=0;
        for (FoodCount f : list) {
            counts+=f.count;
        }
        for (FoodCount f : list) {
            bel+=f.food.getBel(f.count/counts*gramm);
        }
        return bel;
    }

    /**
     * @param gramm количество грамм
     * @return общее количество жира блюда
     */
    public float getZh(float gramm){
        float zh=0;
        float counts=0;
        for (FoodCount f : list) {
            counts+=f.count;
        }
        for (FoodCount f : list) {
            zh+=f.food.getZh(f.count/counts*gramm);
        }
        return zh;
    }

    /**
     * калькуляция графика распределения энергии
     */
    public void energyCalculate(){
        float en;
        float counts=0;
        for (FoodCount f : list) {
            counts+=f.count;
        }
        for (int i = 0; i <= Data.ASSIMILATION; i++) {
            en=0;
            for (FoodCount f : list) {
                en+=f.food.getEnergy(f.count/counts, i);
            }
            values.add(i, en);
        }
    }

    /**
     * @param f (продукт, пропорция)
     * @return блюдо с добавленным ингридиентом
     */
    public Dish addFood(FoodCount ... f){
        for (FoodCount fc:f){
            list.add(fc);
        }
        energyCalculate();
        return this;
    }

    /**
     * @param f продукт
     * @param c пропорция
     * @return блюдо с добавленным ингридиентом
     */
    public Dish addFood(Food f, float c){
        list.add(new FoodCount(f,c));
        energyCalculate();
        return this;
    }

    /**
     * Класс еда+пропорция
     */
    public class FoodCount{
        public Food food;
        public float count;
        public FoodCount(Food f, float c){
            food=f;
            count=c;
        }
    }

    @Override
    public String toString() {
        String s=name+": ";
        for (FoodCount foodCount : list) {
            s+=foodCount.food.getName() + " " + foodCount.count + "\n";
        }
        return s;
    }

    @Override
    public void draw(Graphics g,float x,float y,float width,float height){
        values.draw(g, x, y, width, height);
    }

    @Override
    public void mouseOver(float x, float y, float width, float height, float mouseX, float mouseY) {
        values.mouseOver(x, y, width, height, mouseX, mouseY);
    }
}
