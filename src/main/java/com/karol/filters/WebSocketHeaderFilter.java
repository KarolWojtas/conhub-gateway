package com.karol.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.http.HttpServletRequestWrapper;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class WebSocketHeaderFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(ctx.getRequest());
        String upgradeHeader = wrapper.getHeader("Upgrade");
        if (null == upgradeHeader) {
            upgradeHeader = wrapper.getHeader("upgrade");
        }
        if (null != upgradeHeader && "websocket".equalsIgnoreCase(upgradeHeader)) {
            ctx.getResponse().addHeader("connection", "Upgrade");
            ctx.addZuulRequestHeader("connection", "Upgrade");
            System.out.println(ctx.getRequest().getHeader("Connection"));
        }
        return ctx.getRequest();
    }
}
