package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.Service.AccountService;
import com.mindhub.homebanking.Service.ClientService;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController // escuchar y responder peticiones, responde de los datos del repositorio
@RequestMapping("/api")
public class ClientControllers {
    //La anotación  @Autowired le dice a Spring que cree automáticamente una instancia de  ClientRepository  y la almacene en la variable de instancia  clientRepository . Además, realiza un seguimiento de esta instancia y la usa para cualquier otra clase que tenga un ClientRepository  autoconectado .
    //Esta técnica se llama  inyección de dependencia . Dejamos que el marco cree e "inyecte" los objetos que necesitamos, en lugar de escribir una llamada al  nuevo operador. Esto permite que los objetos sean compartidos y administrados por el marco.
    @Autowired
    private ClientRepository clientRepository;

    @Autowired

    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;


    @GetMapping("/clients")                                         //una peticion asociada a una ruta. ruta como parametro
    public List<ClientDTO> getClient() {                               //se ejecuta metodo get clients para devolver una lista de clientes
        return clientService.getAllClients().stream().map(client ->
                new ClientDTO(client)).collect(Collectors.toList());    //tengo un string y lo que necesito es una coleccion. colecciono los clientesDto para pasarlo a lista
        //retorna todos los clientes en una lista
    }

    @GetMapping("clients/{id}")
    public ClientDTO getClient(@PathVariable Long id) { //lo procesa para transformarlo en un Long y asignarlo al parámetro code de la función, todo esto simplemente con indicar la anotación @PathVariable
        return new ClientDTO(clientService.getClientById(id));
    }

    int min =0;
    int max =99999999;
    public int getRandomNumber(int min,int max){return (int) ((Math.random() * (max - min)) + min);}

    public String accountString() {
        int accountNumber = getRandomNumber(min, max);
        return String.valueOf(accountNumber);
    }


    @PostMapping("/clients")

    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Debes rellenar todos los datos", HttpStatus.FORBIDDEN);
        }

        if (firstName.isEmpty()) {
            return new ResponseEntity<>("Debes ingresar tu nombre", HttpStatus.FORBIDDEN);
        }

        if (lastName.isEmpty()) {
            return new ResponseEntity<>("Debes ingresar tu apellido", HttpStatus.FORBIDDEN);
        }

        if (email.isEmpty()) {
            return new ResponseEntity<>("Debes ingresar un email", HttpStatus.FORBIDDEN);
        }

        if (password.isEmpty()) {
            return new ResponseEntity<>("Debes ingresar una contraseña", HttpStatus.FORBIDDEN);
        }

        if (clientService.findClientByEmail(email) !=  null) {
            return new ResponseEntity<>("El email ya esta registrado", HttpStatus.FORBIDDEN);
        }

        if (!email.contains("@") && !email.contains(".com")) {
            return new ResponseEntity<>("Tienes que ingresar un email valido", HttpStatus.FORBIDDEN);
        }

        if (password.length() < 9) {
            return new ResponseEntity<>("La contraseña debe ser mayor de 8 caracteres", HttpStatus.FORBIDDEN);
        }

        Client client= new Client(firstName,lastName,email,passwordEncoder.encode(password));
        clientService.saveClient(client);


        Account account = new Account("VIN" + accountString(), LocalDateTime.now(), 0, client, true, AccountType.AHORRO);
        accountService.saveAccount(account);

        return new ResponseEntity<>("Successful registration", HttpStatus.CREATED);

    }

    @GetMapping("/clients/current")

    public ClientDTO getClientDTO(Authentication authentication) {

        return new ClientDTO(clientService.findClientByEmail(authentication.getName()));

    }
}


// ClientController ya que será el controlador que administre las peticiones que se realicen sobre la entidad Client.
//STREAM() y map()
//stream envoltorio para poder realizar metodos de orden superior
//La función stream permite procesar y transformar (con map) cada elemento de la lista que retorna findAll(), map ejecuta el constructor de la clase ClientDTO y le pasa como parámetro el elemento Client que está procesando en ese momento.
//findAll() se obtine una lista
//collect(Collectors.toList())
//findById muestra cómo recuperar una entidad por su Id
