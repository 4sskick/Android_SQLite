package androdev.sqlitetut.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androdev.sqlitetut.DBHandler;
import androdev.sqlitetut.R;
import androdev.sqlitetut.model.Model;
import androdev.sqlitetut.util.Constant;

public class Activity_detail extends AppCompatActivity {

    DatePickerDialog.OnDateSetListener dateSetListener;
    Calendar calendar = Calendar.getInstance();

    DBHandler dbHandler;
    List<Model> modelList;

    Button btnSave,
            btnUpdate,
            btnCancel,
            btnDelete;
    EditText editName,
            editDateBirth,
            editGender,
            editAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setupLayout();
        Log.d(Constant.TAG, "onCreate: " + getIntent().getStringExtra(Constant.KEY_NAME) + "-" + getIntent().getIntExtra(Constant.KEY_ID, 0));
    }

    /**
     * CUSTOM METHOD START HERE
     * ------------------------
     */
    private void setupLayout() {
        dbHandler = new DBHandler(this);
        btnCancel = (Button) findViewById(R.id.btn_detail_cancel);
        btnSave = (Button) findViewById(R.id.btn_detail_save);
        btnUpdate = (Button) findViewById(R.id.btn_detail_update);
        btnDelete = (Button) findViewById(R.id.btn_detail_delete);
        editName = (EditText) findViewById(R.id.edit_detail_name);
        editDateBirth = (EditText) findViewById(R.id.edit_detail_date_birth);
        editGender = (EditText) findViewById(R.id.edit_detail_gender);
        editAddress = (EditText) findViewById(R.id.edit_detail_address);

        //set data from database
        modelList = dbHandler.getSummaryDataModelWhere(getIntent().getStringExtra(Constant.KEY_NAME));
        for (Model model : modelList) {
            editName.setText(model.getName());
            editDateBirth.setText(model.getDate_birth());
            editGender.setText(model.getGender());
            editAddress.setText(model.getAddress());
        }

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
                v = Activity_detail.this.getCurrentFocus();
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                assert v != null;
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

                new DatePickerDialog(
                        Activity_detail.this,
                        dateSetListener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                ).show();
            }
        });

        //set action for components
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //set visibility
                v.setVisibility(View.GONE);
                btnDelete.setVisibility(View.GONE);
                btnSave.setVisibility(View.VISIBLE);
                btnCancel.setVisibility(View.VISIBLE);

                //configure editText
                editName.setEnabled(true);
                editDateBirth.setEnabled(true);
                editGender.setEnabled(true);
                editAddress.setEnabled(true);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //starting to check correctness value of data
                String messageOfError = "";
                if ((editGender.getText().toString().equals("M") || editGender.getText().toString().equals("m") ||
                        editGender.getText().toString().equals("F") || editGender.getText().toString().equals("f")) &&
                        editGender.getText().length() > 0) {

                    //checking correctness of editName
                    if (editName.getText().length() > 0) {

                        //checking correctness of dateBirth
                        if (editDateBirth.getText().length() > 9) {

                            //starting to update data
                            dbHandler.updateDataModel(new Model(
                                    getIntent().getIntExtra(Constant.KEY_ID, -1),
                                    editName.getText().toString(),
                                    editAddress.getText().toString(),
                                    editDateBirth.getText().toString(),
                                    editGender.getText().toString())
                            );

                            //set visibility
                            v.setVisibility(View.GONE);
                            btnCancel.setVisibility(View.GONE);
                            btnUpdate.setVisibility(View.VISIBLE);
                            btnDelete.setVisibility(View.VISIBLE);

                            //configure editText
                            editName.setEnabled(false);
                            editDateBirth.setEnabled(false);
                            editGender.setEnabled(false);
                            editAddress.setEnabled(false);

                            messageOfError = getResources().getString(R.string.txt_success_update);

                        } else {
                            messageOfError = getResources().getString(R.string.txt_failed_confirmation_dateBirth);
                        }
                    } else {
                        messageOfError = getResources().getString(R.string.txt_failed_confirmation_name);
                    }

                } else {
                    messageOfError = getResources().getString(R.string.txt_failed_confirmation_gender);
                }

                Snackbar.make(v, messageOfError, Snackbar.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
                btnSave.setVisibility(View.GONE);
                btnUpdate.setVisibility(View.VISIBLE);
                btnDelete.setVisibility(View.VISIBLE);

                //configure editText
                editName.setEnabled(false);
                editDateBirth.setEnabled(false);
                editGender.setEnabled(false);
                editAddress.setEnabled(false);
            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                //opening bottomSheetDialog for confirmation to delete data
                openBottomSheetDialog();
            }
        });
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
     * opening bottomSheetDialog when Button delete clicked
     */
    private void openBottomSheetDialog() {
        //configure bottom sheet dialog
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(Activity_detail.this);
        //set custom view for layout of bottomSheet
        final View bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_modal, null);
        //inflating layout
        bottomSheetDialog.setContentView(bottomSheetView);

        //show the dialog
        bottomSheetDialog.show();

        //set action for button inside bottomSheetDialog
        Button btnNo = (Button) bottomSheetDialog.findViewById(R.id.btn_modal_cancel);
        Button btnYes = (Button) bottomSheetDialog.findViewById(R.id.btn_modal_ok);

        assert btnNo != null;
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        assert btnYes != null;
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //starting to delete data from model
                dbHandler.deleteDataModel(new Model(
                        getIntent().getIntExtra(Constant.KEY_ID, -1),
                        editName.getText().toString(),
                        editAddress.getText().toString(),
                        editDateBirth.getText().toString(),
                        editGender.getText().toString()
                ));

                bottomSheetDialog.dismiss();
                Activity_detail.this.finish();
            }
        });
    }
}
