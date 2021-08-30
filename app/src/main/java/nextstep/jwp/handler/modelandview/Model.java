package nextstep.jwp.handler.modelandview;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Model {

    public static final Model EMPTY = new Model(Collections.emptyMap());

    private Map<String, Object> attributes;

    private Model(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Model() {
        this.attributes = new HashMap<>();
    }

    public void addAttribute(String key, Object value) {
        if (this.attributes.isEmpty()) {
            this.attributes = new HashMap<>();
        }
        attributes.put(key, value);
    }

    public String location() {
        return (String) attributes.get("Location");
    }
}
