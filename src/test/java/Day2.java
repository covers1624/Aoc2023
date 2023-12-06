import org.junit.jupiter.api.Test;

public class Day2 extends Day {

    @Test
    public void run() throws Throwable {
        byte[] line = open("input.txt").readAllBytes();
        for (int i = 0; i < 3000; i++) {
            long time = System.nanoTime();
            int[] result = solve(line);
            time = System.nanoTime() - time;
            LOGGER.info("Part 1: {}", result[0]);
            LOGGER.info("Part 2: {}", result[1]);
            LOGGER.info("Time {}μs", time / 1000D);
        }
    }

    public int[] solve(byte[] line) {
        int idSum = 0;
        int totalPower = 0;

        int len = line.length;

        boolean tooMany = false;
        int id = -1;
        int lastNum = -1;
        int num = -1;
        int maxR = 0;
        int maxG = 0;
        int maxB = 0;
        for (int i = 5; i < len; i++) {
            byte ch = line[i];
            switch (ch) {
                case '\n' -> {
                    // Reset state.
                    if (!tooMany) {
                        idSum += id;
                    }
                    totalPower += maxR * maxG * maxB;
                    tooMany = false;
                    maxR = 0;
                    maxG = 0;
                    maxB = 0;
                    i += 4; // Skip to the next number.
                }
                case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {
                    if (num == -1) {
                        num = i;
                    }
                }
                case ' ' -> {
                    if (num != -1) {
                        lastNum = parseInt(line, num, i);
                        num = -1;
                    }
                }
                case ':' -> {
                    if (num != -1) {
                        id = parseInt(line, num, i);
                        num = -1;
                    }
                }
                case 'r' -> {
                    if (lastNum > 12) {
                        tooMany = true;
                    }
                    maxR = Math.max(maxR, lastNum);
                    i += 2; // Skip past.
                }
                case 'g' -> {
                    if (lastNum > 13) {
                        tooMany = true;
                    }
                    maxG = Math.max(maxG, lastNum);
                    i += 4; // Skip past.
                }
                case 'b' -> {
                    if (lastNum > 14) {
                        tooMany = true;
                    }
                    maxB = Math.max(maxB, lastNum);
                    i += 3; // Skip past.
                }
            }
        }
        return new int[] { idSum, totalPower };
    }

    private static int parseInt(byte[] bytes, int start, int end) {
        int result = 0;
        for (int i = start; i < end; i++) {
            result = result * 10 + (bytes[i] - '0');
        }
        return result;
    }
}
