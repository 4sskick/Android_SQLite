package androdev.sqlitetut.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

import androdev.sqlitetut.DBHandler;
import androdev.sqlitetut.R;
import androdev.sqlitetut.model.Model;
import androdev.sqlitetut.util.Constant;
import androdev.sqlitetut.util.RecycleritemClickListener;
import androdev.sqlitetut.view.ViewAdapter;

public class MainActivity extends AppCompatActivity {

    DBHandler dbHandler;
    ViewAdapter viewAdapter;
    RecyclerView recyclerView;
    Button btnAdd;

    List<Model> modelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(Constant.TAG, "onCreate: ...");
        setupLayout();
        Log.d(Constant.TAG, "onCreate: ...#2");
    }

    /**
     * CUSTOM METHODS START HERE
     * -------------------------
     */
    private void setupLayout() {
        dbHandler = new DBHandler(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        btnAdd = (Button) findViewById(R.id.btn_add);

        // set action component
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnItemTouchListener(new RecycleritemClickListener(
                this,
                new RecycleritemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClickListener(View view, int position) {
                        //Snackbar.make(view, modelList.get(position).getName(), Snackbar.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, Activity_detail.class);
                        intent.putExtra(Constant.KEY_ID, modelList.get(position).getId());
                        intent.putExtra(Constant.KEY_NAME, modelList.get(position).getName());

                        startActivity(intent);
                    }
                }
        ));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Activity_add.class);
                startActivity(intent);
            }
        });
    }

    /**
     * asynctask called after all process on
     * onCreate method done executed
     */
    class Preparing extends AsyncTask {

        ProgressDialog progressDialog;

        /**
         * starting from onPreExecute
         * then onCreated continued executing
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(Constant.TAG, "onPreExecute: Preparing..");
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Preparing..");
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }

        /**
         * after onCreate finished execute,
         * starting to execute doInBackground
         *
         * @param params
         * @return
         */
        @Override
        protected Object doInBackground(Object[] params) {
            Log.d(Constant.TAG, "doInBackground: inserting...");
//            dbHandler.addDataModel(new Model("Septian", "Perumahan Tamansari Indah C-12", "13/09/1992", "M"));
//            dbHandler.addDataModel(new Model("Adi", "Bondosowo", "13/09/1992", "M"));
//            dbHandler.addDataModel(new Model("Wijaya", "Jl. Kertorahardjo dalam no.42", "13/09/1992", "M"));
//            dbHandler.addDataModel(new Model("Adi Septian Wijaya", "Malang", "13/09/1992", "M"));
//            dbHandler.addDataModel(new Model("Dockers", " 475 Brannan St #330, San Francisco, CA 94107, United States", "13/09/1992", "M"));
//            dbHandler.addDataModel(new Model("Dunkin Donuts", "White Plains, NY 10601", "13/09/1992", "M"));
//            dbHandler.addDataModel(new Model("Pizza Porlar", "North West Avenue, Boston , USA", "13/09/1992", "M"));
//            dbHandler.addDataModel(new Model("Town Bakers", "Beverly Hills, CA 90210, USA", "13/09/1992", "M"));
//            dbHandler.addDataModel(new Model("Dunkin Donuts", "White Plains, NY 10601", "13/09/1992", "M"));
//            dbHandler.addDataModel(new Model("Septian1", "Perumahan Tamansari Indah C-121", "13/09/1992", "M"));
//            dbHandler.addDataModel(new Model("Adi1", "Bondosowo1", "13/09/1992", "M"));
//            dbHandler.addDataModel(new Model("Wijaya1", "Jl. Kertorahardjo dalam no.421", "13/09/1992", "M"));
//            dbHandler.addDataModel(new Model("Adi Septian Wijaya1", "Malang1", "13/09/1992", "M"));
//            dbHandler.addDataModel(new Model("Dockers1", " 475 Brannan St #330, San Francisco, CA 94107, United States1", "13/09/1992", "M"));
//            dbHandler.addDataModel(new Model("Dunkin Donuts1", "White Plains, NY 106011", "13/09/1992", "M"));
//            dbHandler.addDataModel(new Model("Pizza Porlar1", "North West Avenue, Boston , USA1", "13/09/1992", "M"));
//            dbHandler.addDataModel(new Model("Town Bakers1", "Beverly Hills, CA 90210, USA1", "13/09/1992", "M"));
//            dbHandler.addDataModel(new Model("Dunkin Donuts1", "White Plains, NY 106011", "13/09/1992", "M"));
//            dbHandler.addDataModel(new Model("Septia2", "Perumahan Tamansari Indah C-122", "13/09/1992", "M"));
//            dbHandler.addDataModel(new Model("Adi2", "Bondosowo2", "13/09/1992", "M"));
//            dbHandler.addDataModel(new Model("Wijaya2", "Jl. Kertorahardjo dalam no.422", "13/09/1992", "M"));
//            dbHandler.addDataModel(new Model("Adi Septian Wijaya2", "Malang2", "13/09/1992", "M"));
//            dbHandler.addDataModel(new Model("Dockers2", " 475 Brannan St #330, San Francisco, CA 94107, United States2", "13/09/1992", "M"));
//            dbHandler.addDataModel(new Model("Dunkin Donuts2", "White Plains, NY 106012", "13/09/1992", "M"));
//            dbHandler.addDataModel(new Model("Pizza Porlar2", "North West Avenue, Boston , USA2", "13/09/1992", "M"));
//            dbHandler.addDataModel(new Model("Town Bakers2", "Beverly Hills, CA 90210, USA2", "13/09/1992", "M"));
//            dbHandler.addDataModel(new Model("Dunkin Donuts2", "White Plains, NY 106012", "13/09/1992", "M"));
//            dbHandler.addDataModel(new Model("Septian3", "Perumahan Tamansari Indah C-123", "13/09/1992", "M"));
//            dbHandler.addDataModel(new Model("Adi3", "Bondosowo3", "13/09/1992", "M"));
//            dbHandler.addDataModel(new Model("Wijaya3", "Jl. Kertorahardjo dalam no.423", "13/09/1992", "M"));
//            dbHandler.addDataModel(new Model("Adi Septian Wijaya3", "Malang3", "13/09/1992", "M"));
//            dbHandler.addDataModel(new Model("Dockers3", " 475 Brannan St #330, San Francisco, CA 94107, United States3", "13/09/1992", "M"));
//            dbHandler.addDataModel(new Model("Dunkin Donuts3", "White Plains, NY 106013", "13/09/1992", "M"));
//            dbHandler.addDataModel(new Model("Pizza Porlar3", "North West Avenue, Boston , USA3", "13/09/1992", "M"));
//            dbHandler.addDataModel(new Model("Town Bakers3", "Beverly Hills, CA 90210, USA3", "13/09/1992", "M"));
//            dbHandler.addDataModel(new Model("Dunkin Donuts3", "White Plains, NY 106013", "13/09/1992", "M"));

            //reading all data in model
            Log.d(Constant.TAG, "doInBackground: Reading...");
            modelList = dbHandler.getSumarryDataModel();

            return null;
        }

        /**
         * last executed onPostExecute,
         * so this gonna be perfect place to gather all data
         * to presented on UI
         *
         * @param o
         */
        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Log.d(Constant.TAG, "onPostExecute: dismissed progressDialog");

            viewAdapter = new ViewAdapter(MainActivity.this, modelList);
            recyclerView.setAdapter(viewAdapter);
            progressDialog.dismiss();
        }
    }

    class PreparingOnPost extends AsyncTask {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(Constant.TAG, "onPreExecute: Preparing #2..");
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Preparing..");
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }

        @Override
        protected Object doInBackground(Object[] params) {
            //reading all data in model
            Log.d(Constant.TAG, "doInBackground: Reading #2...");
            modelList = dbHandler.getSumarryDataModel();

            return null;
        }

        /**
         * last executed onPostExecute,
         * so this gonna be perfect place to gather all data
         * to presented on UI
         *
         * @param o
         */
        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Log.d(Constant.TAG, "onPostExecute: dismissed progressDialog #2...");

            viewAdapter = new ViewAdapter(MainActivity.this, modelList);
            recyclerView.setAdapter(viewAdapter);
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (modelList != null) {
            if (modelList.isEmpty()) {
                new PreparingOnPost().execute();
                Log.d(Constant.TAG, "onResume: modellist empty");
            } else {
                Log.d(Constant.TAG, "onResume: modellist NOT empty");
            }
        } else {
            Log.d(Constant.TAG, "onResume: modellist NULL");

            /**
             * execute preparing data in background
             * then after progress dialog dismissed
             * and populating data from DB done
             * set data on recycler
             */
            new Preparing().execute();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(Constant.TAG, "onDestroy: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(Constant.TAG, "onStop: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        modelList.clear();
        Log.d(Constant.TAG, "onPause: ");
    }
}
