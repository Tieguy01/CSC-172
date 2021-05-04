/**
 * @author Ethan Shahan
 */

package Lab6;

public class DynamicLab6 {

    static int calls;
    static int[][] table; // table created to dynamicaly keep track of change made for amounts that have already been checked

    public static int[] makeChange(int amount, int[] denominations) {
        table = new int[amount + 1][];
        int[] counts = new int[denominations.length + 1];
        recMakeChange(amount, denominations, counts);
        int[] change = new int[counts[counts.length - 1]];
        int k = 0;
        for (int i = 0; i < counts.length - 1; i++) {
            for (int j = 0; j < counts[i]; j++) {
                change[k++] = denominations[i];
            }
        }
        return change;
    }

    public static boolean recMakeChange(int amount, int[] denominations, int[] counts) {
        calls++;
        if (amount == 0) {
            return true;
        } else if (amount < 0) {
            return false;
        } else {

            // if change for an amount has already been stored in the table, recursuve calls to find that amount no longer need to be made
            if (table[amount] != null) {
                for (int i = 0; i < table[amount].length; i++) {
                    counts[i] = table[amount][i];
                }
                return true;
            }

            boolean rValue = false;
            int[] temp = new int[counts.length];
            int[] best = new int[counts.length];
            best[best.length - 1] = amount + 1;
            for (int i = 0; i < denominations.length; i++) {
                for (int j = 0; j < temp.length; j++) {
                    temp[j] = 0;
                }
                if (recMakeChange(amount - denominations[i], denominations, temp)) {
                    if (temp[temp.length - 1] < best[best.length - 1]) {
                        temp[i]++;
                        temp[temp.length - 1]++;
                        for (int j = 1; j < best.length; j++) {
                            best[j] = temp[j];
                        }
                    }
                    rValue = true;
                }
            }
            if (rValue) {
                // anytime change for an amount is found, it is stored in the table so that it will not need to be found again in the future
                table[amount] = new int[counts.length];
                for (int i = 1; i < best.length; i++) {
                    table[amount][i] = counts[i] = best[i];
                }
            } 
            return rValue;
        }
    }
    public static void main(String[] args) {
        int[] denominations = {100000, 5000, 2000, 1000, 500, 100, 25, 10, 5, 1};
        int amount = 63;
        if (args.length == 1) amount = Integer.parseInt(args[0]);

        calls = 0;
        int[] change = makeChange(amount, denominations);
        System.out.print("Change for " + amount + " is {");
        for (int i = 0; i < change.length - 1; i++) {
            System.out.print(change[i] + ", ");
        }
        System.out.print(change[change.length - 1] + "}\n");
        System.out.println("calls: " + calls);
    }
}