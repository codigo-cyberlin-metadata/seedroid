package id.codigo.seedroid_mediapicker.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

import id.codigo.seedroid_mediapicker.CropListener;
import id.codigo.seedroid_mediapicker.MediaItem;
import id.codigo.seedroid_mediapicker.MediaOption;
import id.codigo.seedroid_mediapicker.R;
import id.codigo.seedroid_mediapicker.utils.MediaUtils;
import id.codigo.seedroid_mediapicker.utils.Utils;

/**
 * Created by papahnakal on 19/10/17.
 */

public class SeedroidPhotoCropFragment extends BaseFragment implements View.OnClickListener {
    private static final String EXTRA_MEDIA_SELECTED = "extra_media_selected";
    private static final String EXTRA_MEDIA_OPTIONS = "extra_media_options";

    private CropListener mCropListener;
    private MediaOption mMediaOptions;
    private MediaItem mMediaItemSelected;
    private CropImageView mCropImageView;
    private View mRotateLeft, mRotateRight;
    private View mCancel;
    private View mSave;
    private ProgressDialog mDialog;
    private SaveFileCroppedTask mSaveFileCroppedTask;

    public static SeedroidPhotoCropFragment newInstance(MediaItem item,
                                                MediaOption options) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_MEDIA_SELECTED, item);
        bundle.putParcelable(EXTRA_MEDIA_OPTIONS, options);
        SeedroidPhotoCropFragment cropFragment = new SeedroidPhotoCropFragment();
        cropFragment.setArguments(bundle);
        return cropFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCropListener = (CropListener) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mMediaItemSelected = savedInstanceState
                    .getParcelable(EXTRA_MEDIA_SELECTED);
            mMediaOptions = savedInstanceState
                    .getParcelable(EXTRA_MEDIA_OPTIONS);
        } else {
            Bundle bundle = getArguments();
            mMediaItemSelected = bundle.getParcelable(EXTRA_MEDIA_SELECTED);
            mMediaOptions = bundle.getParcelable(EXTRA_MEDIA_OPTIONS);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_MEDIA_OPTIONS, mMediaOptions);
        outState.putParcelable(EXTRA_MEDIA_SELECTED, mMediaItemSelected);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mediapicker_crop,
                container, false);
        init(root);
        return root;
    }

    private void init(View view) {
        mCropImageView = (CropImageView) view.findViewById(R.id.crop);
        mRotateLeft = view.findViewById(R.id.rotate_left);
        mRotateRight = view.findViewById(R.id.rotate_right);
        mCancel = view.findViewById(R.id.cancel);
        mSave = view.findViewById(R.id.save);

        mRotateLeft.setOnClickListener(this);
        mRotateRight.setOnClickListener(this);
        mSave.setOnClickListener(this);
        mCancel.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCropImageView.setFixedAspectRatio(mMediaOptions.isFixAspectRatio());
        mCropImageView.setAspectRatio(mMediaOptions.getAspectX(),
                mMediaOptions.getAspectY());
        String filePath = null;
        String scheme = mMediaItemSelected.getUriOrigin().getScheme();
        if (scheme.equals(ContentResolver.SCHEME_CONTENT)) {
            filePath = MediaUtils.getRealImagePathFromURI(getActivity()
                    .getContentResolver(), mMediaItemSelected.getUriOrigin());
        } else if (scheme.equals(ContentResolver.SCHEME_FILE)) {
            filePath = mMediaItemSelected.getUriOrigin().getPath();
        }
        if (TextUtils.isEmpty(filePath)) {
            Log.e("PhotoCrop", "not found file path");
            getFragmentManager().popBackStack();
            return;
        }
        int width = getResources().getDisplayMetrics().widthPixels / 3 * 2;
        Bitmap bitmap = MediaUtils.decodeSampledBitmapFromFile(filePath, width,
                width);
        try {
            ExifInterface exif = new ExifInterface(filePath);
            mCropImageView.setImageBitmap(bitmap, exif);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.rotate_left) {// must catch exception, maybe bitmap in CropImage null
            try {
                mCropImageView.rotateImage(-90);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (i == R.id.rotate_right) {
            try {
                mCropImageView.rotateImage(90);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (i == R.id.cancel) {
            getFragmentManager().popBackStack();

        } else if (i == R.id.save) {
            mSaveFileCroppedTask = new SaveFileCroppedTask(getActivity());
            mSaveFileCroppedTask.execute();

        } else {
        }
    }

    private Uri saveBitmapCropped(Bitmap bitmap) {
        if (bitmap == null)
            return null;
        try {
            File file;
            if (mMediaOptions.getCroppedFile() != null) {
                file = mMediaOptions.getCroppedFile();
            } else {
                file = Utils.createTempFile(mContext);
            }
            boolean success = bitmap.compress(Bitmap.CompressFormat.JPEG, 100,
                    new FileOutputStream(file));
            if (success) {
                return Uri.fromFile(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class SaveFileCroppedTask extends AsyncTask<Void, Void, Uri> {
        private WeakReference<Activity> reference;

        public SaveFileCroppedTask(Activity activity) {
            reference = new WeakReference<Activity>(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (reference.get() != null && mDialog == null
                    || !mDialog.isShowing()) {
                mDialog = ProgressDialog.show(reference.get(), null, reference
                        .get().getString(R.string.waiting), false, false);
            }
        }

        @Override
        protected Uri doInBackground(Void... params) {
            Uri uri = null;
            // must try-catch, maybe getCroppedImage() method crash because not
            // set bitmap in mCropImageView
            try {
                Bitmap bitmap = mCropImageView.getCroppedImage();
                uri = saveBitmapCropped(bitmap);
                if (bitmap != null) {
                    bitmap.recycle();
                    bitmap = null;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return uri;
        }

        @Override
        protected void onPostExecute(Uri result) {
            super.onPostExecute(result);
            if (mDialog != null) {
                mDialog.dismiss();
                mDialog = null;
            }
            mMediaItemSelected.setUriCropped(result);
            mCropListener.onSuccess(mMediaItemSelected);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSaveFileCroppedTask != null) {
            mSaveFileCroppedTask.cancel(true);
            mSaveFileCroppedTask = null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mCropImageView = null;
        mDialog = null;
        mSave = null;
        mCancel = null;
        mRotateLeft = null;
        mRotateRight = null;
    }
}
