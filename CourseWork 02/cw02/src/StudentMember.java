import java.io.Serializable;

public class StudentMember extends DefaultMember implements Serializable {  //serializable -> done becaus of the binary file reading & writing

    private String schoolName;                                              //private variable

    public StudentMember(String name, String membershipID, Date startMembershipDate, String schoolName) { //constructor for the private variables
        super(name, membershipID, startMembershipDate);
        this.schoolName = schoolName;
    }

    public String getSchoolName() {                 //method to get school name
        return schoolName;
    }

    public void setSchoolName(String schoolName) {  //method to set school name
        this.schoolName = schoolName;
    }

    @Override
    public String toString() {                      //converting all the values to string inorder to print it
        return "-----Student Member-----\nName : "+getName()+"\nMembership ID : "+getMembershipID()+"\nDate joined : "+getStartMembershipDate()+"\nSchool Name : "+getSchoolName()+"\n";
    }


}
