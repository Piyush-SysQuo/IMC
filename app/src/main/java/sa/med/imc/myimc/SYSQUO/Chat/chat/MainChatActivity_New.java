package sa.med.imc.myimc.SYSQUO.Chat.chat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.twilio.chat.Channel;
import com.twilio.chat.Channels;
import com.twilio.chat.ChatClient;
import com.twilio.chat.ChatClientListener;
import com.twilio.chat.ErrorInfo;
import com.twilio.chat.StatusListener;
import com.twilio.chat.User;

import java.util.List;
import java.util.Objects;

import sa.med.imc.myimc.Appointmnet.view.AppointmentActivity;
import sa.med.imc.myimc.MainActivity;
import sa.med.imc.myimc.Network.Constants;
import sa.med.imc.myimc.Network.ImcApplication;
import sa.med.imc.myimc.Network.SharedPreferencesUtils;
import sa.med.imc.myimc.R;
import sa.med.imc.myimc.SYSQUO.Chat.application.AlertDialogHandler;
import sa.med.imc.myimc.SYSQUO.Chat.chat.channels.ChannelAdapter;
import sa.med.imc.myimc.SYSQUO.Chat.chat.channels.ChannelManager;
import sa.med.imc.myimc.SYSQUO.Chat.chat.channels.LoadChannelListener;
import sa.med.imc.myimc.SYSQUO.Chat.chat.listeners.TaskCompletionListener;
import sa.med.imc.myimc.SYSQUO.Selection.Model.CreateRoomRequestModel;
import sa.med.imc.myimc.SYSQUO.Selection.SelectionActivity;
import sa.med.imc.myimc.SYSQUO.Selection.ViewModel.CreateRoomViewModel;
import sa.med.imc.myimc.SYSQUO.Video.VideoActivity;
import sa.med.imc.myimc.SYSQUO.util.Progress;
import sa.med.imc.myimc.SplashActivity;
import sa.med.imc.myimc.Utils.Common;


public class MainChatActivity_New extends AppCompatActivity implements ChatClientListener {
    private Context context;
    private Activity mainActivity;
    private TextView TextView_ChatUserName;
//    private ListView channelsListView;
    private ImageView ImageView_ChateBackpressed, ImageView_ChateVideoCall;
    private ProgressDialog progressDialog;
    private ChannelManager channelManager;
    private ChannelAdapter channelAdapter;
    private ChatClientManager chatClientManager;
    private MainChatFragment chatFragment;
    public Channel generalChannel;
    private List<Channel> channels;
    private String defaultChannelName;
    private String defaultChannelUniqueName;
    private Channels channelsObject;
    String mrnNumber, physician;
    Progress progress;
    Channel selectedChannel;
    String drName = null;
    public static MainChatActivity_New mainChatActivity_new;
    @Override
    protected void onDestroy() {
        super.onDestroy();
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                chatClientManager.shutdown();
                ImcApplication.getInstance().getChatClientManager().setChatClient(null);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sysquo_activity_main_chat_new);

        try {

            channelManager = ChannelManager.getInstance();
            mainChatActivity_new = this;

            progress = new Progress(this);
            (Objects.requireNonNull(progress.getWindow())).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progress.setCanceledOnTouchOutside(false);
            progress.setCancelable(false);

            ChatClientManager clientManager = ImcApplication.getInstance().getChatClientManager();
//        defaultChannelName  = "test1";
//        defaultChannelUniqueName = "test1";

            mrnNumber = SharedPreferencesUtils.getInstance(this).getValue(Constants.KEY_MRN, null);
            physician = SharedPreferencesUtils.getInstance(this).getValue(Constants.KEY_VIDEO_PHYSICIAN, null);
            defaultChannelName = mrnNumber + "_" + physician;
            defaultChannelUniqueName = mrnNumber + "_" + physician;

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            chatFragment = new MainChatFragment();
            fragmentTransaction.add(R.id.fragment_container, chatFragment);
            fragmentTransaction.commit();

            context = this;
            mainActivity = this;

            TextView_ChatUserName = (TextView) findViewById(R.id.TextView_ChatUserName);
//        channelsListView            = (ListView) findViewById(R.id.listViewChannels);
            ImageView_ChateVideoCall = (ImageView) findViewById(R.id.ImageView_ChateVideoCall);
            ImageView_ChateBackpressed = (ImageView) findViewById(R.id.ImageView_ChateBackpressed);
            setUsernameTextView();

            setUpListeners();
            checkTwilioClient();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    private void setUsernameTextView() {
        drName = SharedPreferencesUtils.getInstance(this).getValue(Constants.KEY_VIDEO_PHYSICIAN_NAME, "");
        TextView_ChatUserName.setText(drName);
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    private void setUpListeners() {
        ImageView_ChateVideoCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent in = new Intent(MainChatActivity_New.this, VideoActivity.class);
//                startActivity(in);
//                startActivity(new Intent(MainChatActivity_New.this, VideoActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                clickToStartVideoCall();
            }
        });

