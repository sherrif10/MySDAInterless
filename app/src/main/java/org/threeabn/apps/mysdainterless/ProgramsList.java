package org.threeabn.apps.mysdainterless;

import android.app.Activity;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

/**
 * Created by k-joseph on 29/10/2017.
 */

public class ProgramsList extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] programPaths;

    public ProgramsList(Activity context, String[] programPaths) {
        super(context, R.layout.list_programs, programPaths);//TODO fetch and rather pass in program name here instead of programPaths
        this.context = context;
        this.programPaths = programPaths;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_programs, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        String path = programPaths[position];
        Bitmap img = getBitMapFromVideo(MySDAInterlessConstantsAndEvaluations.PROGRAMS_DIRECTORY + File.separator + path);

        txtTitle.setText(programPaths[position]);
        imageView.setImageBitmap(img);

        return rowView;
    }

    public Bitmap createThumbnailFromPath(String filePath, int type){
        return ThumbnailUtils.createVideoThumbnail(filePath, type);
    }

    public Bitmap getBitMapFromVideo(String path) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();

        mediaMetadataRetriever.setDataSource(path);
        Bitmap bmFrame = mediaMetadataRetriever.getFrameAtTime(5000000); //unit in microsecond

        return bmFrame;
    }
}
