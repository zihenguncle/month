package jx.com.monthtwo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jx.com.monthtwo.R;
import jx.com.monthtwo.bean.PhoneBean;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder> {

    private List<PhoneBean.DataBean> mdata;
    private Context context;

    public CarAdapter(Context context) {
        this.context = context;
        mdata = new ArrayList<>();
    }

    public void setMdata(List<PhoneBean.DataBean> mdata) {
        this.mdata = mdata;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.sjitem,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CarAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.title.setText(mdata.get(i).getSellerName());
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        viewHolder.recyclerView.setLayoutManager(layoutManager);
        viewHolder.checkBox.setChecked(mdata.get(i).isCheck());
        final ThingAdapter adapter = new ThingAdapter(mdata.get(i).getList(),context);
        viewHolder.recyclerView.setAdapter(adapter);
        adapter.setCallBack(new ThingAdapter.EditCallBack() {
            @Override
            public void editcallback() {
                if(editCallBack!=null){
                    editCallBack.editcallback();
                }
                boolean ischeck = true;
                List<PhoneBean.DataBean.ListBean> list = mdata.get(i).getList();
                for (PhoneBean.DataBean.ListBean listBean : list){
                    if(!listBean.isChecked()){
                        ischeck = false;
                        break;
                    }

                }
                mdata.get(i).setCheck(ischeck);
                viewHolder.checkBox.setChecked(ischeck);
            }
        });
        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mdata.get(i).setCheck(viewHolder.checkBox.isChecked());
                adapter.setcheck(viewHolder.checkBox.isChecked());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBox;
        private TextView title;
        private RecyclerView recyclerView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.sj_checked);
            title = itemView.findViewById(R.id.sj_title);
            recyclerView = itemView.findViewById(R.id.sj_recycle);
        }
    }
    public EditCallBack editCallBack;
    public void setCallBack(EditCallBack editCallBack){
        this.editCallBack = editCallBack;
    }
    public interface EditCallBack{
        void editcallback();
    }
}
