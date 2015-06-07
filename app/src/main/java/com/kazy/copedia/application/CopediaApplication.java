package com.kazy.copedia.application;

import com.facebook.stetho.Stetho;

import android.app.Application;

public class CopediaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initialize(
                Stetho.newInitializerBuilder(this).enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this)).build()
        );
    }

}
