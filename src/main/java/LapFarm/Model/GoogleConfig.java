/*
 * package LapFarm.Model;
 * 
 * import org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.security.oauth2.client.registration.ClientRegistration;
 * import org.springframework.security.oauth2.client.registration.
 * ClientRegistrationRepository; import
 * org.springframework.security.oauth2.client.registration.
 * InMemoryClientRegistrationRepository;
 * 
 * @Configuration public class GoogleConfig {
 * 
 * @Bean public ClientRegistrationRepository clientRegistrationRepository() {
 * return new InMemoryClientRegistrationRepository(googleClientRegistration());
 * }
 * 
 * private ClientRegistration googleClientRegistration() { return
 * ClientRegistration.withRegistrationId("google").clientId(
 * "YOUR_GOOGLE_CLIENT_ID") // Thay bằng Google // Client ID của bạn
 * .clientSecret("YOUR_GOOGLE_CLIENT_SECRET") // Thay bằng Google Client Secret
 * của bạn .scope("openid", "profile",
 * "email").authorizationUri("https://accounts.google.com/o/oauth2/auth")
 * .tokenUri("https://oauth2.googleapis.com/token")
 * .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo").
 * userNameAttributeName("sub")
 * .clientName("Google").redirectUri("{baseUrl}/login/oauth2/code/google").build
 * (); } }
 */