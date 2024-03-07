package com.orchware.gateway.config;

import com.orchware.gateway.config.filter.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@RefreshScope
@Configuration
public class RoutesConfig {

    @Value("${swagger.host}")
    private String swaggerHost;

    @Autowired
    @Lazy
    private JwtRequestFilter filter;

    /*@Autowired
    @Lazy
    private JwtAuthenticationFilter filter;*/

    /**
     * Establishes a gateway connection with core, commons, accounts and login.
     * Authenticates using a security middleware.
     *
     * @param builder The route locator object used to link each route.
     * @return A route locator object used to find each microservice by their respective routes.
     */
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("LOGIN-SERVICE", p -> p
                        .path("/auth/**")
                        .filters(f -> f.filter(filter)
                                .circuitBreaker(config -> config
                                .setName("LOGIN-SERVICE")
                                .setFallbackUri("forward:/loginServiceFallBack"))
                        .rewritePath("/auth/(?<path>.*)", "/${path}"))
                        .uri("lb://LOGIN-SERVICE"))
                .route("ACCOUNT-SERVICE", r -> r
                        .path("/account/**")
                        .filters(f -> f.filter(filter)
                                .circuitBreaker(config -> config
                                        .setName("ACCOUNT-SERVICE")
                                        .setFallbackUri("forward:/accountFallBack")))
                        .uri("lb://ACCOUNT-SERVICE"))
                .route("CORE-SERVICE", r -> r
                        .path("/core/**")
                        .filters(f -> f.filter(filter)
                                .circuitBreaker(config -> config
                                        .setName("CORE-SERVICE")
                                        .setFallbackUri("forward:/orchestrationFallBack"))
                                .rewritePath("/core/(?<path>.*)", "/${path}"))
                        .uri("lb://CORE-SERVICE"))
                .route("COMMONS-SERVICE", r -> r
                        .path("/commons/**")
                        .filters(f -> f.filter(filter)
                                .circuitBreaker(config -> config
                                        .setName("COMMONS-SERVICE")
                                        .setFallbackUri("forward:/commonsServiceFallBack"))
                                .rewritePath("/commons/(?<path>.*)", "/${path}"))
                        .uri("lb://COMMONS-SERVICE"))
                .route("openapi", r -> r
                        .path("/v3/api-docs/**")
                        .filters(f -> f
                                .rewritePath("/v3/api-docs/(?<path>.*)", "/${path}/v3/api-docs"))
                        .uri(swaggerHost))
                .build();

//        .route("dynamicRewrite", r ->
//                r.path("/v3/api-docs/**")
//                        .filters(f -> f.filter((exchange, chain) -> {
//                            ServerHttpRequest req = exchange.getRequest();
//                            addOriginalRequestUrl(exchange, req.getURI());
//                            String path = req.getURI().getRawPath();
//                            String newPath = path.replaceAll(
//                                    "/v3/api-docs/\\**",
//                                    "/v3/api-docs");
//                            ServerHttpRequest request = req.mutate().path(newPath).build();
//                            exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, request.getURI());
//                            return chain.filter(exchange.mutate().request(request).build());
//                        }))
//                        .uri("http://localhost:8000"))
//                .build();
    }
}
