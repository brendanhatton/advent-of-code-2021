package code.advent.day4.part1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Board {

    List<String[]> rows = new ArrayList<>();
    List<Integer> markedRowCounter = new ArrayList<>();
    List<Integer> markedColumnCounter = new ArrayList<>();

    public void addRow(String line) {
//            List<Integer> row = Arrays.stream(s.split(" ")).map(Integer::parseInt).collect(Collectors.toList());
//            rows.add(row);
        rows.add(line.split("\\s+"));
        markedRowCounter.add(0);
        markedColumnCounter.add(0);
    }

    public boolean numberCalled(String calledNumber) {
        System.out.println("number called " + calledNumber);
        return rows.stream().map(row -> {
            boolean bingo = false;
            for (int i = 0; i < row.length; i++) {
                if (calledNumber.equals(row[i])) {
                    System.out.println("We matched number " + calledNumber);
                    int index = rows.indexOf(row);
                    Integer rowCounter = markedRowCounter.get(index);
                    rowCounter++;
                    markedRowCounter.set(index, rowCounter);

                    Integer columnCounter = markedColumnCounter.get(i);
                    columnCounter++;
                    markedColumnCounter.set(i, columnCounter);

                    System.out.println("row counter: " + markedRowCounter);
                    System.out.println("column counter: " + markedColumnCounter);
                    int countNeededForBingo = rows.size();

                    System.out.println(rowCounter + " " + countNeededForBingo + " " + columnCounter + " " + rows.size());

                    bingo = rowCounter >= countNeededForBingo || columnCounter >= rows.size();
                    if (bingo) {
                        System.out.println("BINGO");
                    }
                }
            }
            return bingo;
        }).anyMatch(x -> x.equals(true));

    }

}
