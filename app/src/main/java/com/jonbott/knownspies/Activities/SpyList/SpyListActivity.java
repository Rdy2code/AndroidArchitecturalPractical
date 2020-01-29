package com.jonbott.knownspies.Activities.SpyList;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jonbott.knownspies.Coordinators.RootCoordinator;
import com.jonbott.knownspies.Dependencies.DependencyRegistry;
import com.jonbott.knownspies.ModelLayer.DTOs.SpyDTO;
import com.jonbott.knownspies.ModelLayer.Enums.Source;
import com.jonbott.knownspies.R;

import java.util.ArrayList;
import java.util.List;

public class SpyListActivity extends AppCompatActivity {

    private static final String TAG = "SpyListActivity";

    private SpyListPresenter presenter;
    private RootCoordinator coordinator;
    private RecyclerView recyclerView;

    //Move data logic into presenter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_spy_list);

        attachUi();

        DependencyRegistry.shared.inject(this);
    }

    //region Injection Methods
    public void configureWith(SpyListPresenter presenter, RootCoordinator coordinator) {
        this.presenter = presenter;
        this.coordinator = coordinator;
        loadData();
        setUpObservables();
    }

    private void setUpObservables() {

        presenter.spies().subscribe(spies -> {
            SpyViewAdapter adapter = (SpyViewAdapter) recyclerView.getAdapter();
            adapter.setSpies(spies);
        });
    }
    //endregion

    //region Helper Methods
    private void attachUi() {
        Button newSpyButton = (Button) findViewById(R.id.new_spy_button);
        newSpyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.addNewSpy();
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(this);

        recyclerView = (RecyclerView) findViewById(R.id.spy_recycler_view);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

        initializeListView();
    }

    //endregion

    //region Data Process specific to SpyListActivity

    private void loadData() {
        presenter.loadData(this::onDataReceived);
    }

    //region User Interaction

    private void rowTapped(int position) {
        SpyDTO spy = presenter.spies().getValue().get(position);
        gotoSpyDetails(spy.id);
    }

    private void onDataReceived(Source source) {
        String message = String.format("Data from %s", source.name());
        Toast.makeText(SpyListActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    //endregion

    //region List View Adapter

    private void initializeListView() {
        SpyViewAdapter adapter = new SpyViewAdapter((v, position) -> rowTapped(position));
        recyclerView.setAdapter(adapter);
    }

    //endregion

    //region Navigation

    private void gotoSpyDetails(int spyId) {

        coordinator.handleSpyCellTapped(this, spyId);
    }

    //endregion

}
