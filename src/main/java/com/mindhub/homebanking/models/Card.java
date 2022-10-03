package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long Id;

    private String cardholder;

    private CardType type;

    private CardColor typeColor;

    private String number;

    private int cvv;

    private LocalDateTime thrudate;

    private LocalDateTime fromdate;

    private Boolean cardActive = true;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    public Card() {
    }

    public Card(String cardholder, CardType type, CardColor typeColor, String number, int cvv, LocalDateTime thrudate, LocalDateTime fromdate, Client client, boolean cardActive) {
        this.cardholder = cardholder;
        this.type = type;
        this.typeColor = typeColor;
        this.number = number;
        this.cvv = cvv;
        this.thrudate = thrudate;
        this.fromdate = fromdate;
        this.client = client;
        this.cardActive = cardActive;
    }

    public Long getId() {
        return Id;
    }

    public String getCardholder() {
        return cardholder;
    }
    public void setCardholder(String cardholder) {
        this.cardholder = cardholder;
    }

    public CardType getType() {
        return type;
    }
    public void setType(CardType type) {
        this.type = type;
    }

    public CardColor getTypeColor() {
        return typeColor;
    }
    public void setTypeColor(CardColor typeColor) {
        this.typeColor = typeColor;
    }

    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }

    public int getCvv() {
        return cvv;
    }
    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public LocalDateTime getThrudate() {
        return thrudate;
    }
    public void setThrudate(LocalDateTime thrudate) {
        this.thrudate = thrudate;
    }

    public LocalDateTime getFromdate() {
        return fromdate;
    }
    public void setFromdate(LocalDateTime fromdate) {
        this.fromdate = fromdate;
    }

    @JsonIgnore //Jackson transforma los datos a json
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }

    public Boolean getCardActive() {
        return cardActive;
    }
    public void setCardActive(Boolean cardActive) {
        this.cardActive = cardActive;
    }
}
