package com.meizu.newtest.Activity;

//import org.json.JSONArray;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.meizu.newtest.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class httptest extends Activity  {
	/*
	private TextView numtv;
	private TextView goaltv;
	private TextView nametv;
	private TextView teamtv;
	*/
	//private TextView resulttv;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.httptest);
        /*
        numtv = (TextView)this.findViewById(R.id.num);
        goaltv =(TextView)this.findViewById(R.id.goal);
        nametv =(TextView)this.findViewById(R.id.name);
        teamtv =(TextView)this.findViewById(R.id.team);
        */
		//resulttv = (TextView)this.findViewById(R.id.result);
		//新页面接收数据
		Bundle bundle = this.getIntent().getExtras();
		List<String> data = new ArrayList<String>();
		ListView listview = new ListView(this);
		//接收name值
		String respstr = bundle.getString("respstr");
		try {
			JSONObject jsonresult = new JSONObject(respstr);
//        	for(int i=1;i<11;i++){
//				String playerInfoStr = jsonresult.getString(String.valueOf(i));
//				//resulttv.setText(playerInfoStr);
//
//				JSONObject playerInfo = new JSONObject(playerInfoStr);
//				String goal = playerInfo.getString("goal");
//				String name = playerInfo.getString("name");
//				String team = playerInfo.getString("team");
//				String result = "排名"+String.valueOf(i)+": "+name+" "+goal+" "+team;
//				data.add(result);
//        	}
			JSONArray res = jsonresult.getJSONArray("playerlist");
			for(int x = 0;x < 10;x++){
				JSONObject resobj = (JSONObject) res.get(x);
				String numres = resobj.getString("排名");
				String nameres = resobj.getString("球员");
				String goalres = resobj.getString("进球");
				String resstr = "排名:"+numres+"/"+"球员:"+nameres+"/"+"进球:"+goalres;
				data.add(resstr);
			}
			listview.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,data));
			setContentView(listview);
			/*
			numtv.setText("1");
			goaltv.setText(goal);
			nametv.setText(name);
			teamtv.setText(team);
			*/
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
