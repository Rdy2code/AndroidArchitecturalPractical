package com.jonbott.knownspies.Coordinators;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jonbott.knownspies.Activities.Details.SpyDetailsActivity;
import com.jonbott.knownspies.Activities.SecretDetails.SecretDetailsActivity;
import com.jonbott.knownspies.Activities.SpyList.SpyListActivity;
import com.jonbott.knownspies.Helpers.Constants;

public class RootCoordinator {


    public void handleSecretButtonTapped(Context context, int spyId) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.spyIdKey, spyId);

        Intent intent = new Intent(context, SecretDetailsActivity.class);
        intent.putExtras(bundle);

        context.startActivity(intent);
    }

    public void handleFinishedClicked(Context context) {

        Intent intent = new Intent(context, SpyListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    public void handleSpyCellTapped(Context context, int spyId) {

        Bundle bundle = new Bundle();
        bundle.putInt(Constants.spyIdKey, spyId);

        Intent intent = new Intent(context, SpyDetailsActivity.class);
        intent.putExtras(bundle);

        context.startActivity(intent);
    }
}
