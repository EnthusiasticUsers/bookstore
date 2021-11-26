package cn.itcast.com.util;

public class StatusUtil {

    public static String success(String msg){
        return "{\"code\":200, \"status\":\"success\", \"msg\":\"" + msg + "\"}";
    }

    public static String failed(String msg){
        return "{\"code\":500, \"status\":\"failed\", \"msg\":\"" + msg + "\"}";
    }
}
