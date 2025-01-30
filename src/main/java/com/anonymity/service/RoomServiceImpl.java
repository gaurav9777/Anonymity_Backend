package com.anonymity.service;

import com.anonymity.entity.Room;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RoomServiceImpl {
    private final Map<String, Room> rooms = new ConcurrentHashMap<>();

    public String createRoom() {
        String code;
        do {
            code = generateRandomCode();
        } while (rooms.containsKey(code));
        rooms.put(code, new Room(code));
        return code;
    }

    public Room joinRoom(String code) {
        return rooms.get(code);
    }

    private String generateRandomCode() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }
}


/*

package codeforces;

import java.util.*;

import static java.lang.System.exit;

public class D {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Game!!!");
        System.out.println("You have 3 change to start the game.");
        System.out.println("To start the game type (s) and to quit type (q) : ");
        int openCloseGame = 1;
        String winner = "";
        winner = gameLoop(openCloseGame,winner);
        if(winner.equals("Not started")){
            System.out.println("Game not started!!!");
        }else if(winner.equals("Both Lost")){
            System.out.println("Both Player Lost No One wins, to restart again type s or to quite type q");
            gameLoop(1,"");
        }else if(winner.equals("A")){
            System.out.println("Player A wins the game!");
        }else{
            System.out.println("Player B wins the game!");
        }
        exit(1);
    }

    private static String gameLoop(int openCloseGame, String winner) {
        Scanner sc = new Scanner(System.in);
        while (openCloseGame<=3){
            String gameState = sc.next();
            switch (gameState) {
                case "s":
                    System.out.println("Provide the dimension for which you want to play : ");
                    int dimension = sc.nextInt();
                    winner = startGame(dimension);
                    if(winner.equals("Both Lost")){
                        System.out.println("Both Player Lost No One wins, to restart again type s or to quite type q :");
                        gameLoop(1,"");
                    }else if(winner.equals("A")){
                        return "A";
                    }else{
                        return "B";
                    }
                case "q":
                    endGame();
                default:
                    System.out.println("Please type 's' to start and 'q' to quit");
                    System.out.println("remaining attempt to start game "+(3-openCloseGame));
                    openCloseGame += 1;
                    if(openCloseGame==4){
                        return "Not started";
                    }
            }
        }
        return "";
    }

    private static void endGame() {
        System.out.println("Thanks for playing!");
        exit(1);
    }

    private static String startGame(int dimension) {
        Scanner sc = new Scanner(System.in);
        int totalState = dimension*dimension;
        String gamePosition [][]= new String[dimension][dimension];
        for(int row = 0; row<dimension ;row++){
            Arrays.fill(gamePosition[row],"-");
        }
        System.out.println("Game floor looks like : ");
        printCurrentState(gamePosition);
        boolean playersTurn = true;
        boolean doWeHaveWinner = false;
        String finalWinner = "Both Lost";
        while (totalState > 0 && !doWeHaveWinner){
            if(playersTurn){
                System.out.println("Player A to play : enter position : ");
                playersTurn = false;
                int coOrdinatePosition = sc.nextInt();
                int status = placeAtPosition(gamePosition,coOrdinatePosition-1,dimension,true);
                if(status == -1){
                    playersTurn = true;
                    totalState += 1;
                    System.out.println("already that position is filled please check the game status\n and place provide the untouched position");
                }
                doWeHaveWinner = checkForWinner(gamePosition);
                finalWinner = doWeHaveWinner ? "A" : "Both Lost";
            }else{
                System.out.println("Player B to play : enter position : ");
                playersTurn = true;
                int coOrdinatePosition = sc.nextInt();
                int status = placeAtPosition(gamePosition,coOrdinatePosition-1,dimension,false);
                if(status == -1){
                    playersTurn = true;
                    totalState += 1;
                    System.out.println("already that position is filled please check the game status\n and place provide the untouched position");
                }
                doWeHaveWinner = checkForWinner(gamePosition);
                finalWinner = doWeHaveWinner ? "B" : "Both Lost";
            }
            totalState -= 1;
        }
        return finalWinner;
    }

    private static int placeAtPosition(String[][] gamePosition, int coOrdinatePosition,int dimension,boolean playerAToPlay) {
        int rowPosition = (coOrdinatePosition/dimension);
        int colPosition = (coOrdinatePosition%dimension);
        if(gamePosition[rowPosition][colPosition].equals("X") || gamePosition[rowPosition][colPosition].equals("O")){
            return -1;
        }
        gamePosition[rowPosition][colPosition] = playerAToPlay == true ?  "X" : "O";
        printCurrentState(gamePosition);
        return 1;
    }

    private static void printCurrentState(String[][] gamePosition) {
        for(int row=0;row<gamePosition.length;row++){
            for(int col=0;col<gamePosition[0].length;col++){
                System.out.print(gamePosition[row][col]+"  ");
            }
            System.out.println();
        }
    }
    private static boolean checkForWinner(String[][] gamePosition) {
        //check for row wise winner
        for(int row=0;row<gamePosition.length;row++){
            int xCount = 0;
            int oCount = 0;
            for(int col=0;col<gamePosition[0].length;col++){
                if(gamePosition[row][col].equals("X")){
                    xCount++;
                }else if(gamePosition[row][col].equals("O")){
                    oCount++;
                }
            }
            if(xCount == gamePosition.length || oCount == gamePosition.length){
                return true;
            }
        }
        ////check for col wise winner
        for(int col=0;col<gamePosition[0].length;col++){
            int xCount = 0;
            int oCount = 0;
            for(int row=0;row<gamePosition.length;row++){
                if(gamePosition[row][col].equals("X")){
                    xCount++;
                }else if(gamePosition[row][col].equals("O")){
                    oCount++;
                }
            }
            if(xCount == gamePosition.length || oCount == gamePosition.length){
                return true;
            }
        }

        //check for positive diagonal winner
        int xCount = 0;
        int oCount = 0;
        for(int col=0;col<gamePosition[0].length;col++){
            for(int row=0;row<gamePosition.length;row++){
                if(row == col){
                    if (gamePosition[col][row].equals("X")) {
                        xCount++;
                    } else if (gamePosition[col][row].equals("O")) {
                        oCount++;
                    }
                }
            }
        }
        if(xCount == gamePosition.length || oCount == gamePosition.length){
            return true;
        }

        // check for reverse diagonal winner
        xCount = 0;
        oCount = 0;
        for(int col=0;col<gamePosition[0].length;col++){
            for(int row=0;row<gamePosition.length;row++){
                if((row+col+1) == gamePosition.length){
                    if (gamePosition[col][row].equals("X")) {
                        xCount++;
                    } else if (gamePosition[col][row].equals("O")) {
                        oCount++;
                    }
                }
            }
        }
        if(xCount == gamePosition.length || oCount == gamePosition.length){
            return true;
        }
        return false;
    }

}





*/
