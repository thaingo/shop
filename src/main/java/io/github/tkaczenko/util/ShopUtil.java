package io.github.tkaczenko.util;

import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Created by tkaczenko on 02.07.17.
 */
@Component
public final class ShopUtil {
    public static String generateRandomString(Random random, int length) {
        return random.ints(48, 122)
                .filter(i -> (i < 57 || i > 65) && (i < 90 || i > 97))
                .mapToObj(i -> (char) i)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
}
