package cardio.com.cardio.liquidDialog.model;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import cardio.com.cardio.common.Firebase.FirebaseHelper;
import cardio.com.cardio.common.model.model.Alimentacao;
import cardio.com.cardio.common.model.model.Recomentation;
import cardio.com.cardio.common.util.PreferencesUtils;
import cardio.com.cardio.liquidDialog.presenter.LiquidDialogPresenter;

public class LiquidDialogModelImp implements LiquidDialogModel {

    private Context mContext;
    private LiquidDialogPresenter mLiquidDialogPresenter;

    public LiquidDialogModelImp(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void setLiquidDialogPresenter(LiquidDialogPresenter liquidDialogPresenter) {
        this.mLiquidDialogPresenter = liquidDialogPresenter;
    }

    @Override
    public void initializeMetaDataListeners() {
        FirebaseHelper.quantidadeBebidasDatabaseReference.addListenerForSingleValueEvent(getBebidasMetadata);
    }

    public String getCurrentPatientKey() {
        return PreferencesUtils.getString(mContext, PreferencesUtils.CURRENT_PATIENT_KEY);
    }

    @Override
    public void saveIntoFirebase(Recomentation recomentation){
        try {
            DatabaseReference mDbRef = FirebaseHelper.getInstance()
                    .getPatientDatabaseReference(getCurrentPatientKey())
                    .child(FirebaseHelper.PERFORMED_ACTION_KEY)
                    .child(FirebaseHelper.ALIMENTACAO_KEY);

            recomentation.setId(mDbRef.push().getKey());
            mDbRef.child(recomentation.getId()).child(FirebaseHelper.LIQUID_KEY).setValue(((Alimentacao) recomentation.getAction()).getFood());
            mDbRef.child(recomentation.getId()).child(FirebaseHelper.QUANTITY_KEY).setValue(((Alimentacao) recomentation.getAction()).getQuantidade());
            mDbRef.child(recomentation.getId()).child(FirebaseHelper.PERFORMED_EXECUTED_DATE).setValue((recomentation.getAction()).getExecutedDate());

            mLiquidDialogPresenter.finishSendRecomendation(true);
        }catch (Exception e){
            e.printStackTrace();
            mLiquidDialogPresenter.finishSendRecomendation(false);
        }
    }

    private ValueEventListener getBebidasMetadata = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            Map<String, String> options = new HashMap<>();

            for (DataSnapshot entrySnapshot : dataSnapshot.getChildren()){
                options.put(entrySnapshot.getKey(), String.valueOf(entrySnapshot.getValue()));
            }

            mLiquidDialogPresenter.finishLoadBebidasMetadata(options);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
