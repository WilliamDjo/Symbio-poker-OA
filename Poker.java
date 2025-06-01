import java.util.*;

public class Poker {
    // hash map to store card values
    private static final HashMap<Character, Integer> CARD_VALUE_MAP = new HashMap<>();
    static {
        CARD_VALUE_MAP.put('2', 2);
        CARD_VALUE_MAP.put('3', 3);
        CARD_VALUE_MAP.put('4', 4);
        CARD_VALUE_MAP.put('5', 5);
        CARD_VALUE_MAP.put('6', 6);
        CARD_VALUE_MAP.put('7', 7);
        CARD_VALUE_MAP.put('8', 8);
        CARD_VALUE_MAP.put('9', 9);
        CARD_VALUE_MAP.put('T', 10);
        CARD_VALUE_MAP.put('J', 11);
        CARD_VALUE_MAP.put('Q', 12);
        CARD_VALUE_MAP.put('K', 13);
        CARD_VALUE_MAP.put('A', 14);
    }

    // hand rankings
    private static final int HIGH_CARD = 1;
    private static final int PAIR = 2;
    private static final int TWO_PAIR = 3;
    private static final int THREE_OF_A_KIND = 4;
    private static final int STRAIGHT = 5;
    private static final int FLUSH = 6;
    private static final int FULL_HOUSE = 7;
    private static final int FOUR_OF_A_KIND = 8;
    private static final int STRAIGHT_FLUSH = 9;
    private static final int ROYAL_FLUSH = 10;
    static class Card {
        int value;
        char suit;
        
        Card(String card) {
            this.value = CARD_VALUE_MAP.get(card.charAt(0));
            this.suit = card.charAt(0);
        }
    }
    static class Hand {
        List<Card> cards;
        int rank;
        List<Integer> tie;

        Hand(List<Card> cards) {
            this.cards = cards;
            this.tie = new ArrayList<>();
            evaluateHand();
        }

        private void evaluateHand() {
            cards.sort((a ,b) -> b.value - a.value);

            Map<Integer, Integer> valueCount = new HashMap<>();
            Map<Character, Integer> suitCount = new HashMap<>();

            for (Card card : cards) {
                valueCount.put(card.value, valueCount.getOrDefault(card.value, 0) + 1);
                suitCount.put(card.suit, suitCount.getOrDefault(card.suit, 0) + 1);
            }

            // Check for straight
            boolean isStraight = checkStraight(cards);

            // make value counts into pairs so that it's more iterable
            List<Map.Entry<Integer, Integer>> valuePairs = new ArrayList<>(valueCount.entrySet());
            valuePairs.sort((a, b) -> {
                if (b.getValue() != a.getValue()) {
                    return b.getValue() - a.getValue();
                }
                return b.getKey() - a.getKey();
            });
            
             // Determine hand rank
            if (isStraight) {
                rank = STRAIGHT;
                tie.add(cards.get(0).value);
            } else if (valuePairs.get(0).getValue() == 3) {
                rank = THREE_OF_A_KIND;
                tie.add(valuePairs.get(0).getKey());
                for (Map.Entry<Integer, Integer> entry : valuePairs) {
                    if (entry.getValue() == 1) {
                        tie.add(entry.getKey());
                    }
                }
            } else if (valuePairs.get(0).getValue() == 2 && valuePairs.get(1).getValue() == 2) {
                rank = TWO_PAIR;
                tie.add(Math.max(valuePairs.get(0).getKey(), valuePairs.get(1).getKey()));
                tie.add(Math.min(valuePairs.get(0).getKey(), valuePairs.get(1).getKey()));
                tie.add(valuePairs.get(2).getKey());
            } else if (valuePairs.get(0).getValue() == 2) {
                rank = PAIR;
                tie.add(valuePairs.get(0).getKey());
                for (Map.Entry<Integer, Integer> entry : valuePairs) {
                    if (entry.getValue() == 1) {
                        tie.add(entry.getKey());
                    }
                }
            } else {
                rank = HIGH_CARD;
                for (Card card : cards) {
                    tie.add(card.value);
                }
            }
        }

        private boolean checkStraight(List<Card> cards) {
            for (int i = 0; i < cards.size() - 1; i++) {
                if (cards.get(i).value - cards.get(i + 1).value != 1) {
                    return false;
                }
            }
            return true;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                continue;
            }

            String[] cards = line.split(" ");
            if (cards.length != 10) {
                continue;
            }

            // Parsing player 1's hand
            List<Card> p1Cards = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                p1Cards.add(new Card(cards[i]));
            }
            Hand handOne = new Hand(p1Cards);

            // Parsing player 2's hand
            List<Card> p2Cards = new ArrayList<>();
            for (int i = 5; i < 10; i++) {
                p2Cards.add(new Card(cards[i]));
            }
            Hand handTwo = new Hand(p2Cards);
        }
        scanner.close();
    }
}

