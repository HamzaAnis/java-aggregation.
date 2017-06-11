
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.util.Comparator;

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
            System.out.println(groupColumns);
            sort();
            getDesiredColumnsAgain();

            if (operation.equals("add")) {
                System.out.println("Need to do avg");
            } else if (operation.equals("count")) {
                System.out.println("Need to do avg");
            } else if (operation.equals("avg")) {
                System.out.println("Need to do avg");
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

    public static void sort() {
        System.out.println("Group column index are " + groupColumnsIndex);
        System.out.println(desiredColumnDataforAggregation);
        for (int i = 1; i < desiredColumnDataforAggregation.size(); i++) {
            ArrayList<String> tempI = desiredColumnDataforAggregation.get(i);
//            String iD = tempI.get(0);
            String iD = getStringOfRow(tempI);
            for (int j = i + 1; j < desiredColumnDataforAggregation.size(); j++) {
                ArrayList<String> tempJ = desiredColumnDataforAggregation.get(j);
//                String iD2 = tempJ.get(0);
                String iD2 = getStringOfRow(tempJ);
                int check = iD.compareTo(iD2);
//                System.out.println(iD + "  " + iD2 + " " + check);
                if (check == 0) {
                    ArrayList<String> tempSwap = desiredColumnDataforAggregation.get(i + 1);
                    desiredColumnDataforAggregation.set(i + 1, desiredColumnDataforAggregation.get(j));
                    desiredColumnDataforAggregation.set(j, tempSwap);
                    i++;
                    j++;
                }
            }
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

    public static void getDesiredColumnsAgain() {
        System.out.println("Group columns"+ groupColumns);
        System.out.println("Group index "+groupColumnsIndex);    }

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
    public static String getStringOfRow(ArrayList<String> arrtoString) {
        StringBuilder str = new StringBuilder();
        for (int s : groupColumnsIndex) {
            str.append(arrtoString.get(s));
        }
        return str.toString();
    }
}
