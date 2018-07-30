package cardio.com.cardio.common.Firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseConfig {

    private static DatabaseReference referenciaFirebase;
    private static FirebaseAuth autentication;

    public static DatabaseReference getFirebase(){
        if(referenciaFirebase == null){
            referenciaFirebase = FirebaseDatabase.getInstance().getReference();
        }
        return referenciaFirebase;
    }

    public static FirebaseAuth getFirebaseAuth(){
        if(autentication == null){
            autentication = FirebaseAuth.getInstance();
        }
        return autentication;
    }
}
