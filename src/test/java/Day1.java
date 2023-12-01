import org.junit.jupiter.api.Test;

import java.util.Map;

public class Day1 extends Day {

    private final Map<String, Character> MAP = Map.of(
            "one", '1',
            "two", '2',
            "three", '3',
            "four", '4',
            "five", '5',
            "six", '6',
            "seven", '7',
            "eight", '8',
            "nine", '9'
    );

    @Test
    public void run() {
        LOGGER.info("Part 1: {}", solve("input.txt", false)); // 54951
        LOGGER.info("Part 2: {}", solve("input.txt", true)); // 55218
    }

    private int solve(String file, boolean part2) {
        int sum = 0;
        for (String line : loadLines(file)) {
            char first = 0xFFFF;
            char last = 0xFFFF;
            StringBuilder buf = new StringBuilder();
            for (char c : line.toCharArray()) {
                if (part2) {
                    if (!Character.isDigit(c)) {
                        buf.append(c);
                        String str = buf.toString();
                        while (!str.isEmpty()) {
                            Character ch = MAP.get(str);
                            if (ch != null) {
                                c = ch;
                                buf.setLength(0);
                                buf.append(str.substring(1));
                                break;
                            }
                            str = str.substring(1);
                        }
                    } else {
                        buf.setLength(0);
                    }
                }

                if (Character.isDigit(c)) {
                    if (first == 0xFFFF) {
                        first = c;
                    }
                    last = c;
                }
            }
//            LOGGER.info(first + "" + last);
            sum += Integer.parseInt(first + "" + last);
        }
        return sum;
    }
}
