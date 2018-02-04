package party_invite;

import java.util.Comparator;

public class Invitee{
	private String invName;
	private long invUserID;
	
	public Invitee(long userID, String name) {
		invName = name;
		invUserID = userID;
	}

	public long getInvUserID() {
		return invUserID;
	}

	public String getName() {
		return invName;
	}

	public static Comparator<Invitee> COMPARE_BY_USER_ID  = new Comparator<Invitee>() {
		public int compare(Invitee one, Invitee other) {
			return Long.compare(one.invUserID, other.invUserID);
		}
	};
}
