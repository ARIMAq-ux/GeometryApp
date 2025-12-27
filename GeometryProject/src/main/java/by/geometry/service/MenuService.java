package main.java.by.geometry.service;

import main.java.by.geometry.model.*;
import main.java.by.geometry.util.*;
import main.java.by.geometry.exception.GeometryException;
import java.util.*;

public class MenuService {
    private final Scanner scanner;
    private final FigureList figureList;
    private static final String FILE_PATH = "src/main/resources/shapes.txt";

    public MenuService(Scanner scanner, FigureList figureList) {
        this.scanner = scanner;
        this.figureList = figureList;
    }

    public void run() {
        System.out.println("=== Geometry Figures Application ===");

        while (true) {
            printMainMenu();
            String choice = scanner.nextLine().trim();

            try {
                boolean shouldContinue = processChoice(choice);
                if (!shouldContinue) break;
            } catch (GeometryException e) {
                System.err.println("Geometry error: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }

            System.out.println(); // пустая строка для читаемости
        }
    }

    private void printMainMenu() {
        System.out.println("\nMain Menu:");
        System.out.println("1. Create new figure");
        System.out.println("2. Show all figures");
        System.out.println("3. Sort figures");
        System.out.println("4. Move figure");
        System.out.println("5. Load from file");
        System.out.println("6. Save to file");
        System.out.println("7. Clear all figures");
        System.out.println("8. Exit");
        System.out.print("Choose option (1-8): ");
    }

    private boolean processChoice(String choice) {
        return switch (choice) {
            case "1" -> {
                createFigureMenu();
                yield true;
            }
            case "2" -> {
                figureList.printAll();
                yield true;
            }
            case "3" -> {
                sortFiguresMenu();
                yield true;
            }
            case "4" -> {
                moveFigureMenu();
                yield true;
            }
            case "5" -> {
                loadFromFile();
                yield true;
            }
            case "6" -> {
                saveToFile();
                yield true;
            }
            case "7" -> {
                clearFigures();
                yield true;
            }
            case "8" -> {
                System.out.println("Goodbye!");
                yield false;
            }
            default -> {
                System.out.println("Invalid choice. Please enter 1-8.");
                yield true;
            }
        };
    }

    private void createFigureMenu() {
        System.out.println("\nCreate Figure:");
        System.out.println("1. Rectangle");
        System.out.println("2. Circle");
        System.out.println("3. Ellipse");
        System.out.println("4. Back to main menu");
        System.out.print("Choose figure type (1-4): ");

        String type = scanner.nextLine().trim();

        switch (type) {
            case "1":
                createRectangle();
                break;
            case "2":
                createCircle();
                break;
            case "3":
                createEllipse();
                break;
            case "4":
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void createRectangle() {
        System.out.println("\nCreating Rectangle:");
        System.out.println("Enter coordinates for opposite corners (x1 y1 x2 y2):");
        System.out.print("> ");

        String input = scanner.nextLine().trim();
        String[] parts = input.split("\\s+");

        if (parts.length != 4) {
            System.out.println("Error: Expected 4 numbers");
            return;
        }

        try {
            double x1 = Double.parseDouble(parts[0]);
            double y1 = Double.parseDouble(parts[1]);
            double x2 = Double.parseDouble(parts[2]);
            double y2 = Double.parseDouble(parts[3]);

            Rectangle rect = new Rectangle(x1, y1, x2, y2);
            figureList.add(rect);
            System.out.println("Rectangle created successfully!");

        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format");
        } catch (GeometryException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void createCircle() {
        System.out.println("\nCreating Circle:");
        System.out.println("Enter center coordinates and border point (cx cy bx by):");
        System.out.print("> ");

        String input = scanner.nextLine().trim();
        String[] parts = input.split("\\s+");

        if (parts.length != 4) {
            System.out.println("Error: Expected 4 numbers");
            return;
        }

        try {
            double cx = Double.parseDouble(parts[0]);
            double cy = Double.parseDouble(parts[1]);
            double bx = Double.parseDouble(parts[2]);
            double by = Double.parseDouble(parts[3]);

            Circle circle = new Circle(cx, cy, bx, by);
            figureList.add(circle);
            System.out.println("Circle created successfully!");

        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format");
        } catch (GeometryException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void createEllipse() {
        System.out.println("\nCreating Ellipse:");
        System.out.println("Enter center and two points on axes (cx cy x1 y1 x2 y2):");
        System.out.print("> ");

        String input = scanner.nextLine().trim();
        String[] parts = input.split("\\s+");

        if (parts.length != 6) {
            System.out.println("Error: Expected 6 numbers");
            return;
        }

        try {
            double cx = Double.parseDouble(parts[0]);
            double cy = Double.parseDouble(parts[1]);
            double x1 = Double.parseDouble(parts[2]);
            double y1 = Double.parseDouble(parts[3]);
            double x2 = Double.parseDouble(parts[4]);
            double y2 = Double.parseDouble(parts[5]);

            Ellipse ellipse = new Ellipse(cx, cy, x1, y1, x2, y2);
            figureList.add(ellipse);
            System.out.println("Ellipse created successfully!");

        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format");
        } catch (GeometryException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void sortFiguresMenu() {
        if (figureList.isEmpty()) {
            System.out.println("No figures to sort.");
            return;
        }

        System.out.println("\nSort Figures:");
        System.out.println("1. Sort by area");
        System.out.println("2. Sort by perimeter");
        System.out.print("Choose sort type (1-2): ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                figureList.sortByArea();
                System.out.println("Figures sorted by area.");
                break;
            case "2":
                figureList.sortByPerimeter();
                System.out.println("Figures sorted by perimeter.");
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void moveFigureMenu() {
        if (figureList.isEmpty()) {
            System.out.println("No figures to move.");
            return;
        }

        figureList.printAll();
        System.out.print("Enter figure number to move (0-" + (figureList.size()-1) + "): ");

        try {
            int index = Integer.parseInt(scanner.nextLine().trim());

            if (index < 0 || index >= figureList.size()) {
                System.out.println("Invalid figure number.");
                return;
            }

            System.out.print("Enter movement vector (dx dy): ");
            String[] parts = scanner.nextLine().trim().split("\\s+");

            if (parts.length != 2) {
                System.out.println("Error: Expected 2 numbers");
                return;
            }

            double dx = Double.parseDouble(parts[0]);
            double dy = Double.parseDouble(parts[1]);

            IFigure figure = figureList.get(index);

            if (figure instanceof Rectangle rect) {
                rect.moveRectangle(dx, dy);
            } else if (figure instanceof Circle circle && !(figure instanceof Ellipse)) {
                circle.moveCircle(dx, dy);
            } else if (figure instanceof Ellipse ellipse) {
                ellipse.moveCircle(dx, dy);
            }

            System.out.println("Figure moved successfully!");

        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void loadFromFile() {
        List<IFigure> loaded = FileReaderUtil.readFiguresFromFile(FILE_PATH);

        if (!loaded.isEmpty()) {
            for (IFigure figure : loaded) {
                try {
                    figureList.add(figure);
                } catch (Exception e) {
                    System.err.println("Skipping invalid figure: " + e.getMessage());
                }
            }
            System.out.println("Loaded " + loaded.size() + " figures from file.");
        }
    }

    private void saveToFile() {
        if (figureList.isEmpty()) {
            System.out.println("No figures to save.");
            return;
        }

        FileReaderUtil.saveFiguresToFile(FILE_PATH, figureList.getFigures());
    }

    private void clearFigures() {
        System.out.print("Are you sure you want to clear all figures? (yes/no): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();

        if (confirmation.equalsIgnoreCase("yes") || confirmation.equalsIgnoreCase("y")) {
            figureList.clear();
            System.out.println("All figures cleared.");
        } else {
            System.out.println("Operation cancelled.");
        }
    }
}