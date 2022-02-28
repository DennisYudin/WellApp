package dev.yudin.data;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

import java.util.Random;

@Log4j
@Component
public class DataGenerator implements Generator{
    private static final String PREFIX = "EQ-";
    private static final String NUMBERS_FOR_CHOOSE = "0123456789";
    private static final int AMOUNT_SYMBOLS = 4;

    private final Random random = new Random();

    @Override
    public String create() {
        log.debug("Call method create()");
        StringBuilder result = new StringBuilder();

        result.append(PREFIX);

        for (int i = 0; i < AMOUNT_SYMBOLS; i++) {
            int symbolSampleSize = NUMBERS_FOR_CHOOSE.length();
            int randomSymbol = random.nextInt(symbolSampleSize);
            char symbol = NUMBERS_FOR_CHOOSE.charAt(randomSymbol);

            result.append(symbol);
        }
        log.debug("Generated equipment name: " + result.toString());
        return result.toString();
    }
}
