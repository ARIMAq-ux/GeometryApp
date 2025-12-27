package main.java.by.geometry.app;

import main.java.by.geometry.service.MenuService;
import main.java.by.geometry.util.*;
import java.util.Scanner;

public class GeometryApp {
    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {
            FigureList figureList = new FigureList();

            MenuService menuService = new MenuService(scanner, figureList);
            menuService.run();

        } catch (Exception e) {
            System.err.println("Fatal error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}