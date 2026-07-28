package com.apigateway.config;

import org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.function.*;
@Configuration
public class RouteConfig {

    //questo e per il mvc non web flux
    @Bean
    public RouterFunction<ServerResponse> authRoutes(){
        //route id con gateway
        return GatewayRouterFunctions.route("auth-routes")
                //da dove arriva
                .route(RequestPredicates.path("/auth/**"), HandlerFunctions.http())
                //name microservizio per laod balancer se ci sono piu istanze di quello
                //anche se arriva da url sorpa mandali al user service
                .filter(LoadBalancerFilterFunctions.lb("user-service"))
                .build();
    }
    // auth/register , auth/login

    @Bean
    public RouterFunction<ServerResponse> adminLocationServiceRoute(){
        //route id con gateway
        return GatewayRouterFunctions.route("admin-location-routes")
                //da dove arriva
                .route(RequestPredicates.POST("/api/cities/**"), HandlerFunctions.http())
                .route(RequestPredicates.POST("/api/airports/**"), HandlerFunctions.http())
                //name microservizio per laod balancer se ci sono piu istanze di quello
                //anche se arriva da url sorpa mandali al user service
                .filter(LoadBalancerFilterFunctions.lb("location-service"))
                .build();
    }

    @Bean
    @Order(1)
    public RouterFunction<ServerResponse> adminAirlineCoreServiceRoutes(){
        //route id con gateway
        return GatewayRouterFunctions.route("admin-location-core-routes")
                //da dove arriva
                .route(RequestPredicates.GET("/api/airlines"), HandlerFunctions.http())
                //name microservizio per laod balancer se ci sono piu istanze di quello
                //anche se arriva da url sorpa mandali al user service
                .filter(LoadBalancerFilterFunctions.lb("airline-core-service"))
//                .filter(CircuitBreakerFilterFunctions.circuitBreaker("airline-core-service-cb"))
//                .before(this::jwtAuthFilter)
//                .before(serverRequest ->requireRole(request, 'ROLE_SYSTEM_ADMIN'))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> userServiceRoutes(){
        //route id con gateway
        return GatewayRouterFunctions.route("user-service-routes")
                //da dove arriva
                .route(RequestPredicates.path("/api/useres/**"), HandlerFunctions.http())
                //name microservizio per laod balancer se ci sono piu istanze di quello
                //anche se arriva da url sorpa mandali al user service
                .filter(LoadBalancerFilterFunctions.lb("user-service"))
                .build();
    }

    @Bean
    @Order(2)
    public RouterFunction<ServerResponse> airlineCoreServiceRoutes(){
        //route id con gateway
        return GatewayRouterFunctions.route("airline-core-routes")
                //da dove arriva
                .route(RequestPredicates.path("/api/airlines/**"), HandlerFunctions.http())
                .route(RequestPredicates.path("/api/aircrafts/**"), HandlerFunctions.http())
                //name microservizio per laod balancer se ci sono piu istanze di quello
                //anche se arriva da url sorpa mandali al user service
                .filter(LoadBalancerFilterFunctions.lb("airline-core-service"))
//                .filter(CircuitBreakerFilterFunctions.circuitBreaker("airline-core-service-cb"))
//                .before(this::jwtAuthFilter)
//                .before(serverRequest ->requireRole(request, 'ROLE_SYSTEM_ADMIN'))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> seatServiceRoutes(){
        //route id con gateway
        return GatewayRouterFunctions.route("seat-service-routes")
                //da dove arriva
                .route(RequestPredicates.path("/api/cabin-classes/**"), HandlerFunctions.http())
                .route(RequestPredicates.path("/api/seat-maps/**"), HandlerFunctions.http())
                .route(RequestPredicates.path("/api/seats/**"), HandlerFunctions.http())
                .route(RequestPredicates.path("/api/seat-instances/**"), HandlerFunctions.http())
                .route(RequestPredicates.path("/api/flight-instance-cabins/**"), HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("seat-service"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> flightOpsServiceRoutes(){
        //route id con gateway
        return GatewayRouterFunctions.route("flight-ops-routes")
                //da dove arriva
                .route(RequestPredicates.path("/api/flights/**"), HandlerFunctions.http())
                .route(RequestPredicates.path("/api/flight-instances/**"), HandlerFunctions.http())
                .route(RequestPredicates.path("/api/flight-schedules/**"), HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("flights-ops-service"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> pricingServiceRoutes(){
        //route id con gateway
        return GatewayRouterFunctions.route("pricing-service-routes")
                //da dove arriva
                .route(RequestPredicates.path("/api/fares/**"), HandlerFunctions.http())
                .route(RequestPredicates.path("/api/fare-rules/**"), HandlerFunctions.http())
                .route(RequestPredicates.path("/api/baggage-policies/**"), HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("pricing-service"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> ancillaryServiceRoutes(){
        //route id con gateway
        return GatewayRouterFunctions.route("flight-ops-routes")
                //da dove arriva
                .route(RequestPredicates.path("/api/melas/**"), HandlerFunctions.http())
                .route(RequestPredicates.path("/api/ancillaries/**"), HandlerFunctions.http())
                .route(RequestPredicates.path("/api/insurance-coverages/**"), HandlerFunctions.http())
                .route(RequestPredicates.path("/api/flight-meals/**"), HandlerFunctions.http())
                .route(RequestPredicates.path("/api/flight-cabin-ancillaries/**"), HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("ancillary-service"))
                .build();
    }

    @Bean
    @Order(2)
    public RouterFunction<ServerResponse> locationServiceRoutes(){
        //route id con gateway
        return GatewayRouterFunctions.route("location-service-routes")
                //da dove arriva
                .route(RequestPredicates.path("/api/cities/**"), HandlerFunctions.http())
                .route(RequestPredicates.path("/api/airports/**"), HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("location-service"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> bookingServiceRoutes(){
        //route id con gateway
        return GatewayRouterFunctions.route("booking-service-routes")
                //da dove arriva
                .route(RequestPredicates.path("/api/boookings/**"), HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("booking-service"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> paymnetServiceRoutes(){
        //route id con gateway
        return GatewayRouterFunctions.route("payment-service-routes")
                //da dove arriva
                .route(RequestPredicates.path("/api/payments/**"), HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("flights-ops-service"))
                //.filter(CircuitBreakerFilterFunctions.circuitBreaker("payment-service-cb", URI.create("forward://fallback")))
                .build();
    }








    //web flux
//    @Bean
//    public RouteLocator appRouteConfig(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route("accounts-service", p -> p
//                        .path("/accounts/**")
//                        .filters(f -> f
//                                .rewritePath("/app/accounts/(?<segment>.*)", "/${segment}")
//                                .addRequestHeader("X-RESPONSE-TIME", LocalDateTime.now().toString())
//                                .circuitBreaker(config -> config
//                                        .setName("accountsCircuitBreaker")
//                                        .setFallbackUri("/forward://contactSupport")))
//                        .uri("lb://ACCOUNTS"))
//                .build();
//    }
}
