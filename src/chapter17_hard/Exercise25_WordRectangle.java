package chapter17_hard;

import java.util.*;

/**
 * Created by Rene Argento on 15/06/20.
 */
public class Exercise25_WordRectangle {

    private static class Trie {

        private class Node {
            char value;
            Map<Character, Node> children;
            boolean isWord;

            public Node(char value) {
                this.value = value;
                children = new HashMap<>();
            }
        }

        private Node root;

        public Trie() {
            root = new Node('*');
        }

        // O(l) runtime, where l is the length of the word
        public boolean containsSubstring(String word) {
            Node currentNode = root;

            for (int i = 0; i < word.length(); i++) {
                char character = word.charAt(i);
                currentNode = currentNode.children.get(character);

                if (currentNode == null) {
                    return false;
                }
            }
            return true;
        }

        // O(l) runtime, where l is the length of the word
        public void insert(String word) {
            insert(root, word, 0);
        }

        private void insert(Node node, String word, int index) {
            if (index == word.length()) {
                return;
            }

            char nextChar = word.charAt(index);
            Node nextNode = node.children.get(nextChar);

            if (nextNode == null) {
                nextNode = new Node(nextChar);
            }
            if (index == word.length() - 1) {
                nextNode.isWord = true;
            }

            insert(nextNode, word, index + 1);
            node.children.put(nextChar, nextNode);
        }
    }

    public static class WordGroup {
        private Set<String> wordSet;
        private List<String> wordList;

        public WordGroup() {
            wordSet = new HashSet<>();
            wordList = new ArrayList<>();
        }

        public void addWord(String word) {
            wordSet.add(word);
            wordList.add(word);
        }

        public boolean contains(String word) {
            return wordSet.contains(word);
        }

        public List<String> getWords() {
            return wordList;
        }
    }

    public static class Rectangle {
        private int height;
        private int length;
        private char[][] characters;

        public Rectangle(int length) {
            this.length = length;
        }

        public Rectangle(char[][] characters) {
            height = characters.length;
            length = characters[0].length;
            this.characters = characters;
        }

        public String getColumn(int columnIndex) {
            if (columnIndex >= characters[0].length) {
                return null;
            }

            StringBuilder column = new StringBuilder();
            for (char[] row : characters) {
                column.append(row[columnIndex]);
            }
            return column.toString();
        }

        public Rectangle append(String word) {
            if (word.length() != length) {
                return null;
            }

            char[][] newRectangle = new char[height + 1][length];

            for (int row = 0; row < height; row++) {
                System.arraycopy(characters[row], 0, newRectangle[row], 0, length);
            }
            word.getChars(0, length, newRectangle[height], 0);

            return new Rectangle(newRectangle);
        }

        public boolean isComplete(WordGroup wordGroup) {
            for (int columnIndex = 0; columnIndex < length; columnIndex++) {
                String column = getColumn(columnIndex);

                if (!wordGroup.contains(column)) {
                    return false;
                }
            }
            return true;
        }

