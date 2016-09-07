package com.junjianliu.library.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.junjianliu.library.R;

import in.srain.cube.views.GridViewWithHeaderAndFooter;

/**
 *
 *  一个列表的业务控件
 *  主要目的就是提高开发效率
 *  减少Activity 代码量
 *
 *  需要 EmptyLayout
 *       listview
 *       SwipeRefreshLayout
 *
 * author liu junjian
 * time   2016/6/16.
 * email spyhanfeng@qq.com
 * github https://github.com/spyrx7
 */
public class ListManagerView extends RelativeLayout {

    public static final  int  TYPE_LISTVIEW = 0;
    public static final  int  TYPE_GRADVIEW = 1;

    public  static  final int START_LOAD = 0;
    public  static  final int START_SUCCEED = 1;
    public  static  final int START_ERROR = 2;
    public  static  final int START_NET_ERROR = 3;
    public  static  final int START_LOAD_OVER= 5;
    public  static  final int START_HIDE_LAYOUT = 4;
    public  static  final int START_NODADA = 6;
    public  static  final int START_NOT_LOGIN = 7;

    private Context context;
    private ListView listView;
    private GridViewWithHeaderAndFooter gridView;
    private EmptyLayout emptyLayout;
    private SwipeRefreshLayout refresh;

    private int pagerCount = 8;            // 页面item 数量， 默认为6个
    private int type = 0;                   //

    private boolean flag = false;

    private int errcode = 0;

    public interface OnLoadData{
        void onLoadData();
    }

    private View footView;

    private OnLoadData onLoadData;


    private SwipeRefreshLayout.OnRefreshListener onRefreshListener;

    public ListManagerView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public ListManagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public ListManagerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    /**
     *  初始化 ListManagerView，构建 listView，emptyLayout，refresh
     */
    private void init(){
        initView();
    }

