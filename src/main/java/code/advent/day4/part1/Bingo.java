package code.advent.day4.part1;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class Bingo {

    List<Board> boards = new ArrayList<>();
    List<Integer> numbersToCall;
    List<Integer> winningBoardScore;

    public Bingo(String inputFilename) {
        File source = getFileSource(inputFilename);
        try (Scanner scanner = new Scanner(source)) {

            numbersToCall = loadNumbersToCall(scanner);

            loadBingoBoards(scanner);

            System.out.println("Lets Play Bingo!");

            winningBoardScore = callNumbersUntilBingoIsCalled();

            System.out.println("Winning board score: " + winningBoardScore.toString());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private File getFileSource(String inputFilename) {
        URL resource = this.getClass().getResource(inputFilename);
        try {
            return new File(resource.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Integer> loadNumbersToCall(Scanner scanner) {
        String line = scanner.nextLine();
        List<Integer> numbers = Arrays.stream(line.split(",")).map(Integer::valueOf).collect(Collectors.toList());
        scanner.nextLine();
        return numbers;
    }

    private void loadBingoBoards(Scanner scanner) {
        int boardId = 0;
        Board board = new Board(boardId++);
        String line;
        while (scanner.hasNext()) {
            line = scanner.nextLine().trim();
            if (line.equals("")) {
                boards.add(board);
                board = new Board(boardId++);
            } else {
                board.addRow(line);
            }
        }

        boards.add(board);
    }

    private List<Integer> callNumbersUntilBingoIsCalled() {
        return numbersToCall.stream()
                        .map(this::callNumber)
                        .filter(i -> !i.isEmpty())
                        .findFirst().get();
    }

    private List<Integer> callNumber(Integer i) {
        System.out.println("number " + i + " called");
        List<Integer> bingoBoards = boards.stream().map(board -> {
            boolean bingo = board.numberCalled(i);
            if (bingo) {
                return calculateBingoScore(board, i);
            } else {
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
        return bingoBoards;
    }

    private Integer calculateBingoScore(Board board, Integer i) {
        List<Integer> uncalledNumbers = board.getUncalledNumbers();
        Integer sumOfUncalledNumbers = uncalledNumbers.stream().reduce(Integer::sum).get();
        return sumOfUncalledNumbers * i;
    }

    public List<Integer> getWinningScore() {
        return winningBoardScore;
    }
}
