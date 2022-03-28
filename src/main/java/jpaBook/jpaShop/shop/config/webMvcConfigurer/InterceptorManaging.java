package jpaBook.jpaShop.shop.config.webMvcConfigurer;

import jpaBook.jpaShop.shop.config.interceptor.HttpRequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class InterceptorManaging implements WebMvcConfigurer {
    private final HttpRequestInterceptor httpRequestInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(httpRequestInterceptor)
                .addPathPatterns("/**");

    }
}
