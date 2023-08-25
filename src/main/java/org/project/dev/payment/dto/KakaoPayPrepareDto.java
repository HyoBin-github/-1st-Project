package org.project.dev.payment.dto;

import lombok.*;
import org.project.dev.payment.entity.KakaoPayPrepareEntity;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KakaoPayPrepareDto {

    private String tid;
    private String tms_result;
    private String next_redirect_app_url;
    private String next_redirect_mobile_url;
    private String next_redirect_pc_url;
    private String android_app_scheme;
    private String ios_app_scheme;
    private String created_at;


<<<<<<< HEAD
    public static KakaoPayPrepareDto toDto(KakaoPayPrepareEntity kakaoPayPrepareEntity) {
=======
    public static KakaoPayPrepareDto toDto(){
<<<<<<< HEAD
        return null;
=======
>>>>>>> 0a0811effe07968bd7f7e1434146bf4b92d7cd72
>>>>>>> 37280a92cda0f8e4eb73187231ced0af56706b9d

        return KakaoPayPrepareDto.builder()
                .android_app_scheme(kakaoPayPrepareEntity.getAndroidAppScheme())
                .created_at(kakaoPayPrepareEntity.getCreatedAt())
                .ios_app_scheme(kakaoPayPrepareEntity.getIosAppScheme())
                .tid(kakaoPayPrepareEntity.getTid())
                .next_redirect_app_url(kakaoPayPrepareEntity.getNextRedirectAppUrl())
                .next_redirect_mobile_url(kakaoPayPrepareEntity.getNextRedirectMobileUrl())
                .next_redirect_pc_url(kakaoPayPrepareEntity.getNextRedirectPcUrl())
                .tms_result(kakaoPayPrepareEntity.getTmsResult())
                .build();
    }


}
