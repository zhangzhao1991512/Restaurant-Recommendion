package rpc;

import java.io.BufferedReader;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 
 * @author Jack
 * A helper class to handle rpc related parsing logics
 */

public class RpcHelper {

	//parse a JSONObject from http request.
	//read a request (url) and parse it then form a json to return
	public static JSONObject readJsonObject(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null){
				sb.append(line);
			}
			reader.close();
			return new JSONObject(sb.toString());
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	
	//write a JSONObject to http response.
	//write a json in the response web page
	public static void writeJsonObject(HttpServletResponse response, JSONObject obj){
		try {
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			out.print(obj);
			out.flush();
			out.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	//write a JSONArray to http response
	public static void writeJsonArray(HttpServletResponse response, JSONArray array) {
		try {
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			out.print(array);
			out.flush();
			out.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
}
