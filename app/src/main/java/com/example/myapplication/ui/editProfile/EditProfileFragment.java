package com.example.myapplication.ui.editProfile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.myapplication.R;
import com.example.myapplication.database.AppDatabase;
import com.example.myapplication.database.User;

public class EditProfileFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        final EditText firstName;
        final EditText lastName;
        final EditText email;
        final Button btn_change;
        final Button btn_home;

        AppDatabase db = AppDatabase.getDatabase(getContext());

        //get username
        SharedPreferences sharedPref = getContext().getSharedPreferences("NOTE_SHARED_PREFERENCE"
                , Context.MODE_PRIVATE);

        String username = sharedPref.getString("username","Default name");


        firstName = (EditText)root.findViewById(R.id.ed_edit_firstname);
        lastName = (EditText)root.findViewById(R.id.ed_edit_lastname);
        email = (EditText)root.findViewById(R.id.ed_edit_email);
        btn_change = (Button)root.findViewById(R.id.btn_profile_change);
        btn_home = (Button)root.findViewById(R.id.btn_profile_home);

        btn_home.setOnClickListener(v -> {
            Navigation.findNavController(root).navigate(R.id.action_editProfileFragment_to_nav_home);
        });

        btn_change.setOnClickListener(v -> {
            db.getQueryExecutor().execute(() -> {
                User usr = db.userDao().loadByEmail(username);

                if(!email.getText().toString().equals(usr.email)){
                    getActivity().runOnUiThread(()->{
                        firstName.setError("Email đã được đăng ký");
                    });
                    return;
                }

                usr.firstName = firstName.getText().toString();
                usr.lastName = lastName.getText().toString();
                usr.email = email.getText().toString();
                db.userDao().update(usr);

                getActivity().runOnUiThread(()->{
                    Toast.makeText(getContext(), "Sửa thông tin thành công", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(root).navigate(R.id.action_editProfileFragment_to_nav_home);
                });
            });

        });

        firstName.setText("");
        lastName.setText("");
        email.setText("");



        return root;
    }
}