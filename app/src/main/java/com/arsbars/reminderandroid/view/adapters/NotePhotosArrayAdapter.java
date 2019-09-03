package com.arsbars.reminderandroid.view.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.arsbars.reminderandroid.R;
import com.arsbars.reminderandroid.viewmodels.GalleryItemViewModel;

import java.io.File;
import java.util.List;

public class NotePhotosArrayAdapter extends ArrayAdapter<GalleryItemViewModel> {
    private LayoutInflater inflater;
    private int layout;
    private List<GalleryItemViewModel> viewModels;

    public NotePhotosArrayAdapter(Context context,
                                  int resource,
                                  List<GalleryItemViewModel> viewModels) {
        super(context, resource, viewModels);
        this.viewModels = viewModels;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        PhotosViewHolder viewHolder;

        if(convertView == null) {
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new PhotosViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (PhotosViewHolder) convertView.getTag();
        }

        File imgFile = new File(this.viewModels.get(position).getThumbnail());
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            viewHolder.imageView.setImageBitmap(myBitmap);
        }
        return convertView;
    }

    private class PhotosViewHolder {
        private final ImageView imageView;
        PhotosViewHolder(View view){
            imageView = view.findViewById(R.id.photoImage);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }
}
