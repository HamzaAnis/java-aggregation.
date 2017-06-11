
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.util.Comparator;

public class Aggregate {

    static String operation = "";//sum,count ,avg
    static String aggregatedColumn = "";//the column name to perform aggregation
    static int aggregatedColumnNumber = 0;//the column number of the aggregated column
    static String inputFile = "";  //name of the input csv file
    public static ArrayList<String> groupColumns = null;  //The group columns on which we have to sort
    public static ArrayList<Integer> groupColumnsIndex = null; //The group column index used when we remove the unuse columns
    public static ArrayList<ArrayList<String>> csvData; //In this 2d array the data is stored
    public static ArrayList<ArrayList<String>> desiredColumnDataforAggregation; //An array list to have the only columns on which we have to appply aggrgation
//    public static ArrayList<ArrayList<String>> helper;
    public static ArrayList<ArrayList<ArrayList<String>>> operationArray; //An array to perform operations

    public static void main(String args[]) throws IOException {
        //initialising values
        groupColumns = new ArrayList<String>();
        groupColumnsIndex = new ArrayList<Integer>();
        csvData = new ArrayList<ArrayList<String>>();
        desiredColumnDataforAggregation = new ArrayList<ArrayList<String>>();
        operationArray = new ArrayList<ArrayList<ArrayList<String>>>();
        //if the arguments are given
        if (args.length > 0) {
            operation = args[0];//Operation is first argument
            aggregatedColumn = args[1];//To aggreagte the colun is the second one
            inputFile = args[2]; //Input file is third argument
//            System.out.println("Aggregated column is " + aggregatedColumn);
//            System.out.println("Operation is " + operation);

//            System.out.println(args.length);
            int columnCount = 0;
            while (columnCount + 3 < args.length) { //columnCount+3 as first one was operation and second one was the aggregated column
                try {
                    groupColumns.add(args[columnCount + 3]); //adding columns 
//                    System.out.println("Group columns are " + groupColumns.get(columnCount));
                } catch (Exception e) {
                    System.out.println("Null exception at " + columnCount + 3);
                }
                columnCount++;
            }
            readCSVFile();//A function to read a csv file
            writeCSVfile("outputfile.csv"); //A function to write the CSV file
            getDesiredColumns();//removing the extra columns
//            System.out.println(groupColumns);
            sort();//Sorting the arraylist
//            getDesiredColumnsAgain();
            System.out.println("Operation is " + operation);
            
            if (operation.equals("sum")) {
                sum();
            } else if (operation.equals("count")) {
                count();
            } else if (operation.equals("avg")) {
                avg();
            }

            for (ArrayList<String> a : desiredColumnDataforAggregation) {
                System.out.println(a + "\n");
            }
//            System.out.println(desiredColumnDataforAggregation);
//            Sort(desiredColumnDataforAggregation);
//            mergerSorthelper(1, desiredColumnDataforAggregation.size() - 1);
//            for (ArrayList<String> a : desiredColumnDataforAggregation) {
//                System.out.println(a);
//            }
//            System.out.println(desiredColumnDataforAggregation);
        }
    }

    public static int sum() {
        int sum = 0;
        System.out.println("Aggregated " + aggregatedColumnNumber);
        ArrayList<String> arr = desiredColumnDataforAggregation.get(1);
        String check = arr.get(0);
        ArrayList<ArrayList<String>> push = new ArrayList<ArrayList<String>>();
        for (int i = 1; i < desiredColumnDataforAggregation.size() - 1; i++) {
            ArrayList<String> arrTemp = desiredColumnDataforAggregation.get(i);
            String check1 = arrTemp.get(0);
            if (check.equals(check1)) {
//                System.out.println("Pushing "+arrTemp.get(0) );
                push.add(arrTemp);
            } else {
//                System.out.println("Push is "+push);
                operationArray.add(push);
                push.clear();
                check = arrTemp.get(0);
            }
            i++;
        }
//        for (ArrayList<ArrayList<String>> t : operationArray) {
//            System.out.println(t);
//        }
//        System.out.println("operatiubak array is "+operationArray);
        return sum;
    }

    public static int avg() {
        int avg = 0;
        ArrayList<String> arr = desiredColumnDataforAggregation.get(1);
        String check = arr.get(0);
        ArrayList<ArrayList<String>> push = new ArrayList<ArrayList<String>>();
        for (int i = 1; i < desiredColumnDataforAggregation.size() - 1; i++) {
            ArrayList<String> arrTemp = desiredColumnDataforAggregation.get(i);
            String check1 = arrTemp.get(0);
            if (check.equals(check1)) {
                push.add(arrTemp);
            } else {
                operationArray.add(push);
                push.clear();
                check = arrTemp.get(0);
            }
            i++;
        }
  
//        System.out.println("operatiubak array is "+operationArray);
        return avg/operationArray.size();
    }

