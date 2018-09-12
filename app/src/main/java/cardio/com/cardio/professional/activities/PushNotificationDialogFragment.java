package cardio.com.cardio.professional.activities;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import cardio.com.cardio.R;

public class PushNotificationDialogFragment extends DialogFragment implements View.OnClickListener {

    private TextView title;
    private TextView body;
    private Button btnClose;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.push_notification_dialog_fragment, container, false);

        ArrayList<String> message = getArguments().getStringArrayList("msg");

        title = (TextView) view.findViewById(R.id.title);
        body = (TextView) view.findViewById(R.id.bordy);
        btnClose = (Button) view.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(this);

        title.setText(message.get(0));
        body.setText(message.get(1));

        return view;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    @Override
    public void onClick(View v) {
        this.dismiss();
    }


}
