package hu.telekom.bankdemo.java.domain;

import java.util.Map;
import java.util.Objects;

public class BankData {

    private final int id;
    private final Address address;
    private final boolean hasFreeWifi;
    private final Map<Day, String> openingHours;

    public BankData(int id, Address address, boolean hasFreeWifi, Map<Day, String> openingHours) {
        this.id = id;
        this.address = address;
        this.hasFreeWifi = hasFreeWifi;
        this.openingHours = openingHours;
    }

    public int getId() {
        return id;
    }

    public Address getAddress() {
        return address;
    }

    public boolean hasFreeWifi() {
        return hasFreeWifi;
    }

    public boolean setHasFreeWifi() {
        return hasFreeWifi;
    }

    public Map<Day, String> getOpeningHours() {
        return openingHours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankData bankData = (BankData) o;
        return id == bankData.id &&
            hasFreeWifi == bankData.hasFreeWifi &&
            address.equals(bankData.address) &&
            openingHours.equals(bankData.openingHours);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address, hasFreeWifi, openingHours);
    }

    @Override
    public String toString() {
        return "BankData{" +
            "id=" + id +
            ", address=" + address +
            ", hasFreeWifi=" + hasFreeWifi +
            ", openingHours=" + openingHours +
            '}';
    }
}
