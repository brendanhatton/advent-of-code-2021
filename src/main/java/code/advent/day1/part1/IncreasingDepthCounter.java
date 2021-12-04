package code.advent.day1.part1;

public class IncreasingDepthCounter {

    public int countIncreasesInDepth(int[] measurements) {
        int count = 0;
        for (int i = 1; i<measurements.length; i++) {
            if (measurements[i] > measurements[i-1]) {
            count++ ;
            }
        }
        return count;
    }

}
