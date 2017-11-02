package com.elong.nbapi.login.model;

/**
 * (类型功能说明描述) TODO
 * <p/>
 * <p>
 * 修改历史:											<br>
 * 修改日期    		修改人员   	版本	 		修改内容	<br>
 * -------------------------------------------------<br>
 * 2017/7/26   qianqian.xu     1.0			初始化创建<br>
 * </p>
 *
 * @author qianqian.xu
 * @department northbound
 */
public class AosTokenInfo {

    //内部员工id
    private String id;

    //内部员工域账户名
    private String userName;

    //内部员工姓名
    private String realName;

    //分机号码
    private String ext;

    //邮箱
    private String email;

    //手机号码
    private String phone;

    //内部员工类型
    private String userType;

    //内部员工状态
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
