package com.jingpaidang.crm.controller.oauth;

import java.io.IOException;
import java.util.Map;

import com.jingpaidang.crm.rpc.jos.JdService;
import com.jingpaidang.crm.rpc.jos.TokenService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: JackChan
 * Date: 8/6/13
 * Time: 10:42 AM
 */
@Controller
@RequestMapping("/oauth")
public class OauthController {
    @Value("${jd.redirect.uri}")
    private String REDIRECT_URI;
    @Value("${jd.app.key}")
    private String APP_KEY;

    @Resource
    private JdService jdService;
    
    @Resource
    private TokenService tokenService ;

    /**
     * 获取并保存商家信息
     */
    @RequestMapping(value = "accessToken", method = RequestMethod.GET)
    public String getAndSaveAccessToken(HttpServletRequest request, HttpServletResponse response) {

        String code = request.getParameter("code");
        String state = request.getParameter("state");

        try {
			Map<String, Object> map = tokenService.getAccessToken(code, state) ;
			System.out.println(map.get("access_token"));
			System.out.println(map.get("refresh_token"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return null;
    }

    /**
     * 跳转到京东授权页面进行授权
     *
     * @return
     */
    @RequestMapping("jdOauth")
    public String jdOauth(ModelMap modelMap) {


        modelMap.addAttribute("jdAppKey", APP_KEY);
        modelMap.addAttribute("jdRedirectUri", REDIRECT_URI);

        return "oauth/jdOauth";
    }
}
