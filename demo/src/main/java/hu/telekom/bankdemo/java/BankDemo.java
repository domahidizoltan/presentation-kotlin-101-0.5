package hu.telekom.bankdemo.java;

import hu.telekom.bankdemo.java.domain.SimpleBankService;
import hu.telekom.bankdemo.java.transport.OtpBankDto;
import hu.telekom.bankdemo.java.transport.SimpleBankDataQuery;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class BankDemo {
    public static final String OTP_FILE_PATH = "src/main/resources/otp.csv";

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();

        List<OtpBankDto> dtos = readData();
        SimpleBankService bankService = ServiceFactory.createService(dtos);
        SimpleBankDataQuery query = new SimpleBankDataQuery(bankService);

        List<OtpBankDto> results = query.getBanksWithWeekendAvailabilityAndWifi(true, true);
        results.forEach(System.out::println);
        System.out.println("Total: " + results.size());

        long end = System.currentTimeMillis();
        System.out.println("Finished in " + (end - start) + "ms");
    }

    private static List<OtpBankDto> readData() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(OTP_FILE_PATH));
        return lines.subList(1, lines.size())
            .stream()
            .filter(line -> !line.isEmpty())
            .map(BankDemo::splitLine)
            .map(OtpBankDto::parseToDto)
            .collect(toList());
    }

    private static List<String> splitLine(String line) {
        return Arrays.asList(line.substring(1, line.length()-1).split("\",\""));
    }
}
