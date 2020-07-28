package com.txstate.edu.homeServices.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "Service_Type_List")

public class ServiceCategory {
    public String getService_Type() {
        return Service_Type;
    }

    public void setService_Type(String service_Type) {
        Service_Type = service_Type;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private String  Service_Type;




}
