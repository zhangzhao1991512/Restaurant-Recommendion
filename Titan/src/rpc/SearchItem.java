package rpc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import db.mysql.MySQLConnection;
import entity.Item;
import external.YelpAPI;

/**
 * Servlet implementation class SearchItem
 */
@WebServlet("/search")
public class SearchItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private MySQLConnection conn = MySQLConnection.getInstance();

	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchItem() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.setContentType("application/json");
//		String username = "";
//		PrintWriter out = response.getWriter();
//		if (request.getParameter("username") != null) {
//			username = request.getParameter("username");
//		}
//		
//		JSONObject obj = new JSONObject();
//		try {
//			obj.put("username", username);
//		} catch (JSONException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		
//		out.print(obj);
//		
//		out.flush();
//		out.close();
		
//		JSONArray array = new JSONArray();
//		try {
//			array.put(new JSONObject().put("username", "abcd"));
//		} catch (JSONException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		RpcHelper.writeJsonArray(response, array);
		
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String userId = request.getParameter("user_id");
		double lat = Double.parseDouble(request.getParameter("lat"));
		double lon = Double.parseDouble(request.getParameter("lon"));
		// Term can be empty or null.
		String term = request.getParameter("term");
		List<Item> items = conn.searchItems(userId, lat, lon, term);

//		YelpAPI api = new YelpAPI();
		//items are all the 'business' results
//		List<Item> items = api.search(lat, lon, term);
		//for front end uses
		Set<String> favorite = conn.getFavoriteItemIds(userId);
		List<JSONObject> list = new ArrayList<>();
		try {
			for (Item item : items) {
				JSONObject obj = item.toJSONObject();
				// Check if this is a favorite one.
		        // This field is required by frontend to correctly display favorite items.
		        obj.put("favorite", favorite.contains(item.getItemId()));

				list.add(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONArray array = new JSONArray(list);
		RpcHelper.writeJsonArray(response, array);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
