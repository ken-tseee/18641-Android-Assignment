package model;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

public class GeoInfo extends Service implements LocationListener {
    private final Context context;
    private final String LOG_TAG = GeoInfo.class.getSimpleName();
    private final long PERIOD_TO_REFRESH_GPS = 60 * 1000;
    private final long DISTANCE_TO_REFRESH_GPS = 10;

    private boolean isNetworkEnabled = false;
    private boolean isGPSEnabled = false;
    private boolean canGetLocation = false;

    private double latitude;
    private double longitude;
    private LocationManager locationManager;
    private Location location;

    /**
     * Constructor.
     * @param context
     */
    public GeoInfo(Context context) {
        this.context = context;
        this.location = getLocation();
    }

    /**
     * Get the location information.
     * @return
     */
    public Location getLocation() {
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( context, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }

        try {
            locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

            if (locationManager == null) {
                throw new Exception("Cannot get LocationManager!");
            }

            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (!isNetworkEnabled() && !isGPSEnabled()) {
                canGetLocation = false;
                throw new Exception("Please check your GPS setting and network setting!");
            } else {
                canGetLocation = true;

                if (isNetworkEnabled()) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            PERIOD_TO_REFRESH_GPS,
                            DISTANCE_TO_REFRESH_GPS,
                            this);
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }

                if (isGPSEnabled()) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                PERIOD_TO_REFRESH_GPS,
                                DISTANCE_TO_REFRESH_GPS,
                                this);
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }

                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }
            }

        } catch (Exception e) {
            Log.d(LOG_TAG, "Cannot get the location! Because " + e);
            return null;
        }
        return location;
    }

    /**
     * Stop getting location information.
     */
    public void stopGPS() {
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( context, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }

    /**
     * Get the latitude of the location.
     * @return
     */
    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }
        return latitude;
    }

    /**
     * Get the longitude of the location.
     * @return
     */
    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }
        return longitude;
    }

    /**
     * Indicate whether the network is enabled.
     * @return
     */
    public boolean isNetworkEnabled() {
        return isNetworkEnabled;
    }

    /**
     * Indicate whether thr GPS is enabled.
     * @return
     */
    public boolean isGPSEnabled() {
        return isGPSEnabled;
    }

    /**
     * Indicate whether the location can be got.
     * @return
     */
    public boolean canGetLocation() {
        return canGetLocation;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
