package pierpaolo.colasante.CapstoneBackend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import pierpaolo.colasante.CapstoneBackend.entities.User;
import pierpaolo.colasante.CapstoneBackend.exceptions.UnauthorizedException;
import pierpaolo.colasante.CapstoneBackend.services.UserService;

import java.io.IOException;
import java.util.UUID;
import java.util.stream.Stream;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {
    private static final AntPathMatcher pathMatcher = new AntPathMatcher();
    @Autowired
    private JWTtools jwtTools;
    @Autowired
    private UserService userService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Per favore metti il token nell'Authorization header");
        } else {
            String accessToken = authHeader.substring(7);
            jwtTools.verifyToken(accessToken);

            String id = jwtTools.extractIdFromToken(accessToken);
            User user = userService.findById(UUID.fromString(id));

            Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        }}
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String[] allowedPaths = {"/auth/**", "/swagger-ui/**", "/v3/**"};
        //  non applico il filtro per generare la documentazione swagger e per l'endpoint /auth
        return Stream.of(allowedPaths)
                .anyMatch(path -> pathMatcher.match(path, request.getServletPath()));}
        //        return super.shouldNotFilter(request);
    }