        public boolean isPartialValid(Trie trie) {
            if (height == 0) {
                return true;
            }

            for (int columnIndex = 0; columnIndex < length; columnIndex++) {
                String column = getColumn(columnIndex);

                if (!trie.containsSubstring(column)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public String toString() {
            StringBuilder rectangle = new StringBuilder();

            for (int row = 0; row < height; row++) {
                for (int column = 0; column < length; column++) {
                    rectangle.append(characters[row][column]);
                }
                if (row != height - 1) {
                    rectangle.append("\n");
                }
            }
            return rectangle.toString();
        }
    }

    // Finds the rectangle of letters of largest area (height x length) such that every row forms a word (reading left
    // to right) and every column forms a word (reading top to bottom).
    // O(ml^3 * n^ml) runtime, where n is the number of words in the list and ml is the maximum length of a word in the
    // list
    // O(n^ml * ml^2) space
    public static Rectangle getWordRectangle(List<String> words) {
        WordGroup[] wordGroups = createWordGroups(words);
        int maxWordLength = wordGroups.length;
        int maxRectangleArea = maxWordLength * maxWordLength;

        Trie[] tries = new Trie[maxWordLength];

        // Try all pairs (length, height) for the rectangle dimensions.
        // The first rectangle found will be the max valid rectangle.
        for (int area = maxRectangleArea; area > 0; area--) {
            for (int length = 1; length <= maxWordLength; length++) {
                if (area % length == 0) {
                    int height = area / length;

                    if (height <= maxWordLength) {
                        Rectangle rectangle = makeRectangle(length, height, wordGroups, tries);
                        if (rectangle != null) {
                            return rectangle;
                        }
                    }
                }
            }
        }
        return null;
    }

    // Group words by length
    private static WordGroup[] createWordGroups(List<String> words) {
        int maxWordLength = 0;
        for (String word : words) {
            maxWordLength = Math.max(maxWordLength, word.length());
        }

        WordGroup[] wordGroups = new WordGroup[maxWordLength];

        for (String word : words) {
            int wordLength = word.length() - 1;
            if (wordGroups[wordLength] == null) {
                wordGroups[wordLength] = new WordGroup();
            }
            wordGroups[wordLength].addWord(word);
        }
        return wordGroups;
    }

    private static Rectangle makeRectangle(int length, int height, WordGroup[] wordGroups, Trie[] tries) {
        WordGroup wordGroupByLength = wordGroups[length - 1];
        WordGroup wordGroupByHeight = wordGroups[height - 1];
        if (wordGroupByLength == null || wordGroupByHeight == null) {
            return null;
        }

        if (tries[height - 1] == null) {
            Trie trie = new Trie();
            for (String word : wordGroupByHeight.getWords()) {
                trie.insert(word);
            }
            tries[height - 1] = trie;
        }

        Rectangle initialRectangle = new Rectangle(length);
        return makePartialRectangle(initialRectangle, height, wordGroups, tries);
    }

    // O(n^ml) runtime, where n is the number of words in the word group list and ml is the maximum length of a word in
    // the word group list
    private static Rectangle makePartialRectangle(Rectangle rectangle, int finalHeight, WordGroup[] wordGroups,
                                                  Trie[] tries) {
        if (rectangle.height == finalHeight) {
            WordGroup wordGroupByHeight = wordGroups[finalHeight - 1];
            if (rectangle.isComplete(wordGroupByHeight)) {
                return rectangle;
            }
            return null;
        }

        // Check if all columns are valid prefixes
        if (!rectangle.isPartialValid(tries[finalHeight - 1])) {
            return null;
        }

        // Go through each word of the right length, append it to the partial rectangle and attempt to build a rectangle
        // recursively.
        WordGroup wordGroupByLength = wordGroups[rectangle.length - 1];

        for (String word : wordGroupByLength.getWords()) {
            Rectangle rectangleWithAppendedWord = rectangle.append(word);
            Rectangle wordRectangle = makePartialRectangle(rectangleWithAppendedWord, finalHeight, wordGroups, tries);

            if (wordRectangle != null) {
                return wordRectangle;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Rectangle wordRectangle1 = getWordRectangle(getListOfWords1());
        System.out.println("Word rectangle:\n" + wordRectangle1);
        System.out.println("\nExpected:\nATR\nBEE\nSSN\nTTE");

        Rectangle wordRectangle2 = getWordRectangle(getListOfWords2());
        System.out.println("\nWord rectangle:\n" + wordRectangle2);
        System.out.println("\nExpected:\nheart\nember\nabuse\nresin\ntrend\nssdsy");
    }

    private static List<String> getListOfWords1() {
        String[] words = {"RENE", "ABCDE", "TEST", "ABC", "ABST", "12345", "1234", "ATR", "BEE", "SSN",
                "TTE", "ALGORITHMS", "STRING"};
        return Arrays.asList(words);
    }

    private static List<String> getListOfWords2() {
        String[] words = { "the", "of", "and", "a", "to", "in", "is", "be",
                "that", "was", "world", "awesome", "he", "for", "it", "with",
                "as", "his", "I", "on", "have", "at", "by", "not", "surely",
                "they", "this", "attract", "computer", "had", "are", "but",
                "from", "or", "she", "an", "which", "you", "one", "we", "all",
                "were", "her", "would", "there", "their", "will", "when",
                "who", "him", "been", "has", "more", "if", "no", "out", "do",
                "so", "can", "what", "up", "said", "about", "other", "into",
                "than", "its", "time", "only", "could", "new", "them", "man",
                "some", "these", "then", "two", "first", "May", "any", "like",
                "now", "my", "such", "make", "over", "our", "even", "most",
                "me", "state", "after", "also", "made", "many", "did", "must",
                "before", "back", "see", "through", "way", "where", "get",
                "much", "go", "well", "your", "know", "should", "down", "work",
                "year", "because", "come", "people", "just", "say", "each",
                "those", "take", "day", "good", "how", "long", "Mr", "own",
                "too", "little", "use", "us", "very", "great", "still", "men",
                "here", "life", "both", "between", "old", "under", "last",
                "never", "place", "same", "another", "think", "abuse", "house",
                "while", "high", "right", "might", "came", "off", "find",
                "states", "since", "used", "give", "against", "three",
                "himself", "look", "few", "general", "heart", "hand", "school",
                "resin", "part", "small", "American", "home", "during",
                "number", "again", "Mrs", "around", "thought", "went",
                "without", "however", "govern", "don't", "does", "got",
                "public", "United", "point", "end", "become", "head", "once",
                "course", "fact", "upon", "need", "system", "set", "every",
                "trend", "war", "put", "form", "water", "took", "program",
                "present", "government", "thing", "told", "possible", "group",
                "large", "until", "always", "city", "didn't", "order", "away",
                "called", "want", "eyes", "something", "unite", "going",
                "face", "far", "asked", "interest", "later", "show", "knew",
                "though", "less", "night", "early", "almost", "let", "open",
                "enough", "side", "case", "days", "yet", "better", "nothing",
                "tell", "problem", "toward", "given", "why", "national",
                "room", "young", "social", "light", "business", "president",
                "help", "power", "country", "next", "things", "word", "looked",
                "real", "John", "line", "second", "church", "seem", "certain",
                "big", "Four", "felt", "several", "children", "service",
                "feel", "important", "rather", "name", "per", "among", "often",
                "turn", "development", "computer", "keep", "family", "seemed", "white",
                "company", "mind", "members", "others", "within", "done",
                "along", "turned", "god", "sense", "week", "best", "change",
                "kind", "began", "child", "ever", "law", "matter", "least",
                "means", "question", "act", "close", "mean", "leave", "itself",
                "force", "study", "York", "action", "it's", "door",
                "experience", "human", "result", "times", "run", "different",
                "car", "example", "hands", "whole", "center", "although",
                "call", "Five", "inform", "gave", "plan", "woman", "boy",
                "feet", "provide", "taken", "thus", "body", "play", "seen",
                "today", "having", "cost", "perhaps", "field", "local",
                "really", "am", "increase", "reason", "themselves", "clear",
                "I'm", "information", "figure", "late", "above", "history",
                "love", "girl", "held", "special", "move", "person", "whether",
                "college", "sure", "probably", "either", "seems", "cannot",
                "art", "free", "across", "death", "quite", "street", "value",
                "anything", "making", "past", "brought", "moment", "control",
                "office", "heard", "problems", "became", "full", "near",
                "half", "nature", "hold", "live", "available", "known",
                "board", "effect", "already", "Economic", "money", "position",
                "believe", "age", "together", "shall", "TRUE", "political",
                "court", "report", "level", "rate", "air", "pay", "community",
                "complete", "music", "necessary", "society", "behind", "type",
                "read", "idea", "wanted", "land", "party", "class", "organize",
                "return", "department", "education", "following", "mother",
                "sound", "ago", "nation", "voice", "six", "bring", "wife",
                "common", "south", "strong", "town", "book", "students",
                "hear", "hope", "able", "industry", "stand", "tax", "west",
                "meet", "particular", "cut", "short", "stood", "university",
                "spirit", "start", "total", "future", "front", "low",
                "century", "Washington", "usually", "care", "recent",
                "evidence", "further", "million", "simple", "road",
                "sometimes", "support", "view", "fire", "says", "hard",
                "morning", "table", "left", "situation", "try", "outside",
                "lines", "surface", "ask", "modern", "top", "peace",
                "personal", "member", "minutes", "lead", "schools", "talk",
                "consider", "gone", "soon", "father", "ground", "living",
                "months", "therefore", "America", "started", "longer", "Dr",
                "dark", "various", "finally", "hour", "north", "third", "fall",
                "greater", "pressure", "stage", "expected", "secretary",
                "needed", "That's", "kept", "eye", "values", "union",
                "private", "alone", "black", "required", "space", "subject",
                "english", "month", "understand", "I'll", "nor", "answer",
                "moved", "amount", "conditions", "direct", "red", "student",
                "rest", "nations", "heart", "costs", "record", "picture",
                "taking", "couldn't", "hours", "deal", "forces", "everything",
                "write", "coming", "effort", "market", "island", "wall",
                "purpose", "basis", "east", "lost", "St", "except", "letter",
                "looking", "property", "Miles", "difference", "entire", "else",
                "color", "followed", "feeling", "son", "makes", "friend",
                "basic", "cold", "including", "single", "attention", "note",
                "cause", "hundred", "step", "paper", "developed", "tried",
                "simply", "can't", "story", "committee", "inside", "reached",
                "easy", "appear", "include", "accord", "Actually", "remember",
                "beyond", "dead", "shown", "fine", "religious", "continue",
                "ten", "defense", "getting", "Central", "beginning", "instead",
                "river", "received", "doing", "employ", "trade", "terms",
                "trying", "friends", "sort", "administration", "higher",
                "cent", "expect", "food", "building", "religion", "meeting",
                "ready", "walked", "follow", "earth", "speak", "passed",
                "foreign", "NATURAL", "medical", "training", "County", "list",
                "floor", "piece", "especially", "indeed", "stop", "wasn't",
                "England", "difficult", "likely", "Suddenly", "moral", "plant",
                "bad", "club", "needs", "international", "working",
                "countries", "develop", "drive", "reach", "police", "sat",
                "charge", "farm", "fear", "test", "determine", "hair",
                "results", "stock", "trouble", "happened", "growth", "square",
                "William", "cases", "effective", "serve", "miss", "involved",
                "doctor", "earlier", "increased", "being", "blue", "hall",
                "particularly", "boys", "paid", "sent", "production",
                "district", "using", "thinking", "concern", "Christian",
                "press", "girls", "wide", "usual", "direction", "feed",
                "trial", "walk", "begin", "weeks", "points", "respect",
                "certainly", "ideas", "industrial", "methods", "operation",
                "addition", "association", "combine", "knowledge", "decided",
                "temperature", "statement", "Yes", "below", "game", "nearly",
                "science", "directly", "horse", "influence", "size", "showed",
                "build", "throughout", "questions", "character", "foot",
                "Kennedy", "firm", "reading", "husband", "doubt", "services",
                "according", "lay", "stay", "programs", "anyone", "average",
                "French", "spring", "former", "summer", "bill", "lot",
                "chance", "due", "comes", "army", "actual", "Southern",
                "neither", "relate", "rise", "evening", "normal", "wish",
                "visit", "population", "remain", "measure", "merely",
                "arrange", "condition", "decision", "account", "opportunity",
                "pass", "demand", "strength", "window", "active", "deep",
                "degree", "ran", "western", "E", "sales", "continued", "fight",
                "heavy", "arm", "standard", "generally", "carry", "hot",
                "provided", "serious", "led", "wait", "hotel", "opened",
                "performance", "maybe", "station", "changes", "literature",
                "marry", "claim", "works", "bed", "wrong", "main", "unit",
                "George", "hit", "planning", "supply", "systems", "add",
                "chief", "officer", "Soviet", "pattern", "stopped", "price",
                "success", "lack", "myself", "truth", "freedom", "manner",
                "quality", "gun", "manufacture", "clearly", "share",
                "movement", "length", "ways", "burn", "forms", "Organization",
                "break", "somewhat", "efforts", "cover", "meaning", "progress",
                "treatment", "beautiful", "placed", "happy", "attack",
                "apparently", "blood", "groups", "carried", "sign", "radio",
                "dance", "I've", "regard", "man's", "train", "herself",
                "numbers", "corner", "REACTION", "immediately", "language",
                "running", "recently", "shake", "larger", "lower", "machine",
                "attempt", "learn", "couple", "race", "audience", "Oh",
                "middle", "brown", "date", "health", "persons",
                "understanding", "arms", "daily", "suppose", "additional",
                "hospital", "pool", "technical", "served", "declare",
                "described", "current", "poor", "steps", "reported", "sun",
                "based", "produce", "determined", "receive", "park", "staff",
                "faith", "responsibility", "Europe", "latter", "British",
                "season", "equal", "learned", "practice", "green", "writing",
                "ones", "choice", "fiscal", "term", "watch", "scene",
                "activity", "product", "types", "ball", "heat", "clothe",
                "lived", "distance", "parent", "letters", "returned",
                "forward", "obtained", "offer", "specific", "straight", "fix",
                "division", "slowly", "shot", "poet", "seven", "moving",
                "mass", "plane", "proper", "propose", "drink", "obviously",
                "plans", "whatever", "afternoon", "figures", "parts",
                "approve", "saying", "born", "immediate", "fame", "gives",
                "extent", "justice", "cars", "mark", "pretty", "opinion",
                "ahead", "glass", "refuse", "enter", "completely", "send",
                "desire", "judge", "none", "waiting", "popular", "Democratic",
                "film", "mouth", "Corps", "importance", "touch", "director",
                "ship", "there's", "council", "EFFECTS", "event", "worth",
                "existence", "designed", "hardly", "indicated", "analysis",
                "established", "products", "growing", "patient", "rule",
                "bridge", "pain", "base", "check", "cities", "elements",
                "leaders", "discussion", "limited", "sit", "Thomas",
                "agreement", "gas", "factors", "marriage", "easily", "closed",
                "excite", "accept", "applied", "allow", "bit", "married",
                "oil", "Rhode", "shape", "interested", "strange", "compose",
                "professional", "remained", "news", "Despite", "beauty",
                "responsible", "wonder", "spent", "tear", "unless", "eight",
                "permit", "covered", "Negro", "played", "I'd", "vote",
                "balance", "Charles", "loss", "Commission", "original", "fair",
                "reasons", "studies", "exactly", "built", "behavior", "enemy",
                "teeth", "bank", "die", "James", "relations", "weight",
                "prepared", "related", "sea", "bar", "warn", "post", "trees",
                "official", "separate", "clay", "Sunday", "raised", "events",
                "thin", "dropped", "cattle", "invite", "playing", "prevent",
                "detail", "standing", "grow", "places", "someone", "bright",
                "Talking", "meant", "print", "capital", "happen", "sides",
                "everyone", "facilities", "filled", "lip", "essential",
                "techniques", "June", "knows", "stain", "hadn't", "dinner",
                "dog", "dollars", "caught", "shout", "buy", "divide",
                "entered", "Chicago", "speed", "jazz", "appoint", "governor",
                "institutions", "fit", "materials", "sight", "store",
                "dependence", "explain", "gain", "he'd", "leadership", "quiet",
                "realize", "parents", "Communist", "neighbor", "round",
                "included", "kitchen", "thousand", "Christ", "isn't",
                "radiation", "broad", "stops", "failure", "retire", "election",
                "primary", "king", "books", "command", "edge", "ember",
                "March", "sitting", "conference", "bottom", "lady", "advise",
                "churches", "model", "battle", "giving", "sport", "address",
                "considerable", "spread", "funds", "trip", "youth",
                "CONSTRUCTION", "rock", "regular", "changed", "boat", "memory",
                "successful", "captain", "hell", "brother", "murder", "quick",
                "moreover", "highly", "difficulty", "inch", "saw", "clean",
                "collect", "camp", "experiment", "shows", "Authority", "older",
                "lord", "variety", "material", "frame", "distinguish",
                "scientific", "introduce", "principal", "Jack", "kill",
                "collection", "fell", "entertain", "pieces", "management",
                "otherwise", "security", "danger", "entirely", "civil",
                "frequently", "advertise", "records", "secret", "title",
                "impossible", "yesterday", "fast", "Mike", "produced", "favor",
                "noted", "caused", "lose", "purposes", "solid", "song",
                "corporation", "lie", "winter", "dress", "electric", "key",
                "dry", "reduce", "fresh", "goes", "hill", "names", "slow",
                "quickly", "telephone", "threaten", "oppose", "deliver",
                "officers", "expression", "published", "famous", "pray",
                "adopt", "London", "clothes", "laws", "citizens", "announced",
                "minute", "master", "sharp", "advantage", "greatest",
                "relation", "Mary", "leaving", "gray", "manager", "animal",
                "object", "bottle", "draw", "honor", "recognize", "drop",
                "intend", "relationship", "opposite", "sources", "poetry",
                "ability", "assistance", "operating", "bear", "join", "climb",
                "companies", "exist", "fixed", "gradual", "possibility",
                "hunt", "spoke", "satisfy", "units", "neck", "sleep",
                "doesn't", "finished", "carefully", "facts", "nice",
                "practical", "save", "takes", "allowed", "wine", "remind",
                "rich", "financial", "dream", "knife", "stations", "civilize",
                "Rose", "cool", "died", "thick", "imagine", "literary", "bind",
                "inches", "earn", "familiar", "seeing", "distribution",
                "marked", "coffee", "rules", "slip", "apply", "page", "beside",
                "daughter", "Relatively", "classes", "explore", "stated",
                "German", "musical", "smile", "significant", "block", "mix",
                "reports", "PROPOSED", "shelter", "presence", "Affairs",
                "named", "ordinary", "circumstances", "mile", "sweep",
                "remains", "admire", "Catholic", "dust", "operations", "rain",
                "tree", "nobody", "Henry", "Robert", "village", "advance",
                "offered", "agree", "mechanic", "upper", "occasion",
                "requirements", "capacity", "appears", "travel", "article",
                "houses", "valley", "beat", "opening", "box", "evil",
                "succeed", "surround", "application", "slightly", "remembered",
                "interests", "warm", "subjects", "search", "presented", "shoe",
                "sweet", "interesting", "membership", "suggest", "notice",
                "connection", "extreme", "exchange", "flow", "spend",
                "everybody", "poems", "campaign", "win", "forced", "freeze",
                "nine", "eat", "newspaper", "please", "escape", "lives",
                "swim", "file", "wind", "provides", "shop", "apartment",
                "fashion", "reasonable", "created", "Germany", "watched",
                "cells", "session", "somehow", "fully", "whose", "teacher",
                "raise", "recognized", "unity", "Providence", "reference",
                "explained", "twenty", "Russian", "features", "shoulder",
                "sir", "forest", "studied", "Sam", "signal", "chair",
                "reduced", "procedure", "forth", "limit", "disturb",
                "universe", "mentioned", "pick", "reality", "differences",
                "soft", "traditional", "Mission", "flat", "looks", "picked",
                "weather", "smaller", "leg", "chairman", "ancient", "narrow",
                "fellow", "twist", "belief", "excellent", "rights",
                "vocational", "laid", "politics", "fill", "response",
                "struggle", "disappear", "prove", "duty", "FOLLOWS", "editor",
                "welcome", "anode", "possess", "hearing", "BUILDINGS", "ideal",
                "scientist", "formed", "watching", "circle", "ought", "garden",
                "library", "accuse", "message", "slight", "junior", "knock",
                "empty", "protection", "treated", "birth", "expressed",
                "planned", "choose", "confuse", "Virginia", "killed",
                "frighten", "stayed", "worry", "surprise", "aside",
                "photograph", "removed", "turning", "Jr", "pull", "personnel",
                "agency", "pointed", "speech", "listen", "November", "sample",
                "Louis", "motor", "selected", "Berlin", "CLAIMS", "spot",
                "strike", "increasing", "exercise", "handle", "hole", "Leader",
                "baby", "ride", "avoid", "cross", "twice", "commercial",
                "failed", "prompt", "fat", "fourth", "visitor", "interior",
                "Jewish", "wing", "desk", "faculty", "forget", "operate",
                "stair", "besides", "relief", "standards", "France", "perfect",
                "pour", "Nevertheless", "brief", "Jones", "kick", "attend",
                "plus", "solution", "wage", "individuals", "powers",
                "minister", "taste", "discovered", "pulled", "hire", "writer",
                "verb", "preach", "friendly", "observed", "fan", "connect",
                "Fig", "count", "egg", "items", "mention", "Texas",
                "calculate", "platform", "drag", "mere", "tomorrow", "faces",
                "pure", "fighting", "resources", "increases", "assumed",
                "broke", "coast", "strict", "whom", "Russia", "qualify",
                "Morgan", "victory", "fields", "pleasure", "contain", "fold",
                "review", "April", "teach", "Richard", "whisper", "chosen",
                "metal", "PRINCIPLES", "competition", "railroad", "safe",
                "proved", "carrying", "horses", "kiss", "Mercer", "wheel",
                "sail", "wants", "compared", "relieve", "approximately",
                "wood", "historical", "persuade", "smiled", "crowd", "motion",
                "shore", "suit", "calls", "seat", "deserve", "San", "snow",
                "double", "educational", "neighborhood", "relative",
                "teachers", "Independent", "puzzle", "nose", "dogs", "waited",
                "naturally", "stone", "origin", "Rome", "wild", "scale",
                "tremble", "drawn", "guess", "communism", "absence", "roof",
                "sections", "sky", "walls", "Aircraft", "complain",
                "independence", "busy", "elect", "revolution", "roar",
                "willing", "League", "mine", "nurse", "liberal", "completed",
                "poem", "dollar", "ordered", "levels", "ton", "settled",
                "allowance", "bitter", "realized", "let's", "moon",
                "sensitive", "servant", "hunger", "China", "sale",
                "appearance", "lips", "policies", "actions", "strengthen",
                "Monday", "onto", "directed", "leading", "machinery",
                "theater", "Paris", "Frank", "somewhere", "Statements", "mill",
                "projects", "starting", "hat", "ruin", "depend", "stands",
                "signs", "families", "stir", "Khrushchev", "largely", "punish",
                "drew", "breathe", "amuse", "characteristic", "electronic",
                "pale", "pictures", "destroy", "expense", "somebody",
                "completion", "disappoint", "fifty", "found", "soil", "flame",
                "enjoy", "bless", "emotional", "promise", "she'd", "wave",
                "commerce", "Jury", "bay", "tempt", "correct", "asking",
                "content", "estimated", "conscious", "shine", "teaching",
                "catch", "dish", "Saturday", "greet", "background", "flood",
                "insect", "worse", "yellow", "occurred", "afraid", "ceremony",
                "decrease", "trust", "yourself", "legs", "you've",
                "communication", "describe", "sincere", "decide", "leaf",
                "encourage", "rub", "declared", "cry", "bite", "July", "lung",
                "significance", "helped", "gross", "apart", "disease",
                "issues", "scratch", "dictionary", "risk", "broadcast", "drum",
                "representative", "uncle", "cutting", "Jesus", "neglect",
                "depth", "substantial", "GETS", "adventure", "beg", "entrance",
                "plays", "throw", "ends", "Arts", "alive", "confidence",
                "intellectual", "cheer", "properties", "experiments", "nut",
                "plenty", "beneath", "closely", "description", "melt", "swear",
                "tall", "loose", "area", "bury", "measured", "request",
                "ourselves", "stream", "wipe", "band", "fingers", "creature",
                "Hanover", "attorney", "load", "passing", "billion", "earnest",
                "discussed", "translate", "achievement", "headquarters",
                "inquiry", "rapidly", "express", "hesitate", "guard", "jobs",
                "borrow", "owe", "Phil", "California", "ambition", "supposed",
                "lake", "they're", "slope", "Typical", "spite", "wore", "dear",
                "employees", "map", "pair", "spin", "one's", "praise",
                "imagination", "hung", "instrument", "plow", "holding",
                "objects", "straighten", "dominant", "scarce", "ring",
                "matters", "creep", "plain", "resolution", "credit", "period",
                "improve", "maintenance", "seize", "Laos", "we'll", "dozen",
                "located", "dig", "towards", "curse", "major", "breath",
                "weigh", "comfort", "federal", "guests", "priest", "sell",
                "bodies", "female", "primarily", "cousin", "grew", "spiritual",
                "dine", "engine", "politician", "custom", "educate",
                "individual", "job", "Tom", "cook", "grass", "mail",
                "salesman", "nail", "tap", "wet", "bedroom", "sufficient",
                "chest", "dramatic", "silence", "behave", "breakfast",
                "sudden", "passage", "scatter", "objection", "unusual",
                "argument", "policy", "powerful", "throat", "formal", "rapid",
                "Parker", "wrap", "luck", "grind", "rifle", "HIGHEST", "loan",
                "represent", "skill", "spell", "broken", "arch", "angle",
                "sick", "swell", "blind", "Contemporary", "engineer",
                "military", "boundary", "location", "homes", "boil",
                "officials", "operator", "Senate", "lend", "hearts", "embers", "abused", "resins", "trendy", "ssdsy" };
        return Arrays.asList(words);
    }

}
