package app.android.saranalaporbandung.Intro;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class IntroAdapter extends FragmentPagerAdapter {
    public IntroAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new IntroSatu();
            case 1:
                return new IntroDua();
            case 2:
                return new IntroTiga();

            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}