package sa.med.imc.myimc.SYSQUO.Video.JoningRequest;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sa.med.imc.myimc.SYSQUO.Network.APIClass;
import sa.med.imc.myimc.SYSQUO.Network.RetrofitInstance;
import sa.med.imc.myimc.SYSQUO.Video.VideoToken.ServerTokenResponseModel;

public class ConferenceJoiningReqRepository {

    private MutableLiveData<ConferenceJoiningResponseMode> mutableLiveData;

    public ConferenceJoiningReqRepository() {

        mutableLiveData = new MutableLiveData<>();
    }

    public void serverTokenAPI(String token, ConferenceJoiningRequestMode conferenceJoiningRequestMode){
        APIClass apiDataService = RetrofitInstance.getInstance().create(APIClass.class);
        Call<ConferenceJoiningResponseMode> call = apiDataService.conferenceJoiningReq("Bearer "+token, conferenceJoiningRequestMode);
        call.enqueue(new Callback<ConferenceJoiningResponseMode>() {
            @Override
            public void onResponse(Call<ConferenceJoiningResponseMode> call, Response<ConferenceJoiningResponseMode> response) {
                if (response.body() != null) {
                    mutableLiveData.postValue(response.body());
                }else{
                    mutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<ConferenceJoiningResponseMode> call, Throwable t) {
                mutableLiveData.postValue(null);
            }
        });
    }




    public LiveData<ConferenceJoiningResponseMode> ConfRequResponseLiveData() {
        return mutableLiveData;
    }


}
