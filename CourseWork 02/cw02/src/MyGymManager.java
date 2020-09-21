import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;
import static java.lang.Integer.*;

public class MyGymManager extends Application implements GymManager{

    private static ArrayList<DefaultMember> memberArrayList = new ArrayList<>();    //Creating array list
    private static File gymFile;                                                    //Initiating the file
    private static File gymBinaryFile;                                              //Initiating the file
//========================================================================================================================================================================>
    public static void main(String[] args) throws IOException{

        try {
            gymBinaryFile = new File("GymRecordsBinaryFile.ser");           //check if this file is available
            gymBinaryFile.createNewFile();                                           //else create the file
        }catch (IOException e) {
            System.out.print("Creating file");
        }finally {
            try{
                FileInputStream gymFileInputStream = new FileInputStream(gymBinaryFile);                //binary file input stream to read from file
                ObjectInputStream gymObjectInputStream = new ObjectInputStream(gymFileInputStream);     //binary file object input stream to read objects
                memberArrayList = (ArrayList<DefaultMember>) gymObjectInputStream.readObject();         //reading

                gymFileInputStream.close();
                gymObjectInputStream.close();

            }catch (EOFException e){                                                                    //prevents error when it reaches the end of file <when reading objects Objects>
                System.out.println("====");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        launch(args);                                                                                   //launch start method
    }
//========================================================================================================================================================================>
    @Override
    public void start(Stage mainStage) throws IOException {
        Scanner menuScanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n|------------------------------------------------------------------------------------|");
            System.out.println("|------------------ G Y M    M A N A G E M E N T    S Y S T E M ---------------------|");
            System.out.println("|------------------------------------------------------------------------------------|\n");
            System.out.println(" \t\tEnter 'A' to add new member to the list");
            System.out.println(" \t\tEnter 'D' to delete a member from the list");
            System.out.println(" \t\tEnter 'P' to print a list of members in the system");
            System.out.println(" \t\tEnter 'S' to sort the list of members");
            System.out.println(" \t\tEnter 'C' to search a member in the list");
            System.out.println(" \t\tEnter 'W' to write/Save to a file");
            System.out.println(" \t\tEnter 'O' to open the GUI");
            System.out.println(" \t\tEnter 'X' to end the program\n");
            System.out.print("-->> Select an option : ");
            String selectMenu = menuScanner.next();         //Menu option to select which method to start with

            switch (selectMenu) {
                case "A":       //option to add
                case "a":

                    System.out.println("\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                    System.out.println("+\t\tNumber of members in the gym        -> " + memberArrayList.size()+"\t\t+");
                    System.out.println("+\t\tNumber of members that can be added -> " + (100 - memberArrayList.size())+"\t\t+");
                    System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                    if (memberArrayList.size() != 100) {
                        addMember();
                        break;
                    } else {
                        System.out.println("\n***************LIMIT EXCEEDED***************\n");
                        break;
                    }

                case "D":       //option to delete
                case "d":
                    deleteMember();
                    break;

                case "P":       //option to print
                case "p":
                    printMember();
                    break;

                case "S":       //option to sort
                case "s":
                    sortMember();
                    break;

                case "C":       //option to search
                case "c":
                    searchMember();
                    break;

                case "W":       //option to write/save to file
                case "w":
                    writeMember();
                    break;

                case "O":       //option to open GUI
                case "o":
                    openGUI(mainStage);
                    break;

                case "X":       //option to exit program
                case "x":
                    System.out.println("======================================================================================");
                    System.out.println("=                                    Thank you!!!                                    =");
                    System.out.println("======================================================================================");
                    System.exit(1);

                default:        //when invalid option entered, it returns back to the menu
                    System.out.println("\n---------------Invalid Option---------------\n");
                    break;
            }
        }
    }
//========================================================================================================================================================================>
    public void addMember() {   //select the type of member
        System.out.println();
        Scanner addScanner = new Scanner(System.in);
        Menu:
        while (true) {
            System.out.println(" \t\tEnter '1' to add member to Default Member List");
            System.out.println(" \t\tEnter '2' to add member to Student Member List");
            System.out.println(" \t\tEnter '3' to add member to Over 60 Member List\n");
            System.out.print("-->> Select an option : ");
            String addMember = addScanner.next();           //Menu to select the type of the member

            switch (addMember) {
                case "1":                                   //default member option
                    defaultMember();
                    break Menu;

                case "2":                                   //student member option
                    studentMember();
                    break Menu;

                case "3":                                   //over 60 member option
                    over60Member();
                    break Menu;

                default:                                    //invalid option
                    System.out.println("\n---------------Invalid Option---------------\n");
                    break;
            }
        }
    }
//========================================================================================================================================================================>
    public void defaultMember(){                        //input details for default members
        String name;
        String membershipID;
        String startMembershipDate;
        while (true){
            try {
                Scanner addScanner = new Scanner(System.in);
                System.out.print("\nName : ");                  //name input and validation
                name =  addScanner.nextLine().toUpperCase();    //store name as upper case
                while (true){                                     //loop if improper name is given
                    if (!name.matches("[a-zA-Z\\s]+")) {    //validating the name input
                        System.out.println("---------------Please enter valid name---------------\n");
                        System.out.print("Name : ");
                        name =  addScanner.nextLine().toUpperCase();
                    }else {
                        break;
                    }
                }

                System.out.print("Membership Number : ");   //membership ID input and validation
                membershipID = addScanner.nextLine();

                boolean checkTaken = true;                  //boolean to check if the ID number is repeating
                while (checkTaken) {                        //loop if ID number is already taken
                    if (!membershipID.matches("[0-9]+")) {  //validating ID number input
                        System.out.println("---------------Please enter valid ID number---------------\n");
                        System.out.print("Membership Number : ");
                        membershipID = addScanner.nextLine();
                        checkTaken = true;
                    } else {
                        checkTaken = false;
                    }

                    for (int loop = 0; loop < memberArrayList.size(); loop++) {
                        try {
                            if ((memberArrayList.get(loop).getMembershipID() == parseInt(membershipID))) {  //check if ID number is repeated?
                                System.out.println("---------------Membership ID already taken---------------\n");
                                System.out.print("Membership Number : ");
                                membershipID = addScanner.nextLine();
                                checkTaken = true;
                            } else {
                                checkTaken = false;
                            }
                        }catch (Exception e){
                            System.out.println("---------------Please enter valid ID number---------------\n");     //improper ID number entered
                            System.out.print("Membership Number : ");
                            membershipID = addScanner.nextLine();
                            checkTaken = true;
                        }
                    }
                }

                System.out.print("Date of join in (DD/MM/YYYY): ");     //Join date input and validation
                startMembershipDate =  addScanner.nextLine();
                while (true) {
                    if (startMembershipDate.matches("[0-9/]+")) {                       //date can contain only these elements
                        try {
                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");   //pattern to enter date
                            formatter.setLenient(false);                                      //check date format object
                            formatter.parse(startMembershipDate);                             //parsing input date to formatter
                            break;

                        } catch (ParseException e) {
                            System.out.println("Please use the given Date format -> (\"DD/MM/YYYY\")");     //if entered date is not in the required format
                            System.out.print("Date of join in (DD/MM/YYYY): ");                             //Join date input and validation
                            startMembershipDate =  addScanner.nextLine();
                        }
                    } else if (!startMembershipDate.matches("[0-9/]+")){                              //date can contain only these elements
                        System.out.println("---------------Enter valid Date in given Date format -> (\"DD/MM/YYYY\")---------------\n");             //date validating
                        System.out.print("Date of join in (DD/MM/YYYY): ");                                 //Join date input and validation
                        startMembershipDate = addScanner.nextLine();
                    }else {
                        break;
                    }
                }

                memberArrayList.add(new DefaultMember(name,membershipID,new Date(startMembershipDate)));    //adding the default member to array list
                System.out.println("\n---------------Recruit receipt---------------\n\n\t* Your name is "+ name+"\n\t* Your ID is "+membershipID+"\n\t* Membership date from - "+startMembershipDate+"\n");
                //printing the details which was inputted
                break;
            }catch (InputMismatchException e){
                    System.out.println("\n---------------Invalid inputs---------------\n");
            }
        }
    }
//========================================================================================================================================================================>
    public void studentMember(){    //input details for student member

        String schoolName;
        String name;
        String membershipID;
        String startMembershipDate;
        while (true){
            try {
                Scanner addScanner = new Scanner(System.in);
                System.out.print("\nName : ");                  //name input and validation
                name =  addScanner.nextLine().toUpperCase();    //store as upper case
                while (true){
                    if (!name.matches("[a-zA-Z\\s]+")) {    //name validation
                        System.out.println("---------------Please enter valid name---------------\n");
                        System.out.print("Name : ");
                        name =  addScanner.nextLine().toUpperCase();
                    }else {
                        break;
                    }
                }

                System.out.print("Membership Number : ");   //membership ID input and validation
                membershipID = addScanner.nextLine();

                boolean checkTaken = true;                  //boolean to check if the ID number is repeating
                while (checkTaken) {                        //loop if ID number is already taken
                    if (!membershipID.matches("[0-9]+")) {  //validating ID number input
                        System.out.println("---------------Please enter valid ID number---------------\n");
                        System.out.print("Membership Number : ");
                        membershipID = addScanner.nextLine();
                        checkTaken = true;
                    } else {
                        checkTaken = false;
                    }

                    for (int loop = 0; loop < memberArrayList.size(); loop++) {
                        try {
                            if ((memberArrayList.get(loop).getMembershipID() == parseInt(membershipID))) {  //check if ID is repeating
                                System.out.println("---------------Membership ID already taken---------------\n");
                                System.out.print("Membership Number : ");
                                membershipID = addScanner.nextLine();
                                checkTaken = true;
                            } else {
                                checkTaken = false;
                            }
                        }catch (Exception e){
                            System.out.println("---------------Please enter valid ID number---------------\n");     //improper ID entered
                            System.out.print("Membership Number : ");
                            membershipID = addScanner.nextLine();
                            checkTaken = true;
                        }
                    }
                }

                System.out.print("Date of join in (DD/MM/YYYY): ");     //Join date input and validation
                startMembershipDate =  addScanner.nextLine();
                while (true) {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");   //pattern to enter date
                    if (startMembershipDate.matches("[0-9/]+")) {                       //date can contain only these elements
                        try {
                            formatter.setLenient(false);                                      //check date format object
                            formatter.parse(startMembershipDate);                             //parsing input date to formatter
                            break;

                        } catch (ParseException e) {
                            System.out.println("Please use the given Date format -> (\"DD/MM/YYYY\")");     //if entered date is not in the required format
                            System.out.print("Date of join in (DD/MM/YYYY): ");                             //Join date input and validation
                            startMembershipDate =  addScanner.nextLine();
                        }
                    } else if (!startMembershipDate.matches("[0-9/]+")){                              //date can contain only these elements
                        System.out.println("---------------Enter valid Date---------------\n");             //date validating
                        System.out.print("Date of join in (DD/MM/YYYY): ");                                 //Join date input and validation
                        startMembershipDate = addScanner.nextLine();
                    }else {
                        break;
                    }
                }
                System.out.print("School : ");   //School name input and validation
                schoolName =  addScanner.nextLine().toUpperCase();
                while (true){
                    if ((schoolName.matches("[0-9]+")) || (!schoolName.matches("[a-zA-Z0-9-\\s]+"))) {   //validating school name
                        System.out.println("---------------Please enter valid school name---------------\n");
                        System.out.print("School : ");
                        schoolName =  addScanner.nextLine().toUpperCase();                                          //store school name is upperCase
                    }else {
                        break;
                    }
                }

                memberArrayList.add(new StudentMember(name,membershipID,new Date(startMembershipDate),schoolName));    //adding the student member to array list
                System.out.println("\n---------------Recruit receipt---------------\n\n\t* Your name is "+ name+"\n\t* Your ID is "+membershipID+"\n\t* Membership date from - "+startMembershipDate+"\n\t* Name of School - "+schoolName+"\n");
                //printing the details which was inputted
                break;
            }
            catch (InputMismatchException e){
                System.out.println("---------------Invalid inputs---------------\n");
            }
        }
    }
//========================================================================================================================================================================>
    public void over60Member(){    //input details for over 60 member

        String age;
        String name;
        String membershipID;
        String startMembershipDate;
        while (true){
            try {
                Scanner addScanner = new Scanner(System.in);
                System.out.print("\nName : ");                    //name input and validation
                name =  addScanner.nextLine().toUpperCase();      //store name in upperCase
                while (true){
                    if (!name.matches("[a-zA-Z\\s]+")) {    //name validation
                        System.out.println("---------------Please enter valid name---------------\n");
                        System.out.print("Name : ");
                        name =  addScanner.nextLine().toUpperCase();
                    }else {
                        break;
                    }
                }

                System.out.print("Membership Number : ");   //membership ID input and validation
                membershipID = addScanner.nextLine();

                boolean checkTaken = true;                  //boolean to check if the ID number is repeating
                while (checkTaken) {                        //loop if ID number is already taken
                    if (!membershipID.matches("[0-9]+")) {  //validating ID number input
                        System.out.println("---------------Please enter valid ID number---------------\n");
                        System.out.print("Membership Number : ");
                        membershipID = addScanner.nextLine();
                        checkTaken = true;
                    } else {
                        checkTaken = false;
                    }

                    for (int loop = 0; loop < memberArrayList.size(); loop++) {
                        try {
                            if ((memberArrayList.get(loop).getMembershipID() == parseInt(membershipID))) {  //check if ID is repeating
                                System.out.println("---------------Membership ID already taken---------------\n");
                                System.out.print("Membership Number : ");
                                membershipID = addScanner.nextLine();
                                checkTaken = true;
                            } else {
                                checkTaken = false;
                            }
                        }catch (Exception e){
                            System.out.println("---------------Please enter valid ID number---------------\n");     //improper ID entered
                            System.out.print("Membership Number : ");
                            membershipID = addScanner.nextLine();
                            checkTaken = true;
                        }
                    }
                }

                System.out.print("Date of join in (DD/MM/YYYY): ");     //Join date input and validation
                startMembershipDate =  addScanner.nextLine();
                while (true) {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");   //pattern to enter date
                    if (startMembershipDate.matches("[0-9/]+")) {                       //date can contain only these elements
                        try {
                            formatter.setLenient(false);                                      //check date format object
                            formatter.parse(startMembershipDate);                             //parsing input date to formatter
                            break;

                        } catch (ParseException e) {
                            System.out.println("Please use the given Date format -> (\"DD/MM/YYYY\")");     //if entered date is not in the required format
                            System.out.print("Date of join in (DD/MM/YYYY): ");                             //Join date input and validation
                            startMembershipDate =  addScanner.nextLine();
                        }
                    } else if (!startMembershipDate.matches("[0-9/]+")){                              //date can contain only these elements
                        System.out.println("---------------Enter valid Date---------------\n");             //date validating
                        System.out.print("Date of join in (DD/MM/YYYY): ");                                 //Join date input and validation
                        startMembershipDate = addScanner.nextLine();
                    }else {
                        break;
                    }
                }

                System.out.print("Age : ");                                                             //Age input and validation
                age =  addScanner.nextLine();
                while (true){
                    if (!age.matches("[0-9]+")){                                                  //age should contain only these elements
                        System.out.println("---------------Enter valid Age---------------\n");
                        System.out.print("Age : ");
                        age =  addScanner.nextLine();
                    }else if ((age.matches("[0-9]+")) && (parseInt(age)<60)) {                    //age should be more than 60
                        System.out.println("---------------The entered age is below 60---------------\n");
                        System.out.print("Age : ");
                        age =  addScanner.nextLine();
                    }else {
                        break;
                    }
                }

                memberArrayList.add(new Over60Member(name,membershipID,new Date(startMembershipDate),age));    //adding the Over60 member to array list
                System.out.println("\n---------------Recruit receipt---------------\n\n\t* Your name is "+ name+"\n\t* Your ID is "+membershipID+"\n\t* Membership date from - "+startMembershipDate+"\n\t* Age - "+age+"\n");
                //printing the details which was inputted

                break;
            }catch (InputMismatchException e){
                    System.out.println("---------------Invalid inputs---------------\n");
                }
            }
        }
//========================================================================================================================================================================>
    public void deleteMember() {            //delete a member by ID
        if (memberArrayList.size()==0){     //check if there are any members
            System.out.print("\n---------------No members registered yet---------------\n");
        }else {
            try {
                while (true) {
                    Scanner deleteScanner = new Scanner(System.in);
                    System.out.print("\nEnter ID to be deleted OR press \"0\" to cancel: ");
                    int searchDelete = deleteScanner.nextInt();
                    if (searchDelete == 0) {    //exit method by pressing "0"
                        break;
                    }
                    for (int loop = 0; loop <= memberArrayList.size(); loop++)
                        if (memberArrayList.get(loop).getMembershipID() == searchDelete) {      //checking the list if the entered ID is there in the list
                            System.out.println("\n---------------Successfully deleted the below member---------------\n\n" + memberArrayList.get(loop));
                            memberArrayList.remove(loop);                                       //remove member
                            System.out.println("\n\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                            System.out.println("+\t\tNumber of members in the gym        -> " + memberArrayList.size()+"\t\t+");
                            System.out.println("+\t\tNumber of members that can be added -> " + (100 - memberArrayList.size())+"\t\t+");
                            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                            //display the number of members
                            break;
                        }
                }
            } catch (Exception e) {     //error msg if ID is not in the list
                System.out.println("\n---------------Invalid ID---------------\n");
                deleteMember();         //iterate again for user to delete a member
            }
        }
    }
//========================================================================================================================================================================>
    public void printMember() {             //print the list of members
        if (memberArrayList.size()==0){     //check if there are members in the list
            System.out.print("\n---------------No members registered yet---------------\n");
        }else{
            System.out.println();
            for (int loop = 0 ; loop < memberArrayList.size() ; loop++){
                System.out.println(memberArrayList.get(loop));          //print all the members in the list
            }
        }System.out.println("\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("+\t\tNumber of members in the gym        -> " + memberArrayList.size()+"\t\t+");
        System.out.println("+\t\tNumber of members that can be added -> " + (100 - memberArrayList.size())+"\t\t+");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
        //display the number of member
    }
//========================================================================================================================================================================>
    public void sortMember() {              //sort members in the list from the given option
        if (memberArrayList.size()==0){     //check if there are members in the list
            System.out.print("\n---------------No members registered yet---------------\n");
        }else {
            while (true){
            Scanner sortScanner = new Scanner(System.in);
            System.out.println();
            System.out.println(" \t\tEnter \"1\" to sort by ID");           //option to sort by ID number
            System.out.println(" \t\tEnter \"2\" to sort by Name\n");       //option to sort by name
            System.out.print("-->> Select an option : ");
            String sort = sortScanner.nextLine();
            System.out.println();

                if (sort.equals("1")) {                                     //sorting by ID    <BUBBLE SORT METHOD>
                    for (int outerLoop = 0; outerLoop < memberArrayList.size() - 1; outerLoop++) {
                        for (int innerLoop = 0; innerLoop < memberArrayList.size() - (outerLoop + 1); innerLoop++) {
                            if (memberArrayList.get(innerLoop).getMembershipID() > memberArrayList.get(innerLoop + 1).getMembershipID()) {
                                DefaultMember sortByIDVariable = memberArrayList.get(innerLoop + 1);    //store the value in temporary variable
                                memberArrayList.set(innerLoop + 1, memberArrayList.get(innerLoop));
                                memberArrayList.set(innerLoop, sortByIDVariable);
                            }
                        }
                    }
                    for (int loop = 0; loop < memberArrayList.size(); loop++) {
                        System.out.println(memberArrayList.get(loop));      //printing the sorted the list
                    }System.out.println("---------------Sorted according to the ID---------------\n");
                    break;

                } else if (sort.equals("2")) {                              //sorting by ID    <BUBBLE SORT METHOD>
                    for (int outerLoop = 0; outerLoop < memberArrayList.size() - 1; outerLoop++) {
                        for (int innerLoop = 0; innerLoop < memberArrayList.size() - (outerLoop + 1); innerLoop++) {
                            if (memberArrayList.get(innerLoop).getName().compareTo(memberArrayList.get(innerLoop + 1).getName()) > 0) {
                                DefaultMember sortByNameVariable = memberArrayList.get(innerLoop + 1);  //store the value in temporary variable
                                memberArrayList.set(innerLoop + 1, memberArrayList.get(innerLoop));
                                memberArrayList.set(innerLoop, sortByNameVariable);
                            }
                        }
                    }
                    for (int loop = 0; loop < memberArrayList.size(); loop++) {
                        System.out.println(memberArrayList.get(loop));      //printing the sorted the list

                    }System.out.println("---------------Sorted according to the Name---------------\n");
                    break;
                }else{
                    System.out.print("---------------Invalid  Option---------------\n\n");
                }
            }
        }
    }
//========================================================================================================================================================================>
    public void searchMember() {                //search members in the list from the given option
        if (memberArrayList.size()==0) {        //check if there are members in the list
            System.out.print("\n---------------No members registered yet---------------\n");
        }else {
            try {
                while (true) {
                    Scanner searchScanner = new Scanner(System.in);
                    System.out.println();
                    System.out.println(" \t\tEnter \"1\" for search by ID");        //option to search by ID number
                    System.out.println(" \t\tEnter \"2\" for search by Name\n");    //option to search by name
                    System.out.print("-->> Select an option : ");
                    String search = searchScanner.nextLine();
                    System.out.println();

                    if (search.equals("1")) {                                       //search by ID
                        System.out.print("Enter ID number : ");                     //enter ID number
                        int searchID = searchScanner.nextInt();

                        boolean found = false;                                      //boolean to stop the iteration
                        for (int loop = 0; loop < memberArrayList.size(); loop++) {
                            if (memberArrayList.get(loop).getMembershipID() == searchID) {  //check the list if the relevant ID mentioned is available or not
                                System.out.println("\n"+memberArrayList.get(loop));         //print the details of the searched ID
                                found = true;                                               //ID is found in the list
                                break;
                            }
                        }if (!found){                                                       //ID not found in the list
                            System.out.println("\n---------------ID Number not found in the List---------------\n");
                            break;
                        }break;

                    } else if (search.equals("2")) {                                //search by NAME
                        System.out.print("Enter name : ");                          //enter name
                        String searchName = searchScanner.nextLine().toUpperCase();

                        boolean found = false;                                      //boolean to stop the iteration
                        for (int loop = 0; loop < memberArrayList.size(); loop++) {
                            if (memberArrayList.get(loop).getName().equals(searchName)) {   //check the list if the relevant name mentioned is available or not
                                System.out.println("\n"+memberArrayList.get(loop));         //print the details of the searched name
                                found = true;                                               //name is found in the list
                            }
                        }if (!found){                                                       //name not found in the list
                            System.out.println("\n---------------Name not found in the List---------------\n");
                            break;
                        }break;
                    } else {
                        System.out.print("---------------Invalid Option---------------\n\n");
                    }
                }
            }catch (Exception e){
                System.out.println("\n---------------Invalid inputs---------------\n");
            }
        }
    }
//========================================================================================================================================================================>
    public void writeMember() throws IOException {                      //Write/Save to txt file for reference purpose && save as binary file to recollect data once wee reopen
        try {
            gymFile = new File("MyGymRecordsFile.txt");             //check if there is file in that name
            gymBinaryFile = new File("GymRecordsBinaryFile.ser");   //check if there is file in that name

            gymFile.createNewFile();                                        // else create a file
            gymBinaryFile.createNewFile();                                  // else create a file

        } catch (IOException e) {
            System.out.print("Creating file");
        }finally {
            //---writing to text file---
            FileWriter gymFileWriter = new FileWriter(gymFile);
            PrintWriter gymPrintWriter = new PrintWriter(gymFileWriter, true);     //autoflush enabled
            for (int loop = 0; loop < memberArrayList.size(); loop++)
                gymPrintWriter.println(memberArrayList.get(loop));

            //---writing to binary file---
            FileOutputStream gymFileOutputStream = new FileOutputStream(gymBinaryFile);
            ObjectOutputStream gymObjectOutputStream = new ObjectOutputStream(gymFileOutputStream);
            gymObjectOutputStream.writeObject(memberArrayList);

            gymFileWriter.close();
            gymPrintWriter.close();

            gymFileOutputStream.close();
            gymObjectOutputStream.close();

        }
        System.out.println("\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("+\t\tNumber of members in the gym        -> " + memberArrayList.size()+"\t\t+");
        System.out.println("+\t\tNumber of members that can be added -> " + (100 - memberArrayList.size())+"\t\t+");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
        //display the number of member
        System.out.println("\n--------------->Data saving is completed successfully<---------------\n\n");
    }
//========================================================================================================================================================================>
    public void openGUI(Stage mainStage){                        //GUI to display the list of members in a table view && search by a parameter
        mainStage = new Stage();                                 //main stage created
        Label mainHeading = new Label("MY GYM MANAGER");    //main heading label
        mainHeading.setLayoutX(245);
        mainHeading.setLayoutY(45);
        mainHeading.setStyle("-fx-font-size: 50 ; -fx-font-family: 'Bookman Old Style' ; -fx-font-weight: bold");

        Label searchBy = new Label("Search the user by ID or NAME");    //search label
        searchBy.setLayoutX(305);
        searchBy.setLayoutY(120);
        searchBy.setStyle("-fx-font-size: 25 ; -fx-font-family: 'Bookman Old Style'");

        Label totalCount = new Label();    //search label
        totalCount.setLayoutX(345);
        totalCount.setLayoutY(290);
        totalCount.setText("Total number of members in Gym - "+memberArrayList.size());
        totalCount.setStyle("-fx-font-size: 16 ; -fx-font-family: 'Bookman Old Style'; -fx-font-weight: bold");

        Label amountLeft = new Label();    //search label
        amountLeft.setLayoutX(320);
        amountLeft.setLayoutY(310);
        amountLeft.setText("Number of members that can be added - " + (100 - memberArrayList.size()));
        amountLeft.setStyle("-fx-font-size: 16 ; -fx-font-family: 'Bookman Old Style'; -fx-font-weight: bold");
    //====================================================================================================================>
        TextField searchTextField = new TextField();                        //search text field
        searchTextField.setLayoutX(400);
        searchTextField.setLayoutY(175);
        searchTextField.setMinSize(200,35);
        searchTextField.setStyle("-fx-alignment: center;");
    //====================================================================================================================>
        Button searchOkButton = new Button("Search");                   //search button
        searchOkButton.setLayoutX(400);
        searchOkButton.setLayoutY(225);
        searchOkButton.setMinSize(200,35);
        searchOkButton.setStyle("-fx-background-color: lightblue; -fx-font-family: 'Bookman Old Style' ; -fx-font-size: 22");

        Button exitButton = new Button("Exit");                         //exit button
        exitButton.setLayoutX(925);
        exitButton.setMinSize(77,35);
        exitButton.setStyle("-fx-background-color: orangered ; -fx-text-fill: white ; -fx-font-family: 'Bookman Old Style'; -fx-font-size: 18;");

        Button helpButton = new Button("!");                            //help button
        helpButton.setLayoutX(960);
        helpButton.setLayoutY(37);
        helpButton.setMinSize(40,20);
        helpButton.setStyle("-fx-background-color: gray ; -fx-text-fill: white ; -fx-font-family: 'Bookman Old Style'; -fx-font-size: 18; -fx-font-weight: bold");
    //====================================================================================================================>
        TableView myGymManagerTable = new TableView();                      //table view created
        myGymManagerTable.setLayoutY(100);

        TableColumn<String, DefaultMember> columnName = new TableColumn<>("Name");          //creating name column
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnName.setMinWidth(200);

        TableColumn<String, DefaultMember> columnId = new TableColumn<>("Membership ID");   //creating ID column
        columnId.setCellValueFactory(new PropertyValueFactory<>("membershipID"));
        columnId.setMinWidth(200);

        TableColumn<Date, DefaultMember> columnDate = new TableColumn<>("Joined Date");     //creating joined date column
        columnDate.setCellValueFactory(new PropertyValueFactory<>("startMembershipDate"));
        columnDate.setMinWidth(200);

        TableColumn<String, DefaultMember> columnSchool = new TableColumn<>("School");      //creating school name column
        columnSchool.setCellValueFactory(new PropertyValueFactory<>("schoolName"));
        columnSchool.setMinWidth(250);

        TableColumn<String, DefaultMember> columnAge = new TableColumn<>("Age");            //creating age column
        columnAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        columnAge.setMinWidth(150);

        myGymManagerTable.getColumns().addAll(columnName,columnId,columnDate,columnSchool,columnAge);   //adding the columns to the table

        for(int loop = 0; loop < memberArrayList.size(); loop++) {
            DefaultMember gymManagerTableData = memberArrayList.get(loop);                          //writing the data to the table
            myGymManagerTable.getItems().addAll(gymManagerTableData);
        }
//====================================================================================================================>
        searchOkButton.setOnAction(new EventHandler<ActionEvent>() {                            //search button onAction
            @Override
            public void handle(ActionEvent event) {

                if (searchTextField.getText().equals("")) {                                     //if textfield is empty display the whole list
                    myGymManagerTable.getItems().clear();
                    for(int i = 0; i < memberArrayList.size(); i++) {
                        DefaultMember gymManagerTableData = memberArrayList.get(i);
                        myGymManagerTable.getItems().addAll(gymManagerTableData);
                    }
                }
                 else if(searchTextField.getText().matches("[0-9]+")){                                      //if textfield contains numbers, search according to ID
                    myGymManagerTable.getItems().clear();
                    for(int i = 0; i < memberArrayList.size(); i++){
                         if (memberArrayList.get(i).getMembershipID() == parseInt(searchTextField.getText())) {   //check the ID list if any matches are there
                             DefaultMember searchByMembershipID = memberArrayList.get(i);
                             myGymManagerTable.getItems().addAll(searchByMembershipID);
                         }
                    }
                 }else if(searchTextField.getText().matches("[a-zA-Z\\s]+")){                               //if textfield contains letters, search according to name
                    myGymManagerTable.getItems().clear();
                    for(int i = 0; i < memberArrayList.size(); i++){
                        if (memberArrayList.get(i).getName().contains(searchTextField.getText().toUpperCase())) { //check the name list if any matches are there
                            DefaultMember searchByName = memberArrayList.get(i);
                            myGymManagerTable.getItems().addAll(searchByName);
                        }
                    }
                }
            }
        });
        //====================================================================================================================>
        Stage finalMainStage = mainStage;   //assigning mainStage to another local variable
        exitButton.setOnAction(new EventHandler<ActionEvent>() {    //exit GUI
            @Override
            public void handle(ActionEvent event) {
                finalMainStage.close();
            }
        });
        //====================================================================================================================>
        helpButton.setOnAction(new EventHandler<ActionEvent>() {    //help button
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);   //alert box to display the help view
                alert.setTitle("My Calculator usage");
                alert.setHeaderText("NOTE \t\t\t\t\t\t\t\t\t\t\t\t\t\t");
                alert.setContentText("\t* Member whose details contains \"School Name\" is a Student Member.\n" +
                        "\t* Member whose details contains \"Age\" is an Over 60 Member.\n" +
                        "\t* Member whose details does not contains \"School Name\" & \"Age\" is a Default Member.\n" +
                        "\t* A member can be searched by typing the member name or his member ID number ");
                Optional<ButtonType> result = alert.showAndWait();
            }
        });
        //====================================================================================================================>
        Pane upperPane = new Pane();        //upper pane to display labels, textfields, buttons
        upperPane.getChildren().addAll(mainHeading,searchBy,searchTextField,searchOkButton,exitButton,helpButton,totalCount,amountLeft);
        upperPane.setStyle("-fx-background-color: deepskyblue");
        upperPane.setMinHeight(360);

        Pane lowerPane = new Pane();        //lower pane conatains the scroll pane

        ScrollPane tableScrollPane = new ScrollPane();  //scroll pane to display table
        tableScrollPane.setContent(myGymManagerTable);

        lowerPane.getChildren().addAll(tableScrollPane);

        VBox myGymManagerVBox = new VBox(upperPane,lowerPane);  //VBox to contain both the panes

        Scene myGymManagerScene = new Scene(myGymManagerVBox,990,750);//setring the scene with VBox
        mainStage.setResizable(false);          //stage is given a fixed size
        mainStage.setTitle("My Gym Manager");
        mainStage.setScene(myGymManagerScene);  //setting the stage with Scene
        mainStage.showAndWait();
    }
}