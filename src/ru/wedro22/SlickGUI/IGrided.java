package ru.wedro22.SlickGUI;



public interface IGrided {
    /**
     * @return сетка, на которой распологается элемент
     */
    public IGrided getParent();

    /**
     * @return сетка родительского элемента
     */
    public Grid getParentGrid();

    /**
     * @return стартовая ячейка элемента
     */
    public Grid.Cell getStart();

    /**
     * @return конечная ячейка элемента
     */
    public Grid.Cell getEnd();

    /**
     * установка родительского элемента, на котором размещаем this
     * @param parent родительский элемент
     */
    void setParent(IGrided parent);

    /**
     * установка стартовой ячейки элемента
     * @param start стартовая ячейка
     */
    public void setStart(Grid.Cell start);

    /**
     * установка конечной ячейки элемента
     * @param end конечная ячейка
     */
    public void setEnd(Grid.Cell end);

    /**
     * установка стартовой ячейки элемента
     * @param parent родительский элемент
     * @param start стартовая ячейка
     */
    public default void setStart(IGrided parent, Grid.Cell start){
        setParent(parent);
        setStart(start);
    }

    /**
     * установка конечной ячейки элемента
     * @param parent родительский элемент
     * @param end конечная ячейка
     */
    public default void setEnd(IGrided parent, Grid.Cell end){
        setParent(parent);
        setEnd(end);
    }

    /**
     * установка области расположения элемента
     * @param start стартовая ячейка
     * @param end конечная ячейка
     */
    public default void setLocate(Grid.Cell start, Grid.Cell end) {
        setStart(start);
        setEnd(end);
    }

    /**
     * установка области расположения элемента
     * @param parent родительский элемент
     * @param start стартовая ячейка
     * @param end конечная ячейка
     */
    public default void setLocate(IGrided parent, Grid.Cell start, Grid.Cell end) {
        setParent(parent);
        setStart(start);
        setEnd(end);
    }


    /**
     * @return сетка в this элементе
     */
    public Grid getGrid();

    /**
     * установить сетку в this элемент
     * @param grid сетка
     */
    void setGrid(Grid grid);
}
