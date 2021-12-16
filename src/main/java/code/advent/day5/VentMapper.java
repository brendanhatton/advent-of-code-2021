package code.advent.day5;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class VentMapper {

    int[][] ventMap;
    List<Line> lines = new ArrayList<>();

    public VentMapper(String inputFilename) throws FileNotFoundException {
        int maxX = 0;
        int maxY = 0;
        File source = getFileSource(inputFilename);
        try (Scanner scanner = new Scanner(source)) {
            while (scanner.hasNext()) {
                String inputLine = scanner.nextLine();
                Line line = new Line(inputLine);
                lines.add(line);
                if (line.getXstart() > maxX) {
                    maxX = line.getXstart();
                }
                if (line.getXend() > maxX) {
                    maxX = line.getXend();
                }
                if (line.getYstart() > maxY) {
                    maxY = line.getYstart();
                }
                if (line.getYend() > maxY) {
                    maxY = line.getYend();
                }
            }
            ventMap = new int[maxX + 1][maxY + 1];

        }
    }

    public int[][] markStraightLinesOnVentMap() {
        lines.stream().filter(Line::isStraight).forEach(line -> {
            System.out.println("Mapping line " + line);
            if (line.isHorizontal()) {
                int lower = line.getXstart() < line.getXend() ? line.getXstart() : line.getXend();
                int higher = line.getXstart() < line.getXend() ? line.getXend() : line.getXstart();
                for (int i = lower; i <= higher; i++) {
                    System.out.println("incrementing [" + i + "][" + line.getYstart() + "]");
                    ventMap[i][line.getYstart()]++;
                }

            } else {
                int lower = line.getYstart() < line.getYend() ? line.getYstart() : line.getYend();
                int higher = line.getYstart() < line.getYend() ? line.getYend() : line.getYstart();
                for (int i = lower; i <= higher; i++) {
                    System.out.println("incrementing [" + line.getXstart() + "][" + i + "]");
                    ventMap[line.getXstart()][i]++;
                }

            }

        });
        return ventMap;
    }

    public int[][] markDiagonalLinesOnVentMap() {
        lines.stream().filter(Line::isDiagonal).forEach(line -> {
            System.out.println("Mapping diagonal line " + line);
            int xAxisLength = line.getXend() - line.getXstart();
            int xAxisIncrementor = xAxisLength / Math.abs(xAxisLength);
            int yAxisLength = line.getYend() - line.getYstart();
            int yAxisIncrementor = yAxisLength / Math.abs(yAxisLength);
            for (int i = 0; i <= Math.abs(xAxisLength); i++) {
                System.out.println("incrementing [" + (line.getXstart() + i * xAxisIncrementor) + "][" + (line.getYstart() + i * yAxisIncrementor) + "]");
                ventMap[line.getXstart() + i * xAxisIncrementor][line.getYstart() + i * yAxisIncrementor]++;
            }
        });
        return ventMap;
    }

    public Long countDangerZonesMarkedOnMap() {
        return Arrays.stream(ventMap)
                .map(row ->
                        Arrays.stream(row)
                                .filter(i -> i > 1)
                                .count())
                .reduce(Long::sum).get();
    }


    public int[][] getVentMap() {
        return ventMap;
    }

    private File getFileSource(String inputFilename) {
        URL resource = this.getClass().getResource(inputFilename);
        try {
            return new File(resource.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Line> getLines() {
        return lines;
    }
}
