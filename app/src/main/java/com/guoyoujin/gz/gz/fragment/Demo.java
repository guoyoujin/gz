package com.guoyoujin.gz.gz.fragment;


import com.guoyoujin.gz.gz.R;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
public class Demo {
  public static int[] tab10() {
    return new int[] {
        R.string.demo_tab_1,
        R.string.demo_tab_2,
        R.string.demo_tab_3,
        R.string.demo_tab_5,
        R.string.demo_tab_6,
        R.string.demo_tab_7,
        R.string.demo_tab_8
    };
  }
  public void setup(final SmartTabLayout layout) {
    //Do nothing.
  }
  public int[] tabs() {
    return tab10();
  }

}
