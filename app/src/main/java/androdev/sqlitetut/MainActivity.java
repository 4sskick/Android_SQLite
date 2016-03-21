package androdev.sqlitetut;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import androdev.sqlitetut.view.ViewAdapter;

public class MainActivity extends AppCompatActivity {

    DBHandler dbHandler;
    ViewAdapter viewAdapter;
    RecyclerView recyclerView;
    List<Model> modelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("MAIN", "onCreate: ...");
        dbHandler = new DBHandler(this);

        /**
         * execute preparing data in background
         * then after progress dialog dismissed
         * and populating data from DB done
         * set data on recycler
         */
        new Preparing().execute();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        Log.d("MAIN", "onCreate: ...#2");
    }

    /**
     * asynctask called after all process on
     * onCreate method done executed
     */
    class Preparing extends AsyncTask{

        ProgressDialog progressDialog;

        /**
         * starting from onPreExecute
         * then onCreated continued executing
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("MAIN", "onPreExecute: Preparing..");
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Preparing..");
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }

        /**
         * after onCreate finished execute,
         * starting to execute doInBackground
         * @param params
         * @return
         */
        @Override
        protected Object doInBackground(Object[] params) {
            Log.d("MAIN", "doInBackground: inserting...");
            dbHandler.addModel(new Model("Septian", "Perumahan Tamansari Indah C-12"));
            dbHandler.addModel(new Model("Adi", "Bondosowo"));
            dbHandler.addModel(new Model("Wijaya", "Jl. Kertorahardjo dalam no.42"));
            dbHandler.addModel(new Model("Adi Septian Wijaya", "Malang"));
            dbHandler.addModel(new Model("Dockers", " 475 Brannan St #330, San Francisco, CA 94107, United States"));
            dbHandler.addModel(new Model("Dunkin Donuts", "White Plains, NY 10601"));
            dbHandler.addModel(new Model("Pizza Porlar", "North West Avenue, Boston , USA"));
            dbHandler.addModel(new Model("Town Bakers", "Beverly Hills, CA 90210, USA"));

            //reading all data in model
            Log.d("MAIN", "doInBackground: Reading...");
            modelList = dbHandler.getAllDataModel();

            for (Model model : modelList) {
                String log = "id: " + model.getId() + ", name: " + model.getName() + ", address: " + model.getAddress();
                Log.d("MAIN", "doInBackground: " + log);
            }
            return null;
        }

        /**
         * last executed onPostExecute,
         * so this gonna be perfect place to gather all data
         * to presented on UI
         * @param o
         */
        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Log.d("MAIN", "onPostExecute: dismissed progressDialog");

            viewAdapter = new ViewAdapter(MainActivity.this, modelList);
            recyclerView.setAdapter(viewAdapter);
            progressDialog.dismiss();
        }
    }
}
