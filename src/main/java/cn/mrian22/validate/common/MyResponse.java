package cn.mrian22.validate.common;

/**
 * @author 22
 * 自定义的返回类型
 */
public class MyResponse {
    private Integer status;
    private String message;
    private Object data;

    public MyResponse(String message) {
        this.message = message;
    }

    public MyResponse(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public MyResponse(Integer status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
}
