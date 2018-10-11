package ru.olegJa;

import sun.util.calendar.Gregorian;

import java.util.GregorianCalendar;

/**
 * Created by олег on 11.10.2018.
 */
public class EventBracer {
    public static float getEnergy(){
        return FakeBracer.getEnergy();
    }
    public static void sendEnergy(){
        II.sendEnergy(getEnergy());
    }


    static class FakeBracer{
        static GregorianCalendar d=new GregorianCalendar(2018,10,11,0,0);


        public static float getEnergy() {
            float en;
            if (d.getTime().getHours()<6|d.getTime().getHours()<21)
                en=4;
            else if (d.getTime().getHours()<17)
                en=16;
            else en=12;

            return en;
        }
    }
}
