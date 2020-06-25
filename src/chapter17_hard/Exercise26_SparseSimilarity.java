package chapter17_hard;

import util.HashMapList;

import java.util.*;

/**
 * Created by Rene Argento on 20/06/20.
 */
public class Exercise26_SparseSimilarity {

    public static class DocumentPair {
        private int id1;
        private int id2;

        public DocumentPair(int id1, int id2) {
            this.id1 = id1;
            this.id2 = id2;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null || getClass() != object.getClass()) {
                return false;
            }
            DocumentPair pair = (DocumentPair) object;
            return id1 == pair.id1 && id2 == pair.id2;
        }

        @Override
        public int hashCode() {
            return (id1 * 31) ^ id2;
        }
    }

    public static class Document {
        private int id;
        private int[] words;

        public Document(int id, int[] words) {
            this.id = id;
            this.words = words;
        }

        public int size() {
            return words != null ? words.length : 0;
        }
    }

    // O(w * d^2) runtime, where w is the number of distinct words and d is the number of documents.
    // Note that such runtime is unlikely to happen since the similarity between documents is sparse.
    // O(d^2) space, also unlikely to happen due to the sparse similarity.
    public static Map<DocumentPair, Double> computeDocumentSimilarities(List<Document> documents) {
        Map<Integer, Document> idToDocumentMap = getIdToDocumentMap(documents);
        HashMapList<Integer, Integer> wordToDocumentsMap = getWordToDocumentsMap(documents);

        Map<DocumentPair, Double> documentSimilarities = computeIntersections(wordToDocumentsMap);
        adjustIntersectionsToSimilarities(idToDocumentMap, documentSimilarities);
        return documentSimilarities;
    }

    private static Map<Integer, Document> getIdToDocumentMap(List<Document> documents) {
        Map<Integer, Document> idToDocumentMap = new HashMap<>();

        for (Document document : documents) {
            idToDocumentMap.put(document.id, document);
        }
        return idToDocumentMap;
    }

    private static HashMapList<Integer, Integer> getWordToDocumentsMap(List<Document> documents) {
        HashMapList<Integer, Integer> wordToDocumentsMap = new HashMapList<>();

        for (Document document : documents) {
            if (document.words == null) {
                continue;
            }

            for (int word : document.words) {
                wordToDocumentsMap.put(word, document.id);
            }
        }
        return wordToDocumentsMap;
    }

    private static Map<DocumentPair, Double> computeIntersections(HashMapList<Integer, Integer> wordToDocumentsMap) {
        Map<DocumentPair, Double> intersections = new HashMap<>();

        for (int word : wordToDocumentsMap.keySet()) {
            List<Integer> documentIds = wordToDocumentsMap.get(word);

            for (int i = 0; i < documentIds.size(); i++) {
                for (int j = i + 1; j < documentIds.size(); j++) {
                    incrementIntersection(intersections, documentIds.get(i), documentIds.get(j));
                }
            }
        }
        return intersections;
    }

    private static void incrementIntersection(Map<DocumentPair, Double> intersections, int documentId1, int documentId2) {
        int minDocumentId = Math.min(documentId1, documentId2);
        int maxDocumentId = Math.max(documentId1, documentId2);
        DocumentPair pair = new DocumentPair(minDocumentId, maxDocumentId);

        double currentIntersection = intersections.getOrDefault(pair, 0.0);
        intersections.put(pair, currentIntersection + 1.0);
    }

    private static void adjustIntersectionsToSimilarities(Map<Integer, Document> idToDocumentMap,
                                                          Map<DocumentPair, Double> similarities) {
        for (Map.Entry<DocumentPair, Double> entry : similarities.entrySet()) {
            DocumentPair documentPair = entry.getKey();
            double intersectionSize = entry.getValue();
            Document document1 = idToDocumentMap.get(documentPair.id1);
            Document document2 = idToDocumentMap.get(documentPair.id2);

            double unionSize = (double) document1.size() + document2.size() - intersectionSize;
            double similarity = intersectionSize / unionSize;
            entry.setValue(similarity);
        }
    }

    public static void main(String[] args) {
        int[] elements1 = {14, 15, 100, 9, 3};
        Document document1 = new Document(13, elements1);

        int[] elements2 = {32, 1, 9, 3, 5};
        Document document2 = new Document(16, elements2);

        int[] elements3 = {15, 29, 2, 6, 8, 7};
        Document document3 = new Document(19, elements3);

        int[] elements4 = {7, 10};
        Document document4 = new Document(24, elements4);

        List<Document> documents = new ArrayList<>();
        documents.add(document1);
        documents.add(document2);
        documents.add(document3);
        documents.add(document4);

        Map<DocumentPair, Double> documentSimilarities = computeDocumentSimilarities(documents);
        printDocumentSimilarities(documentSimilarities);

        System.out.println("\nExpected:");
        System.out.println("ID1,  ID2 : SIMILARITY");
        System.out.println(" 13,  19  : 0.1");
        System.out.println(" 13,  16  : 0.25");
        System.out.println(" 19,  24  : 0.14285714285714285");
    }

    private static void printDocumentSimilarities(Map<DocumentPair, Double> documentSimilarities) {
        System.out.printf("%4s %4s : %10s\n", "ID1,", "ID2", "SIMILARITY");

        for (Map.Entry<DocumentPair, Double> entry : documentSimilarities.entrySet()) {
            DocumentPair documentPair = entry.getKey();
            double similarity = entry.getValue();
            System.out.printf("%3d, %3d  : ", documentPair.id1, documentPair.id2);
            System.out.println(similarity);
        }
    }

}
