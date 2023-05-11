package com.SWE.project.Classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.*;

public abstract class Sport {
    public static Set<String> availableSports = new HashSet<String>();
    public final static File file = new File("backEnd\\src\\main\\resources\\sports.txt");

    public static void main(String[] args) {
        try {
            readFile();
            saveNewSport("Volleyball");
            System.out.println(availableSports);
        } catch (FileNotFoundException e) {
            System.out.println("sports.txt not found");
        } catch (IOException e) {
            System.out.println("Error while writing to sports.txt");
        }
    }

    public static void readFile() throws FileNotFoundException {
        Scanner scanner = new Scanner(file);

        while (scanner.hasNext()) {
            availableSports.add(scanner.next());
        }

        scanner.close();
    }

    public static void saveNewSport(String newSport) throws IOException {
        availableSports.add(newSport);

        FileWriter fw = new FileWriter(file);
        availableSports.forEach((value) -> {
            try {
                fw.write(value + "\n");
            } catch (IOException e) {
                System.out.println("Error writing to sports.txt");
            }
        });

        fw.close();
    }
}