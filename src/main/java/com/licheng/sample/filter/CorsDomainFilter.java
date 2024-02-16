package com.licheng.sample.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;

/*
 * @author LiCheng
 * @date  2024-02-16 22:16:03
 *
 * Cors拦截器 这里默认允许所有请求头跨域访问
 */
public class CorsDomainFilter extends CorsFilter {
    private Logger logger = LoggerFactory.getLogger(CorsDomainFilter.class);

    public CorsDomainFilter(CorsConfigurationSource configSource) {
        super(configSource);
        Assert.notNull(configSource, "CorsConfigurationSource must not be null");
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String origin = request.getHeader("Origin");
//        logger.info("origin: "+ origin);
        if (StringUtils.hasText(origin)) {
            URI uri = null;
            try {
                uri = new URI(origin);
            } catch (URISyntaxException e) {
                logger.error("", e);
            }
            if (uri != null) {
                String uriHost = uri.getHost();
                if ("localhost".equalsIgnoreCase(uriHost) || "127.0.0.1".equalsIgnoreCase(uriHost)) {
                    return false;
                }
            }
        }
        return false;
    }

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*"); // 允许任何域名使用
        corsConfiguration.addAllowedHeader("*"); // 允许任何头
        corsConfiguration.addAllowedMethod("*"); // 允许任何方法（post、get等）
        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }

    @Bean
    public CorsDomainFilter corsDomainFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        logger.info("use CorsDomainFilter for corsDomain setting.");
        return new CorsDomainFilter(source);
    }
}
