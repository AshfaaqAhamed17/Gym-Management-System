import org.junit.Test;

import static org.junit.Assert.*;

public class StudentMemberTest {

    @Test
    public void getSchoolNameTest() {
        StudentMember studentMember = new StudentMember("Ashfaaq","2019394",new Date("12/07/2020"),"Zahira College");
        String schoolNameTest = "Zahira College";
        studentMember.setSchoolName(schoolNameTest);
        assertEquals(schoolNameTest,studentMember.getSchoolName());
    }
}