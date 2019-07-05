package ky.entity;

/**
 * 短信上游接口响应接口
 *
 * @author xiaomei
 */
public class PmsMessageRetuen extends BaseEntity {

    private String response; //00 提交成功 01 账号或密码错误 02 账号欠费 09 无效的接收方号码 10 网络或系统内部错误

    private String searchID; //检索发送的短信状态及回复内容

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getSearchID() {
        return searchID;
    }

    public void setSearchID(String searchID) {
        this.searchID = searchID;
    }

}
