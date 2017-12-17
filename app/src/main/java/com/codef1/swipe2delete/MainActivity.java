package com.codef1.swipe2delete;

import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private static final String JSON = "[\n" +
            "{\n" +
            "id: 1,\n" +
            "name: \"酱香牛肉\",\n" +
            "description: \"原料：雪花牛腩、油、郫县豆瓣酱、黄酒、山楂、草果、白豆蔻、蒜叶、生姜、白糖\",\n" +
            "price: 40,\n" +
            "thumbnail: \"http://i3.meishichina.com/attachment/recipe/2017/12/13/20171213151317706534713.jpg@!c320\"\n" +
            "},\n" +
            "{\n" +
            "id: 2,\n" +
            "name: \"红烧带鱼\",\n" +
            "description: \"原料：带鱼、油、老抽、生抽、生姜、小葱、黄酒、生粉\",\n" +
            "price: 50,\n" +
            "thumbnail: \"http://i3.meishichina.com/attachment/recipe/2017/12/13/20171213151317686057113.jpg@!c320\"\n" +
            "},\n" +
            "{\n" +
            "id: 3,\n" +
            "name: \"叉烧排骨\",\n" +
            "description: \"原料：肋排、叉烧酱、料酒、蜂蜜、葱叶、姜片、食盐、白芝麻\",\n" +
            "price: 85,\n" +
            "thumbnail: \"http://i3.meishichina.com/attachment/recipe/2017/12/13/20171213151317571614713.jpg@!c320\"\n" +
            "},\n" +
            "{\n" +
            "id: 4,\n" +
            "name: \"水煮肉片\",\n" +
            "description: \"原料：精肉、白菜、莴笋、鸡蛋、豆瓣酱、老抽、鸡粉、生姜、蒜末、葱花、花椒、胡椒粉、辣椒段、辣椒粉、料酒、生粉\",\n" +
            "price: 50,\n" +
            "thumbnail: \"http://i3.meishichina.com/attachment/recipe/2017/12/11/20171211151299710119313.jpg@!c320\"\n" +
            "},\n" +
            "{\n" +
            "id: 5,\n" +
            "name: \"干锅鸡翅\",\n" +
            "description: \"原料：鸡翅中、土豆、洋葱、莴笋、豆瓣酱、生抽、鸡粉、胡椒粉、蒜末、姜末、葱花、辣椒段、花椒、白芝麻\",\n" +
            "price: 40,\n" +
            "thumbnail: \"http://i3.meishichina.com/attachment/recipe/2017/12/10/20171210151291442369313.jpg@!c320\"\n" +
            "},\n" +
            "{\n" +
            "id: 6,\n" +
            "name: \"剁椒烧鱼头\",\n" +
            "description: \"原料：鲢鱼头、料酒、郫县豆瓣酱、剁椒酱、姜片、白糖、蒜叶\",\n" +
            "price: 230,\n" +
            "thumbnail: \"http://i3.meishichina.com/attachment/recipe/2017/12/05/20171205151243748453413.jpg@!c320\"\n" +
            "},\n" +
            "{\n" +
            "id: 7,\n" +
            "name: \"罗非鱼炖豆腐\",\n" +
            "description: \"原料：罗非鱼、豆腐、葱、姜、蒜、八角、花椒、香菜、花生油、盐、鸡精、料酒、香醋、白糖、水\",\n" +
            "price: 330,\n" +
            "thumbnail: \"http://i3.meishichina.com/attachment/recipe/2017/12/02/20171202151218046970713.jpg@!c320\"\n" +
            "},\n" +
            "{\n" +
            "id: 8,\n" +
            "name: \"红酒炖牛肉\",\n" +
            "description: \"原料：牛腩、干红葡萄酒、大葱段、黑胡椒粉、冰糖、鸡精、干辣椒、陈皮、八角、花椒、桂皮、黄油、面粉、盐、蒜、香叶\",\n" +
            "price: 430,\n" +
            "thumbnail: \"http://i3.meishichina.com/attachment/recipe/2017/12/02/20171202151217822513313.jpg@!c320\"\n" +
            "}\n" +
            "]";

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private List<Item> cartList;
    private CartListAdapter mAdapter;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        coordinatorLayout = findViewById(R.id.coordinator_layout);
        cartList = new ArrayList<>();
        mAdapter = new CartListAdapter(this, cartList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

        List<Item> items = new Gson().fromJson(JSON, new TypeToken<List<Item>>() {
        }.getType());

        // adding items to cart list
        cartList.clear();
        cartList.addAll(items);

        // refreshing recycler view
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof CartListAdapter.MyViewHolder) {
            // get the removed item name to display it in snack bar
            String name = cartList.get(viewHolder.getAdapterPosition()).getName();

            // backup of removed item for undo purpose
            final Item deletedItem = cartList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            mAdapter.removeItem(viewHolder.getAdapterPosition());

            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, name + " 已经删除!", Snackbar.LENGTH_LONG);
            snackbar.setAction("撤销", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    mAdapter.restoreItem(deletedItem, deletedIndex);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }
}
