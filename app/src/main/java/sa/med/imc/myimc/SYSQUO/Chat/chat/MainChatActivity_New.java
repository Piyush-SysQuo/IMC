package sa.med.imc.myimc.SYSQUO.Chat.chat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.hbisoft.pickit.PickiT;
import com.hbisoft.pickit.PickiTCallbacks;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.mindorks.editdrawabletext.DrawablePosition;
import com.mindorks.editdrawabletext.EditDrawableText;
import com.mindorks.editdrawabletext.onDrawableClickListener;
import com.shockwave.pdfium.PdfDocument;
import com.twilio.chat.CallbackListener;
import com.twilio.chat.Channel;
import com.twilio.chat.ChannelListener;
import com.twilio.chat.Channels;
import com.twilio.chat.ChatClient;
import com.twilio.chat.ChatClientListener;
import com.twilio.chat.ErrorInfo;
import com.twilio.chat.Member;
import com.twilio.chat.Message;
import com.twilio.chat.Messages;
import com.twilio.chat.ProgressListener;
import com.twilio.chat.StatusListener;
import com.twilio.chat.User;
import com.vincent.filepicker.Constant;
import com.vincent.filepicker.activity.ImagePickActivity;
import com.vincent.filepicker.activity.NormalFilePickActivity;
import com.vincent.filepicker.filter.entity.NormalFile;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import sa.med.imc.myimc.BuildConfig;
import sa.med.imc.myimc.MainActivity;
import sa.med.imc.myimc.Network.Constants;
import sa.med.imc.myimc.Network.ImcApplication;
import sa.med.imc.myimc.Network.SharedPreferencesUtils;
import sa.med.imc.myimc.R;
import sa.med.imc.myimc.SYSQUO.Chat.application.AlertDialogHandler;
import sa.med.imc.myimc.SYSQUO.Chat.chat.ImageLoader.PdfFullPopupWindow;
import sa.med.imc.myimc.SYSQUO.Chat.chat.ImageLoader.PhotoFullPopupWindow;
import sa.med.imc.myimc.SYSQUO.Chat.chat.channels.ChannelAdapter;
import sa.med.imc.myimc.SYSQUO.Chat.chat.channels.ChannelManager;
import sa.med.imc.myimc.SYSQUO.Chat.chat.channels.LoadChannelListener;
import sa.med.imc.myimc.SYSQUO.Chat.chat.listeners.TaskCompletionListener;
import sa.med.imc.myimc.SYSQUO.Chat.chat.messages.JoinedStatusMessage;
import sa.med.imc.myimc.SYSQUO.Chat.chat.messages.LeftStatusMessage;
import sa.med.imc.myimc.SYSQUO.Chat.chat.messages.MessageAdapter;
import sa.med.imc.myimc.SYSQUO.Chat.chat.messages.StatusMessage;
import sa.med.imc.myimc.SYSQUO.Selection.Model.CreateRoomRequestModel;
import sa.med.imc.myimc.SYSQUO.Selection.ViewModel.CreateRoomViewModel;
import sa.med.imc.myimc.SYSQUO.Video.VideoActivity;
import sa.med.imc.myimc.SYSQUO.util.FileUtilsSysQuo;
import sa.med.imc.myimc.SYSQUO.util.Progress;
import sa.med.imc.myimc.SYSQUO.util.RealPathUtil;
import sa.med.imc.myimc.Utils.Common;
import sa.med.imc.myimc.Utils.FileUtil;
import timber.log.Timber;

import static com.vincent.filepicker.activity.BaseActivity.IS_NEED_FOLDER_LIST;
import static com.vincent.filepicker.activity.ImagePickActivity.IS_NEED_CAMERA;


