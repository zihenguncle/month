package jx.com.monthtwo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.Map;

import jx.com.monthtwo.MainActivity;
import jx.com.monthtwo.R;
import jx.com.monthtwo.adapter.ShowAdapter;
import jx.com.monthtwo.bean.AddBean;
import jx.com.monthtwo.bean.ShowBean;
import jx.com.monthtwo.persenter.IPersenterImpl;
import jx.com.monthtwo.url.Apis;
import jx.com.monthtwo.view.IView;

public class ShowActivity extends AppCompatActivity implements IView,View.OnClickListener {

    private EditText text;
    private ImageView search;
    private TextView zonghe,xiaoliang,jiage,shaixuan;
    private IPersenterImpl iPersenter;
    private XRecyclerView xrecyclerView;

    private ShowAdapter adapter;

    private int page;
    private int mSotr=0;
    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        initView();
        initData();
    }

    private void initData() {
        page = 1;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrecyclerView.setLayoutManager(layoutManager);
        xrecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                info();
            }

            @Override
            public void onLoadMore() {
                info();
            }
        });
        adapter = new ShowAdapter(this);
        xrecyclerView.setAdapter(adapter);

        adapter.setCallBack(new ShowAdapter.setonClickListener() {
            @Override
            public void setClick(int pid) {
                addinfo(pid);
            }
        });

    }
    public void addinfo(int pid){
        Map<String,String> map = new HashMap<>();
        map.put("uid",1234+"");
        map.put("pid",pid+"");
        iPersenter.startRequest(Apis.ADD,map,AddBean.class);
    }
    private void info(){
        Map<String,String> map = new HashMap<>();
        map.put("keywords",name);
       map.put("page",page+"");
        map.put("sort",mSotr+"");
        iPersenter.startRequest(Apis.CAR,map,ShowBean.class);
    }



    private void initView() {
        iPersenter = new IPersenterImpl(this);
        text = findViewById(R.id.edit_search);
        search = findViewById(R.id.image_search);
        zonghe = findViewById(R.id.zonghe);
        xiaoliang = findViewById(R.id.xiaoliang);
        jiage = findViewById(R.id.jiage);
        shaixuan = findViewById(R.id.shaixuan);
        zonghe.setOnClickListener(this);
        xiaoliang.setOnClickListener(this);
        jiage.setOnClickListener(this);
        shaixuan.setOnClickListener(this);
        xrecyclerView = findViewById(R.id.xrecycle);
        search.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //搜索请求数据
            case R.id.image_search:
                page=1;
                mSotr=0;
                name = text.getText().toString();
                info();
                break;
            case R.id.zonghe:
                page=1;
                mSotr=0;
                info();
                break;
            case R.id.xiaoliang:
                page=1;
                mSotr=1;
                info();
                break;
            case R.id.jiage:
                page=1;
                mSotr=2;
                info();
                break;
                default:
                    break;
        }
    }

    @Override
    public void success(Object data) {

        if (data instanceof ShowBean){
            ShowBean bean = (ShowBean) data;
            if(page == 1){
                adapter.setMlist(bean.getData());
            }else {
                adapter.addMlist(bean.getData());
            }
            page++;
            xrecyclerView.loadMoreComplete();
            xrecyclerView.refreshComplete();
        } else if (data instanceof AddBean){
            AddBean bean = (AddBean) data;
            String msg = bean.getMsg();
            Toast.makeText(ShowActivity.this,msg,Toast.LENGTH_SHORT).show();
        }


    }
}
