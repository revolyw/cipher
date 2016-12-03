package model;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * 基础model pojo
 * 作用域仅限model包
 * Created by Willow on 12/4/16.
 */
abstract class BaseModel implements Serializable {

    private static final long serialVersionUID = 765461054764759834L;

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put(this.getClass().getSimpleName(), this);
        return obj;
    }

    public String toString() {
        return this.toJson().toString();
    }
}
