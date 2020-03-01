package hu.telekom.bankdemo.java.transport;

import java.util.List;

public interface BankDataQuery<T> {

    List<T> getBanksWithWeekendAvailabilityAndWifi(boolean isOpenAtWeekend, boolean hasFreeWifi);

}
