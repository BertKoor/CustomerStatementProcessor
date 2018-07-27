package nl.bertkoor.process;

import nl.bertkoor.model.CustomerStatement;
import org.junit.Test;

import java.math.BigDecimal;
import static junit.framework.TestCase.fail;

import static org.assertj.core.api.Assertions.assertThat;

public class CsvLineParserTest {

    private CsvLineParser sut = new CsvLineParser();

    @Test
    public void assertThatCsvHeadingParsesNull() {
        assertThat(sut.parse("Reference,foo,bar,code,kata,comb")).isNull();
    }

    @Test
    public void assertThatValidStatementLineParsesCorrect() {
        CustomerStatement statement = sut.parse("foo,bar,code,+1,-2.34,5.6789E+2");
        assertThat(statement).isEqualTo(CustomerStatement.builder()
                .referenceNumber("foo")
                .accountNumber("bar")
                .description("code")
                .startBalance(BigDecimal.ONE.setScale(2))
                .mutation(BigDecimal.valueOf(-2.34))
                .endBalance(BigDecimal.valueOf(567.89))
                .build());
    }

    @Test
    public void assertThatShortLineThrowsException() {
        try {
            sut.parse("foo,bar,code,1,2");
            fail();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("CSV line should contain 6 elements");
        }
    }

    @Test
    public void assertThatLongLineThrowsException() {
        try {
            sut.parse("foo,bar,code,1,2,3,4");
            fail();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("CSV line should contain 6 elements");
        }
    }

    @Test(expected = NumberFormatException.class)
    public void assertThatIllegalDecimalDataThrowsException() {
        sut.parse("ref,foo,bar,code,kata,comb");
    }
}