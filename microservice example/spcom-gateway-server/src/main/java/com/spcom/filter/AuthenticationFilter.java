package com.spcom.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spcom.config.CustomProblemDetail;
import com.spcom.util.JwtUtil;

import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

	@Autowired
	private RouteValidator validator;

	// @Autowired
//    private RestTemplate template;
	@Autowired
	private JwtUtil jwtUtil;

	public AuthenticationFilter() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		return ((exchange, chain) -> {
			if (validator.isSecured.test(exchange.getRequest())) {
				// header contains token or not
				if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
//                    throw new RuntimeException("missing authorization header");
					return unauthorized(exchange, "JWT Token is missing or not in the correct format");
				}

				String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
				if (authHeader != null && authHeader.startsWith("Bearer ")) {
					authHeader = authHeader.substring(7);
				}
				try {
//                    //REST call to AUTH service
//                    template.getForObject("http://IDENTITY-SERVICE//validate?token" + authHeader, String.class);
					jwtUtil.validateToken(authHeader);

				} catch (Exception e) {
					System.out.println("invalid access...!");
//                    throw new RuntimeException("un authorized access to application");
					return unauthorized(exchange, "unauthorized access to application");
				}
			}
			return chain.filter(exchange);
		});
	}

	private Mono<Void> unauthorized(ServerWebExchange exchange, String errorMessage) {
		exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
		exchange.getResponse().getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json");
//        {
//            "type": "about:blank",
//            "title": "Forbidden",
//            "status": 403,
//            "detail": "Bad credentials",
//            "instance": "/auth/token",
//            "access_denied_reason": "JWT Signature is missing"
//        }
		ObjectMapper objectMapper = new ObjectMapper();
		CustomProblemDetail problemDetail = new CustomProblemDetail();
		problemDetail.setType("about:blank");
		problemDetail.setTitle("Forbidden");
		problemDetail.setStatus(403);
		problemDetail.setDetail(errorMessage);
		problemDetail.setInstance("gatway");

//        return this.handleProblem(errorMessage, problemDetail, exchange);
		try {
			return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory()
					.wrap(objectMapper.writeValueAsString(problemDetail).getBytes())));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static class Config {

	}
}
