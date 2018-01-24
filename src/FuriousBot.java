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


/**
 * Created by Лютий on 26.10.2017.
 */
public class FuriousBot extends TelegramLongPollingBot {

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
        Users user = new Users(firstName,secondName);
        String notice = null;
       // Notes note = new Notes();
        user.note = new Notes();
        String txt = msg.getText();

        String writed = " Hello, " + firstName + " " + secondName + "!\nThis is " + getBotUsername() +
                " ! \nI have now methods /start, /say_something ,\n" +
                " /write_note( at the same line write your note )\n" +
                " and method /get_last_note . \nYou can ty it!";

        if (txt.substring(0, 1).equals("/")) {
            if (txt.equals("/start")) {
                sendMsg(msg, writed);
            } else if (txt.equals("/say_something")) {
                sendMsg(msg, "Today is a good day!\n Just smile and shine!");

            } else if (txt.substring(0, 11).equals("/write_note")) {

                if (txt.length() <= 12) {
                sendMsg(msg, "Try again, your note was too short!");
                }

            notice = txt.substring(11, txt.length());

            user.note.writeNote(notice);
            sendMsg(msg, "Your note has been saved! Id of your note is : " +  user.note.getLastID() +
                    "\n Your note is : \n" +  user.note.getLastNote());
        }else if (txt.substring(0,14).equals("/get_last_note")) {

                if ( user.note.getLastID() >=0 ) {
                    // int   Count = Integer.valueOf(txt.substring(19,20)); it`s for few notes
                    sendMsg(msg, "Your last note: \n " +  user.note.getLastNote());
                }else{
                    sendMsg(msg, "You don`t have any saved notes!\n" +
                            "Please, write some and try again.");
                }
            }

    }else{
        sendMsg(msg, "Incorrect input . Please, try again");
    }

}
 /*   @SuppressWarnings("deprecation")
    private File sendDocument(Message message, File file) {
        SendDocument sen = new SendDocument();
        sen.setChatId(message.getChatId());
        org.telegram.telegrambots.api.objects.File ff = new org.telegram.telegrambots.api.objects.File()
        ff= file;
        sen.setNewDocument();
        try {
            super.sendDocument(sen);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

  */

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




    @Override
    public String getBotUsername() {
        return "MyFuriousBot";
    }

    @Override
    public String getBotToken() {
        return "402476540:AAEwHy4LNFdAmLP9I8H5u_22J_1z5OsINcQ";
    }


}


