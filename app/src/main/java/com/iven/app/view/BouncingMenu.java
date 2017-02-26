package com.iven.app.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

/**
 * @author Iven
 * @date 2017/2/26 19:07
 */

public class BouncingMenu {
    private View rootView;
    private ViewGroup mParentViewGroup;
    private static final String TAG = "zpy_BouncingMenu";

    public BouncingMenu(View view, int resId) {
        mParentViewGroup = findRootParent(view);
        rootView = LayoutInflater.from(view.getContext()).inflate(resId, null, false);

    }

    //向上循环找父View
    private ViewGroup findRootParent(View view) {
        do {
            if (view instanceof FrameLayout) {
                if (view.getId() == android.R.id.content) {
                    return (ViewGroup) view;
                }
            }
            if (view != null) {
                ViewParent parent = view.getParent();
                view = parent instanceof View ? (View) parent : null;
            }
        } while (view != null);
        return null;
    }

    public static BouncingMenu makeMenu(int resId) {
        return new BouncingMenu(resId);
    }
}
