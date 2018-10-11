package ru.olegJa;

import org.newdawn.slick.Graphics;

/**
 * Рисуемый
 */
public interface IDraw {
    /**
     * @param g ссылка на графику
     * @param x левый край
     * @param y верхний край
     * @param width длина
     * @param height высота
     */
    public void draw(Graphics g, float x, float y, float width, float height);
}
