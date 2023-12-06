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
        LOGGER.info("Part 1: {}", runMulti(RACES));
        LOGGER.info("Part 2: {}", runRace(PART2));
    }

    private int runMulti(List<Race> races) {
        int error = 1;
        for (Race race : races) {
            error *= runRace(race);
        }
        return error;
    }

    private int runRace(Race race) {
        int numWon = 0;
        for (long speed = 1; speed < race.time; speed++) {
            long remaining = race.time - speed;
            long dist = speed * remaining;
            if (dist > race.dist) {
                numWon++;
            }

        }
        return numWon;
    }

    private record Race(long time, long dist) { }
}
