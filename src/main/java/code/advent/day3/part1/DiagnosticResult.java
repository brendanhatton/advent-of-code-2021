package code.advent.day3.part1;


public class DiagnosticResult {
    private final String gammaBinaryString;
    private final String epsilonBinaryString;
    private int[] zerosCount;
    private int[] onesCount;

    public DiagnosticResult(int[] zerosCount, int[] onesCount) {

        this.zerosCount = zerosCount;
        this.onesCount = onesCount;
        this.gammaBinaryString = calculateGamma();
        this.epsilonBinaryString = calculateEpsilon();
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

    public int getGamma() {
       return Integer.parseInt(gammaBinaryString, 2);
    }

    public int getEpsilon() {
        return Integer.parseInt(epsilonBinaryString, 2);
    }

    public int getPowerConsumption() {
        return getGamma() * getEpsilon();
    }
}
