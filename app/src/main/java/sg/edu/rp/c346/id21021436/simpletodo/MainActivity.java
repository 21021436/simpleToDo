package sg.edu.rp.c346.id21021436.simpletodo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //define variables
    EditText etNewToDo;
    Button btnAddNewItem;
    Button btnDelItem;
    Button btnClearAllItems;
    ListView lvToDoList;
    Spinner spnAddRemove;

    ArrayList<String> alToDoItems;
    ArrayAdapter<String> aaToDoItems;

    //set indexToDelete to an "impossible" initial value for safety
    int indexToDelete = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //link variables to activity fields
        etNewToDo = findViewById(R.id.editNewItemText);
        btnAddNewItem = findViewById(R.id.buttonAddNewItem);
        btnDelItem = findViewById(R.id.buttonDeleteItem);
        btnClearAllItems = findViewById(R.id.buttonClearAllItems);
        lvToDoList = findViewById(R.id.listViewToDoList);
        spnAddRemove = findViewById(R.id.spinnerAddRemove);

        //Define ArrayList to store tasks (To Do Items)
        alToDoItems = new ArrayList<>();

        //Define ArrayAdapter to link the listview to the ArrayList
        aaToDoItems = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alToDoItems);
        lvToDoList.setAdapter(aaToDoItems);

        //Code for Adding New Task/Item when Add Button clicked
        btnAddNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTodo = etNewToDo.getText().toString();
                //Will only add an item if the edit text field is not empty. ArrayList can accept null values so otherwise you will get a whole list of null entries
                if (!newTodo.equals("")) alToDoItems.add(newTodo);
                aaToDoItems.notifyDataSetChanged();
                //Good practice to set the text field to null after this
                etNewToDo.setText(null);
            }
        });

        //Code for Deleting Task/Item when Delete Button clicked
        btnDelItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTodo = etNewToDo.getText().toString();
                //Only capture the value to integer if not empty (otherwise will crash)
                if (!newTodo.isEmpty()) indexToDelete = Integer.parseInt(newTodo);
                //Nested if loop checks to ensure the list of items is not empty and that index is in range before attempting a deletion. Appropriate Toast messages shown if either condition does not hold
                if (!alToDoItems.isEmpty()){
                    if (indexToDelete >= 0 && indexToDelete < alToDoItems.size()){
                        alToDoItems.remove(indexToDelete);
                        aaToDoItems.notifyDataSetChanged();
                    }
                    else Toast.makeText(MainActivity.this, "Wrong index number", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(MainActivity.this, "You don't have any task to remove", Toast.LENGTH_SHORT).show();
                //Good practice to set the text field to null after this
                etNewToDo.setText(null);
            }
        });

        //Code for Clearing all Tasks/Items when Clear Button clicked
        btnClearAllItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alToDoItems.clear();
                aaToDoItems.notifyDataSetChanged();
                //Good practice to set the text field to null after this
                etNewToDo.setText(null);
            }
        });

        //Spinner code. Use switch/case to allow for flexibility if new spinner items planned in further enhancements
        spnAddRemove.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){


                switch(position){
                    case 0:
                        etNewToDo.setHint(R.string.newTaskHint);
                        etNewToDo.setText("");
                        btnAddNewItem.setEnabled(true);
                        btnDelItem.setEnabled(false);
                        break;
                    case 1:
                        etNewToDo.setHint(R.string.indexTaskEnter);
                        etNewToDo.setText("");
                        btnAddNewItem.setEnabled(false);
                        btnDelItem.setEnabled(true);
                        break;

                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent){

            }
        });

    }
}