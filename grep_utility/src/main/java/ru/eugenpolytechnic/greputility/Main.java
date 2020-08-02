package ru.eugenpolytechnic.greputility;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    @Option(name = "-v", metaVar = "Invert", usage = "Invert the filter condition")
    private boolean invert;

    @Option(name = "-i", metaVar = "Ignore", usage = "Ignore case")
    private boolean ignore;

    @Option(name = "-r", metaVar = "Regex", usage = "Use regex instead of the word", forbids={"-i"})
    private boolean regex;

    @Argument(required = true, metaVar = "Word", usage = "Word or regex for searching")
    private String word;

    @Argument(required = true, metaVar = "TxtDoc", index = 1, usage = "Document to filter")
    private File doc;

    public static void main(String[] args) {
       new Main().launch(args);
    }

    private void launch(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("Command Line: [-v] [-i] [-r] word input_name.txt");
            parser.printUsage(System.err);
            return;
        }

        GrepUtil grep = new GrepUtil(doc, regex, invert, ignore, word);
        try {
            ArrayList<String> result = grep.checkDoc();
            for (String line: result) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
