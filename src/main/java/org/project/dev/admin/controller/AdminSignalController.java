package org.project.dev.admin.controller;


import lombok.RequiredArgsConstructor;
import org.project.dev.admin.service.AdminMainService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


/*\
TODO
admin 페이지의 모든 알림 controller
ex) 문의상태 알림 = 사이트에 항의가 들어왔는데 3일이나 지남 => 해당 제품 판매자에게 경고 알림

 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/signal")
public class AdminSignalController {

    private final AdminMainService adminMainService;

    /*
    TODO
    로그인 후 보여질 메인 페이지
    단순 페이지 연결
     */
    @GetMapping("/")
    public Map<String,Object> main(){
        Map<String,Object> response = new HashMap<String,Object>();
        //response.put("response",)
        return response;
    }
}
