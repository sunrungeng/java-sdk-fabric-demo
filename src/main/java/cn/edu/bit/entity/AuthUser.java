package cn.edu.bit.entity;

public class AuthUser {

    private String identify;
    private String belongto;

    public AuthUser() {
    }

    public AuthUser(String identify, String belongto) {
        this.identify = identify;
        this.belongto = belongto;
    }

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public String getBelongto() {
        return belongto;
    }

    public void setBelongto(String belongto) {
        this.belongto = belongto;
    }
}
