package hu.telekom.bankdemo.java.transport;

import hu.telekom.bankdemo.java.domain.Address;
import hu.telekom.bankdemo.java.domain.BankData;
import hu.telekom.bankdemo.java.domain.Day;

import java.util.*;

import static hu.telekom.bankdemo.java.domain.Day.*;
import static hu.telekom.bankdemo.java.transport.DtoFields.*;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class OtpBankDto {

    private final String id;
    private String type;
    private final String town;
    private final String zipCode;
    private final String address;
    private String lat;
    private String _long;
    private List<String> openingHours;
    private List<String> specialServices;

    public OtpBankDto(String id, String town, String zipCode, String address) {
        this.id = id;
        this.town = town;
        this.zipCode = zipCode;
        this.address = address;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTown() {
        return town;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getAddress() {
        return address;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLong(String _long) {
        this._long = _long;
    }

    public List<String> getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(List<String> openingHours) {
        this.openingHours = openingHours;
    }

    public List<String> getSpecialServices() {
        return specialServices;
    }

    public void setSpecialServices(List<String> specialServices) {
        this.specialServices = specialServices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OtpBankDto that = (OtpBankDto) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(type, that.type) &&
            Objects.equals(town, that.town) &&
            Objects.equals(zipCode, that.zipCode) &&
            Objects.equals(address, that.address) &&
            Objects.equals(lat, that.lat) &&
            Objects.equals(_long, that._long) &&
            Objects.equals(openingHours, that.openingHours) &&
            Objects.equals(specialServices, that.specialServices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, town, zipCode, address, lat, _long, openingHours, specialServices);
    }

    @Override
    public String toString() {
        return "OtpBankDto{" +
            "id='" + id + '\'' +
            ", type='" + type + '\'' +
            ", town='" + town + '\'' +
            ", zipCode='" + zipCode + '\'' +
            ", address='" + address + '\'' +
            ", lat='" + lat + '\'' +
            ", _long='" + _long + '\'' +
            ", openingHours=" + openingHours +
            ", specialServices=" + specialServices +
            '}';
    }

    public static OtpBankDto parseToDto(List<String> fields) {
        String id = fields.get(ID.idx);
        String town = fields.get(TOWN.idx);
        String zipCode = fields.get(ZIP_CODE.idx);
        String address = fields.get(ADDRESS.idx);
        String type = fields.get(TYPE.idx);
        String lat = fields.get(LAT.idx);
        String _long = fields.get(LONG.idx);
        List<String> openingHours = parseOpeningHours(fields);
        List<String> specialServices = parseSpecialServices(fields);

        OtpBankDto dto = new OtpBankDto(id, town, zipCode, address);
        dto.setType(type);
        dto.setLat(lat);
        dto.setLong(_long);
        dto.setOpeningHours(openingHours);
        dto.setSpecialServices(specialServices);
        return dto;
    }

    public static OtpBankDto toDto(BankData bank) {
        String id = String.valueOf(bank.getId());
        String town = bank.getAddress().getTown();
        String zipCode = String.valueOf(bank.getAddress().getZipCode());
        String address = bank.getAddress().getAddress();

        List<String> specialServices = new ArrayList<>();
        if (bank.setHasFreeWifi()) specialServices.add("Free Wifi");

        List<String> openingHours = bank.getOpeningHours()
            .entrySet().stream()
            .map(entry -> entry.getKey() + "=" + entry.getValue())
            .collect(toList());

        OtpBankDto dto = new OtpBankDto(id, town, zipCode, address);
        dto.setOpeningHours(openingHours);
        dto.setSpecialServices(specialServices);

        return dto;
    }

    public static BankData toEntity(OtpBankDto dto) {
        int id = Integer.parseInt(dto.id);
        Address address = new Address(dto.getAddress(), Integer.parseInt(dto.getZipCode()), dto.getTown());
        boolean hasFreeWifi = dto.getSpecialServices().contains("Free Wifi");
        Map<Day, String> openingHours = toOpeningHoursMap(dto.getOpeningHours());
        return new BankData(id, address, hasFreeWifi, openingHours);
    }

    private static Map<Day, String> toOpeningHoursMap(List<String> hours) {
        Map<Day, String> map = new HashMap<>();
        map.put(MONDAY, hours.get(0));
        map.put(TUESDAY, hours.get(1));
        map.put(WEDNESDAY, hours.get(2));
        map.put(THURSDAY, hours.get(3));
        map.put(FRIDAY, hours.get(4));
        map.put(SATURDAY, hours.get(5));
        map.put(SUNDAY, hours.get(6));

        return map.entrySet().stream()
            .filter(entry -> !entry.getValue().isEmpty())
            .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static List<String> parseOpeningHours(List<String> fields) {
        return Arrays.asList(
            fields.get(OPENING_HOURS_001.idx),
            fields.get(OPENING_HOURS_002.idx),
            fields.get(OPENING_HOURS_003.idx),
            fields.get(OPENING_HOURS_004.idx),
            fields.get(OPENING_HOURS_005.idx),
            fields.get(OPENING_HOURS_006.idx),
            fields.get(OPENING_HOURS_007.idx)
        );
    }

    private static List<String> parseSpecialServices(List<String> fields) {
        return Arrays.asList(
            fields.get(SPECIAL_SERVICES_001.idx),
            fields.get(SPECIAL_SERVICES_002.idx),
            fields.get(SPECIAL_SERVICES_003.idx),
            fields.get(SPECIAL_SERVICES_004.idx)
        );
    }

}
