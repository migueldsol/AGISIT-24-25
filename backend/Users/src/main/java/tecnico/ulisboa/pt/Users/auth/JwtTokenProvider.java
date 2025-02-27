package tecnico.ulisboa.pt.Users.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import tecnico.ulisboa.pt.Users.auth.domain.AuthUser;
import tecnico.ulisboa.pt.Users.auth.repository.AuthUserRepository;
import tecnico.ulisboa.pt.Users.exceptions.UserException;
import tecnico.ulisboa.pt.Users.exceptions.ErrorMessage; 

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Date;


@Component
public class JwtTokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    private AuthUserRepository authUserRepository;

    private static PublicKey publicKey;

    private static PrivateKey privateKey;

    private static String jwtSeed="secret";

    public JwtTokenProvider(AuthUserRepository authUserRepository) {
        this.authUserRepository = authUserRepository;
    }

    public static void generateKeys() {
        try {

            // Create a KeyPairGenerator instance for RSA
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");

            // Set a specific seed for SecureRandom to make it deterministic
        byte[] seed = jwtSeed.getBytes(); // Use a fixed seed to ensure repeatability
        SecureRandom secureRandom = new SecureRandom(seed);

        // Initialize KeyPairGenerator with the secure random and key size
        keyPairGenerator.initialize(2048, secureRandom);
        
        // Generate the KeyPair
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        
        // Get the private and public keys
        privateKey = keyPair.getPrivate();
        publicKey = keyPair.getPublic();
        } catch (Exception e) {
            logger.error("Error generating keys: " + e.toString());
        }

    }

    static String generateToken(AuthUser authUser) {
        logger.warn("Generating token for user: " + authUser.getUsername());
        if (publicKey == null) {
            generateKeys();
        }

        Claims claims = Jwts.claims().setSubject(authUser.getUsername());

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 1000 * 60 * 60 * 24);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(privateKey)
                .compact();
    }

    private static Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(token).getBody();
    }

    Authentication getAuthentication(String token) {
        logger.warn("getAuthentication");
        Claims tokenClaims = getAllClaimsFromToken(token);
        logger.warn("Token claims: " + tokenClaims);
        String authUsername = tokenClaims.getSubject();
        //List<Integer> executions = (ArrayList<Integer>) tokenClaims.get("executions");
        logger.warn("Authenticating user: " + authUsername);
        AuthUser authUser = this.authUserRepository.findAuthUserByUsername(authUsername).orElseThrow(() -> new UserException(ErrorMessage.USER_NOT_FOUND, authUsername));
        return new UsernamePasswordAuthenticationToken(authUser, "", authUser.getAuthorities());
    }

    public boolean validateToken(String token) {
        logger.warn("token " + token);
        try {
            Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            logger.error("Invalid token" + e.toString());
            return false;
        }
    }

    public String getUsername(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }
}