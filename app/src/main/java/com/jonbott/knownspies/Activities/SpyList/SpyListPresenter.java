package com.jonbott.knownspies.Activities.SpyList;

import com.jonbott.knownspies.ModelLayer.DTOs.SpyDTO;
import com.jonbott.knownspies.ModelLayer.Enums.Source;

import java.util.List;

import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;

public interface SpyListPresenter {
    void loadData(Consumer<Source> notifyDataReceived);

    void addNewSpy();

    BehaviorSubject<List<SpyDTO>> spies();  //RxJava type to be used as observable
}
