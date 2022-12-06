package com.studio.matchtune.fragment;

import static com.studio.matchtune.utility.Common.isConnected;
import static com.studio.matchtune.utility.Common.showToast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.fragment.app.DialogFragment;

import android.os.Environment;
import android.provider.CalendarContract;
import android.provider.Settings;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.coremedia.iso.boxes.Container;
import com.google.gson.Gson;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;
import com.studio.matchtune.Api.ApiInterface;
import com.studio.matchtune.ResponceModel.LoginRoot;
import com.studio.matchtune.ResponceModel.MusicResponce;
import com.studio.matchtune.activity.MainActivity;
import com.studio.matchtune.activity.R;
import com.studio.matchtune.activity.activity_splash;
import com.studio.matchtune.inputModel.AttributeModel;
import com.studio.matchtune.inputModel.DataModel;
import com.studio.matchtune.inputModel.responcedata;
import com.studio.matchtune.utility.ApplicationConstant;
import com.studio.matchtune.utility.utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.util.HashMap;
import java.util.Map;
import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class fragment_login extends DialogFragment  implements CompoundButton.OnCheckedChangeListener {
    ApiInterface apiInterface;
    private EditText mEditText,txtPassword;
    private ImageView arrowcircle;
    private Button submit;
    String useremail;
    GifImageView loader;
    RequestQueue queue;
    TextView  loginmsg;


    public AppCompatCheckBox rememberme,keepsignin;

    public String finder;
    boolean checkremember  = false;
    boolean isToggledRadio1;
    ImageView  removeuser,removepwd;
    public static fragment_login newInstance(String title) {
        fragment_login frag = new fragment_login();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Retrofit retrofit = com.studio.matchtune.Api.ApiClient.getclient();
        apiInterface = retrofit.create(com.studio.matchtune.Api.ApiInterface.class);

        return inflater.inflate(R.layout.fragment_login, container);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEditText = (EditText) view.findViewById(R.id.txtUserName);
        txtPassword = (EditText) view.findViewById(R.id.txtPassword);
        loginmsg = (TextView) view.findViewById(R.id.loginmsg);
        removeuser = (ImageView) view.findViewById(R.id.removeuser);
        removepwd = (ImageView) view.findViewById(R.id.removepwd);

        submit = (Button) view.findViewById(R.id.submit);
        loader = (GifImageView) view.findViewById(R.id.loaderlogin);
     //   groupradio = (RadioGroup) view.findViewById(R.id.groupradio);

        rememberme = (AppCompatCheckBox) view.findViewById(R.id.rememberme);
        keepsignin = (AppCompatCheckBox) view.findViewById(R.id.keepsignin);
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
       // mEditText.requestFocus();
        arrowcircle = (ImageView) view.findViewById(R.id.arrowcircle);
        // getDialog().getWindow().setSoftInputMode(
        //     WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        submit.setBackgroundResource(R.drawable.btn_background_false);
        removeuser.setVisibility(View.GONE);
        removepwd.setVisibility(View.GONE);
        arrowcircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });



        SharedPreferences shrd = getContext().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String userEmailid = shrd.getString("email", "");
        String userpassword = shrd.getString("pwd", "");
        String finders = shrd.getString("finder", "");
        mEditText.setText(userEmailid);
        txtPassword.setText(userpassword);
        if (finders.length() > 1) {
            rememberme.setChecked(true);
        } else {
            keepsignin.setChecked(true);
        }

        finder = finders;




        //Open this
//        if (Environment.isExternalStorageManager()){
//
//// If you don't have access, launch a new activity to show the user the system's dialog
//// to allow access to the external storage
//        }else{
//            Intent intent = new Intent();
//            intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
//           // open this
////            Uri uri = Uri.fromParts("package", this.getPackageName(), null);
////            intent.setData(uri);
////            startActivity(intent);
//        }


//        groupradio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int i) {
//
//                if (i == R.id.rememberme) {
//                    finder = "rememberme";
//
//
//                } else if (i == R.id.keepsignin) {
//                   finder = "keepsignin";
//
//                } else {
//                    finder = "";
//                }
//                Log.d("testfinder","finder: "+ finder );
//
//            }
//        });


