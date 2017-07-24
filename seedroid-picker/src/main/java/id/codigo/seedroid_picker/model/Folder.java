package id.codigo.seedroid_picker.model;

import java.util.ArrayList;

/**
 * Created by papahnakal on 24/07/17.
 */

public class Folder {
    private String folderName;
    private ArrayList<Image> images;

    public Folder(String folderName) {
        this.folderName = folderName;
        this.images = new ArrayList<>();
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }
}
