package com.example.samad786.cyclone.Activities.Fragments;
import android.accessibilityservice.GestureDescription;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.samad786.cyclone.R;
/**
 * Created by samad786 on 4/20/2017.
 */
public class OrderFragment  extends Fragment {
    Button sendorder;
    ImageView delivery,collection,card,cash;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_fragment, null, false);
        delivery=(ImageView)view.findViewById(R.id.delivery);
        collection=(ImageView)view.findViewById(R.id.collection);
        card=(ImageView)view.findViewById(R.id.card);
        cash=(ImageView)view.findViewById(R.id.cash);
        delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delivery.setBackgroundResource(R.drawable.checked);
                collection.setBackgroundResource(R.drawable.unchecked);
                showMessage("Delivery Selected","R25 added to your Bill!");
            }
        });
        collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delivery.setBackgroundResource(R.drawable.unchecked);
                collection.setBackgroundResource(R.drawable.checked);
                showMessage("Collection Selected","R25 added to your Bill!");
            }
        });
        cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cash.setBackgroundResource(R.drawable.checked);
                card.setBackgroundResource(R.drawable.unchecked);
                showMessage("Payment Type","Cash payment selected");
            }
        });
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card.setBackgroundResource(R.drawable.checked);
                cash.setBackgroundResource(R.drawable.unchecked);
                showMessage("Payment Type","Card payment selected");
            }
        });
        sendorder=(Button)view.findViewById(R.id.sendorder);
        sendorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager=getActivity().getSupportFragmentManager();
                FragmentTransaction transaction=manager.beginTransaction();
                transaction.replace(R.id.containerView,new payment_fragment()).commit();
            }
        });
        return view;
    }
    private  void showMessage(String title,String message)
    {
        // custom dialog
        final Dialog dialog = new android.app.Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.payment_dialog);

        //dialog.setTitle("Title...");

        // set the custom dialog components - text, image and button
        TextView t=(TextView)dialog.findViewById(R.id.title);
        t.setText(title);
        TextView m=(TextView)dialog.findViewById(R.id.message);
        m.setText(message);
        Button dialogButton = (Button) dialog.findViewById(R.id.ok);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
}