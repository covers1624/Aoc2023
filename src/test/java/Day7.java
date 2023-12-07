import it.unimi.dsi.fastutil.chars.Char2IntArrayMap;
import it.unimi.dsi.fastutil.chars.Char2IntMap;
import net.covers1624.quack.collection.FastStream;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Day7 extends Day {

    private static final boolean part2 = true;
    private static final List<Character> order = part2
            ? List.of('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J')
            : List.of('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2');

    @Test
    public void run() {
        List<Hand> hands = FastStream.of(loadLines("input.txt"))
                .map(Hand::parse)
                .toList();

        hands.sort(Comparator.reverseOrder());
        long winnings = 0;
        for (int i = 0; i < hands.size(); i++) {
            Hand hand = hands.get(i);
            LOGGER.info(hand + " " + hand.rank() + " " + hand.bestRank());
            winnings += (long) hand.bid * (i + 1);
        }
        LOGGER.info(winnings);
    }

    private record Hand(String cards, int bid) implements Comparable<Hand> {

        public static Hand parse(String str) {
            String[] split = str.split(" ");
            return new Hand(split[0], Integer.parseInt(split[1]));
        }

        @Override
        public int compareTo(Hand o) {
            int rank = bestRank();
            int oRank = o.bestRank();
            if (rank == oRank) {
                for (int i = 0; i < 5; i++) {
                    int ch = order.indexOf(cards.charAt(i));
                    int oCH = order.indexOf(o.cards.charAt(i));
                    if (ch == oCH) continue;
                    return Integer.compare(ch, oCH);
                }
                throw new RuntimeException("The fuck? " + cards + " " + o.cards);
            }
            return Integer.compare(rank, oRank);
        }

        private int bestRank() {
            if (!part2 || !StringUtils.containsAny(cards, 'J')) return rank();

            int best = Integer.MAX_VALUE;
            for (int i = 0; i < order.size() - 1; i++) {
                int rank = doRank(counts(cards.replace('J', order.get(i))));
                if (rank < best) {
                    best = rank;
                }
            }
            return best;
        }

        private int rank() {
            return doRank(counts(cards));
        }

        private int doRank(Char2IntMap counts) {
            if (counts.size() == 1) return 0; // Five of a kind
            if (counts.size() == 2) {

                int a = -1;
                int b = -1;
                for (Char2IntMap.Entry entry : counts.char2IntEntrySet()) {
                    if (a == -1) {
                        a = entry.getIntValue();
                    } else if (b == -1) {
                        b = entry.getIntValue();
                    }
                }
                if (a == 4 || b == 4) {
                    return 1; // Four of a kind
                } else if (a == 3 || b == 3) {
                    return 2; // Full house.
                }
                throw new RuntimeException("The fuck? " + cards);
            }
            if (counts.size() == 3) {
                int a = -1;
                int b = -1;
                int c = -1;
                for (Char2IntMap.Entry entry : counts.char2IntEntrySet()) {
                    if (a == -1) {
                        a = entry.getIntValue();
                    } else if (b == -1) {
                        b = entry.getIntValue();
                    } else {
                        c = entry.getIntValue();
                    }
                }
                if (a == 3 || b == 3 || c == 3) {
                    return 3; // Three of a kind.
                } else if (a + b == 4 || b + c == 4 || a + c == 4) {
                    return 4; // Two pair.
                }
                throw new RuntimeException("The fuck? " + cards);
            }
            if (counts.size() == 4) return 5; // One pair.
            if (counts.size() == 5) return 6; // High card.
            throw new RuntimeException("The fuck? " + cards);
        }

        private static Char2IntMap counts(String cards) {
            Char2IntMap map = new Char2IntArrayMap();
            for (char ch : cards.toCharArray()) {
                map.mergeInt(ch, 1, Integer::sum);
            }
            return map;
        }
    }
}
