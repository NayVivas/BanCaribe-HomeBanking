package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity //Crea una tabla en la base de datos
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    private String name;

    private double maxAmount;

    private double interesPercentage;


    //cuando trabajas con datos simples por ejemplo una lista de números, cadenas, booleanos y demás, solo tienes que utilizar la anotación @EllementCollection y Spring se encargará de crear esa entidad y configurar la relación de uno a muchos automáticamente.
    @ElementCollection
    //@ElementCollection se usa para crear listas de  objetos incrustables  . Un objeto incrustable son datos destinados a usarse solo en el objeto que lo contiene
    @Column(name="payment")
    private List<Integer> payments = new ArrayList<>();

    @OneToMany(mappedBy = "loan",fetch = FetchType.EAGER)
    Set<ClientLoan> clientLoans = new HashSet<>();

    public Loan() {
    }
    public Loan(String name, double maxAmount, List<Integer> payments, double interesPercentage) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
        this.interesPercentage = interesPercentage;
    }

    public Loan(String name, double maxAmount, List<Integer> payments, Set<ClientLoan> clientLoans) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
        this.clientLoans = clientLoans;
    }

    public Loan(String loanName, double maxAmount, double percentage, List<Integer> listPayments) {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public double getMaxAmount() {
        return maxAmount;
    }
    public void setMaxAmount(double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }
    public void setPayments(List<Integer> payments) {
        this.payments = payments;
    }

    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }

    public void setClientLoans(Set<ClientLoan> clientLoans) {
        this.clientLoans = clientLoans;
    }

    public void addClientLoan(ClientLoan clientLoan) {
        clientLoan.setLoan(this);
        clientLoans.add(clientLoan);
    }

    public double getInteresPercentage() {
        return interesPercentage;
    }

    public void setInteresPercentage(double interesPercentage) {
        this.interesPercentage = interesPercentage;
    }
}
