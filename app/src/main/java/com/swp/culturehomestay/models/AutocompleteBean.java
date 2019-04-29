package com.swp.culturehomestay.models;

public class AutocompleteBean {
    private String group;
    private String value;
    private String title;

    @Override
    public String toString() {
        return title;
    }

    public AutocompleteBean() {
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
