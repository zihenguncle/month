package jx.com.monthtwo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jx.com.monthtwo.R;
import jx.com.monthtwo.adapter.CarAdapter;
import jx.com.monthtwo.bean.PhoneBean;
import jx.com.monthtwo.persenter.IPersenterImpl;
import jx.com.monthtwo.url.Apis;
import jx.com.monthtwo.view.IView;

public class CarActivity extends AppCompatActivity implements IView,View.OnClickListener {

    private RecyclerView recyclerView;
    private CheckBox checkBox;
    private TextView car_price;
    private Button jiesuan;
    private IPersenterImpl iPersenter;
    private CarAdapter adapter;
    private PhoneBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);
        initView();
        initData();
    }

    private void initData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CarAdapter(this);
        recyclerView.setAdapter(adapter);
        info();
        adapter.setCallBack(new CarAdapter.EditCallBack() {

            @Override
            public void editcallback() {
                int num=0;
                double price = 0;
                int count = 0;
                List<PhoneBean.DataBean> data = bean.getData();
                for (int i = 0; i < data.size(); i++) {
                    List<PhoneBean.DataBean.ListBean> list = data.get(i).getList();
                    for (int j = 0; j < list.size(); j++) {
                        count+= list.get(j).getNum();
                        if(list.get(j).isChecked()){
                            num+=list.get(j).getNum();
                            price+=list.get(j).getPrice()*num;
                        }
                    }
                }
                if(count>num){
                    checkBox.setChecked(false);
                }else {
                    checkBox.setChecked(true);
                }
                car_price.setText("总价:"+price);
                jiesuan.setText("去结算"+num);
            }
        });
    }

    private void info() {
        Map<String,String> map = new HashMap<>();
        map.put("uid",1234+"");
        iPersenter.startRequest(Apis.GETADD,map,PhoneBean.class);
    }

    private void initView() {
        iPersenter = new IPersenterImpl(this);
        recyclerView = findViewById(R.id.car_recycle);
        checkBox = findViewById(R.id.car_checkedall);
        car_price = findViewById(R.id.car_price);
        jiesuan = findViewById(R.id.car_but);
        checkBox.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.car_checkedall:
                setcheck(checkBox.isChecked());
                adapter.notifyDataSetChanged();
                break;
                default:
                    break;
        }
    }

    private void setcheck(boolean checked) {
        List<PhoneBean.DataBean> data = bean.getData();
        int num = 0;
        double price = 0;
        for (int i = 0; i < data.size(); i++) {
            List<PhoneBean.DataBean.ListBean> list = data.get(i).getList();
            data.get(i).setCheck(checked);
            for (int j = 0; j < list.size(); j++) {
                list.get(j).setChecked(checked);
                num+=list.get(j).getNum();
                price+=list.get(j).getPrice()*num;
            }
        }
        if(checked){
            car_price.setText("总价:"+price);
            jiesuan.setText("去结算"+num);
        }else {
            car_price.setText("总价:"+0.0);
            jiesuan.setText("去结算"+0.0);
        }
    }

    @Override
    public void success(Object data) {
        bean = (PhoneBean) data;
        bean.getData().remove(0);
        adapter.setMdata(bean.getData());
    }
}
