package Utils;

import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.security.cert.X509Certificate;

import javax.net.ssl.*;

import com.alibaba.fastjson.JSONObject;
import com.by.constant.Paths;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
public class CommonUtils {

    /**
     * 类名: MyX509TrustManager.java</br>
     * 描述: 信任管理器</br>
     * 开发人员：wangl</br>
     * 创建时间： 2018-01-09</br>
     */
    public static class MyX509TrustManager implements X509TrustManager {

        // 检查客户端证书
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        // 检查服务器端证书
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        // 返回受信任的X509证书数组
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }

    /**
     * 发送https请求
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);

            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            jsonObject = JSONObject.parseObject(buffer.toString());
        } catch (ConnectException ce) {
            System.out.println("连接超时");
        } catch (Exception e) {
            System.out.println("请求异常");
        }
        return jsonObject;
    }

    public static String CurrentTime(){
        Date day=new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(day);
        //System.out.println(df.format(day));
    }

    public static String getSavePath(String openid){
        String realSavePath;
        if(System.getProperty("file.separator").equals("/")) {
            realSavePath = Paths.linuxSavePath;
        }else{
            realSavePath = Paths.windowsSavePath;
        }
        int hashcode = openid.hashCode();
        int dir = hashcode % 200;

        String savepath = realSavePath + File.separator + dir + File.separator;
        File file = new File(savepath);
        if(!file.exists()) {
            file.mkdirs();
        }

        return savepath;
    }
}
