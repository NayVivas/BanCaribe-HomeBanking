package com.mindhub.homebanking.Service;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Card;
import java.util.List;

public interface CardService {

    public List<Card> getAllCards();

    Card getCardById(Long id);

    Card getCardByNumber(String number);

    void saveCard(Card card);
}
