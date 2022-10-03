package com.mindhub.homebanking;

import com.mindhub.homebanking.Utils.CardUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class CardUtilsTests {

    @Test
    public void cardNumberIsCreated(){
        String cardNumber = CardUtils.getCardNumber();
        assertThat(cardNumber,is(not(emptyOrNullString())));
    }

    @Test
    public void cardNumberIsStartWith(){
        String cardNumber = CardUtils.getCardNumber();
        assertThat(cardNumber,is(not(startsWith("-"))));
    }

    @Test
    public void cvvNumberLessThan() {
        int cvvNumber = CardUtils.getCvvNumber();
        assertThat(cvvNumber, greaterThan(100));
    }

    @Test
    public void cvvNumberNullValue() {
        int cvvNumber = CardUtils.getCvvNumber();
        assertThat(cvvNumber, notNullValue());
    }
}
