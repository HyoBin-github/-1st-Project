package org.project.dev.payment.dto;

import lombok.*;


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


    public static KakaoPayPrepareDto toDto(){
        return null;

    }


}
