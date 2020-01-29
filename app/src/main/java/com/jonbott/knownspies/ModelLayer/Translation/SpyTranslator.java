package com.jonbott.knownspies.ModelLayer.Translation;

import com.jonbott.knownspies.ModelLayer.DTOs.SpyDTO;
import com.jonbott.knownspies.ModelLayer.Database.Realm.Spy;

import io.realm.Realm;

public interface SpyTranslator {

    SpyDTO translate (Spy from);

    Spy translate (SpyDTO dto, Realm realm);
}
