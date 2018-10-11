package ru.olegJa;

import org.newdawn.slick.Graphics;

import java.util.ArrayList;

/**
 * Created by олег on 26.05.2018.
 */
public class Resources implements IDraw, IMouseOver{
    ArrayList<DishCount>list=new ArrayList<>();
    Food organs;
    public float mass=70;
    public float size=180;

    /*static int energy(){
        int n=0;
        for (DishChange dishChange : list) {
            n+=dishChange.change.energy();
        }
        return n;
    }*/

    public void addDish(DishCount... dishCounts){
        for (DishCount dc : dishCounts) {
            list.add(dc);

        }
    }

    public void addDish(Dish d, long time, int gramm){
        DishCount dc = new DishCount(d, time, gramm);
        list.add(dc);
    }


    public float energyFood(){
        for (DishCount dc : list) {
            if (System.currentTimeMillis()-dc.time>86400000)
                deleteDish(dc);
        }

        float energy=0;
        for(DishCount dc:list){
            int min= (int) ((System.currentTimeMillis()-dc.time)/60000);
            energy+=dc.dish.getEnergy(dc.gramm, min);
        }
        return energy;
    }
    public float energyOrgans(){
        return 0;
    }
    public float energyTime(){
        return energyFood()+energyOrgans();
    }
    public int energyToOrgans(float en){
        return 0;
    }

    public void deleteDish(DishCount dc){
        list.remove(dc);
    }

    public class DishCount {
        public int gramm;
        public Dish dish;
        public long time;
        public DishCount(Dish d, long time, int gramm){
            dish=d;
            this.time=time;
            this.gramm=gramm;
        }
    }

    @Override
    public void draw(Graphics g, float x, float y, float width, float height) {

    }

    @Override
    public void mouseOver(float x, float y, float width, float height, float mouseX, float mouseY) {

    }
}