//           if(mEditText.isFocused()){
//              Log.d("testfocus","is focused");
//           }else{
//               Log.d("testfocus","is not focused");
//           }




        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                removeuser.setVisibility(View.VISIBLE);
                Log.d("testfocus","is before text");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                removeuser.setVisibility(View.VISIBLE);
                Log.d("testfocus","on text ");

            }

            @Override
            public void afterTextChanged(Editable s) {
                removeuser.setVisibility(View.VISIBLE);
                Log.d("testfocus","after text");
                String email = mEditText.getText().toString();
                if (utility.isValidEmail(email) && txtPassword.getText().toString().length() > 6) {
                    Log.d("emailvalid", "email vaild");
                    submit.setBackgroundResource(R.drawable.btn_background_true);
                } else {
                    Log.d("emailvalidno", "email not vaild");
                    submit.setBackgroundResource(R.drawable.btn_background_false);
                }

            }
        });


        txtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                removepwd.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                removepwd.setVisibility(View.VISIBLE);
                String email = mEditText.getText().toString();
                String pwd = txtPassword.getText().toString();
                if (utility.isValidEmail(email) && txtPassword.getText().toString().length() > 6) {
                    submit.setBackgroundResource(R.drawable.btn_background_true);
                    Log.d("emailvalidpwd", "password vaild");
                } else {
                    Log.d("emailvalidnopwd", "password no vaild");
                    submit.setBackgroundResource(R.drawable.btn_background_false);
                }

            }
        });


        removeuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditText.setText("");
                removeuser.setVisibility(View.GONE);
            }
        });

        removepwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtPassword.setText("");
                removepwd.setVisibility(View.GONE);
            }
        });



        isToggledRadio1 = false;

