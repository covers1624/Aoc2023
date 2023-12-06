import org.junit.jupiter.api.Test;

import java.util.List;

public class Day6 extends Day {

    private final List<Race> RACES = List.of(
            new Race(38, 234),
            new Race(67, 1027),
            new Race(76, 1157),
            new Race(73, 1236)
    );

    private final List<Race> RACES_TEST = List.of(
            new Race(7, 9),
            new Race(15, 40),
            new Race(30, 200)
    );

    private final Race PART2 = new Race(38677673, 234102711571236L);
    private final Race PART2_TEST = new Race(71530, 940200);

    @Test
    public void run() {
        int counter = 0;
        long first = -1;
        long[] runs = new long[30];
        for (int i = 0; i < 300; i++) {
            long time = System.nanoTime();
            int a = runMulti(RACES);
            int b = runQuadratic(PART2);
            time = System.nanoTime() - time;
            if (first == -1) first = time;
            LOGGER.info("Part 1: {}", a);
            LOGGER.info("Part 2: {}", b);
            LOGGER.info("Time {}μs", time / 1000D);
            runs[counter++ % runs.length] = time;
        }
        double avg = 0;
        for (long run : runs) {
            avg += run;
        }
        avg /= runs.length;
        LOGGER.info("First run {}μs {}ns", first / 1000D, first);
        LOGGER.info("Last {} runs avg {}μs {}ns", runs.length, avg / 1000D, avg);
    }

    private int runMulti(List<Race> races) {
        int error = 1;
        for (Race race : races) {
            error *= runQuadratic(race);
        }
        return error;
    }

    private int runQuadratic(Race race) {
        int min = (int) ((race.time - Math.sqrt(Math.pow(race.time, 2) - 4 * race.dist)) / 2);
        int max = (int) ((race.time + Math.sqrt(Math.pow(race.time, 2) - 4 * race.dist)) / 2);

        return max - min;
    }

    private record Race(long time, long dist) { }
}
