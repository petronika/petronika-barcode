package net.petronika.barcode.barcode4j;

import java.io.OutputStream;

import java.awt.image.BufferedImage;

import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.DefaultConfiguration;

import org.krysalis.barcode4j.BarcodeGenerator;
import org.krysalis.barcode4j.BarcodeUtil;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.output.eps.EPSCanvasProvider;

/**
 * <a href="http://barcode4j.sourceforge.net/">Barcode4j</a> wrapper for rendering barcodes.
 * <p/>
 * The code inspired by {@link org.krysalis.barcode4j.servlet.BarcodeServlet}.
 */
public class Barcode4jBuilder {

	public static final BarcodeType DEFAULT_TYPE = BarcodeType.BARCODE_CODE128;
	public static final OutputFormat DEFAULT_FORMAT = OutputFormat.FORMAT_PNG;

    public static final int RESOLUTION_MIN = 10;
    public static final int RESOLUTION_MAX = 2400;
    public static final int RESOLUTION_DEFAULT  = 300;

    public static final String DISABLE_QUIET_ZONE = "disable";

	private BarcodeType type = DEFAULT_TYPE;
	private OutputFormat format = DEFAULT_FORMAT;
	private boolean grayscale = true;
	private int resolution = RESOLUTION_DEFAULT;
	private String height = null;
	private String moduleWidth = null;
	private String wideFactor = null;
	private String quietZone = null;
    private HumanReadablePlacement humanReadablePlacement = null;
    private String humanReadableSize = null;
    private String humanReadableFont = null;
    private String humanReadablePattern = null;

	/**
	 * Renders the barcode
	 * 
	 * @param value Barcode value 
	 * @param out OutputStream to write to
	 */
	public void render(String value, OutputStream out) throws Exception {
		if ( value == null || value.isEmpty() ) {
			throw new IllegalArgumentException("value");
		}
		if ( out == null ) {
			throw new IllegalArgumentException("out");
		}

        if ( type == null ) {
            throw new IllegalStateException("Barcode type is not specified");
        }
        if ( format == null ) {
            throw new IllegalStateException("Output format is not specified");
        }

		Configuration config = buildConfig();

		final int orientation = 0;

		BarcodeGenerator gen = BarcodeUtil.getInstance().createBarcodeGenerator(config);

		if ( format == OutputFormat.FORMAT_SVG ) {
			throw new UnsupportedOperationException("SVG output format is unsupported");
		}
		else if ( format == OutputFormat.FORMAT_EPS ) {
			EPSCanvasProvider eps = new EPSCanvasProvider(out, orientation);
			gen.generateBarcode(eps, value);
			eps.finish();
        }
		else {
            if ( resolution < RESOLUTION_MIN ) {
                throw new IllegalStateException("Minimum resolution must be 10dpi");
            }
            if ( resolution > RESOLUTION_MAX ) {
                throw new IllegalStateException("Resolutions above 2400dpi are not allowed");
            }
            BitmapCanvasProvider bitmap = grayscale
            		? new BitmapCanvasProvider(
							out, format.getMimeType(), resolution,
							BufferedImage.TYPE_BYTE_GRAY, true, orientation)
            		: new BitmapCanvasProvider(
            				out, format.getMimeType(), resolution,
							BufferedImage.TYPE_BYTE_BINARY, false, orientation);
			gen.generateBarcode(bitmap, value);
			bitmap.finish();
		}
	}

	/**
	 * Barcode type
	 */
	public Barcode4jBuilder type(BarcodeType type) {
		this.type = type;
		return this;
	}

	/**
	 * Output format
	 */
	public Barcode4jBuilder format(OutputFormat format) {
		this.format = format;
		return this;
	}

	/**
	 * Grayscale. Applies to bitmap formats only (JPEG, PNG etc.).
	 */
	public Barcode4jBuilder grayscale(boolean grayscale) {
		this.grayscale = grayscale;
		return this;
	}

	/**
	 * Bitmap resolution (in dpi). Applies to bitmap formats only (JPEG, PNG etc.).
	 */
	public Barcode4jBuilder resolution(int resolution) {
		this.resolution = resolution;
		return this;
	}

