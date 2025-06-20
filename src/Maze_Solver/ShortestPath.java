package Maze_Solver;

import java.util.*;

public class ShortestPath {

    public static void main(String[] args) {
        Scanner in  = new Scanner(System.in);
        System.out.print("Enter the matrix dim : " );

        int n = in.nextInt();

        char[][] matrix = new char[n][n];

        for (char[] arr : matrix){
            Arrays.fill(arr, '0');
        }
        System.out.print("Enter the adventure position : ");

        int aRow = in.nextInt();
        int aCol = in.nextInt();

        matrix[aRow][aCol] = 'A';

        System.out.print("Enter the Desitinatior position :  ");
        int dRow = in.nextInt();
        int dCol = in.nextInt();

        matrix[dRow][dCol] = 'D';
        for(int i = 0; i<n; i++){
            for(int j = 0; j<n; j++){
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
        int path = findShortestPath(aRow, aCol, dRow, dCol);
        System.out.print("the Shortest path is : " + path);

    }

    public static int findShortestPath(int aRow , int aCol, int dRow , int dCol){
        return Math.max(Math.abs(dRow - aRow), Math.abs(dCol - aCol));
    }

}
