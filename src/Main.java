import models.Business;
import models.HashTable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        HashTable<String, Business> hashTable = new HashTable<>(1000);
        String csvFile = "bussines.csv";
        String line = "";
        String splitBy = ",";
        int id = 1;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] businessData = line.split(splitBy); // use comma as separator
                if (businessData.length < 5) continue; // Ensure correct number of fields
                Business business = new Business(businessData[0], businessData[1], businessData[2], businessData[3], businessData[4]);
                hashTable.put(businessData[0], business);
                id++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean exit = false;
        while (!exit) {
            System.out.println("\n=== Menú ===");
            System.out.println("1. Ingresar todos los datos al mapa");
            System.out.println("2. Buscar con función 1");
            System.out.println("3. Buscar con función 2");
            System.out.println("4. Salir");

            try {
                System.out.print("Ingrese una opción: ");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String option = reader.readLine();

                switch (option) {
                    case "1":
                        System.out.println("Ingresando todos los datos al mapa...");
                        long startTimeInsert = System.nanoTime();
                        insertData(hashTable, csvFile, splitBy);
                        long endTimeInsert = System.nanoTime();
                        double insertionTime = (endTimeInsert - startTimeInsert) / 1_000_000.0;
                        System.out.println("Tiempo de inserción: " + insertionTime + " ms");
                        break;
                    case "2":
                        System.out.print("Ingrese el ID a buscar con función 1: ");
                        String idToSearch1 = reader.readLine().trim();
                        long startTimeSearch1 = System.nanoTime();
                        searchAndPrint(hashTable, idToSearch1);
                        long endTimeSearch1 = System.nanoTime();
                        double searchTime1 = (endTimeSearch1 - startTimeSearch1) / 1_000_000.0;
                        System.out.println("Tiempo de búsqueda con función 1: " + searchTime1 + " ms");
                        break;
                    case "3":
                        System.out.print("Ingrese el ID a buscar con función 2: ");
                        String idToSearch2 = reader.readLine().trim();
                        long startTimeSearch2 = System.nanoTime();
                        searchAndPrint(hashTable, idToSearch2);
                        long endTimeSearch2 = System.nanoTime();
                        double searchTime2 = (endTimeSearch2 - startTimeSearch2) / 1_000_000.0;
                        System.out.println("Tiempo de búsqueda con función 2: " + searchTime2 + " ms");
                        break;
                    case "4":
                        System.out.println("Saliendo del programa...");
                        exit = true;
                        break;
                    default:
                        System.out.println("Opción no válida. Por favor, ingrese una opción del 1 al 4.");
                        break;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void insertData(HashTable<String, Business> hashTable, String csvFile, String splitBy) {
        String line;
        int id = 1;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] businessData = line.split(splitBy); // use comma as separator
                if (businessData.length < 5) continue; // Ensure correct number of fields
                Business business = new Business(businessData[0], businessData[1], businessData[2], businessData[3], businessData[4]);
                hashTable.put(businessData[0], business);
                System.out.println("[" + id + "] " + business);
                id++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void searchAndPrint(HashTable<String, Business> hashTable, String idToSearch) {
        long startTime = System.nanoTime();
        Business searchResult = hashTable.search(idToSearch);
        long endTime = System.nanoTime();

        if (searchResult != null) {
            System.out.println("Resultado de la búsqueda: " + searchResult);
        } else {
            System.out.println("Negocio no encontrado para el ID: " + idToSearch);
        }

        double searchTime = (endTime - startTime) / 1_000_000.0;
        System.out.println("Tiempo de búsqueda: " + searchTime + " ms");
    }
}
