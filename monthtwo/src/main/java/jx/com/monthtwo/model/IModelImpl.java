package jx.com.monthtwo.model;

import java.util.Map;

import jx.com.monthtwo.callback.ICallBack;
import jx.com.monthtwo.okhttp.OkCallBack;
import jx.com.monthtwo.okhttp.OkHttpUtils;

public class IModelImpl implements IModel {
    @Override
    public void requestData(String url, Map<String, String> params, Class clazz, final ICallBack iCallBack) {
        OkHttpUtils.getInstance().postEnqueue(url, params, clazz, new OkCallBack() {
            @Override
            public void success(Object obj) {
                iCallBack.setData(obj);
            }

            @Override
            public void failed(Exception e) {
                iCallBack.setData(e.getMessage());
            }
        });
    }
}
