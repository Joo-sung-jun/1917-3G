package com.example.bottom_navigation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;



import java.util.ArrayList;
import java.util.List;

public class Gyoyang extends Fragment {
    private  View view;
    private Spinner spn_gyoyang,spn_areagyo;
    private TextView tv_result3,tv_result4;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<User> arrayList;
    List<UserLearn> learnArrayList = new ArrayList<UserLearn>();

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private LinearLayoutManager linearLayoutManager;

    private String area_tong;
    private int credit_tong;
    private Integer credit_gae;
    private String data_tong;
    private String data_gae;
    static String final_dataTong = "";

    private FirebaseAuth auth; // ?????? ?????? ??????
    private DatabaseReference mDatabase;
    private DatabaseReference gyoDatabase;
    String mPath;


    private ListView listView;
    private ListView listview2;
    private ListViewAdapter adapterlist;

    private ReadAndWriteSnippets readAndWriteSnippets;

    public static Gyoyang newinstance(){
        return new Gyoyang();
    }
    public Gyoyang(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_gyoyang,null);

        ImageButton btn_back = (ImageButton)view.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment(Checklist.newinstance());
            }
        });

        //??????????????????.
        recyclerView = view.findViewById(R.id.re_jeongong2);//????????? ??????
        recyclerView.setHasFixedSize(true);//?????????????????? ??????????????????
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();//User ????????? ?????? ????????? ?????????(??????????????????)

        listView = view.findViewById(R.id.listview);
        listview2 = view.findViewById(R.id.listview2);
        learnArrayList = new ArrayList<>();






        Spinner spn_gyoyang = (Spinner)view.findViewById(R.id.spn_gyoyang);
        Spinner spn_areatong = (Spinner)view.findViewById(R.id.spn_areagyo);

        Spinner spn_tongcredit = (Spinner)view.findViewById(R.id.tonggyo_credit);
        EditText tong_name = (EditText)view.findViewById(R.id.tong_input);
        ImageButton btn_tong_save = (ImageButton)view.findViewById(R.id.btn_save_check_tong);

        Spinner spn_gaecredit = (Spinner)view.findViewById(R.id.gaegyo_credit);
        EditText gae_name = (EditText)view.findViewById(R.id.gae_input);
        ImageButton btn_gae_save = (ImageButton)view.findViewById(R.id.btn_save_check_gae);

        TextView txt_tongTitle = (TextView)view.findViewById(R.id.txt_title);

        listView = (ListView)view.findViewById(R.id.listview);
        listview2 = (ListView)view.findViewById(R.id.listview2);





        /* ------------------------------------------------???????????? ????????? DB??? ???????????? ----------------------------------------*/
        //?????? ?????? ????????? ?????? ?????? ??? ????????? ----> else if(position ==3) ?????? ????????? ?????? !!!!!!
        spn_areatong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    area_tong = "????????? ?????? ??????";
                }

                else if (position == 1) {
                    area_tong = "????????? ?????? ??????";
                }

                else if (position == 2) {
                    area_tong = "????????? ?????? ??????";
                }

                else if (position == 3) {
                    area_tong = "????????? ?????? ??????";
                }

                else if (position == 4) {
                    area_tong = "????????? ?????? ??????";
                }

                else if (position == 5) {
                    area_tong = "????????? ?????? ??????";
                }

                else if (position == 6) {
                    area_tong = "????????? ??????";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //?????? ?????? ????????? ?????? ?????? ??? ?????????
        spn_tongcredit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    credit_tong = 1;
                }

                else if (position == 1) {
                    credit_tong = 2;
                }

                else if (position == 2) {
                    credit_tong = 3;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        auth = FirebaseAuth.getInstance(); // ?????????????????? ?????? ?????? ?????????.
        mDatabase = FirebaseDatabase.getInstance().getReference();

        gyoDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser firebaseUser = auth.getCurrentUser();


        //?????? ???????????? ?????? ??? ?????? ????????? ????????????????????? ????????????.

        btn_tong_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                data_tong = tong_name.getText().toString();
                final_dataTong = data_tong;

                //?????????????????? Realtime database?????? UserInfo????????? ???, tongGyo????????? ???????????? ??? ?????? className????????? ??????. ????????? ??? ?????? className, area, credit?????? ????????????.
                mDatabase.child("UserInfo").child(firebaseUser.getUid()).child("finish").child(final_dataTong).child("className").setValue(data_tong);
                mDatabase.child("UserInfo").child(firebaseUser.getUid()).child("finish").child(final_dataTong).child("tongArea").setValue(area_tong);
                mDatabase.child("UserInfo").child(firebaseUser.getUid()).child("finish").child(final_dataTong).child("credit").setValue(credit_tong);
                mDatabase.child("UserInfo").child(firebaseUser.getUid()).child("finish").child(final_dataTong).child("area").setValue("tongGyo");
                tong_name.setText(null);




//


                adapterlist.notifyDataSetChanged();

            }
        });


        adapterlist = new ListViewAdapter(getActivity(), new ListViewAdapter.OnDeleteClickListener() {
            @Override
            public void onDelete(View v, int pos) {
                adapterlist.removeItem(pos);

                //RealTime DB?????? ?????? ??????????????? ??????.
                FirebaseUser firebaseUser = auth.getCurrentUser();
                DatabaseReference hopperRef = mDatabase.child("UserInfo").child(firebaseUser.getUid()).child("finish").child(final_dataTong);
                hopperRef.removeValue();
            }
        });

        listView.setAdapter(adapterlist);
        listview2.setAdapter(adapterlist);


        /* ---------------------------------------------------------------------------------------------------------------*/



        /* ------------------------------------------------???????????? ????????? DB??? ???????????? ----------------------------------------*/
        //?????? ?????? ????????? ?????? ?????? ??? ?????????
        spn_gaecredit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    credit_gae = 1;
                }

                else if (position == 1) {
                    credit_gae = 2;
                }

                else if (position == 2) {
                    credit_gae = 3;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //?????? ???????????? ?????? ??? ?????? ????????? ????????????????????? ????????????.
        btn_gae_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data_gae = gae_name.getText().toString();
                //?????????????????? Realtime database?????? UserInfo????????? ???, tongGyo????????? ???????????? ??? ?????? className????????? ??????. ????????? ??? ?????? className, area, credit?????? ????????????.
                mDatabase.child("UserInfo").child(firebaseUser.getUid()).child("finish").child(data_gae).child("className").setValue(data_gae);
                mDatabase.child("UserInfo").child(firebaseUser.getUid()).child("finish").child(data_gae).child("credit").setValue(credit_gae);
                mDatabase.child("UserInfo").child(firebaseUser.getUid()).child("finish").child(data_gae).child("area").setValue("gaeGyo");
                gae_name.setText(null);

                Log.e("data", data_gae);

            }
        });

        /* ---------------------------------------------------------------------------------------------------------------*/




        database = FirebaseDatabase.getInstance(); // ?????????????????? ?????????????????? ??????
        databaseReference = database.getReference("User"); // DB????????? ??????






        spn_gyoyang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    LinearLayout tong_linear = (LinearLayout) getActivity().findViewById(R.id.tong_cul);
                    tong_linear.setVisibility(View.INVISIBLE);

                    LinearLayout recyc_list = (LinearLayout) getActivity().findViewById(R.id.recyc_gyo_list);
                    recyc_list.setVisibility(View.VISIBLE);

                    LinearLayout input_window = (LinearLayout) getActivity().findViewById(R.id.input_window);
                    input_window.setVisibility(View.INVISIBLE);

                    LinearLayout input_window_gae = (LinearLayout) getActivity().findViewById(R.id.input_window_gae);
                    input_window_gae.setVisibility(View.INVISIBLE);

                    databaseReference.orderByChild("area").equalTo("a_necessary").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


                            User user = snapshot.getValue(User.class); // ???????????? User ????????? ???????????? ?????????.
                            arrayList.add(user); //?????? ??????????????? ?????????????????? ?????? ????????????????????? ????????????


                            adapter.notifyDataSetChanged();  // ????????? ?????? ??? ????????????
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            
                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    arrayList.clear();
                    adapter = new CustomAdapter(arrayList, getActivity());
                    recyclerView.setAdapter(adapter); //????????????????????? ???????????????

                } else if (position == 1) {
                    LinearLayout tong_linear = (LinearLayout) getActivity().findViewById(R.id.tong_cul);
                    tong_linear.setVisibility(View.INVISIBLE);

                    LinearLayout recyc_list = (LinearLayout) getActivity().findViewById(R.id.recyc_gyo_list);
                    recyc_list.setVisibility(View.VISIBLE);

                    LinearLayout input_window = (LinearLayout) getActivity().findViewById(R.id.input_window);
                    input_window.setVisibility(View.INVISIBLE);

                    LinearLayout input_window_gae = (LinearLayout) getActivity().findViewById(R.id.input_window_gae);
                    input_window_gae.setVisibility(View.INVISIBLE);

                    databaseReference.orderByChild("area").equalTo("basic").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            User user = snapshot.getValue(User.class); // ???????????? User ????????? ???????????? ?????????.
                            arrayList.add(user); //?????? ??????????????? ?????????????????? ?????? ????????????????????? ????????????


                            adapter.notifyDataSetChanged();  // ????????? ?????? ??? ????????????
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    arrayList.clear();
                    adapter = new CustomAdapter(arrayList, getActivity());
                    recyclerView.setAdapter(adapter); //????????????????????? ???????????????

                } else if (position == 2) {
                    LinearLayout tong_linear = (LinearLayout) getActivity().findViewById(R.id.tong_cul);
                    tong_linear.setVisibility(View.INVISIBLE);

                    LinearLayout recyc_list = (LinearLayout) getActivity().findViewById(R.id.recyc_gyo_list);
                    recyc_list.setVisibility(View.VISIBLE);

                    LinearLayout input_window = (LinearLayout) getActivity().findViewById(R.id.input_window);
                    input_window.setVisibility(View.INVISIBLE);

                    LinearLayout input_window_gae = (LinearLayout) getActivity().findViewById(R.id.input_window_gae);
                    input_window_gae.setVisibility(View.INVISIBLE);

                    databaseReference.orderByChild("area").equalTo("e_learning").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            User user = snapshot.getValue(User.class); // ???????????? User ????????? ???????????? ?????????.
                            arrayList.add(user); //?????? ??????????????? ?????????????????? ?????? ????????????????????? ????????????

                            adapter.notifyDataSetChanged();  // ????????? ?????? ??? ????????????
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    arrayList.clear();
                    adapter = new CustomAdapter(arrayList, getActivity());
                    recyclerView.setAdapter(adapter); //????????????????????? ???????????????

                } else if (position == 3) {
                    adapterlist.clear();
                    LinearLayout tong_linear = (LinearLayout) getActivity().findViewById(R.id.tong_cul);
                    tong_linear.setVisibility(View.VISIBLE);

                    LinearLayout recyc_list = (LinearLayout) getActivity().findViewById(R.id.recyc_gyo_list);
                    recyc_list.setVisibility(View.INVISIBLE);

                    LinearLayout input_window = (LinearLayout) getActivity().findViewById(R.id.input_window);
                    input_window.setVisibility(View.VISIBLE);

                    LinearLayout input_window_gae = (LinearLayout) getActivity().findViewById(R.id.input_window_gae);
                    input_window_gae.setVisibility(View.INVISIBLE);

                    mPath = "UserInfo/"+firebaseUser.getUid()+"/finish";
                    gyoDatabase = database.getReference("UserInfo");

                    gyoDatabase.child(firebaseUser.getUid()).child("finish").orderByChild("area").equalTo("tongGyo").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            UserLearn userLearn = snapshot.getValue(UserLearn.class);
                            adapterlist.addItem(userLearn.getTongArea(), userLearn.getClassName(), userLearn.getCredit());


                            adapterlist.notifyDataSetChanged();
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



//


                } else if (position == 4) {
                    adapterlist.clear();
                    LinearLayout tong_linear = (LinearLayout) getActivity().findViewById(R.id.tong_cul);
                    tong_linear.setVisibility(View.INVISIBLE);

                    LinearLayout recyc_list = (LinearLayout) getActivity().findViewById(R.id.recyc_gyo_list);
                    recyc_list.setVisibility(View.INVISIBLE);

                    LinearLayout input_window = (LinearLayout) getActivity().findViewById(R.id.input_window);
                    input_window.setVisibility(View.INVISIBLE);

                    LinearLayout input_window_gae = (LinearLayout) getActivity().findViewById(R.id.input_window_gae);
                    input_window_gae.setVisibility(View.VISIBLE);

                    gyoDatabase.child(firebaseUser.getUid()).child("finish").orderByChild("area").equalTo("gaeGyo").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            UserLearn userLearn = snapshot.getValue(UserLearn.class);
                            adapterlist.addItem("????????????", userLearn.getClassName(), userLearn.getCredit());

                            adapterlist.notifyDataSetChanged();
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    arrayList.clear();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        linearLayoutManager = new VariableScrollSpeedLinearLayoutManager(getActivity(), 100); // ????????? ?????? ??????




        return view;

    }
}