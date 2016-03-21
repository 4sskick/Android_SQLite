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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHandler dbHandler = new DBHandler(this);

        /**
         * execute preparing data in background
         * then after progress dialog dismissed
         * and populating data from DB done
         * set data on recycler
         */
        new Preparing(dbHandler).execute();
        List<Model> modellist = dbHandler.getAllDataModel();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        ViewAdapter viewAdapter = new ViewAdapter(this, modellist);
        recyclerView.setAdapter(viewAdapter);
    }

    class Preparing extends AsyncTask{

        DBHandler dbHandler;
        ProgressDialog progressDialog;

        public Preparing(DBHandler dbHandler) {
            this.dbHandler = dbHandler;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Preparing..");
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }

        @Override
        protected Object doInBackground(Object[] params) {
            Log.d("MAIN", "onCreate: inserting...");
            dbHandler.addModel(new Model("Septian", "Perumahan Tamansari Indah C-12"));
            dbHandler.addModel(new Model("Adi", "Bondosowo"));
            dbHandler.addModel(new Model("Wijaya", "Jl. Kertorahardjo dalam no.42"));
            dbHandler.addModel(new Model("Adi Septian Wijaya", "Malang"));
            dbHandler.addModel(new Model("Dockers", " 475 Brannan St #330, San Francisco, CA 94107, United States"));
            dbHandler.addModel(new Model("Dunkin Donuts", "White Plains, NY 10601"));
            dbHandler.addModel(new Model("Pizza Porlar", "North West Avenue, Boston , USA"));
            dbHandler.addModel(new Model("Town Bakers", "Beverly Hills, CA 90210, USA"));

            //reading all data in model
            Log.d("MAIN", "onCreate: Reading...");
            List<Model> modelList = dbHandler.getAllDataModel();

            for (Model model : modelList) {
                String log = "id: " + model.getId() + ", name: " + model.getName() + ", address: " + model.getAddress();
                Log.d("MAIN", "onCreate: " + log);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            progressDialog.dismiss();
        }
    }
}
