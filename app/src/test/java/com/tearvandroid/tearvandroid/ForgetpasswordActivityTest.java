package com.tearvandroid.tearvandroid;

import org.junit.Test;

import static org.junit.Assert.*;

public class ForgetpasswordActivityTest {
    @Test
    public void isEmailValid() {
        ForgetpasswordActivity fpa = new ForgetpasswordActivity();
        String email1 = "hello@gmail.com";
        boolean eh = fpa.isEmailValid(email1);
        assertEquals(eh, true);

        String email2 = "nope";
        boolean eh1 = fpa.isEmailValid(email2);
        assertEquals(eh1, false);

    }
}