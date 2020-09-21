import java.io.Serializable;

public class DefaultMember implements Serializable {        //serializable -> done becaus of the binary file reading & writing
    private String name;                                    //private variable
    private String membershipID;                            //private variable
    private Date startMembershipDate;                       //private variable

    public DefaultMember(String name, String membershipID, Date startMembershipDate) {      //constructor for the private variables
        this.name = name;
        this.membershipID = membershipID;
        this.startMembershipDate = startMembershipDate;
    }


    public String getName() {               //method to get name
        return name;
    }

    public int getMembershipID() {          //method to get ID
        return Integer.parseInt(membershipID);
    }

    public Date getStartMembershipDate() {  //method to get start membership date
        return startMembershipDate;
    }

    public void setName(String name) {                              //method to set name
        this.name = name;
    }

    public void setMembershipID(String membershipdID) {             //method to set ID
        this.membershipID = membershipdID;
    }

    public void setStartMembershipDate(Date startMembershipDate) {  //method to set start membership date
        this.startMembershipDate = startMembershipDate;
    }

    @Override
    public String toString() {      //converting all the values to string inorder to print it
        return "-----Default Member-----\nName : "+getName()+"\nMembership ID : "+getMembershipID()+"\nDate joined : "+getStartMembershipDate()+"\n";
    }

}
