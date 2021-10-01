package com.nooneprojects.takaincome_bd.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.nooneprojects.takaincome_bd.Fragments.SignIn_Fragments;
import com.nooneprojects.takaincome_bd.Fragments.SignUp_Fragment;

public class LoginAdapter extends FragmentPagerAdapter {

    private Context context;
    int totalTabs;


    public LoginAdapter(FragmentManager fm, Context context, int totalTabs) {
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0:
                SignIn_Fragments signIn_fragments=new SignIn_Fragments();
                return signIn_fragments;

            case 1:
                SignUp_Fragment signUp_fragment=new SignUp_Fragment();
                return signUp_fragment;


            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
