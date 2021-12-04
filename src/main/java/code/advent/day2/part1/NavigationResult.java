package code.advent.day2.part1;

public class NavigationResult {
    private int horizontalPosition;
    private int depth;

    public int moveForeward(int amount) {
        horizontalPosition += amount;
        return horizontalPosition;
    }

    public int increaseDepth(int amount) {
        depth += amount;
        return depth;
    }

    public int decreaseDepth(int amount) {
        depth -= amount;
        return depth;
    }


    public int getHorizontalPosition() {
    return horizontalPosition;
    }

    public int getDepth() {
        return depth;
    }

    public int getDepthMultipliedByHorzontalPosition() {
        return depth * horizontalPosition;
    }

    @Override
    public String toString() {
        return "NavigationResult{" +
                "horizontalPosition=" + horizontalPosition +
                ", depth=" + depth +
                '}';
    }
}
