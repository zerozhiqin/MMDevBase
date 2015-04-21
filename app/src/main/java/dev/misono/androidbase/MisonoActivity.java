package dev.misono.androidbase;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import dev.misono.util.task.BGTask;

public class MisonoActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_misono);

        BGTask.doing(new BGTask.Work<String>() {
            @Override
            public String work() {
                return "doing something";
            }
        }).when(new BGTask.Start() {
            @Override
            public void start() {
                Log.v("MM", "start");
            }
        }).when(new BGTask.Done<String>() {
            @Override
            public void done(String o) {
                Log.v("MM", o);
            }
        }).go();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_misono, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
