package jx.com.monthtwo.custom;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;

import jx.com.monthtwo.R;
import jx.com.monthtwo.adapter.ThingAdapter;
import jx.com.monthtwo.bean.PhoneBean;

public class NumView extends RelativeLayout implements View.OnClickListener{

    private EditText editText;
    private Context context;

    public NumView(Context context) {
        super(context);
        info(context);
        this.context = context;
    }

    public NumView(Context context, AttributeSet attrs) {
        super(context, attrs);
        info(context);
        this.context = context;
    }

    public NumView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        info(context);
        this.context = context;
    }
    public void info(Context context){
        View view = View.inflate(context, R.layout.numview,null);
        ImageView add = view.findViewById(R.id.add);
        ImageView jian = view.findViewById(R.id.jian);
        editText = view.findViewById(R.id.numedit);
        add.setOnClickListener(this);
        jian.setOnClickListener(this);
        addView(view);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    num = Integer.valueOf(charSequence.toString());
                    list.get(position).setNum(num);
                }catch (Exception e){
                    list.get(position).setNum(1);
                }
                if(editCallBack!=null){
                    editCallBack.editcallback();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add:
                num++;
                editText.setText(num+"");
                list.get(position).setNum(num);
                adapter.notifyDataSetChanged();
                editCallBack.editcallback();
                break;
            case R.id.jian:
                if(num>1){
                    num--;
                }else {
                    Toast.makeText(context,"没了",Toast.LENGTH_SHORT).show();
                }
                editText.setText(num+"");
                list.get(position).setNum(num);
                adapter.notifyDataSetChanged();
                editCallBack.editcallback();
                break;
                default:
                    break;
        }
    }
    ThingAdapter adapter;
    List<PhoneBean.DataBean.ListBean> list;
    int position;
    int num;
    public void setEdit(ThingAdapter adapter, List<PhoneBean.DataBean.ListBean> list,int i){
        this.adapter = adapter;
        this.list =list;
        position = i;
        num = list.get(i).getNum();
        editText.setText(num+"");

    }
    public EditCallBack editCallBack;
    public void setCallBack(EditCallBack editCallBack){
        this.editCallBack = editCallBack;
    }



    public interface EditCallBack{
        void editcallback();
    }
}