//             groupradio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                 @Override
//                 public void onCheckedChanged(RadioGroup group, int i) {
//
//                     if (i == R.id.rememberme) {
//                         finder = "rememberme";
//
//                         if(rememberme.isChecked()){
//                             rememberme.setActivated(false);
//                             Log.d("testfinder","finder: iffff" );
//                         }
//                         else{
//                             rememberme.setActivated(true);
//                             Log.d("testfinder","finder: false12" );
//                         }
//
//                     }
//                     Log.d("testfinder","finder: "+ finder );
//
//                 }
//             });


            rememberme.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (rememberme.isActivated() == true) {
                        rememberme.setActivated(false);
                        rememberme.setChecked(false);
                        rememberme.setSelected(false);
                        finder = "";
                        Log.d("remembercheck","rem");
                    } else {
                        rememberme.setChecked(true);
                        rememberme.setSelected(true);
                        rememberme.setActivated(true);
                        finder = "rememberme";

                        Log.d("remembercheck","rem not " + finder);
                    }


                }
            });

            keepsignin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (keepsignin.isActivated() == true) {
                        keepsignin.setActivated(false);
                        keepsignin.setChecked(false);
                        keepsignin.setSelected(false);
                        finder = "";
                        Log.d("remembercheck","rem");
                    } else {
                        keepsignin.setChecked(true);
                        keepsignin.setSelected(true);
                        keepsignin.setActivated(true);
                        finder = "keepsignin";

                        Log.d("remembercheck","rem not ");
                    }
                }
            });


            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mEditText.getText().toString().length() == 0) {
                        if (txtPassword.getText().toString().length() > 0) {
                            txtPassword.setBackgroundResource(R.drawable.edittext_noborder);
                        }
                        mEditText.setBackgroundResource(R.drawable.edittext_noborder);
                    } else if (txtPassword.getText().toString().length() == 0) {

                        if (mEditText.getText().toString().length() > 0) {
                            mEditText.setBackgroundResource(R.drawable.edittext_noborder);
                        }
                        txtPassword.setBackgroundResource(R.drawable.edittext_noborder);
                    } else {
                        txtPassword.setBackgroundResource(R.drawable.edittext_noborder);
                        mEditText.setBackgroundResource(R.drawable.edittext_noborder);

                        UserLogin(mEditText.getText().toString(), txtPassword.getText().toString());

//                    try {
//                        makeLoginRequest(mEditText.getText().toString(), txtPassword.getText().toString());
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }


                        File sdCard = Environment.getExternalStorageDirectory(); //path returns "/mnt/sdcard"
                        File videofile = new File(sdCard.getAbsolutePath() + "/Android/data/match_tune/mm.mp4");
                        File audiofile = new File(sdCard.getAbsolutePath() + "/Android/data/match_tune/test.m4a");
                        File finalfile = new File(sdCard.getAbsolutePath() + "/Android/data/match_tune/final.mp4");
                        // dir.mkdirs();
                        File file = new File(videofile.getPath());
                        if (file.exists()) {
                            Log.i(file.getAbsolutePath(), "file existe! ");
                        } else {
                            Log.i("Archivo", "No existe! ");
                        }
                        mux(videofile.getPath(), audiofile.getPath(), finalfile.getAbsolutePath());
                    }
                }
            });


        }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        if (isChecked) {
//            if (rememberme.isChecked()) {
//                rememberme.setChecked(true);
//
//            }
//            if (keepsignin.isChecked()) {
//                keepsignin.setChecked(true);
//
//            }
//
//        }else{
//            if (!rememberme.isChecked()) {
//                rememberme.setChecked(false);
//
//            }
//            if (!keepsignin.isChecked()) {
//                keepsignin.setChecked(false);
//
//            }
//
//        }
    }

    public void UserLogin(String mail, String pass) {
        mEditText.onEditorAction(EditorInfo.IME_ACTION_DONE);
        txtPassword.onEditorAction(EditorInfo.IME_ACTION_DONE);
        loginmsg.setVisibility(View.VISIBLE);
        loader.setVisibility(View.VISIBLE);

        responcedata responcedata = new responcedata();
        DataModel data = new DataModel();
//        ArrayList<String>  arrayList=new ArrayList<>();
//        arrayList.add("Blues");
        data.setType("tokens");
        AttributeModel attributedata=new AttributeModel();
        data.setAttributes(attributedata);
        responcedata.setData(data);
//        arrayList.add("nsdkj");
        attributedata.setEmail(mail);
        attributedata.setPassword(pass);
        Log.d("testing","dfsfadsfl"+ new Gson().toJson(responcedata));
        Log.d("apihitsssd","api responce");

        apiInterface.getUserLogin(responcedata).enqueue(new Callback<LoginRoot>() {
            @Override
            public void onResponse(Call<LoginRoot> call, retrofit2.Response<LoginRoot> response) {

                try {
                    if (response != null & response.code()<201) {
//                        Log.d("apihitres","responce is"+ new Gson().toJson(response));
                        Log.e("apihitdatarescode","api responce is: "+ response.code());
                        Log.d("apihitsssdres","api responce: "+ response);
                        Log.d("apihitsssdres","api responce: "+ response.body().getData().getAttributes().getValue());
                        String token  = response.body().getData().getAttributes().getValue();
                        SharedPreferences.Editor editor = getContext().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE).edit();
                        editor.putString("usertoken",token);
                        editor.putString("useremail",mEditText.getText().toString());
                        editor.putString("useremails",mEditText.getText().toString());
                        editor.putString("userpwd",txtPassword.getText().toString());
                        Log.d("your token is","token is :  " +token );
                        Log.d("your email is","email is :  " +mEditText.getText().toString() );
                        editor.apply();

                        if(finder.equals("rememberme")){
                            SharedPreferences.Editor editors = getContext().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE).edit();
                            editors.putString("email",mEditText.getText().toString());
                            editors.putString("pwd",txtPassword.getText().toString());
                            editors.putString("finder","rememberme");
                            editors.apply();
                        }else{

                            SharedPreferences prefs = getContext().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
                            SharedPreferences.Editor prefsEditor = prefs.edit();
                            prefsEditor.remove("email");
                            prefsEditor.remove("pwd");
                            prefsEditor.remove("finder");
                            prefsEditor.apply();

                        }

                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);

                    }
                    else{
                        Toast.makeText(getActivity(), "Authentication error", Toast.LENGTH_SHORT).show();
                        loader.setVisibility(View.GONE);
                    }

                } catch (Exception e) {
                    Log.e("exp", e.getLocalizedMessage());
                    loader.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Authentication error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginRoot> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());
            }
        });

//
    }




    private void makeLoginRequest(String mail, String pass) throws JSONException {

        if(isConnected(getActivity().getApplicationContext()))
        {
            JSONObject attObj = new JSONObject();

            attObj.put("email", "test789690@gmail.com");
            attObj.put("password", "password");
            JSONObject dataObj = new JSONObject();
            dataObj.put("type","tokens");
            dataObj.put("attributes", attObj);

            JSONObject jobj = new JSONObject();
            jobj.put("data", dataObj);

            final JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, "https://api.matchtune.com/tokens", jobj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {
                        String value = jsonObject.getJSONObject("data").getJSONObject("attributes").getString("value");
                        Log.d("cheklogin","userlogin: "  + value);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("testingerror","Volley error Test");
                    Toast.makeText(getActivity().getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();

                }
            });
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Volley.newRequestQueue(getActivity().getApplicationContext()).add(stringRequest);

        }
        else
        {
            showToast(getActivity().getApplicationContext(), "Please check your internet connection");
        }

    }


    private void makeRegisterRequest(String mail, String pass) throws JSONException {

        if(isConnected(getActivity().getApplicationContext()))
        {
            JSONObject attObj = new JSONObject();

            attObj.put("email", "test789690@gmail.com");
            attObj.put("password", "password");
            attObj.put("confirmation", "password");
            attObj.put("tos", true);

            JSONObject dataObj = new JSONObject();
            dataObj.put("type","accounts");
            dataObj.put("attributes", attObj);

            JSONObject jobj = new JSONObject();
            jobj.put("data", dataObj);

            final JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, "https://api.matchtune.com/accounts", jobj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {
                        String value = jsonObject.getJSONObject("data").getJSONObject("attributes").getString("value");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity().getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();

                }
            });
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Volley.newRequestQueue(getActivity().getApplicationContext()).add(stringRequest);

