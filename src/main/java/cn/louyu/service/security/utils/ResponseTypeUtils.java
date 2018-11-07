package cn.louyu.service.security.utils;

import cn.louyu.utils.TextUtils;
import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseTypeUtils {
    private final static String RESPONSE_TYPE="response-type";

    public final static String JSON_CONTENT_TYPE="application/json";

    public static String getResponseType(HttpServletRequest request){
        return request.getHeader(RESPONSE_TYPE);
    }

    /**
     *根据客户端头决定是否要返回Json数据
     * */
    public static boolean isRespondJson(HttpServletRequest request){
        String type=getResponseType(request);
        if(TextUtils.isNullOrEmpty(type)||!JSON_CONTENT_TYPE.equals(type.trim())) return false;
        return true;
    }


    /**
     * 返回Json数据作为应答
     * */
    public static void respondJsonData(HttpServletResponse response,String json) throws IOException {
        respondJsonData(null,response,json);
    }

    /**
     * 返回Json数据作为应答
     * */
    public static void respondJsonData(HttpServletRequest request, HttpServletResponse response,String json) throws IOException {
        //json 格式 字符串 通过 response 以application/json;charset=UTF-8 格式写到响应里面去
        response.setContentType(ResponseTypeUtils.JSON_CONTENT_TYPE+";charset=UTF-8");
        response.getWriter().write(json);
    }
}
