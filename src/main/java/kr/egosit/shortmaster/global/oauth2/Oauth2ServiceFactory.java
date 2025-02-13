package kr.egosit.shortmaster.global.oauth2;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class Oauth2ServiceFactory {
    private final Map<String, Oauth2Service> oauth2ServiceMap;


    public Oauth2ServiceFactory(List<Oauth2Service> services) {
        this.oauth2ServiceMap = services.stream()
                .collect(Collectors.toMap(
                        s -> s.getClass().getSimpleName().replace("Oauth2Service", "").toLowerCase()
                        ,service -> service));
    }

    public Oauth2Service getOauth2Service(String provider) {
        return oauth2ServiceMap.get(provider.toLowerCase());
    }
}
