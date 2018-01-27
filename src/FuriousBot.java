import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.groupadministration.SetChatPhoto;
import org.telegram.telegrambots.api.methods.send.SendDocument;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.stickers.UploadStickerFile;
import org.telegram.telegrambots.api.methods.updates.GetUpdates;
import org.telegram.telegrambots.api.objects.*;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import sun.plugin.util.UserProfile;

import java.io.IOException;


/**
 * Created by Лютий on 26.10.2017.
 */
public class FuriousBot extends TelegramLongPollingBot {
    Notes note = new Notes();

    public static void main(String[] args) {
        ApiContextInitializer.init(); // Here we initialize our API
        TelegramBotsApi botapi = new TelegramBotsApi();
        try {
            botapi.registerBot(new FuriousBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpdateReceived(Update e) {
            Message msg = e.getMessage();
            Chat chat = msg.getChat();
            String firstName = chat.getFirstName();
            String secondName = chat.getLastName();
            String notice = null;
            String txt = msg.getText();

        String writed = " Hello, " + firstName + " " + secondName + "!\nThis is " + getBotUsername() +
                " ! \nI have now methods /start, /say_something ,\n" +
                " /write_note( at the same line write your note,\n don`t use from tool-bar )\n" +
                " /get_last_note , /notes_as_file ,\n " +
                "/get_last5_notes \nYou can ty it!";

        if (txt.substring(0, 1).equals("/")) {
            if (txt.equals("/start"))  {

                sendMsg(msg, writed);

            } else if (txt.equals("/say_something"))   {

                sendMsg(msg, "Today is a good day!\n Just smile and shine!");

            } else if (txt.substring(0, 11).equals("/write_note")) {

                if (txt.length() <= 12) {
                sendMsg(msg, "Try again, your note was too short!");
                }else {
                    notice = txt.substring(11, txt.length());

                    note.writeNote(notice);
                    sendMsg(msg, "Your note has been saved! Id of your note is : " +  note.getLastID() +
                            "\n Your note is : \n" +  note.getLastNote());
                }


            }else if (txt.substring(0,14).equals("/get_last_note")) {

                if ( note.getLastID() >=0 ) {
                    // int   Count = Integer.valueOf(txt.substring(19,20)); it`s for few notes
                    sendMsg(msg, "Your last note: \n " +  note.getLastNote());
                }else{
                    sendMsg(msg, "You don`t have any saved notes!\n" +
                            "Please, write some and try again.");
                }
            }else if (txt.substring(0,14).equals("/notes_as_file")){
                try {
                    Long chatID = msg.getChatId();
                    if (note.hasNotes()) sendDocUploadingAFile(chatID, note.getNotesAsFile(), "Here are file with your notes");
                }catch (Exception e1){
                    System.out.println("We had file exeption");
                }
            }else if (txt.substring(0,16).equals("/get_last5_notes")){
                for (String st: note.getLast_5_Notes()) {
                    sendMsg(msg, st);
                }
            }

    }else{
        sendMsg(msg, "Incorrect input . Please, try again");
    }

}


    @SuppressWarnings("deprecation")
    private void sendMsg(Message msg, String text) {
        SendMessage s = new SendMessage();
        s.setChatId(msg.getChatId()); // Боту может писать не один человек, и поэтому чтобы отправить сообщение, грубо говоря нужно узнать куда его отправлять
        s.setText(text);
        try { //Чтобы не крашнулась программа при вылете Exception
            sendMessage(s);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    private void sendDocUploadingAFile(Long chatId, java.io.File save,String caption) throws TelegramApiException {

        SendDocument sendDocumentRequest = new SendDocument();
        sendDocumentRequest.setChatId(chatId);
        sendDocumentRequest.setNewDocument(save);
        sendDocumentRequest.setCaption(caption);
        sendDocument(sendDocumentRequest);
    }



    @Override
    public String getBotUsername() {
        return "MyFuriousBot";
    }

    @Override
    public String getBotToken() {
        return "******************************";
    }


}


