package nl.bertkoor.process;

import nl.bertkoor.model.CustomerStatement;

import java.math.BigDecimal;

public class CsvLineParser {

    public CustomerStatement parse(final String line) {
        String[] columns = line.split(",");
        if (columns.length != 6) {
            throw new IllegalArgumentException("CSV line should contain 6 elements");
        }
        return ("Reference".equals(columns[0])) ? null :
                CustomerStatement.builder()
                        .referenceNumber(columns[0])
                        .accountNumber(columns[1])
                        .description(columns[2])
                        .startBalance(this.parseBigDec(columns[3]))
                        .mutation(this.parseBigDec(columns[4]))
                        .endBalance(this.parseBigDec(columns[5]))
                        .build();
    }

    protected BigDecimal parseBigDec(String value) {
        return new BigDecimal(value).setScale(2);
    }
}
