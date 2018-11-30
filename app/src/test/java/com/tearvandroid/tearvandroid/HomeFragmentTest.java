package com.tearvandroid.tearvandroid;

import org.junit.Test;

import static org.junit.Assert.*;

public class HomeFragmentTest {

    @Test
    public void isClick() {
        HomeFragment hf = new HomeFragment();
        boolean click = true;
        boolean notclick = false;
        //boolean check;


        boolean check = hf.isClick(click);
        assertEquals(check, true);

        check = hf.isClick(notclick);

        assertEquals(check, false);

    }
}