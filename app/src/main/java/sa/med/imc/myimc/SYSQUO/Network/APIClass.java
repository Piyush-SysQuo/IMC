package sa.med.imc.myimc.SYSQUO.Network;



import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import sa.med.imc.myimc.SYSQUO.Chat.chat.accesstoken.ChatAccessTokenRequetModel;
import sa.med.imc.myimc.SYSQUO.Chat.chat.accesstoken.ChatAccessTokenResponseModel;
import sa.med.imc.myimc.SYSQUO.EmergencyCall.Model.EmergencyCallRequestModel;
import sa.med.imc.myimc.SYSQUO.EmergencyCall.Model.EmergencyCallResponseModel;
import sa.med.imc.myimc.SYSQUO.EmergencyCall.Model.LeaveEmergencyCallRequestModel;
import sa.med.imc.myimc.SYSQUO.EmergencyCall.Model.LeaveEmergencyCallResponseModel;
import sa.med.imc.myimc.SYSQUO.Selection.Model.CreateRoomResponseModel;
import sa.med.imc.myimc.SYSQUO.Selection.Model.CreateRoomRequestModel;
import sa.med.imc.myimc.SYSQUO.Video.ExitRoom.Model.DisconnectRoomRequestModel;
import sa.med.imc.myimc.SYSQUO.Video.ExitRoom.Model.DisconnectRoomResponseModel;
import sa.med.imc.myimc.SYSQUO.Video.ExitRoom.Model.ExitRoomRequestModel;
import sa.med.imc.myimc.SYSQUO.Video.ExitRoom.Model.ExitRoomResponseModel;
import sa.med.imc.myimc.SYSQUO.Video.JoningRequest.ConferenceJoiningRequestMode;
import sa.med.imc.myimc.SYSQUO.Video.JoningRequest.ConferenceJoiningResponseMode;
import sa.med.imc.myimc.SYSQUO.Video.VideoToken.ServerTokenResponseModel;

public interface APIClass {

//    @FormUrlEncoded
//    @POST("HostApi/createRoom")
    @POST("api/twilio/create-room")
    Call<CreateRoomResponseModel> createRoom(@Header("Authorization") String token, @Body CreateRoomRequestModel createRoomRequestModel);

//    @FormUrlEncoded
//    @POST("HostApi/createRoom")
    @POST("api/twilio/conference-joining-request")
    Call<ConferenceJoiningResponseMode> conferenceJoiningReq(@Header("Authorization") String token, @Body ConferenceJoiningRequestMode conferenceJoiningRequestMode);

//    @FormUrlEncoded
    @POST("api/twilio/call-end")
    Call<ExitRoomResponseModel> exitRoomEmergency(@Header("Authorization") String token, @Body ExitRoomRequestModel exitRoomRequestModel);

//    @FormUrlEncoded
    @POST("api/twilio/chat/get-access-token")
    Call<ChatAccessTokenResponseModel> chatAccessToken(@Header("Authorization") String token, @Body ChatAccessTokenRequetModel chatAccessTokenRequetModel);

    @POST("api/twilio/disconnected-event-timer-start")
    Call<DisconnectRoomResponseModel> disconnectRoom(@Header("Authorization") String token, @Body DisconnectRoomRequestModel disconnectRoomRequestModel);

    @POST("api/twilio/join-emergency-room")
    Call<EmergencyCallResponseModel> emergencyCall(@Header("Authorization") String token, @Body EmergencyCallRequestModel  emergencyCallRequestModel);

    @POST("api/twilio/leave-emergency-room")
    Call<LeaveEmergencyCallResponseModel> leaveEmergencyCall(@Header("Authorization") String token, @Body LeaveEmergencyCallRequestModel leaveEmergencyCallRequestModel);

    @FormUrlEncoded
    @POST("HostApi/getTwilioToken")
    Call<ServerTokenResponseModel> serverToken(@Field("identity") String identity, @Field("roomName") String roomName);

    @FormUrlEncoded
    @POST("HostApi/deleteFromWaitingList")
    Call<CreateRoomResponseModel> exitRoom(@Field("identity") String identity);
}
