package id.codigo.seedroid_picker.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import id.codigo.seedroid_picker.R;

/**
 * Created by papahnakal on 24/07/17.
 */

public class FolderViewHolder extends RecyclerView.ViewHolder {
    public ImageView image;
    public TextView name;
    public TextView number;

    public FolderViewHolder(View itemView) {
        super(itemView);
        this.image = (ImageView) itemView.findViewById(R.id.image);
        this.name = (TextView) itemView.findViewById(R.id.tv_name);
        this.number = (TextView) itemView.findViewById(R.id.tv_number);
    }
}
