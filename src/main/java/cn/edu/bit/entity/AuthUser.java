package cn.edu.bit.entity;

public class AuthUser {

    private String belongto;
    private String identify;
    private String zone;
    private int idCount;
    private int deviceCount;
    private String publicParameter;
    private String addTime;

    public AuthUser() {
    }

    public AuthUser(String belongto, String identify, String zone, int idCount, int deviceCount, String publicParameter, String addTime) {
        this.belongto = belongto;
        this.identify = identify;
        this.zone = zone;
        this.idCount = idCount;
        this.deviceCount = deviceCount;
        this.publicParameter = publicParameter;
        this.addTime = addTime;
    }

    public String getBelongto() {
        return belongto;
    }

    public void setBelongto(String belongto) {
        this.belongto = belongto;
    }

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public int getIdCount() {
        return idCount;
    }

    public void setIdCount(int idCount) {
        this.idCount = idCount;
    }

    public int getDeviceCount() {
        return deviceCount;
    }

    public void setDeviceCount(int deviceCount) {
        this.deviceCount = deviceCount;
    }

    public String getPublicParameter() {
        return publicParameter;
    }

    public void setPublicParameter(String publicParameter) {
        this.publicParameter = publicParameter;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
}
