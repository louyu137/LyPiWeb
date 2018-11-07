package cn.louyu.models;

import java.io.Serializable;

/**
 * Created by LOUYU on 2018/4/12.
 */

public class ResultMsg<T extends Object> implements Serializable {
    public boolean Success;
    public String Msg;
    public T Data;
    public int Status;
    public int Count;

    public ResultMsg() {}

    public ResultMsg(boolean success, String msg) {
        this(success,msg,null);
    }

    public ResultMsg(boolean success, String msg, T data) {
       this(success,msg,data,0,0);
    }

    public ResultMsg(boolean success, String msg, T data, int status, int count) {
        Success = success;
        Msg = msg;
        Data = data;
        Status = status;
        Count = count;
    }

    public final String acquireNotNullMsg(){
        return Msg==null?"":Msg;
    }
}
