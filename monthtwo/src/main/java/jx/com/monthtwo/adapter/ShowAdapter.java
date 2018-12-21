package jx.com.monthtwo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import jx.com.monthtwo.R;
import jx.com.monthtwo.activity.CarActivity;
import jx.com.monthtwo.bean.ShowBean;

public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.ViewHolder> {

    private List<ShowBean.DataBean> mlist;
    private Context context;

    public ShowAdapter(Context context) {
        this.context = context;
        mlist = new ArrayList<>();
    }

    public void setMlist(List<ShowBean.DataBean> list) {
        mlist.clear();
        if(list != null){
            mlist.addAll(list);
        }
        notifyDataSetChanged();
    }
    public void addMlist(List<ShowBean.DataBean> list) {
        if(list != null){
            mlist.addAll(list);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ShowAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.showitem,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.title.setText(mlist.get(i).getTitle());
        viewHolder.price.setText(mlist.get(i).getPrice()+"");
        Glide.with(context).load(mlist.get(i).split()).into(viewHolder.imageView);
        viewHolder.but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setonClick.setClick(mlist.get(i).getPid());
            }
        });
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CarActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView title,price;
        private Button but;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.show_item_iamge);
            title = itemView.findViewById(R.id.show_item_title);
            price = itemView.findViewById(R.id.show_item_price);
            but = itemView.findViewById(R.id.show_item_but);
        }
    }

    public setonClickListener setonClick;
    public void setCallBack(setonClickListener editCallBack){
        this.setonClick = editCallBack;
    }
    public interface setonClickListener{
        void setClick(int pid);
    }

}
