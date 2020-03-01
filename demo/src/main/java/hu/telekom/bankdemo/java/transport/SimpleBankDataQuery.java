package hu.telekom.bankdemo.java.transport;

import hu.telekom.bankdemo.java.domain.Availability;
import hu.telekom.bankdemo.java.domain.SimpleBankService;

import java.util.List;
import java.util.Map;

import static hu.telekom.bankdemo.java.domain.Availability.*;
import static java.util.stream.Collectors.toList;

public class SimpleBankDataQuery implements BankDataQuery<OtpBankDto> {

    private SimpleBankService bankService;

    public SimpleBankDataQuery(SimpleBankService bankService) {
        this.bankService = bankService;
    }

    @Override
    public List<OtpBankDto> getBanksWithWeekendAvailabilityAndWifi(boolean isOpenAtWeekend, boolean hasFreeWifi) {
        return bankService.getBanksByAvailability()
            .entrySet().stream()
            .filter(entry -> collectByWeekDayType(isOpenAtWeekend, entry.getKey()))
            .map(Map.Entry::getValue)
            .flatMap(List::stream)
            .filter(bank -> bank.hasFreeWifi() != hasFreeWifi)
            .map(OtpBankDto::toDto)
            .collect(toList());
    }

    private boolean collectByWeekDayType(boolean isOpenAtWeekend, Availability key) {
        if (isOpenAtWeekend) {
            return key == INCREASED || key == ALLDAY;
        } else {
            return key == REDUCED || key == NORMAL;
        }
    }

}
