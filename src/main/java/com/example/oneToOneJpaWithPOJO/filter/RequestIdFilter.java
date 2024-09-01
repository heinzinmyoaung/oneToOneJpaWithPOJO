package com.example.oneToOneJpaWithPOJO.filter;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;
//
//@Component
//public class RequestIdFilter implements Filter {
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
//            throws IOException, ServletException {
//
//        // Generate a unique RequestId
//        String requestId = UUID.randomUUID().toString();
//
//        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
//        httpResponse.addHeader("x-request-id", requestId); // requestId add in Header
//
//        // Set the requestId in MDC
//        MDC.put("requestId", requestId); // define key(requestId) to can use in application.properties file
//        try {
//            filterChain.doFilter(servletRequest,httpResponse);
//        } finally {
//            // Clean up MDC
//            MDC.remove("requestId"); //remove
//        }
//    }
//}

@Component
public class RequestIdFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
//        String requestId = UUID.randomUUID().toString();
        HttpServletResponse httpRequest = (HttpServletResponse) response;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Retrieve or generate a requestId
        String requestId = httpRequest.getHeader("RequestId");
        if (requestId == null) {
            requestId = UUID.randomUUID().toString();
        }

        httpResponse.addHeader("x-request-id", requestId); // requestId add in Header

        MDC.put("requestId", requestId);
        try {
            filterChain.doFilter(request, response);
        } finally {
            MDC.remove("requestId");
        }
    }


}
