
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.PrintWriter;

public class Aggregate {

    static String operation = "";
    static String aggregatedColumn = "";
    static int aggregatedColumnNumber = 0;
    static String inputFile = "";
    public static ArrayList<String> groupColumns = null;  //The group columns on which we have to sort
    public static ArrayList<Integer> groupColumnsIndex = null; //The group column index 
    public static ArrayList<ArrayList<String>> csvData;
    public static ArrayList<ArrayList<String>> desiredColumnDataforAggregation; //An array list to have the only columns on which we have to appply aggrgation
    public static ArrayList<ArrayList<String>> helper;

    public static void main(String args[]) throws IOException {
        //initialization
        groupColumns = new ArrayList<String>();
        groupColumnsIndex = new ArrayList<Integer>();
        csvData = new ArrayList<ArrayList<String>>();
        desiredColumnDataforAggregation = new ArrayList<ArrayList<String>>();

        if (args.length > 0) {
            operation = args[0];//Operation is first argument
            aggregatedColumn = args[1];//To aggreagte the colun is the second one
            inputFile = args[2]; //Input file is third argument
//            System.out.println("Aggregated column is " + aggregatedColumn);
//            System.out.println("Operation is " + operation);

//            System.out.println(args.length);
            int columnCount = 0;
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
            writeCSVfile("outputfile.csv");
            getDesiredColumns();
//            helper = new ArrayList<ArrayList<String>>(csvData.size());
helper=desiredColumnDataforAggregation;

            mergerSorthelper(1, desiredColumnDataforAggregation.size() - 1);
            for (ArrayList<String> a : desiredColumnDataforAggregation) {
                System.out.println(a);
            }
//            System.out.println(desiredColumnDataforAggregation);

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

    public static void writeCSVfile(String fileName) throws IOException {
        PrintWriter pw = new PrintWriter(new File(fileName));

        for (ArrayList<String> row : csvData) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < row.size(); i++) {
                sb.append(row.get(i));
                if (i != row.size() - 1) {
                    sb.append(",");
                } else {
                    sb.append("\n");
                }
            }
            pw.write(sb.toString());
        }

        pw.close();

    }

    public static ArrayList<String> getArrayList(String line) {
        ArrayList<String> result = new ArrayList<>();

        if (line != null) {
            String[] splitData = line.split("\\s*,\\s*");
            for (String splitData1 : splitData) {
                if (!(splitData1 == null) || !(splitData1.length() == 0)) {
                    result.add(splitData1.trim());
                }
            }
        }

        return result;
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
                //Store only those which is not the aggrgatedcolumn
                if (!temp.equals(aggregatedColumn)) {
                    groupColumnsIndex.add(i);
                }
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
//        System.out.println(desiredColumnDataforAggregation);
//System.out.println(csvData);
    }

    public static void mergerSort() {
    }

    public static void mergerSorthelper(int low, int high) {
//        System.out.println("Called");
        if (low < high) {
            int middle = low + (high - low) / 2;
            mergerSorthelper(low, middle);
            mergerSorthelper(middle + 1, high);
            merge(low, middle, high);
        }
    }

//    number is the orignal
//    helper is of the sam size
    public static void merge(int low, int middle, int high) {
        for (int i = low; i <= high; i++) {
//            System.out.println("SET");
            helper.set(i, desiredColumnDataforAggregation.get(i));
        }
        int i = low;
        int j = middle + 1;
        int k = low;

        while (i <= middle && j <= high) {
            int check = getStringOfRow(helper.get(i)).compareTo(getStringOfRow(helper.get(j)));
            System.out.println(getStringOfRow(helper.get(i)) + "   and   " + getStringOfRow(helper.get(j)) + "  Check is " + check);
            if (check <= 0) {
                desiredColumnDataforAggregation.set(k, helper.get(i));
                i++;
            } else {
                desiredColumnDataforAggregation.set(k, helper.get(j));
                j++;
            }
        }

        while (i <= middle) {
            desiredColumnDataforAggregation.set(k, helper.get(i));
            k++;
            i++;
        }
    }

    public static String getStringOfRow(ArrayList<String> arrtoString) {
        StringBuilder str = new StringBuilder();
        for (int s : groupColumnsIndex) {
            str.append(arrtoString.get(s));
        }
        return str.toString();
    }
}
