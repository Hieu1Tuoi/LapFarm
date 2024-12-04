package LapFarm.Service;

import LapFarm.Model.GoogleConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class GoogleService {
	private final GoogleConfig googleConfig;
	private final ObjectMapper objectMapper;

	public GoogleService(GoogleConfig googleConfig) {
		this.googleConfig = googleConfig;
		this.objectMapper = new ObjectMapper();
	}

	// Generate Google Authorization URL
	public String generateAuthorizationUrl() {
		Map<String, String> params = new HashMap<>();
		params.put("client_id", googleConfig.getClientId());
		params.put("redirect_uri", googleConfig.getRedirectUri());
		params.put("response_type", "code");
		params.put("scope", GoogleConfig.SCOPE_PROFILE + " " + GoogleConfig.SCOPE_EMAIL);
		params.put("access_type", "online");

		StringBuilder urlBuilder = new StringBuilder(GoogleConfig.GOOGLE_AUTH_URL + "?");
		for (Map.Entry<String, String> entry : params.entrySet()) {
			urlBuilder.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8)).append("=")
					.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8)).append("&");
		}
		return urlBuilder.toString().replaceAll("&$", "");
	}

	// Exchange Authorization Code for Access Token
	public Map<String, Object> exchangeCodeForToken(String authorizationCode) throws Exception {
		URL url = new URL(GoogleConfig.GOOGLE_TOKEN_URL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setDoOutput(true);

		String postData = String.format(
				"client_id=%s&client_secret=%s&code=%s&grant_type=authorization_code&redirect_uri=%s",
				URLEncoder.encode(googleConfig.getClientId(), StandardCharsets.UTF_8),
				URLEncoder.encode(googleConfig.getClientSecret(), StandardCharsets.UTF_8),
				URLEncoder.encode(authorizationCode, StandardCharsets.UTF_8),
				URLEncoder.encode(googleConfig.getRedirectUri(), StandardCharsets.UTF_8));

		conn.getOutputStream().write(postData.getBytes(StandardCharsets.UTF_8));

		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		StringBuilder response = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			response.append(line);
		}
		reader.close();

		return objectMapper.readValue(response.toString(), Map.class);
	}

	// Retrieve User Information
	public Map<String, Object> getUserInfo(String accessToken) throws Exception {
		URL url = new URL(GoogleConfig.GOOGLE_USER_INFO_URL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Authorization", "Bearer " + accessToken);

		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		StringBuilder response = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			response.append(line);
		}
		reader.close();

		return objectMapper.readValue(response.toString(), Map.class);
	}
}