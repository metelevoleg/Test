package ru.olegJa;

import org.newdawn.slick.*;
import ru.wedro22.TestDraw;

/**
 * Created by олег on 20.09.2018.
 */
public class Okno extends BasicGame {
    private GameContainer container;
    Color color1=new Color(0,0,205);
    Color color2=Color.orange;
    Color color3=Color.black;
    //Info info1,info2;
    float w,h;
    Button b1,b2,b3;
    Button1 button1;

    /**
     * Create a new basic game
     *
     * @param title The title for the game
     */
    public Okno(String title) {
        super(title);
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        w=container.getWidth();
        h=container.getHeight();
        container.setShowFPS(false);
        this.container=container;
        container.getGraphics().setBackground(color1);
        button1=new Button1(w*0.1f,h*0.55f,w*0.4f,w*0.4f,"dish.png");
        button1.setActive(0.3f);
        /*info1=new Info(w*0.1f,h*0.2f,w*0.35f,h*0.6f,"dish.png",Color.green,Color.red,4000,0,
                2100);
        info1.setCurrent(300);
        info2=new Info(w*0.55f,h*0.2f,w*0.35f,h*0.6f,"dish.png",Color.green,Color.red,4000,0,
                2100);
        info2.setCurrent(500);*/
        b1=new Button(w*0.1f,h*0.8f,w*0.2f,w*0.2f,"bracer1.png");
        b2=new Button(w*0.4f,h*0.8f,w*0.2f,w*0.2f,"graph.png");
        b3=new Button(w*0.7f,h*0.8f,w*0.2f,w*0.2f,"options.png");
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {

    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        button1.draw(g);
        /*info1.draw(g);
        info2.draw(g);*/
        b1.draw(g);
        b2.draw(g);
        b3.draw(g);
    }
    public void keyPressed(int key, char c) {
        if (key== Input.KEY_F)       //НЕ УДАЛЯТЬ
            container.setShowFPS(!container.isShowingFPS());    //НЕ УДАЛЯТЬ
    }

    public static void main(String[] args){
        try {
            new AppGameContainer(new Okno("IAm"),360,640,false).start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
