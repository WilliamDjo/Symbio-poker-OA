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
        
        Hand(List<Card> cards) {
            this.cards = cards;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;
            
            System.out.println("Read line: " + line);
        }
        scanner.close();
    }
}

