package id.codigo.seedroid_mediapicker;

import java.util.List;

/**
 * Created by papahnakal on 19/10/17.
 */

public interface MediaSelectedListener {
    public void onHasNoSelected();

    public void onHasSelected(List<MediaItem> mediaSelectedList);
}
