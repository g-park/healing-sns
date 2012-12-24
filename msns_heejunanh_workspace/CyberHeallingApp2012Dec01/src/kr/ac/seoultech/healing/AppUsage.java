package kr.ac.seoultech.healing;

import android.net.Uri;


public class AppUsage {

	public static class AppUsageColumns{		
		// @Todo
		public static final String URI_PREFIX = "content://kr.ac.seoultech.healing";
		public static final Uri CONTENT_URI =Uri.parse(URI_PREFIX + "/AppUsage");
		public static final Uri CONTENT_URI_GROUP_BY =Uri.parse(URI_PREFIX);
		public static final Uri CONTENT_URI_GROUP_BY_CUSTOM =Uri.parse(URI_PREFIX);
		public static final Uri CONTENT_URI_GROUP_BY_FOR_CUSTOM_DURATION =Uri.parse(URI_PREFIX);
		public static final Uri CONTENT_URI_DETAILED =Uri.parse(URI_PREFIX);
		public static final Uri CONTENT_URI_DETAILED_CUSTOM =Uri.parse(URI_PREFIX);;
		public static final Uri CONTENT_URI_SETTINGS =Uri.parse(URI_PREFIX + "/setting");
		public static final Uri CONTENT_URI_SETTINGS_COUNT = Uri.parse(URI_PREFIX);
		
		public static final Uri CONTENT_URI_DELETE_CUSTOM = Uri.parse(URI_PREFIX);
		public static final Uri CONTENT_URI_DETAILED_CUSTOM_ALL =Uri.parse(URI_PREFIX);
		public static final Uri CONTENT_URI_DETAILED_ALL =Uri.parse(URI_PREFIX);
				
	};

}
