package konkuk.forcutbook.global.oauth.user;

import java.util.Map;

public interface OAuth2UserInfo {
    String getAccessToken();

    Map<String, Object> getAttributes();

    String getId();

    String getEmail();

    String getNickname();

    String getProfileImageUrl();
}
