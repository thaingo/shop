package io.github.tkaczenko.util;

import static org.apache.commons.lang3.StringUtils.substringAfterLast;
import static org.apache.commons.lang3.StringUtils.substringBeforeLast;

public final class ProductUtil {
    public static String makeSkuWithUrl(String url) {
        return TextCleaner.normalizeUid(extractSkuFromUrl(url));
    }

    private static String extractSkuFromUrl(String url) {
        String jobId = url;
        if (jobId.endsWith("/")) {
            jobId = substringBeforeLast(jobId, "/");
        }
        if (jobId.contains("/")) {
            jobId = substringAfterLast(jobId, "/");
        }
        if (jobId.contains(".")) {
            jobId = substringBeforeLast(jobId, ".");
        }
        return jobId;
    }

}