        ImageView_ChateBackpressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferencesUtils.getInstance(MainChatActivity_New.this).setValue(Constants.KEY_NAV_CLASS, true);
                Intent in = new Intent(MainChatActivity_New.this, MainActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(in);
//                finish();
            }
        });
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public void clickToStartVideoCall() {
        CreateRoomViewModel viewModel = ViewModelProviders.of(MainChatActivity_New.this).get(CreateRoomViewModel.class);
        viewModel.init();
    //        viewModel.ServerToken("Piyush");
        CreateRoomRequestModel createRoomRequestModel = new CreateRoomRequestModel();
        createRoomRequestModel.setPageNumber(0);
        createRoomRequestModel.setPageSize(0);
        String mrnNumber = SharedPreferencesUtils.getInstance(this).getValue(Constants.KEY_MRN, null);
        String physician = SharedPreferencesUtils.getInstance(this).getValue(Constants.KEY_VIDEO_PHYSICIAN, null);
        createRoomRequestModel.setRoomName(mrnNumber + "_" + physician);
        createRoomRequestModel.setUserEmail("xyz@gmail.com");
        String token = SharedPreferencesUtils.getInstance(this).getValue(Constants.KEY_ACCESS_TOKEN, null);
        viewModel.CreateRoom(token, createRoomRequestModel);
        viewModel.getVolumesResponseLiveData().observe(MainChatActivity_New.this, response -> {

            if(response!=null){
                if(response.getStatus())
                {
                    Dexter.withContext(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                            Dexter.withContext(MainChatActivity_New.this).withPermission(Manifest.permission.RECORD_AUDIO).withListener(new PermissionListener() {
                                @Override
                                public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                    Intent in = new Intent(MainChatActivity_New.this, VideoActivity.class);
                                    startActivity(in);
    //                                    finish();
    //                                    startActivity(new Intent(SelectionActivity.this, VideoActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                }

                                @Override
                                public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                                }

                                @Override
                                public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                    permissionToken.continuePermissionRequest();
                                }
                            }).check();
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                            permissionToken.continuePermissionRequest();
                        }
                    }).check();
                }
            }
            else
            {
                Toast.makeText(this, "Some thing went wrong ", Toast.LENGTH_SHORT).show();
            }
        });


    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public void checkTwilioClient() {
    //    PIYUSH
        showActivityIndicator(getStringResource(R.string.loading_channels_message));
        chatClientManager = ImcApplication.getInstance().getChatClientManager();
        if (chatClientManager.getChatClient() == null) {
            initializeClient();
        } else {
            populateChannels();
        }
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    private void initializeClient() {
        chatClientManager.connectClient(new TaskCompletionListener<Void, String>() {
            @Override
            public void onSuccess(Void aVoid) {
                populateChannels();
            }

            @Override
            public void onError(String errorMessage) {
                stopActivityIndicator();
                showAlertWithMessage("Client connection error: " + errorMessage);
            }
        });
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public void populateChannels()
    {
        channelManager.setChannelListener(this);
        channelManager.populateChannels(new LoadChannelListener() {
            @Override
            public void onChannelsFinishedLoading(List<Channel> channels) {
                MainChatActivity_New.this.channelManager
                        .joinOrCreateGeneralChannelWithCompletion(new StatusListener() {
                            @Override
                            public void onSuccess() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        populateChannelsSecond();
                                    }
                                });
                            }

                            @Override
                            public void onError(ErrorInfo errorInfo) {
                                showAlertWithMessage(getStringResource(R.string.generic_error) + " - " + errorInfo.getMessage());
                            }
                        });
            }
        });
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public void populateChannelsSecond()
    {
        channelManager.setChannelListener(this);
        channelManager.populateChannels(new LoadChannelListener() {
            @Override
            public void onChannelsFinishedLoading(List<Channel> channels) {
                MainChatActivity_New.this.channelManager
                        .joinOrCreateGeneralChannelWithCompletion(new StatusListener() {
                            @Override
                            public void onSuccess() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        stopActivityIndicator();
                                        setChannel(0);
                                    }
                                });
                            }

                            @Override
                            public void onError(ErrorInfo errorInfo) {
                                showAlertWithMessage(getStringResource(R.string.generic_error) + " - " + errorInfo.getMessage());
                            }
                        });
            }
        });
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    private void setChannel(final int position) {
        List<Channel> channels = channelManager.getChannels();
        if (channels == null) {
            return;
        }
        final Channel currentChannel = chatFragment.getCurrentChannel();
        selectedChannel = null;
        if(channels.size() >0) {
            for (int i = 0; i < channels.size(); i++) {
                if (channels.get(i).getFriendlyName().equals(defaultChannelUniqueName)) {
                    selectedChannel = channels.get(i);
                }
            }
        }
//        final Channel selectedChannel = channels.get(position);
//        if (currentChannel != null && currentChannel.getSid().contentEquals(selectedChannel.getSid())) {
//            return;
//        }
        if (selectedChannel != null) {
//            showActivityIndicator("Joining " + selectedChannel.getFriendlyName() + " channel");
            TextView_ChatUserName.setText(drName);
//            if (currentChannel != null && currentChannel.getStatus() == Channel.ChannelStatus.JOINED) {
//                this.channelManager.leaveChannelWithHandler(currentChannel, new StatusListener() {
//                    @Override
//                    public void onSuccess() {
//                        joinChannel(selectedChannel);
//                    }
//
//                    @Override
//                    public void onError(ErrorInfo errorInfo) {
//                        stopActivityIndicator();
//                    }
//                });
//                return;
//            }
            joinChannel(selectedChannel);
//            stopActivityIndicator();
        } else {
            stopActivityIndicator();
            showAlertWithMessage(getStringResource(R.string.generic_error));
            Log.e(ImcApplication.TAG,"Selected channel out of range");
        }
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    private void joinChannel(final Channel selectedChannel) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chatFragment.setCurrentChannel(selectedChannel, new StatusListener() {
                    @Override
                    public void onSuccess() {
                        stopActivityIndicator();
                        MainChatActivity_New.this.stopActivityIndicator();
                    }

                    @Override
                    public void onError(ErrorInfo errorInfo) {
//                        Common.makeToast(MainChatActivity_New.this, errorInfo.getMessage());
                    }
                });
    //                setTitle(selectedChannel.getFriendlyName());
    //                drawer.closeDrawer(GravityCompat.START);
            }
        });
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    private String getStringResource(int id) {
        Resources resources = getResources();
        return resources.getString(id);
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    private void showActivityIndicator(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                /*progressDialog = new ProgressDialog(MainChatActivity_New.this.mainActivity);
                progressDialog.setMessage(message);
                progressDialog.show();
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setCancelable(false);*/
                progress.show();
            }
        });
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public void stopActivityIndicator() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                /*if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }*/
                if(progress.isShowing()){
                    progress.dismiss();
                }
            }
        });
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    private void showAlertWithMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialogHandler.displayAlertWithMessage(message, context);
            }
        });
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    @Override
    public void onBackPressed() {
        /*Intent in  = new Intent(this, SelectionActivity.class);
        startActivity(in);
        finish();*/
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    @Override
    public void onChannelJoined(Channel channel) {

    }

    @Override
    public void onChannelInvited(Channel channel) {

    }

    @Override
    public void onChannelAdded(Channel channel) {

    }

    @Override
    public void onChannelUpdated(Channel channel, Channel.UpdateReason updateReason) {

    }

    @Override
    public void onChannelDeleted(Channel channel) {

    }

    @Override
    public void onChannelSynchronizationChange(Channel channel) {

    }

    @Override
    public void onError(ErrorInfo errorInfo) {

    }

    @Override
    public void onUserUpdated(User user, User.UpdateReason updateReason) {

    }

    @Override
    public void onUserSubscribed(User user) {

    }

    @Override
    public void onUserUnsubscribed(User user) {

    }

    @Override
    public void onClientSynchronization(ChatClient.SynchronizationStatus synchronizationStatus) {

    }

    @Override
    public void onNewMessageNotification(String s, String s1, long l) {

    }

    @Override
    public void onAddedToChannelNotification(String s) {

    }

    @Override
    public void onInvitedToChannelNotification(String s) {

    }

    @Override
    public void onRemovedFromChannelNotification(String s) {

    }

    @Override
    public void onNotificationSubscribed() {

    }

    @Override
    public void onNotificationFailed(ErrorInfo errorInfo) {

    }

    @Override
    public void onConnectionStateChange(ChatClient.ConnectionState connectionState) {

    }

    @Override
    public void onTokenExpired() {

    }

    @Override
    public void onTokenAboutToExpire() {

    }
}
