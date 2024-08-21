package konkuk.forcutbook.global.oauth.service;

import konkuk.forcutbook.global.oauth.exception.OAuthException;
import konkuk.forcutbook.global.oauth.exception.errorcode.OAuthExceptionErrorCode;
import konkuk.forcutbook.global.oauth.user.KakaoOAuth2UserInfo;
import konkuk.forcutbook.global.oauth.user.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {

        String registrationId = userRequest.getClientRegistration()
                .getRegistrationId();

        String accessToken = userRequest.getAccessToken().getTokenValue();

        OAuth2UserInfo oAuth2UserInfo = new KakaoOAuth2UserInfo(accessToken, oAuth2User.getAttributes());

        // OAuth2UserInfo field value validation
        if (!StringUtils.hasText(oAuth2UserInfo.getEmail())) {
            throw new OAuthException(OAuthExceptionErrorCode.NOT_VALID_EMAIL);
        }

        return new OAuth2UserPrincipal(oAuth2UserInfo);
    }
}
