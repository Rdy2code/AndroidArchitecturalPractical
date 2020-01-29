package com.jonbott.knownspies.ModelLayer.Network;

import io.reactivex.functions.Consumer;

public interface NetworkLayer {
    void loadJson(Consumer<String> finished);
}
