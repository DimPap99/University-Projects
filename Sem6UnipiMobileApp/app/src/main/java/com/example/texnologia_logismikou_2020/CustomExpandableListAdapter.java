package com.example.texnologia_logismikou_2020;

import java.util.HashMap;
import java.util.List;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;
    private boolean is_expanded = false;
    private int[] icons_group_array = {
            R.drawable.homepage,
            R.drawable.contact,
            R.drawable.university,
            R.drawable.anakoinwsh,
            R.drawable.sxoles,
            R.drawable.members,
            R.drawable.research,
            R.drawable.activities,
            R.drawable.services
    };
    private int[] icons_tmima = {
            R.drawable.rector,
            R.drawable.diakrisi,
    };

    private int[] icons_anakoinwseis = {
            R.drawable.news,
            R.drawable.praxeis,
    };

    private int[] icons_spoudes = {
            R.drawable.ungraduate,
            R.drawable.graduate,
            R.drawable.pdh,
            R.drawable.elearn,
            R.drawable.guide,
    };

    private int[] icons_ereuna = {
            R.drawable.sectors,
            R.drawable.publish,
            R.drawable.projects,
            R.drawable.researchcenter,
    };

    private int[] icons_paroxes = {
            R.drawable.papei_paroxes,
            R.drawable.company,
    };

    private int[] icons_drast = {
            R.drawable.sigklitos,
            R.drawable.speech,
            R.drawable.conference,
            R.drawable.events
    };

    private int[] icons_melh = {
            R.drawable.teacher,
            R.drawable.quality,
            R.drawable.researcher,
            R.drawable.cand,
            R.drawable.help
    };

    public CustomExpandableListAdapter(Context context, List<String> expandableListTitle,
                                       HashMap<String, List<String>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item, null);
        }
        TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.expandedListItem);
        expandedListTextView.setText(expandedListText);
        expandedListTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f);
        expandedListTextView.setTextColor(Color.parseColor("#e6e6e6"));
        ImageView items_icon = (ImageView ) convertView
                .findViewById(R.id.icons_list);
        if(listPosition == 2){
            items_icon.setImageResource(icons_tmima[expandedListPosition]);
        }else if(listPosition == 3){
            items_icon.setImageResource(icons_anakoinwseis[expandedListPosition]);
        }else if(listPosition == 4){
            items_icon.setImageResource(icons_spoudes[expandedListPosition]);
        }else if(listPosition == 5){
            items_icon.setImageResource(icons_melh[expandedListPosition]);
        }else if(listPosition == 6){
            items_icon.setImageResource(icons_ereuna[expandedListPosition]);
        }else if(listPosition == 7){
            items_icon.setImageResource(icons_drast[expandedListPosition]);
        }else if(listPosition == 8){
            items_icon.setImageResource(icons_paroxes[expandedListPosition]);
        }
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        listTitleTextView.setTextColor(Color.parseColor("#f2f2f2"));
        listTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f);
        ImageView imgViewChild = (ImageView ) convertView
                .findViewById(R.id.icons);
        imgViewChild.setImageResource(icons_group_array[listPosition]);
        ImageView arrowicon = (ImageView ) convertView
                .findViewById(R.id.arrows);
        if((listPosition == 2 || listPosition == 3 || listPosition == 4 || listPosition == 5 || listPosition == 6 || listPosition == 7 || listPosition == 8)){
            if(isExpanded){
                arrowicon.setImageResource(R.drawable.group_up);
                isExpanded = false;
            }else{
                arrowicon.setImageResource(R.drawable.group_down);
                isExpanded = true;
            }
        }
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}
