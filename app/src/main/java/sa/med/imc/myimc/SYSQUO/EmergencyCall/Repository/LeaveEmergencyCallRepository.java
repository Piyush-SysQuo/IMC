package sa.med.imc.myimc.SYSQUO.EmergencyCall.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sa.med.imc.myimc.SYSQUO.EmergencyCall.Model.EmergencyCallRequestModel;
import sa.med.imc.myimc.SYSQUO.EmergencyCall.Model.LeaveEmergencyCallRequestModel;
import sa.med.imc.myimc.SYSQUO.EmergencyCall.Model.LeaveEmergencyCallResponseModel;
import sa.med.imc.myimc.SYSQUO.Network.APIClass;
import sa.med.imc.myimc.SYSQUO.Network.RetrofitInstance;

public class LeaveEmergencyCallRepository {

    private MutableLiveData<LeaveEmergencyCallResponseModel> mutableLiveData;

    public LeaveEmergencyCallRepository() {

        mutableLiveData = new MutableLiveData<>();
    }

    public void leaveEmergencyCallAPI(@NonNull String token, LeaveEmergencyCallRequestModel leaveEmergencyCallRequestModel){
        APIClass apiDataService = RetrofitInstance.getInstance().create(APIClass.class);
        Call<LeaveEmergencyCallResponseModel> call = apiDataService.leaveEmergencyCall("Bearer "+token, leaveEmergencyCallRequestModel);
        call.enqueue(new Callback<LeaveEmergencyCallResponseModel>() {
            @Override
            public void onResponse(Call<LeaveEmergencyCallResponseModel> call, Response<LeaveEmergencyCallResponseModel> response) {
                if (response.body() != null) {
                    mutableLiveData.postValue(response.body());
                }else{
                    mutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<LeaveEmergencyCallResponseModel> call, Throwable t) {
                mutableLiveData.postValue(null);
            }
        });
    }




    public LiveData<LeaveEmergencyCallResponseModel> LeaveEmergencyCallResponseLiveData() {
        return mutableLiveData;
    }


}
