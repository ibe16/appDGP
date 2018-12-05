package dgp.ugr.granaroutes.fragmentos;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

import dgp.ugr.granaroutes.R;
import dgp.ugr.granaroutes.adaptador_recyclerview.Adaptador;
import dgp.ugr.granaroutes.data.Ruta;

import static android.support.constraint.Constraints.TAG;

public class FragmentoRutas extends Fragment {

    private RecyclerView recyclerView;
    private ProgressBar cargando;
    private DatabaseReference mDatabase;
    private DatabaseReference mRutas;
    private ArrayList<ArrayList<Ruta>> rutas;
    private ArrayList<String[]> rutasRaw;
    private ArrayList<Uri> mapas;
    private JsonReader jsonReader;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_actividad_rutas, null);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        rutasRaw = new ArrayList<>();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()){

                    rutasRaw.add(d.toString().split("\\{"));
                    JSONObject jObject = null;
                    JSONArray rutasArray = null;
                    try {
                        jObject = new JSONObject(d.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        rutasArray = jObject.getJSONArray("rutas");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    for (int i=0; i < rutasArray.length(); i++)
                    {
                        try {
                            JSONObject oneObject = rutasArray.getJSONObject(i);
                            // Pulling items from the array
                            String oneObjectsItem = oneObject.getString("STRINGNAMEinTHEarray");
                            String oneObjectsItem2 = oneObject.getString("anotherSTRINGNAMEINtheARRAY");
                        } catch (JSONException e) {
                            // Oops
                        }
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        //Adaptador adaptador = new Adaptador(this,0);

        recyclerView = view.findViewById(R.id.rv_rutas);
        cargando = view.findViewById(R.id.pb_loading_indicator);
        //recyclerView.setAdapter(adaptador);

        return view;
    }
}
