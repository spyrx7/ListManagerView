package com.junjianliu.listmanagerview;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.junjianliu.library.widget.ListManagerView;




public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListManagerView listMaagerView = (ListManagerView) findViewById(R.id.list);

        // 1 listView (默认)
        // 设置下啦刷新事件
        listMaagerView.setOnRefreshListener(this);

        // 2 设置上拉加载更多事件
        listMaagerView.setOnLoadData(new ListManagerView.OnLoadData() {
            @Override
            public void onLoadData() {
                //
            }
        });

        // 3 设置异常状态的点击事件
        listMaagerView.setOnLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (listMaagerView.getErrorCode()){
                    case ListManagerView.START_NOT_LOGIN:
                        // 没有登录
                        break;
                    case ListManagerView.START_NET_ERROR:
                        // 没有网络
                        break;
                    case ListManagerView.START_NODADA:
                        // 没有数据
                        break;
                }
            }
        });


        // 4 设置adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,getData());
        listMaagerView.setAdapter(adapter);
    }


    private String[] getData(){
        String str = "Hotel Chevalier is a 2007 short film written and directed by Wes Anderson, starring Jason Schwartzman and Natalie Portman as former lovers who reunite in a Paris hotel room. The 13-minute film acts as a prologue to Anderson's 2007 feature film The Darjeeling Limited, in which a man (played by Adrien Brody) reunites with his brothers (Owen Wilson and Schwartzman) in India after the death of their father. Hotel Chevalier was shot on location in a Parisian hotel by a small crew and self-financed by Anderson, who initially intended it as a stand-alone work. Its first showing was at the Venice Film Festival première of the feature film on September 2, 2007, and it made its own debut later that month at Apple Stores in four American cities. The day after its première, it was made available for free from the iTunes Store for one month, during which it was downloaded more than 500,000 times. The film garnered near-universal critical acclaim from reviewers who compared it favorably with The Darjeeling Limited and praised its richness, poignancy, and careful construction";
        String[] array = str.split(" ");
        return array;
    }


    @Override
    public void onRefresh() {

    }
}
