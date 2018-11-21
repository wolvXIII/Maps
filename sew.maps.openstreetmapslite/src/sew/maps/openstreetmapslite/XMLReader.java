/*
 * Sébastien Eon 2013 / CC0-1.0
 */
package sew.maps.openstreetmapslite;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import sew.xml.XmlPullParserHelper;

import sew.maps.Coordinates;
import sew.maps.Place;
import sew.maps.impl.SimpleCoordinates;
import sew.maps.impl.SimplePlace;

public class XMLReader {

	private static final String SEARCHRESULTS_NODE = "searchresults";
	private static final String PLACE_NODE = "place";
	private static final String DISPLAY_NAME_ATTRIBUTE = "display_name";
	private static final String LATITUDE_ATTRIBUTE = "lat";
	private static final String LONGITUDE_ATTRIBUTE = "lon";

	public Place[] readSearchResult(InputStream inputStream) throws XmlPullParserException, IOException {
		XmlPullParser parser = XmlPullParserHelper.createParser(XmlPullParserFactory.newInstance(), inputStream);
		parser.require(XmlPullParser.START_TAG, null, SEARCHRESULTS_NODE);
		parser.nextTag();
		List<SimplePlace> placesList = new ArrayList<>();
		while (PLACE_NODE.equals(parser.getName())) {
			HashMap<String, String> placeAttributes = XmlPullParserHelper.parseSimpleNodeWithAttributes(parser,
					PLACE_NODE);
			String name = placeAttributes.get(DISPLAY_NAME_ATTRIBUTE);
			double latitude = Double.parseDouble(placeAttributes.get(LATITUDE_ATTRIBUTE));
			double longitude = Double.parseDouble(placeAttributes.get(LONGITUDE_ATTRIBUTE));
			Coordinates coordinates = new SimpleCoordinates(latitude, longitude);
			SimplePlace place = new SimplePlace(coordinates, name, null);
			placesList.add(place);
			parser.nextTag();
		}
		parser.require(XmlPullParser.END_TAG, null, SEARCHRESULTS_NODE);

		Place[] result = placesList.toArray(new SimplePlace[placesList.size()]);

		return result;
	}

}
