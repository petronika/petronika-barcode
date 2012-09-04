package net.petronika.barcode.barcode4j;

import java.io.File;
import java.io.OutputStream;
import java.io.FileOutputStream;

import org.junit.Test;

public class Barcode4jBuilderTest {

	@Test
	public void testBuilder() throws Exception {
		OutputStream output = null;

		output = new FileOutputStream(new File("barcode-1.png"));
		new Barcode4jBuilder()
			.render("1111111111", output);
		output.close();

		output = new FileOutputStream(new File("barcode-2.png"));
		new Barcode4jBuilder()
			.type(Barcode4jBuilder.DEFAULT_TYPE)
			.format(Barcode4jBuilder.DEFAULT_FORMAT)
			.render("2222222222", output);
		output.close();

		output = new FileOutputStream(new File("barcode-3.png"));
		new Barcode4jBuilder()
			.quietZone(Barcode4jBuilder.DISABLE_QUIET_ZONE)
			.render("3333333333", output);
		output.close();

		output = new FileOutputStream(new File("barcode-4.png"));
		new Barcode4jBuilder()
			.humanReadablePlacement(HumanReadablePlacement.HRP_NONE)
			.render("4444444444", output);
		output.close();

		output = new FileOutputStream(new File("barcode-5.png"));
		new Barcode4jBuilder()
			.humanReadablePlacement(HumanReadablePlacement.HRP_NONE)
			.moduleWidth("0.5mm")
			.render("5555555555", output);
		output.close();

		output = new FileOutputStream(new File("barcode-6.png"));
		new Barcode4jBuilder()
			.type(BarcodeType.BARCODE_DATAMATRIX)
			.render("6666666666", output);
		output.close();

		output = new FileOutputStream(new File("barcode.png"));
		new Barcode4jBuilder()
			.type(Barcode4jBuilder.DEFAULT_TYPE)
			.format(Barcode4jBuilder.DEFAULT_FORMAT)
			.grayscale(true)
			.resolution(Barcode4jBuilder.RESOLUTION_DEFAULT)
			.height("3.0cm")
			.moduleWidth("0.5mm")
			.wideFactor("2")
			.quietZone("1cm")
			.humanReadablePlacement(HumanReadablePlacement.HRP_BOTTOM)
			.humanReadableSize("10pt")
			.humanReadableFont("Helvetica")
			.humanReadablePattern("__-__-__-__-__")
			.render("0123456789", output);
		output.close();
	}
}