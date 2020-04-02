package chapter16_moderate;

/**
 * Created by Rene Argento on 30/03/20.
 */
public class Exercise15_MasterMind {

    public static class Result {
        public int hits;
        public int pseudoHits;

        public Result(int hits, int pseudoHits) {
            this.hits = hits;
            this.pseudoHits = pseudoHits;
        }
    }

    private static final int RED = 0;
    private static final int YELLOW = 1;
    private static final int GREEN = 2;
    private static final int BLUE = 3;

    private static final int MAX_COLORS = 4;

    // O(n) runtime, where n is the number of computer slots
    // O(c) space, where c is the number of colors
    public static Result computeHits(String guess, String solution) {
        if (guess.length() != solution.length()) {
            return null;
        }

        int hits = 0;
        int pseudoHits = 0;
        int[] unmatchedGuess = new int[MAX_COLORS];
        int[] unmatchedSolution = new int[MAX_COLORS];

        for (int i = 0; i < guess.length(); i++) {
            char guessColor = guess.charAt(i);
            char solutionColor = solution.charAt(i);

            if (guessColor == solutionColor) {
                hits++;
            } else {
                updateColorArray(unmatchedGuess, guessColor);
                updateColorArray(unmatchedSolution, solutionColor);
            }
        }

        for (int i = 0; i < MAX_COLORS; i++) {
            pseudoHits += Math.min(unmatchedGuess[i], unmatchedSolution[i]);
        }
        return new Result(hits, pseudoHits);
    }

    private static void updateColorArray(int[] colorArray, char color) {
        switch (color) {
            case 'R': colorArray[RED]++; break;
            case 'Y': colorArray[YELLOW]++; break;
            case 'G': colorArray[GREEN]++; break;
            case 'B': colorArray[BLUE]++; break;
        }
    }

    public static void main(String[] args) {
        String guess1 = "GGRR";
        String solution1 = "RGBY";
        test(guess1, solution1, new Result(1, 1));

        String guess2 = "GRRR";
        String solution2 = "RRRR";
        test(guess2, solution2, new Result(3, 0));

        String guess3 = "BYRG";
        String solution3 = "GRYB";
        test(guess3, solution3, new Result(0, 4));

        String guess4 = "RYGB";
        String solution4 = "RYGB";
        test(guess4, solution4, new Result(4, 0));

        String guess5 = "GGGG";
        String solution5 = "YYYY";
        test(guess5, solution5, new Result(0, 0));

        String guess6 = "GGRR";
        String solution6 = "RRGG";
        test(guess6, solution6, new Result(0, 4));

        String guess7 = "GGRR";
        String solution7 = "RGBYY";
        test(guess7, solution7, null);

        String guess8 = "BBYG";
        String solution8 = "GBYB";
        test(guess8, solution8, new Result(2, 2));
    }

    private static void test(String guess, String solution, Result expectedResult) {
        Result result = computeHits(guess, solution);
        if (result != null) {
            System.out.println("Hits: " + result.hits + " Pseudo-hits: " + result.pseudoHits);
        } else {
            System.out.println("Guess and solution have different lengths");
        }

        if (expectedResult != null) {
            System.out.println("Expected: Hits: " + expectedResult.hits + " Pseudo-hits: " + expectedResult.pseudoHits + "\n");
        } else {
            System.out.println("Expected: Guess and solution have different lengths\n");
        }
    }

}
