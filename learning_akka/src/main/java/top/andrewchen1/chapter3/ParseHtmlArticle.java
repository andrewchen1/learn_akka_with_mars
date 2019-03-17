package top.andrewchen1.chapter3;

import lombok.Data;

@Data
public class ParseHtmlArticle {
    private final String uri, htmlString;

    public ParseHtmlArticle(String uri, String htmlString) {
        this.uri = uri;
        this.htmlString = htmlString;
    }
}
