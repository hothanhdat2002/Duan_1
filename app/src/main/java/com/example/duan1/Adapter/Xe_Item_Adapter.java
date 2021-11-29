package com.example.duan1.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.duan1.Model.HangXe;
import com.example.duan1.Model.Xe;
import com.example.duan1.R;

import java.util.List;

public class Xe_Item_Adapter extends BaseAdapter {
    private List<Xe> data;
    private Context context;
    public Xe_Item_Adapter(List<Xe> _data, Context _context){
        this.data = _data;
        this.context = _context;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
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
        Xe Xe =(Xe) getItem(_i);
        holder.textView.setText(Xe.getName());
        return view;
    }
    private static class ViewHolder{
        final TextView textView;
        private ViewHolder(TextView textView){this.textView = textView;}
    }
}
