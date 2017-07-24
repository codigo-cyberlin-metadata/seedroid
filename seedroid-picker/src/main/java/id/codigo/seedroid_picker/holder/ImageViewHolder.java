package id.codigo.seedroid_picker.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import id.codigo.seedroid_picker.R;
import id.codigo.seedroid_picker.listener.OnImageClickListener;

/**
 * Created by papahnakal on 24/07/17.
 */

public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ImageView imageView;
    public View alphaView;
    public final OnImageClickListener itemClickListener;

    public ImageViewHolder(View itemView, OnImageClickListener itemClickListener) {
        super(itemView);
        this.imageView = (ImageView) itemView.findViewById(R.id.image_view);
        this.alphaView = itemView.findViewById(R.id.view_alpha);
        this.itemClickListener = itemClickListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
