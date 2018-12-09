package dgp.ugr.granaroutes.data;

import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

import dgp.ugr.granaroutes.R;
import dgp.ugr.granaroutes.adaptador.Adaptador;

import static android.support.constraint.Constraints.TAG;

public class ContentProvider implements Serializable {

    private ArrayList<Ruta> rutas;
    private static volatile ContentProvider instanciado;

    //private constructor.
    private ContentProvider(){

        //Prevent form the reflection api.
        if (instanciado != null){
            throw new RuntimeException("Utilizar .getInstance() para convocar ContentProvider");
        }
    }

    public static ContentProvider getInstance() {
        if (instanciado == null) { //if there is no instance available... create new one
            synchronized (ContentProvider.class) {
                if (instanciado == null) instanciado = new ContentProvider();
            }
        }

        return instanciado;
    }

    //Make singleton from serialize and deserialize operation.
    protected ContentProvider readResolve() {
        return getInstance();
    }

    public void fetchRutas(ArrayList<Ruta> arrayList){
        rutas = arrayList;
    }

    public ArrayList<Ruta> getRutas() {
        return rutas;
    }
}

