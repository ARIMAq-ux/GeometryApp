package main.java.by.geometry.util;

import main.java.by.geometry.model.IFigure;
import java.util.*;

public class FigureList {
    private final List<IFigure> figures = new ArrayList<>();

    public void add(IFigure figure) {
        if (figure == null) {
            throw new IllegalArgumentException("Figure cannot be null");
        }
        figures.add(figure);
    }

    public void sortByArea() {
        figures.sort(Comparator.comparingDouble(IFigure::area));
    }

    public void sortByPerimeter() {
        figures.sort(Comparator.comparingDouble(IFigure::perimeter));
    }

    public List<IFigure> getFigures() {
        return Collections.unmodifiableList(figures);
    }

    public void printAll() {
        if (figures.isEmpty()) {
            System.out.println("No figures in the list.");
            return;
        }

        System.out.println("\n=== Figures List (" + figures.size() + " figures) ===");
        for (int i = 0; i < figures.size(); i++) {
            System.out.printf("%d. %s%n", i, figures.get(i));
        }
        System.out.println("=================================\n");
    }

    public void clear() {
        figures.clear();
    }

    public int size() {
        return figures.size();
    }

    public IFigure get(int index) {
        if (index < 0 || index >= figures.size()) {
            throw new IndexOutOfBoundsException("Invalid figure index: " + index);
        }
        return figures.get(index);
    }

    public boolean isEmpty() {
        return figures.isEmpty();
    }
}