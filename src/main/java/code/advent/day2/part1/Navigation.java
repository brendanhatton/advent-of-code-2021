package code.advent.day2.part1;

import java.util.Arrays;

public class Navigation {

    public NavigationResult navigate(String[] directions) {
        NavigationResult result = new NavigationResult();
        Arrays.stream(directions).forEach(direction -> {
            String[] tokens = direction.split(" ");
            String instruction = tokens[0];
            int value = Integer.parseInt(tokens[1]);

            switch (instruction){
                case "forward":
                    result.moveForeward(value);
                    break;
                case "up":
                    result.decreaseDepth(value);
                    break;
                case "down":
                    result.increaseDepth(value);
                    break;
            }
            System.out.println("instruction: " + direction + " - current position: " + result);

        });
        return result;
    }

}
