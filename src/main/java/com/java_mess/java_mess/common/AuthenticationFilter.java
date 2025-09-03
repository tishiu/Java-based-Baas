package com.java_mess.java_mess.common;

import java.io.IOException;

import org.springframework.http.HttpStatus;

import com.java_mess.java_mess.model.App;
import com.java_mess.java_mess.service.AppService;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthenticationFilter implements Filter {
    private AppService appService;

    public AuthenticationFilter(AppService appService) {
        this.appService = appService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code if needed
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String apiClientKey = request.getHeader("x-api-key");
        log.debug(String.format("got apikey from header: {}", apiClientKey));
        if (apiClientKey == null) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return;
        }

        App app = appService.getAppByClientKey(apiClientKey);
        if (app == null) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return;
        }

        request.setAttribute("authenticatedApp", app);

        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Cleanup code if needed
    }
}
