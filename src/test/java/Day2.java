import org.junit.jupiter.api.Test;

public class Day2 extends Day {

    private static final int[] MAX = { 12, 13, 14 };

    @Test
    public void run() {
        int idSum = 0;
        int totalPower = 0;
        boolean skip;
        for (String line : loadLines("input.txt")) {
            skip = false;
            System.out.println(line);
            String[] gameSplit = line.split(":");
            String[] hands = gameSplit[1].split(";");
            int[] maxes = new int[3];
            for (String hand : hands) {
                String[] items = hand.split(",");
                for (String item : items) {
                    String[] split = item.trim().split(" ");
                    int colour = getIdx(split[1]);
                    int count = Integer.parseInt(split[0]);
                    int max = MAX[colour];

                    if (count > max) skip = true;
                    maxes[colour] = Math.max(maxes[colour], count);
                }
            }
            if (!skip) {
                idSum += Integer.parseInt(gameSplit[0].split(" ")[1].trim());
            }
            totalPower += maxes[0] * maxes[1] * maxes[2];
        }
        System.out.println(idSum);
        System.out.println(totalPower);
    }

    private static int getIdx(String colour) {
        return switch (colour) {
            case "red" -> 0;
            case "green" -> 1;
            case "blue" -> 2;
            default -> throw new AssertionError();
        };
    }
}
