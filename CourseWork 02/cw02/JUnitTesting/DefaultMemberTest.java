import org.junit.Test;

import static org.junit.Assert.*;

public class DefaultMemberTest {

    @Test
    public void getName() {
        DefaultMember defaultMember = new DefaultMember("Ashfaaq","2019394",new Date("12/07/2020"));
        String nameTest = "Ashfaaq";
        defaultMember.setName(nameTest);
        assertEquals(nameTest,defaultMember.getName());


    }

    @Test
    public void getMembershipID() {
        DefaultMember defaultMember = new DefaultMember("Ashfaaq","2019394",new Date("12/07/2020"));
        int membershipIDTest = 2019394;
        defaultMember.setMembershipID(String.valueOf(membershipIDTest));
        assertEquals(membershipIDTest,defaultMember.getMembershipID());
    }

    @Test
    public void getStartMembershipDate() {

        DefaultMember defaultMember = new DefaultMember("Ashfaaq","2019394",new Date("12/07/2020"));
        String dateTest = "12/07/2020";
        defaultMember.setStartMembershipDate(new Date(dateTest));
        assertEquals(dateTest,defaultMember.getStartMembershipDate().getStartMembershipDate());

    }
}