package main.java.by.geometry.model;

import main.java.by.geometry.exception.GeometryException;

public class Ellipse extends Circle {
    private double r2; // Второй радиус
    private Point secondBorderPoint; // Вторая точка на границе

    // Конструктор с двумя отрезками (радиусы)
    public Ellipse(Segment r1, Segment r2) {
        super(r1);
        this.secondBorderPoint = r2.getEnd();
        setR2(r2.length());
    }

    // Конструктор с тремя точками
    public Ellipse(Point center, Point p1, Point p2) {
        super(center, p1);
        this.secondBorderPoint = p2;
        setR2(center.distanceTo(p2));
    }

    // Конструктор с тремя парами координат
    public Ellipse(double cx, double cy, double x1, double y1, double x2, double y2) {
        super(cx, cy, x1, y1);
        this.secondBorderPoint = new Point(x2, y2);
        setR2(Math.sqrt(Math.pow(x2 - cx, 2) + Math.pow(y2 - cy, 2)));
    }

    public void setR2(double r2) {
        if (r2 <= 0) {
            throw new GeometryException("Ellipse second radius must be positive (got %.2f)", r2);
        }
        this.r2 = r2;
    }

    public double getR1() {
        return length(); // Первый радиус из Circle
    }

    public double getR2() {
        return r2;
    }

    public Point getSecondBorderPoint() {
        return secondBorderPoint;
    }

    public void setSecondBorderPoint(Point point) {
        if (point == null) {
            throw new GeometryException("Second border point cannot be null");
        }
        this.secondBorderPoint = point;
        setR2(getStart().distanceTo(point));
    }

    public Point getFirstBorderPoint() {
        return getEnd(); // Из Circle
    }

    @Override
    public double area() {
        return Math.PI * getR1() * getR2();
    }

    @Override
    public double perimeter() {
        double r1 = getR1();
        double r2 = getR2();
        double D = 2 * Math.max(r1, r2);
        double d = 2 * Math.min(r1, r2);
        return (4 * Math.PI * D * d + Math.pow(D - d, 2)) / (D + d);
    }

    @Override
    public void moveCircle(double stepX, double stepY) {
        super.moveCircle(stepX, stepY);
        secondBorderPoint.move(stepX, stepY);
    }

    @Override
    public String toString() {
        return String.format("Ellipse[Center=%s, R1=%.2f, R2=%.2f] Area=%.2f Perimeter=%.2f",
                getStart(), getR1(), getR2(), area(), perimeter());
    }
}