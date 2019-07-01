package httpHelper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpHelper {
    String url = "http://c0d43173.ngrok.io";
    Map<String,String> param = new HashMap<>();

    public HttpHelper(String url, Map<String, String> param) {
        this.url += url;
        this.param = param;
    }
    public HttpHelper(String url) {
        this.url += url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getParam() {
        return param;
    }

    public void setParam(Map<String, String> param) {
        this.param = param;
    }

    public String execute(){
        String result = null;
        String urlstr = this.url;
        boolean firstIter = false;
        if (this.param != null) {
            urlstr += "?";
            for (Map.Entry<String, String> entry : this.param.entrySet()) {
                if(firstIter){
                   firstIter = false;
                }else
                    {
                        urlstr += "&";
                    }
                urlstr += entry.getKey() + "=" + entry.getValue();
            }
        }
        try {
            InputStream is = null;
            URL url = new URL(urlstr);
            is = url.openStream();
            result = new BufferedReader(new InputStreamReader(is))
                    .lines().collect(Collectors.joining("\n"));
        }catch (Exception e){
                System.err.println(e);
        }
        return result;
    }
}