    public static int count() {
        ArrayList<String> arr = desiredColumnDataforAggregation.get(1);
        String check = arr.get(0);
        ArrayList<ArrayList<String>> push = new ArrayList<ArrayList<String>>();
        for (int i = 1; i < desiredColumnDataforAggregation.size() - 1; i++) {
            ArrayList<String> arrTemp = desiredColumnDataforAggregation.get(i);
            String check1 = arrTemp.get(0);
            if (check.equals(check1)) {
//                System.out.println("Pushing "+arrTemp.get(0) );
                push.add(arrTemp);
            } else {
//                System.out.println("Push is "+push);
                operationArray.add(push);
                push.clear();
                check = arrTemp.get(0);
            }
            i++;
        }
 
        return operationArray.size();
    }

    public static void sort() {

//        ArrayList<String> temp = desiredColumnDataforAggregation.get(0);
//        groupColumns.clear();
//        groupColumnsIndex.clear();
//        for (int i = 0; i < temp.size(); i++) {
//            groupColumns.add(temp.get(i));
//            groupColumnsIndex.add(i);
//        }
//        groupColumns
//        System.out.println("Group column index are " + groupColumnsIndex);
//        System.out.println(desiredColumnDataforAggregation);
        for (int i = 1; i < desiredColumnDataforAggregation.size(); i++) {
            ArrayList<String> tempI = desiredColumnDataforAggregation.get(i);//getting the value of the 1st arraylist
            String iD = getStringOfRow(tempI);//COnverting object to String for comparison
            for (int j = i + 1; j < desiredColumnDataforAggregation.size(); j++) {
                ArrayList<String> tempJ = desiredColumnDataforAggregation.get(j);//getting the value of the inner arraylist
                String iD2 = getStringOfRow(tempJ); //Converting the arrayist to string vaue for comparison
                int check = iD.compareTo(iD2);//Comparing both string.
                if (check == 0) {//Means they are same and need to be moved
                    //replacing
                    ArrayList<String> tempSwap = desiredColumnDataforAggregation.get(i + 1);
                    desiredColumnDataforAggregation.set(i + 1, desiredColumnDataforAggregation.get(j));
                    desiredColumnDataforAggregation.set(j, tempSwap);
                    i++;
                    j++;
                }
            }
        }

    }

    //Reading CSV file
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

        System.out.println("The file is read from the " + inputFile);
        //outputing the csvData
        for (ArrayList t : csvData) {
            System.out.println(t + "\n");
        }

