package code.advent.day5.part1;

import java.util.Arrays;

public class Line {


    private final int xStart;
    private final int yStart;
    private final int xEnd;
    private final int yEnd;

    //x1,y1 -> x2,y2
    //5,5 -> 8,2
    public Line(String inputLine) {
        String[] parts = inputLine.split("->");
        int[] startingPoints = Arrays.stream(parts[0].trim().split(",")).mapToInt(Integer::parseInt).toArray();
        xStart = startingPoints[0];
        yStart = startingPoints[1];
        int[] endingPoints = Arrays.stream(parts[1].trim().split(",")).mapToInt(Integer::parseInt).toArray();
        xEnd = endingPoints[0];
        yEnd = endingPoints[1];

    }

    public int getXstart() {
        return xStart;
    }

    public int getYstart() {
        return yStart;
    }

    public int getXend() {
        return xEnd;
    }

    public int getYend() {
        return yEnd;
    }

    public boolean isStraight() {
        return xStart == xEnd || yStart == yEnd;
    }

    @Override
    public String toString() {
        return "Line{" +
                "xStart=" + xStart +
                ", yStart=" + yStart +
                ", xEnd=" + xEnd +
                ", yEnd=" + yEnd +
                '}';
    }

    public boolean isHorizontal() {
        return yStart == yEnd;
    }
}
