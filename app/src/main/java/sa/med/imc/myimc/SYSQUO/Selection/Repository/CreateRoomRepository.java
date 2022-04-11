package sa.med.imc.myimc.SYSQUO.Selection.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sa.med.imc.myimc.SYSQUO.Network.APIClass;
import sa.med.imc.myimc.SYSQUO.Network.RetrofitInstance;
import sa.med.imc.myimc.SYSQUO.Selection.Model.CreateRoomRequestModel;
import sa.med.imc.myimc.SYSQUO.Selection.Model.CreateRoomResponseModel;

public class CreateRoomRepository {

    private MutableLiveData<CreateRoomResponseModel> mutableLiveData;

    public CreateRoomRepository() {

        mutableLiveData = new MutableLiveData<>();
    }

    public void createRoomAPI(String token, CreateRoomRequestModel createRoomRequestModel){
        APIClass apiDataService = RetrofitInstance.getInstance().create(APIClass.class);
        Call<CreateRoomResponseModel> call = apiDataService.createRoom("Bearer "+token, createRoomRequestModel);
        call.enqueue(new Callback<CreateRoomResponseModel>() {
            @Override
            public void onResponse(Call<CreateRoomResponseModel> call, Response<CreateRoomResponseModel> response) {
                if (response.body() != null) {
                    mutableLiveData.postValue(response.body());
                }else{
                    mutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<CreateRoomResponseModel> call, Throwable t) {
                mutableLiveData.postValue(null);
            }
        });
    }




    public LiveData<CreateRoomResponseModel> CreateRoomResponseLiveData() {
        return mutableLiveData;
    }


}
