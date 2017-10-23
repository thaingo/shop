package io.github.tkaczenko.session;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tkaczenko on 17.04.17.
 */
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "session")
public class LikedCart {
    private Map<String, Item> items = new HashMap<>(0);

    public Map<String, Item> getItems() {
        return Collections.unmodifiableMap(items);
    }

    public void addItem(String id, Item item) {
        items.put(id, item);
    }

    public Item getItem(String sku) {
        return items.get(sku);
    }

    public static final class Item {
        private boolean liked = false;
        private boolean disliked = false;

        public boolean isLiked() {
            return liked;
        }

        public void setLiked(boolean liked) {
            this.liked = liked;
        }

        public boolean isDisliked() {
            return disliked;
        }

        public void setDisliked(boolean disliked) {
            this.disliked = disliked;
        }
    }
}
