package nl.bertkoor.process;

import nl.bertkoor.model.CustomerStatement;

import java.math.BigDecimal;

public final class CsvLineParser {

    private static final int CVS_COLUMN_COUNT = 6;

    public CustomerStatement parse(final String line) {
        String[] columns = line.split(",");
        if (columns.length != CVS_COLUMN_COUNT) {
            throw new IllegalArgumentException("CSV line should contain "
                    + CVS_COLUMN_COUNT + " elements");
        }
        if ("Reference".equals(columns[0])) {
            return null;
        }
        int c = 0;
        return CustomerStatement.builder()
                .referenceNumber(columns[c++])
                .accountNumber(columns[c++])
                .description(columns[c++])
                .startBalance(this.parseBigDec(columns[c++]))
                .mutation(this.parseBigDec(columns[c++]))
                .endBalance(this.parseBigDec(columns[c++]))
                .build();
    }

    protected BigDecimal parseBigDec(final String value) {
        return new BigDecimal(value).setScale(2);
    }
}
