package main.java.by.geometry.model;

import main.java.by.geometry.exception.GeometryException;

public class Circle extends Segment implements IFigure {

    // Конструктор с отрезком (радиус)
    public Circle(Segment radius) {
        super(radius.getStart(), radius.getEnd());
        validateRadius();
    }

    // Конструктор с двумя точками: центр и граница
    public Circle(Point center, Point border) {
        super(center, border);
        validateRadius();
    }

    // Конструктор с двумя парами координат
    public Circle(double cx, double cy, double bx, double by) {
        super(cx, cy, bx, by);
        validateRadius();
    }

    private void validateRadius() {
        double radius = length();
        if (radius <= 0) {
            throw new GeometryException("Circle radius must be positive (got %.2f)", radius);
        }
        if (radius > 10000) {
            throw new GeometryException("Circle radius too large (%.2f). Max is 10000", radius);
        }
    }

    public double getRadius() {
        return length();
    }

    @Override
    public double area() {
        double radius = getRadius();
        return Math.PI * radius * radius;
    }

    @Override
    public double perimeter() {
        return 2 * Math.PI * getRadius();
    }

    /**
     * Перемещение центра круга
     */
    public void moveCircle(double stepX, double stepY) {
        moveSegment(stepX, stepY);
    }

    @Override
    public String toString() {
        return String.format("Circle[Center=%s, Radius=%.2f] Area=%.2f Perimeter=%.2f",
                getStart(), getRadius(), area(), perimeter());
    }
}