package util;

import cache.SelfCache;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import httpconstants.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;

public class GetWebUser {
    public void GetWebUser(){
        StringBuilder response = new StringBuilder();
        try {
            // 创建 URL 对象
            URL url = new URL(Constants.SERVER_LOGIN_URL+"/WebInfoServlet?backUrl=web1");
            // 打开 URL 连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // 设置请求方法为 GET
            connection.setRequestMethod("GET");
            // 设置连接超时时间
            connection.setConnectTimeout(5000);
            // 读取响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // 关闭连接
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SelfCache.userLoginMessage = tranGson(response.toString());
    }
    public static HashMap<String, Date> tranGson(String str){
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String, Date>>(){}.getType();
        return gson.fromJson(str, type);
    }
}
