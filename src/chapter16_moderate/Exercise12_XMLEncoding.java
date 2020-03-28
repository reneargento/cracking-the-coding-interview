package chapter16_moderate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 26/03/20.
 */
public class Exercise12_XMLEncoding {

    public static class Element {
        public String name;
        public String value;
        public List<Attribute> attributes;
        public List<Element> children;

        public Element(String name, String value) {
            this.name = name;
            this.value = value;
            attributes = new ArrayList<>();
            children = new ArrayList<>();
        }

        public Element(String name) {
            this(name, null);
        }

        public String getNameCode() {
            switch (name) {
                case "family": return "1";
                case "person": return "2";
                case "firstName": return "3";
                case "lastName": return "4";
                case "state": return "5";
            }
            return "--";
        }

        public void insert(Attribute attribute) {
            attributes.add(attribute);
        }

        public void insert(Element element) {
            children.add(element);
        }
    }

    public static class Attribute {
        public String tag;
        public String value;

        public Attribute(String tag, String value) {
            this.tag = tag;
            this.value = value;
        }

        public String getTagCode() {
            switch (tag) {
                case "family": return "1";
                case "person": return "2";
                case "firstName": return "3";
                case "lastName": return "4";
                case "state": return "5";
            }
            return "--";
        }
    }

    // O(e + a) runtime, where e is the number of elements and a is the number of attributes
    // O(e) space
    public static String encodeToString(Element root) {
        StringBuilder stringBuilder = new StringBuilder();
        encode(root, stringBuilder);
        return stringBuilder.toString();
    }

    private static void encode(Element root, StringBuilder stringBuilder) {
        encode(root.getNameCode(), stringBuilder);
        for (Attribute attribute : root.attributes) {
            encode(attribute, stringBuilder);
        }
        encodeEnd(stringBuilder);

        if (root.value != null && !root.value.equals("")) {
            encode(root.value, stringBuilder);
        } else {
            for (Element element : root.children) {
                encode(element, stringBuilder);
            }
        }
        encodeEnd(stringBuilder);
    }

    private static void encode(Attribute attribute, StringBuilder stringBuilder) {
        encode(attribute.getTagCode(), stringBuilder);
        encode(attribute.value, stringBuilder);
    }

    private static void encode(String value, StringBuilder stringBuilder) {
        value = value.replace("0", "\\0");
        stringBuilder.append(value);
        stringBuilder.append(" ");
    }

    private static void encodeEnd(StringBuilder stringBuilder) {
        stringBuilder.append("0");
        stringBuilder.append(" ");
    }

    public static void main(String[] args) {
        Element personElement = new Element("person", "Some Message");
        Attribute firstNameAttribute = new Attribute("firstName", "Gayle");
        personElement.insert(firstNameAttribute);

        Element familyElement = new Element("family");
        Attribute lastNameAttribute = new Attribute("lastName", "McDowell");
        Attribute stateAttribute = new Attribute("state", "CA");
        familyElement.insert(lastNameAttribute);
        familyElement.insert(stateAttribute);
        familyElement.insert(personElement);

        String encodedXML = encodeToString(familyElement);
        System.out.println("Encoded XML: " + encodedXML);
        System.out.printf("%12s %s", "Expected:", "1 4 McDowell 5 CA 0 2 3 Gayle 0 Some Message 0 0\n");
    }

}
