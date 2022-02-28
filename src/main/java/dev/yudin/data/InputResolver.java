package dev.yudin.data;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Log4j
@Component
public class InputResolver implements Resolver {
    private static final String TARGET_SYMBOL = ",";
    private static final String REPLACEMENT_SYMBOL = " ";
    private static final String WHITESPACE_SYMBOL = " ";

    @Override
    public List<String> convert(String input) {
        log.debug("Call method convert()");

        List<String> result = new ArrayList<>();
        StringBuilder sentence = new StringBuilder();

        String replacedCommaInput = input.replace(TARGET_SYMBOL, REPLACEMENT_SYMBOL);

        char[] symbols = replacedCommaInput.trim().toCharArray();

        for (char symbol : symbols) {
            if (!Character.isWhitespace(symbol)) {
                sentence.append(symbol);
            } else {
                String lastSymbolInSentence = String.valueOf(sentence.charAt(sentence.length() - 1));
                if (!lastSymbolInSentence.equals(WHITESPACE_SYMBOL)) {
                    sentence.append(symbol);
                }
            }
        }
        String[] names = sentence.toString().split(WHITESPACE_SYMBOL);
        result.addAll(Arrays.asList(names));

        if (log.isDebugEnabled()) {
            log.debug("Well names are " + result);
        }
        return result;
    }
}
