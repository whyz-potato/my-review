package whyzpotato.myreview.controller.naver_api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import static whyzpotato.myreview.controller.naver_api.Authority.CLIENT_ID;
import static whyzpotato.myreview.controller.naver_api.Authority.CLIENT_SECRET;

@Configuration
public class NaverConfiguration {
    @Bean
    public WebClient searchWebClient() {
        return WebClient.builder()
                .baseUrl("https://openapi.naver.com/v1/search")
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                    httpHeaders.add(CLIENT_ID.getHeader(), CLIENT_ID.getValue());
                    httpHeaders.add(CLIENT_SECRET.getHeader(), CLIENT_SECRET.getValue());
                })
                .build();
    }
}
