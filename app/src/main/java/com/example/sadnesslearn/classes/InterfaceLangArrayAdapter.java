package com.example.sadnesslearn.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sadnesslearn.R;

import java.util.List;

public class InterfaceLangArrayAdapter extends ArrayAdapter<InterfaceLangItem> {
    private int selectedPosition;

    public InterfaceLangArrayAdapter(Context context, List<InterfaceLangItem> list){
        super(context, R.layout.list_item_interface_lang, list);
        selectedPosition = 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        InterfaceLangItem item = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_interface_lang, parent, false);
        }

        TextView tv_lang_item = convertView.findViewById(R.id.tv_item_interface_lang);
        ImageView iv_lang_item = convertView.findViewById(R.id.iv_item_interface_lang);
        CheckBox cb_lang_item = convertView.findViewById(R.id.cb_item_interface_lang);

        tv_lang_item.setText(item.getLangName());
        iv_lang_item.setImageResource(item.getImageId());

        cb_lang_item.setChecked(position == selectedPosition);
        cb_lang_item.setTag(position);
        notifyDataSetChanged();

        return convertView;
    }

    public int getSelectedPosition() { return this.selectedPosition; }

    public void setSelectedPosition(int selectedPosition) { this.selectedPosition = selectedPosition; }
}
