package com.wizardev.popdialogfrombottom;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private View contentView;
    private PopupWindow popupWindow;
    private Button mPopupAction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        initPopupWindow();
        mPopupAction = findViewById(R.id.popup_window);
        mPopupAction.setOnClickListener(this);
    }

    private void initPopupWindow() {
        //PopupWindow中的布局
        contentView = LayoutInflater.from(this).inflate(R.layout.popup_layout, null, false);
        //初始化PopupWindow，这里的三个参是必须的
        popupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //点击外部消失
        popupWindow.setOutsideTouchable(true);
        //设置可以点击
        popupWindow.setTouchable(true);
        //进入退出的动画
        popupWindow.setAnimationStyle(R.style.MyPopWindowAnim);
        //设置PopupWindow的背景
        //popupWindow.setBackgroundDrawable(new ColorDrawable(Color.GRAY));
       /* TextView dismiss = contentView.findViewById(R.id.dismiss);
        dismiss.setOnClickListener(this);*/
        /*TextView camera = contentView.findViewById(R.id.open_from_camera);
        camera.setOnClickListener(this);
        TextView album = contentView.findViewById(R.id.open_album);
        album.setOnClickListener(this);
        TextView cancel = contentView.findViewById(R.id.cancel);
        cancel.setOnClickListener(this);*/
    }

    private void showPopWindow() {
        //设置是否遮住状态栏
        fitPopupWindowOverStatusBar(true);
        View rootview = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_main, null);
        popupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }
    //弹出的窗口是否覆盖状态栏
    public void fitPopupWindowOverStatusBar(boolean needFullScreen) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                //利用反射重新mLayoutInScreen的值
                Field mLayoutInScreen = PopupWindow.class.getDeclaredField("mLayoutInScreen");
                mLayoutInScreen.setAccessible(true);
                mLayoutInScreen.set(popupWindow, needFullScreen);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.popup_window:
                showPopWindow();
                break;
        }
    }
}
