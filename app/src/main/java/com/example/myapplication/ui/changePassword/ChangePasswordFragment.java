package com.example.myapplication.ui.changePassword;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.myapplication.R;
import com.example.myapplication.database.AppDatabase;
import com.example.myapplication.database.User;

public class ChangePasswordFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_change_password, container, false);

        final EditText currentPass;
        final EditText newPass;
        final EditText confirmPass;
        final Button btn_change;
        final Button btn_home;

        AppDatabase db = AppDatabase.getDatabase(getContext());

        //get username
        SharedPreferences sharedPref = getContext().getSharedPreferences("NOTE_SHARED_PREFERENCE"
                , Context.MODE_PRIVATE);

        String username = sharedPref.getString("username","Default name");


        currentPass = (EditText)root.findViewById(R.id.ed_current_password);
        newPass = (EditText)root.findViewById(R.id.ed_new_password);
        confirmPass = (EditText)root.findViewById(R.id.ed_confirm_password);
        btn_change = (Button)root.findViewById(R.id.btn_changepass_change);
        btn_home = (Button)root.findViewById(R.id.btn_changepass_home);

        btn_home.setOnClickListener(v -> {
            Navigation.findNavController(root).navigate(R.id.action_changePasswordFragment_to_nav_home);
        });

        btn_change.setOnClickListener(v -> {
            db.getQueryExecutor().execute(() -> {
                User usr = db.userDao().loadByEmail(username);
                String pass = currentPass.getText().toString();
                if(!pass.equals(usr.password)){
                    getActivity().runOnUiThread(()->{
                        currentPass.setError("Mật khẩu hiện tại không đúng");
                    });
                    return;
                }
                if(!confirmPass.getText().toString().equals(newPass.getText().toString())){
                    getActivity().runOnUiThread(()->{
                        confirmPass.setError("Mật khẩu không giống");
                    });
                    return;
                }
                usr.password = newPass.getText().toString();
                db.userDao().update(usr);
                getActivity().runOnUiThread(()->{
                    Toast.makeText(getContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(root).navigate(R.id.action_changePasswordFragment_to_nav_home);
                });
            });

        });

        currentPass.setText("");
        newPass.setText("");
        confirmPass.setText("");



        return root;
    }
}