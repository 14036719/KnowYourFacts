package sg.edu.rp.soi.c347.knowyourfacts;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter
{ ArrayList<Fragment> fragments = new ArrayList<Fragment>();

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments.add(new Frag1());
        fragments.add(new Frag2());
        fragments.add(new Frag3());
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}

