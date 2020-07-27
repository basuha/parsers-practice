package icons;

import lombok.SneakyThrows;
import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.*;
import java.nio.Buffer;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IconToData {
	@SneakyThrows
	public static void main(String[] args) {
//		File[] files = new File("png").listFiles();
//		OutputStream outputStream = new FileOutputStream("icons.txt");
//		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
//
//
//		writer.flush();
//
//		for (File f : files) {
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			ImageIO.write(scale(ImageIO.read(f), 10, 10), "png", baos);
//			baos.flush();
//			writer.write("<image>");
//			for (byte b : baos.toByteArray()) {
//				writer.write(b);
//			}
//			writer.write("</image>");
//			writer.flush();
//			baos.close();
//		}
//		writer.close();
		recover();
	}

	public static void recover() throws IOException {
		List<Byte[]> images = new ArrayList<>();
		InputStream reader = new FileInputStream("icons.txt");
		BufferedReader buf = new BufferedReader(new InputStreamReader(reader));
		String nextLine;

		while ((nextLine = buf.readLine()) != null) {

		}
		Pattern pattern = Pattern.compile("<image>(.+?)</image>");
		Matcher matcher = pattern.matcher(nextLine);

		int counter = 0;
		while (matcher.find()) {
			String image = matcher.group(1);
			System.out.println(image);
			System.out.println(Arrays.toString(image.getBytes()));
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(image.getBytes());
			ImageIO.write(ImageIO.read(byteArrayInputStream), "png", new File("result/" + counter++ + ".png"));
		}
	}

	public static BufferedImage scale(BufferedImage imageToScale, int dWidth, int dHeight) {
		BufferedImage scaledImage = null;
		if (imageToScale != null) {
			scaledImage = new BufferedImage(dWidth, dHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics2D graphics2D = scaledImage.createGraphics();
			graphics2D.drawImage(imageToScale, 0, 0, dWidth, dHeight, null);
			graphics2D.dispose();
		}
		return scaledImage;
	}
}
