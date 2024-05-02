package doaa.project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVUtility {

    public static String[] readFirstRowData(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();  // Read the first line of the CSV
            if (line != null) {
                // Strip outer quotes and split by comma
                String[] data = line.replace("\"", "").split(",");
                return data; // Return the array of data, with quotes removed
            }
        } catch (IOException e) {
            System.err.println("Error reading from CSV file: " + e.getMessage());
            throw e;
        }
        return new String[0]; // Return empty array if no data or error
    }
    
    
    public static void writeToCSV(List<String[]> data) throws IOException {
        FileWriter csvWriter = null;
        try {
            // Open the file in append mode
            csvWriter = new FileWriter(Config.CSV_FILE_PATH, true);
            for (String[] record : data) {
                String csvLine = String.join(",", record);
                csvWriter.append("\n");
                csvWriter.append(csvLine);
            }
            csvWriter.flush();
        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
            throw e;
        } finally {
            if (csvWriter != null) {
                try {
                    csvWriter.close();
                } catch (IOException e) {
                    System.err.println("Error closing CSV writer: " + e.getMessage());
                }
            }
        }
    }

}
