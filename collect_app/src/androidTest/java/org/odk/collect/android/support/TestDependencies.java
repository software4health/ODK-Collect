package org.odk.collect.android.support;

import android.app.Application;
import android.content.Context;
import android.webkit.MimeTypeMap;

import androidx.work.WorkManager;

import org.odk.collect.android.gdrive.GoogleAccountPicker;
import org.odk.collect.android.gdrive.GoogleApiProvider;
import org.odk.collect.android.gdrive.sheets.DriveApi;
import org.odk.collect.android.gdrive.sheets.SheetsApi;
import org.odk.collect.android.injection.config.AppDependencyModule;
import org.odk.collect.android.openrosa.OpenRosaHttpInterface;
import org.odk.collect.android.storage.StoragePathProvider;
import org.odk.collect.android.version.VersionInformation;
import org.odk.collect.android.views.BarcodeViewDecoder;
import org.odk.collect.async.Scheduler;
import org.odk.collect.utilities.UserAgentProvider;

public class TestDependencies extends AppDependencyModule {

    public final StubOpenRosaServer server = new StubOpenRosaServer();
    public final TestScheduler scheduler = new TestScheduler();
    public final FakeGoogleApi googleApi = new FakeGoogleApi();
    public final FakeGoogleAccountPicker googleAccountPicker = new FakeGoogleAccountPicker();
    public final StoragePathProvider storagePathProvider = new StoragePathProvider();
    public final StubBarcodeViewDecoder stubBarcodeViewDecoder = new StubBarcodeViewDecoder();

    @Override
    public OpenRosaHttpInterface provideHttpInterface(MimeTypeMap mimeTypeMap, UserAgentProvider userAgentProvider, Application application, VersionInformation versionInformation) {
        return server;
    }

    @Override
    public Scheduler providesScheduler(WorkManager workManager) {
        return scheduler;
    }

    @Override
    public GoogleApiProvider providesGoogleApiProvider(Context context) {
        return new GoogleApiProvider(context) {

            @Override
            public SheetsApi getSheetsApi(String account) {
                googleApi.setAttemptAccount(account);
                return googleApi;
            }

            @Override
            public DriveApi getDriveApi(String account) {
                googleApi.setAttemptAccount(account);
                return googleApi;
            }
        };
    }

    @Override
    public GoogleAccountPicker providesGoogleAccountPicker(Context context) {
        return googleAccountPicker;
    }

    @Override
    public BarcodeViewDecoder providesBarcodeViewDecoder() {
        return stubBarcodeViewDecoder;
    }
}
