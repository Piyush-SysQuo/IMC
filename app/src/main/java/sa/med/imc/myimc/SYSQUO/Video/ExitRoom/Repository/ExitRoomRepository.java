package sa.med.imc.myimc.SYSQUO.Video.ExitRoom.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sa.med.imc.myimc.SYSQUO.Network.APIClass;
import sa.med.imc.myimc.SYSQUO.Network.RetrofitInstance;
import sa.med.imc.myimc.SYSQUO.Video.ExitRoom.Model.ExitRoomRequestModel;
import sa.med.imc.myimc.SYSQUO.Video.ExitRoom.Model.ExitRoomResponseModel;

public class ExitRoomRepository {

    private MutableLiveData<ExitRoomResponseModel> mutableLiveData;

    public ExitRoomRepository() {

        mutableLiveData = new MutableLiveData<>();
    }

    public void exitRoomAPI(@NonNull String token, ExitRoomRequestModel exitRoomRequestModel){
        APIClass apiDataService = RetrofitInstance.getInstance().create(APIClass.class);
        Call<ExitRoomResponseModel> call;
//        if(isEmergency) {
            call = apiDataService.exitRoomEmergency("Bearer "+token, exitRoomRequestModel);
//        }
//        else {
//            call = apiDataService.exitRoom(identity);
//        }
        call.enqueue(new Callback<ExitRoomResponseModel>() {
            @Override
            public void onResponse(Call<ExitRoomResponseModel> call, Response<ExitRoomResponseModel> response) {
                if (response.body() != null) {
                    mutableLiveData.postValue(response.body());
                }else{
                    mutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<ExitRoomResponseModel> call, Throwable t) {
                mutableLiveData.postValue(null);
            }
        });
    }




    public LiveData<ExitRoomResponseModel> AddBalResponseLiveData() {
        return mutableLiveData;
    }


}
