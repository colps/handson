package com.tw.battleship.game;

import com.tw.battleship.game.board.BattleArea;
import com.tw.battleship.game.piece.BattleShip;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class InputFileReader {

    public static void main(String[] args) throws IOException, URISyntaxException {
        URL f = InputFileReader.class.getClassLoader().getResource("game.txt");
        List<String> lines = Files.readAllLines(Paths.get(f.toURI()));
        Game g = initGame(lines);
        g.start();
    }

    private static Game initGame(List<String> lines){
        int currLine = 0;
        BattleArea areaA = initBattleArea(lines.get(currLine));
        BattleArea areaB = initBattleArea(lines.get(currLine));
        currLine ++;
        int shipCount = shipCount(lines.get(currLine));
        currLine ++;
        for(int i = 1; i <= shipCount; i++){
            String line = lines.get(currLine);
            initShip(line, areaA, 3);
            initShip(line, areaB, 4);
            currLine++;
        }
        Player playerA = new Player("Player-1", areaA, getTargets(lines.get(currLine)));
        currLine++;
        Player playerB = new Player("Player-2", areaB, getTargets(lines.get(currLine)));
        return new Game(playerA, playerB);
    }

    private static BattleArea initBattleArea(String line){
        String[] input = line.split("\\s+");
        return new BattleArea(Integer.valueOf(input[0]), input[1].charAt(0));
    }

    private static int shipCount(String line){
        return Integer.valueOf(line.trim());
    }

    private static BattleShip initShip(String line, BattleArea area, int positionIndex){
        String[] input = line.split("\\s+");
        BattleShip ship = new BattleShip(Integer.valueOf(input[1]), Integer.valueOf(input[2]),
                BattleShip.Type.valueOf(input[0]));
        area.deploy(ship, input[positionIndex]);
        return ship;
    }

    private static String[] getTargets(String line){
        return line.split("\\s+");
    }

}
