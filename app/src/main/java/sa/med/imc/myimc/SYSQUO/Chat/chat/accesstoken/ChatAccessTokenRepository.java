package sa.med.imc.myimc.SYSQUO.Chat.chat.accesstoken;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sa.med.imc.myimc.SYSQUO.Network.APIClass;
import sa.med.imc.myimc.SYSQUO.Network.RetrofitInstance;

public class ChatAccessTokenRepository {

    private MutableLiveData<ChatAccessTokenResponseModel> mutableLiveData;

    public ChatAccessTokenRepository() {

        mutableLiveData = new MutableLiveData<>();
    }

    public void chatAccessTokenAPI(@NonNull String token, ChatAccessTokenRequetModel chatAccessToken){
        APIClass apiDataService = RetrofitInstance.getInstance().create(APIClass.class);
        Call<ChatAccessTokenResponseModel> call = apiDataService.chatAccessToken(token, chatAccessToken);
        call.enqueue(new Callback<ChatAccessTokenResponseModel>() {
            @Override
            public void onResponse(Call<ChatAccessTokenResponseModel> call, Response<ChatAccessTokenResponseModel> response) {
                if (response.body() != null) {
                    mutableLiveData.postValue(response.body());
                }else{
                    mutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<ChatAccessTokenResponseModel> call, Throwable t) {
                mutableLiveData.postValue(null);
            }
        });
    }




    public LiveData<ChatAccessTokenResponseModel> ChatAccessTokenResponseLiveData() {
        return mutableLiveData;
    }


}
