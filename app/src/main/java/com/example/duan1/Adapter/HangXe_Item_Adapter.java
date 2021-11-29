package com.example.duan1.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duan1.Model.HangXe;
import com.example.duan1.R;


import java.util.List;

public class HangXe_Item_Adapter extends BaseAdapter {
    private List<HangXe> data;
    private Context context;
    public HangXe_Item_Adapter(List<HangXe> _data, Context _context){
        this.data = _data;
        this.context = _context;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int _i, View _view, ViewGroup _viewgroup) {
        View view = _view;
        if (view == null){
           view = view.inflate(_viewgroup.getContext(), R.layout.layout_item_spinner,null);
           TextView textView = (TextView) view.findViewById(R.id.tv_sp_name);
           ViewHolder holder = new ViewHolder(textView);
           view.setTag(holder);
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        HangXe hangXe =(HangXe) getItem(_i);
        holder.textView.setText(hangXe.getName());
        return view;
    }
    private static class ViewHolder{
        final TextView textView;
        private ViewHolder(TextView textView){this.textView = textView;}
    }
}
