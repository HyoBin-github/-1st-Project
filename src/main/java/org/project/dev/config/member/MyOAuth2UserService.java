package org.project.dev.config.member;
import org.project.dev.cartNew.entity.CartEntity;
import org.project.dev.cartNew.repository.CartRepository;
import org.project.dev.constrant.Role;
import org.project.dev.member.entity.MemberEntity;
import org.project.dev.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class MyOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        String clientId = clientRegistration.getClientId();
        String registrationId = clientRegistration.getRegistrationId();
        String registrationName = clientRegistration.getClientName();

        // 테스트하고 잘 나오면 지우기
        System.out.println("==================================");
        System.out.println("clientId : " + clientId);
        System.out.println("registrationId : " + registrationId);
        System.out.println("registrationName : " + registrationName);
        System.out.println("==================================");

        Map<String, Object> attributes = oAuth2User.getAttributes();

        for (String key : attributes.keySet()) {
            System.out.println(key + ": " + attributes.get(key));
        }

        String memberEmail = "";
        String memberPassword = "dlfwhghkdlxld";
        String memberName = "";
        String memberNickName = "";
        String memberPhone="";
        String memberBirth="";
        String memberPostCode="";


        if (registrationId.equals("google")) {
            System.out.println("google 로그인");

            memberEmail = (String) attributes.get("email");
            memberName = (String) attributes.get("name");

        } else if (registrationId.equals("naver")) {
            System.out.println("naver 로그인");

            Map<String, Object> response = (Map<String, Object>) attributes.get("response");

            memberEmail = (String) response.get("email");
            memberName= (String) response.get("name");
            memberNickName = (String) response.get("nickname");
//            memberPhone=(String) response.get("mobile");
            // 휴대전화번호 데이터 형식 변환
            memberPhone = transformPhoneNumber((String) response.get("mobile"));
            memberBirth=(String) response.get("birthday");
//            birth=(String) response.get("profile_image"); // 프로필 URL
//            birthYear=(String)response.get("birthyear");

            System.out.println((String) response.get("id"));
            System.out.println((String) response.get("email"));
            System.out.println((String) response.get("name"));

        } else if (registrationId.equals("kakao")) {
            System.out.println("kakao 로그인");

            Map<String, Object> response = (Map<String, Object>) attributes.get("kakao_account");
            System.out.println("response : " + response);
            System.out.println("response - email : " + response.get("email"));
            System.out.println("response - birth : " + response.get("birthday"));

            Map<String, Object> profile = (Map<String, Object>) response.get("profile");
            System.out.println("response - nickname : " + profile.get("nickname"));

            memberEmail = (String) response.get("email");
            memberNickName = (String) profile.get("nickname");
            memberBirth=(String) response.get("birthday");
//            name=(String) profile.get("name");
//            phone=(String) profile.get("phone_number");
//          birthYear=(String)profile.get("birthyear");
        }

        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberEmail);
        if (optionalMemberEntity.isPresent()) {
//            OAuthUser → DB 비교, 있으면
            return new MyUserDetails(optionalMemberEntity.get());
        }
        MemberEntity memberEntity = memberRepository.save(MemberEntity.builder()
                .memberEmail(memberEmail)
                .memberPassword(passwordEncoder.encode(memberPassword))
                .memberName(memberName)
                .memberNickName(memberNickName)
                .memberPhone(memberPhone)
                .memberBirth(memberBirth)
                .memberPostCode(memberPostCode)
                .role(Role.MEMBER)
                .build());
        
        // 회원가입 후 장바구니 생성
        createCartForMember(memberEntity);

        return new MyUserDetails(memberEntity, attributes);
    }
    
    // 장바구니 생성 메서드
    private void createCartForMember(MemberEntity memberEntity) {
        CartEntity cart = CartEntity.createCart(memberEntity);
        cartRepository.save(cart);
    }

    // 휴대전화번호 db에 저장될때 해당 형식으로 저장되도록 하는 메서드
    private String transformPhoneNumber(String memberPhone) {
        // 입력된 휴대전화번호에서 "-" 문자를 제거
        String cleanedPhoneNumber = memberPhone.replaceAll("-", "");

        return cleanedPhoneNumber;
    }

}
