import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class CSVtoSQLite {
    public static void main (String[] args) {
        ArrayList<String> validData = filterCSV();
        createTable("ms3Interview.db");
        // the if statement just ensures that validData doesn't not exist and isn't non-existent
        if (validData != null && validData.size() != 0) {
            insertData("ms3Interview.db", validData);
        }
    }

    public static void parseBadData (ArrayList<String> valid, ArrayList<String> invalid) {
        FileWriter csvWriter = null;
        try {
            // creates and sets up new csv file for invalid data
            csvWriter = new FileWriter("ms3Interview-bad.csv");
            csvWriter.append(valid.get(0));
            csvWriter.append("\n");
            for (String rowData : invalid) {
                csvWriter.append(String.join(",", rowData));
                csvWriter.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (csvWriter != null) {
                try {
                    csvWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static ArrayList<String> filterCSV () {
        // data import -- could be a Scanner if user input is desired
        String csvPath = "C:/Users/Ethan/Desktop/Workspace/Coding/01_csv_to_sqlite/ms3Interview.csv";

        // initial variables for further storage
        String line = "";
        ArrayList<String> valid = new ArrayList<>();
        ArrayList<String> invalid = new ArrayList<>();

        try {
            // imports/reads data
            BufferedReader csvReader = new BufferedReader(new FileReader(csvPath));
            // iterates through data and stores into array
            while ((line = csvReader.readLine()) != null) {
                String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                Boolean isValid = true;
                for (int i = 0; i < values.length; i++) {
                    // pushes invalid rows into array list that gets pushed into separate csv file
                    if (values[i].isEmpty()) {
                        invalid.add(line);
                        isValid = false;
                    }
                }

                // pushes valid rows into array list
                if (isValid) {
                    valid.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (invalid.size() > 0) {
            parseBadData(valid, invalid);
        }
        return valid;
    }

    public static void createTable (String fileName) {
        // creates database file at this location
        String databasePath = "jdbc:sqlite:C:/Users/Ethan/Desktop/Workspace/Coding/01_csv_to_sqlite/" + fileName;

        // initializes connection with sqlite drivers
        try (Connection conn = DriverManager.getConnection(databasePath)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                Statement createTable = conn.createStatement();
                // creates sqlite table in database
                createTable.executeQuery(
                "CREATE TABLE IF NOT EXISTS Valid_Data"+
                        "(A STRING,"+
                        "B STRING,"+
                        "C STRING,"+
                        "D STRING,"+
                        "E STRING,"+
                        "F STRING,"+
                        "G STRING,"+
                        "H STRING,"+
                        "I STRING,"+
                        "J STRING,"+
                        // prevents duplicate rows
                        "unique (A,B,C,D,E,F,G,H,I,J))"
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertData (String fileName, ArrayList<String> validData) {
        // points to database location again
        String databasePath = "jdbc:sqlite:C:/Users/Ethan/Desktop/Workspace/Coding/01_csv_to_sqlite/" + fileName;
        // stores sqlite query
        String sql = "INSERT INTO Valid_Data (A,B,C,D,E,F,G,H,I,J) VALUES (?,?,?,?,?,?,?,?,?,?)";

        try (
            Connection conn = DriverManager.getConnection(databasePath);
            PreparedStatement statement = conn.prepareStatement(sql);
        ) {
            // iterates through validData and sets columns one row at a time
            for (int i = 1; i < validData.size(); i++) {
                String[] values = validData.get(i).split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                statement.clearParameters();
                statement.setString(1, values[0]);
                statement.setString(2, values[1]);
                statement.setString(3, values[2]);
                statement.setString(4, values[3]);
                statement.setString(5, values[4]);
                statement.setString(6, values[5]);
                statement.setString(7, values[6]);
                statement.setString(8, values[7]);
                statement.setString(9, values[8]);
                statement.setString(10, values[9]);
                statement.addBatch();
            }
            statement.clearParameters();
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
