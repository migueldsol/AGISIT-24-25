package tecnico.ulisboa.pt.Users.auth;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;


public class JwtTokenFilter extends GenericFilterBean {

    private JwtTokenProvider jwtTokenProvider;

    JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {

        String token = getToken((HttpServletRequest) req);
        logger.warn("Token: " + token);
        try {
            if (token != null && !token.isEmpty()) {
                logger.warn("Token is not null");
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                logger.warn("Auth: " + auth);
                SecurityContextHolder.getContext().setAuthentication(auth);
            } else {
                logger.warn("JWT token is missing");
            }
        } catch (
                MalformedJwtException ex) {
            logger.error("Invalkey JWT token");
        } catch (
                ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (
                UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty");
            logger.error(ex.getMessage());
        } catch (SecurityException ex) {
            logger.error("JWT signature does not match locally computed signature. JWT validity cannot be asserted and should not be trusted");
        }

        filterChain.doFilter(req, res);
    }
    private String getToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
