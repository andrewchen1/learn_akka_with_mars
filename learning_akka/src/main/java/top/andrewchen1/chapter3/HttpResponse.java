package top.andrewchen1.chapter3;

import lombok.Data;

@Data
public class HttpResponse {
    private final String body;
    public HttpResponse(String body){
        this.body = body;
    }
}
