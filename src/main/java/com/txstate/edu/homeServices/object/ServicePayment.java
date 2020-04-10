package com.txstate.edu.homeServices.object;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServicePayment {

    @JsonProperty("payment_id")
    private Integer paymentId;

    @JsonProperty("service_id")
    private Integer serviceId;

    @JsonProperty("card_number")
    private String cardNumber;

    @JsonProperty("card_expiry_month")
    private Integer cardExpiryMonth;

    @JsonProperty("card_expiry_year")
    private Integer cardExpiryYear;

    @JsonProperty("card_cvv")
    private String cardCVV;

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Integer getCardExpiryMonth() {
        return cardExpiryMonth;
    }

    public void setCardExpiryMonth(Integer cardExpiryMonth) {
        this.cardExpiryMonth = cardExpiryMonth;
    }

    public Integer getCardExpiryYear() {
        return cardExpiryYear;
    }

    public void setCardExpiryYear(Integer cardExpiryYear) {
        this.cardExpiryYear = cardExpiryYear;
    }

    public String getCardCVV() {
        return cardCVV;
    }

    public void setCardCVV(String cardCVV) {
        this.cardCVV = cardCVV;
    }

    @Override
    public String toString() {
        return "ServicePayment{" +
                "paymentId=" + paymentId +
                ", serviceId=" + serviceId +
                ", cardNumber='" + cardNumber + '\'' +
                ", cardExpiryMonth=" + cardExpiryMonth +
                ", cardExpiryYear=" + cardExpiryYear +
                ", cardCVV='" + cardCVV + '\'' +
                '}';
    }
}
