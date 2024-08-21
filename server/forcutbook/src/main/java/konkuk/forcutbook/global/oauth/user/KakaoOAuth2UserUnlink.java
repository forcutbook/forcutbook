package konkuk.forcutbook.global.oauth.user;

public class KakaoOAuth2UserUnlink {
    private static final String URL = "https://kapi.kakao.com/v1/user/unlink";
//    private final RestTemplate restTemplate;

    public void unlink(String accessToken) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(accessToken);
//        HttpEntity<Object> entity = new HttpEntity<>("", headers);
//        restTemplate.exchange(URL, HttpMethod.POST, entity, String.class);
    }
}
