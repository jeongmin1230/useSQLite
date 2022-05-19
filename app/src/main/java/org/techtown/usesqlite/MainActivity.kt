package org.techtown.usesqlite

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import org.techtown.usesqlite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var dbHelper: DBHelper
    lateinit var database: SQLiteDatabase


//    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        loading()

        binding.insert.setOnClickListener {
            var query = "INSERT INTO animals('animal') values('${binding.edit.text}');"
            database.execSQL(query)
            binding.tvAnimalType.append("${binding.edit.text}\n")
            binding.edit.text.clear()
            Toast.makeText(this, "추가되었습니다.${binding.edit.text}", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("Range")
    fun loading() {
        dbHelper = DBHelper(this, "mydb.db", null, 1)
        database = dbHelper.writableDatabase

        var query = "SELECT * FROM animals;"
        var cursor = database.rawQuery(query, null)
        while(cursor.moveToNext()){
            var animal = cursor.getString(cursor.getColumnIndex("animal"))
            binding.tvAnimalType.append(animal + "\n")
        }
    }

    fun onClickUpdate(view: View) {
        var query = "UPDATE animals SET animal = '${binding.etUpdateChooseTo.text.toString()}' WHERE animal = '${binding.etUpdateChooseFrom.text.toString()}';"

        Toast.makeText(this, "${binding.etUpdateChooseFrom.text.toString()}(을)를 ${binding.etUpdateChooseTo.text.toString()}(으)로 변경했습니다.",Toast.LENGTH_SHORT).show()
        database.execSQL(query)
    }
    fun onClickDelete(view: View) {
        var etDeleteChooseNumValue = "${binding.etDeleteChooseType.text.toString()}" // 입력값을 받아옴
        var query = "DELETE FROM animals WHERE animal = '${etDeleteChooseNumValue}';"

        binding.etDeleteChooseType.text.clear()
        Toast.makeText(this, "${etDeleteChooseNumValue}(을)를 삭제했습니다.\n 새로고침하면 변경사항이 적용됩니다.", Toast.LENGTH_SHORT).show()
        database.execSQL(query)
    }
}