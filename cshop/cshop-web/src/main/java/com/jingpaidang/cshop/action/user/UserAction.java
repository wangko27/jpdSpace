package com.jingpaidang.cshop.action.user;


import javax.annotation.Resource;

import com.jingpaidang.cshop.common.utils.ValidateUtil;
import com.jingpaidang.cshop.domain.user.Account;
import com.jingpaidang.cshop.rpc.jd.JdService;
import com.jingpaidang.cshop.rpc.tb.TbService;
import com.jingpaidang.cshop.service.PlatformRpcService;
import com.taobao.api.ApiException;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.jingpaidang.cshop.action.admin.BaseAction;
import com.jingpaidang.cshop.domain.user.User;
import com.jingpaidang.cshop.service.UserService;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;


@ParentPackage("user")
@Results(
        @Result(name = "index", location = "../move/move!srcShopItemList", type = "redirectAction")
)
public class UserAction extends BaseAction implements ModelDriven<User> {
    private static final Logger logger = LoggerFactory.getLogger(UserAction.class);

    private static final long serialVersionUID = -1576092130109256640L;

    @Resource
    private UserService userService;
    @Resource
    private PlatformRpcService platformRpcService;

    //授权信息返回
    private String code;
    private String state;
    private Integer flag;
    // 传递accountId参数
    private Integer accountId;
    //是否已经拥有平台帐号
    private Integer own;
    //平台的商店名称
    private String shopName;
    //
    private String errorMsg;

    private User model = new User();

    /**
     * 开始使用系统
     *
     * @return
     */
    public String toIndex() {

        return "login";
    }


    /**
     * 由平台进入系统,将account保存下来，并将id 传入
     *
     * @return
     */
    public String enterSystem() {
        if (flag != null) {
            try {
                Map<String, Object> oauthMsg;
                oauthMsg = platformRpcService.getOauthMsg(flag, code, state);


                Account account;
                account = userService.newOrUpdateAccount(oauthMsg);
                User user = userService.findUserByPid(account.getPlatformLoginId());
                setAccountId(account.getId());
                if (user == null) {
                    return "relation";
                }
                this.setSession("user", user);
                return "index";

            } catch (Exception e) {
                return ERROR;
            }


        }

        return ERROR;
    }

    /**
     * 注销用户
     *
     * @return
     */
    public String logout() {
        this.removeSession("user");
        return "login";
    }

    /**
     * 验证email是否被注册
     */
    public String validateEmail() {
        Boolean b = userService.validateEmail(model.getUserEmail());

        return ajax((Object) b);
    }

    /**
     * 注册关联account
     *
     * @return
     */

    public String registAndRelate() {
        userService.saveUser(model);


        if (accountId != null) {

            userService.relateAccount2User(model, accountId, shopName);
        }

        setSession("user", model);

        return "index";
    }

    /**
     * 登录关联 account
     */
    public String loginAndRelate() {
        User login = userService.login(model);
        if (login == null) {

            setErrorMsg("登录名或密码错误");
            return "relation";
        }
        if (accountId != null) {

            userService.relateAccount2User(login, accountId, shopName);
        }

        setSession("user", login);

        return "index";
    }

    /**
     * 用户登录
     */
    public String login() {
        User login = userService.login(model);
        if (login == null) {

            setErrorMsg("登录名或密码错误");
            return "login";
        }
        setSession("user", login);

        return "index";
    }

    /**
     * 用户注册
     */
    public String reg() {
        userService.saveUser(model);
        if (accountId != null) {

            userService.relateAccount2User(model, accountId, shopName);
        }

        setSession("user", model);

        return "index";
    }

    /**
     * 跳转到登录页
     *
     * @return
     */
    public String loginPage() {
        return "login";
    }

    /**
     * 跳转到注册页
     *
     * @return
     */
    public String regPage() {
        return "reg";
    }

    @Override
    public User getModel() {

        return model;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }


    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Integer getOwn() {
        return own;
    }

    public void setOwn(Integer own) {
        this.own = own;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
