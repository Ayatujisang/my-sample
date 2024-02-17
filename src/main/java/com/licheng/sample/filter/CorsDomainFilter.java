package com.licheng.sample.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;

/*
 * @author LiCheng
 * @date  2024-02-17 22:05:25
 *
 * 覆盖spring原生的corsDomianFilter
 */
public class CorsDomainFilter extends CorsFilter implements Ordered {
    private Logger logger = LoggerFactory.getLogger(CorsDomainFilter.class);

    private int order = -2147483648;

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

    @Override
    public int getOrder() {
        return order;
    }
}
