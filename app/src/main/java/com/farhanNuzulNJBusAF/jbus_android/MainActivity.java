package com.farhanNuzulNJBusAF.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.farhanNuzulNJBusAF.jbus_android.model.Bus;

import java.util.ArrayList;
import java.util.List;

/**
 * The main activity for displaying a paginated list of buses.
 *
 * <p>
 * This activity provides functionality for paginating through a list of buses, displaying a specified number of buses per page.
 * It includes navigation buttons for moving to the previous and next pages, and it allows users to access their account profile.
 * </p>
 *
 * @see androidx.appcompat.app.AppCompatActivity
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {
    public ListView busListView;
    public BusArrayAdapter bus_ListView;
    private Button[] btns;
    private int currentPage = 0;
    private int pageSize = 10; // kalian dapat bereksperimen dengan field ini
    private int listSize;
    private int noOfPages;
    private List<Bus> listBus = new ArrayList<>();
    private Button prevButton = null;
    private Button nextButton = null;
    private HorizontalScrollView pageScroll = null;

    /**
     * Called when the activity is starting. This is where most initialization should go.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bus_ListView = new BusArrayAdapter(this, Bus.sampleBusList(15));
        busListView = findViewById(R.id.ListView_bus);
        busListView.setAdapter(bus_ListView);

        prevButton = findViewById(R.id.prev_page);
        nextButton = findViewById(R.id.next_page);
        pageScroll = findViewById(R.id.page_number_scroll);
        busListView = findViewById(R.id.ListView_bus);
        listBus = Bus.sampleBusList(30);
        listSize = listBus.size();
        paginationFooter();
        goToPage(currentPage);

        prevButton.setOnClickListener(v -> {
            currentPage = currentPage != 0? currentPage-1 : 0;
            goToPage(currentPage);
        });
        nextButton.setOnClickListener(v -> {
            currentPage = currentPage != noOfPages -1? currentPage+1 : currentPage;
            goToPage(currentPage);
        });
    }

    /**
     * Create options menu in the action bar.
     *
     * @param menu The options menu in which the items are placed.
     * @return true for the menu to be displayed.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    /**
     * Handle menu item selection.
     *
     * @param menu The menu item that was selected.
     * @return true if the menu item was handled successfully.
     */
    public boolean onOptionsItemSelected (MenuItem menu){
        if (menu.getItemId() == R.id.account_profile){
                Intent aboutMeIntent = new Intent(this, AboutMeActivity.class);
                startActivity(aboutMeIntent);
                return true;
        }
        else return false;
    }

    /**
     * Create the pagination footer with page number buttons.
     */
    private void paginationFooter() {
        int val = listSize % pageSize;
        val = val == 0 ? 0:1;
        noOfPages = listSize / pageSize + val;
        LinearLayout ll = findViewById(R.id.btn_layout);
        btns = new Button[noOfPages];
        if (noOfPages <= 6) {
            ((FrameLayout.LayoutParams) ll.getLayoutParams()).gravity =
                    Gravity.CENTER;
        }
        for (int i = 0; i < noOfPages; i++) {
            btns[i]=new Button(this);
            btns[i].setBackgroundColor(getResources().getColor(android.R.color.transparent));
            btns[i].setText(""+(i+1));
            btns[i].setTextColor(getResources().getColor(R.color.black));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(150,
                    150);
            ll.addView(btns[i], lp);
            final int j = i;
            btns[j].setOnClickListener(v -> {
                currentPage = j;
                goToPage(j);
            });
        }
    }

    /**
     * Navigate to the specified page and update the view.
     *
     * @param index The index of the page to navigate to.
     */
    private void goToPage(int index) {
        for (int i = 0; i< noOfPages; i++) {
            if (i == index) {
                btns[index].setBackgroundDrawable(getResources().getDrawable(R.drawable.circle));
                btns[i].setTextColor(getResources().getColor(android.R.color.black));
                scrollToItem(btns[index]);
                viewPaginatedList(listBus, currentPage);
            } else {
                btns[i].setBackgroundColor(getResources().getColor(android.R.color.transparent));
                btns[i].setTextColor(getResources().getColor(android.R.color.black));
            }
        }
    }

    /**
     * Smoothly scroll to the selected item.
     *
     * @param item The button corresponding to the selected item.
     */
    private void scrollToItem(Button item) {
        int scrollX = item.getLeft() - (pageScroll.getWidth() - item.getWidth()) /
                2;
        pageScroll.smoothScrollTo(scrollX, 0);
    }

    /**
     * Display the paginated list based on the current page.
     *
     * @param listBus The complete list of buses.
     * @param page    The current page number.
     */
    private void viewPaginatedList(List<Bus> listBus, int page) {
        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, listBus.size());
        List<Bus> paginatedList = listBus.subList(startIndex, endIndex);
        bus_ListView = (BusArrayAdapter) busListView.getAdapter();
        bus_ListView.clear();
        bus_ListView.addAll(paginatedList);
    }
}