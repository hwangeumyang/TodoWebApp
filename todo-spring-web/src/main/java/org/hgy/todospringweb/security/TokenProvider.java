package org.hgy.todospringweb.security;

import org.hgy.todospringweb.model.UserEntity;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import javax.crypto.SecretKey;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
public class TokenProvider {
//	private static final String SECRET_KEY = "secretKey"; // 책에서는 좀 더 복잡하고 긴 의미없는 문자열을 사용
	private static final SecretKey SECRET_KEY = Jwts.SIG.HS512.key().build();
	
	public String create(UserEntity entity) {	
		Date expiryDate = Date.from(
				Instant.now()
				.plus(30, ChronoUnit.SECONDS));
//				.plus(1, ChronoUnit.DAYS));
		log.info("secret_key" + SECRET_KEY.toString());
		log.debug("secret_key" + SECRET_KEY);

		
		return Jwts.builder()
				.subject(entity.getUsername())
				.issuer("hgy todo app")
				.issuedAt(new Date())
				.expiration(expiryDate)
				.signWith(SECRET_KEY, Jwts.SIG.HS512)
				.compact();
	}
	
	public String validateAndGetUserId(String token) {	
		try {
			log.info("secret_key" + SECRET_KEY.toString());
			log.debug("secret_key" + SECRET_KEY.toString());
//			var claims = Jwts.parser()
//					.verifyWith(SECRET_KEY).build()
//					.parseSignedClaims(token);
//			if(claims.getPayload().getExpiration().after(new Date())) {
//				return claims.getPayload().getSubject();
//			}
//			
//			throw new RuntimeException("Expired token");  
					
			return Jwts.parser()
					.verifyWith(SECRET_KEY).build()
					.parseSignedClaims(token)
					.getPayload()
					.getSubject();
		} catch(JwtException e) {
			// 실퍃할 경우, 책에서는 이에 대해 다루지 않으므로 생략
			throw new UnsupportedOperationException();
		}
	}

}
