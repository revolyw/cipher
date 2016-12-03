package dto;

/**
 * Created by Willow on 16/11/17.
 */
public class AjaxResult {
    private Boolean isSuccess;
    private String msg;
    private Object data;

    private AjaxResult() {
        this.isSuccess = true;
        this.msg = "";
        this.data = null;
    }

    //静态工厂方法
    public static AjaxResult newInstance() {
        return new AjaxResult();
    }

    public Boolean getSuccess() {
        return isSuccess;
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
