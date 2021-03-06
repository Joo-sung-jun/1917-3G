package com.example.bottom_navigation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class Passgraduation extends Fragment {
    CheckBox cb_1;
    CheckBox cb_2;
    CheckBox cb_3;
    CheckBox cb_4;
    CheckBox cb_5;


    public static Passgraduation newinstance() {
        return new Passgraduation();
    }

    public Passgraduation() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_passgraduation, null);

        ImageButton btn_back = (ImageButton) view.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).replaceFragment(Checklist.newinstance());
            }
        });

        ImageButton btn_ess = (ImageButton) view.findViewById(R.id.btn_ess_passgra);
        btn_ess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).replaceFragment(PassgraEss.newinstance());
            }
        });

        ImageButton btn_selec = (ImageButton) view.findViewById(R.id.btn_selec_passgra);
        btn_selec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).replaceFragment(PassgraSelec.newinstance());
            }
        });
        return view;
    }
    
}

//        Context context = getActivity();
//        SharedPreferences pref = context.getSharedPreferences("pref",0);
//        cb_1 = (CheckBox)view.findViewById(R.id.cb_1);
//        cb_2 = (CheckBox)view.findViewById(R.id.cb_2);
//        cb_3 = (CheckBox)view.findViewById(R.id.cb_3);
//        cb_4 = (CheckBox)view.findViewById(R.id.cb_4);
//        cb_5 = (CheckBox)view.findViewById(R.id.cb_5);
//
//        cb_1.setChecked(pref.getBoolean("check",false));
//        cb_2.setChecked(pref.getBoolean("check1",false));
//        cb_3.setChecked(pref.getBoolean("check2",false));
//        cb_4.setChecked(pref.getBoolean("check3",false));
//        cb_5.setChecked(pref.getBoolean("check4",false));
//
//
//        View view_mypage = inflater.inflate(R.layout.fragment_mypage,null);
//        Button btn_quit = (Button) view_mypage.findViewById(R.id.btn_quit);
//        btn_quit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (cb_1.isChecked())
//                    cb_1.setChecked(false);
//                if (cb_2.isChecked())
//                    cb_2.setChecked(false);
//                if (cb_3.isChecked())
//                    cb_3.setChecked(false);
//                if (cb_4.isChecked())
//                    cb_4.setChecked(false);
//                if (cb_5.isChecked())
//                    cb_5.setChecked(false);
//            }
//        });
//
//
//        return view;
//
//
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        Context context = getActivity();
//        SharedPreferences pref = context.getSharedPreferences("pref", 0);
//        SharedPreferences.Editor editor = pref.edit();
//
//        editor.putBoolean("check",cb_1.isChecked());
//        editor.putBoolean("check1",cb_2.isChecked());
//        editor.putBoolean("check2",cb_3.isChecked());
//        editor.putBoolean("check3",cb_4.isChecked());
//        editor.putBoolean("check4",cb_5.isChecked());
//        editor.commit(); // ???????????? ????????????.
//    }


