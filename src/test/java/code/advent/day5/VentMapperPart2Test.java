package code.advent.day5;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class VentMapperPart2Test {

    @Test
    void shouldMarkStraightAndDiagonalLinesFrom3x3ExampleFile() throws FileNotFoundException {
        VentMapper mapper = new VentMapper("/day5-part1-lines-3x3-example-with-diagonals.txt");
        mapper.markStraightLinesOnVentMap();
        int[][] ventMap = mapper.markDiagonalLinesOnVentMap();
        System.out.println(ventMap);
        assertArrayEquals(new int[]{3,1,3}, ventMap[0]);
        assertArrayEquals(new int[]{1,2,1}, ventMap[1]);
        assertArrayEquals(new int[]{3,1,3}, ventMap[2]);

    }

    @Test
    void shouldCountDangerZonesFromExampleFileWithDiagonals() throws FileNotFoundException {
        VentMapper mapper = new VentMapper("/day5-part1-lines-example.txt");
        mapper.markStraightLinesOnVentMap();
        mapper.markDiagonalLinesOnVentMap();
        assertEquals(12, mapper.countDangerZonesMarkedOnMap());
    }

    @Test
    void shouldCountDangerZonesFromActualFileWithDiagonals() throws FileNotFoundException {
        VentMapper mapper = new VentMapper("/day5-part1-lines-actual.txt");
        mapper.markStraightLinesOnVentMap();
        mapper.markDiagonalLinesOnVentMap();
        assertEquals(17787, mapper.countDangerZonesMarkedOnMap());
    }
}