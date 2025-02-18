package data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class InvestmentDataStorage {

    public static void main(String[] args) {
        System.out.println("Hello");
        writeToFile("");
    }

    public static void writeToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("userData.csv"))) {
            writer.write("Test data1\n");
            writer.write("Test data2\n");
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
