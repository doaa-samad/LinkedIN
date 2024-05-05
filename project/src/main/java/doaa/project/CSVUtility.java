package doaa.project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVUtility {

	public static String[] readFirstRowData(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            if (line != null) {
                return line.replace("\"", "").split(",");
            }
        } catch (IOException e) {
            System.err.println("Error reading from CSV file: " + e.getMessage());
            throw e;
        }
        return new String[0];
    }
    
    // Writing LinkedIn data to CSV
    public static void writeToCSV(List<String[]> data) throws IOException {
        try (FileWriter csvWriter = new FileWriter(Config.CSV_FILE_PATH, true)) {
            for (String[] record : data) {
                String csvLine = String.join(",", record);
                csvWriter.append(csvLine + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
            throw e;
        }
    }
    
    // Writing Google hrefs to CSV starting two rows below the last entry of LinkedIn data
    public static void appendGoogleHrefsToCSV(List<String> googleHrefs) throws IOException {
        try (FileWriter csvWriter = new FileWriter(Config.CSV_FILE_PATH, true)) {
            // Append two blank lines first
            csvWriter.append("\n\n");
            for (String href : googleHrefs) {
                csvWriter.append("Google URL: " + href + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
            throw e;
        }
    }
}

    

