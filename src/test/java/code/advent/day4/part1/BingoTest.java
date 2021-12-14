package code.advent.day4.part1;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BingoTest {

    @Test
    void exampleBingo() {
        Bingo bingo = new Bingo("/day4-part1-bingo-example.txt");
        assertEquals(List.of(4512), bingo.getWinningScore());

    }

    @Test
    void actualBingo() {
        Bingo bingo = new Bingo("/day4-part1-bingo-actual.txt");
        assertEquals(List.of(21607), bingo.getWinningScore());

    }

}