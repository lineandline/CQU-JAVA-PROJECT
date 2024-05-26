package util;

import httpconstants.Constants;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

//检查令牌是否真实
public class CheckUtil {
    private String username;

    public String checkST(String st) {
        // 创建 HttpClient 对象
        HttpClient httpClient = HttpClients.createDefault();

        // 创建 HttpPost 对象，并设置请求的 URL
        HttpPost httpPost = new HttpPost(Constants.SERVER_LOGIN_URL+"/StCheckServlet");

        // 设置请求体的内容，这里可以添加其他需要发送的参数
        StringEntity requestEntity = new StringEntity("ST=" + st, "UTF-8");
        httpPost.setEntity(requestEntity);

        // 执行 POST 请求并获取响应
        try {
            HttpResponse response = httpClient.execute(httpPost);

            // 从响应中获取响应体
            HttpEntity entity = response.getEntity();
            username = EntityUtils.toString(entity);

            // 释放资源
            EntityUtils.consume(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return username;
    }
}
