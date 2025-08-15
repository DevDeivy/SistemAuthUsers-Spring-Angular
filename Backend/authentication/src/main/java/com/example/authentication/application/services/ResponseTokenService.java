package com.example.authentication.application.services;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ResponseTokenService {

   private static final String _SecretKey = "YOUR_SECRET_KEY";

   public String getTokenWithoutClaims(UserDetails userDetails){
       return getTokenWithClaims(new HashMap<>(), userDetails);
   }

   private String getTokenWithClaims(Map<String, Object> extraClaims, UserDetails userDetails){
       return Jwts
               .builder()
               .setClaims(extraClaims)
               .setSubject(userDetails.getUsername())
               .setIssuedAt(new Date(System.currentTimeMillis()))
               .setExpiration(new Date(System.currentTimeMillis() + 1000 + 60 + 60 * 24))
               .signWith(key(), SignatureAlgorithm.HS256)
               .compact();
   }

   private Key key(){
       byte [] keyByte = Decoders.BASE64.decode(_SecretKey);
       return Keys.hmacShaKeyFor(keyByte);
   }
}













