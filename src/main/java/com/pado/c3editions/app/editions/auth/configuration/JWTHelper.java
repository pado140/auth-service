package com.pado.c3editions.app.editions.auth.configuration;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;
import com.pado.c3editions.app.editions.auth.security.UserApp;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import io.jsonwebtoken.lang.Maps;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;

public class JWTHelper {

	public static String secretKey="357538782F413F4428472B4B6250655367566B59703373367639792442264529";
//	public static Algorithm algorithm=Algorithm.HMAC256(secretKey.getBytes());

	public static String extractUsername(String token) {
		return extractAllClaims(token).getSubject();
	}

	public static Date extractExpiration(String token) {
		return extractAllClaims(token).getExpiration();
	}

	public static String[] extractClaim(String token) {
		return extractAllClaims(token).get("Authorities",String[].class);
	}
	private static Claims extractAllClaims(String token) {

		return Jwts.parserBuilder().deserializeJsonWith(new JacksonDeserializer(Maps.of("Authorities",String[].class).build()))
				.setSigningKey(getSignKey()).build().parseClaimsJws(token)
				.getBody();
	}

	public static Boolean isTokenExpired(String token) {
		return extractExpiration(token).after(new Date());
	}

	public static String generateToken(UserApp user,String issuer,boolean refr) {
		return createToken(user, user.getUsername(),issuer,refr);
	}

	private static String createToken(UserApp user, String subject,String issuer,boolean refresh) {
//		Map claims=Map.of("recovery-code",user.getUser().getRecover(),
//				"Authorities", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
		var builder=Jwts.builder()
				.setSubject(subject)
				.claim("recovery-code",user.getUser().getRecover())
				.setIssuer(issuer)
				.setIssuedAt(new Date())
				.claim("change_password",user.getUser().isChangepassword());
		if(!refresh) {
			builder.addClaims(Map.of("Authorities", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())))
							.setExpiration(Date.from(LocalDateTime.now().plusHours(24).toInstant(ZoneOffset.UTC)));
			}
		if(refresh){
			builder.setExpiration(Date.from(LocalDateTime.now().plusMonths(10).toInstant(ZoneOffset.UTC)));
		}

		return builder.signWith(getSignKey()).compact();
//		JWTCreator.Builder jwtBuilder=JWT
//				.create()
//				.withSubject(subject)
//				.withClaim("recovery-code",user.getUser().getRecover())
//				.withPayload(Map.of("change_password",user.getUser().isChangepassword()))
//				.withIssuedAt(new Date())
//				.withIssuer(issuer);
//		if(!refresh) {
//			jwtBuilder.withClaim("Authorities", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
//			jwtBuilder.withExpiresAt(LocalDateTime.now().plusHours(24).toInstant(ZoneOffset.UTC));
//		}
//		if(refresh)
//				jwtBuilder.withExpiresAt(LocalDateTime.now().plusMonths(10).toInstant(ZoneOffset.UTC));
//
//		return jwtBuilder.sign(algorithm);
	}

	public static Boolean validateToken(String token) {
		final String username = extractUsername(token);
		return (username!=null && !isTokenExpired(token));
	}

	private static Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