	/**
	 * Height (example: '2.5cm').
	 */
	public Barcode4jBuilder height(String height) {
		this.height = height;
		return this;
	}

	/**
	 * Module Width (example '0.3mm').
	 */
	public Barcode4jBuilder moduleWidth(String moduleWidth) {
		this.moduleWidth = moduleWidth;
		return this;
	}

	/**
	 * Wide Factor (example: '2', '3').
	 */
	public Barcode4jBuilder wideFactor(String wideFactor) {
		this.wideFactor = wideFactor;
		return this;
	}

	/**
	 * Enable Quiet Zone (example: '10mw', '1cm', 'disable').
	 */
	public Barcode4jBuilder quietZone(String quietZone) {
		this.quietZone = quietZone;
		return this;
	}

	/**
	 * Placement of human-readable part.
	 */
	public Barcode4jBuilder humanReadablePlacement(HumanReadablePlacement humanReadablePlacement) {
		this.humanReadablePlacement = humanReadablePlacement;
		return this;
	}

	/**
	 * Human-readable part font size (example: '8pt')
	 */
	public Barcode4jBuilder humanReadableSize(String humanReadableSize) {
		this.humanReadableSize = humanReadableSize;
		return this;
	}

	/**
	 * Human-readable part font (example: 'Helvetica')
	 */
	public Barcode4jBuilder humanReadableFont(String humanReadableFont) {
		this.humanReadableFont = humanReadableFont;
		return this;
	}

	/**
	 * Human-readable part pattern (example: "\_patterned\_:__/__/____").
	 * <p/>
	 * Any '_' is placeholder for the next message symbol,
	 * all other pattern symbols will be inserted between.
	 * The '\' is escape char. If the patterned message is
	 * too long you can increase the quite zone lenght to make it visible.
	 */
	public Barcode4jBuilder humanReadablePattern(String humanReadablePattern) {
		this.humanReadablePattern = humanReadablePattern;
		return this;
	}

	/**
	 * Builds the Configuration object
	 * 
	 * @return Configuration object
	 */
	protected Configuration buildConfig() {
		DefaultConfiguration config = new DefaultConfiguration("barcode");

        DefaultConfiguration child = new DefaultConfiguration(type.getName());
        config.addChild(child);

        DefaultConfiguration attr;

        if ( height != null ) {
			attr = new DefaultConfiguration("height");
			attr.setValue(height);
			child.addChild(attr);
        }

        if ( moduleWidth != null ) {
			attr = new DefaultConfiguration("module-width");
			attr.setValue(moduleWidth);
			child.addChild(attr);
		}

		if ( wideFactor != null ) {
			attr = new DefaultConfiguration("wide-factor");
			attr.setValue(wideFactor);
			child.addChild(attr);
		}

		if ( quietZone != null ) {
            attr = new DefaultConfiguration("quiet-zone");
            if ( quietZone.startsWith(DISABLE_QUIET_ZONE) ) {
                attr.setAttribute("enabled", "false");
            } else {
                attr.setValue(quietZone);
            }
            child.addChild(attr);
		}

        if ( !(humanReadablePlacement == null)
                && (humanReadablePattern == null)
                && (humanReadableSize == null)
                && (humanReadableFont == null) ) {
			attr = new DefaultConfiguration("human-readable");

			DefaultConfiguration subAttr;
			if ( humanReadablePattern != null ) {
			    subAttr = new DefaultConfiguration("pattern");
			    subAttr.setValue(humanReadablePattern);
			    attr.addChild(subAttr);
			}
			if ( humanReadableSize != null ) {
			    subAttr = new DefaultConfiguration("font-size");
			    subAttr.setValue(humanReadableSize);
			    attr.addChild(subAttr);
			}
			if ( humanReadableFont != null ) {
			    subAttr = new DefaultConfiguration("font-name");
			    subAttr.setValue(humanReadableFont);
			    attr.addChild(subAttr);
			}
			if ( humanReadablePlacement != null ) {
			  subAttr = new DefaultConfiguration("placement");
			  subAttr.setValue(humanReadablePlacement.getConstant());
			  attr.addChild(subAttr);
			}

			child.addChild(attr);
        }

        return config;
	}
}