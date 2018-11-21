/*
 * Sébastien Eon 2013 / CC0-1.0
 */
package sew.maps;

/**
 *
 */
public interface AddressComponent {

	/**
	 * @return the short name.
	 */
	public abstract String getShortName();

	/**
	 * @return the long name.
	 */
	public abstract String getLongName();

	/**
	 * @return the types.
	 */
	public abstract String[] getTypes();

}