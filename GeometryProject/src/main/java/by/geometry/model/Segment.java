package main.java.by.geometry.model;

import main.java.by.geometry.exception.GeometryException;

public class Segment extends Point {
    private Point end;

    public Segment(Point start, Point end) {
        super(start.getX(), start.getY());
        setEnd(end);
    }

    public Segment(double x1, double y1, double x2, double y2) {
        super(x1, y1);
        this.end = new Point(x2, y2);
        validateSegment();
    }

    public Point getStart() {
        return new Point(getX(), getY());
    }

    public Point getEnd() {
        return end;
    }

    public void setEnd(Point end) {
        if (end == null) {
            throw new GeometryException("End point cannot be null");
        }
        this.end = end;
        validateSegment();
    }

    public void setStart(Point start) {
        if (start == null) {
            throw new GeometryException("Start point cannot be null");
        }
        setX(start.getX());
        setY(start.getY());
        validateSegment();
    }

    public double length() {
        return distanceTo(end);
    }

    public void moveSegment(double stepX, double stepY) {
        move(stepX, stepY);
        end.move(stepX, stepY);
    }

    private void validateSegment() {
        if (getStart().equals(end)) {
            throw new GeometryException("Segment cannot have zero length");
        }
    }

    @Override
    public String toString() {
        return String.format("Segment[%s -> %s, length=%.2f]",
                getStart(), end, length());
    }

}
