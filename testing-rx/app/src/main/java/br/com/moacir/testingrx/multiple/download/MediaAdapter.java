package br.com.moacir.testingrx.multiple.download;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.com.moacir.testingrx.R;

/**
 * Created by moacir on 22/10/16.
 */

public class MediaAdapter extends BaseAdapter {

    private List<MediaInfo> mediaList = new ArrayList<>();

    public void setMediaList(List<MediaInfo> files) {
        if (files == null) {
            return;
        }
        mediaList.clear();
        mediaList.addAll(files);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mediaList.size();
    }

    @Override
    public MediaInfo getItem(int position) {
        if (position < 0 || position > mediaList.size()) {
            return null;
        } else {
            return mediaList.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view = (convertView != null ? convertView : createView(parent));
        final MediaViewHolder viewHolder = (MediaViewHolder) view.getTag();
        viewHolder.setMediaInfo(getItem(position));
        return view;
    }

    private View createView(ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.item_media, parent, false);
        final MediaViewHolder viewHolder = new MediaViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    private static final class MediaViewHolder {

        private final ImageView imageView;

        public MediaViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.img_media);
        }

        public void setMediaInfo(MediaInfo mediaInfo) {
            if (mediaInfo.filePath != null){
                File file = new File(mediaInfo.filePath);
                if (file.exists()) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(mediaInfo.filePath);
                    imageView.setImageBitmap(myBitmap);
                }
            }

        }
    }
}
