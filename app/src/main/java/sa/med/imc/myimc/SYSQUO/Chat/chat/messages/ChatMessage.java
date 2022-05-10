package sa.med.imc.myimc.SYSQUO.Chat.chat.messages;

import com.twilio.chat.Message;

import java.io.InputStream;

public interface ChatMessage {

  String getMessageBody();

  String getAuthor();

  String getDateCreated();
  String getFileName();

  void setContentTemporaryUrl(String s);
  String getContentTemporaryUrl();
  Message.Type getMediaType();
  boolean hasMedia();
}
