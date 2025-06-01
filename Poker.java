import java.util.*;

/**
 * Symbio Online Assessment: Poker Hand Evaluator - A command-line program that evaluates poker hands
 * for two players and determines the winner of each hand.
 * 
 * Input: Lines of 10 cards via STDIN (5 per player)
 * Output: Total number of hands won by each player
 * 
 * @author William
 */
public class Poker {

    // Card value mapping - Maps card characters to their numeric values
    // Ace is high only (value 14) as per requirements
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

    /**
     * Represents a playing card with a value and suit.
     * Cards are immutable once created.
     */
    static class Card {
        int value;  // Numeric value (2-14)
        char suit;  // Suit character (D, H, S, C)
        
        /**
         * Constructs a Card from a 2-character string representation.
         * 
         * @param card A 2-character string (e.g., "AH" for Ace of Hearts)
         */
        Card(String card) {
            this.value = CARD_VALUE_MAP.get(card.charAt(0));
            this.suit = card.charAt(0);
        }
    }

    /**
     * Represents a 5-card poker hand with evaluation and comparison.
     */
    static class Hand {
        List<Card> cards;  // The 5 cards in this hand
        int rank; // Hand rank (HIGH_CARD to ROYAL_FLUSH)
        List<Integer> tie; // Values used to break ties between same-ranked hands

        /**
         * Constructs a Hand from a list of 5 cards and immediately evaluates it.
         * 
         * @param cards List of 5 Card objects
         */
        Hand(List<Card> cards) {
            this.cards = cards;
            this.tie = new ArrayList<>();
            evaluateHand();
        }

        /**
         * Evaluates the poker hand and determines its rank and tie-breakers.
         * This method identifies the hand type (pair, flush, etc.) and sets up
         * the tie list for comparison with other hands of the same rank.
         */
        private void evaluateHand() {
            // Sort cards by value in descending order (highest first)
            // This makes it easier to check for straights and extract tie-breakers
            cards.sort((a ,b) -> b.value - a.value);

            // Count how many times each card value appears (for pairs etc.)
            Map<Integer, Integer> valueCount = new HashMap<>();
             // Count how many cards of each suit we have (for flush)
            Map<Character, Integer> suitCount = new HashMap<>();

            // Build the frequency maps
            for (Card card : cards) {
                valueCount.put(card.value, valueCount.getOrDefault(card.value, 0) + 1);
                suitCount.put(card.suit, suitCount.getOrDefault(card.suit, 0) + 1);
            }

            // Check for straight, 5 consecutive values
            boolean isStraight = checkStraight(cards);

            // Check for flush, all 5 cards must be the same suit
            boolean isFlush = suitCount.size() == 1;

            // Make value counts into pairs so that it's sortable
            // This helps identify pairs, three of a kind, etc.
            List<Map.Entry<Integer, Integer>> valuePairs = new ArrayList<>(valueCount.entrySet());

            // Sort by count first (descending), then by card value (descending)
            // This ensures that three of a kind comes before pairs, and higher cards come first
            valuePairs.sort((a, b) -> {
                if (b.getValue() != a.getValue()) {
                    return b.getValue() - a.getValue(); // Sort by count
                }
                return b.getKey() - a.getKey(); // Then by value
            });
            
             // Determine hand rank - Check from highest rank to lowest
            if (isStraight && isFlush) {
                // Straight flush - could be royal flush if ace high
                if (cards.get(0).value == 14 && cards.get(4).value == 10) {
                    rank = ROYAL_FLUSH;
                } else {
                    rank = STRAIGHT_FLUSH; // Any other straight flush
                }
                tie.add(cards.get(0).value); // Highest card determines winner
            } else if (valuePairs.get(0).getValue() == 4) {
                // Four of a kind - four cards of same value
                rank = FOUR_OF_A_KIND;
                tie.add(valuePairs.get(0).getKey());  // Value of the four cards
                tie.add(valuePairs.get(1).getKey()); // Kicker (5th card)
            } else if (valuePairs.get(0).getValue() == 3 && valuePairs.get(1).getValue() == 2) {
                // Full house - three of a kind plus a pair
                rank = FULL_HOUSE;
                tie.add(valuePairs.get(0).getKey()); // Value of the three cards
                tie.add(valuePairs.get(1).getKey()); // Value of the pair
            } else if (isFlush) {
                // Flush - all same suit but not straight
                rank = FLUSH;
                // All cards matter for tie-breaking, in descending order
                for (Card card : cards) {
                    tie.add(card.value);
                }
            } else if (isStraight) {
                // Straight - five consecutive values but not all same suit
                rank = STRAIGHT;
                tie.add(cards.get(0).value); // Highest card determines winner
            } else if (valuePairs.get(0).getValue() == 3) {
                // Three of a kind - three cards of same value
                rank = THREE_OF_A_KIND;
                tie.add(valuePairs.get(0).getKey());   // Value of the three cards
                // Add kickers (remaining cards) in descending order
                for (Map.Entry<Integer, Integer> entry : valuePairs) {
                    if (entry.getValue() == 1) {
                        tie.add(entry.getKey());
                    }
                }
            } else if (valuePairs.get(0).getValue() == 2 && valuePairs.get(1).getValue() == 2) {
                // Two pair - two different pairs
                rank = TWO_PAIR;
                // Ensure higher pair is compared first
                tie.add(Math.max(valuePairs.get(0).getKey(), valuePairs.get(1).getKey()));
                tie.add(Math.min(valuePairs.get(0).getKey(), valuePairs.get(1).getKey()));
                tie.add(valuePairs.get(2).getKey());
            } else if (valuePairs.get(0).getValue() == 2) {
                // A pair - two cards of same value
                rank = PAIR;
                tie.add(valuePairs.get(0).getKey());  // Value of the pair
                // Add kickers (remaining cards) in descending order
                for (Map.Entry<Integer, Integer> entry : valuePairs) {
                    if (entry.getValue() == 1) {
                        tie.add(entry.getKey());
                    }
                }
            } else {
                // High card - no pairs or better
                rank = HIGH_CARD;
                // All cards matter for tie-breaking, in descending order
                for (Card card : cards) {
                    tie.add(card.value);
                }
            }
        }
        
