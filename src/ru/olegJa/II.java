package ru.olegJa;

/**
 * Created by олег on 11.10.2018.
 */
public class II {
    static Resources resources=new Resources();
    public static void sendEnergy(float en){
        float enegry=resources.energyTime();
        enegry-=en;
        resources.energyToOrgans(energy);
    }
}
