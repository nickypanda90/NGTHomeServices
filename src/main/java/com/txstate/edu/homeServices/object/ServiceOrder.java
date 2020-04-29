package com.txstate.edu.homeServices.object;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServiceOrder {

    @JsonProperty("service_id")
    private Integer serviceId;

    @JsonProperty("customer_id")
    private Integer customerId;

    @JsonProperty("business_id")
    private Integer businessId;

    @JsonProperty("service_date_time")
    private String serviceDateTime;

    @JsonProperty("service_description")
    private String serviceDescription;

    @JsonProperty("service_category")
    private String serviceCategory;

    private String status;

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

    public String getServiceDateTime() {
        return serviceDateTime;
    }

    public void setServiceDateTime(String serviceDateTime) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ServiceOrder{" +
                "serviceId=" + serviceId +
                ", customerId=" + customerId +
                ", businessId=" + businessId +
                ", serviceDateTime='" + serviceDateTime + '\'' +
                ", serviceDescription='" + serviceDescription + '\'' +
                ", serviceCategory='" + serviceCategory + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
