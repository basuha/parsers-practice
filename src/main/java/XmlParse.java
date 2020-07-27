import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class XmlParse {
	// Список для сотрудников из XML файла
	private static ArrayList<Employee> employees = new ArrayList<>();

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		// Получение фабрики, чтобы после получить билдер документов.
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		// Получили из фабрики билдер, который парсит XML, создает структуру Document в виде иерархического дерева.
		DocumentBuilder builder = factory.newDocumentBuilder();

		// Запарсили XML, создав структуру Document. Теперь у нас есть доступ ко всем элементам, каким нам нужно.
		Document document = builder.parse(new File("test.xml"));

		// Получение списка всех элементов employee внутри корневого элемента (getDocumentElement возвращает ROOT элемент XML файла).
		NodeList employeeElements = document.getDocumentElement().getElementsByTagName("employee");

		// Перебор всех элементов employee
		for (int i = 0; i < employeeElements.getLength(); i++) {
			Node employee = employeeElements.item(i);

			// Получение атрибутов каждого элемента
			NamedNodeMap attributes = employee.getAttributes();

			// Добавление сотрудника. Атрибут - тоже Node, потому нам нужно получить значение атрибута с помощью метода getNodeValue()
			employees.add(new Employee(attributes.getNamedItem("name").getNodeValue(), attributes.getNamedItem("job").getNodeValue()));
		}

		// Вывод информации о каждом сотруднике
		for (Employee employee : employees)
			System.out.println(String.format("Информации о сотруднике: имя - %s, должность - %s.", employee.getName(), employee.getJob()));
	}
}
