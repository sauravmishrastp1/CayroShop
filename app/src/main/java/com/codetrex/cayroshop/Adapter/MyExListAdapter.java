package com.codetrex.cayroshop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codetrex.cayroshop.R;
import com.codetrex.cayroshop.model.SubCartegoryModel;
import com.codetrex.cayroshop.ui.ProductActivity;

import java.util.HashMap;
import java.util.List;

public class MyExListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<SubCartegoryModel> subCartegoryModels;
    private HashMap<SubCartegoryModel, List<SubCartegoryModel>> listDataChild;

    public MyExListAdapter(Context context, List<SubCartegoryModel> subCartegoryModels, HashMap<SubCartegoryModel, List<SubCartegoryModel>> listDataChild) {
        this.context = context;
        this.subCartegoryModels = subCartegoryModels;
        this.listDataChild = listDataChild;
    }

    @Override
    public SubCartegoryModel getChild(int groupPosition, int childPosititon) {
        return this.listDataChild.get(this.subCartegoryModels.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, final ViewGroup parent) {



      //  Toast.makeText(context, ""+listDataChild.get(subCartegoryModels.get(0).getSubcat_name()), Toast.LENGTH_SHORT).show();

        if (convertView == null) {
            final String childText = String.valueOf(getChild(groupPosition, childPosition).getSubcat_name());

            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.child_sub_cat_item, null);
            TextView txtListChild = convertView
                    .findViewById(R.id.category_name);
            txtListChild.setText(childText);

            txtListChild.setText(childText);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id = String.valueOf(getChild(groupPosition,childPosition).getSubcatid());
                    Intent intent=new Intent(context, ProductActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("catid",id);
                    context.startActivity(intent);
                }
            });
        }


        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        if (this.listDataChild.get(this.subCartegoryModels.get(groupPosition)) == null)
            return 0;
        else
            return this.listDataChild.get(this.subCartegoryModels.get(groupPosition))
                    .size();
    }

    @Override
    public SubCartegoryModel getGroup(int groupPosition) {
        return this.subCartegoryModels.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.subCartegoryModels.size();

    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {



        if (convertView == null) {
            String headerTitle = String.valueOf(getGroup(groupPosition).getSubcat_name());
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.sub_cat_item, null);
            TextView lblListHeader = convertView.findViewById(R.id.category_name);
            ImageView img = convertView.findViewById(R.id.indicator);

            lblListHeader.setTypeface(null, Typeface.BOLD);
            lblListHeader.setText(headerTitle);
        }



        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
