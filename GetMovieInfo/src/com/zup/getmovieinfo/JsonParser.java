package com.zup.getmovieinfo;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonParser {

	protected String parseJson (String detail, String getJson) throws JSONException {

		JSONObject jObj = null;
		try {
			jObj = new JSONObject(getJson);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jObj.getString(detail);
	}
}
