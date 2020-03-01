package hu.telekom.bankdemo.java.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static hu.telekom.bankdemo.java.domain.Availability.*;
import static hu.telekom.bankdemo.java.domain.Day.SATURDAY;
import static hu.telekom.bankdemo.java.domain.Day.SUNDAY;
import static java.util.stream.Collectors.*;

public class SimpleBankService implements BankService<BankData> {

    private List<BankData> banks;

    public SimpleBankService(List<BankData> banks) {
        this.banks = banks;
    }

    @Override
    public Map<Availability, List<BankData>> getBanksByAvailability() {
        return banks.stream()
            .collect(groupingBy(this::getAvailability, mapping(Function.identity(), toList())));
    }

    private Availability getAvailability(BankData bank) {
        Set<Day> openingDays = bank.getOpeningHours().keySet();
        Availability availability = NORMAL;

        if (openingDays.size() < 5) { availability = REDUCED; }
        else if (openingDays.containsAll(Arrays.asList(SATURDAY, SUNDAY))) { availability = ALLDAY; }
        else if (openingDays.contains(SATURDAY)) { availability = INCREASED; }

        return availability;
    }

}
