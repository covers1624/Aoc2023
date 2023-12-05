import net.covers1624.quack.collection.FastStream;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class Day5 extends Day {

    @Test
    public void run() {
        List<String> lines = loadLines("input.txt");

        List<Long> seeds = new ArrayList<>();
        List<Range> seeds2 = new ArrayList<>();

        List<Range> seedToSoil = new ArrayList<>();
        List<Range> soilToFertilizer = new ArrayList<>();
        List<Range> fertilizerToWater = new ArrayList<>();
        List<Range> waterToLight = new ArrayList<>();
        List<Range> lightToTemperature = new ArrayList<>();
        List<Range> temperatureToHumidity = new ArrayList<>();
        List<Range> humidityToLocation = new ArrayList<>();

        List<Range> active = null;
        for (String line : lines) {
            if (line.isEmpty()) continue;
            if (line.startsWith("seeds: ")) {
                seeds.addAll(FastStream.of(line.substring(6).split(" ")).map(String::trim).filterNot(String::isBlank).map(Long::parseLong).toList());
                for (int i = 0; i < seeds.size(); i += 2) {
                    seeds2.add(new Range(
                            -1,
                            seeds.get(i),
                            seeds.get(i + 1)
                    ));
                }
                continue;
            }
            switch (line) {
                case "seed-to-soil map:" -> {
                    active = seedToSoil;
                    continue;
                }
                case "soil-to-fertilizer map:" -> {
                    active = soilToFertilizer;
                    continue;
                }
                case "fertilizer-to-water map:" -> {
                    active = fertilizerToWater;
                    continue;
                }
                case "water-to-light map:" -> {
                    active = waterToLight;
                    continue;
                }
                case "light-to-temperature map:" -> {
                    active = lightToTemperature;
                    continue;
                }
                case "temperature-to-humidity map:" -> {
                    active = temperatureToHumidity;
                    continue;
                }
                case "humidity-to-location map:" -> {
                    active = humidityToLocation;
                    continue;
                }
            }
            assert active != null;
            String[] splits = line.split(" ");
            active.add(new Range(
                    Long.parseLong(splits[0]),
                    Long.parseLong(splits[1]),
                    Long.parseLong(splits[2])
            ));
        }

        long lowLocation = Long.MAX_VALUE;
        for (Long seed : seeds) {
            long soil = Range.map(seedToSoil, seed);
            long fertilizer = Range.map(soilToFertilizer, soil);
            long water = Range.map(fertilizerToWater, fertilizer);
            long light = Range.map(waterToLight, water);
            long temperature = Range.map(lightToTemperature, light);
            long humidity = Range.map(temperatureToHumidity, temperature);
            long location = Range.map(humidityToLocation, humidity);
            lowLocation = Math.min(lowLocation, location);
        }
        System.out.println(lowLocation);

        // Fucking brute force. Jit don't fail me now!
        lowLocation = Long.MAX_VALUE;
        for (Range range : seeds2) {
            LOGGER.info("Range: {}", range);
            for (long seed = range.src; seed < range.src + range.len; seed++) {
                long soil = Range.map(seedToSoil, seed);
                long fertilizer = Range.map(soilToFertilizer, soil);
                long water = Range.map(fertilizerToWater, fertilizer);
                long light = Range.map(waterToLight, water);
                long temperature = Range.map(lightToTemperature, light);
                long humidity = Range.map(temperatureToHumidity, temperature);
                long location = Range.map(humidityToLocation, humidity);
                lowLocation = Math.min(lowLocation, location);
            }
        }
        System.out.println(lowLocation);
    }

    record Range(long dest, long src, long len) {

        public boolean contained(long val) {
            return val >= src && val < src + len;
        }

        public long map(long val) {
            return dest + val - src;
        }

        public static long map(List<Range> ranges, long val) {
            for (Range range : ranges) {
                if (range.contained(val)) {
                    return range.map(val);
                }
            }
            return val;
        }
    }
}
