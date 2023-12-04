import net.covers1624.quack.collection.FastStream;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Day4 extends Day {

    @Test
    public void run() {
        Map<Integer, Card> cards = FastStream.of(loadLines("input.txt"))
                .map(Card::parse)
                .toMap(e -> e.num, e -> e);

        System.out.println("Part 1: " + FastStream.of(cards.values()).intSum(e -> e.score));
        Map<Card, Integer> toDo = FastStream.of(cards.values())
                .sorted(Comparator.comparingInt(e -> e.num))
                .toLinkedHashMap(e -> e, e -> 1);
        List<Card> order = new ArrayList<>(toDo.keySet());
        int numCards = 0;
        for (Card card : order) {
            int num = toDo.get(card);
            numCards += num;
            for (int i = card.num + 1; i <= card.num + card.wonNumbers.size(); i++) {
                Card c = cards.get(i);
                toDo.put(c, toDo.get(c) + num);
            }
        }
        System.out.println("Part 2: " + numCards);
    }

    public record Card(int num, List<Integer> numbers, List<Integer> winning, List<Integer> wonNumbers, int score) {

        public static Card parse(String line) {
            String[] lineSplit = line.split(":");
            int card = Integer.parseInt(lineSplit[0].substring(5).trim());
            String numbersStr = lineSplit[1];
            String[] numberSplit = numbersStr.split("\\|");
            List<Integer> winning = FastStream.of(numberSplit[0].split(" "))
                    .map(String::trim)
                    .filterNot(String::isEmpty)
                    .map(Integer::parseInt)
                    .toList();

            List<Integer> numbers = FastStream.of(numberSplit[1].split(" "))
                    .map(String::trim)
                    .filterNot(String::isEmpty)
                    .map(Integer::parseInt)
                    .toList();
            List<Integer> wonNumbers = FastStream.of(numbers)
                    .filter(winning::contains)
                    .toList();

            int won = wonNumbers.size();
            return new Card(card, numbers, winning, wonNumbers, won != 0 ? (int) Math.pow(2, wonNumbers.size() - 1) : 0);
        }
    }
}
