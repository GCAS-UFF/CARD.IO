package cardio.com.cardio.common.Firebase;

import android.content.Context;
import android.provider.ContactsContract;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cardio.com.cardio.common.model.model.Paciente;
import cardio.com.cardio.common.util.PreferencesUtils;

public class FirebaseHelper {

//    PARAMETROS

    private Context context;
    private static FirebaseHelper firebaseHelper;

    public static final String PACIENT_KEY = "Paciente";
    public static final String PROFESSIONAL_KEY = "Profissional";
    public static final String USER_KEY = "Usuario";
    public static final String PERFORMED_ACTION_KEY = "AcoesRealizadas";
    public static final String RECOMMENDED_ACTION_KEY = "AcoesRecomendadas";
    public static final String PATIENT_LIST_KEY = "ListaDePacientes";
    public static final String USER_TYPE_KEY = "Tipo";
    public static final String MEDICAO_DADOS_FISIOLOGICOS_KEY = "MedicaoDadosFisiologicos";
    public static final String ALIMENTACAO_KEY = "Alimentacao";
    public static final String EXERCICIO_KEY = "Exercicio";
    public static final String QUANTITY_KEY = "Quantity";

    private DatabaseReference genericPatientDatabaseReference = FirebaseDatabase.getInstance().getReference()
            .child(PACIENT_KEY);

    private DatabaseReference genericProfessionalDatabaseReference = FirebaseDatabase.getInstance().getReference()
            .child(PROFESSIONAL_KEY);

    private DatabaseReference genericUserDatabaseReference = FirebaseDatabase.getInstance().getReference()
            .child(USER_KEY);

//    METODOS

    private FirebaseHelper(Context context){
        this.context = context;
    }

    public static void initialize (Context context) {
        firebaseHelper = new FirebaseHelper(context);
    }

    public static FirebaseHelper getInstance() {
        return firebaseHelper;
    }


    public DatabaseReference getPatientDatabaseReference(String id){
        return genericPatientDatabaseReference.child(id);
    }

    public DatabaseReference getProfessionalDatabaseReference(String id){
        return genericProfessionalDatabaseReference.child(id);
    }

    public DatabaseReference getPatientListDatabaseReference (String professionalId){
        return getProfessionalDatabaseReference(professionalId).child(PATIENT_LIST_KEY);
    }

    public DatabaseReference getUserDatabaseReference (String id){
        return genericUserDatabaseReference.child(id);
    }

    public DatabaseReference getUserTypeDatabaseReference (String userId){
        return getUserDatabaseReference(userId).child(USER_TYPE_KEY);
    }

    public DatabaseReference getCurrentProfesionalDatabaseReference (){
        return getProfessionalDatabaseReference(PreferencesUtils.getString(context, FirebaseHelper.USER_KEY));
    }

    public DatabaseReference getCurrentPatientDatabaseReference (){
        return getPatientDatabaseReference(PreferencesUtils.getString(context, FirebaseHelper.USER_KEY));
    }

    public DatabaseReference getCurrentPatientListDatabaseReference (){
        return getPatientListDatabaseReference(PreferencesUtils.getString(context, FirebaseHelper.USER_KEY));
    }
}
