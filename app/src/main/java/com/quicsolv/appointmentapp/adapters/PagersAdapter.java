package com.quicsolv.appointmentapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.quicsolv.appointmentapp.fragments.TabAppointmentHistoryFragment;
import com.quicsolv.appointmentapp.fragments.TabPendingAppointmentFragment;
import com.quicsolv.appointmentapp.fragments.TabScheduledAppointmentFragment;

/**********************************************************************
 * Created by   -  Tushar Patil
 * Organization -  QuicSolv Technologies Pvt.Ltd
 * Date         -  23 Apr 2018
 ***********************************************************************/

public class PagersAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagersAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TabAppointmentHistoryFragment tab1 = new TabAppointmentHistoryFragment();
                return tab1;
            case 1:
                TabScheduledAppointmentFragment tab2 = new TabScheduledAppointmentFragment();
                return tab2;
            case 2:
                TabPendingAppointmentFragment tab3 = new TabPendingAppointmentFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}