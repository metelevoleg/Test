package ru.olegJa;

import ru.wedro22.proportion.ProportionTime;

/**
 * Класс для хранения различных данных
 */
public class Data {
    /**
     * энергия окисления нутриентов
     */
    public static final float BEL_ENERGY=17.2f, ZH_ENERGY=38.9f, UG_ENERGY=17.2f;



    /**
     * время усвоения пищи
     */
    public static final int ASSIMILATION=1440;

    /**
     * график усвоения углеводов
     */
    public static ProportionTime graphUg=new ProportionTime(0,480)
            .add(60,60).add(180,100).add(240,60).add(300,40);

    /**
     * график усвоения белков
     */
    public static ProportionTime graphBel=new ProportionTime(0,720)
            .add(60,100).add(120,90).add(180,60);

    /**
     * график усвоения жиров
     */
    public static ProportionTime graphZhir=new ProportionTime(0,720)
            .add(180,20).add(300,60);
}
