package code.advent.day4.part2;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

class BingoTest {

    @Test
    void exampleBingo() {
        Bingo bingo = new Bingo("/day4-part1-bingo-example.txt");
        assertEquals(1924, bingo.getWinningScore());

    }

    @Test
    void actualBingo() {
        Bingo bingo = new Bingo("/day4-part1-bingo-actual.txt");
        assertEquals(19012, bingo.getWinningScore());

    }

}