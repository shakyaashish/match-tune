package com.studio.matchtune.utility;

import android.graphics.Color;
import android.util.Patterns;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class utility {
    public int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }


    public static boolean isValidEmail(String email) {
        return (Patterns.EMAIL_ADDRESS).matcher(email).matches();
    }


    public List<Integer> getRandomVideo(){
        Random rng = new Random(); // Ideally just create one instance globally
        List<Integer> generated = new ArrayList<Integer>();
        for (int i = 2; i < 94; i++)
        {
            while(true)
            {
                Integer next = rng.nextInt(94) + 1;
                if (!generated.contains(next))
                {
                    generated.add(next);
                    break;
                }
            }
        }
        return generated;
    }
}