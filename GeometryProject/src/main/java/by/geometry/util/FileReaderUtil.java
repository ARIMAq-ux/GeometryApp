package main.java.by.geometry.util;

import main.java.by.geometry.model.*;
import main.java.by.geometry.exception.GeometryException;
import java.io.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

public class FileReaderUtil {
    private static final DecimalFormatSymbols US_SYMBOLS =
            DecimalFormatSymbols.getInstance(Locale.US);
    private static final DecimalFormat DECIMAL_FORMAT =
            new DecimalFormat("0.0########", US_SYMBOLS); // до 8 знаков после точки

    private FileReaderUtil() {}

    public static List<IFigure> readFiguresFromFile(String filename) {
        List<IFigure> figures = new ArrayList<>();

        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("File not found: " + filename);
            return figures;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (line.isEmpty()) continue;

                try {
                    IFigure figure = parseFigureLine(line, lineNumber);
                    figures.add(figure);
                } catch (GeometryException e) {
                    System.err.printf("Line %d: %s%n", lineNumber, e.getMessage());
                } catch (Exception e) {
                    System.err.printf("Line %d: Unexpected error: %s%n", lineNumber, e.getMessage());
                }
            }

            System.out.printf("Loaded %d figures from %s%n", figures.size(), filename);

        } catch (IOException e) {
            throw new GeometryException("Error reading file %s: %s", filename, e.getMessage());
        }

        return figures;
    }

    private static IFigure parseFigureLine(String line, int lineNumber) {
        try {
            // Формат: TYPE|параметры
            String[] parts = line.split("\\|");
            if (parts.length != 2) {
                throw new GeometryException("Invalid format. Expected: TYPE|parameters", lineNumber);
            }

            String type = parts[0].toUpperCase();
            // Используем американскую локаль для парсинга
            Scanner scanner = new Scanner(parts[1]);
            scanner.useLocale(Locale.US);
            scanner.useDelimiter(",");

            List<Double> coordinates = new ArrayList<>();
            while (scanner.hasNext()) {
                if (scanner.hasNextDouble()) {
                    coordinates.add(scanner.nextDouble());
                } else {
                    throw new GeometryException("Line %d: Invalid number: %s", lineNumber, scanner.next());
                }
            }
            scanner.close();

            return switch (type) {
                case "RECTANGLE" -> {
                    if (coordinates.size() != 4) {
                        throw new GeometryException(
                                "Line %d: Rectangle requires 4 coordinates (x1,y1,x2,y2), got %d",
                                lineNumber, coordinates.size()
                        );
                    }
                    yield new Rectangle(
                            coordinates.get(0), coordinates.get(1),
                            coordinates.get(2), coordinates.get(3)
                    );
                }
                case "CIRCLE" -> {
                    if (coordinates.size() != 4) {
                        throw new GeometryException(
                                "Line %d: Circle requires 4 coordinates (cx,cy,bx,by), got %d",
                                lineNumber, coordinates.size()
                        );
                    }
                    yield new Circle(
                            coordinates.get(0), coordinates.get(1),
                            coordinates.get(2), coordinates.get(3)
                    );
                }
                case "ELLIPSE" -> {
                    if (coordinates.size() != 6) {
                        throw new GeometryException(
                                "Line %d: Ellipse requires 6 coordinates (cx,cy,x1,y1,x2,y2), got %d",
                                lineNumber, coordinates.size()
                        );
                    }
                    yield new Ellipse(
                            coordinates.get(0), coordinates.get(1),
                            coordinates.get(2), coordinates.get(3),
                            coordinates.get(4), coordinates.get(5)
                    );
                }
                default -> throw new GeometryException("Line %d: Unknown figure type: %s", lineNumber, type);
            };

        } catch (NumberFormatException e) {
            throw new GeometryException("Line %d: Invalid number format: %s", lineNumber, e.getMessage());
        } catch (InputMismatchException e) {
            throw new GeometryException("Line %d: Invalid coordinate format", lineNumber);
        }
    }

    public static void saveFiguresToFile(String filename, List<IFigure> figures) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (IFigure figure : figures) {
                String figureString = figureToFileString(figure);
                if (!figureString.isEmpty()) {
                    writer.println(figureString);
                }
            }
            System.out.printf("Saved %d figures to %s%n", figures.size(), filename);
        } catch (IOException e) {
            throw new GeometryException("Error saving to file %s: %s", filename, e.getMessage());
        }
    }

    private static String figureToFileString(IFigure figure) {
        if (figure instanceof Rectangle rect) {
            Point start = rect.getStart();
            Point end = rect.getEnd();
            return String.format(Locale.US, "RECTANGLE|%s,%s,%s,%s",
                    formatDouble(start.getX()),
                    formatDouble(start.getY()),
                    formatDouble(end.getX()),
                    formatDouble(end.getY()));

        } else if (figure instanceof Circle circle && !(figure instanceof Ellipse)) {
            Point center = circle.getStart();
            Point border = circle.getEnd();
            return String.format(Locale.US, "CIRCLE|%s,%s,%s,%s",
                    formatDouble(center.getX()),
                    formatDouble(center.getY()),
                    formatDouble(border.getX()),
                    formatDouble(border.getY()));

        } else if (figure instanceof Ellipse ellipse) {
            Point center = ellipse.getStart();
            Point border1 = ellipse.getFirstBorderPoint();
            Point border2 = ellipse.getSecondBorderPoint();

            return String.format(Locale.US, "ELLIPSE|%s,%s,%s,%s,%s,%s",
                    formatDouble(center.getX()),
                    formatDouble(center.getY()),
                    formatDouble(border1.getX()),
                    formatDouble(border1.getY()),
                    formatDouble(border2.getX()),
                    formatDouble(border2.getY()));
        }

        return "";
    }

    private static String formatDouble(double value) {
        String formatted = DECIMAL_FORMAT.format(value);
        if (formatted.endsWith(".0")) {
            formatted = formatted.substring(0, formatted.length() - 2);
        }
        return formatted;
    }

}