package net.petronika.barcode.barcode4j;

/**
 * Enumeration for barcode type.
 * <p/>
 * For a list of barcode type names can be used the next call:
 * {@link org.krysalis.barcode4j.BarcodeUtil}.getInstance().getClassResolver().getBarcodeNames()
 */
public enum BarcodeType {
	BARCODE_CODABAR("codabar"),
	BARCODE_CODE39("code39"),
	BARCODE_CODE128("code128"),
	BARCODE_DATAMATRIX("datamatrix"),
	BARCODE_EAN8("ean-8"),
	BARCODE_EAN13("ean-13"),
	BARCODE_EAN128("ean-128"),
	BARCODE_INTL2OF5("intl2of5"),
	BARCODE_ITF14("itf-14"),
	BARCODE_PDF417("pdf417"),
	BARCODE_POSTNET("postnet"),
	BARCODE_ROYAL_MAIL_CBC("royal-mail-cbc"),
	BARCODE_UPC_A("upc-a"),
	BARCODE_UPC_E("upc-e"),
	BARCODE_USPS4CB("usps4cb");

	private final String name;

	BarcodeType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}