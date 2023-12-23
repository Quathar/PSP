package com.quathar.psp.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.net.URL;
import java.util.List;

/**
 * <h1>Joke Model</h1>
 *
 * @since 2022-12-14
 * @version 1.0
 * @author Q
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Joke {

    // <<-FIELDS->>
    private List<String> categories;
    private String       createdAt;
    private URL          iconUrl;
    private String       id;
    private String       updatedAt;
    private URL          url;
    private String       value;

    // <<-METHODS->>
    @Override
    public String toString() {
        return String.format(
                "%nJoke {%n" +
                        "%-10s categories=%s,%n" +
                        "%-10s createdAt=%s,%n" +
                        "%-10s iconUrl=%s,%n" +
                        "%-10s id='%s',%n" +
                        "%-10s updatedAt=%s,%n" +
                        "%-10s url=%s,%n" +
                        "%-10s value='%s'%n" +
                        "}",
                " ", this.categories,
                " ", this.createdAt,
                " ", this.iconUrl,
                " ", this.id,
                " ", this.updatedAt,
                " ", this.url,
                " ", this.value);
    }

    // <<-GETTERS & SETTERS->>
    public List<String> getCategories() {
        return this.categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public URL getIconUrl() {
        return this.iconUrl;
    }

    public void setIconUrl(URL iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public URL getUrl() {
        return this.url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
