import org.junit.Test;

import static org.junit.Assert.*;

public class Over60MemberTest {

    @Test
    public void getAgeTest() {
        Over60Member over60Member = new Over60Member("Jonson","16670",new Date("12/07/2020"),"65");
        String ageTest = "65";
        over60Member.setAge(ageTest);
        assertEquals(ageTest,over60Member.getAge());
    }
}