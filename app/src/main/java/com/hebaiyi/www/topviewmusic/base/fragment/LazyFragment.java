package com.hebaiyi.www.topviewmusic.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.lang.reflect.Field;

public abstract class LazyFragment extends Fragment {

    protected ViewGroup container;
    protected LayoutInflater inflater;

    private View contentView;
    private Context context;
    private FrameLayout layout;
    private Bundle mSavedInstanceState;
    private boolean isLazyLoad = true;
    private boolean isInit = false;
    private boolean isStart = false;

    public static final String INTENT_BOOLEAN_LAZYLOAD = "intent_boolean_lazyLoad";

    public void setIsLazyBeforeCreate(boolean isLazyLoad) {
        this.isLazyLoad = isLazyLoad;
    }

    protected abstract int getContentViewId();

    protected abstract void init(Bundle savedInstanceState);

    protected Boolean isLazyLoad() {
        return null;
    }


    public Context getApplicationContext() {
        return context;
    }

    public View findViewById(int id) {
        if (contentView != null)
            return contentView.findViewById(id);
        return null;
    }

    public final boolean isInited() {
        return isInit;
    }

    protected abstract void onBeforeInit(Bundle savedInstanceState);

    protected void onCreateViewLazy(Bundle savedInstanceState) {
        setContentView(getContentViewId());
        onBeforeInit(savedInstanceState);
        init(savedInstanceState);
    }

    public void setContentView(int layoutResID) {
        if (shouldLoadLazy() && getContentView() != null && getContentView().getParent() != null) {
            layout.removeAllViews();
            View view = inflater.inflate(layoutResID, layout, false);
            layout.addView(view);
        } else {
            setContentView(inflater.inflate(layoutResID, container, false));
        }
    }

    protected void onFragmentStartLazy() {
    }

    protected void onResumeLazy() {
    }

    protected void onPauseLazy() {
    }

    protected void onFragmentStopLazy() {
    }

    protected void onDestroyViewLazy() {
    }

    public void setContentView(View view) {
        if (shouldLoadLazy() && getContentView() != null && getContentView().getParent() != null) {
            layout.removeAllViews();
            layout.addView(view);
        } else {
            contentView = view;
        }
    }

    public View getContentView() {
        return contentView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity().getApplicationContext();
    }

    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        this.container = container;
        //noinspection deprecation
        onCreateView(savedInstanceState);
        if (contentView == null)
            return super.onCreateView(inflater, container, savedInstanceState);
        return contentView;
    }

    @SuppressWarnings("DeprecatedIsStillUsed")
    @Deprecated
    protected final void onCreateView(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            isLazyLoad = bundle.getBoolean(INTENT_BOOLEAN_LAZYLOAD, isLazyLoad);
        }
        if (shouldLoadLazy()) {
            if (getUserVisibleHint() && !isInit) {
                isInit = true;
                mSavedInstanceState = savedInstanceState;
                onCreateViewLazy(savedInstanceState);
            } else {
                layout = new FrameLayout(getApplicationContext());
                layout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
                setContentView(layout);
            }
        } else {
            isInit = true;
            onCreateViewLazy(savedInstanceState);
        }
    }

    @Deprecated
    @Override
    public final void onStart() {
        super.onStart();
        if (isInit && !isStart && getUserVisibleHint()) {
            isStart = true;
            onFragmentStartLazy();
        }
    }

    @Override
    @Deprecated
    public final void onResume() {
        super.onResume();
        if (isInit) {
            onResumeLazy();
        }
    }

    @Override
    @Deprecated
    public final void onPause() {
        super.onPause();
        if (isInit) {
            onPauseLazy();
        }
    }

    @Deprecated
    @Override
    public final void onStop() {
        super.onStop();
        if (isInit && isStart && getUserVisibleHint()) {
            isStart = false;
            onFragmentStopLazy();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        contentView = null;
        container = null;
        inflater = null;
        if (isInit) {
            onDestroyViewLazy();
        }
        isInit = false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !isInit && getContentView() != null) {
            isInit = true;
            onCreateViewLazy(mSavedInstanceState);
            onResumeLazy();
        }
        if (isInit && getContentView() != null) {
            if (isVisibleToUser) {
                isStart = true;
                onFragmentStartLazy();
            } else {
                isStart = false;
                onFragmentStopLazy();
            }
        }
    }

    private boolean shouldLoadLazy() {
        if (isLazyLoad() == null) {
            return isLazyLoad;
        } else {
            return isLazyLoad();
        }
    }

}