        /**
         * Checks if the cards form a straight (5 consecutive values).
         * Assumes cards are already sorted in descending order.
         * 
         * @param cards List of cards sorted by value (descending)
         * @return true if the cards form a straight, false otherwise
         */
        private boolean checkStraight(List<Card> cards) {
            for (int i = 0; i < cards.size() - 1; i++) {
                if (cards.get(i).value - cards.get(i + 1).value != 1) {
                    return false;
                }
            }
            return true;
        }
        /**
         * Compares this hand to another hand to determine the winner.
         * 
         * @param other The hand to compare against
         * @return positive if this hand wins, negative if other wins, 0 if tie
         */
        public int compare(Hand other) {
            // First compare by rank (higher rank wins)
            if (this.rank != other.rank) {
                return this.rank - other.rank;
            }
            
            // Comparison during a tie
            for (int i = 0; i < Math.min(this.tie.size(), other.tie.size()); i++) {
                if (!this.tie.get(i).equals(other.tie.get(i))) {
                    return this.tie.get(i) - other.tie.get(i);
                }
            }

            // Complete tie (very rare but possible)
            return 0;
        }
    }

     /**
     * Main method - reads poker hands from STDIN and outputs win counts.
     * 
     * Expected input format: Lines containing 10 cards (5 per player)
     * Example: "2H 3D 5S 9C KD 2C 3H 4S 8C AH"
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int player1WinCount = 0; // Track wins for player 1
        int player2WinCount = 0; // Track wins for player 2
        
        // Process each line of input until EOF
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

            // Compare hands  
            int result = handOne.compare(handTwo);
            if (result > 0) {
                player1WinCount++;
            } else if (result < 0) {
                player2WinCount++;
            }
        }
        scanner.close();

        // Output results
        System.out.println("Player 1: " + player1WinCount + " hands");
        System.out.println("Player 2: " + player2WinCount + " hands");
    }
}

