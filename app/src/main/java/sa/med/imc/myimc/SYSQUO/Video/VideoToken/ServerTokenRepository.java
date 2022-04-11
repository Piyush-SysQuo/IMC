package sa.med.imc.myimc.SYSQUO.Video.VideoToken;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sa.med.imc.myimc.SYSQUO.Network.APIClass;
import sa.med.imc.myimc.SYSQUO.Network.RetrofitInstance;

public class ServerTokenRepository {

    private MutableLiveData<ServerTokenResponseModel> mutableLiveData;

    public ServerTokenRepository() {

        mutableLiveData = new MutableLiveData<>();
    }

    public void serverTokenAPI(@NonNull String identity, @NonNull String roomName){
        APIClass apiDataService = RetrofitInstance.getInstance().create(APIClass.class);
        Call<ServerTokenResponseModel> call = apiDataService.serverToken(identity, roomName);
        call.enqueue(new Callback<ServerTokenResponseModel>() {
            @Override
            public void onResponse(Call<ServerTokenResponseModel> call, Response<ServerTokenResponseModel> response) {
                if (response.body() != null) {
                    mutableLiveData.postValue(response.body());
                }else{
                    mutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<ServerTokenResponseModel> call, Throwable t) {
                mutableLiveData.postValue(null);
            }
        });
    }




    public LiveData<ServerTokenResponseModel> AddBalResponseLiveData() {
        return mutableLiveData;
    }


}
