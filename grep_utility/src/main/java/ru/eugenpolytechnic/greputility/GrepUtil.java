package ru.eugenpolytechnic.greputility;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class GrepUtil {

    private final File doc;

    private final boolean regex;

    private final boolean invert;

    private final boolean ignore;

    private final String word;

    public GrepUtil(File doc, boolean regex, boolean invert, boolean ignore, String word) {
        this.doc = doc;
        this.regex = regex;
        this.invert = invert;
        this.ignore = ignore;
        this.word = word;
    }

    private boolean checkCondition(String line) {
        boolean result;
        if (regex) {
            Pattern p = Pattern.compile(word);
            Matcher m = p.matcher(line);
            result = m.find();
        } else {
            result = ignore ? line.toLowerCase().contains(word.toLowerCase()) : line.contains(word);
        }
        return invert != result;
    }

    public ArrayList<String> checkDoc() throws IOException {
        ArrayList<String> result = new ArrayList<>();
        try (FileReader fr = new FileReader(doc)) {
            try (BufferedReader reader = new BufferedReader(fr)) {
                String line = reader.readLine();
                while (line != null) {
                    if (checkCondition(line))
                        result.add(line);
                    line = reader.readLine();
                }
            }
        }
        return result;
    }
}
