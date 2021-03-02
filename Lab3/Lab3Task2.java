package Lab3;

import java.util.ArrayList;
import java.util.Arrays;

import Lab3.Lab3Task1;

public class Lab3Task2 {

    /**
     * @param dir
     * 1: left
     * 2: right
     * 3: up
     * 4: down
     */
    public static int[][] runningSum2DArray(int[][] array, int dir) {
        
        int[][] modArray = new int[array.length][array[0].length];
        for(int i = 0; i < array.length; i++) {
            for(int j = 0; j < array[i].length; j++) {
                modArray[i][j] = array[i][j];
            }
        }

        switch (dir) {
            case 1: // left
                for(int r = 0; r < modArray.length; r++) {
                    int sum = 0;
                    for(int c = modArray[r].length - 1; c >= 0; c--) {
                        modArray[r][c] += sum;
                        sum = modArray[r][c];
                    }
                }
                return modArray;
                
            case 2: // right
                for(int r = 0; r < modArray.length; r++) {
                    int sum = 0;
                    for(int c = 0; c < modArray[r].length; c++) {
                        modArray[r][c] += sum;
                        sum = modArray[r][c];
                    }
                }
                return modArray;

            case 3: // up
                for(int c = 0; c < modArray[0].length; c++) {
                    int sum = 0;
                    for(int r = modArray.length - 1; r >= 0; r--) {
                        modArray[r][c] += sum;
                        sum = modArray[r][c];
                    }
                }
                return modArray;

            case 4: // down
                for(int c = 0; c < modArray[0].length; c++) {
                    int sum = 0;
                    for(int r = 0; r < modArray.length; r++) {
                        modArray[r][c] += sum;
                        sum = modArray[r][c];
                    }
                }
                return modArray;

            default:
                return array;
        }
    }

    /**
     * @param dir
     * 1: left
     * 2: right
     * 3: up
     * 4: down
     */
    public static ArrayList<ArrayList<Integer>> running2DArrayList(ArrayList<ArrayList<Integer>> list, int dir) {
        ArrayList<ArrayList<Integer>> modList = new ArrayList<ArrayList<Integer>>();
        modList.add(new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0)));
        modList.add(new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0)));
        modList.add(new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0)));
        modList.add(new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0)));

        switch (dir) {
            case 1: // left
                for(int r = 0; r < modList.size(); r++) {
                    int sum = 0;
                    for(int c = modList.get(0).size() - 1; c >= 0; c--) {
                        modList.get(r).set(c, list.get(r).get(c) + sum);
                        sum = modList.get(r).get(c);
                    }
                }
                return modList;

            case 2: // right
                for(int r = 0; r < modList.size(); r++) {
                    int sum = 0;
                    for(int c = 0; c < modList.get(r).size(); c++) {
                        modList.get(r).set(c, list.get(r).get(c) + sum);
                        sum = modList.get(r).get(c);
                    }
                }
                return modList;

            case 3: // up
                for(int c = 0; c < modList.get(0).size(); c++) {
                    int sum = 0;
                    for(int r = modList.size() - 1; r >= 0; r--) {
                        modList.get(r).set(c, list.get(r).get(c) + sum);
                        sum = modList.get(r).get(c);
                    }
                }
                return modList;

            case 4: // down
                for(int c = 0; c < modList.get(0).size(); c++) {
                    int sum = 0;
                    for(int r = 0; r < modList.size(); r++) {
                        modList.get(r).set(c, list.get(r).get(c) + sum);
                        sum = modList.get(r).get(c);
                    }
                }
                return modList;
                
            default:
                return list;
        }
    }

    public static void main(String[] args) {
        int[][] array = {{10, 15, 30, 40},{15, 5, 8, 2}, {20, 2, 4, 2},{1, 4, 5, 0}};
        ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();
        list.add(new ArrayList<Integer>(Arrays.asList(10, 15, 30, 40)));
        list.add(new ArrayList<Integer>(Arrays.asList(15, 5, 8, 2)));
        list.add(new ArrayList<Integer>(Arrays.asList(20, 2, 4, 2)));
        list.add(new ArrayList<Integer>(Arrays.asList(1, 4, 5, 0)));
        
        System.out.println("Running Sum Array Left: ");
        Lab3Task1.print2Darray(runningSum2DArray(array, 1));
        System.out.println("Running Sum Array Right: ");
        Lab3Task1.print2Darray(runningSum2DArray(array, 2));
        System.out.println("Running Sum Array Up");
        Lab3Task1.print2Darray(runningSum2DArray(array, 3));
        System.out.println("Running Sum Array Down");
        Lab3Task1.print2Darray(runningSum2DArray(array, 4));

        System.out.println();

        System.out.println("Running Sum List Left: ");
        Lab3Task1.print2DList(running2DArrayList(list, 1));
        System.out.println("Running Sum List Right: ");
        Lab3Task1.print2DList(running2DArrayList(list, 2));
        System.out.println("Running Sum List Up: ");
        Lab3Task1.print2DList(running2DArrayList(list, 3));
        System.out.println("Running Sum List Down: ");
        Lab3Task1.print2DList(running2DArrayList(list, 4));
    }
}