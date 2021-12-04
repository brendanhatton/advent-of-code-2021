package code.advent.day1.part2;

public class IncreasingDepthCounterSlidingWindow {

    public int countIncreasesInDepth(int[] measurements) {
        int count = 0;
        for (int i = 1; i<measurements.length-2; i++) {
            int firstWindow = measurements[i-1] + measurements[i] + measurements[i+1];
            int secondWindow = measurements[i] + measurements[i+1] + measurements[i+2];
            if (secondWindow > firstWindow) {
            count++ ;
            }
        }
        return count;
    }

}
