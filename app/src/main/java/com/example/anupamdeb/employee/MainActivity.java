package com.example.anupamdeb.employee;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtEmployeeId;
    EditText edtEmployeeName;
    EditText edtContactNumber;
    Button btnAdd;
    Button btnDelete;
    Button btnUpdate;
    Button btnView;
    Button btnViewAll;
    Button btnShowInfo;
    Button btnDeleteAll;

    Button btnSearchByName;

    TextView txtEmpId;
    TextView txtEmpName;
    TextView txtContactNumber;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtEmployeeId = (EditText) findViewById(R.id.edtEmployeeId);
        edtEmployeeName = (EditText) findViewById(R.id.edtEmployeeName);
        edtContactNumber = (EditText) findViewById(R.id.edtEmployeeContactNumber);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnView = (Button) findViewById(R.id.btnView);
        btnViewAll = (Button) findViewById(R.id.btnViewAll);
        btnShowInfo = (Button) findViewById(R.id.btnShowInfo);
        btnDeleteAll = (Button) findViewById(R.id.btnDeleteAll);

        btnSearchByName = (Button) findViewById(R.id.btnSearchByName);

        txtEmpId = (TextView)findViewById(R.id.txtEmpId);
        txtEmpName = (TextView)findViewById(R.id.txtEmpName);
        txtContactNumber = (TextView)findViewById(R.id.txtContactNumber);

        btnAdd.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnView.setOnClickListener(this);
        btnViewAll.setOnClickListener(this);
        btnShowInfo.setOnClickListener(this);
        btnDeleteAll.setOnClickListener(this);

        btnSearchByName.setOnClickListener(this);

        createDatabase();

    }

    public void createDatabase()
    {
        db=openOrCreateDatabase("EmployeeDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS employees(edtEmployeeId VARCHAR,edtEmployeeName VARCHAR,edtContactNumber VARCHAR);");
    }

    public void onClick(View view)
    {
        //Add
        if(view==btnAdd)
        {
            txtEmpName.setVisibility(View.VISIBLE);
            txtContactNumber.setVisibility(View.VISIBLE);
            edtEmployeeName.setVisibility(View.VISIBLE);
            edtContactNumber.setVisibility(View.VISIBLE);

            txtEmpId.setVisibility(View.VISIBLE);
            edtEmployeeId.setVisibility(View.VISIBLE);

            if((edtEmployeeId.getText().toString().trim().length()==0)||

                    (edtEmployeeName.getText().toString().trim().length()==0)||

                    (edtContactNumber.getText().toString().trim().length()==0))
            {
                showMessage("Error", "Please enter all values");
                clearText();
                return;
            }
            else
            {
                Cursor c=db.rawQuery("SELECT * FROM employees WHERE edtEmployeeId='"+edtEmployeeId.getText()+"'", null);
                if(c.moveToFirst())
                {
                    showMessage("invalid", "Record already exist with this id");
                    clearText();
                    return;
                }
                else
                {
                    db.execSQL("INSERT INTO employees VALUES('"+edtEmployeeId.getText()+"','"+edtEmployeeName.getText()+
                            "','"+edtContactNumber.getText()+"');");
                    showMessage("Success", "Record added");
                    clearText();
                    return;
                }
            }
        }


        //Delete
        if(view==btnDelete)
        {
            txtEmpId.setVisibility(View.VISIBLE);
            edtEmployeeId.setVisibility(View.VISIBLE);
            txtEmpName.setVisibility(View.INVISIBLE);
            txtContactNumber.setVisibility(View.INVISIBLE);
            edtEmployeeName.setVisibility(View.INVISIBLE);
            edtContactNumber.setVisibility(View.INVISIBLE);

            if(edtEmployeeId.getText().toString().trim().length()==0)
            {
                showMessage("Error", "Please enter Employee Id");
                return;
            }
            Cursor c=db.rawQuery("SELECT * FROM employees WHERE edtEmployeeId='"+edtEmployeeId.getText()+"'", null);
            if(c.moveToFirst())
            {
                db.execSQL("DELETE FROM employees WHERE edtEmployeeId='"+edtEmployeeId.getText()+"'");
                showMessage("Success", "Record Deleted");
            }
            else
            {
                showMessage("Error", "Invalid EmployeeId");

            }
            clearText();
        }

        //update
        if(view==btnUpdate)
        {
            txtEmpId.setVisibility(View.VISIBLE);
            edtEmployeeId.setVisibility(View.VISIBLE);
            txtEmpName.setVisibility(View.VISIBLE);
            txtContactNumber.setVisibility(View.VISIBLE);
            edtEmployeeName.setVisibility(View.VISIBLE);
            edtContactNumber.setVisibility(View.VISIBLE);

            if(edtEmployeeId.getText().toString().trim().length()==0)
            {
                showMessage("Error", "Please enter Employee Id");
                return;
            }
            else
            {
                if(edtEmployeeId.getText().toString().trim().length()!=0 &&
                        edtEmployeeName.getText().toString().trim().length()==0 &&
                        edtContactNumber.getText().toString().trim().length()==0)
                {
                    showMessage("Plz", "Fill any field");
                    return;
                }
                if(edtEmployeeId.getText().toString().trim().length()!=0 &&
                        edtEmployeeName.getText().toString().trim().length()==0 &&
                        edtContactNumber.getText().toString().trim().length()!=0)
                {
                    Cursor c=db.rawQuery("SELECT * FROM employees WHERE edtEmployeeId='"+edtEmployeeId.getText()+"'", null);
                        if(c.moveToFirst())
                        {
                            db.execSQL("UPDATE employees SET edtContactNumber='"+edtContactNumber.getText()+
                                    "' WHERE edtEmployeeId='"+edtEmployeeId.getText()+"'");
                            showMessage("Success", "contact number updated");
                            return;
                        }
                        else
                        {
                            {
                                showMessage("Error", "Invalid Employee Id");
                                clearText();
                                return;
                            }
                        }

                }
                if(edtEmployeeId.getText().toString().trim().length()!=0 &&
                        edtEmployeeName.getText().toString().trim().length()!=0 &&
                        edtContactNumber.getText().toString().trim().length()==0)
                {
                    Cursor c=db.rawQuery("SELECT * FROM employees WHERE edtEmployeeId='"+edtEmployeeId.getText()+"'", null);
                    if(c.moveToFirst())
                    {
                        if(c.moveToFirst())
                        {
                            db.execSQL("UPDATE employees SET edtEmployeeName='"+edtEmployeeName.getText()+
                                    "' WHERE edtEmployeeId='"+edtEmployeeId.getText()+"'");
                            showMessage("Success", "employee name updated");
                            return;
                        }
                    }
                    else
                    {
                        {
                            showMessage("Error", "Invalid Employee Id");
                            clearText();
                            return;
                        }
                    }
                }
                Cursor c=db.rawQuery("SELECT * FROM employees WHERE edtEmployeeId='"+edtEmployeeId.getText()+"'", null);
                if(c.moveToFirst())
                {
                    db.execSQL("UPDATE employees SET edtEmployeeName='"+edtEmployeeName.getText()+"',edtContactNumber='"+edtContactNumber.getText()+
                            "' WHERE edtEmployeeId='"+edtEmployeeId.getText()+"'");
                    showMessage("Success", " Full Record updated");
                    return;
                }
                else
                {
                    showMessage("Error", "Invalid Employee Id");
                }
                clearText();
                return;
            }
        }

        //View
        if(view==btnView)
        {
            txtEmpId.setVisibility(View.VISIBLE);
            edtEmployeeId.setVisibility(View.VISIBLE);
            txtEmpName.setVisibility(View.INVISIBLE);
            txtContactNumber.setVisibility(View.INVISIBLE);
            edtEmployeeName.setVisibility(View.INVISIBLE);
            edtContactNumber.setVisibility(View.INVISIBLE);

            if(edtEmployeeId.getText().toString().trim().length()==0)
            {
                showMessage("Error", "Please enter Employee Id");
                return;
            }
            Cursor c=db.rawQuery("SELECT * FROM employees WHERE edtEmployeeId='"+edtEmployeeId.getText()+"'", null);
            if(c.moveToFirst())
            {
                txtEmpName.setVisibility(View.VISIBLE);
                txtContactNumber.setVisibility(View.VISIBLE);
                edtEmployeeName.setVisibility(View.VISIBLE);
                edtContactNumber.setVisibility(View.VISIBLE);

                edtEmployeeName.setText(c.getString(1));
                edtContactNumber.setText(c.getString(2));
            }
            else
            {
                showMessage("Error", "Invalid Employee Id");
                clearText();
            }
        }

        //View All
        if(view==btnViewAll)
        {
            clearText();
            Cursor c=db.rawQuery("SELECT * FROM employees", null);
            if(c.getCount()==0)
            {
                showMessage("Error", "No records found");
                return;
            }
            StringBuffer buffer=new StringBuffer();
            while(c.moveToNext())
            {
                buffer.append("EmployeeId: "+c.getString(0)+"\n");
                buffer.append("EmployeeName: "+c.getString(1)+"\n");
                buffer.append("EmployeeContactNumber: "+c.getString(2)+"\n\n");
            }
            showMessage("Employee Details", buffer.toString());
        }

        //Delete All
        if(view==btnDeleteAll)
        {
            clearText();
            Cursor c=db.rawQuery("SELECT * FROM employees", null);
            if(c.getCount()==0)
            {
                showMessage("message", "yet not inserted any data plz insert .....");
            }
            else
            {
                db.execSQL("delete from employees");
                showMessage("Deleted","Successfully");
                clearText();
                createDatabase();
            }

        }

        //Show Info
        if(view==btnShowInfo)
        {
            clearText();
            showMessage("Employee Application", "Developed By @Anupam");
        }

        //Search By Name
        if(view == btnSearchByName)
        {
            txtEmpName.setVisibility(View.VISIBLE);
            edtEmployeeName.setVisibility(View.VISIBLE);
            txtEmpId.setVisibility(View.INVISIBLE);
            edtEmployeeId.setVisibility(View.INVISIBLE);
            txtContactNumber.setVisibility(View.INVISIBLE);
            edtContactNumber.setVisibility(View.INVISIBLE);

            if(edtEmployeeName.getText().toString().trim().length()==0)
            {
                showMessage("Message","Enter valid!");
            }
            else
            {
                Cursor c=db.rawQuery("SELECT * FROM employees WHERE edtEmployeeName LIKE '%"+edtEmployeeName.getText()+"%' ", null);
                if(c.getCount()==0)
                {
                    showMessage("Error", "No records found");
                    return;
                }
                clearText();
                StringBuffer buffer=new StringBuffer();
                while(c.moveToNext())
                {
                    buffer.append("EmployeeId: "+c.getString(0)+"\n");
                    buffer.append("EmployeeName: "+c.getString(1)+"\n");
                    buffer.append("EmployeeContactNumber: "+c.getString(2)+"\n\n");
                }
                showMessage("Employee Details", buffer.toString());
            }
        }
    }

    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void clearText()
    {
        edtEmployeeId.setText("");
        edtEmployeeName.setText("");
        edtContactNumber.setText("");
        edtContactNumber.requestFocus();
    }
}
