package net.petronika.barcode.barcode4j;

import org.krysalis.barcode4j.tools.MimeTypes;

/**
 * Enumeration for output formats used in Barcode4J.
 * 
 * @see org.krysalis.barcode4j.tools.MimeTypes
 */
public enum OutputFormat {
    FORMAT_SVG(MimeTypes.MIME_SVG),
    FORMAT_EPS(MimeTypes.MIME_EPS),
    FORMAT_TIFF(MimeTypes.MIME_TIFF),
    FORMAT_JPEG(MimeTypes.MIME_JPEG),
    FORMAT_PNG(MimeTypes.MIME_PNG),
    FORMAT_GIF(MimeTypes.MIME_GIF),
    FORMAT_BMP(MimeTypes.MIME_BMP);

	private final String mimeType;

	OutputFormat(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getMimeType() {
		return mimeType;
	}

	public boolean isBitmapFormat() {
		return MimeTypes.isBitmapFormat(mimeType);
	}
}