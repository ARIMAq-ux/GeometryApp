package main.java.by.geometry.model;

import main.java.by.geometry.exception.GeometryException;

import java.util.Objects;

public class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        setX(x);
        setY(y);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        validateCoordinate(x, "X");
        this.x = x;
    }

    public void setY(double y) {
        validateCoordinate(y, "Y");
        this.y = y;
    }

    public void move(double stepX, double stepY) {
        setX(x + stepX);
        setY(y + stepY);
    }

    public double distanceTo(Point other) {
        if (other == null) {
            throw new GeometryException("Other point cannot be null");
        }
        double dx = other.x - this.x;
        double dy = other.y - this.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    private void validateCoordinate(double value, String coordinateName) {
        if (Double.isNaN(value)) {
            throw new GeometryException("%s coordinate cannot be NaN", coordinateName);
        }
        if (Double.isInfinite(value)) {
            throw new GeometryException("%s coordinate cannot be infinite", coordinateName);
        }
    }

    @Override
    public String toString() {
        return String.format("(%.2f; %.2f)", x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Point other)) return false;
        return Math.abs(this.x - other.x) < 0.0001 &&
                Math.abs(this.y - other.y) < 0.0001;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}