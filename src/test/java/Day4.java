import com.google.common.collect.ImmutableSet;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Day4 extends Day {

    @Test
    public void run() {
        int counter = 0;
        long[] runs = new long[30];
        for (int i = 0; i < 300; i++) {
            long time = System.nanoTime();
            int[] result = solve();
            time = System.nanoTime() - time;
            LOGGER.info("Part 1: {}", result[0]);
            LOGGER.info("Part 2: {}", result[1]);
            LOGGER.info("Time {}μs", time / 1000D);
            runs[counter++ % runs.length] = time;
        }
        double avg = 0;
        for (long run : runs) {
            avg += run;
        }
        avg /= runs.length;
        LOGGER.info("Last {} runs avg {}μs", runs.length, avg / 1000D);
    }

    public int[] solve() {
        List<String> lines = loadLines("input.txt");
        int score = 0;
        int[] cards = new int[lines.size()];
        for (String line : lines) {
            int colonSep = line.indexOf(':');
            int num = Integer.parseInt(line, line.lastIndexOf(' ', colonSep) + 1, colonSep, 10);
            int pipeSep = line.indexOf('|');
            Set<String> winningNumbers = ImmutableSet.copyOf(line.substring(colonSep + 1, pipeSep).split(" "));
            String[] ourNumbers = line.substring(pipeSep + 1).split(" ");
            int nWinning = 0;
            for (String ourNumber : ourNumbers) {
                if (ourNumber.isBlank()) continue;
                if (winningNumbers.contains(ourNumber)) {
                    nWinning++;
                }
            }
            cards[num - 1] = nWinning;
            if (nWinning != 0) {
                score += (int) Math.pow(2, nWinning - 1);
            }
        }

        int numCards = 0;
        int[] part2Cards = new int[lines.size()];
        Arrays.fill(part2Cards, 1);
        for (int i = 0; i < part2Cards.length; i++) {
            int num = part2Cards[i];
            numCards += num;
            for (int j = 0; j < cards[i]; j++) {
                part2Cards[i + j + 1] += num;
            }
        }
        return new int[] { score, numCards };
    }
}
