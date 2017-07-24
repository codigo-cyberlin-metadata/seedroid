package id.codigo.seedroid_picker.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;

import java.util.List;

import id.codigo.seedroid_picker.R;
import id.codigo.seedroid_picker.holder.ImageViewHolder;
import id.codigo.seedroid_picker.listener.OnImageClickListener;
import id.codigo.seedroid_picker.model.Image;


/**
 * Created by papahnakal on 24/07/17.
 */

public class ImagePickAdapter extends RecyclerView.Adapter<ImageViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<Image> images;
    private List<Image> selectedImages;
    private OnImageClickListener itemClickListener;

    public ImagePickAdapter(Context context, List<Image> images, List<Image> selectedImages, OnImageClickListener itemClickListener) {
        this.context = context;
        this.images = images;
        this.selectedImages = selectedImages;
        this.itemClickListener = itemClickListener;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(itemView, itemClickListener);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Image image = images.get(position);

        Glide.with(context)
                .load(image.getPath())
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder)
                .into(holder.imageView);

        if (isSelected(image)) {
            holder.alphaView.setAlpha(0.5f);
            ((FrameLayout) holder.itemView).setForeground(ContextCompat.getDrawable(context, R.drawable.ic_done_white));
        } else {
            holder.alphaView.setAlpha(0.0f);
            ((FrameLayout) holder.itemView).setForeground(null);
        }
    }
    private boolean isSelected(Image image) {
        for (Image selectedImage : selectedImages) {
            if (selectedImage.getPath().equals(image.getPath())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int getItemCount() {
        return images.size();
    }


    public void setData(List<Image> images) {
        this.images.clear();
        this.images.addAll(images);
    }

    public void addAll(List<Image> images) {
        int startIndex = this.images.size();
        this.images.addAll(startIndex, images);
        notifyItemRangeInserted(startIndex, images.size());
    }

    public void addSelected(Image image) {
        selectedImages.add(image);
        notifyItemChanged(images.indexOf(image));
    }

    public void removeSelectedImage(Image image) {
        selectedImages.remove(image);
        notifyItemChanged(images.indexOf(image));
    }

    public void removeSelectedPosition(int position, int clickPosition) {
        selectedImages.remove(position);
        notifyItemChanged(clickPosition);
    }

    public void removeAllSelectedSingleClick() {
        selectedImages.clear();
        notifyDataSetChanged();
    }
}
