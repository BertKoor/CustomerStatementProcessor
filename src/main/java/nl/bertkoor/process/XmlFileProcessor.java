package nl.bertkoor.process;

import noNamespace.CustomerStatementType;
import noNamespace.RecordsDocument;
import nl.bertkoor.model.CustomerStatement;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class XmlFileProcessor extends FileProcessor {

    private XmlStatementParser parser = new XmlStatementParser();

    public XmlFileProcessor(final PrintStream printStream) {
        super(printStream);
    }

    public void process(final InputStream stream) {
        try {
            ArrayList validationErrors = new ArrayList();
            XmlOptions options = buildOptions(validationErrors);
            RecordsDocument document = RecordsDocument.Factory.parse(stream, options);
            for (CustomerStatementType statement: document.getRecords().getRecordArray()) {
                this.processStatement(statement);
            }
        } catch (XmlException | IOException e) {
            e.printStackTrace();
        } finally {
            this.endReport();
        }
    }

    private XmlOptions buildOptions(ArrayList validationErrors) {
        XmlOptions options = new XmlOptions();
//        Map<String, String> nsMap = new HashMap<>();
//        nsMap.put("", "http://www.rabobank.com");
//        options.setLoadSubstituteNamespaces(nsMap);
        options.setErrorListener(validationErrors);
        return options;
    }

    @Override
    protected CustomerStatement parseStatement(Object value) {
        return this.parser.parse((CustomerStatementType)value);
    }

}
