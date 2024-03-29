package com.quicsolv.appointmentapp.fragments;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.quicsolv.appointmentapp.R;
import com.quicsolv.appointmentapp.activities.DashboardActivity;
import com.quicsolv.appointmentapp.activities.RequestAppointmentActivity;
import com.quicsolv.appointmentapp.adapters.PagersAdapter;
import com.quicsolv.appointmentapp.utils.Prefs;

public class AppointmentListFragment extends Fragment {

    ListView lvContacts;

    public AppointmentListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_appointment_list, container, false);

        String dateStrFromNotfn = Prefs.getSharedPreferenceString(getActivity(), Prefs.PREF_NOTIFICATION_DATA, "");
        if (!dateStrFromNotfn.equals("")){
            Intent intent = new Intent(getActivity(), RequestAppointmentActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }

        ((DashboardActivity) getActivity()).setToolBarTitle(getString(R.string.header_list_of_appointments));

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_history)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_scheduled)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_pending)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        View root = tabLayout.getChildAt(0);
        if (root instanceof LinearLayout) {
            ((LinearLayout) root).setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(getResources().getColor(R.color.splash_color));
            drawable.setSize(2, 1);
            ((LinearLayout) root).setDividerPadding(10);
            ((LinearLayout) root).setDividerDrawable(drawable);
        }

        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);
        PagersAdapter adapter = new PagersAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        boolean isFromRequestApt = Prefs.getSharedPreferenceBoolean(getActivity(), Prefs.PREF_IS_FROM_REQUEST_APT, false);
        if (isFromRequestApt) {
            viewPager.setCurrentItem(2);
        } else {
            viewPager.setCurrentItem(1);
        }

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
        });

        // test1
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPager.getAdapter().notifyDataSetChanged();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return view;
    }

}
