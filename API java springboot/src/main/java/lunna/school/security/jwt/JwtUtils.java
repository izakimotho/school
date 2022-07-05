package lunna.school.security.jwt;

import io.jsonwebtoken.*;
import lunna.school.exception.RecordNotFoundException;
import lunna.school.model.Organization;
import lunna.school.security.CustomUserDetails;
import lunna.school.service.SchoolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;


/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 26. Jun 2021 5:39 PM
 **/

@Component
public class JwtUtils {
    @Autowired
    SchoolService schoolService;
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${lunna.app.jwtSecret}")
    private String jwtSecret;

    @Value("${lunna.app.jwtExpirationMs}")
    private int jwtExpirationMs;
    @Autowired
    AuthTokenFilter authTokenFilter;


    public String generateJwtToken(Authentication authentication) {
        if(authentication == null){
            throw new RecordNotFoundException("Bad Credentials");
        }

        CustomUserDetails userPrincipal = (CustomUserDetails) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .claim("school_id",userPrincipal.getOrganization().getOrg_id())
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
    public  Claims getClaims(String token) {
        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token).getBody();
        return claims;
    }

    public UUID getSchoolId(HttpServletRequest request) {
        //This line will throw an exception if it is not a signed JWS (as expected)
        String token = getToken(request);
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token).getBody();
        String id = (String) claims.get("school_id");
        return UUID.fromString(id);

    }


    public String getToken(HttpServletRequest request){
        return authTokenFilter.parseJwt(request);

    }

    public Claims updateExpiryForJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().setExpiration(new Date());
    }

    public Organization getSchool(HttpServletRequest request){
        return schoolService.getSchoolById(getSchoolId(request));
    }

}
