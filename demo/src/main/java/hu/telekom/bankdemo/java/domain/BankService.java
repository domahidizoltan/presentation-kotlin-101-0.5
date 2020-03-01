package hu.telekom.bankdemo.java.domain;

import java.util.List;
import java.util.Map;

public interface BankService<T> {

    Map<Availability, List<T>> getBanksByAvailability();

}
