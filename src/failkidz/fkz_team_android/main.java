package failkidz.fkz_team_android;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class main extends ListActivity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();

        JSONObject json = JSON.getJSONfromURL("http://130.229.141.250:8080/fkz-team/ScoreboardREST");
//        JSONObject json = JSON.getJSONfromURL("https://api.github.com/users/Wneh");

        try{

            JSONArray scoreboard = json.getJSONArray("scoreboard");

            for(int i=0;i<scoreboard.length();i++){
                HashMap<String, String> map = new HashMap<String, String>();
                JSONObject e = scoreboard.getJSONObject(i);
                map.put("id", String.valueOf(i));
                map.put("teamName", e.getString("teamname"));
                map.put("points", e.getString("points"));
                mylist.add(map);
            }
        }catch(JSONException e)        {
            Log.e("log_tag", "Error parsing JSON data " + e.toString());
        }

        ListAdapter adapter = new SimpleAdapter(this, mylist , R.layout.listelement,
                new String[] { "teamName", "points" },
                new int[] { R.id.item_title, R.id.item_subtitle });

        setListAdapter(adapter);

        final ListView lv = getListView();
        lv.setTextFilterEnabled(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                @SuppressWarnings("unchecked")
                HashMap<String, String> o = (HashMap<String, String>) lv.getItemAtPosition(position);
                Toast.makeText(main.this, "ID '" + o.get("id") + "' was clicked.", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
