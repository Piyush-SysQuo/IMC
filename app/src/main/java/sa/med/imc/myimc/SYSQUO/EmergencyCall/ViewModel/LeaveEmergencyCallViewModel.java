package sa.med.imc.myimc.SYSQUO.EmergencyCall.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import sa.med.imc.myimc.SYSQUO.EmergencyCall.Model.EmergencyCallRequestModel;
import sa.med.imc.myimc.SYSQUO.EmergencyCall.Model.LeaveEmergencyCallRequestModel;
import sa.med.imc.myimc.SYSQUO.EmergencyCall.Model.LeaveEmergencyCallResponseModel;
import sa.med.imc.myimc.SYSQUO.EmergencyCall.Repository.EmergencyCallRepository;
import sa.med.imc.myimc.SYSQUO.EmergencyCall.Repository.LeaveEmergencyCallRepository;


public class LeaveEmergencyCallViewModel extends AndroidViewModel {

    private LiveData<LeaveEmergencyCallResponseModel> leaveEmergencyCallResponseModelLiveData;
    private LeaveEmergencyCallRepository leaveEmergencyCallRepository;



    public LeaveEmergencyCallViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        leaveEmergencyCallRepository = new LeaveEmergencyCallRepository();
        leaveEmergencyCallResponseModelLiveData = leaveEmergencyCallRepository.LeaveEmergencyCallResponseLiveData();
    }

    public void LeaveEmergencyCall(String token, LeaveEmergencyCallRequestModel leaveEmergencyCallRequestModel) {

        leaveEmergencyCallRepository.leaveEmergencyCallAPI(token, leaveEmergencyCallRequestModel);
    }

    public LiveData<LeaveEmergencyCallResponseModel> getVolumesResponseLiveData() {
        return leaveEmergencyCallResponseModelLiveData;
    }


}
