package jx.com.monthtwo.model;

import java.util.Map;

import jx.com.monthtwo.callback.ICallBack;

public interface IModel {
    void requestData(String url, Map<String,String> params, Class clazz, ICallBack iCallBack);
}
