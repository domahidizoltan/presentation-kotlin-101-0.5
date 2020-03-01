package hu.telekom.bankdemo.java.domain;

import java.util.Objects;

public class Address {

    private String address;
    private int zipCode;
    private String town;

    public Address(String address, int zipCode, String town) {
        this.address = address;
        this.zipCode = zipCode;
        this.town = town;
    }

    public String getAddress() {
        return address;
    }

    public int getZipCode() {
        return zipCode;
    }

    public String getTown() {
        return town;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address1 = (Address) o;
        return zipCode == address1.zipCode &&
            address.equals(address1.address) &&
            town.equals(address1.town);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, zipCode, town);
    }

    @Override
    public String toString() {
        return "Address{" +
            "address='" + address + '\'' +
            ", zipCode=" + zipCode +
            ", town='" + town + '\'' +
            '}';
    }
}
