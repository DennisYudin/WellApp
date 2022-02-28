package dev.yudin.data;

import dev.yudin.config.AppConfigTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfigTest.class)
class InputResolverTest {

    @Autowired
    private Resolver inputResolver;

    @Test
    void convert_ShouldReturnListOfWellNames_WhenInputIsSentence() {

        String input = "Well-1, Well-2";
        List<String> expected = new ArrayList<>(Arrays.asList("Well-1", "Well-2"));
        List<String> actual = inputResolver.convert(input);

        assertTrue(expected.containsAll(actual));
    }

    @Test
    void convert_ShouldReturnListOfWellNames_WhenInputIsSentenceWithCommaWithoutAnyWhiteSpaces() {

        String input = "Well-1,Well-2";
        List<String> expected = new ArrayList<>(Arrays.asList("Well-1", "Well-2"));
        List<String> actual = inputResolver.convert(input);

        assertTrue(expected.containsAll(actual));
    }

    @Test
    void convert_ShouldReturnListOfWellNames_WhenInputIsSentenceWithWhiteSpaces() {

        String input = " Well-1,   Well-2 ";
        List<String> expected = new ArrayList<>(Arrays.asList("Well-1", "Well-2"));
        List<String> actual = inputResolver.convert(input);

        assertTrue(expected.containsAll(actual));
    }

    @Test
    void convert_ShouldReturnListOfWellNames_WhenInputIsSentenceWithWhiteSpacesWithoutComma() {

        String input = " Well-1 Well-2 ";
        List<String> expected = new ArrayList<>(Arrays.asList("Well-1", "Well-2"));
        List<String> actual = inputResolver.convert(input);

        assertTrue(expected.containsAll(actual));
    }

    @Test
    void convert_ShouldReturnListOfWellNames_WhenInputIsSentenceWithThreeWords() {

        String input = "Well-1, Well-2, Well-3";
        List<String> expected = new ArrayList<>(Arrays.asList("Well-1", "Well-2", "Well-3"));
        List<String> actual = inputResolver.convert(input);

        assertTrue(expected.containsAll(actual));
    }
}
