package framework.model.util;

/**
 * Created by Willow on 16/11/17.
 */
public class Return {
    private Boolean isSuccess;
    private String msg;
    private Object data;

    private Return() {
        this.isSuccess = true;
        this.msg = "";
        this.data = null;
    }

    //静态工厂方法
    public static Return newInstance() {
        return new Return();
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Object data) {
        this.isSuccess = true;
        this.data = data;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


}
