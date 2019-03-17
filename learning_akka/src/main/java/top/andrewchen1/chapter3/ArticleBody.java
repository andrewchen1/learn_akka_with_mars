package top.andrewchen1.chapter3;

import lombok.Data;

@Data
public class ArticleBody {
    private final String uri;
    private final String body;
    public ArticleBody(String uri, String body) {
        this.uri = uri;
        this.body = body;
    }
}
