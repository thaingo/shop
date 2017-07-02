package io.github.tkaczenko.data.model.enumeration;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tkaczenko on 28.01.17.
 */
public enum VisualizationType {
    ORIGINAL_PICTURE(0),
    BIG_PICTURE(1),
    PICTURE(2);

    private static final Map<Integer, VisualizationType> lookup = new HashMap<>();

    static {
        for (VisualizationType v : EnumSet.allOf(VisualizationType.class))
            lookup.put(v.getCode(), v);
    }

    private int code;

    VisualizationType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static VisualizationType get(int code) {
        return lookup.get(code);
    }
}
