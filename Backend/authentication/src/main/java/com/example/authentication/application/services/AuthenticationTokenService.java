package com.example.authentication.application.services;

import com.example.authentication.domain.models.RefreshToken;
import com.example.authentication.domain.models.User;
import com.example.authentication.infraestructure.repository.RefreshTokenRepository;
import com.example.authentication.infraestructure.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import io.jsonwebtoken.Jwts;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationTokenService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private static final String SECRET_KEY = "thisismytokensecretkatorcecuarentaycuatromilochojentospejosjajaja";

   public String getTokenWithoutClaims(UserDetails userDetails){
       return getTokenWithClaims(new HashMap<>(), userDetails);
   }

   private String getTokenWithClaims(Map<String, Object> extraClaims, UserDetails userDetails){
       return Jwts
               .builder()
               .setClaims(extraClaims)
               .setSubject(userDetails.getUsername())
               .setIssuedAt(new Date(System.currentTimeMillis()))
               .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
               .signWith(key(), SignatureAlgorithm.HS256)
               .compact();
   }

   private Key key(){
       return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
   }

    public ResponseEntity<Object> refreshToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid bearer token");
        }

        String refreshToken = authHeader.substring(7);
        String userEmail = extractUsername(refreshToken);

        if (userEmail == null) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException(userEmail));

        if (!isTokenvalid(refreshToken, user)) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        String accessToken = getTokenWithoutClaims(user);

        RefreshToken tokenEntity = refreshTokenRepository.findByTokenRefresh(refreshToken)
                .orElse(new RefreshToken());
        tokenEntity.setTokenRefresh(refreshToken);
        tokenEntity.setRevoked(false);
        tokenEntity.setExpired(false);
        tokenEntity.setUser(user);

        refreshTokenRepository.save(tokenEntity);

        if (tokenEntity.getRevoked() || tokenEntity.getExpired()){
            throw new IllegalArgumentException("Token is expired or revoked");
        }

        Map<String, String> response = new HashMap<>();
        response.put("accessToken", accessToken);
        response.put("refreshToken", refreshToken);

        return ResponseEntity.ok(response);
    }


    public boolean revokedToken(String token){
       RefreshToken refreshToken = refreshTokenRepository.findByTokenRefresh(token)
               .orElseThrow(() -> new IllegalArgumentException("token not found"));

       refreshToken.setExpired(true);
       refreshToken.setRevoked(true);
       refreshTokenRepository.save(refreshToken);
       return true;
    }

    public boolean isTokenvalid(String token, UserDetails user){
       String email = extractUsername(token);
       return email.equals(user.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token){
       return extractExpirationToken(token).before(new Date());
    }

    private Date extractExpirationToken(String token) {
       Claims jwtToken = Jwts.parserBuilder()
               .setSigningKey(key())
               .build()
               .parseClaimsJws(token)
               .getBody();
       return jwtToken.getExpiration();
    }

    public String extractUsername(String refreshToken) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(refreshToken)
                .getBody();
        return claims.getSubject();
    }

}













