package jx.com.monthtwo.custom;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class LableView extends LinearLayout {

    private int maxHeight;
    private int marginleft = 20;
    private int margintop = 20;

    public LableView(Context context) {
        super(context);
    }

    public LableView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LableView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthsize = MeasureSpec.getSize(widthMeasureSpec);
        int heightsize = MeasureSpec.getSize(widthMeasureSpec);

        measureChildren(widthMeasureSpec,heightMeasureSpec);
        findMaxHeight();

        int left = 0;
        int top = 0;

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            if(left != 0){
                if((left+view.getMeasuredWidth())>widthsize){
                    top = top+maxHeight+margintop;
                    left = 0;
                }
            }
            left = left+view.getMeasuredWidth()+marginleft;
        }
        setMeasuredDimension(widthsize,(top+maxHeight)>heightsize?heightsize:top+maxHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        findMaxHeight();
        int left = 0;
        int top = 0;

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            if(left != 0){
                if(left+view.getMeasuredWidth()>getWidth()){
                    top = top+maxHeight+margintop;
                    left = 0;
                }
            }
            view.layout(left,top,left+view.getMeasuredWidth(),top+maxHeight);
            left = left+view.getMeasuredWidth()+marginleft;
        }

    }

    private void findMaxHeight(){
        maxHeight = 0;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if(maxHeight<childAt.getMeasuredHeight()){
                maxHeight = childAt.getMeasuredHeight();
            }

        }
    }

}
