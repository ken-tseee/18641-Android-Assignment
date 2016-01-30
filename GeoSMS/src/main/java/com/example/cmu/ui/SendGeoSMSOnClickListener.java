package com.example.cmu.ui;

import android.app.Activity;
import android.content.Context;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import model.GeoInfo;

/**
 * Author: Junjian Xie
 * Email: junjianx@andrew.cmu.edu
 * Date: 15/11/14
 */
public class SendGeoSMSOnClickListener implements View.OnClickListener {
    private final String LOG_TAG = SendGeoSMSOnClickListener.class.getSimpleName();
    private Activity activity;

    /**
     * Constructor.
     * @param activity
     */
    public SendGeoSMSOnClickListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        GeoInfo geoInfo = new GeoInfo(activity);
        if (geoInfo.canGetLocation()) {
            double latitude = geoInfo.getLatitude();
            double longitude = geoInfo.getLongitude();

            TextView textView = (TextView) activity.findViewById(R.id.text_show_location);
            textView.setText("Location:\n" +
                    "\t\tLatitude: " + latitude + "\n" +
                    "\t\tLongitude: " + longitude);

            try {
                TelephonyManager telephonyManager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
                String phoneNumber = telephonyManager.getLine1Number();

                sendSMS(phoneNumber,
                        "Location:\n" +
                                "\t\tLatitude: " + latitude + "\n" +
                                "\t\tLongitude: " + longitude);

                Toast.makeText(activity.getApplicationContext(),
                        "GeoSMS is successfully sent!",
                        Toast.LENGTH_LONG).show();
                Log.d(LOG_TAG, "Successfully send GeoSMS!");
            } catch (Exception e) {
                Toast.makeText(activity.getApplicationContext(),
                        "Fail to send GeoSMS. \nPlease try again!",
                        Toast.LENGTH_LONG).show();
                Log.d(LOG_TAG, "Fail to send GeoSMS because " + e + ".");
            }
        } else {
            Toast.makeText(activity.getApplicationContext(),
                    "Cannot get the location!\nPlease check the setting and try again!",
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Send an SMS.
     * @param phoneNumber
     * @param message
     */
    private void sendSMS(String phoneNumber, String message) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
    }
}
