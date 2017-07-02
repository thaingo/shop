package io.github.tkaczenko.util;

import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by tkaczenko on 09.04.17.
 */
@Component
public final class UrlUtil {
    public static String encode(String url) throws UnsupportedEncodingException {
        return URLEncoder.encode(url, "UTF-8")
                .replace("+", "%20");
    }

    public static String decode(String url) throws UnsupportedEncodingException {
        return URLDecoder.decode(url, "UTF-8")
                .replace("%20", " ");
    }
}
