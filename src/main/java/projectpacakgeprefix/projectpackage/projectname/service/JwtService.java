package projectpacakgeprefix.projectpackage.projectname.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtService
{
	protected final String DATA_CLAIM_KEY = "data";
	protected final String BEARER_PREFIX = "Bearer ";

	@Value("${secretkey}")
	protected String secretKey;

	// ==========================================================================
	// Public Methods
	// ==========================================================================

	/**
	 * encodes an object as a JWT
	 * @param object - the object to encode
	 * @param tokenSubject - the subject of the token
	 * @param expirationDate - when the token should expire.
	 * @return - the JWT string
	 */
	public String objectToJwt(Object object, String tokenSubject, Date expirationDate)
	{
		try
		{
			return Jwts.builder()
			           .claim(DATA_CLAIM_KEY, this.getObjectMapper().writeValueAsString(object))
			           .setSubject(tokenSubject)
			           .signWith(this.getSigningKey())
			           .setExpiration(expirationDate)
			           .compact();
		}
		catch (JsonProcessingException e)
		{
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * decode the "data" claim of a jwt token
	 * @param jwt - the jwt to decode
	 * @param tokenSubject - the token subject that the jwt MUST have.
	 * @param dataClass - the class that the data should be deserialized in to.
	 * @param <T> - type of the returned data
	 * @return - data claim from jwt.
	 */
	public <T> T decodeJwt(String jwt, String tokenSubject, Class<T> dataClass)
	{
		try
		{
			// if token is bearer token strip out "Bearer " prefix
			if (jwt.startsWith(BEARER_PREFIX))
			{
				jwt = jwt.replaceFirst("^" + BEARER_PREFIX, "");
			}

			String json = Jwts.parserBuilder()
			                  .setSigningKey(this.getSigningKey())
			                  .requireSubject(tokenSubject)
			                  .build()
			                  .parseClaimsJws(jwt).getBody().get(DATA_CLAIM_KEY, String.class);

			return this.getObjectMapper().readValue(json, dataClass);
		}
		catch (JsonProcessingException e)
		{
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	// ==========================================================================
	// Protected Methods
	// ==========================================================================

	protected Key getSigningKey()
	{
		return Keys.hmacShaKeyFor(this.secretKey.getBytes(StandardCharsets.UTF_8));
	}

	protected ObjectMapper getObjectMapper()
	{
		ObjectMapper objectMapper = new ObjectMapper();
		// add support for ZonedDateTime
		objectMapper.registerModule(new JavaTimeModule());
		return objectMapper;
	}
}
