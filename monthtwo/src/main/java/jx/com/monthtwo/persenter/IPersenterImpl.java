package jx.com.monthtwo.persenter;

import java.util.Map;

import jx.com.monthtwo.callback.ICallBack;
import jx.com.monthtwo.model.IModelImpl;
import jx.com.monthtwo.view.IView;

public class IPersenterImpl implements IPersenter {
    private IView iView;
    private IModelImpl iModel;

    public IPersenterImpl(IView iView) {
        this.iView = iView;
        iModel = new IModelImpl();
    }

    @Override
    public void startRequest(String url, Map<String, String> params, Class clazz) {
        iModel.requestData(url, params, clazz, new ICallBack() {
            @Override
            public void setData(Object data) {
                iView.success(data);
            }
        });
    }
}
