package cardio.com.cardio.common.Firebase;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.iid.FirebaseInstanceId;

import java.text.ParseException;

import cardio.com.cardio.common.util.Formater;
import cardio.com.cardio.common.util.PreferencesUtils;

public class FirebaseHelper{

//    PARAMETROS

    private Context context;
    private static FirebaseHelper firebaseHelper;

    public static final String PATIENT_KEY = "Paciente";
    public static final String PROFESSIONAL_KEY = "Profissional";
    public static final String USER_KEY = "Usuario";
    public static final String APPOINTMENT_KEY = "Consultas";
    public static final String PERFORMED_ACTION_KEY = "AcoesRealizadas";
    public static final String RECOMMENDED_ACTION_KEY = "AcoesRecomendadas";
    public static final String PATIENT_LIST_KEY = "ListaDePacientes";
    public static final String USER_TYPE_KEY = "Tipo";
    public static final String MEDICAO_DADOS_FISIOLOGICOS_KEY = "MedicaoDadosFisiologicos";
    public static final String ALIMENTACAO_KEY = "Alimentacao";
    public static final String EXERCICIO_KEY = "Exercicio";
    public static final String MEDICINE_KEY = "Medicamento";
    public static final String QUANTITY_KEY = "quantidade";
    public static final String METADATA_KEY = "Metadados";
    public static final String ADRESS_KEY = "Endereco";
    public static final String SPECIALITY_KEY = "Especialidades";
    public static final String APPOINTMENT_PATIENT_KEY = "paciente";
    public static final String APPOINTMENT_DATA_KEY = "data";
    public static final String QUANTITY_BEVERAGE_KEY = "QuantidadeBebidas";
    public static final String MEDICINE_NAME_KEY = "name";
    public static final String MEDICINE_DOSAGE_KEY = "dosagem";
    public static final String MEDICINE_NOTE_KEY = "observação";
    public static final String MEDICINE_START_HOUR_KEY = "horaInicial";
    public static final String MEDICINE_PROFESSIONAL_KEY = "profissionalResponsavel";
    public static final String EXCERCISE_NAME_KEY = "Atividade";
    public static final String EXCERCISE_INTENSITY_KEY = "Intensidade";
    public static final String EXERCISE_DURATION_KEY = "Duração";
    public static final String PERFORMED_EXECUTED_DATE = "executedDate";

//  DATABASE REFERENCES

    public static final DatabaseReference specialitiesDatabaseReference = FirebaseDatabase.getInstance()
            .getReference()
            .child(METADATA_KEY)
            .child(SPECIALITY_KEY);

    public static final DatabaseReference quantidadeBebidasDatabaseReference = FirebaseDatabase.getInstance().getReference()
            .child(METADATA_KEY)
            .child(QUANTITY_BEVERAGE_KEY);

    public static final DatabaseReference adressDatabaseReference = FirebaseDatabase.getInstance().getReference()
            .child(METADATA_KEY)
            .child(ADRESS_KEY);

    public static final DatabaseReference appointmentDatabaseReference = FirebaseDatabase.getInstance().getReference()
            .child(APPOINTMENT_KEY);

    private DatabaseReference genericPatientDatabaseReference = FirebaseDatabase.getInstance().getReference()
            .child(PATIENT_KEY);

    private DatabaseReference genericProfessionalDatabaseReference = FirebaseDatabase.getInstance().getReference()
            .child(PROFESSIONAL_KEY);

    private DatabaseReference genericUserDatabaseReference = FirebaseDatabase.getInstance().getReference()
            .child(USER_KEY);


//    METODOS

    public static final DatabaseReference getAllPatientsListDatabaseReference(){
        return FirebaseDatabase.getInstance().getReference().child(PATIENT_KEY);
    }

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

    public Query getAppointmentByDateAndPatient (String dateStr, String userId) throws ParseException {
        long dateLong = Formater.getDateFromString(dateStr).getTime();
        long dayInMilliseconds = 86400000;

        return appointmentDatabaseReference.orderByChild(APPOINTMENT_DATA_KEY).startAt(dateLong);
    }
}