package mrakopedia;

import lombok.SneakyThrows;

import java.io.*;
import java.util.List;

public class Main {
	@SneakyThrows
	public static void main(String[] args) {
		MrakopediaParser parser = new MrakopediaParser(1000);
//		OutputStream outputStream = new FileOutputStream("mrako_r_500.txt");
//
//		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
//
//		for (String story : parser.getStoriesList()) {
//			writer.write(story);
//			writer.flush();
//		}
//		writer.close();

		System.out.println(parser.testLink("https://mrakopedia.net/w/api.php?action=query&format=json&list=random&rnnamespace=0&rnlimit=10"));
	}
}
