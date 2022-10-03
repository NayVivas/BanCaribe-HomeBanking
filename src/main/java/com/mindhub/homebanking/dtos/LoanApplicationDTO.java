package com.mindhub.homebanking.dtos;

import java.util.List;

public class LoanApplicationDTO {

    private Long id;

    private double amount;

    private int payments;

    private String destinyNumberAccount;

    private String loanName;

    private double maxAmount;

    private double percentage;

    private List<Integer> listPayments;

    public LoanApplicationDTO(Long id, double amount, int payments, String destinyNumberAccount, String loanName, double maxAmount, double percentage, List<Integer> listPayments) {
        this.id = id;
        this.amount = amount;
        this.payments = payments;
        this.destinyNumberAccount = destinyNumberAccount;
        this.loanName = loanName;
        this.maxAmount = maxAmount;
        this.percentage = percentage;
        this.listPayments = listPayments;
    }

    public Long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }
    public int getPayments() {
        return payments;
    }
    public String getDestinyNumberAccount() {
        return destinyNumberAccount;
    }

    public String getLoanName() {
        return loanName;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public double getPercentage() {
        return percentage;
    }

    public List<Integer> getListPayments() {
        return listPayments;
    }
}
