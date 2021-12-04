package code.advent.day3.part1;

import java.util.Arrays;

public class DiagnosticProcessor {

    public DiagnosticResult diagnose(String[] inputs) {
        int[] onesCount = new int[12];
        int[] zerosCount = new int[12];
        Arrays.stream(inputs).forEach(s -> {
            for (int i = 0; i< s.length(); i++) {
                if (s.charAt(i) == '1') {
                    onesCount[i]++;
                } else {
                    zerosCount[i]++;
                }
            }
        });
        return new DiagnosticResult(zerosCount, onesCount);
    }
}
