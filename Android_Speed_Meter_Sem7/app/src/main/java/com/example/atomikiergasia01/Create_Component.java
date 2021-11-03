package com.example.atomikiergasia01;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

public class Create_Component {
// set width/height etc and then add constraints.Finally add it to the laylout
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static Button create_Button(int width, int height, int color, Context context, ConstraintLayout constraintLayout, int posx, int posy, ConstraintSet set){


        Button button = new Button(context);
        button.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
        button.setText("Save");
        button.setPadding(10, 2, 10, 2);
        button.setWidth(width);
        button.setHeight(height);
        button.setBackgroundColor(color);
        button.setTextColor(Color.parseColor("#FFFFFF"));
        button.setId(View.generateViewId());
        constraintLayout.addView(button);
        // set the constraint on the component
        set.clone(constraintLayout);
        set.connect(button.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT, posx);
        set.connect(button.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, posy);
        set.applyTo(constraintLayout);
        return button;

    }
// set width/height etc and then add constraints.Finally add it to the laylout

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static EditText create_EditText(int width, int height, int color, Context context, ConstraintLayout constraintLayout, int posx, int posy, ConstraintSet set){
        EditText editText = new EditText(context);
        editText.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
        editText.setWidth(width );
        editText.setTextColor(color);
        editText.setHeight(height);
        editText.setId(View.generateViewId());
        constraintLayout.addView(editText);
        set.clone(constraintLayout);
        set.connect(editText.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT, posx);
        set.connect(editText.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, posy);
        set.applyTo(constraintLayout);
        return  editText;
    }

}
