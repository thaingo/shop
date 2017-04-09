package ua.org.javatraining.andrii_tkachenko.util;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by tkaczenko on 09.04.17.
 */
@Component
@Scope("request")
public final class URLUtil {
    public static String encode(String url) throws UnsupportedEncodingException {
        return URLEncoder.encode(url, "UTF-8")
                .replace("+", "%20");
    }

    public static String decode(String url) throws UnsupportedEncodingException {
        return URLDecoder.decode(url, "UTF-8")
                .replace("%20", " ");
    }
}
