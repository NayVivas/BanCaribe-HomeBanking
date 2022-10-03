package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.Service.CardService;
import com.mindhub.homebanking.Service.ClientService;
import com.mindhub.homebanking.Utils.CardUtils;
import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class CardControllers {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CardService cardService;

    @Autowired
    private ClientService clientService;

    @GetMapping("/cards")

    public List<CardDTO> getCard() {
        return cardService.getAllCards().stream().map(card ->
                new CardDTO(card)).collect(Collectors.toList());
    }

    @GetMapping("cards/{id}")
    public CardDTO getCard(@PathVariable Long id) {
        return new CardDTO(cardService.getCardById(id));
    }


    @PostMapping("clients/current/cards")

    public ResponseEntity<Object> createCard(Authentication authentication,
                                             @RequestParam CardType cardType,
                                             @RequestParam CardColor cardColor) {

        Client client = clientService.findClientByEmail(authentication.getName());

        List<Card> listCard = client.getCard().stream().filter(card -> (card.getType() == cardType && card.getTypeColor() == cardColor)).collect(Collectors.toList());

        if (listCard.size() > 0) {
            return new ResponseEntity<>("No puedes solicitar mas tarejtas de este tipo", HttpStatus.FORBIDDEN);
        }

        LocalDateTime thruDate = LocalDateTime.now();
        LocalDateTime fromDate = thruDate.plusYears(5);

        String cardNumber = CardUtils.getCardNumber();
        int cvvNumber = CardUtils.getCvvNumber();


        Card card = new Card(client.getFirstName() + "" + client.getLastName(), cardType, cardColor, cardNumber, cvvNumber, fromDate, thruDate, client, true);
        cardService.saveCard(card);
        clientService.saveClient(client);
        return new ResponseEntity<>("Tarjeta creada", HttpStatus.CREATED);
    }

    @PatchMapping("/clients/current/cards/delete/{id}")
    public ResponseEntity<Object> cardDelete(
            Authentication authentication,
            @PathVariable Long id) {

        Client client = clientService.findClientByEmail(authentication.getName());
        Card card = cardService.getCardById(id);

        card.setCardActive(false);
        cardService.saveCard(card);

        return new ResponseEntity<>("Modificación exitoso", HttpStatus.CREATED);
    }
}
