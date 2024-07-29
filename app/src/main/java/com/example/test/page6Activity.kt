package com.example.test

import android.app.AlertDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.SearchView
import android.widget.TimePicker
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.switchmaterial.SwitchMaterial
import java.util.Calendar

class page6Activity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_page6)
        setSupportActionBar(findViewById(R.id.my_toolbar))

        handleFab()
        handleBtn()
        handleCheckbox()
        handleRadio()
        handleSwitch()
        handlePicker()
        handleTip()
        handleDialog()
        handleActionBar()
    }

    // 右下角按钮
    fun handleFab() {
        val fab: View = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
        }
    }

    // 处理按钮
    fun handleBtn() {
        val supabutton: Button = findViewById(R.id.supabutton)
        supabutton.setOnClickListener{
            Log.d("BUTTONS", "User tapped the Supabutton")
        }
    }

    // 处理复选框
    fun handleCheckbox() {
        findViewById<CheckBox>(R.id.checkbox_meat).setOnCheckedChangeListener { buttonView, isChecked ->
            Log.d("CHECKBOXES", "Meat is checked: $isChecked")
        }
        findViewById<CheckBox>(R.id.checkbox_cheese)
            .setOnCheckedChangeListener { buttonView, isChecked ->
                Log.d("CHECKBOXES", "Cheese is checked: $isChecked")
            }
    }

    // 处理单选框
    fun handleRadio() {
        findViewById<RadioButton>(R.id.radio_pirates).setOnCheckedChangeListener { buttonView, isChecked ->
            Log.d("RADIO", "Pirates is checked: $isChecked")
        }

        findViewById<RadioButton>(R.id.radio_ninjas).setOnCheckedChangeListener { buttonView, isChecked ->
            Log.d("RADIO", "Ninjas is checked: $isChecked")
        }
    }

    // 处理选择框
    fun handleSwitch() {
        val switch: SwitchMaterial = findViewById(R.id.material_switch)
        switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Log.d("SWITCH", "Material_switch is checked: $isChecked")
            } else {
                Log.d("SWITCH", "Material_switch isn't checked: $isChecked")
            }
        }
    }

    // 处理时间选择器
    fun handlePicker() {
        findViewById<Button>(R.id.pickTime).setOnClickListener {
            TimePickerFragment().show(supportFragmentManager, "timePicker")
        }
    }

    // 处理提示
    @RequiresApi(Build.VERSION_CODES.O)
    fun handleTip() {
        val fab: FloatingActionButton = findViewById(R.id.fab2)
        fab.tooltipText = "Send an email"
    }

    // 处理弹框
    fun handleDialog() {
        findViewById<Button>(R.id.showDialog).setOnClickListener {
            StartGameDialogFragment().show(supportFragmentManager, "GAME_DIALOG")
        }
    }

    // 处理菜单
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.game_menu, menu)
        val searchItem = menu?.findItem(R.id.action_search)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection.
        return when (item.itemId) {
            R.id.new_game -> {
                newGame()
                true
            }
            R.id.help -> {
                showHelp()
                true
            }
            android.R.id.home -> {
                // 处理返回按钮点击事件
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showHelp() {
        TODO("Not yet implemented")
    }

    private fun newGame() {
        TODO("Not yet implemented")
    }

    fun handleActionBar() {
        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
//            actionBar.hide()
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}

/**
 * 时间选择器
 */
class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current time as the default values for the picker.
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        // Create a new instance of TimePickerDialog and return it.
        return TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        // Do something with the time the user picks.
    }
}

/**
 * 对话框
 */
class StartGameDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction.
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Start game")
                .setPositiveButton("Start") { dialog, id ->
                    // START THE GAME!
                }
                .setNegativeButton("Cancel") { dialog, id ->
                    // User cancelled the dialog.
                }
            // Create the AlertDialog object and return it.
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}