package jx.com.monthtwo.custom;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import jx.com.monthtwo.R;
import jx.com.monthtwo.activity.ShowActivity;

public class HeadView extends LinearLayout {
    public HeadView(Context context) {
        super(context);
        info(context);
    }

    public HeadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        info(context);
    }

    public HeadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        info(context);
    }
    public void info(final Context context){
        View view = View.inflate(context, R.layout.headview,null);
        ImageView back = view.findViewById(R.id.headview_back);
        ImageView search = view.findViewById(R.id.headview_search);
        final EditText editText = view.findViewById(R.id.headview_edit);
        addView(view);
        search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                editCallBack.editcallback(editText.getText().toString());
            }
        });
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShowActivity.class);
                context.startActivity(intent);
            }
        });
    }

    public EditCallBack editCallBack;
    public void setCallBack(EditCallBack editCallBack){
        this.editCallBack = editCallBack;
    }
    public interface EditCallBack{
        void editcallback(String name);
    }

}