public class MainChatActivity_New extends AppCompatActivity implements ChatClientListener, ChannelListener, AttachmentSelectionInterface, PickiTCallbacks, OnPageChangeListener, OnLoadCompleteListener, OnPageErrorListener {
    private Context context;
    private Activity mainActivity;
    private TextView TextView_ChatUserName;
//    private ListView channelsListView;
    private ImageView ImageView_ChateBackpressed, ImageView_ChateVideoCall;
    private ProgressDialog progressDialog;
    private ChannelManager channelManager;
    private ChannelAdapter channelAdapter;
    private ChatClientManager chatClientManager;
    public Channel generalChannel;
    private List<Channel> channels;
    private String defaultChannelName;
    private String defaultChannelUniqueName;
    private Channels channelsObject;
    String mrnNumber, physician;
    Progress progress;
    Channel selectedChannel;
    String drName = null, mimeType = null, mediaType1 = null, pdfFileName = null;

//    Fragment
    Button sendButton;
    ListView messagesListView;
    EditDrawableText messageTextEdit;
    LinearLayout mRevealView;
    MessageAdapter messageAdapter;
    Channel currentChannel;
    Messages messagesObject;
    private static final int REQUEST_CODE = 6384; // onActivityResult request
    public static MainChatActivity_New mainChatActivity_new;
    long downloadID;
    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;
    int iD = 1;
    Uri downloadedFileURI;
    ActivityResultLauncher<Intent> sActivityResultLauncher;
    PickiT pickiT;
    private static final int PERMISSION_REQ_ID_RECORD_AUDIO = 22;
    private static final int PERMISSION_REQ_ID_WRITE_EXTERNAL_STORAGE = PERMISSION_REQ_ID_RECORD_AUDIO + 1;

    Integer pageNumber = 0;
    private static final String TAG = MainChatActivity_New.class.getSimpleName();
    PDFView pdfView;
    View viewPdf;
    boolean isViewPdf = false;
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

        unregisterReceiver(onDownloadComplete);
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

            mrnNumber = SharedPreferencesUtils.getInstance(this).getValue(Constants.KEY_MRN, null);
            physician = SharedPreferencesUtils.getInstance(this).getValue(Constants.KEY_VIDEO_PHYSICIAN, null);
            defaultChannelName = mrnNumber + "_" + physician;
            defaultChannelUniqueName = mrnNumber + "_" + physician;

            /*FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            chatFragment = new MainChatFragment();
            fragmentTransaction.add(R.id.fragment_container, chatFragment);
            fragmentTransaction.commit();*/
            context = this;
            mainActivity = this;

            TextView_ChatUserName = (TextView) findViewById(R.id.TextView_ChatUserName);
            ImageView_ChateVideoCall = (ImageView) findViewById(R.id.ImageView_ChateVideoCall);
            ImageView_ChateBackpressed = (ImageView) findViewById(R.id.ImageView_ChateBackpressed);

            setUsernameTextView();

            setUpListeners();
            checkTwilioClient();

