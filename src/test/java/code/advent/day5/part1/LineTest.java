package code.advent.day5.part1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LineTest {

    @Test
    void shouldParseBasicInput() {
        Line line = new Line("1,2 -> 3,4");

        assertEquals(1, line.getXstart());
        assertEquals(2, line.getYstart());
        assertEquals(3, line.getXend());
        assertEquals(4, line.getYend());

    }

    @Test
    void shouldIdentifyStraightLines() {
        Line line = new Line("1,2 -> 3,4");
        assertEquals(false, line.isStraight());

        line = new Line("1,2 -> 1,4");
        assertEquals(true, line.isStraight());

        line = new Line("1,2 -> 3,2");
        assertEquals(true, line.isStraight());

        line = new Line("1,1 -> 2,2");
        assertEquals(false, line.isStraight());
    }
}