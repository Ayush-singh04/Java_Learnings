package java_learnings.BackTracking;

import java.util.ArrayList;
import java.util.List;

public class N_Oueens {
    /* Make a 4x4 chess board where 4 Queens are placed, Find the no. of ways that all are safe from 
     * each other--- 
     */
     static boolean isSafe(int row, int col, char[][] board) {
       //horizontal
       for(int j=0; j<board.length; j++) {
           if(board[row][j] == 'Q') {
               return false;
           }
       }
      
       //vertical
       for(int i=0; i<board.length; i++) {
           if(board[i][col] == 'Q') {
               return false;
           }
       }
      
       //upper left
       int r = row;
       for(int c=col; c>=0 && r>=0; c--, r--) {
           if(board[r][c] == 'Q') {
               return false;
           }
       }
      
       //upper right
       r = row;
       for(int c=col; c<board.length && r>=0; r--, c++) {
           if(board[r][c] == 'Q') {
               return false;
           }
       }
      
       //lower left
       r = row;
       for(int c=col; c>=0 && r<board.length; r++, c--) {
           if(board[r][c] == 'Q') {
               return false;
           }
       }
      
       //lower right
       for(int c=col; c<board.length && r<board.length; c++, r++) {
           if(board[r][c] == 'Q') {
               return false;
           }
       }
      
       return true;
   }
  
   static void saveBoard(char[][] board, List<List<String>> allBoards) {
       String row = "";
       List<String> newBoard = new ArrayList<>();
      
       for(int i=0; i<board.length; i++) {
           row = "";
           for(int j=0; j<board[0].length; j++) {
               if(board[i][j] == 'Q')
                   row += 'Q';
               else
                   row += '.';
           }
           newBoard.add(row);
       }
      
       allBoards.add(newBoard);
   }
  
   static void helper(char[][] board, List<List<String>> allBoards, int col) {
       if(col == board.length) {
           saveBoard(board, allBoards);
           return;
       }
      
       for(int row=0; row<board.length; row++) {
           if(isSafe(row, col, board)) {
               board[row][col] = 'Q';
               helper(board, allBoards, col+1);
               board[row][col] = '.';
           }
       }
   }
  
   static List<List<String>> solveNQueens(int n) {
       List<List<String>> allBoards = new ArrayList<>();
       char[][] board = new char[n][n];
      
       helper(board, allBoards, 0);
       return allBoards;
   }
public static void main(String[] args) {
    System.out.println(solveNQueens(4));
  }
   
}
