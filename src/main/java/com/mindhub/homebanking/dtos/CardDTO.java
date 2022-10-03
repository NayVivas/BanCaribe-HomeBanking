package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import java.time.LocalDateTime;

public class CardDTO {

    private Long Id;

    private String cardholder;

    private CardType type;

    private CardColor typeColor;

    private String number;

    private int cvv;

    private LocalDateTime thrudate;

    private LocalDateTime fromdate;

    private Boolean cardActive = true;

    public CardDTO() {
    }

    public CardDTO(Card card) {
        this.Id = card.getId();
        this.cardholder = card.getCardholder();
        this.type = card.getType();
        this.typeColor = card.getTypeColor();
        this.number = card.getNumber();
        this.cvv = card.getCvv();
        this.thrudate = card.getThrudate();
        this.fromdate = card.getFromdate();
        this.cardActive = card.getCardActive();
    }

    public Long getId() {
        return Id;
    }

    public String getCardholder() {
        return cardholder;
    }

    public CardType getType() {
        return type;
    }

    public CardColor getTypeColor() {
        return typeColor;
    }

    public String getNumber() {
        return number;
    }

    public int getCvv() {
        return cvv;
    }

    public LocalDateTime getThrudate() {
        return thrudate;
    }

    public LocalDateTime getFromdate() {
        return fromdate;
    }

    public Boolean getCardActive() {
        return cardActive;
    }
}
