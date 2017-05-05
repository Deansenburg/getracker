package com.dean.getracker.controller;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import com.dean.getracker.R;
import com.dean.getracker.binder.geControllerBinder;
import com.dean.getracker.helper.geDatabaseHelper;
import com.dean.getracker.helper.geModelHelper;
import com.dean.getracker.model.geEntry;
import com.dean.getracker.model.geModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class inputActivity extends ActionBarActivity {

    ListView readingsView;

    SQLiteDatabase db;
    geDatabaseHelper helper;
    geGraphController controller;

    List<geModel> models = new ArrayList<>();

    ArrayAdapter<String> listAdapter;

    Button gBtn, eBtn, addBtn;

    int currentModel = 0;
    enum Mode
    {
        add, replace, delete
    }
    Mode addMode = Mode.add;

    EditText dateText, numberText;
    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDate();
        }

    };

    TextWatcher addButtonWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            checkDate();
            checkValue();
            checkAddButton();
        }
    };

    AdapterView.OnItemClickListener itemOnClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            dateText.setText(listAdapter.getItem(position).split(" : ")[0]);
            checkDate();
            checkValue();
            checkAddButton();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        readingsView = (ListView) findViewById(R.id.readingListView);
        readingsView.setOnItemClickListener(itemOnClick);

        gBtn = (Button) findViewById(R.id.buttonG);
        eBtn = (Button) findViewById(R.id.buttonE);
        dateText = (EditText) findViewById(R.id.dateEditText);

        numberText = (EditText) findViewById(R.id.valueEditText);
        numberText.addTextChangedListener(addButtonWatcher);

        addBtn = (Button) findViewById(R.id.addButton);

        helper = new geDatabaseHelper(getApplicationContext());
        db = helper.getWritableDatabase();

        geModelHelper mHelper = new geModelHelper(db, helper);
        controller = mHelper.createFromDatabase();
        models = controller.getModels();

        buttonEClick(null);
        checkDate();
        checkAddButton();
    }

    private void checkAddButton()
    {
        boolean shouldAllow = true;
        if (dateText.getText().toString().trim().length() == 0)
        {
            shouldAllow = false;
        }
        if (numberText.getText().toString().trim().length() == 0)
        {
            shouldAllow = false;
        }

        addBtn.setEnabled(shouldAllow);
        if (addMode == Mode.add)
        {
            addBtn.setText("Add");
        }
        else if (addMode == Mode.replace)
        {
            addBtn.setText("Replace");
        }
        else if (addMode == Mode.delete)
        {
            addBtn.setText("Delete");
        }
    }

    private void loadIntoView(geModel m)
    {
        ArrayList<String> listArray = new ArrayList<>();
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listArray);
        readingsView.setAdapter(listAdapter);

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
        if (m != null)
        {
            for (geEntry e:m.getEntries())
            {
                listAdapter.add(format.format(e.Date())+" : "+e.Value());
            }
        }
    }

    private void checkValue()
    {
        String s = numberText.getText().toString();
        if (s.length() > 0)
        {
            Integer val = Integer.parseInt(s);
            if (val == 0)
            {
                addMode = Mode.delete;
            }
        }
    }

    private void checkDate()
    {
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yy");
        boolean exists = false;
        for (geEntry e:models.get(currentModel).getEntries())
        {
            if (dateText.getText().toString().equals(f.format(e.Date())))
            {
                exists = true;
                break;
            }
        }
        if (exists)
        {
            addMode = Mode.replace;
        }
        else
        {
            addMode = Mode.add;
        }
    }

    private void updateDate()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");

        dateText.setText(sdf.format(myCalendar.getTime()));
        checkDate();
        checkAddButton();
    }

    public void showDatePicker(View v)
    {
        new DatePickerDialog(this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private String getDatabaseForIndex(int index)
    {
        String s = "";
        if (index == 0)
        {
            s = geDatabaseHelper.ReadColumns.TABLE_NAME_E;
        }
        else if (index == 1)
        {
            s = geDatabaseHelper.ReadColumns.TABLE_NAME_G;
        }
        return s;
    }

    public void buttonAddClick(View v)
    {
        geModelHelper mHelper = new geModelHelper(db, helper);
        SimpleDateFormat toDate = new SimpleDateFormat("dd/MM/yy");
        SimpleDateFormat toSql = new SimpleDateFormat(("yyyy-MM-dd"));

        Date d = null;
        try {
            d = toDate.parse(dateText.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (addMode == Mode.replace)
        {
            helper.replaceInDB(db, getDatabaseForIndex(currentModel),toSql.format(d),
                    Integer.parseInt(numberText.getText().toString()));
        }
        else if(addMode == Mode.add)
        {
            helper.addToDB(db, getDatabaseForIndex(currentModel), toSql.format(d),
                    Integer.parseInt(numberText.getText().toString()));
        }
        else if(addMode == Mode.delete)
        {
            helper.deleteInDB(db, getDatabaseForIndex(currentModel), toSql.format(d),
                    Integer.parseInt(numberText.getText().toString()));
        }
        controller = mHelper.createFromDatabase();
        models = controller.getModels();
        loadIntoView(models.get(currentModel));

        numberText.setText("");
        checkDate();
        checkAddButton();
    }

    public void buttonEClick(View v)
    {
        eBtn.setClickable(false);
        eBtn.setAlpha(1f);
        gBtn.setClickable(true);
        gBtn.setAlpha(0.1f);
        loadIntoView(models.get(0));//0 is elec
        currentModel = 0;
    }
    public void buttonGClick(View v)
    {
        eBtn.setClickable(true);
        eBtn.setAlpha(0.1f);
        gBtn.setClickable(false);
        gBtn.setAlpha(1f);
        loadIntoView(models.get(1));//1 is gas
        currentModel = 1;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_input, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_graph) {
            Intent i = new Intent(inputActivity.this, graphActivity.class);


            IBinder conBinder = new geControllerBinder(controller);
            Bundle b = new Bundle();
            b.putBinder(getString(R.string.extras_graphBinder), conBinder);
            i.putExtra(getString(R.string.extras_graphBundle), b);

            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
