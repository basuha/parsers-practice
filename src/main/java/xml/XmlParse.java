package xml;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.*;
import org.w3c.dom.ls.LSOutput;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XmlParse {
	// Список для сотрудников из XML файла
	private static ArrayList<Employee> employees = new ArrayList<>();

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new File("test.xml"));
		Element element = document.getDocumentElement();
		NodeList nodeList = element.getChildNodes();

		Company company = new Company();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.hasChildNodes()) {
				NodeList child = node.getChildNodes();
				System.out.println(child.getLength());
				for (int j = 0; j < child.getLength(); j++) {
					Node node1 = child.item(i);
					System.out.println(node1);
				}
			}
		}
	}
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Company {
	private String name;
	private List<Office> offices;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Office {
	private String floor;
	private String room;
	private List<Employee> employees;
}

