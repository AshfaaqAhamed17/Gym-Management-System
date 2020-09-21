import javafx.stage.Stage;
import java.io.IOException;

interface GymManager {
    void addMember();                       //adding member method
        void defaultMember();               //adding a default member method
        void studentMember();               //adding a student member method
        void over60Member();                //adding an over 60 member method
    void deleteMember();                    //deleting a member method
    void printMember();                     //printing a member method
    void sortMember();                      //sorting a member method
    void searchMember();                    //search a member method
    void writeMember() throws IOException;  //write or save members to file method
    void openGUI(Stage mainStage);          //open the GUI method
}