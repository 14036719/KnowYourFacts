package sg.edu.rp.soi.c347.knowyourfacts;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    MyFragmentPagerAdapter pagerAdapter;
    ViewPager pager;
    Button btnReadLater;
    int itemNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());

        pager = (ViewPager) findViewById(R.id.vP);
        btnReadLater = (Button) findViewById(R.id.btnReadLater);
        btnReadLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.SECOND, 5);

                Intent i = new Intent(MainActivity.this, ScheduledNotificationReceiver.class);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 123, i, PendingIntent.FLAG_CANCEL_CURRENT);

                AlarmManager am = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);

                finish();
            }
        });
        pager.setAdapter(pagerAdapter);
    }
    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        SharedPreferences.Editor prefEdit = preferences.edit();

        prefEdit.putInt("itemNo", pager.getCurrentItem());

        prefEdit.commit();
    }
    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        itemNo = preferences.getInt("itemNo", 0);

        pager.setCurrentItem(itemNo);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Next:
                if (pager.getCurrentItem() < pager.getChildCount() - 1) {
                    pager.setCurrentItem(pager.getCurrentItem() + 1, true);
                }
                return true;
            case R.id.Random:
                Random r = new Random();
                int no = r.nextInt(pager.getChildCount());
                if (pager.getCurrentItem() == no) {
                    pager.setCurrentItem(r.nextInt(pager.getChildCount()), true);
                }
                else {
                    pager.setCurrentItem(no, true);
                }
                return true;
            case R.id.Previous:
                if (pager.getCurrentItem() > 0) {
                    // Set last argument to true for smooth scrolling
                    pager.setCurrentItem(pager.getCurrentItem() -1, true);
                    Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
