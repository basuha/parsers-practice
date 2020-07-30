package xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SAXParserExample {
	private static List<Employee> employees = new ArrayList<>();

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		MySAXHandler handler = new MySAXHandler();

		parser.parse(new File("test.xml"), handler);
		for (Employee e : employees) {
			System.out.println(e);
		}
	}

	private static class MySAXHandler extends DefaultHandler {
		@Override
		public void startDocument() throws SAXException {
			super.startDocument();
		}

		@Override
		public void endDocument() throws SAXException {
			System.out.println("end");
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) {
			if (qName.equals("employee")) {
				String name = attributes.getValue("name");
				String job = attributes.getValue("job");
				employees.add(new Employee(name,job));
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			super.endElement(uri, localName, qName);
		}

		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			super.characters(ch, start, length);
		}
	}
}

