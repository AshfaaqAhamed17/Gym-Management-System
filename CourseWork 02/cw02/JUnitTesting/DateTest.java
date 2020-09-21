import org.junit.Test;

import static org.junit.Assert.*;

public class DateTest {

    @Test
    public void getStartMembershipDateTest() {
        Date date = new Date("20/07/2020");
        String dateTest = "20/07/2020";
        date.setStartMembershipDate(dateTest);
        assertEquals(dateTest,date.getStartMembershipDate());
    }
}