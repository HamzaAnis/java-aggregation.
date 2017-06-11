
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Aggregate {

    static String operation = "";
    static String aggregatedColumn = "";
    static String inputFile = "";
    public static ArrayList<String> groupColumns = null;
    public static ArrayList<ArrayList<String>> csvData;
    public static ArrayList<ArrayList<String>> desiredColumnDataforAggregation; //An array list to 

    public static void main(String args[]) throws IOException {
        groupColumns = new ArrayList<String>();
        csvData = new ArrayList<ArrayList<String>>();
        desiredColumnDataforAggregation = new ArrayList<ArrayList<String>>();
        int columnCount = 0;
        if (args.length > 0) {
            operation = args[0];
            aggregatedColumn = args[1];
            inputFile = args[2];
//            System.out.println("Aggregated column is " + aggregatedColumn);
//            System.out.println("Operation is " + operation);

            System.out.println(args.length);
            while (columnCount + 3 < args.length) {
                try {
                    groupColumns.add(args[columnCount + 3]);
//                    System.out.println("Group columns are " + groupColumns.get(columnCount));
                } catch (Exception e) {
                    System.out.println("Null exception at " + columnCount + 3);
                }
                columnCount++;
            }
            readCSVFile();
            getDesiredColumns();

        }

    }

    public static void readCSVFile() throws IOException {

        BufferedReader bufferReader = null;

        try {
            String line;
            bufferReader = new BufferedReader(new FileReader(inputFile));

            while ((line = bufferReader.readLine()) != null) {
                ArrayList<String> csvLine = getArrayList(line);
                csvData.add(csvLine);
            }

        } catch (IOException e) {
            System.out.println("The system can not find the file named \"" + inputFile + "\"\n Program is exiting now");
        } finally {
            if (bufferReader != null) {
                bufferReader.close();
            }
        }
    }

    public static ArrayList<String> getArrayList(String line) {
        ArrayList<String> crunchifyResult = new ArrayList<>();

        if (line != null) {
            String[] splitData = line.split("\\s*,\\s*");
            for (String splitData1 : splitData) {
                if (!(splitData1 == null) || !(splitData1.length() == 0)) {
                    crunchifyResult.add(splitData1.trim());
                }
            }
        }

        return crunchifyResult;
    }

    public static void getDesiredColumns() {
        ArrayList<Integer> toKeep = new ArrayList<Integer>();
        ArrayList<String> columnnsName = new ArrayList<String>();//As first column of the array list has the column name
        columnnsName = csvData.get(0);

        int i = 0;
        for (String temp : columnnsName) {
//            System.out.println("Here " +groupColumns.contains(temp)+"  " + temp);

            if (groupColumns.contains(temp) || temp.equals(aggregatedColumn)) {
                toKeep.add(i);
                //                System.out.println(temp + " is selected");
            }
            i++;
        }
//        System.out.println(toKeep);

        for (ArrayList<String> rows : csvData) {
//            System.out.println(rows);
//            System.out.println(rows.size());
            ArrayList<String> tempColumn = new ArrayList<>();// Will be clear on every iteration
            for (i = 0; i < rows.size(); i++) {
                if (toKeep.contains(i)) {
                    tempColumn.add(rows.get(i));
                }
            }
            desiredColumnDataforAggregation.add(tempColumn);
        }
        System.out.println(desiredColumnDataforAggregation);
//System.out.println(csvData);
    }
}
