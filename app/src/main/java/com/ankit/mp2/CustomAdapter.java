package com.ankit.mp2;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ankit.mp2.Filters.Filter;
import com.ankit.mp2.Filters.Grayscale;

import java.util.ArrayList;

public class CustomAdapter extends PagerAdapter {
    Context context;
    int[] imageId = {R.drawable.grayscale, R.drawable.grayscale};
    String[] text={"Grayscale", "Test"};
    public static final int filterCount = 4;

    ArrayList<Filter> filters = new ArrayList<>();

    public CustomAdapter(Context context) {
        this.context = context;
        filters.add(new Grayscale(imageId[0], text[0]));
        filters.add(new Grayscale(imageId[0], text[1]));
        filters.add(new Grayscale(imageId[0], text[0]));
        filters.add(new Grayscale(imageId[0], text[0]));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //  TODO Auto-generated method stub
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View viewItem = inflater.inflate(R.layout.image_item, container, false);
        ImageView imageView = (ImageView) viewItem.findViewById(R.id.imageView);
        TextView textView=(TextView) viewItem.findViewById(R.id.textview);
        for (int i = 0; i < filterCount; i++) {
            imageView.setImageResource(filters.get(i).getThumbId());
            textView.setText(filters.get(i).getTitle());
        }
        (container).addView(viewItem);

        return viewItem;

    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return filterCount;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==((View)object);
    }

}
