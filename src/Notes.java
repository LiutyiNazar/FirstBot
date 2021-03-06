import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Лютий on 26.10.2017.
 */
public class Notes {
    private String writing;
    public List<String> notices = new ArrayList<>();
    private  int NoteID = 0;

    public void writeNote(String string){

        notices.add(string);

    }

    public int getLastID(){
        return notices.size()-1;//notices.size()-1;
    }

    public String getLastNote(){
        return notices.get(notices.size()-1);
    }

    public List<String> getLastFewNotes(int CountOfNotes){
        List<String> list = new ArrayList<>();
        if( CountOfNotes > notices.size()){
            CountOfNotes = notices.size();
        }
        int tempID = notices.size()  - CountOfNotes;
        for (int i = CountOfNotes; i>0; i--){
            if(tempID > -1) {
                writing = notices.get(tempID);
                list.add(writing);
                tempID++;
            }
        }
        return list;
    }

    public List<String> getLast_5_Notes (){
        return getLastFewNotes(5);
    }

    public List<String> getLast_10_Notes (){
        return getLastFewNotes(10);
    }

    public List<String> getAllMyNotes(){
        List<String> list = new ArrayList<>();
        for (String o : notices){
            if(o!= null){
                list.add(o);
            }
        }
        return list;
    }

    public File getNotesAsFile () throws IOException{
        File file = new File("L:\\noted.txt");
        int counter = 1;

            FileWriter fileWriter = new FileWriter(file);

            fileWriter.write("Your notes: \r\n");
            if (!notices.isEmpty()) {
                for (String notes : notices) {
                    fileWriter.write("" + counter + " - " + notes+ "\r\n"  );

                    counter++;
                }
            }
            fileWriter.flush();
            fileWriter.close();

            return file;

    }

    public boolean hasNotes (){
        return (!notices.isEmpty());
    }
}
