package party_invite;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DistanceCalculation{
	public static final double R = 6372.8;
	public static void main (String[] args) throws IOException, ParseException{
		ArrayList<Invitee> inviteList = new ArrayList<Invitee>();
		DistanceCalculation.parseJSON(inviteList);
	}

	public static double greatCircleDistanceCalc(double custLat, double custLon) {
		double officeLat = 53.339428;
		double officeLon = -6.257664;
		double lonDiff = Math.toRadians(custLon - officeLon);
		officeLat = Math.toRadians(officeLat);
		custLat = Math.toRadians(custLat);

		double centralAngle = Math.acos(Math.sin(officeLat)*Math.sin(custLat) + Math.cos(officeLat)*Math.cos(custLat)*Math.cos(lonDiff));

		return R * centralAngle;
	}

	public static void parseJSON(ArrayList<Invitee> inviteList) throws FileNotFoundException, IOException, ParseException {
		JSONParser parser = new JSONParser();
		JSONArray  a = (JSONArray) parser.parse(new FileReader("customers.json"));

		for (Object o: a) {
			JSONObject person = (JSONObject)o;

			double custLat = Double.parseDouble((String) person.get("latitude"));
			double custLon = Double.parseDouble((String) person.get("longitude"));

			double distance = greatCircleDistanceCalc(custLat,custLon);

			if(distance <= 100) {
				inviteList.add(new Invitee((long)person.get("user_id"),(String)person.get("name")));
			}
		}
		Collections.sort(inviteList, Invitee.COMPARE_BY_USER_ID);
		prettyPrintArray(inviteList);
	}

	public static void prettyPrintArray(ArrayList<Invitee> inviteList) {
		for(Invitee i: inviteList) {
			System.out.println("Name: "+ i.getName() + " User ID: " + i.getInvUserID());
		}
	}
}