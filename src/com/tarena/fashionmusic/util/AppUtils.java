package com.tarena.fashionmusic.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


import com.tarena.fashionmusic.MyApplication;

/**
 * Created by iwanghang on 16/4/30.
 * APP工具�?
 * 在DRMPlayerApp创建context上下�?,在这里获取并调用
 */
public class AppUtils {
    //隐藏键盘
    public static void hideInputMethod(View view){
        InputMethodManager imm = (InputMethodManager) MyApplication.context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()){//判断键盘是否经激�?(弹出)
            imm.hideSoftInputFromWindow(view.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);//隐藏键盘
        }
    }
}
