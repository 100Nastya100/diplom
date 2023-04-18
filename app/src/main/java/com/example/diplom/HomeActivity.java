package com.example.diplom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.diplom.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity {

    Button signInButton, registerButton;

    ConstraintLayout rootElement;
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        rootElement = findViewById(R.id.root_element);
        signInButton = findViewById(R.id.sign_in_button);
        registerButton = findViewById(R.id.register_button);
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");

    }

    public void showRegisterWindow(View view) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("Регистрация");
        dialog.setMessage("Введите ваши данные");

        LayoutInflater inflater = LayoutInflater.from(this);
        View register_window = inflater.inflate(R.layout.register_window, null);
        dialog.setView(register_window);

        final EditText name = register_window.findViewById(R.id.name);
        final EditText fam = register_window.findViewById(R.id.fam);
        final EditText tel = register_window.findViewById(R.id.tel);
        final EditText email = register_window.findViewById(R.id.email);
        final EditText password = register_window.findViewById(R.id.password);

        dialog.setNegativeButton("Закрыть", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });

        dialog.setPositiveButton("Зарегистрироваться", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (TextUtils.isEmpty(name.getText().toString())) {
                    Snackbar.make(rootElement, "Введите имя", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(fam.getText().toString())) {
                    Snackbar.make(rootElement, "Введите фамилию", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(tel.getText().toString())) {
                    Snackbar.make(rootElement, "Введите номер телефона ", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email.getText().toString())) {
                    Snackbar.make(rootElement, "Введите email", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if (password.getText().toString().length() < 6) {
                    Snackbar.make(rootElement, "Введите пароль, который более 5 символов", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                auth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Пользователь успешно создан
                                User user = new User();
                                user.setName(name.getText().toString());
                                user.setFam(fam.getText().toString());
                                user.setTel(tel.getText().toString());
                                user.setEmail(email.getText().toString());
                                user.setPassword(password.getText().toString());
                                users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user);

                                Snackbar.make(rootElement, "Регистрация прошла успешно", Snackbar.LENGTH_SHORT).show();
                                auth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        startActivity(new Intent(HomeActivity.this, CustomActivity.class));
                                        finish();
                                    }
                                });
                            } else {
                                // Произошла ошибка
                                Snackbar.make(rootElement, "Ошибка регистрации: " + task.getException().getMessage(), Snackbar.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        dialog.show();
    }


    public void showSignInWindow(View view) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Вход в аккаунт");
        dialog.setMessage("Введите почту и пароль");

        LayoutInflater inflater = LayoutInflater.from(this);
        View signInWindow = inflater.inflate(R.layout.sign_in_window, null);
        dialog.setView(signInWindow);

        final EditText email = signInWindow.findViewById(R.id.email);
        final EditText password = signInWindow.findViewById(R.id.password);

        dialog.setNegativeButton("Закрыть", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });

        dialog.setPositiveButton("Войти в аккаунт", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (TextUtils.isEmpty(email.getText().toString())) {
                    Snackbar.make(rootElement, "Введите email", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if (password.getText().toString().length() < 5) {
                    Snackbar.make(rootElement, "Введите пароль, который более 5 символов", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                auth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        startActivity(new Intent(HomeActivity.this, CustomActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(rootElement, "Ошибка авторизации. " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        });
        dialog.show();
    }
}