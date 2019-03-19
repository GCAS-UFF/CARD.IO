package cardio.uff.cardio.common.Firebase;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import android.util.Log;

public class CardioJobService extends JobService {
    private static final String TAG = "MyJobService";

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG, "Performing long running task in scheduled job");
        // TODO(developer): add long running task here.
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
