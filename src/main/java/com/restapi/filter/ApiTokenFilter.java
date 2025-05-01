package com.restapi.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import com.restapi.config.ServiceParametersConfig;

import java.io.IOException;

@Component
public class ApiTokenFilter implements Filter {

    private final ServiceParametersConfig serviceParametersConfig;

    public ApiTokenFilter(ServiceParametersConfig serviceParametersConfig) {
        this.serviceParametersConfig = serviceParametersConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String apiToken = httpRequest.getHeader("X-API-TOKEN");

        if (serviceParametersConfig.getKey().equals(apiToken)) {
            chain.doFilter(request, response);
        } else {
            HttpServletResponse httpResp = (HttpServletResponse) response;
            httpResp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResp.getWriter().write("Invalid API token");
        }
    }
}