package app.sleepdiary.com.sleepdiary;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.app.Activity;
import android.widget.Toast;
import android.support.v7.app.ActionBarActivity;
import com.parse.ParseUser;
import com.parse.ParseException;
import com.parse.SignUpCallback;
import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by Yuhan on 8/31/15.
 */

public class CreateIdActivity extends ActionBarActivity {

    dataBaseHelper helper = new dataBaseHelper(this);

    EditText userid;
    EditText pass1;
    EditText pass2;

    String useridstr = "";
    String pass1str = "";
    String pass2str = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createid);

//        getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
//        );


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
////            return true;
//            Intent i = new Intent(CreateIdActivity.this,SettingsActivity.class);
//            CreateIdActivity.this.startActivity(i);
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }


    public void button_CIdOnClick (View view)
    {

        if (isNetworkAvailable()) {
            // Do your stuff here.
            if(view.getId()==R.id.createdId)
            {

                userid = (EditText)findViewById(R.id.useridc);
                pass1 = (EditText)findViewById(R.id.pwd);
                pass2 = (EditText)findViewById(R.id.conpwd);

                useridstr = userid.getText().toString();
                pass1str = pass1.getText().toString();
                pass2str = pass2.getText().toString();

                if(useridstr.isEmpty())
                {
                    Toast pass = Toast.makeText(CreateIdActivity.this,"Please set your User ID!", Toast.LENGTH_SHORT);
                    pass.show();
                }

                else if(pass1str.isEmpty())
                {
                    Toast pass = Toast.makeText(CreateIdActivity.this,"Please set your Password!", Toast.LENGTH_SHORT);
                    pass.show();
                }

                else if(pass2str.isEmpty())
                {
                    Toast pass = Toast.makeText(CreateIdActivity.this,"Please confirm your Password!", Toast.LENGTH_SHORT);
                    pass.show();
                }

//            else if(helper.findExistId(useridstr))
//            {
//                Toast pass = Toast.makeText(CreateIdActivity.this,"The ID has already existed!", Toast.LENGTH_SHORT);
//                pass.show();
//            }
                else  if(!pass1str.equals(pass2str))
                {
                    //popup msg
                    Toast pass = Toast.makeText(CreateIdActivity.this,"Password don't match!", Toast.LENGTH_SHORT);
                    pass.show();
                }
                else
                {
                    Contact c = new Contact();


                    c.setId(useridstr);
                    c.setPwd(pass1str);

                    ParseUser user = new ParseUser();
                    user.setUsername(useridstr);
                    user.setPassword(pass1str);
                    user.put("pass",pass1str);
                    user.put("Researcher",0);
                    //helper.insertColumn(c);

                    user.signUpInBackground(new SignUpCallback ()
                    {
                        public void done(ParseException e)
                        {
                            if (e != null)
                            {
                                Toast pass = Toast.makeText(CreateIdActivity.this,e.getMessage(), Toast.LENGTH_SHORT);
                                pass.show();
                            }
                            else
                            {
                                Intent i = new Intent(CreateIdActivity.this,MainActivity.class);
                                i.putExtra("loginstatus",true);
                                i.putExtra("userid",useridstr);
                                i.addFlags(i.FLAG_ACTIVITY_CLEAR_TASK|i.FLAG_ACTIVITY_NEW_TASK);
                                CreateIdActivity.this.startActivity(i);
                            }
                        }

                    });

//                Intent i = new Intent(CreateIdActivity.this,SleepActivity.class);
//                i.putExtra("userid",useridstr);
//                CreateIdActivity.this.startActivity(i);

                }

            }
        }
        else {
            Toast pass = Toast.makeText(CreateIdActivity.this, "Network is not available, please check your network.", Toast.LENGTH_LONG);
            pass.show();
        }


        if(view.getId() == R.id.cancel_c)
        {
            Intent i = new Intent(CreateIdActivity.this,SettingsActivity.class);
            CreateIdActivity.this.startActivity(i);
        }

    }

    public void onBackPressed() {
        Intent i = new Intent(CreateIdActivity.this,MainActivity.class);
        //i.putExtra("lastpage",lastpage);
        //startService(j);
        startActivity(i);

    }
}