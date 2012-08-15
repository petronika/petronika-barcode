package net.petronika.barcode.barcode4j;

/**
 * Enumeration for placement of the human readable part of a barcode.
 * 
 * @see org.krysalis.barcode4j.HumanReadablePlacement
 */
public enum HumanReadablePlacement {
	HRP_TOP(org.krysalis.barcode4j.HumanReadablePlacement.HRP_TOP.getName()),
	HRP_BOTTOM(org.krysalis.barcode4j.HumanReadablePlacement.HRP_BOTTOM.getName()),
	HRP_NONE(org.krysalis.barcode4j.HumanReadablePlacement.HRP_NONE.getName());

	private final String constant;

	HumanReadablePlacement(String constant) {
		this.constant = constant;
	}

	public String getConstant() {
		return constant;
	}
}