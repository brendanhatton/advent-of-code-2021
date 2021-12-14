package code.advent.day4.part1;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class Bingo {

    List<Board> boards = new ArrayList<>();
    String[] numbersToCall;

    public Bingo(String inputFilename) {
        URL resource = this.getClass().getResource(inputFilename);
//        System.out.println(resource);

        try (Scanner scanner = new Scanner(new File(resource.toURI()))) {

            String line = scanner.nextLine();
//            System.out.println(line);
            numbersToCall = line.split(",");
            scanner.nextLine();
            Board board = new Board();
            while (scanner.hasNext()) {
                line = scanner.nextLine().trim();
//                System.out.println(line);
                if (line.equals("")) {
                    boards.add(board);
                    board = new Board();
                } else {
                    board.addRow(line);
                }
            }

            boards.add(board);

            System.out.println("\nLets Play Bingo!");


            Optional<List<Board>> winningBoard = Arrays.stream(numbersToCall).map(this::callNumber).filter(i -> !i.isEmpty()).findFirst();

//            for (String i: numbersToCall) {
//                callNumber(i);
//            }
            System.out.println("Winning board: " + winningBoard.toString());



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Board> callNumber(String i) {
        List<Board> bingoBoards = boards.stream().map(board -> {
            System.out.println("number " + i + " called");
            boolean bingo = board.numberCalled(i);
            if (bingo) {
                return board;
            } else {
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
        return bingoBoards;
    }
}
