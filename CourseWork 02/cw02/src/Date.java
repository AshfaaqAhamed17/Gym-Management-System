import java.io.Serializable;

public class Date implements Serializable {                             //serializable -> done becaus of the binary file reading & writing

    private String startMembershipDate;                                 //private variable

    public Date(String startMembershipDate) {                            //constructor for the private variables
        this.startMembershipDate = startMembershipDate;
    }

    public String getStartMembershipDate() {                            //method to get start membership date
        return startMembershipDate;
    }

    public void setStartMembershipDate(String startMembershipDate) {    //method to set start membership date
        this.startMembershipDate = startMembershipDate;
    }

    @Override
    public String toString() {                                          //converting all the values to string inorder to print it
        return startMembershipDate;
    }
}
