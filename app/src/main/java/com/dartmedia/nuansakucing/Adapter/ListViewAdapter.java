package com.dartmedia.nuansakucing.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dartmedia.nuansakucing.Model.Model;
import com.dartmedia.nuansakucing.R;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;

public class ListViewAdapter extends ArrayAdapter<Model> {

    private Context context;
    int resource;
    private List<Model> items;

    public ListViewAdapter(Context context, int resource, List<Model> items) {
        super(context, resource, items);
        this.context = context;
        this.resource = resource;
        this.items = items;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        final View view = layoutInflater.inflate(resource,null,false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context.getApplicationContext(),"Ohh Snap My Position is "+position,Toast.LENGTH_SHORT).show();

            }
        });

        ImageView photo = (ImageView) view.findViewById(R.id.img_listView);
        TextView desc = (TextView) view.findViewById(R.id.desc_listview);
        TextView titl = (TextView) view.findViewById(R.id.title_listview);
        TextView jarak = (TextView) view.findViewById(R.id.jarak_listview);
        Button dir = (Button) view.findViewById(R.id.directionBtn_listview);
        Button share = (Button) view.findViewById(R.id.shareBtn_listview);

        final Model model = items.get(position);
        DecimalFormat df = new DecimalFormat("0.00");

        Glide.with(context).load(model.getPhoto()).into(photo);
        desc.setText(model.getDesc());
        titl.setText(model.getTitle());
        jarak.setText(model.getJar());

        dir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr=" + model.getGP().getLatitude() + "," + model.getGP().getLongitude() + ""));
                view.getContext().startActivity(intent);
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Application of social rating share with your friend");
                try {
                    Objects.requireNonNull(context).startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.whatsapp")));
                }
            }
        });

        return view;

    }
}
