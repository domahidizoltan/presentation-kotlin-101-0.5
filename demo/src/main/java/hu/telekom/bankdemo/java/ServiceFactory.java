package hu.telekom.bankdemo.java;

import hu.telekom.bankdemo.java.domain.BankData;
import hu.telekom.bankdemo.java.domain.SimpleBankService;
import hu.telekom.bankdemo.java.transport.OtpBankDto;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class ServiceFactory {

    public static SimpleBankService createService(List<OtpBankDto> dtos) {
        List<BankData> entities = dtos.stream()
            .map(OtpBankDto::toEntity)
            .collect(toList());
        return new SimpleBankService(entities);
    }

}
