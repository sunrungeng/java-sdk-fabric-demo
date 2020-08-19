package cn.edu.bit.common;

import net.sf.json.JSONObject;

public class JsonView {

    //错误代码 0-成功
    private Integer code = 0;

    // 消息
    private String message;

    // 数据
    private Object data;

    public static String render(Object data){
        JsonView tmp = new JsonView(0, "success",data);
        return JSONObject.fromObject(tmp).toString();
    }

    public static String render(Integer code){
        JsonView tmp = new JsonView(code, "");
        return JSONObject.fromObject(tmp).toString();
    }

    public static String render(Integer code, String message){
        JsonView tmp = new JsonView(code, message);
        return JSONObject.fromObject(tmp).toString();
    }

    public static String render(Integer code, String message, Object data){
        JsonView tmp = new JsonView(code, message, data);
        return JSONObject.fromObject(tmp).toString();
    }

    public JsonView(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public JsonView(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public JsonView(Integer code) {
        this.code = code;
    }

    public JsonView() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String toString(){
        return JSONObject.fromObject(this).toString();
    }

}