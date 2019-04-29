package com.swp.culturehomestay.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.swp.culturehomestay.R;
import com.swp.culturehomestay.models.AutocompleteBean;

import java.util.ArrayList;
import java.util.List;


public class AutoCompleteSearchAdapter extends ArrayAdapter<AutocompleteBean> {
    private List<AutocompleteBean> countryListFull;

    public AutoCompleteSearchAdapter(@NonNull Context context, @NonNull List<AutocompleteBean> countryList) {
        super(context, 0, countryList);
        countryListFull = new ArrayList<>(countryList);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return countryFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.autocomplete_row, parent, false
            );
        }

        TextView textViewName = convertView.findViewById(R.id.tvName);
        ImageView imageViewFlag = convertView.findViewById(R.id.ivIcon);

        AutocompleteBean autocompleteBean = getItem(position);

        if (autocompleteBean != null) {
            textViewName.setText(autocompleteBean.getTitle());
            if(!autocompleteBean.getGroup().equals("Homestay")){
                imageViewFlag.setImageResource(R.drawable.ic_marker_white);
            }

        }

        return convertView;
    }

    private Filter countryFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<AutocompleteBean> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(countryListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (AutocompleteBean item : countryListFull) {
                    if (item.getTitle().toLowerCase().contains(filterPattern)) {
                        suggestions.add(item);
                    }
                }
            }

            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((AutocompleteBean) resultValue).getTitle();
        }
    };
}