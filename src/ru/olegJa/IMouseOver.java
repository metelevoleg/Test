package ru.olegJa;

/**
 * Created by олег on 01.08.2018.
 * Мышконаводящийся
 */
public interface IMouseOver {
    /**
     * @param x левый край
     * @param y верхний край
     * @param width длина
     * @param height высота
     * @param mouseX расположение мыши по X
     * @param mouseY расположение мыши по Y
     */
    public void mouseOver(float x, float y, float width, float height, float mouseX, float mouseY);
}
