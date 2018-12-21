package jx.com.monthtwo.persenter;

import java.util.Map;

public interface IPersenter {
    void startRequest(String url, Map<String,String> params,Class clazz);
}
