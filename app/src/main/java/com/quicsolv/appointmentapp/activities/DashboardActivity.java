package com.quicsolv.appointmentapp.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.quicsolv.appointmentapp.MyApplication;
import com.quicsolv.appointmentapp.R;
import com.quicsolv.appointmentapp.fragments.AppointmentListFragment;
import com.quicsolv.appointmentapp.fragments.NoInternetConnectionFragment;
import com.quicsolv.appointmentapp.fragments.ProfileFragment;
import com.quicsolv.appointmentapp.fragments.ReportsFragment;
import com.quicsolv.appointmentapp.fragments.RequestAppointmentFragment;
import com.quicsolv.appointmentapp.fragments.ResetPasswordFragment;
import com.quicsolv.appointmentapp.fragments.SubmittedQuestionnarieFragment;
import com.quicsolv.appointmentapp.utils.Connectivity;
import com.quicsolv.appointmentapp.utils.Prefs;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Context mContext;
    private DrawerLayout drawer;
    private FloatingActionButton fab;

    private TextView txt_title_name_nav;
    private TextView txt_title_email_nav;
    private CircleImageView profileImage;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mContext = DashboardActivity.this;

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        fab = (FloatingActionButton) findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);

        txt_title_name_nav = (TextView) header.findViewById(R.id.txt_title_name_nav);
        txt_title_email_nav = (TextView) header.findViewById(R.id.txt_title_email_nav);

        profileImage = (CircleImageView) header.findViewById(R.id.profile_image);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        openDefaultFragment();

        txt_title_name_nav.setText(Prefs.getSharedPreferenceString(mContext, Prefs.PREF_PATIENT_NAME, ""));
        txt_title_email_nav.setText(Prefs.getSharedPreferenceString(mContext, Prefs.PREF_PATIENT_EMAIL, ""));

        String profileImgUrl = Prefs.getSharedPreferenceString(mContext, Prefs.PREF_PATIENT_PROFILE_IMAGE_URL_, "");
        if (!profileImgUrl.equals("")) {
            Picasso.with(mContext).load(profileImgUrl).error(R.drawable.profile).into(profileImage);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void openDefaultFragment() {
        Fragment fragment = null;
        Class fragmentClass = null;
        if (!Prefs.getSharedPreferenceBoolean(mContext, Prefs.PREF_IS_FROM_REQUEST_APT, false)) {
            Prefs.setSharedPreferenceBoolean(mContext, Prefs.PREF_IS_FROM_REQUEST_APT, false);
        }
        if (Connectivity.isNetworkConnected(MyApplication.getInstance())) {
            fab.setVisibility(View.GONE);
            fragmentClass = AppointmentListFragment.class;
        } else {
            fragmentClass = NoInternetConnectionFragment.class;
        }
        openFragment(fragment, fragmentClass, true);
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
//        else {
//            super.onBackPressed();
//        }

// TODO Auto-generated method stub
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        // builder.setCancelable(false);
        builder.setMessage(getString(R.string.are_you_sure_quit));
        builder.setPositiveButton(getString(R.string.yes_please), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
//                Toast.makeText(mContext, "Yes i wanna exit", Toast.LENGTH_LONG).show();
                finish();
                finish();
            }
        });
        builder.setNegativeButton(getString(R.string.no_stay_here), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
//        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            drawer.openDrawer(GravityCompat.START);  // OPEN DRAWER
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;
        Class fragmentClass = null;

        if (id == R.id.nav_my_profile) {
            fab.setVisibility(View.GONE);
            if (Connectivity.isNetworkConnected(MyApplication.getInstance())) {
                fragmentClass = ProfileFragment.class;
            } else {
                fragmentClass = NoInternetConnectionFragment.class;
            }
            openFragment(fragment, fragmentClass, true);
        } else if (id == R.id.nav_my_appointments) {
            fab.setVisibility(View.GONE);
            if (Connectivity.isNetworkConnected(MyApplication.getInstance())) {
                fragmentClass = AppointmentListFragment.class;

            } else {
                fragmentClass = NoInternetConnectionFragment.class;

            }
            openFragment(fragment, fragmentClass, true);

        } else if (id == R.id.nav_request_appointment) {
            fab.setVisibility(View.GONE);
            if (Connectivity.isNetworkConnected(MyApplication.getInstance())) {
                fragmentClass = RequestAppointmentFragment.class;
            } else {
                fragmentClass = NoInternetConnectionFragment.class;
            }
            openFragment(fragment, fragmentClass, true);

        } else if (id == R.id.nav_my_questionnarie) {
            fab.setVisibility(View.GONE);
            if (Connectivity.isNetworkConnected(MyApplication.getInstance())) {
                fragmentClass = SubmittedQuestionnarieFragment.class;
            } else {
                fragmentClass = NoInternetConnectionFragment.class;
            }
            openFragment(fragment, fragmentClass, true);

        } else if (id == R.id.nav_reset_password) {
            fab.setVisibility(View.GONE);
            if (Connectivity.isNetworkConnected(MyApplication.getInstance())) {
                fragmentClass = ResetPasswordFragment.class;
            } else {
                fragmentClass = NoInternetConnectionFragment.class;
            }
            openFragment(fragment, fragmentClass, true);

        } else if (id == R.id.nav_reports) {
            fab.setVisibility(View.GONE);
            if (Connectivity.isNetworkConnected(MyApplication.getInstance())) {
                fragmentClass = ReportsFragment.class;
            } else {
                fragmentClass = NoInternetConnectionFragment.class;
            }
            openFragment(fragment, fragmentClass, false);

        } else if (id == R.id.nav_logout) {
            logoutDialog();
        }

        return true;
    }

    private void openFragment(Fragment fragment, Class fragmentClass, boolean wantToshoAnimation) {
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (wantToshoAnimation) {
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_right, R.anim.exit_to_left);
        }
        transaction.replace(R.id.frame_container, fragment, "").commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public void setToolBarTitle(String toolBarTitle) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView txtTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        txtTitle.setText(toolBarTitle);
    }

    public void setNavDrawerUserName(String name) {
        txt_title_name_nav.setText(name);
    }

    public void showHideFabButton(boolean isDisplay) {
        if (isDisplay) {
            fab.setVisibility(View.VISIBLE);
        } else {
            fab.setVisibility(View.GONE);
        }
    }

    public void setProfileImage(String profilePath) {
        Picasso.with(mContext).load(profilePath).error(R.drawable.profile).into(profileImage);
    }

    public void logoutDialog(){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext, R.style.AlertDialogTheme);

        // Setting Dialog Title
        alertDialog.setTitle(getString(R.string.header_logout));

        alertDialog.setCancelable(false);

        // Setting Dialog Message
        alertDialog.setMessage(getString(R.string.want_to_logout));

        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.ic_logout_black);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton(getString(R.string.yes_please), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Prefs.setSharedPreferenceString(mContext, Prefs.PREF_EMAIL, "");
                Prefs.setSharedPreferenceString(mContext, Prefs.PREF_PASSWORD, "");

                finish();
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton(getString(R.string.no_stay_here), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
}
