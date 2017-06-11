
public class Aggregate {

    static String operation = "";
    static String aggregatedColumn = "";
    static String inputFile = "";
    static String[] groupColumns;

    public static void main(String args[]) {
        int columnCount = 0;
        groupColumns = new String[]{"", "", "", "", "", "", "", ""};
        if (args.length > 0) {
            operation = args[0];
            aggregatedColumn = args[1];
            inputFile = args[2];
            System.out.println("Aggregated column is " + aggregatedColumn);
            System.out.println("Operation is " + operation);

            System.out.println(args.length);
            while (columnCount + 3 < args.length) {
                try {
                    groupColumns[columnCount] = args[columnCount + 3];
                    System.out.println("Group columns are " + groupColumns[columnCount]);
                } catch (Exception e) {
                    System.out.println("Null exception at " + columnCount + 3);
                }
                columnCount++;
            }
        }
    }

}
