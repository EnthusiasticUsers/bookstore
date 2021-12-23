package cn.itcast.com.util;



import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class DownUtil {
    public static String getFilename(String agent,String filename) throws UnsupportedEncodingException {
        if(agent.contains("MISE")){
            filename = URLEncoder.encode(filename,"utf-8");
            filename = filename.replace("+"," ");
        }else if(agent.contains("firefox") || agent.contains("chrome")) {
            filename = new String(filename.getBytes(), "ISO-8859-1");
        }
        else {
            filename = URLEncoder.encode(filename,"utf-8");
        }

        return filename;
    }
}
