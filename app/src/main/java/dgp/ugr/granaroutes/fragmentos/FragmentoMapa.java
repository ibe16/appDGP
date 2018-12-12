package dgp.ugr.granaroutes.fragmentos;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import dgp.ugr.granaroutes.R;

public class FragmentoMapa extends Fragment implements OnMapReadyCallback {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapa);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        cargaMapa();
        return inflater.inflate(R.layout.layout_actividad_mapa, null);


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
/*        mapa = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mapa.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mapa.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/


    }

    private void cargaMapa(){
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setComponent(ComponentName.unflattenFromString("com.google.android.apps.maps/com.google.android.maps.MapsActivity"));
        intent.addCategory("android.intent.category.LAUNCHER");
        // replace string with your Google My Map URL
        Uri uri = Uri.parse("https://www.google.com/maps/d/u/1/viewer?ll=37.16188504127159%2C-3.681225026642096&z=14&mid=1jwDsNlODaJ53eT_DB7YrcCNSV4hLe_zk");
        intent.setData(uri);
        startActivity(intent);
    }
}
