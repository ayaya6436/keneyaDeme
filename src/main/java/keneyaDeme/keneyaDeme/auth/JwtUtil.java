package keneyaDeme.keneyaDeme.auth;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import keneyaDeme.keneyaDeme.model.Users;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
@Component
public class JwtUtil {
  private final String secret_key = "mysecretkey";
  private long accessTokenValidity = 60*60*1000;

  private final JwtParser jwtParser;
  private final String TOKEN_HEADER = "Authorization";
  private final String TOKEN_PREFIX = "Bearer ";

    public JwtUtil() {
        this.jwtParser = Jwts.parser().setSigningKey(secret_key);
    }

    public String createToken(Users user){
      Claims claims = Jwts.claims().setSubject(user.getEmail());
      claims.put("lastName",user.getNom());
      claims.put("firstName",user.getPrenom());

      Date tokenCreateTime = new Date();
      Date tokenValidity = new Date(tokenCreateTime.getTime() + TimeUnit.MINUTES.toMillis(accessTokenValidity));
      return Jwts.builder()
        .setClaims(claims)
        .setExpiration(tokenValidity)
        .signWith(SignatureAlgorithm.HS256,secret_key)
        .compact();
    }

 // Méthode pour extraire les claims à partir du jeton
  private Claims parseJwtClaims(String token) {
    return jwtParser.parseClaimsJws(token).getBody();
  }

  //Methode pour resoudre les claims depuis la requette http

  public Claims resolveClaims(HttpServletRequest req){
      try {
        String token = resolveToken(req);
        if(token != null){
          return parseJwtClaims(token);
        }
        return null;
      }catch(ExpiredJwtException ex){
        req.setAttribute("expired", ex.getMessage());
        throw ex;
      }catch(Exception ex){
        req.setAttribute("invalid", ex.getMessage());
        throw ex;
      }
  }

  //Methode pour extraire les claims depuis la requette http

  public String resolveToken(HttpServletRequest request){
      String bearerToken = request.getHeader(TOKEN_HEADER);
      if(bearerToken!= null && bearerToken.startsWith(TOKEN_PREFIX)){
          return bearerToken.substring(TOKEN_PREFIX.length());
      }
      return null;
  }

  public boolean validateClaims(Claims claims) throws AuthenticationException {
    try {
      return claims.getExpiration().after(new Date());
    } catch (Exception e) {
      throw e;
    }
  }

  public String getEmail(Claims claims) {
    return claims.getSubject();
  }

  private List<String> getRoles(Claims claims) {
    return (List<String>) claims.get("roles");
  }
}
