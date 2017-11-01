package id.codigo.seedroid_mediapicker.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import id.codigo.seedroid_mediapicker.R;

/**
 * Created by papahnakal on 19/10/17.
 */

public class SeedroidMediaPickerErrorDialog extends DialogFragment {
    private String mMessage;
    private DialogInterface.OnClickListener mOnPositionClickListener;

    public static SeedroidMediaPickerErrorDialog newInstance(String msg) {
        SeedroidMediaPickerErrorDialog dialog = new SeedroidMediaPickerErrorDialog();
        Bundle bundle = new Bundle();
        bundle.putString("msg", msg);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMessage = getArguments().getString("msg");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setMessage(mMessage)
                .setPositiveButton(R.string.ok, mOnPositionClickListener)
                .create();
    }

    public void setOnOKClickListener(DialogInterface.OnClickListener mOnClickListener) {
        this.mOnPositionClickListener = mOnClickListener;
    }
}
