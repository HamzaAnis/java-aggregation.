
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Aggregate {

    static String operation = "";
    static String aggregatedColumn = "";
    static int aggregatedColumnNumber=0;
    static String inputFile = "";
    public static ArrayList<String> groupColumns = null;
    public static ArrayList<ArrayList<String>> csvData;
    public static ArrayList<ArrayList<String>> desiredColumnDataforAggregation; //An array list to have the only columns on which we have to appply aggrgation

    public static void main(String args[]) throws IOException {
        //initialization
        groupColumns = new ArrayList<String>();
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
                if(temp.equals(aggregatedColumn)){
                    aggregatedColumnNumber=i;
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
        System.out.println(desiredColumnDataforAggregation);
//System.out.println(csvData);
    }

    public static void mergerSort() {
        ArrayList<ArrayList<String>> helper = new ArrayList<ArrayList<String>>();
    }

    public static void mergerSorthelper(int low, int high) {
        if (low < high) {
            int middle = low + (high - low) / 2;
            mergerSorthelper(low, middle);
            mergerSorthelper(middle + 1, high);
        }
    }
    
    public static void merge(int low,int middle,int high){
        int i=low;
        int j=middle+1;
        int k=low;
        
        while(i<middle&&j<=high){
            
        }
    }

}
