package com.renatodias.brisachips.Fragmants.Cadastro.Model;

public class User {

    public String fantasy_name;
    public String name;
    public String cnpj;
    public String uf_number;
    public String city_number;
    public String phone1;
    public String phone2;
    public String contact;
    public String city;
    public String neighborhood;
    public String address;

    public void User(){}

    public User(String fantasy_name, String name, String cnpj, String uf_number, String city_number, String phone1, String phone2, String contact, String city, String neighborhood, String address) {
        this.fantasy_name = fantasy_name;
        this.name = name;
        this.cnpj = cnpj;
        this.uf_number = uf_number;
        this.city_number = city_number;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.contact = contact;
        this.city = city;
        this.neighborhood = neighborhood;
        this.address = address;
    }

    public String getFantasy_name() {
        return fantasy_name;
    }

    public void setFantasy_name(String fantasy_name) {
        this.fantasy_name = fantasy_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getUf_number() {
        return uf_number;
    }

    public void setUf_number(String uf_number) {
        this.uf_number = uf_number;
    }

    public String getCity_number() {
        return city_number;
    }

    public void setCity_number(String city_number) {
        this.city_number = city_number;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
