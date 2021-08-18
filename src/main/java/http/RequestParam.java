package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpRequestUtils;

import java.util.HashMap;
import java.util.Map;

public class RequestParam {

    private static final Logger log = LoggerFactory.getLogger(RequestParam.class);

    private Map<String, String> params = new HashMap<String, String>();

    public void setQueryString(String queryString) {
        addParamter(queryString);
    }

    public void setBody(String queryString) {
        addParamter(queryString);
    }

    private void addParamter(String data) {
        log.debug("data : {}", data);

        if (data == null || data.isEmpty()) {
            return;
        }

        params.putAll(HttpRequestUtils.parseQueryString(data));
        log.debug("params : {}", params);
    }

    public String getParameter(String name) {
        return params.get(name);
    }

}
