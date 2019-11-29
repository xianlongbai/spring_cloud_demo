package com.bxl.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Component
@Order(1)
public class MyLimitFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(MyLimitFilter.class);

    //每秒产生1000个令牌
    private static final RateLimiter RATE_LIMITER = RateLimiter.create(1);

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return -4;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        boolean tryAcquire = RATE_LIMITER.tryAcquire();
        log.info("本次request请求获取令牌结果：'{}'",tryAcquire);
        // 如果获取不到就直接停止
        if(!tryAcquire){
            log.error("limit filter : failed!");
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(HttpStatus.TOO_MANY_REQUESTS.value());
            try {
                requestContext.getResponse().getWriter().write("sorry,too many request!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        log.info("limit filter : success!");
        return null;
    }
}
