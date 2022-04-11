package sa.med.imc.myimc.SYSQUO.Video.ExitRoom.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sa.med.imc.myimc.SYSQUO.Network.APIClass;
import sa.med.imc.myimc.SYSQUO.Network.RetrofitInstance;
import sa.med.imc.myimc.SYSQUO.Video.ExitRoom.Model.DisconnectRoomRequestModel;
import sa.med.imc.myimc.SYSQUO.Video.ExitRoom.Model.ExitRoomRequestModel;
import sa.med.imc.myimc.SYSQUO.Video.ExitRoom.Model.DisconnectRoomResponseModel;

public class DisconnectRoomRepository {

    private MutableLiveData<DisconnectRoomResponseModel> mutableLiveData;

    public DisconnectRoomRepository() {

        mutableLiveData = new MutableLiveData<>();
    }

    public void disconnectRoomAPI(@NonNull String token, DisconnectRoomRequestModel disconnectRoomRequestModel){
        APIClass apiDataService = RetrofitInstance.getInstance().create(APIClass.class);
        Call<DisconnectRoomResponseModel> call;
//        if(isEmergency) {
            call = apiDataService.disconnectRoom("Bearer "+token, disconnectRoomRequestModel);
//        }
//        else {
//            call = apiDataService.exitRoom(identity);
//        }
        call.enqueue(new Callback<DisconnectRoomResponseModel>() {
            @Override
            public void onResponse(Call<DisconnectRoomResponseModel> call, Response<DisconnectRoomResponseModel> response) {
                if (response.body() != null) {
                    mutableLiveData.postValue(response.body());
                }else{
                    mutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<DisconnectRoomResponseModel> call, Throwable t) {
                mutableLiveData.postValue(null);
            }
        });
    }




    public LiveData<DisconnectRoomResponseModel> DisconnectRoomResponseLiveData() {
        return mutableLiveData;
    }


}
