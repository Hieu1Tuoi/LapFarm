package LapFarm.Model;

public class GoogleConfig {
	private String clientId;
	private String clientSecret;
	private String redirectUri;

	// Google OAuth endpoints
	public static final String GOOGLE_AUTH_URL = "https://accounts.google.com/o/oauth2/v2/auth";
	public static final String GOOGLE_TOKEN_URL = "https://oauth2.googleapis.com/token";
	public static final String GOOGLE_USER_INFO_URL = "https://www.googleapis.com/oauth2/v3/userinfo";

	// Scopes
	public static final String SCOPE_PROFILE = "https://www.googleapis.com/auth/userinfo.profile";
	public static final String SCOPE_EMAIL = "https://www.googleapis.com/auth/userinfo.email";

	// Constructors
	public GoogleConfig() {
	}

	public GoogleConfig(String clientId, String clientSecret, String redirectUri) {
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.redirectUri = redirectUri;
	}

	// Getters and Setters
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getRedirectUri() {
		return redirectUri;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

	public String generateAuthorizationUrl() {
		return GOOGLE_AUTH_URL + "?" + "client_id=" + clientId + "&" + "redirect_uri=" + redirectUri + "&"
				+ "response_type=code&" + "scope=" + SCOPE_PROFILE + "+" + SCOPE_EMAIL + "&" + "access_type=online";
	}
}