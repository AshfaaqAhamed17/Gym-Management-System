import java.io.Serializable;

public class Over60Member extends DefaultMember implements Serializable {   //serializable -> done becaus of the binary file reading & writing

    private String age;                                                     //private variable

    public Over60Member(String name, String membershipID, Date startMembershipDate, String age) { //constructor for the private variables
        super(name, membershipID, startMembershipDate);
        this.age = age;
    }

    public String getAge() {            //method to get age
        return age;
    }

    public void setAge(String age) {    //method to set age
        this.age = age;
    }

    @Override
    public String toString() {          ////converting all the values to string inorder to print it
        return "-----Over 60 Member-----\nName : "+getName()+"\nMembership ID : "+getMembershipID()+"\nDate joined : "+getStartMembershipDate()+"\nAge : "+getAge()+"\n";
    }
}

