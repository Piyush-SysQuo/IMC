package sa.med.imc.myimc.SYSQUO.Chat.chat.messages;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.twilio.chat.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import sa.med.imc.myimc.Managebookings.view.ManageBookingsActivity;
import sa.med.imc.myimc.Network.SharedPreferencesUtils;
import sa.med.imc.myimc.R;
import sa.med.imc.myimc.SYSQUO.util.Constants;


public class MessageAdapter extends BaseAdapter {
  private final int TYPE_MESSAGE = 0;
  private final int TYPE_STATUS = 1;

  private List<ChatMessage> messages;
  private LayoutInflater layoutInflater;
  private TreeSet<Integer> statusMessageSet = new TreeSet<>();
  Context mContext;

  public MessageAdapter(Activity activity) {
    layoutInflater = activity.getLayoutInflater();
    this.mContext = activity;
    messages = new ArrayList<>();
  }

  public void setMessages(List<Message> messages) {
    this.messages = convertTwilioMessages(messages);
    this.statusMessageSet.clear();
    notifyDataSetChanged();
  }

  public void addMessage(Message message) {
    messages.add(new UserMessage(message));
    notifyDataSetChanged();
  }

  public void addStatusMessage(StatusMessage message) {
    messages.add(message);
    statusMessageSet.add(messages.size() - 1);
    notifyDataSetChanged();
  }

  public void removeMessage(Message message) {
    messages.remove(messages.indexOf(message));
    notifyDataSetChanged();
  }

  private List<ChatMessage> convertTwilioMessages(List<Message> messages) {
    List<ChatMessage> chatMessages = new ArrayList<>();
    for (Message message : messages) {
      chatMessages.add(new UserMessage(message));
    }
    return chatMessages;
  }

  @Override
  public int getViewTypeCount() {
    return 2;
  }

  @Override
  public int getItemViewType(int position) {
    return statusMessageSet.contains(position) ? TYPE_STATUS : TYPE_MESSAGE;
  }

  @Override
  public int getCount() {
    return messages.size();
  }

  @Override
  public Object getItem(int i) {
    return messages.get(i);
  }

  @Override
  public long getItemId(int i) {
    return i;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup viewGroup) {

    int type = getItemViewType(position);
    int res;

    SharedPreferences sharedPreferences = mContext.getSharedPreferences(Constants.SHARED_PREF_LANGUAGE, 0);
    String language = sharedPreferences.getString(Constants.KEY_LANGUAGE,"");
    if (SharedPreferencesUtils.getInstance(mContext).getValue(sa.med.imc.myimc.Network.Constants.KEY_LANGUAGE, sa.med.imc.myimc.Network.Constants.ENGLISH).equalsIgnoreCase(sa.med.imc.myimc.Network.Constants.ENGLISH)) {
      switch (type) {
        case TYPE_MESSAGE:
          ChatMessage message = messages.get(position);
//          PIYUSH 28-0-2022
          String identity = "DRSHAH";
          if(!message.getAuthor().equals(identity))
          {
            res = R.layout.sysquo_message_send;
            convertView = layoutInflater.inflate(res, viewGroup, false);
            LinearLayout LayoutMain = (LinearLayout) convertView.findViewById(R.id.LayoutMain);
            TextView textViewMessage = (TextView) convertView.findViewById(R.id.textViewMessage);
            TextView textViewAuthor = (TextView) convertView.findViewById(R.id.textViewAuthor);
            TextView textViewDate = (TextView) convertView.findViewById(R.id.textViewDate);
            textViewMessage.setText(message.getMessageBody());
            textViewAuthor.setText(message.getAuthor());
            textViewDate.setText(DateFormatter.getFormattedDateFromISOString(message.getDateCreated()));
          }
          else
          {
            res = R.layout.sysquo_message;
            convertView = layoutInflater.inflate(res, viewGroup, false);
            LinearLayout LayoutMain = (LinearLayout) convertView.findViewById(R.id.LayoutMain);
            TextView textViewMessage = (TextView) convertView.findViewById(R.id.textViewMessage);
            TextView textViewAuthor = (TextView) convertView.findViewById(R.id.textViewAuthor);
            TextView textViewDate = (TextView) convertView.findViewById(R.id.textViewDate);
            textViewMessage.setText(message.getMessageBody().trim());
            textViewAuthor.setText(message.getAuthor().trim());
            textViewDate.setText(DateFormatter.getFormattedDateFromISOString(message.getDateCreated().trim()));
          }
          break;
        case TYPE_STATUS:
          /*res = R.layout.status_message;
          convertView = layoutInflater.inflate(res, viewGroup, false);
          ChatMessage status = messages.get(position);
          TextView textViewStatus = (TextView) convertView.findViewById(R.id.textViewStatusMessage);
          String statusMessage = status.getMessageBody();
          textViewStatus.setText(statusMessage);*/
          break;
      }
    }
    else
    {
      switch (type) {
        case TYPE_MESSAGE:
          ChatMessage message = messages.get(position);
//          PIYUSH 28-03-2022
          String identity = "DRSHAH";
          if(!message.getAuthor().equals(identity))
          {
            res = R.layout.sysquo_message_send;
            convertView = layoutInflater.inflate(res, viewGroup, false);
            LinearLayout LayoutMain = (LinearLayout) convertView.findViewById(R.id.LayoutMain);
            LinearLayout LayoutMain_Sender = (LinearLayout) convertView.findViewById(R.id.LayoutMain_Sender);
            LayoutMain_Sender.setBackground(mContext.getDrawable(R.drawable.sysquo_chat_send_ar));
            TextView textViewMessage = (TextView) convertView.findViewById(R.id.textViewMessage);
            TextView textViewAuthor = (TextView) convertView.findViewById(R.id.textViewAuthor);
            TextView textViewDate = (TextView) convertView.findViewById(R.id.textViewDate);
            textViewMessage.setText(message.getMessageBody().trim());
            textViewAuthor.setText(message.getAuthor().trim());
            textViewDate.setText(DateFormatter.getFormattedDateFromISOString(message.getDateCreated().trim()));
          }
          else
          {
            res = R.layout.sysquo_message;
            convertView = layoutInflater.inflate(res, viewGroup, false);
            LinearLayout LayoutMain = (LinearLayout) convertView.findViewById(R.id.LayoutMain);
            LinearLayout LayoutMain_Receiver = (LinearLayout) convertView.findViewById(R.id.LayoutMain_Receiver);
            LayoutMain_Receiver.setBackground(mContext.getDrawable(R.drawable.sysquo_chat_received_ar));
            TextView textViewMessage = (TextView) convertView.findViewById(R.id.textViewMessage);
            TextView textViewAuthor = (TextView) convertView.findViewById(R.id.textViewAuthor);
            TextView textViewDate = (TextView) convertView.findViewById(R.id.textViewDate);
            textViewMessage.setText(message.getMessageBody().trim());
            textViewAuthor.setText(message.getAuthor().trim());
            textViewDate.setText(DateFormatter.getFormattedDateFromISOString(message.getDateCreated().trim()));
          }
          break;
        case TYPE_STATUS:
          res = R.layout.sysquo_status_message;
          convertView = layoutInflater.inflate(res, viewGroup, false);
          ChatMessage status = messages.get(position);
          TextView textViewStatus = (TextView) convertView.findViewById(R.id.textViewStatusMessage);
          String statusMessage = status.getMessageBody().trim();
          textViewStatus.setText(statusMessage);
          break;
      }
    }

    return convertView;
  }
}
