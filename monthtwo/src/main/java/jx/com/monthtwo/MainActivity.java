package jx.com.monthtwo;

import android.graphics.Color;
import android.nfc.tech.NfcA;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import jx.com.monthtwo.custom.HeadView;
import jx.com.monthtwo.custom.LableView;

public class MainActivity extends AppCompatActivity {

    private HeadView headView;
    private LableView lableView,love_lable;
    private String[] str = new String[]{"耐克男鞋","巴拉巴拉童装","iphone7手机壳","卫衣男","男鞋运动针织","裤子男休闲","喜欢的草莓"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        //添加喜欢的str
        setLoveLable();
        //添加搜索的内容
        setrecycle();
    }

    //添加搜索的内容
    private void setrecycle() {
        headView.setCallBack(new HeadView.EditCallBack() {
            @Override
            public void editcallback(String name) {
                TextView textView = new TextView(MainActivity.this);
                textView.setText(name);
                textView.setTextColor(Color.BLACK);
                textView.setTextSize(20);
                lableView.addView(textView);
            }
        });
    }

    //添加喜欢的lable
    private void setLoveLable() {
        for (int i = 0; i < str.length; i++) {
            TextView textView = new TextView(this);
            textView.setText(str[i]);
            textView.setTextColor(Color.GRAY);
            textView.setTextSize(18);
            love_lable.addView(textView);
        }
    }

    private void initView() {
        headView = findViewById(R.id.headview);
        lableView = findViewById(R.id.lableview);
        love_lable = findViewById(R.id.xihuan_lable);
    }
}