    private void initView(){
        if(type == TYPE_GRADVIEW){
            gridView = new GridViewWithHeaderAndFooter(context);
        }else{
            listView = new ListView(context);
        }
        emptyLayout = new EmptyLayout(context);
        refresh = new SwipeRefreshLayout(context);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LayoutParams params2 = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        refresh.setLayoutParams(params);
        emptyLayout.setLayoutParams(params2);
        emptyLayout.setVisibility(GONE);

        if(type == TYPE_GRADVIEW){
            gridView.setLayoutParams(params2);
            refresh.addView(gridView);
        }else{
            listView.setLayoutParams(params2);
            listView.setFooterDividersEnabled(false);
            listView.setDividerHeight(0);
            refresh.addView(listView);
        }
        initFootView();
        if(type == TYPE_GRADVIEW){
            gridView.addFooterView(footView);
            if(gridView.getCount() < pagerCount ) {
                footView.findViewById(R.id.layout).setVisibility(GONE);
            }
            gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                    switch (scrollState) {
                        // 当不滚动时
                        case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                            // 判断滚动到底部
                            if (gridView.getLastVisiblePosition() == (gridView.getCount() - 1)) {
                                if (!flag) {
                                    flag = true;
                                    if(onLoadData  != null){
                                        onLoadData.onLoadData();
                                    }
                                }
                            }
                            // 判断滚动到顶部
                            if (gridView.getFirstVisiblePosition() == 0) {
                            }

                            if(gridView.getCount() >= pagerCount){
                                footView.findViewById(R.id.layout).setVisibility(VISIBLE);

                                if(errcode == START_LOAD_OVER){
                                    if(footView != null) {
                                        ((ProgressBar) footView.findViewById(R.id.img_icon)).setVisibility(View.GONE);
                                        ((TextView) footView.findViewById(R.id.load_text)).setText(R.string.load_over);
                                    }
                                }else{
                                    if(footView != null) {
                                        ((ProgressBar) footView.findViewById(R.id.img_icon)).setVisibility(View.VISIBLE);
                                        ((TextView) footView.findViewById(R.id.load_text)).setText(R.string.load);
                                    }
                                }

                            }else{
                                if(footView != null){
                                    footView.findViewById(R.id.layout).setVisibility(GONE);
                                }

                            }

                            break;
                    }
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                }
            });
        }else{

            listView.addFooterView(footView);
            if(listView.getCount() < pagerCount ) {
                footView.findViewById(R.id.layout).setVisibility(GONE);
            }
            listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                    switch (scrollState) {
                        // 当不滚动时
                        case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                            // 判断滚动到底部
                            if (listView.getLastVisiblePosition() == (listView.getCount() - 1)) {
                                if (!flag) {
                                    flag = true;
                                    if(onLoadData  != null){
                                        onLoadData.onLoadData();
                                    }
                                }
                            }
                            // 判断滚动到顶部
                            if (listView.getFirstVisiblePosition() == 0) {
                            }

                            if(listView.getCount() >= pagerCount){
                                footView.findViewById(R.id.layout).setVisibility(VISIBLE);

                                if(errcode == START_LOAD_OVER){
                                    if(footView != null) {
                                        ((ProgressBar) footView.findViewById(R.id.img_icon)).setVisibility(View.GONE);
                                        ((TextView) footView.findViewById(R.id.load_text)).setText(R.string.load_over);
                                    }
                                }else{
                                    if(footView != null) {
                                        ((ProgressBar) footView.findViewById(R.id.img_icon)).setVisibility(View.VISIBLE);
                                        ((TextView) footView.findViewById(R.id.load_text)).setText(R.string.load);
                                    }
                                }

                            }else{
                                if(footView != null){
                                    footView.findViewById(R.id.layout).setVisibility(GONE);
                                }

                            }

                            break;
                    }
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                }
            });
        }


        this.addView(refresh);
        this.addView(emptyLayout);
    }

    private void initFootView(){
        if(footView == null){
            footView = View.inflate(context, R.layout.load_more,null);
        }
    }

    /**
     *  对外提供 listManagerView 的类型
     * @param type 默认 为ListView 0  GridView 1
     */
    public void setType(int type){
       this.type = type;
        this.removeAllViews();
        initView();
    }

    /**
     *  对外提供 设置每页显示多少条数据
     * @param pagerCount
     */
    public void setPagerCount(int pagerCount){
        this.pagerCount = pagerCount;
    }

    /**
     *  提供外部 OnRefreshListener
     *
     */
    public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener onRefreshListener){
        this.onRefreshListener = onRefreshListener;
        if(refresh != null){
            refresh.setOnRefreshListener(onRefreshListener);
        }
    }

    /**
     *  提供外部 setRefreshing
     * @param refreshing
     */
    public void setRefreshing(boolean refreshing){
        if(refresh != null){
            refresh.setRefreshing(refreshing);
        }
    }

    /**
     *  提供外部 setErrorType
     * @param errorType
     */
    public void setErrorType(int errorType){
        if(emptyLayout != null){
            emptyLayout.setErrorType(errorType);
            this.errcode = errorType;

            if(errorType == START_LOAD_OVER){
                emptyLayout.setErrorType(START_HIDE_LAYOUT);
                if(footView != null) {
                    ((ProgressBar) footView.findViewById(R.id.img_icon)).setVisibility(View.GONE);
                    ((TextView) footView.findViewById(R.id.load_text)).setText(R.string.load_over);
                }
            }else{
                if(footView != null) {
                    ((ProgressBar) footView.findViewById(R.id.img_icon)).setVisibility(View.VISIBLE);
                    ((TextView) footView.findViewById(R.id.load_text)).setText(R.string.load);
                }
            }
        }
    }

    /**
     *  提供外部 emptyLayout 的OnClickListener
     * @param onClickListener
     */
    public void setOnLayoutClickListener(OnClickListener onClickListener){
        if (emptyLayout != null){
            emptyLayout.setOnLayoutClickListener(onClickListener);
        }
    }

    /**
     *  提供外部listView setAdapter
     * @param adapter
     */
    public void setAdapter(ListAdapter adapter){

        if(type == TYPE_GRADVIEW){
            if(gridView != null){
                gridView.setAdapter(adapter);
            }
        }else{
            if(listView != null){
                listView.setAdapter(adapter);
            }
        }
    }

    /**
     * 提供外部 加载事件
     * @param onLoadData
     */
    public void setOnLoadData(OnLoadData onLoadData){
        this.onLoadData = onLoadData;
    }


    public void setFlag(Boolean flag){
        this.flag = flag;
    }

    /**
     *  获取getIsRefreshing
     * @return
     */
    public boolean getIsRefreshing(){
        if(refresh != null){
           return   refresh.isRefreshing();
        }
        return false;
    }

    /**
     * 获取 状态代码
     * @return
     */
    public int getErrorCode(){
        return this.errcode;
    }


    /***
     *  对外提供设置gridView 的列数
     * @param num
     */
    public void setNumColumns(int num){
        if(gridView != null){
            gridView.setNumColumns(num);
        }
    }
}
