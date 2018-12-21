package jx.com.monthtwo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import jx.com.monthtwo.R;
import jx.com.monthtwo.bean.PhoneBean;
import jx.com.monthtwo.custom.NumView;

public class ThingAdapter extends RecyclerView.Adapter<ThingAdapter.ViewHolder> {

    private List<PhoneBean.DataBean.ListBean> mlist;
    private Context context;

    public ThingAdapter(List<PhoneBean.DataBean.ListBean> mlist, Context context) {
        this.mlist = mlist;
        this.context = context;
    }

    @NonNull
    @Override
    public ThingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context,R.layout.thingitem,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThingAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.title.setText(mlist.get(i).getTitle());
        viewHolder.price.setText(mlist.get(i).getPrice()+"");
        viewHolder.checkBox.setChecked(mlist.get(i).isChecked());
        Glide.with(context).load(mlist.get(i).split()).into(viewHolder.imageView);
        viewHolder.numView.setEdit(this,mlist,i);

        viewHolder.numView.setCallBack(new NumView.EditCallBack() {
            @Override
            public void editcallback() {
                if (editCallBack != null){
                    editCallBack.editcallback();
                }
            }
        });
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mlist.get(i).setChecked(b);
                if(editCallBack != null){
                    editCallBack.editcallback();
                }
            }
        });
    }

    public void setcheck(boolean bool){
        for (PhoneBean.DataBean.ListBean listBean:mlist){
            listBean.setChecked(bool);
        }
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBox;
        private ImageView imageView;
        private TextView title,price;
        private NumView numView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.thing_checked);
            imageView = itemView.findViewById(R.id.thing_image);
            title = itemView.findViewById(R.id.thing_title);
            price = itemView.findViewById(R.id.thing_price);
            numView = itemView.findViewById(R.id.numview);
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
