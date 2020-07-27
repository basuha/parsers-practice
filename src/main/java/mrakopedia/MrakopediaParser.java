package mrakopedia;

import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MrakopediaParser {
	private static final String STORY_URL = "https://mrakopedia.net/w/index.php?title=%s&printable=yes";
	private static final String PAGES_LIST_URL = "https://mrakopedia.net/w/api.php?action=query&format=json&list=random&rnnamespace=0&rnlimit=%s";
	private int pagesListLimit = 10;
	private final List<String> pagesList = new ArrayList<>();
	private final List<String> stories = new ArrayList<>();

	public MrakopediaParser() {
	}

	public MrakopediaParser(int pagesListLimit) {
		this.pagesListLimit = pagesListLimit;
	}

	@SneakyThrows
	public String testLink(String link) {
		URL url = new URL(link);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.connect();
		if (connection.getResponseCode() != 200)
			return "";

		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		StringBuilder response = new StringBuilder();

		String inputLine;
		while ((inputLine = in.readLine()) != null)
			response.append(inputLine);
		in.close();
		return response.toString();
	}

	@SneakyThrows
	private String getStoryRawText(String storyTitle) {
		URL url = new URL(String.format(STORY_URL, storyTitle));
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.connect();
		if (connection.getResponseCode() != 200)
			return "";

		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		StringBuilder response = new StringBuilder();

		String inputLine;
		while ((inputLine = in.readLine()) != null)
			response.append(inputLine);
		in.close();

		Pattern textPattern = Pattern.compile("<p>(.+?)</p>");
		Matcher matcher = textPattern.matcher(response.toString());

		StringBuilder storyText = new StringBuilder()
				.append("\n")
				.append("******")
				.append("\n")
				.append(storyTitle.replaceAll("_", " "))
				.append("\n")
				.append("******")
				.append("\n");

		while (matcher.find()) {
			String line = matcher.group(1);
			if (!line.contains("<img"))
				storyText.append(line.replaceAll("<.+?>", " "))
						.append("\n");
		}
		return storyText.toString();
	}

	@SneakyThrows
	private void getPagesList() {
		URL url = new URL(String.format(PAGES_LIST_URL, pagesListLimit));

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");

		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		JSONObject jsonResponse = new JSONObject(in.readLine());
		JSONObject query = jsonResponse.getJSONObject("query");
		JSONArray pages = query.getJSONArray("random");

		in.close();

		for(Object p: pages)
			pagesList.add(((JSONObject)p)
							.get("title")
							.toString()
							.replaceAll("\\.{3}", "")
							.replaceAll(" ", "_"));
	}

	public List<String> getStoriesList() {
		if (!stories.isEmpty())
			return stories;

		if (pagesList.isEmpty())
			getPagesList();

		int counter = 0;
		for (String s : pagesList) {
			String story = getStoryRawText(s);
			if (!story.isEmpty()) {
				stories.add(story);
				System.out.println("(" + ++counter + ")" + "story: [" + s + "] parsed successfully");
			} else {
				System.err.println("story: [" + s + "] returned 404");
			}
		}
		return stories;
	}
}
