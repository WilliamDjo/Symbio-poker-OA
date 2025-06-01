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

        }
    }
    static class Hand {
        List<Card> cards;
        
        Hand(List<Card> cards) {
            this.cards = cards;
        }
    }

    public static void main(String[] args) {
    
    }
}