            fragmentWidgetInti();
            registerReceiver(onDownloadComplete,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
            pickiT = new PickiT(this, this, this);
            activityResultLauncher();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    @Override
    protected void onResume() {
        super.onResume();
        try {
            mainChatActivity_new = this;

            mrnNumber = SharedPreferencesUtils.getInstance(this).getValue(Constants.KEY_MRN, null);
            physician = SharedPreferencesUtils.getInstance(this).getValue(Constants.KEY_VIDEO_PHYSICIAN, null);
            defaultChannelName = mrnNumber + "_" + physician;
            defaultChannelUniqueName = mrnNumber + "_" + physician;

            /*setUsernameTextView();

            setUpListeners();
            checkTwilioClient();*/
            setChannel(0);

            fragmentWidgetInti();
            pickiT = new PickiT(this, this, this);
            activityResultLauncher();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public void fragmentWidgetInti(){
        sendButton = (Button) findViewById(R.id.buttonSend);
        messagesListView = (ListView) findViewById(R.id.listViewMessages);
        messageTextEdit = (EditDrawableText) findViewById(R.id.editTextMessage);

        messageAdapter = new MessageAdapter(mainActivity, this);
        messagesListView.setAdapter(messageAdapter);
//        messagesListView.smoothScrollToPosition(21);
        setUpListenersFrag();
        setMessageInputEnabled(false);
        messageTextEdit.setDrawableClickListener(new onDrawableClickListener() {
            @Override
            public void onClick(@NotNull DrawablePosition drawablePosition) {
                readPermissionChecker();
            }
        });
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
                clickToStartVideoCall();
            }
        });

        ImageView_ChateBackpressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferencesUtils.getInstance(MainChatActivity_New.this).setValue(Constants.KEY_NAV_CLASS, true);
                Intent in = new Intent(MainChatActivity_New.this, MainActivity.class);
//                in.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
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
            populateChannelsSecond();
        }
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    private void initializeClient() {
        chatClientManager.connectClient(new TaskCompletionListener<Void, String>() {
            @Override
            public void onSuccess(Void aVoid) {
                populateChannelsSecond();
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
        final Channel currentChannel = getCurrentChannel();
        selectedChannel = null;
        if(channels.size() >0) {
            for (int i = 0; i < channels.size(); i++) {
                if (channels.get(i).getFriendlyName().equals(defaultChannelUniqueName)) {
                    selectedChannel = channels.get(i);
                }
            }
        }
        if (selectedChannel != null) {
            TextView_ChatUserName.setText(drName);
            joinChannel(selectedChannel);
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
                setCurrentChannel(selectedChannel, new StatusListener() {
                    @Override
                    public void onSuccess() {
                        stopActivityIndicator();
//                        Common.makeToast(MainChatActivity_New.this, "Joined Success");
                    }

                    @Override
                    public void onError(ErrorInfo errorInfo) {
                        Common.makeToast(MainChatActivity_New.this, errorInfo.getMessage());
                    }
                });
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
    public Channel getCurrentChannel() {
//        Common.makeToast(MainChatActivity_New.this, currentChannel.getFriendlyName()+" : getCurrentChannel");
        return currentChannel;
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public void setCurrentChannel(Channel currentChannel1, final StatusListener handler) {
        showActivityIndicator("");
        if (currentChannel1 == null) {
            this.currentChannel = null;
//      Common.makeToast(getActivity(), "Retrun");
            return;
        }
        if (!currentChannel1.equals(currentChannel)) {
            setMessageInputEnabled(false);
            currentChannel = currentChannel1;
            currentChannel.addListener(this);
            messagesObject = currentChannel1.getMessages();
            if (this.currentChannel.getStatus() == Channel.ChannelStatus.JOINED) {
//        Common.makeToast(getActivity(), "Joined "+currentChannel.getFriendlyName());
                final Handler handler1 = new Handler(Looper.getMainLooper());
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadMessages(handler);
                        stopActivityIndicator();
                    }
                }, 2000);


            }
            else
            {
                stopActivityIndicator();
                this.currentChannel.join(new StatusListener() {
                    @Override
                    public void onSuccess() {
//            Common.makeToast(getActivity(), "Join Success "+currentChannel.getFriendlyName());
                        loadMessages(handler);
                    }

                    @Override
                    public void onError(ErrorInfo errorInfo) {
//            Common.makeToast(getActivity(), "Joining "+errorInfo.getMessage());
                    }
                });
            }
        }

        else if (currentChannel1.equals(currentChannel)) {
            setMessageInputEnabled(false);
            currentChannel = currentChannel1;
            currentChannel.addListener(this);
            messagesObject = currentChannel1.getMessages();
            if (this.currentChannel.getStatus() == Channel.ChannelStatus.JOINED) {
//        Common.makeToast(getActivity(), "Joined "+currentChannel.getFriendlyName());
                final Handler handler1 = new Handler(Looper.getMainLooper());
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadMessages(handler);
                        stopActivityIndicator();
                    }
                }, 2000);


            }
            else
            {
                stopActivityIndicator();
                this.currentChannel.join(new StatusListener() {
                    @Override
                    public void onSuccess() {
//            Common.makeToast(getActivity(), "Join Success "+currentChannel.getFriendlyName());
                        loadMessages(handler);
                    }

                    @Override
                    public void onError(ErrorInfo errorInfo) {
//            Common.makeToast(getActivity(), "Joining "+errorInfo.getMessage());
                    }
                });
            }
        }
        else{
            Common.makeToast(MainChatActivity_New.this, "Channel not connected");
        }
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    private void loadMessages(final StatusListener handler) {
        messagesObject = currentChannel.getMessages();

        if (messagesObject != null) {
            messagesObject.getLastMessages(100, new CallbackListener<List<Message>>() {
                @Override
                public void onSuccess(List<Message> messageList) {
                    messageAdapter.setMessages(messageList);
                    setMessageInputEnabled(true);
                    messageTextEdit.requestFocus();
                    handler.onSuccess();
                }
            });
        }
        else {
//      Common.makeToast(getActivity(), "Message Object is null ");
        }
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    private void setUpListenersFrag() {
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
        messageTextEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return false;
            }
        });
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    private void sendMessage() {
        String messageText = getTextInput();
        if (messageText.length() == 0) {
            return;
        }
        Message.Options messageOptions = Message.options().withBody(messageText);
        this.messagesObject.sendMessage(messageOptions, null);
        clearTextInput();
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    private void setMessageInputEnabled(final boolean enabled) {
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
               MainChatActivity_New.this.sendButton.setEnabled(enabled);
                MainChatActivity_New.this.messageTextEdit.setEnabled(enabled);
            }
        });
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    private String getTextInput() {
        return messageTextEdit.getText().toString();
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    private void clearTextInput() {
        messageTextEdit.setText("");
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    @Override
    public void onMessageAdded(Message message) {
        messageAdapter.addMessage(message);
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    @Override
    public void onMemberAdded(Member member) {
        StatusMessage statusMessage = new JoinedStatusMessage(member.getIdentity());
        this.messageAdapter.addStatusMessage(statusMessage);
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    @Override
    public void onMemberDeleted(Member member) {
        StatusMessage statusMessage = new LeftStatusMessage(member.getIdentity());
        this.messageAdapter.addStatusMessage(statusMessage);
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    @Override
    public void onMessageUpdated(Message message, Message.UpdateReason updateReason) {
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    @Override
    public void onMessageDeleted(Message message) {
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    @Override
    public void onMemberUpdated(Member member, Member.UpdateReason updateReason) {
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    @Override
    public void onTypingStarted(Channel channel, Member member) {
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    @Override
    public void onTypingEnded(Channel channel, Member member) {
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    @Override
    public void onSynchronizationChanged(Channel channel) {
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public void readPermissionChecker(){
        if (checkSelfPermission()) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(messageTextEdit.getWindowToken(), 0);

            MediaSelectionFragment bottomSheetDialog = new MediaSelectionFragment(MainChatActivity_New.this, MainChatActivity_New.this);
            bottomSheetDialog.show(getSupportFragmentManager(), "Bottom Sheet Dialog Fragment");
        }
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public void writePermissionChecker(){
        Dexter.withContext(context).withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(messageTextEdit.getWindowToken(), 0);

                MediaSelectionFragment bottomSheetDialog = new MediaSelectionFragment(MainChatActivity_New.this, MainChatActivity_New.this);
                bottomSheetDialog.show(getSupportFragmentManager(), "Bottom Sheet Dialog Fragment");
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
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    @Override
    public void getAttachMediaType(String mediaType) {
        mediaType1 = mediaType;
        if(mediaType.equals(getResources().getString(R.string.image_text)))
        {
            mimeType = "image/*";
            Intent intent;
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            } else {
                intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            }
            //  In this example we will set the type to video
            intent.setType(mimeType);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.putExtra("return-data", true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
            }
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            sActivityResultLauncher.launch(intent);
        }
        if(mediaType.equals(getResources().getString(R.string.pdf)))
        {
            mimeType = "application/pdf";
            Intent intent = null;
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    intent = new Intent(Intent.ACTION_PICK, MediaStore.Downloads.EXTERNAL_CONTENT_URI);
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    intent = new Intent(Intent.ACTION_PICK, MediaStore.Downloads.INTERNAL_CONTENT_URI);
                }
            }
            //  In this example we will set the type to video
            intent.setType(mimeType);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.putExtra("return-data", true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
            }
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            sActivityResultLauncher.launch(intent);
        }
        else if(mediaType.equals(getResources().getString(R.string.text)))
        {
            mimeType = "text/plain";
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType(mimeType);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public void activityResultLauncher(){
        sActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            Intent data = result.getData();
                            //  Get path from PickiT (The path will be returned in PickiTonCompleteListener)
                            //
                            //  If the selected file is from Dropbox/Google Drive or OnDrive:
                            //  Then it will be "copied" to your app directory (see path example below) and when done the path will be returned in PickiTonCompleteListener
                            //  /storage/emulated/0/Android/data/your.package.name/files/Temp/tempDriveFile.mp4
                            //
                            //  else the path will directly be returned in PickiTonCompleteListener

                            ClipData clipData = Objects.requireNonNull(data).getClipData();
                            if (clipData != null) {
                                int numberOfFilesSelected = clipData.getItemCount();
                                if (numberOfFilesSelected > 1) {
                                    pickiT.getMultiplePaths(clipData);
                                    StringBuilder allPaths = new StringBuilder("Multiple Files Selected:" + "\n");
                                    for(int i = 0; i < clipData.getItemCount(); i++) {
                                        allPaths.append("\n\n").append(clipData.getItemAt(i).getUri());
                                    }
                                }
                                else
                                {
                                    pickiT.getPath(clipData.getItemAt(0).getUri(), Build.VERSION.SDK_INT);
                                }
                            }
                            else {
                                pickiT.getPath(data.getData(), Build.VERSION.SDK_INT);
                            }

                        }
                    }
                }
        );
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public void readFileData(String path) throws FileNotFoundException
    {
        File file = new File(path);
        if (file.exists())
        {
            try {
                // Messages messagesObject;
                messagesObject.sendMessage(
                        Message.options()
//                                .withMedia(new FileInputStream(path), "text/*|application/pdf|image/*")
                                .withMedia(new FileInputStream(path), mimeType)
                                .withMediaFileName(file.getName())
                                .withMediaProgressListener(new ProgressListener() {
                                    @Override
                                    public void onStarted() {
                                        Toast.makeText(MainChatActivity_New.this, "upload Start", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onProgress(long bytes) {
//                                        Toast.makeText(MainChatActivity_New.this, "In Progress", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCompleted(String mediaSid) {
//                                        Toast.makeText(MainChatActivity_New.this, "Completed", Toast.LENGTH_SHORT).show();
                                    }
                                }),
                        new CallbackListener<Message>() {
                            @Override
                            public void onSuccess(Message msg) {
                                pickiT.deleteTemporaryFile(MainChatActivity_New.this);
                                Toast.makeText(MainChatActivity_New.this, "Successfully Uploaded", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(ErrorInfo error) {
                                Timber.e("Error sending MEDIA message");
//                                Toast.makeText(MainChatActivity_New.this, "Error : "+error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }catch (Exception e){
                e.printStackTrace();
//                Toast.makeText(MainChatActivity_New.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
//            Common.makeToast(this, getResources().getString(R.string.fileNotExists));
        }
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
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
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
//    Enlarge Image
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    @Override
    public void enlargeImage(String fileName, String fileUrl, View view){
        new PhotoFullPopupWindow(context, R.layout.sysquo_popup_photo_full, view, fileUrl, null);
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
//    Download the PDF
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    @Override
    public void downloadPdf(String fileName, String fileUrl, String mieType, View v){
        try {
            boolean isExist = isFileExists(fileName);
            if(isExist){
                boolean isDeleted = deleteFile(fileName);
            }
            downloadID = 0;
            pdfFileName = fileName;
            viewPdf = v;
//            notificationInitilization(fileName);
            mimeType = mieType;
            DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            Uri uri = Uri.parse(fileUrl);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setMimeType(mimeType);
            request.setTitle(fileName);
            File dir=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

            dir.mkdirs();

            Uri downloadLocation    =   Uri.fromFile(new File(dir, fileName));
            request.setDestinationUri(downloadLocation);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            downloadID  = manager.enqueue(request);
            downloadedFileURI = null;
            downloadedFileURI = manager.getUriForDownloadedFile(downloadID);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public void notificationInitilization(String fileName){
        File dir=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType(mimeType);
        testIntent.setAction(Intent.ACTION_VIEW);
        dir.mkdirs();

        Uri uri = FileProvider.getUriForFile(ImcApplication.getInstance(), BuildConfig.APPLICATION_ID +".provider",dir);
        testIntent.setDataAndType(uri, mimeType);    // or use */*
//        startActivity(in);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                testIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.sysquo_noti_download)
                .setContentTitle(fileName)
                .setContentText("Downloading File")
                .setAutoCancel(true)
                .setContentIntent(contentIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId = "Your_channel_id";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
            notificationBuilder.setChannelId(channelId);
        }
        notificationManager.notify(iD, notificationBuilder.build());
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Fetching the download id received with the broadcast
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            //Checking if the received broadcast is for our enqueued download by matching download id
            if (downloadID == id) {
                Toast.makeText(MainChatActivity_New.this, "Download Completed", Toast.LENGTH_SHORT).show();
                if(mimeType.equals("application/pdf")) {
//                    openPdfFile();
                    new PdfFullPopupWindow(context, R.layout.sysquo_dialog_pdf_view, viewPdf, pdfFileName, MainChatActivity_New.this);
                }
//                notificationManager.cancel(iD);
//                notificationBuilder.setProgress(0,0,false);
//                notificationBuilder.setContentText(pdfFileName.replace(".pdf", "")+" Downloaded");
//                notificationManager.notify(iD, notificationBuilder.build());
            }
        }
    };
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public void openPdfFile(){
        try {
            /*Intent intent = new Intent(Intent.ACTION_VIEW);
            File file = new File(Environment.getExternalStorageDirectory().toString() + "/Download/" +  pdfFileName);
            String absoluteFilePath = file.getAbsolutePath();
            Uri uri = Uri.parse("content://" + "sa.med.imc.myimc" + "/" + absoluteFilePath);
            intent.setDataAndType(uri, mimeType);
            Intent intentChooser = Intent.createChooser(intent, "Choose Pdf Application");
            startActivity(intentChooser);
            finish();*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        printBookmarksTree(pdfView.getTableOfContents(), "-");
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {

            Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    @Override
    public void onPageError(int page, Throwable t) {
        Log.e(TAG, "Cannot load page " + page);
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    private boolean isFileExists(String filename){
        File folder1 = new File(Environment.getExternalStorageDirectory().toString() + "/Download/" +  filename);
        return folder1.exists();
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public boolean deleteFile(String filename){
        File folder1 = new File(Environment.getExternalStorageDirectory().toString() + "/Download/"  + filename);
        return folder1.delete();
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//

//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    @Override
    public void PickiTonUriReturned() {

    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    @Override
    public void PickiTonStartListener() {

    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    @Override
    public void PickiTonProgressUpdate(int progress) {

    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    @Override
    public void PickiTonCompleteListener(String path, boolean wasDriveFile, boolean wasUnknownProvider, boolean wasSuccessful, String reason) {
        /*if (wasDriveFile) {
            Common.makeToast(this, "Drive file was selected");
        } else if (wasUnknownProvider) {
            Common.makeToast(this, "File was selected from unknown provider");
        } else {
            Common.makeToast(this, "Local file was selected");
        }*/

        //  Chick if it was successful
        if (wasSuccessful) {
            //  Set returned path to TextView
            try {
                readFileData(path);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
//            Common.makeToast(this, "Error, please see the log..");
        }
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    @Override
    public void PickiTonMultipleCompleteListener(ArrayList<String> paths, boolean wasSuccessful, String Reason) {
        StringBuilder allPaths = new StringBuilder();
        for (int i = 0; i < paths.size(); i++) {
            allPaths.append("\n").append(paths.get(i)).append("\n");
        }
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
//    Permission
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    //  Check if permissions was granted
    private boolean checkSelfPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE}, MainChatActivity_New.PERMISSION_REQ_ID_WRITE_EXTERNAL_STORAGE);
            return false;
        }
        return true;
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    //  Handle permissions
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQ_ID_WRITE_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //  Permissions was granted, open the gallery
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(messageTextEdit.getWindowToken(), 0);

                MediaSelectionFragment bottomSheetDialog = new MediaSelectionFragment(MainChatActivity_New.this, MainChatActivity_New.this);
                bottomSheetDialog.show(getSupportFragmentManager(), "Bottom Sheet Dialog Fragment");
            }
            //  Permissions was not granted
            else {
                Common.makeToast(this, "No permission for " + Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
        }
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
}
