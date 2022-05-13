package com.myplaygroup.server.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.myplaygroup.server.feature_login.model.AppUser;
import com.myplaygroup.server.security.model.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {

    @Resource
    private Environment env;

    private Algorithm algorithm;

    public UserInfo getUserInfoFromRequest(HttpServletRequest request){

        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){

            String refresh_token = authorizationHeader.substring("Bearer ".length());

            Algorithm algorithm = getAlgorithm();
            DecodedJWT decodedJWT = getDecodedJWT(algorithm, refresh_token);

            // Get the username and authorities
            return new UserInfo(
                    decodedJWT.getSubject(),
                    refresh_token
            );

        }else {
            throw new IllegalStateException("Refresh token is missing");
        }
    }

    public void setAuthenticationToken(String token){

        Algorithm algorithm = getAlgorithm();
        DecodedJWT decodedJWT = getDecodedJWT(algorithm, token);

        String username = decodedJWT.getSubject();
        String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        stream(roles).forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role));
        });

        // Create the authentication token
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    public Map<String, String> getAccessAndRefreshToken(AppUser user, String requestUrl){

        String username = user.getUsername();
        Algorithm algorithm = getAlgorithm();
        List<String> roles = getUserRoles(user);

        String refresh_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                .withIssuer(requestUrl)
                .sign(algorithm);

        return getAuthorizationTokens(requestUrl, username, roles, algorithm, refresh_token);
    }

    public Map<String, String> getAccessTokenFromRefreshToken(String refresh_token, String requestUrl, AppUser user) {

        Algorithm algorithm = getAlgorithm();

        // Get the username and authorities
        String username = user.getUsername();
        List<String> roles = getUserRoles(user);

        return getAuthorizationTokens(requestUrl, username, roles, algorithm, refresh_token);
    }

    private Map<String, String> getAuthorizationTokens(String requestUrl, String username, List<?> roles, Algorithm algorithm, String refresh_token){
        String access_token = JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .withIssuer(requestUrl)
                .withClaim("roles", roles)
                .sign(algorithm);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);

        return tokens;
    }

    private List<String> getUserRoles(AppUser user){
        return user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }

    private Algorithm getAlgorithm(){
        if(algorithm == null){
            algorithm = Algorithm.HMAC256(env.getProperty("playgroup.app.jwtSecret").getBytes());
        }
        return algorithm;
    }

    private DecodedJWT getDecodedJWT(Algorithm algorithm, String refresh_token){
        JWTVerifier verifier = JWT.require(algorithm).build();

        return verifier.verify(refresh_token);
    }
}
