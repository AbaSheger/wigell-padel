package com.wigell.wigellpadel.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "field_id")

    private Field field;

    @Column(length = 100)
    private String date;

    @Column(length = 100)
    private String time;

    @Column(name = "total_price_sek", length = 100)
    private Double totalPriceSEK;

    @Column(name = "total_price_euro", length = 100)
    private Double totalPriceEuro;

    @Column(name = "number_of_players", length = 100)
    private Integer numberOfPlayers;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;


    public Booking() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getTotalPriceSEK() {
        return totalPriceSEK;
    }

    public void setTotalPriceSEK(Double totalPriceSEK) {
        this.totalPriceSEK = totalPriceSEK;
    }

    public Double getTotalPriceEuro() {
        return totalPriceEuro;
    }

    public void setTotalPriceEuro(Double totalPriceEuro) {
        this.totalPriceEuro = totalPriceEuro;
    }

    public Integer getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(Integer numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}