//            RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
//            String url = "https://api.matchtune.com/accounts";
//            JSONObject attObj = new JSONObject();
//            attObj.put("email", "test78967@gmail.com");
//            attObj.put("password", "password");
//            attObj.put("confirmation", "password");
//            attObj.put("tos", true);
//
//            JSONObject dataObj = new JSONObject();
//            dataObj.put("type","accounts");
//            dataObj.put("attributes", attObj);
//
//            JSONObject jobj = new JSONObject();
//            jobj.put("data", dataObj);
//            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jobj, new Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            try {
//                                if (response.has("token")) {
//                                    String token = response.getString("token");
//                                    Log.d("token: " , token); // Present Key
//                                }
//                                else if(response.has("error")){
//                                    String error = response.getString("error");
//                                    Log.d("error: " , error); // Present Key
//
//                                }
//                            }
//                            catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Toast.makeText(getActivity().getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
//                }
//            });
//            requestQueue.setRetrive(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//            MyVolly.getInstance(getActivity().getApplicationContext()).addToRequestQueue(requestQueue);
//
//            requestQueue.add(request);
        }
        else
        {
            showToast(getActivity().getApplicationContext(), "Please check your internet connection");
        }

    }


    public boolean mux(String videoFile, String audioFile, String outputFile) {
        Movie video;
        try {
            video = new MovieCreator().build(videoFile);

        } catch (RuntimeException e) {
            e.printStackTrace();

            return false;
        } catch (IOException e) {
            e.printStackTrace();

            return false;
        }

        Movie audio;
        try {

            audio = new MovieCreator().build(audioFile);

        } catch (IOException e) {
            e.printStackTrace();

            return false;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }

        Track audioTrack = audio.getTracks().get(0);
        video.addTrack(audioTrack);

        Container out = new DefaultMp4Builder().build(video);

        FileOutputStream fos;
        try {
            fos = new FileOutputStream(outputFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        BufferedWritableFileByteChannel byteBufferByteChannel = new BufferedWritableFileByteChannel(fos);
        try {

            out.writeContainer(byteBufferByteChannel);
            byteBufferByteChannel.close();
            Log.e("Audio Video", "11");
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }



    private static class BufferedWritableFileByteChannel implements WritableByteChannel {
        //    private static final int BUFFER_CAPACITY = 1000000;
        private static final int BUFFER_CAPACITY = 10000000;

        private boolean isOpen = true;
        private final OutputStream outputStream;
        private final ByteBuffer byteBuffer;
        private final byte[] rawBuffer = new byte[BUFFER_CAPACITY];

        private BufferedWritableFileByteChannel(OutputStream outputStream) {
            this.outputStream = outputStream;
            this.byteBuffer = ByteBuffer.wrap(rawBuffer);
            Log.e("Audio Video", "13");
        }
        @Override
        public int write(ByteBuffer inputBuffer) throws IOException {
            int inputBytes = inputBuffer.remaining();

            if (inputBytes > byteBuffer.remaining()) {
                Log.e("Size ok ", "song size is ok");
                dumpToFile();
                byteBuffer.clear();

                if (inputBytes > byteBuffer.remaining()) {
                    Log.e("Size ok ", "song size is not okssss ok");
                    throw new BufferOverflowException();
                }
            }

            byteBuffer.put(inputBuffer);

            return inputBytes;
        }

        @Override
        public boolean isOpen() {
            return isOpen;
        }
        @Override
        public void close() throws IOException {
            dumpToFile();
            isOpen = false;
        }

        private void dumpToFile() {
            try {
                outputStream.write(rawBuffer, 0, byteBuffer.position());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}