package br.com.ezschedule.apischedule.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Security;
import java.util.ArrayList;

public class JWTvalidationFilterAdministrator extends BasicAuthenticationFilter {

    public static final String HEADER_ATTRIBUTE = "Authorization";
    public static final String PREFIX_ATTRIBUTE = "Bearer ";

    public JWTvalidationFilterAdministrator(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
            String attribute = request.getHeader(HEADER_ATTRIBUTE);
            if (attribute == null){
                chain.doFilter(request, response);
                return;
            }

            if (!attribute.startsWith(PREFIX_ATTRIBUTE)){
                chain.doFilter(request, response);
                return;
            }

         String token = attribute.replace(PREFIX_ATTRIBUTE, "");
         UsernamePasswordAuthenticationToken authenticationToken = gePasswordAuthenticationToken(token);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken gePasswordAuthenticationToken(String token){
        String userAdministrator = JWT.require(Algorithm.HMAC512(JWTauthenticateFilterAdministrator.TOKEN_PASSWORD)).
        build().
        verify(token).
        getSubject();

        if(userAdministrator == null){
            return null;
        }
        return new UsernamePasswordAuthenticationToken(userAdministrator, null, new ArrayList<>());

    }
}
