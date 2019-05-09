package com.example.swim_2

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.text.FieldPosition

class MainActivity : AppCompatActivity() {

    private lateinit var images : ArrayList<Image>
    private lateinit var adapter: ImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        my_recycler_view.layoutManager = LinearLayoutManager(this)
        images = ArrayList()
        adapter = ImageAdapter(images)
        my_recycler_view.adapter = adapter
        initSwipe()
    }

    private fun initSwipe() {
        val simpleItemTouchCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    if (direction == ItemTouchHelper.LEFT) {
                        adapter.removeItem(position)
                        adapter.notifyItemRemoved(position)
                    } else if (direction == ItemTouchHelper.RIGHT) {
                        adapter.notifyItemChanged(position)
                        launchSecActivity(position)
                    }
                }
            }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(my_recycler_view)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_action_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId  == R.id.add_image) {
            launchAddImageActivity()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                prepareData(data!!)
                my_recycler_view.adapter!!.notifyItemInserted(0)
                my_recycler_view.scrollToPosition(0)
            }
        }
    }

    private fun prepareData(data: Intent) {
        val image = data.getParcelableExtra<Image>("image")
        images.add(0, image)
    }

    private fun launchAddImageActivity() {
        val intent = Intent (this, AddImageActivity::class.java)
        startActivityForResult(intent, 1)
    }

    private fun launchSecActivity(position: Int) {
        val intent = Intent(this, SecActivity::class.java)
        val bundle = Bundle()
        bundle.run {
            putInt("position", position)
            putParcelableArrayList("images", images)
        }
        intent.putExtras(bundle)
        startActivity(intent)
    }
}
