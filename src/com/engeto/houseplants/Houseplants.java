package com.engeto.houseplants;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;

public class Houseplants {
    public static void main(String[] args) {
        ListOfPlants listOfPlants = new ListOfPlants();
        Logger logger = Logger.getLogger(Houseplants.class.getName());
        try {
            List<Plant> loadedList = ListOfPlants
                    .importFromFile(Settings.getFile(), "\t");
            listOfPlants.setListOfPlants(loadedList);

            System.out.println("Print watering info for each plant "
                    + "(imported from file \"" + Settings.getFile()
                    + "\" into a list):");
            for (Plant plant : listOfPlants.getListOfPlants()) {
                System.out.println(plant.getWateringInfo());
            }
            System.out.println("---");

            Plant plant1 = new Plant("Růže");
            listOfPlants.addPlant(plant1);
            Plant plant2 = new Plant("Tulipán", "pěkná květina",
                    LocalDate.of(2022, 5, 12),
                    LocalDate.of(2022, 5, 17), 5);
            listOfPlants.addPlant(plant2);
            listOfPlants.removePlantAtIndex(1);

            ListOfPlants.exportToFile(listOfPlants.getListOfPlants(),
                    Settings.getFileNew());
            System.out.println("Print contents of file \""
                    + Settings.getFileNew()
                    + "\" (2 plants added, 1 removed):");
            Scanner scanner = new Scanner(new File(Settings.getFileNew()));
            while (scanner.hasNext()) {
                System.out.println(scanner.nextLine());
            }
            System.out.println("---");

            System.out.println("Import of file with wrong datum\n"
                    + "(comment out this import in source code to see "
                    + "the other one in action):");
            List<Plant> loadedListWrongDatum = ListOfPlants.importFromFile(
                    Settings.getFileWrongDatum(), "\t");

            System.out.println("Import of file with wrong frequency of "
                    + "Watering:");
            List<Plant> loadedListWrongFrequency = ListOfPlants.importFromFile(
                    Settings.getFileWrongFrequency(), "\t");
        } catch(FileNotFoundException | PlantException e) {
            logger.log(Level.WARNING, e.getClass().getName() + ": "
                    + e.getMessage());
        }
    }
}
