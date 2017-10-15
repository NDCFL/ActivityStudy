package com.fl.comment;

/**
 * Created by chenfeilong on 2017/10/13.
 */
public enum Test {

    USER_REGISTER_SUCCESS(1,"注册成功！"),
    USER_REGISTER_ACCOUNT_HAVING(2,"账户已经存在！"),
    USER_REGISTER_FAIL(3,"注册失败！");
    Test(int code, String enc) {
        this.code = code;
        this.enc = enc;
    }
    private int code;
    private String enc;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getEnc() {
        return enc;
    }

    public void setEnc(String enc) {
        this.enc = enc;
    }


}
