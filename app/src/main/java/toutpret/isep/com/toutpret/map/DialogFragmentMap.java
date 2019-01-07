package toutpret.isep.com.toutpret.map;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import toutpret.isep.com.toutpret.MainActivity;
import toutpret.isep.com.toutpret.R;

public class DialogFragmentMap extends DialogFragment {
    private MapView mapView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Mapbox.getInstance(getContext(), "pk.eyJ1IjoiaGljaGVtYWRkIiwiYSI6ImNqcWw4aGN4ZjB6NG40M25uMmgxYjNzZzIifQ.h8eqs-0JnQoOFVYIOzl4dw");

        View view = inflater.inflate(R.layout.fragment_map, container, false);

        //WindowManager.LayoutParams wmlp = getDialog().getWindow().getAttributes();
        // wmlp.gravity = Gravity.FILL_HORIZONTAL;

        mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        /*
        ///// Récupérer la location du client
        Location locationEngine = new LostLocationEngine(MainActivity.this);
        locationEngine.setPriority(LocationEnginePriority.HIGH_ACCURACY);
        locationEngine.setInterval(5000);
        locationEngine.activate();
        Location lastLocation = locationEngine.getLastLocation();
        */

        ///// Mettre le marqueur
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                IconFactory iconFactory = IconFactory.getInstance(getContext());
                Icon icon = iconFactory.fromResource(R.drawable.towel_small);

                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(48.13863, 11.57603))
                        .icon(icon));
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView.onStart();
    }

}
