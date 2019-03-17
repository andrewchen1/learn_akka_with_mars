package top.andrewchen1.chapter3;

import lombok.Data;

@Data
public class ParseArticle {
    private final String url;
    public ParseArticle(String url) {
        this.url = url;
    }
}
