package androdev.sqlitetut.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import androdev.sqlitetut.DBHandler;
import androdev.sqlitetut.R;
import androdev.sqlitetut.model.Model;

public class Activity_add extends AppCompatActivity {

    DatePickerDialog.OnDateSetListener dateSetListener;
    Calendar calendar = Calendar.getInstance();

    Button btnSave,
            btnBack;
    EditText editName,
            editGender,
            editAddress,
            editDateBirth;

    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        setupLayout();
    }

    /**
     * CUSTOM METHOD START HERE
     * -------------------------
     */
    private void setupLayout() {
        dbHandler = new DBHandler(this);
        btnSave = (Button) findViewById(R.id.btn_save);
        btnBack = (Button) findViewById(R.id.btn_back);
        editName = (EditText) findViewById(R.id.edit_name);
        editAddress = (EditText) findViewById(R.id.edit_address);
        editDateBirth = (EditText) findViewById(R.id.edit_date_birth);
        editGender = (EditText) findViewById(R.id.edit_gender);

        //set listener of Date Calendar
        dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                //set format date of calendar when picking number
                setFormatDate();
            }
        };

        //set action when editText on dateBirth clicked
        editDateBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //set to hide virtual keyboard when picking date
                v = Activity_add.this.getCurrentFocus();
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                assert v != null;
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

                new DatePickerDialog(
                        Activity_add.this,
                        dateSetListener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                ).show();
            }
        });

        //set action components
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity_add.this.finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check for EMPTY data inside fields
                String messageOfError = "";
                if (isEmptyData()) {
                    messageOfError = getResources().getString(R.string.txt_warn_neg);
                } else {
                    if ((editGender.getText().toString().equals("M") || editGender.getText().toString().equals("m") ||
                            editGender.getText().toString().equals("F") || editGender.getText().toString().equals("f")) &&
                            editGender.getText().length() > 0) {

                        //checking correctness of editName
                        if (editName.getText().length() > 0) {

                            //checking correctness of dateBirth
                            if (editDateBirth.getText().length() > 9) {

                                //starting to add data into database
                                //in the background
                                Preparing preparing = new Preparing(
                                        editName.getText().toString(),
                                        editAddress.getText().toString(),
                                        editDateBirth.getText().toString(),
                                        editGender.getText().toString()
                                );
                                preparing.execute();
                                Activity_add.this.finish();
                            } else {
                                messageOfError = getResources().getString(R.string.txt_failed_confirmation_dateBirth);
                            }
                        } else {
                            messageOfError = getResources().getString(R.string.txt_failed_confirmation_name);
                        }
                    } else {
                        messageOfError = getResources().getString(R.string.txt_failed_confirmation_gender);
                    }
                }
                Snackbar.make(view, messageOfError, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * method for checking field data is empty or not
     *
     * @return boolean
     */
    private boolean isEmptyData() {
        //one of the fields cannot be EMPTY
        if (editName.getText().toString().equals("") ||
                editAddress.getText().toString().equals("") ||
                editDateBirth.getText().toString().equals("") ||
                editGender.getText().toString().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * method for set format of Date based on Calendar
     */
    private void setFormatDate() {
        String formatDate = "dd/MM/yyyy";//format setting of date in calendar
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate, Locale.US);

        //set into editText editDateBirth
        editDateBirth.setText(simpleDateFormat.format(calendar.getTime()));
    }

    /**
     * ASYNCTASK CLASS START HERE
     * --------------------------
     */
    class Preparing extends AsyncTask {

        String name, address, dateBirth, gender;

        public Preparing(String name, String address, String dateBirth, String gender) {
            super();
            this.name = name;
            this.address = address;
            this.dateBirth = dateBirth;
            this.gender = gender;
        }

        @Override
        protected Object doInBackground(Object[] params) {
            dbHandler.addDataModel(new Model(name, address, dateBirth, gender));
            return null;
        }
    }
}
