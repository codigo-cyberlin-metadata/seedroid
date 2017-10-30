package id.codigo.seedroid_mediapicker.activities;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.codigo.seedroid_mediapicker.MediaAdapter;
import id.codigo.seedroid_mediapicker.MediaItem;
import id.codigo.seedroid_mediapicker.MediaOption;
import id.codigo.seedroid_mediapicker.MediaSelectedListener;
import id.codigo.seedroid_mediapicker.R;
import id.codigo.seedroid_mediapicker.utils.MediaUtils;
import id.codigo.seedroid_mediapicker.utils.Utils;
import id.codigo.seedroid_mediapicker.widget.HeaderGridView;
import id.codigo.seedroid_mediapicker.widget.PickerImageView;

/**
 * Created by papahnakal on 19/10/17.
 */

public class SeedroidMediaPickerFragment extends BaseFragment implements
        LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener {
    private static final String LOADER_EXTRA_URI = "loader_extra_uri";
    private static final String LOADER_EXTRA_PROJECT = "loader_extra_project";
    private static final String KEY_MEDIA_TYPE = "media_type";
    private static final String KEY_GRID_STATE = "grid_state";
    private static final String KEY_MEDIA_SELECTED_LIST = "media_selected_list";

    private HeaderGridView mGridView;
    private TextView mNoItemView;
    private MediaAdapter mMediaAdapter;
    private MediaOption mMediaOptions;
    private MediaSelectedListener mMediaSelectedListener;
    private Bundle mSavedInstanceState;
    private List<MediaItem> mMediaSelectedList;

    private int mMediaType;
    private int mPhotoSize, mPhotoSpacing;

    public SeedroidMediaPickerFragment() {
        mSavedInstanceState = new Bundle();
    }

    public static SeedroidMediaPickerFragment newInstance(MediaOption options) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(SeedroidMediaPickerActivity.EXTRA_MEDIA_OPTIONS, options);
        SeedroidMediaPickerFragment fragment = new SeedroidMediaPickerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mMediaSelectedListener = (MediaSelectedListener) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mMediaOptions = savedInstanceState
                    .getParcelable(SeedroidMediaPickerActivity.EXTRA_MEDIA_OPTIONS);
            mMediaType = savedInstanceState.getInt(KEY_MEDIA_TYPE);
            mMediaSelectedList = savedInstanceState
                    .getParcelableArrayList(KEY_MEDIA_SELECTED_LIST);
            mSavedInstanceState = savedInstanceState;
        } else {
            mMediaOptions = getArguments().getParcelable(
                    SeedroidMediaPickerActivity.EXTRA_MEDIA_OPTIONS);
            if (mMediaOptions.canSelectPhotoAndVideo()
                    || mMediaOptions.canSelectPhoto()) {
                mMediaType = MediaItem.PHOTO;
            } else {
                mMediaType = MediaItem.VIDEO;
            }
            mMediaSelectedList = mMediaOptions.getMediaListSelected();
            // Override mediaType by 1st item media if has media selected.
            if (mMediaSelectedList != null && mMediaSelectedList.size() > 0) {
                mMediaType = mMediaSelectedList.get(0).getType();
            }
        }
        // get the photo size and spacing
        mPhotoSize = getResources().getDimensionPixelSize(
                R.dimen.picker_photo_size);
        mPhotoSpacing = getResources().getDimensionPixelSize(
                R.dimen.picker_photo_spacing);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mediapicker, container,
                false);
        initView(root);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mMediaType == MediaItem.PHOTO) {
            requestPhotos(false);
        } else {
            requestVideos(false);
        }
    }

    private void requestPhotos(boolean isRestart) {
        requestMedia(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                MediaUtils.PROJECT_PHOTO, isRestart);
    }

    private void requestVideos(boolean isRestart) {
        requestMedia(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                MediaUtils.PROJECT_VIDEO, isRestart);
    }

    private void requestMedia(Uri uri, String[] projects, boolean isRestart) {
        Bundle bundle = new Bundle();
        bundle.putStringArray(LOADER_EXTRA_PROJECT, projects);
        bundle.putString(LOADER_EXTRA_URI, uri.toString());
        if (isRestart)
            getLoaderManager().restartLoader(0, bundle, this);
        else
            getLoaderManager().initLoader(0, bundle, this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mGridView != null) {
            mSavedInstanceState.putParcelable(KEY_GRID_STATE,
                    mGridView.onSaveInstanceState());
        }
        mSavedInstanceState.putParcelable(
                SeedroidMediaPickerActivity.EXTRA_MEDIA_OPTIONS, mMediaOptions);
        mSavedInstanceState.putInt(KEY_MEDIA_TYPE, mMediaType);
        mSavedInstanceState.putParcelableArrayList(KEY_MEDIA_SELECTED_LIST,
                (ArrayList<MediaItem>) mMediaSelectedList);
        outState.putAll(mSavedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        Uri uri = Uri.parse(bundle.getString(LOADER_EXTRA_URI));
        String[] projects = bundle.getStringArray(LOADER_EXTRA_PROJECT);
        String order = MediaStore.MediaColumns.DATE_ADDED + " DESC";
        return new CursorLoader(mContext, uri, projects, null, null, order);
    }

    private void bindData(Cursor cursor) {
        if (cursor == null || cursor.getCount() <= 0) {
            switchToError();
            return;
        }
        switchToData();
        if (mMediaAdapter == null) {
            mMediaAdapter = new MediaAdapter(mContext, cursor, 0,
                    mMediaImageLoader, mMediaType, mMediaOptions);
        } else {
            mMediaAdapter.setMediaType(mMediaType);
            mMediaAdapter.swapCursor(cursor);
        }
        if (mGridView.getAdapter() == null) {
            mGridView.setAdapter(mMediaAdapter);
            mGridView.setRecyclerListener(mMediaAdapter);
        }
        Parcelable state = mSavedInstanceState.getParcelable(KEY_GRID_STATE);
        if (state != null) {
            mGridView.onRestoreInstanceState(state);
        }
        if (mMediaSelectedList != null) {
            mMediaAdapter.setMediaSelectedList(mMediaSelectedList);
        }
        mMediaAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        bindData(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Preference:http://developer.android.com/guide/components/loaders.html#callback
        if (mMediaAdapter != null)
            mMediaAdapter.swapCursor(null);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Object object = parent.getAdapter().getItem(position);
        if (object instanceof Cursor) {
            Uri uri;
            if (mMediaType == MediaItem.PHOTO) {
                uri = MediaUtils.getPhotoUri((Cursor) object);
            } else {
                uri = MediaUtils.getVideoUri((Cursor) object);
            }
            PickerImageView pickerImageView = (PickerImageView) view
                    .findViewById(R.id.thumbnail);
            MediaItem mediaItem = new MediaItem(mMediaType, uri);
            mMediaAdapter.updateMediaSelected(mediaItem, pickerImageView);
            mMediaSelectedList = mMediaAdapter.getMediaSelectedList();

            if (mMediaAdapter.hasSelected()) {
                mMediaSelectedListener.onHasSelected(mMediaAdapter
                        .getMediaSelectedList());
            } else {
                mMediaSelectedListener.onHasNoSelected();
            }
        }
    }

    public void switchMediaSelector() {
        if (!mMediaOptions.canSelectPhotoAndVideo())
            return;
        if (mMediaType == MediaItem.PHOTO) {
            mMediaType = MediaItem.VIDEO;
        } else {
            mMediaType = MediaItem.PHOTO;
        }
        switch (mMediaType) {
            case MediaItem.PHOTO:
                requestPhotos(true);
                break;
            case MediaItem.VIDEO:
                requestVideos(true);
                break;
            default:
                break;
        }
    }

    public List<MediaItem> getMediaSelectedList() {
        return mMediaSelectedList;
    }

    public boolean hasMediaSelected() {
        return mMediaSelectedList != null && mMediaSelectedList.size() > 0;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mGridView != null) {
            mSavedInstanceState.putParcelable(KEY_GRID_STATE,
                    mGridView.onSaveInstanceState());
            mGridView = null;
        }
        if (mMediaAdapter != null) {
            mMediaAdapter.onDestroyView();
        }
    }

    public int getMediaType() {
        return mMediaType;
    }

    private void switchToData() {
        mNoItemView.setVisibility(View.GONE);
        mNoItemView.setText(null);
        mGridView.setVisibility(View.VISIBLE);
    }

    private void switchToError() {
        mNoItemView.setVisibility(View.VISIBLE);
        mNoItemView.setText(R.string.picker_no_items);
        mGridView.setVisibility(View.GONE);
    }

    private void initView(View view) {
        mGridView = (HeaderGridView) view.findViewById(R.id.grid);
        View header = new View(getActivity());
        ViewGroup.LayoutParams params = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                Utils.getActionbarHeight(getActivity()));
        header.setLayoutParams(params);
        mGridView.addHeaderView(header);

        mGridView.setOnItemClickListener(this);
        mNoItemView = (TextView) view.findViewById(R.id.no_data);

        // get the view tree observer of the grid and set the height and numcols
        // dynamically
        mGridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if (mMediaAdapter != null
                                && mMediaAdapter.getNumColumns() == 0) {
                            final int numColumns = (int) Math.floor(mGridView
                                    .getWidth() / (mPhotoSize + mPhotoSpacing));
                            if (numColumns > 0) {
                                final int columnWidth = (mGridView.getWidth() / numColumns)
                                        - mPhotoSpacing;
                                mMediaAdapter.setNumColumns(numColumns);
                                mMediaAdapter.setItemHeight(columnWidth);
                            }
                        }
                    }
                });
    }
}
