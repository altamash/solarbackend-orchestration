package com.orchware.gateway.config.filter;

import com.orchware.gateway.constants.SecurityConstants;
import com.orchware.gateway.exception.UsernameNotFoundException;
import com.orchware.gateway.feignInterface.AccountInterface;
import com.orchware.gateway.responseDTO.AccountUserDTO;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Predicate;

@RefreshScope
@Component
public class JwtRequestFilter implements GatewayFilter {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
//    private final UserDetailsServiceImpl jwtUserDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AccountInterface accountInterface;
//    @Autowired
//    private JwtUtil jwtUtil;

    public JwtRequestFilter(
//            UserDetailsServiceImpl jwtUserDetailsService,
                            JwtTokenUtil jwtTokenUtil,
                            AccountInterface accountInterface
    ) {
//        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.accountInterface = accountInterface;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        final List<String> apiEndpoints = List.of(
//                "auth/test",
                "auth/signup", "/signin", "/core/core/v3/api-docs",
                "/commons/commons/v3/api-docs", "/auth/v3/api-docs");

        Predicate<ServerHttpRequest> isApiSecured = r -> apiEndpoints.stream()
                .noneMatch(uri -> r.getURI().getPath().contains(uri));

        if (isApiSecured.test(request)) {
            if (!request.getHeaders().containsKey(SecurityConstants.AUTHORIZATION_HEADER)) {
                return this.onError(exchange);
            }
            List<String> requestTokenHeaders = request.getHeaders().get(SecurityConstants.AUTHORIZATION_HEADER);
            String requestTokenHeader = null;
            if (!requestTokenHeaders.isEmpty()) {
                requestTokenHeader = request.getHeaders().get(SecurityConstants.AUTHORIZATION_HEADER).get(0);
            }
            String username = null;
            String jwtToken = null;
            if (requestTokenHeader != null && requestTokenHeader.startsWith(SecurityConstants.BEARER_PREFIX)) {
                jwtToken = requestTokenHeader.substring(7);
                try {
                    username = jwtTokenUtil.getUsernameFromToken(jwtToken);
                } catch (IllegalArgumentException e) {
                    LOGGER.error("Unable to get JWT Token", e);
                } catch (ExpiredJwtException e) {
                    LOGGER.error("JWT Token has expired", e);
                } catch (SignatureException e) {
                    LOGGER.error("Authentication Failed. Username or Password not valid.", e);
                }
            } else {
                LOGGER.error("Authentication Failed.");
                return this.onError(exchange);
            }
            if (username != null) {
                if (request.getHeaders().get(SecurityConstants.AUTHORIZATION_HEADER) == null) {
                    return this.onError(exchange);
                }
            } else {
                return this.onError(exchange);
            }
            String finalUsername = username;
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            Future<AccountUserDTO> future = executorService.submit(() -> accountInterface.fetchUserByUsername(finalUsername)); // TODO: make it local
            AccountUserDTO accountUserDTO;
            try {
                accountUserDTO = future.get();
            } catch (InterruptedException | ExecutionException e) {
                return this.onError(exchange);
            }
            if (accountUserDTO == null) {
                return this.onError(exchange);
            }
//        UserDetails userDetails = UserDetailsImpl.build(accountUserDTO);
            executorService.shutdown();
            if (!jwtTokenUtil.validateToken(jwtToken, accountUserDTO.getUsername())) {
                return this.onError(exchange);
            }
        }
        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }
}
