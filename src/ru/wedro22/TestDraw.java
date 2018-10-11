package ru.wedro22;

import org.newdawn.slick.*;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import ru.olegJa.Dish;
import ru.olegJa.Food;
import ru.olegJa.Info;
import ru.olegJa.Utilite;
import ru.olegJa.allcolor.RadialFill;
import ru.olegJa.allcolor.Spector;
import ru.olegJa.allcolor.Texture;
import ru.wedro22.database.DBSqlite;

public class TestDraw extends BasicGame {
    private GameContainer container;    //НЕ УДАЛЯТЬ






    /**
     * Create a new basic game
     *
     * @param title The title for the game
     */
    public TestDraw(String title) {
        super(title);
    }


    @Override
    public void init(GameContainer container) throws SlickException {
        container.setShowFPS(false);//НЕ УДАЛЯТЬ
        this.container=container;   //НЕ УДАЛЯТЬ


    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {

    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {


    }

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {

    }

    @Override
    public void keyPressed(int key, char c) {
        if (key==Input.KEY_F)       //НЕ УДАЛЯТЬ
            container.setShowFPS(!container.isShowingFPS());    //НЕ УДАЛЯТЬ
    }

    public static void main(String[] args){
        try {
            new AppGameContainer(new TestDraw("TestDraw"),800,600,false).start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
