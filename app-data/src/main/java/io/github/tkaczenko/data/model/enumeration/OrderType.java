package io.github.tkaczenko.data.model.enumeration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tkaczenko on 11.03.17.
 */
public enum OrderType {
    IN_PROGRESS(0),
    SUBMITTED(1),
    DELIVERED(2);

    private static final Map<Integer, OrderType> lookup = Collections.unmodifiableMap(initializeMapping());
    private int code;

    OrderType(int code) {
        this.code = code;
    }

    private static Map<Integer, OrderType> initializeMapping() {
        Map<Integer, OrderType> map = new HashMap<>();
        for (OrderType v : OrderType.values()) {
            map.put(v.getCode(), v);
        }
        return map;
    }

    public static OrderType get(int code) {
        return lookup.get(code);
    }

    public int getCode() {
        return code;
    }
}
