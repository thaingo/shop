package io.github.tkaczenko.view;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tkaczenko on 08.05.17.
 */
@Component
public class AttributesForm {
    private int numOfAttributes = 0;

    private List<Attribute> attributes = new ArrayList<>();

    public AttributesForm() {

    }

    public AttributesForm(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public int getNumOfAttributes() {
        return numOfAttributes;
    }

    public void setNumOfAttributes(int numOfAttributes) {
        this.numOfAttributes = numOfAttributes;
    }

    public static class Attribute {
        private String oldName;
        private String newName;

        public Attribute() {
        }

        public Attribute(String oldName, String newName) {
            this.oldName = oldName;
            this.newName = newName;
        }

        public String getOldName() {
            return oldName;
        }

        public void setOldName(String oldName) {
            this.oldName = oldName;
        }

        public String getNewName() {
            return newName;
        }

        public void setNewName(String newName) {
            this.newName = newName;
        }
    }
}
