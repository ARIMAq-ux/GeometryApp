package main.java.by.geometry.model;

import main.java.by.geometry.exception.GeometryException;

public class Rectangle extends Segment implements IFigure {
    private Point b;
    private Point d;

    public Rectangle(Point a, Point c) {
        super(a, c);
        validateAndCalculatePoints();
    }

    public Rectangle(double x1, double y1, double x2, double y2) {
        super(x1, y1, x2, y2);
        validateAndCalculatePoints();
    }

    public Rectangle(Segment s1, Segment s2) {
        super(s1.getStart(), s2.getEnd());
        validateAndCalculatePoints();
    }

    private void validateAndCalculatePoints() {
        double ax = getX();
        double ay = getY();
        double cx = getEnd().getX();
        double cy = getEnd().getY();

        if (Math.abs(ax - cx) < 0.0001) {
            throw new GeometryException("Rectangle width cannot be zero");
        }
        if (Math.abs(ay - cy) < 0.0001) {
            throw new GeometryException("Rectangle height cannot be zero");
        }

        b = new Point(cx, ay);
        d = new Point(ax, cy);
    }

    public double getWidth() {
        return Math.abs(getEnd().getX() - getX());
    }

    public double getHeight() {
        return Math.abs(getEnd().getY() - getY());
    }

    @Override
    public double area() {
        return getWidth() * getHeight();
    }

    @Override
    public double perimeter() {
        return 2 * (getWidth() + getHeight());
    }

    public void moveRectangle(double stepX, double stepY) {
        moveSegment(stepX, stepY);
        b.move(stepX, stepY);
        d.move(stepX, stepY);
    }

    @Override
    public String toString() {
        return String.format("Rectangle[%s, %s, %s, %s] Area=%.2f Perimeter=%.2f",
                getStart(), b, getEnd(), d, area(), perimeter());
    }

}
