package mrakopedia;

import lombok.SneakyThrows;

import java.io.*;

public class NNRunner {
	@SneakyThrows
	public static void main(String[] args) throws IOException {
		String command = "python ./test.py --text=batya.txt --epochs=10 --out_len=4000 --generate";
		ProcessBuilder processBuilder = new ProcessBuilder();

		processBuilder.command("cmd.exe", "/c", "python .\\nn.py");

		Process process = processBuilder.start();

		StringBuilder output = new StringBuilder();

		BufferedReader reader = new BufferedReader(
				new InputStreamReader(process.getInputStream()));

		String line;
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
			output.append(line).append("\n");
		}

		int exitVal = process.waitFor();
		if (exitVal == 0) {
			System.out.println("Success!");
			System.out.println(output);
			System.exit(0);
		} else {
			System.err.println("error");
		}
	}
}
