package ru.eugenpolytechnic.greputility;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GrepUtilTest {

    private PrintStream stdStream = System.out;
    private ByteArrayOutputStream output = new ByteArrayOutputStream();
    private String ls = System.lineSeparator();
    private Path testFile = Paths.get("src","test", "resources", "test.txt");
    private String fileAddress = testFile.toString();

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(output));
    }

    @AfterEach
    public void  setStreamToDefault() {
        System.setOut(stdStream);
    }

    @Test
    public void grepBasic() {
        Main.main(new String[]{"hello", fileAddress});
        assertEquals("hello hello hello" + ls
                + "hello" + ls
                + "helloworld" + ls, output.toString());
    }

    @Test
    public void grepInvertBasic() {
        Main.main(new String[]{"-v", "hello", fileAddress});
        assertEquals("Hello"+ ls
                + "HELLO" + ls
                + "hELLO HEllo" + ls
                + "private" + ls
                + "boolean interactive" + ls, output.toString());
    }

    @Test
    public void grepIgnoreBasic() {
        Main.main(new String[]{"-i", "hello", fileAddress});
        assertEquals("hello hello hello"+ ls
                + "hello" + ls
                + "Hello" + ls
                + "HELLO" + ls
                + "hELLO HEllo" + ls
                + "helloworld" + ls, output.toString());
    }

    @Test
    public void grepInvertIgnoreBasic() {
        Main.main(new String[]{"-v", "-i", "hello", fileAddress});
        assertEquals("private"+ ls
                + "boolean interactive" + ls, output.toString());
    }

    @Test
    public void grepRegex() {
        Main.main(new String[]{"-r", "hello", fileAddress});
        assertEquals("hello hello hello" + ls
                + "hello" + ls
                + "helloworld" + ls, output.toString());
    }

    @Test
    public void grepRegex2() {
        Main.main(new String[]{"-r", "(hello ?){2,}", fileAddress});
        assertEquals("hello hello hello" + ls, output.toString());
    }

    @Test
    public void grepRegex3() {
        Main.main(new String[]{"-r", "[Hh]ello", fileAddress});
        assertEquals("hello hello hello" + ls
                + "hello" + ls
                + "Hello" + ls
                + "helloworld" + ls, output.toString());
    }

    @Test
    public void grepInvertRegex() {
        Main.main(new String[]{"-v", "-r", "[Hh]ello", fileAddress});
        assertEquals("HELLO" + ls
                + "hELLO HEllo" + ls
                + "private" + ls
                + "boolean interactive" + ls, output.toString());
    }

    @Test
    public void empty() {
        Main.main(new String[]{"qwerty", fileAddress});
        assertEquals("", output.toString());
    }
}

