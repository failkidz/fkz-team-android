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

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new ButtonListener());
        updateList();

    }

    public void updateList(){
        ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();

        JSONObject json = JSON.getJSONfromURL("http://213.89.179.118:8080/fkz-team/ScoreboardREST");
        if(json != null){
            try{

                JSONArray scoreboard = json.getJSONArray("scoreboard");

                for(int i=0;i<scoreboard.length();i++){
                    HashMap<String, String> map = new HashMap<String, String>();
                    JSONObject e = scoreboard.getJSONObject(i);
//                    map.put("id", String.valueOf(i));
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
        }
    }

    public class ButtonListener implements View.OnClickListener {

        public void onClick(View view) {
            updateList();
        }
    }
}

