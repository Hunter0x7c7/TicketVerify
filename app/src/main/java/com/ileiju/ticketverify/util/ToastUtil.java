package com.ileiju.ticketverify.util;

import android.content.Context;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.widget.Toast;

import com.ileiju.ticketverify.base.BaseApplication;

import org.xutils.common.util.DensityUtil;

/**
 * 创建日期： 2017/2/28 16:50
 * 描    述：土司工具类
 */

public class ToastUtil {

    private static Toast mToast;
    public static final int LENGTH_LONG = Toast.LENGTH_LONG;
    public static final int LENGTH_SHORT = Toast.LENGTH_SHORT;

    /**
     * 显示Toast
     *
     * @param showContent 要显示的内容
     */
    public static synchronized void showDebugInfo(CharSequence showContent) {
        showDebugInfo(showContent, true);
    }

    /**
     * 显示Toast
     *
     * @param showContent 要显示的内容
     * @param isPrintln   是否打印显示的内容到控制台
     */
    public static synchronized void showDebugInfo(CharSequence showContent, boolean isPrintln) {
        if (ConfigUtil.isDebugMode()) {

            if (mToast != null) {
                mToast.setText(showContent);
            } else {
                mToast = Toast.makeText(BaseApplication.getContext(), showContent, LENGTH_SHORT);
            }
            mToast.show();

            if (isPrintln) {
                System.out.println("Toast- " + showContent);
            }
        }
    }

    /**
     * 显示一个土司，如果已经显示一个土司则先清空
     *
     * @param showContentId 需要显示的内容
     */
    public static void showDebugInfo(@StringRes int showContentId) {
        showDebugInfo(BaseApplication.getContext().getResources().getString(showContentId));
    }


    /**
     * 显示一个土司，如果已经显示一个土司则先清空
     *
     * @param showContentId 需要显示的内容
     */
    public static void showPrompt(@StringRes int showContentId) {
        showPrompt(BaseApplication.getContext().getResources().getString(showContentId));
    }


    /**
     * 显示Toast
     *
     * @param showContent 要显示的内容
     */
    public static void showPrompt(CharSequence showContent) {
        showPrompt(showContent, LENGTH_SHORT, -1,0,0);
    }

    /**
     * 显示Toast
     *
     * @param showContentId 要显示的内容
     */
    public static synchronized void showPrompt(@StringRes int showContentId, int duration ) {
        Context context = BaseApplication.getContext();
        String string = context.getResources().getString(showContentId);
        showPrompt(context, string, duration, -1,0,0);
    } /**
     * 显示Toast
     *
     * @param showContentId 要显示的内容
     */
    public static synchronized void showPrompt(@StringRes int showContentId, int duration, int gravity, int xOffset, int yOffset) {
        Context context = BaseApplication.getContext();
        String string = context.getResources().getString(showContentId);
        showPrompt(context, string, duration, gravity,xOffset,yOffset);
    }

    /**
     * 显示Toast
     *
     * @param showContent 要显示的内容
     */
    public static synchronized void showPrompt(CharSequence showContent, int duration ) {
        showPrompt(BaseApplication.getContext(), showContent, duration, -1,0,0);
    }

    /**
     * 显示Toast
     *
     * @param showContent 要显示的内容
     */
    public static synchronized void showPrompt(CharSequence showContent, int duration, int gravity, int xOffset, int yOffset) {
        showPrompt(BaseApplication.getContext(), showContent, duration, gravity,xOffset,yOffset);
    }

    /**
     * 显示Toast
     *
     * @param context     上下文
     * @param showContent 要显示的内容
     */
    public static synchronized void showPrompt(Context context, CharSequence showContent
            , int showDuration, int showGravity, int xOffset, int yOffset) {

        if (mToast != null) {
            mToast.cancel();
        }
        if (showDuration != -1) {
            mToast = Toast.makeText(context, showContent, showDuration);
        } else {
            mToast = Toast.makeText(context, showContent, Toast.LENGTH_SHORT);
        }

        if (showGravity == -1) {
            showGravity = Gravity.BOTTOM;
            xOffset = 0;
            yOffset = DensityUtil.dip2px(50);
        }
        mToast.setGravity(showGravity, xOffset, yOffset);
        mToast.show();

        SystemUtil.println("Toast- " + showContent);
    }

    /**
     * 清除土司的显示
     */
    public static void clearToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }
}
