package code.advent.day4.part1;

import java.util.*;
import java.util.stream.Collectors;

public class Board {

    private int boardId;
    private List<List<Integer>> rows = new ArrayList<>();
    private List<Integer> markedRowCounter = new ArrayList<>();
    private List<Integer> markedColumnCounter = new ArrayList<>();
    private List<Integer> calledNumbers = new ArrayList<>();
    private int countNeededForBingo = rows.size();

    public Board(int boardId) {
        this.boardId = boardId;
    }


    public void addRow(String line) {
        List<Integer> row = Arrays.stream(line.split("\\s+"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        rows.add(row);
        countNeededForBingo = rows.size();
        markedRowCounter.add(0);
        markedColumnCounter.add(0);
    }

    public boolean numberCalled(Integer calledNumber) {
        calledNumbers.add(calledNumber);
        return rows.stream().map(row -> {
            boolean bingo = false;
            for (int i = 0; i < row.size(); i++) {
                if (calledNumber.equals(row.get(i))) {
                    int index = rows.indexOf(row);
                    Integer rowCounter = markNumberForRow(index);

                    Integer columnCounter = markCounterForColumn(i);

                    bingo = rowCounter >= countNeededForBingo || columnCounter >= countNeededForBingo;
                    if (bingo) {
                        System.out.println("BINGO");
                        System.out.println("Winning board: " + this.toString());
                    }
                }
            }
            return bingo;
        }).anyMatch(bingo -> bingo.equals(true));

    }

    private Integer markCounterForColumn(int i) {
        Integer columnCounter = markedColumnCounter.get(i);
        columnCounter++;
        markedColumnCounter.set(i, columnCounter);
        return columnCounter;
    }

    private Integer markNumberForRow(int index) {
        Integer rowCounter = markedRowCounter.get(index);
        rowCounter++;
        markedRowCounter.set(index, rowCounter);
        return rowCounter;
    }

    public List<Integer> getUncalledNumbers() {
        return rows.stream().flatMap(Collection::stream)
                .filter(i -> !calledNumbers.contains(i))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Board{" +
                "boardId=" + boardId +
                ", rows=" + rows.stream().map(Objects::toString).collect(Collectors.joining(", ")) +
                ", markedRowCounter=" + markedRowCounter +
                ", markedColumnCounter=" + markedColumnCounter +
                ", calledNumbers=" + calledNumbers +
                '}';
    }
}
