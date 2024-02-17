package com.licheng.sample.config;


import com.licheng.sample.filter.CorsDomainFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/*
 * @author LiCheng
 * @date  2024-02-17 22:04:42
 *
 * 自定义cors策略
 * 允许任何请求跨域
 */
@Configuration
@ConditionalOnClass(CorsFilter.class)
public class CorsDomainConfiguration {
    private Logger logger = LoggerFactory.getLogger(CorsDomainConfiguration.class);

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*"); // 允许任何域名使用
        corsConfiguration.addAllowedHeader("*"); // 允许任何头
        corsConfiguration.addAllowedMethod("*"); // 允许任何方法（post、get等）
        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }

    @Bean
    @ConditionalOnClass(CorsFilter.class)
    @Order(2147483640)
    public CorsDomainFilter corsDomainFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        logger.info("use CorsDomainFilter for corsDomain setting.");
        return new CorsDomainFilter(source);
    }


}
