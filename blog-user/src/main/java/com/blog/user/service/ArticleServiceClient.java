package com.blog.user.service;

import com.blog.user.dto.FavoriteCountDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * 文章服务客户端，用于与article服务通信
 */
@Slf4j
@Service
public class ArticleServiceClient {
    private final WebClient webClient;

    public ArticleServiceClient() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8082") // blog-article服务的地址
                .build();
    }

    /**
     * 添加文章收藏数
     *
     * @param favoriteCountDto 添加文章收藏数
     */
    public void handleFavoriteCount(FavoriteCountDto favoriteCountDto) {
        try {
            Mono<String> response = webClient.post()
                    .uri("/article/comment/favorite/handle")
                    .accept(MediaType.APPLICATION_JSON)
                    .body(Mono.just(favoriteCountDto), FavoriteCountDto.class)
                    .retrieve()
                    .bodyToMono(String.class);

            String result = response.block();
            log.info("文章收藏数处理结果: {}", result);
        } catch (Exception e) {
            log.error("调用article服务处理文章收藏数失败", e);
        }
    }
}
