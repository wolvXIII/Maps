/*
 * Sébastien Eon 2013 / CC0-1.0
 */
package sew.maps.tomtom;

import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import sew.xml.XmlPullParserHelper;

import sew.maps.AddressComponent;
import sew.maps.Coordinates;
import sew.maps.Place;
import sew.maps.impl.SimpleAddressComponent;
import sew.maps.impl.SimpleCoordinates;
import sew.maps.impl.SimplePlace;

public class XMLReader {

	private static final String GEOCODERESPONSE_NODE = "geoResponse";
	private static final String RESULT_STATUS_LIST_NODE = "resultStatusList";
	private static final String STATUS_NODE = "status";
	private static final String RESULT_NODE = "geoResult";
	private static final String TYPE_NODE = "type";
	private static final String FORMATTED_ADDRESS_NODE = "formatted_address";
	private static final String ADDRESS_COMPONENT_NODE = "address_component";
	private static final String LONG_NAME_NODE = "long_name";
	private static final String SHORT_NAME_NODE = "short_name";
	private static final String GEOMETRY_NODE = "geometry";
	private static final String LOCATION_NODE = "location";
	private static final String LATITUDE_NODE = "lat";
	private static final String LONGITUDE_NODE = "lng";
	private static final String LOCATION_TYPE_NODE = "location_type";
	private static final String VIEWPORT_NODE = "viewport";
	private static final String SOUTHWEST_NODE = "southwest";
	private static final String NORTHEAST_NODE = "northeast";
	private static final String BOUNDS_NODE = "bounds";
	private static final String PARTIAL_MATCH_NODE = "partial_match";

	public Place[] readSearchResult(InputStream inputStream) throws XmlPullParserException, IOException {
		XmlPullParser parser = XmlPullParserHelper.createParser(XmlPullParserFactory.newInstance(), inputStream);
		parser.require(XmlPullParser.START_TAG, null, GEOCODERESPONSE_NODE);
		parser.nextTag();
		parser.require(XmlPullParser.START_TAG, null, RESULT_STATUS_LIST_NODE);
		parser.nextTag();
		while (STATUS_NODE.equals(parser.getName())) {
			XmlPullParserHelper.parseSimpleNodeWithText(parser, STATUS_NODE);
			parser.nextTag();
		}
		parser.require(XmlPullParser.END_TAG, null, RESULT_STATUS_LIST_NODE);
		parser.nextTag();
		Vector placesVector = new Vector();
		while (RESULT_NODE.equals(parser.getName())) {
			parser.require(XmlPullParser.START_TAG, null, RESULT_NODE);
			parser.nextTag();
			readCoordinates(parser);
			Vector typesVector = new Vector();
			while (TYPE_NODE.equals(parser.getName())) {
				typesVector.addElement(XmlPullParserHelper.parseSimpleNodeWithText(parser, TYPE_NODE));
				parser.nextTag();
			}
			String[] types = new String[typesVector.size()];
			typesVector.copyInto(types);
			String formattedAddress = XmlPullParserHelper.parseSimpleNodeWithText(parser, FORMATTED_ADDRESS_NODE);
			parser.nextTag();
			Vector addressesComponentsVector = new Vector();
			while (ADDRESS_COMPONENT_NODE.equals(parser.getName())) {
				parser.require(XmlPullParser.START_TAG, null, ADDRESS_COMPONENT_NODE);
				parser.nextTag();
				String longName = XmlPullParserHelper.parseSimpleNodeWithText(parser, LONG_NAME_NODE);
				parser.nextTag();
				String shortName = XmlPullParserHelper.parseSimpleNodeWithText(parser, SHORT_NAME_NODE);
				parser.nextTag();
				Vector subtypesVector = new Vector();
				while (TYPE_NODE.equals(parser.getName())) {
					typesVector.addElement(XmlPullParserHelper.parseSimpleNodeWithText(parser, TYPE_NODE));
					parser.nextTag();
				}
				parser.require(XmlPullParser.END_TAG, null, ADDRESS_COMPONENT_NODE);
				String[] subtypes = new String[subtypesVector.size()];
				subtypesVector.copyInto(subtypes);
				AddressComponent addressComponent = new SimpleAddressComponent(shortName, longName, types);
				addressesComponentsVector.addElement(addressComponent);
				parser.nextTag();
			}
			parser.require(XmlPullParser.START_TAG, null, GEOMETRY_NODE);
			parser.nextTag();
			Coordinates coordinates = readCoordinates(parser, LOCATION_NODE);
			XmlPullParserHelper.parseSimpleNodeWithText(parser, LOCATION_TYPE_NODE);
			parser.nextTag();
			readBounds(parser, VIEWPORT_NODE);
			if (BOUNDS_NODE.equals(parser.getName())) {
				readBounds(parser, BOUNDS_NODE);
			}
			parser.require(XmlPullParser.END_TAG, null, GEOMETRY_NODE);
			parser.nextTag();
			if (PARTIAL_MATCH_NODE.equals(parser.getName())) {
				XmlPullParserHelper.parseSimpleNodeWithText(parser, PARTIAL_MATCH_NODE);
				parser.nextTag();
			}

			parser.require(XmlPullParser.END_TAG, null, RESULT_NODE);
			AddressComponent[] addressComponents = new AddressComponent[addressesComponentsVector.size()];
			addressesComponentsVector.copyInto(addressComponents);

			Place place = new SimplePlace(coordinates, formattedAddress, addressComponents);
			placesVector.addElement(place);

			parser.nextTag();
		}
		parser.require(XmlPullParser.END_TAG, null, GEOCODERESPONSE_NODE);

		Place[] result = new Place[placesVector.size()];
		placesVector.copyInto(result);

		return result;
	}

	/**
	 * <parentName> <southwest> <lat>47.2340161</lat> <lng>-1.5372403</lng> </southwest> <northeast>
	 * <lat>47.2367820</lat> <lng>-1.5361923</lng> </northeast> </parentName>
	 */
	private void readBounds(XmlPullParser parser, String rootName) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, rootName);
		parser.nextTag();
		readCoordinates(parser, SOUTHWEST_NODE);
		readCoordinates(parser, NORTHEAST_NODE);
		parser.require(XmlPullParser.END_TAG, null, rootName);
		parser.nextTag();
	}

	/**
	 * <parentName> <lat>47.2367820</lat> <lng>-1.5361923</lng> </parentName>
	 */
	private Coordinates readCoordinates(XmlPullParser parser, String parentName)
			throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, parentName);
		parser.nextTag();
		Coordinates coordinates = readCoordinates(parser);
		parser.require(XmlPullParser.END_TAG, null, parentName);
		parser.nextTag();
		return coordinates;
	}

	/**
	 * <lat>47.2367820</lat> <lng>-1.5361923</lng>
	 */
	private Coordinates readCoordinates(XmlPullParser parser) throws XmlPullParserException, IOException {
		String latitudeString = XmlPullParserHelper.parseSimpleNodeWithText(parser, LATITUDE_NODE);
		parser.nextTag();
		String longitudeString = XmlPullParserHelper.parseSimpleNodeWithText(parser, LONGITUDE_NODE);
		parser.nextTag();
		double latitude = Double.parseDouble(latitudeString);
		double longitude = Double.parseDouble(longitudeString);
		Coordinates coordinates = new SimpleCoordinates(latitude, longitude);
		return coordinates;
	}

}
