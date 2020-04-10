package com.example.tugas;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity{
    private static final Pattern PASSWORD =
            Pattern.compile("^" +
                    "(?=.*[A-Z])" +             //minimal 1 huruf besar
                    "(?=.*[a-z])" +             //minimal 1 huruf kecil
                    "(?=.*[0-9])" +             //minimal 1 digit angka
//                    "(?=.*[@#$%^&*+=,.?;:])" +  //minimal 1 special character
                    ".{8,}" + "$");             //minimal 8 character

    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    TextView tv_NamaDepan, tv_NamaBelakang, tv_TTL, tv_Alamat,
            tv_JK, tv_Agama, tv_NoTelp, tv_Email;
    EditText et_namadpn, et_namablkng, et_tmpt, et_tgl,
            et_alamat, et_telp, et_email, et_pwd, et_pwd2;
    String namaDpn, namaBlkng, tempat, tanggal, alamat,
            jenisKelamin, agama, telp, email;
    RadioGroup rg_jk, rg_agama, rg_agama1, rg_agama2;
    RadioButton rb_lk, rb_pr, rb_islam, rb_kristen, rb_katolik,
            rb_hindu, rb_budha, rb_konghucu, rb_alKep;
    Button btn_kembali, btn_daftar;
    AwesomeValidation validation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //id di activity_main.xml
        et_namadpn     = findViewById(R.id.nm_dpn);
        et_namablkng   = findViewById(R.id.nm_blkng);
        et_tmpt        = findViewById(R.id.tmpt_lahir);
        et_tgl         = findViewById(R.id.tgl_lahir);
        et_alamat      = findViewById(R.id.alamat);
        et_telp        = findViewById(R.id.telepon);
        et_email       = findViewById(R.id.email);
        et_pwd         = findViewById(R.id.addPassword);
        et_pwd2        = findViewById(R.id.addConfirmPassword);

        rg_jk          = findViewById(R.id.group_jk);
        rg_agama       = findViewById(R.id.group_agama);
        rg_agama1      = findViewById(R.id.group_agama1);
        rg_agama2      = findViewById(R.id.group_agama2);

        rb_lk          = findViewById(R.id.lk);
        rb_pr          = findViewById(R.id.pr);
        rb_islam       = findViewById(R.id.islam);
        rb_kristen     = findViewById(R.id.kristen);
        rb_katolik     = findViewById(R.id.katolik);
        rb_hindu       = findViewById(R.id.hindu);
        rb_budha       = findViewById(R.id.budha);
        rb_konghucu    = findViewById(R.id.konghucu);
        rb_alKep       = findViewById(R.id.aliran_kepercayaan);

        btn_kembali    = findViewById(R.id.kembali);
        btn_daftar     = findViewById(R.id.daftar);

        jenisKelamin = "";
        agama        = "";

        myCalendar      = Calendar.getInstance();

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        et_tgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(MainActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        validation = new AwesomeValidation(ValidationStyle.BASIC);

        validation.addValidation(this,R.id.nm_dpn,
                RegexTemplate.NOT_EMPTY,R.string.empty_namaDepan);
        validation.addValidation(this,R.id.nm_blkng,
                RegexTemplate.NOT_EMPTY,R.string.empty_namaBelakang);
        validation.addValidation(this,R.id.tmpt_lahir,
                RegexTemplate.NOT_EMPTY,R.string.empty_tempatLahir);
        validation.addValidation(this,R.id.tgl_lahir,
                RegexTemplate.NOT_EMPTY,R.string.empty_tglLahir);
        validation.addValidation(this,R.id.alamat,
                RegexTemplate.NOT_EMPTY,R.string.empty_alamat);
        validation.addValidation(this,R.id.telepon,
                RegexTemplate.NOT_EMPTY,R.string.empty_telepon);
        validation.addValidation(this,R.id.email,
                RegexTemplate.NOT_EMPTY,R.string.empty_email);
        validation.addValidation(this,R.id.email,
                Patterns.EMAIL_ADDRESS,R.string.invalid_email);
        validation.addValidation(this,R.id.addPassword,
                RegexTemplate.NOT_EMPTY,R.string.empty_password);
        validation.addValidation(this,R.id.addPassword,
                PASSWORD,R.string.invalid_password);
        validation.addValidation(this,R.id.addConfirmPassword,
                RegexTemplate.NOT_EMPTY,R.string.empty_confirmPassword);
        validation.addValidation(this,R.id.addConfirmPassword,
                R.id.addPassword,R.string.invalid_confirmpassword);

        btn_daftar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //showDialog();
                if (validation.validate()){
                    get_Text();
                }
                else{
                    Toast.makeText(getApplicationContext(),
                            "Mohon Isi Data Yang Kosong",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_kembali.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
                System.exit(0);
            }
        });
    }

    public void pesanErrorJK(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Terjadi Kesalahan");
        alertDialogBuilder
                .setMessage("Mohon pilih jenis kelamin")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void pesanErrorAgama(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Terjadi Kesalahan");
        alertDialogBuilder
                .setMessage("Mohon pilih agama")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void onRadioButtonClicked(View view){
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()){
            case R.id.islam:
                if (checked){
                    agama = "Islam";
                    rg_agama2.clearCheck();
                }
                break;
            case R.id.kristen:
                if (checked){
                    agama = "Kristen";
                    rg_agama1.clearCheck();
                }
                break;
            case R.id.katolik:
                if (checked){
                    agama = "Katholik";
                    rg_agama2.clearCheck();
                }
                break;
            case R.id.hindu:
                if (checked){
                    agama = "Hindu";
                    rg_agama1.clearCheck();
                }
                break;
            case R.id.budha:
                if (checked){
                    agama = "BUdha";
                    rg_agama2.clearCheck();
                }
                break;
            case R.id.konghucu:
                if (checked){
                    agama = "Konghucu";
                    rg_agama1.clearCheck();
                }
                break;
            case R.id.aliran_kepercayaan:
                if (checked){
                    agama = "Aliran Kepercayaan";
                    rg_agama2.clearCheck();
                }
                break;

            case R.id.lk:
                if (checked){
                    jenisKelamin = "Laki-laki";
                }
                break;
            case R.id.pr:
                if (checked){
                    jenisKelamin = "Perempuan";
                }
                break;
        }
    }

    private void updateLabel() {
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        et_tgl.setText(sdf.format(myCalendar.getTime()));
    }

    public void get_Text(){
        namaDpn = et_namadpn.getText().toString();
        namaBlkng = et_namablkng.getText().toString();
        tempat = et_tmpt.getText().toString();
        tanggal = et_tgl.getText().toString();
        alamat = et_alamat.getText().toString();
        telp = et_telp.getText().toString();
        email = et_email.getText().toString();

        if (jenisKelamin.equals("")){
            pesanErrorJK();
        }
        else if (agama.equals("")){
            pesanErrorAgama();
        }
        else{
            showDialog();
        }
    }

    private void showDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // set title dialog
        alertDialogBuilder.setTitle("Konfirmasi...");
        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Apakah data yang anda masukkan sudah benar?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        showDetailPendaftaran();
                    }
                })
                .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // jika tombol ini diklik, akan menutup dialog
                        // dan tidak terjadi apa2
                        dialog.cancel();
                    }
                });
        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();
        // menampilkan alert dialog
        alertDialog.show();
    }

    private void showDetailPendaftaran(){
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.detail_pendaftaran, null);

        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Detail Pendaftaran");
        dialog.setContentView(R.layout.detail_pendaftaran);

        tv_NamaDepan = dialogView.findViewById(R.id.hasil_nmDepan);
        tv_NamaBelakang = dialogView.findViewById(R.id.hasil_nmBelakang);
        tv_TTL = dialogView.findViewById(R.id.hasil_ttl);
        tv_Alamat = dialogView.findViewById(R.id.hasil_alamat);
        tv_JK = dialogView.findViewById(R.id.hasil_jk);
        tv_Agama = dialogView.findViewById(R.id.hasil_agama);
        tv_NoTelp = dialogView.findViewById(R.id.hasil_noTelp);
        tv_Email = dialogView.findViewById(R.id.hasil_email);

        //merubah data textView di detail_pendaftaran menjadi isi dari editText
        tv_NamaDepan.setText(namaDpn);
        tv_NamaBelakang.setText(namaBlkng);
        tv_TTL.setText(tempat + ", " + tanggal);
        tv_Alamat.setText(alamat);
        tv_JK.setText(jenisKelamin);
        tv_Agama.setText(agama);
        tv_NoTelp.setText(telp);
        tv_Email.setText(email);

        //agar dialog tidak hilang saat di click di area luar dialog
        dialog.setCanceledOnTouchOutside(false);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        dialog.getWindow().setLayout((7 * width) / 7, LinearLayout.LayoutParams.WRAP_CONTENT);
        Button btnKeluar = (Button) dialogView.findViewById(R.id.keluar);
        Button btnOke = (Button) dialogView.findViewById(R.id.oke);
        btnOke.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v) {
                Toast.makeText(MainActivity.this, "Pendaftaran Selesai", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        btnKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
        //menampilkan custom dialog
        dialog.show();
    }
}