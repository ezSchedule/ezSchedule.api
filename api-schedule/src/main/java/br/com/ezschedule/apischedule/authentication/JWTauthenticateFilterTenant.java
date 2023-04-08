package br.com.ezschedule.apischedule.authentication;

import br.com.ezschedule.apischedule.data.TenantDetailData;
import br.com.ezschedule.apischedule.model.Tenant;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JWTauthenticateFilterTenant extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    public static final int TOKEN_EXPIRATION = 600_000;
    public static final String TOKEN_PASSWORD = "df64a5e4-618b-42e9-a427-9cbdf2f0102e";

    public JWTauthenticateFilterTenant(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            Tenant tenant = new ObjectMapper().readValue(request.getInputStream(), Tenant.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    tenant.getEmail(),
                    tenant.getPassword(),
                    new ArrayList<>()
            ));
        } catch (IOException e) {
            throw new RuntimeException("Failed to authenticate user\n", e   );
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        TenantDetailData tenantDetailData = (TenantDetailData) authResult.getPrincipal();

        String token = JWT.create().
                withSubject(tenantDetailData.getUsername()).
                withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION)).
                sign(Algorithm.HMAC512(TOKEN_PASSWORD));

        response.getWriter().write(token);
        response.getWriter().flush();
    }
}
