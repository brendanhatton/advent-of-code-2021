package code.advent.day3.part2;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class DiagnosticResult {
    private final String gammaBinaryString;
    private final String epsilonBinaryString;
    private final String oxygenGeneratorBinaryString;
    private int[] zerosCount;
    private int[] onesCount;
    private String[] inputs;
    private String co2ScrubberBinaryString;

    public DiagnosticResult(int[] zerosCount, int[] onesCount, String[] inputs) {

        this.zerosCount = zerosCount;
        this.onesCount = onesCount;
        this.inputs = inputs;
        this.gammaBinaryString = calculateGamma();
        this.epsilonBinaryString = calculateEpsilon();
        this.oxygenGeneratorBinaryString = calculateOxygenGenerator();
        this.co2ScrubberBinaryString = calculateCo2Scrubber();
    }

    private String calculateGamma() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < zerosCount.length; i++) {
            if (zerosCount[i] > onesCount[i]) {
                result.append("0");
            } else {
                result.append("1");
            }
        }
        return result.toString();
    }

    private String calculateEpsilon() {
        StringBuilder epsilonString = new StringBuilder();
        for(char c : gammaBinaryString.toCharArray()) {
            if (c == '1') {
                epsilonString.append("0");
            } else {
                epsilonString.append("1");
            }
        }
        return epsilonString.toString();
    }

    public String calculateOxygenGenerator() {

        String[] remaining = inputs;
        int i = 0;
        while (remaining.length > 1) {
            remaining = calcOxy(remaining, i++);
        }
        System.out.println("oxygen generator value: " + remaining[0]);
        return remaining[0];
    }

    public String calculateCo2Scrubber() {

        String[] remaining = inputs;
        int i = 0;
        while (remaining.length > 1) {
            remaining = calcCo2(remaining, i++);
        }
        System.out.println("co2 scrubber value: " + remaining[0]);
        return remaining[0];
    }

     private String[] calcOxy(String[] remaining, final int i) {
        ArrayList<String> ones = new ArrayList<>();
        ArrayList<String> zeros = new ArrayList<>();
         Arrays.stream(remaining).forEach(item -> {
              if (item.charAt(i) == '0') {
                  zeros.add(item);
              } else {
                  ones.add(item);
              }
         });

         char discriminator;
         if (zeros.size() == ones.size()) {
             discriminator = '1';
         } else if (zeros.size() > ones.size()) {
             discriminator = '0';
         } else {
             discriminator = '1';
         }
//         System.out.println("index: " + i + " discriminator: " + discriminator + " remaining values: " + Arrays.toString(remaining));
         return Arrays.stream(remaining).filter(input -> input.toCharArray()[i] == discriminator).toArray(String[]::new);
     }
     private String[] calcCo2(String[] remaining, final int i) {
        ArrayList<String> ones = new ArrayList<>();
        ArrayList<String> zeros = new ArrayList<>();
         Arrays.stream(remaining).forEach(item -> {
              if (item.charAt(i) == '0') {
                  zeros.add(item);
              } else {
                  ones.add(item);
              }
         });

         char discriminator;
         if (zeros.size() == ones.size()) {
             discriminator = '0';
         } else if (zeros.size() > ones.size()) {
             discriminator = '1';
         } else {
             discriminator = '0';
         }
//         System.out.println("index: " + i + " discriminator: " + discriminator + " remaining values: " + Arrays.toString(remaining));
         return Arrays.stream(remaining).filter(input -> input.toCharArray()[i] == discriminator).toArray(String[]::new);
     }

    public int getGamma() {
       return Integer.parseInt(gammaBinaryString, 2);
    }

    public int getEpsilon() {
        return Integer.parseInt(epsilonBinaryString, 2);
    }

    public int getPowerConsumption() {
        return getGamma() * getEpsilon();
    }

    public int getOxygenGeneratorRating() {
        return Integer.parseInt(oxygenGeneratorBinaryString, 2);
    }

    public int getCO2ScrubberRating() {
        return Integer.parseInt(co2ScrubberBinaryString, 2);
    }

    public int getLifeSupportRating() {
        return getOxygenGeneratorRating() * getCO2ScrubberRating();
    }
}
