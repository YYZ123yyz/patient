package www.patient.jykj_zxyl.base.http;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:公用App请求Url配置
 *
 * @author: qiuxinhai
 * @date: 2018/5/31 16:10
 */
public class AppUrlConfig {

    /**线上Api OnLine Key*/
    public static String SERVICE_API_ONLINE_KEY="service_api_online_key";
    /**线上Api OnLine Url*/
    public static String SERVICE_API_ONLINE_URL="https://www.jiuyihtn.com:38081/";

    /**线下Api Test Key*/
    public static String SERVICE_API_TEST_KEY="service_api_test_key";

//    /**线下Api Test key*/
//    public static String SERVICE_API_TEST_URL="http://114.215.137.171:28080/";

    /**线下Api Test key*/
    public static String SERVICE_API_TEST_URL="https://www.jiuyihtn.com:38081/";
    /**
     * 获取所有的url
     *新·
     * @return map
     */
    public static Map<String, String> getAllApiUrl() {
        Map<String, String> map = new HashMap<>();
        map.put(SERVICE_API_ONLINE_KEY, SERVICE_API_ONLINE_URL);
        map.put(SERVICE_API_TEST_KEY, SERVICE_API_TEST_URL);
        return map;
    }


}
