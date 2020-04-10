package com.txstate.edu.homeServices.object;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class ServiceOrder {

    @JsonProperty("service_id")
    private Integer serviceId;

    @JsonProperty("customer_id")
    private Integer customerId;

    @JsonProperty("business_id")
    private Integer businessId;

    @JsonProperty("service_date_time")
    private LocalDateTime serviceDateTime;

    @JsonProperty("service_description")
    private String serviceDescription;

    @JsonProperty("service_category")
    private String serviceCategory;

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public LocalDateTime getServiceDateTime() {
        return serviceDateTime;
    }

    public void setServiceDateTime(LocalDateTime serviceDateTime) {
        this.serviceDateTime = serviceDateTime;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public String getServiceCategory() {
        return serviceCategory;
    }

    public void setServiceCategory(String serviceCategory) {
        this.serviceCategory = serviceCategory;
    }

    @Override
    public String toString() {
        return "ServiceOrder{" +
                "serviceId=" + serviceId +
                ", customerId=" + customerId +
                ", businessId=" + businessId +
                ", serviceDateTime=" + serviceDateTime +
                ", serviceDescription='" + serviceDescription + '\'' +
                ", serviceCategory='" + serviceCategory + '\'' +
                '}';
    }
}
