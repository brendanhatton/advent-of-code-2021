package code.advent.day5;

import code.advent.day5.Line;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class VentMapperPart1Test {

    @Test
    void shouldLoadLinesFromExampleFile() throws FileNotFoundException {
        VentMapper mapper = new VentMapper("/day5-part1-lines-example.txt");
        assertEquals(10, mapper.getLines().size());
        Line firstLine = mapper.getLines().get(0);
        assertEquals(0, firstLine.getXstart());
        assertEquals(5, firstLine.getXend());
        assertEquals(9, firstLine.getYstart());
        assertEquals(9, firstLine.getYend());
    }

    @Test
    void shouldGenerateMapFromExampleFile() throws FileNotFoundException {
        VentMapper mapper = new VentMapper("/day5-part1-lines-example.txt");
        assertEquals(10, mapper.getVentMap().length);
        assertEquals(10, mapper.getVentMap()[0].length);
    }




    @Test
    void shouldMarkStraightLinesFrom3x3ExampleFile() throws FileNotFoundException {
        VentMapper mapper = new VentMapper("/day5-part1-lines-3x3-example.txt");
        int[][] ventMap = mapper.markStraightLinesOnVentMap();
        System.out.println(ventMap);
        assertArrayEquals(new int[]{2,1,2}, ventMap[0]);
        assertArrayEquals(new int[]{1,0,1}, ventMap[1]);
        assertArrayEquals(new int[]{2,1,2}, ventMap[2]);

    }


    @Test
    void shouldMarkStraightLinesFromExampleFile() throws FileNotFoundException {
        VentMapper mapper = new VentMapper("/day5-part1-lines-example.txt");
        int[][] ventMap = mapper.markStraightLinesOnVentMap();
        System.out.println(ventMap);
    }

    @Test
    void shouldCountDangerZonesFromExampleFile() throws FileNotFoundException {
        VentMapper mapper = new VentMapper("/day5-part1-lines-example.txt");
        mapper.markStraightLinesOnVentMap();
        assertEquals(5, mapper.countDangerZonesMarkedOnMap());
    }

    @Test
    void shouldCountDangerZonesFromActualFile() throws FileNotFoundException {
        VentMapper mapper = new VentMapper("/day5-part1-lines-actual.txt");
        mapper.markStraightLinesOnVentMap();
        assertEquals(5306, mapper.countDangerZonesMarkedOnMap());
    }
}