        System.out.println("\n***************************************************************\n\n");
    }

    //writin csv file
    public static void writeCSVfile(String fileName) throws IOException {
        PrintWriter pw = new PrintWriter(new File(fileName));

        for (ArrayList<String> row : csvData) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < row.size(); i++) {
                //As in csv the column are seperated by ,
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

        System.out.println("The data is write back to the " + fileName + "\n\n");
    }

    public static void getDesiredColumnsAgain() {
        System.out.println("Group columns" + groupColumns);
        System.out.println("Group index " + groupColumnsIndex);
    }

    //COnverting the line get from the csv file and converting it into arraylist after splitting.
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

    //Only get the desired column mentioned in the command line arguments
    public static void getDesiredColumns() {
        ArrayList<Integer> toKeep = new ArrayList<Integer>();//An array to check which column to keep
        ArrayList<String> columnnsName = new ArrayList<String>();//As first column of the array list has the column name
        columnnsName = csvData.get(0);//the first one has the names of the column

        int i = 0;
        for (String temp : columnnsName) {
//            System.out.println("Here " +groupColumns.contains(temp)+"  " + temp);

            if (groupColumns.contains(temp) || temp.equals(aggregatedColumn)) {
                toKeep.add(i);// this means that the column need to be added
                //Store only those which is not the aggrgatedcolumn
                if (!temp.equals(aggregatedColumn)) {
                    groupColumnsIndex.add(i);
                    aggregatedColumnNumber = i;
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
            for (i = 0; i < toKeep.size(); i++) {
//                if (toKeep.contains(i)) {
//                    tempColumn.add(rows.get(i));
//                }
//tempColumn.
                tempColumn.add(rows.get(toKeep.get(i)));
            }
            desiredColumnDataforAggregation.add(tempColumn);
        }
//        System.out.println(desiredColumnDataforAggregation);
//        System.out.println(csvData);
    }
    //
    //    public static void Sort() {
    //        ComparatorChain chain;
    //        chain = new ComparatorChain();
    //        mergeSort(desiredColumnDataforAggregation);
    //    }
    //
    //    public static void mergeSort(ArrayList<ArrayList<String>> input) {
    //        int size = input.size();
    //        if (size < 2) {
    //            return;
    //        }
    //        int mid = size / 2;
    //        int leftSize = mid;
    //        int rightSize = size - mid;
    //        ArrayList<ArrayList<String>> left = new ArrayList<>();
    //        ArrayList<ArrayList<String>> right = new ArrayList<>();
    //        for (int i = 0; i < leftSize; i++) {
    //            ArrayList<String> temp = new ArrayList<>();
    //            left.add(temp);
    //        }
    //        for (int i = 0; i < rightSize; i++) {
    //            ArrayList<String> temp = new ArrayList<>();
    //            right.add(temp);
    //        }
    ////        System.out.println("Left Size is " + left.size());
    ////        System.out.println("Right Size is " + right.size());
    //        for (int i = 0; i < mid; i++) {
    //            left.set(i, input.get(i));
    ////            left[i] = inputArray[i];
    //
    //        }
    //        for (int i = mid; i < size; i++) {
    //            right.set(i - mid, input.get(i));
    ////            right[i - mid] = inputArray[i];
    //        }
    ////        Collections.sort(desiredColumnDataforAggregation, new Comparator<>() {
    ////            @Override
    ////            public int compare(ArrayList<ArrayList<String>> o1, ArrayList<ArrayList<String>> o2) {
    ////                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    ////            }
    ////
    ////        });
    //        mergeSort(left);
    //        mergeSort(right);
    //        merge(left, right, input);
    //    }
    //
    //    public static void merge(ArrayList<ArrayList<String>> left, ArrayList<ArrayList<String>> right, ArrayList<ArrayList<String>> arr) {
    //
    //        int leftSize = left.size();
    //        int rightSize = right.size();
    //        int i = 0, j = 0, k = 0;
    //        while (i < leftSize && j < rightSize) {
    //            int check = getStringOfRow(left.get(i)).compareTo(getStringOfRow(right.get(j)));
    ////            System.out.println(getStringOfRow(left.get(i)) + "   and   " + getStringOfRow(right.get(j)) + "  Check is " + check);
    //            if (check <= 0) {
    ////                arr.set(k, left.get(i));
    //                i++;
    //                k++;
    //            } else {
    ////                arr.set(k, right.get(j));
    //                k++;
    //                j++;
    //            }
    //        }
    //        while (i < leftSize) {
    ////            arr.set(k, left.get(i));
    //            k++;
    //            i++;
    //        }
    //        while (j < leftSize) {
    ////            arr.set(k, left.get(j));
    //            k++;
    //            j++;
    //        }
    //    }

    //****************************
    //    public static void mergerSort() {
    //    }
    //
    //    public static void mergerSorthelper(int low, int high) {
    ////        System.out.println("Called");
    //        if (low < high) {
    //            int middle = low + (high - low) / 2;
    //            mergerSorthelper(low, middle);
    //            mergerSorthelper(middle + 1, high);
    //            merge(low, middle, high);
    //        }
    //    }
    //
    ////    number is the orignal
    ////    helper is of the sam size
    //    public static void merge(int low, int middle, int high) {
    //        for (int i = low; i <= high; i++) {
    ////            System.out.println("SET");
    //            helper.set(i, desiredColumnDataforAggregation.get(i));
    //        }
    //        int i = low;
    //        int j = middle + 1;
    //        int k = low;
    //
    //        while (i <= middle && j <= high) {
    //            int check = getStringOfRow(helper.get(i)).compareTo(getStringOfRow(helper.get(j)));
    //            System.out.println(getStringOfRow(helper.get(i)) + "   and   " + getStringOfRow(helper.get(j)) + "  Check is " + check);
    //            if (check <= 0) {
    //                desiredColumnDataforAggregation.set(k, helper.get(i));
    //                i++;
    //            } else {
    //                desiredColumnDataforAggregation.set(k, helper.get(j));
    //                j++;
    //            }
    //        }
    //
    //        while (i <= middle) {
    //            desiredColumnDataforAggregation.set(k, helper.get(i));
    //            k++;
    //            i++;
    //        }
    //    }
    //getting the string form of the arraylist to compare
    public static String getStringOfRow(ArrayList<String> arrtoString) {
        StringBuilder str = new StringBuilder();
        for (int s : groupColumnsIndex) {
//            if(s!=aggregatedColumnNumber)
            str.append(arrtoString.get(s));
        }
        return str.toString();
    }
}
