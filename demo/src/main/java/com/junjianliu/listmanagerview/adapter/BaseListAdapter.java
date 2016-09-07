package com.junjianliu.listmanagerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by pc-spyrx7 on 2015/12/16.
 * 作者  pc-spyrx7
 * 邮箱 spyhanfeng@qq.com
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {
    public LayoutInflater inflater;
    public Context context;
    public List<T> datas;
    public int layoutId;

    public BaseListAdapter(Context context, List<T> datas, int layoutId){
        this.context = context;
        this.datas = datas;
        this.inflater= LayoutInflater.from(context);
        this.layoutId=layoutId;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public T getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void add(T t){
        if(t != null){
            this.datas.add(t);
        }
    }

    public void setDatas(List<T> datas){
        this.datas = datas;
    }

    public void add(T t,int postion){
        this.datas.set(postion, t);

    }

    public void adds(List<T> T){
        this.datas.addAll(T);
    }

    public void remove(int postion){
        this.datas.remove(postion);
    }

    public void clear(){
        this.datas.clear();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = ViewHolder.get(context, convertView, parent, layoutId, position);
            convert(holder, getItem(position));
            return holder.getConvertView();
    }

    public abstract void convert(ViewHolder holder,T t);

}
