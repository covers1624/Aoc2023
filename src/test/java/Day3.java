import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day3 extends Day {

    public int[][] neighbors = {
            { -1, -1 },
            { 0, -1 },
            { 1, -1 },
            { 1, 0 },
            { 1, 1 },
            { 0, 1 },
            { -1, 1 },
            { -1, 0 }
    };

    @Test
    public void run() {
//        Char2IntMap map = new Char2IntArrayMap();
//        for (String line : loadLines("input.txt")) {
//            for (char c : line.toCharArray()) {
//                map.mergeInt(c, 1, Integer::sum);
//            }
//        }
//        for (Char2IntMap.Entry entry : map.char2IntEntrySet()) {
//            System.out.println(entry.getCharKey() + "  " + entry.getIntValue());
//        }
//        if (true) return;

        List<String> lines = loadLines("input.txt");
        int width = lines.get(0).length();
        int height = lines.size();
        // For my sanity..
        lines.forEach(e -> {
            assert e.length() == width;
        });

        int sum = 0;
        int gearSum = 0;
        Set<FoundString> found = new HashSet<>();
        for (int y = 0; y < height; y++) {
            String line = lines.get(y);
            for (int x = 0; x < width; x++) {
                char ch = line.charAt(x);
                switch (ch) {
                    case '*', '%', '@', '#', '+', '$', '-', '=', '/', '&' -> {
                        List<String> gearNums = new ArrayList<>();
                        for (int[] neighbor : neighbors) {
                            int nx = x + neighbor[0];
                            int ny = y + neighbor[1];
                            // bounds
                            if (nx > width || nx < 0) continue;
                            if (ny > height || ny < 0) continue;

                            String nL = lines.get(ny);
                            if (isDigit(nL.charAt(nx))) {
                                FoundString string = findNum(nL, width, nx, ny);
                                if (found.add(string)) {
                                    if (ch == '*') {
                                        gearNums.add(nL.substring(string.xA, string.xB));
                                    }
                                    sum += Integer.parseInt(nL, string.xA, string.xB, 10);
                                }
                            }
                        }
                        if (gearNums.size() == 2) {
                            gearSum += Integer.parseInt(gearNums.get(0)) * Integer.parseInt(gearNums.get(1));
                        }
                    }
                }
            }
        }
        System.out.println(sum);
        System.out.println(gearSum);

    }

    private static FoundString findNum(String line, int width, int x, int y) {
        int min = x;
        while (min - 1 >= 0 && isDigit(line.charAt(min - 1))) {
            min--;
        }
        int max = x;
        while (max < width && isDigit(line.charAt(max))) {
            max++;
        }
        return new FoundString(y, min, max);
    }

    private static boolean isDigit(char ch) {
        return ch >= '0' && ch <= '9';
    }

    record FoundString(int y, int xA, int xB) {
    }
}
