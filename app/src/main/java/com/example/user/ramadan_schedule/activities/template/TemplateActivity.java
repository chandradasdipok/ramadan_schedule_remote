package com.example.user.ramadan_schedule.activities.template;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.user.ramadan_schedule.R;
import com.example.user.ramadan_schedule.activities.main.ListScheduleActivity;
import com.example.user.ramadan_schedule.activities.main.PagerScheduleActivity;
import com.example.user.ramadan_schedule.data.constants.ApplicationConstants;
import com.example.user.ramadan_schedule.data.constants.RegistrationConstants;
import com.example.user.ramadan_schedule.data.db.DataBaseHelper;
import com.example.user.ramadan_schedule.datamodels.District;
import com.example.user.ramadan_schedule.datamodels.RamadanDay;
import com.example.user.ramadan_schedule.datamodels.interfaces.ITable;
import com.example.user.ramadan_schedule.utils.CustomToast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

public abstract class TemplateActivity extends AppCompatActivity {

    protected Toolbar templateToolbar;
    protected CustomToast customToast = new CustomToast(this);
    protected DataBaseHelper localDataBaseHelper = new DataBaseHelper(this);

    protected List<RamadanDay> ramadanDayList = new ArrayList<RamadanDay>();

    public abstract void initView();
    public abstract void loadData();
    public abstract void initializeViewByData();
    public abstract void listenView();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }
    
    protected int getCurrentItem(){
        Calendar localCalendar = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        localCalendar.add(Calendar.HOUR, 6);
        int currentPosition = 0;
        for (RamadanDay ramadanDay:ramadanDayList){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(ramadanDay.getSehrDate());
            if (calendar.get(Calendar.DATE) == localCalendar.get(Calendar.DATE) &&
                    calendar.get(Calendar.MONTH) == localCalendar.get(Calendar.MONTH) &&
                    calendar.get(Calendar.YEAR) == localCalendar.get(Calendar.YEAR)){
                currentPosition = ramadanDay.ramadanDay-1;
            }
        }
        return currentPosition;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new ViewLoadingTask().execute();
    }

    class ViewLoadingTask extends AsyncTask<Void,Void,Void> {
        ProgressDialog progressDialog = new ProgressDialog(TemplateActivity.this);
        @Override
        protected void onPreExecute() {
            initView();
            progressDialog.setMessage("Loading");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            loadData();
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            if (progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            initializeViewByData();
            listenView();
        }
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_list){
            Intent intent = new Intent(this, ListScheduleActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.menu_pager){
            Intent intent = new Intent(this, PagerScheduleActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.menu_switch_district){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Switch District");
            List<ITable>iTables = localDataBaseHelper.selectRows(new District());
            List<District> districtList = District.toDistricts(iTables);
            final String[] iTablesString = new String[iTables.size()];
            for (int i = 0; i < iTables.size(); i++) {
                iTablesString[i] = districtList.get(i).districtName;
            }
            alertDialogBuilder.setItems(iTablesString, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences sharedPreferences = getSharedPreferences(RegistrationConstants.APPLICATION_PREFERENCE, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    ApplicationConstants.USER_DISTRICT = iTablesString[which].toString();
                    editor.putString(RegistrationConstants.USER_DISTRICT, ApplicationConstants.USER_DISTRICT);
                    editor.commit();
                    Intent intent = new Intent(TemplateActivity.this,PagerScheduleActivity.class);
                    startActivity(intent);
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        return true;
    }
    @Override
    public void onBackPressed() {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
    }
}