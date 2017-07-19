# Seedroid Application

Seedroid Application is a framework for working together with one design pattern for transfer knowledge, handling bugs, take over project, and reusable function

## Getting Started

- Must known MVP or MVVM pattern
- Android data binding

## Installing to Project

###**Add and configure your gradle project :**
- add dependencies realm and google services
```
dependencies {
        ...
        classpath 'com.google.gms:google-services:3.0.0'
        classpath "io.realm:realm-gradle-plugin:3.0.0"
        ...
    }
```
- add maven repository
```
allprojects {
    repositories {
        jcenter()
        maven { url 'https://jitpack.io' }
    }
}
```

###**Add and configure your gradle application :**
- setting minimum SDK API 15 and setting multidex enable true
```
defaultConfig {
        ..
        minSdkVersion 15
        multiDexEnabled true
        ..
    }
```
- enable data binding and packaging option
```
android {
    ...
    dataBinding {
            enabled = true
        }
    packagingOptions {
            pickFirst 'META-INF/LICENSE'
        }
    ...
}
```
- add dependencies seedroid application and apply plugin google services under gradle application
```
dependencies {
    ...
    compile 'com.github.codigo-cyberlin-metadata:seedroid:v0.0.1-SNAP'
    ...
}
apply plugin: 'com.google.gms.google-services'
```
###**Configure new project :**
- **Make sure your application class extend SeedroidApplication**
```
public class ProjectApplication extends SeedroidApplication {
    ---
}
```
- **Create resource style that extending from BaseTheme style.**
```
<resources>
    <style name="AppTheme" parent="BaseTheme">
        ---
    </style>
</resources>
```
- **Configure your manifest file**
```
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    package="YOUR_PACKAGE">
    <application
        ---
        android:name=".ProjectApplication"
        android:theme="@style/AppTheme"
        tools:replace="android:name,android:theme">
    </application>
</manifest>
```
## Implementation 
After installing and build gradle success, now we can implement framework into a new project.
- **Create SampleModel on pivate mode**
```
public class SampleModel {
    --- Setter and Getter
}
```
- **Create your view interface that extending from BaseView interface.**
```
public interface SampleView extends BaseView {
    void sampleMethod1(SampleModel obj);
    void sampleMethod2(String message);
}
```
- **Create your presenter class that extending from BasePresenter class and implement onStartUI() method.**
```
public class SamplePresenter extends BasePresenter<SampleView> {
    @Override
    public void onStartUI() {
        ---
    }
}
```
- **Create layout use data binding**
```
<?xml version="1.0" encoding="utf-8"?>
<layout>

    <data>
        <variable
            name="presenter"
            type="YOUR_PACKAGE.SamplePresenter" />
        <variable
            name="model"
            type="YOUR_PACKAGE.SampleModel" />
    </data>

    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">
        ---
    </LinearLayout>
</layout>
```
- **Create BaseActivity and get view binding**
```
public class SampleActivity extends BaseActivity<SampleBinding, SampleView, SamplePresenter> implements SampleView {
        @Override
        public int attachLayout() { return R.layout.sample; }
        
        @Override
        public SamplePresenter createPresenter() { return new SamplePresenter(); }
    
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getViewBinding().setPresenter(getMvpPresenter());
        }
    
        @Override
        public void sampleMethod1(SampleModel obj) { getViewBinding().setModel(obj); }
    
        @Override
        public void sampleMethod2(String message) { --- }

    ---
}
```

- **Create BaseRecyclerActivity**
```
public class SampleListFragment extends BaseRecyclerActivity<SampleModel, SampleRecyclerFragmentView, SampleRecyclerPresenter>
        implements SampleRecyclerFragmentView {
    @Override
    public SampleRecyclerPresenter createPresenter() { return new SampleRecyclerPresenter();  }
    
    @Override
    public BaseRecyclerAdapter onInitAdapter() {
       return  SampleRecylerAdapter(customListView.getItems());
    }
    
    @Override
    public void onLoadItems(int limit, int offset) { … }
}
```
- **Create BaseRecyclerFragment**
```
public class SampleListFragment extends BaseRecyclerFragment<SampleModel, SampleRecyclerFragmentView, SampleRecyclerPresenter>
        implements SampleRecyclerFragmentView {
    @Override
    public SampleRecyclerPresenter createPresenter() { return new SampleRecyclerPresenter();  }
    
    @Override
    public BaseRecyclerAdapter onInitAdapter() {
       return  SampleRecylerAdapter(customListView.getItems());
    }
    
    @Override
    public void onLoadItems(int limit, int offset) { … }
}
```
<!-- - **More example can u get on there :**  -->

<!-- 
## Running the tests

Explain how to run the automated tests for this system

### Break down into end to end tests

Explain what these tests test and why

```
Give an example
```

### And coding style tests

Explain what these tests test and why

```
Give an example
```

## Deployment

Add additional notes about how to deploy this on a live system

## Built With

* [Dropwizard](http://www.dropwizard.io/1.0.2/docs/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [ROME](https://rometools.github.io/rome/) - Used to generate RSS Feeds

## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.
 -->

## Versioning

- **Version 0.0.1-SNAP is available (Prerelease/Develop)** 
```
com.github.codigo-cyberlin-metadata:seedroid:v0.0.1-SNAP
```

## Authors

* **codigo-cyberlin-metadata** 
* **codigo-android-developer-team**

<!-- 
## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
 -->
## Tips And Guide
* **google-service.json**

if you find issue google-service.json not found you can add inside main folder your project by copy or download from there:
```
[google-services.json](https://github.com/codigo-cyberlin-metadata/seedroid/blob/master/framework/google-services.json)

```
dont forget to change package name inside client array on your copy of google-service.json
```
  ....
  "client": [
    {
      "client_info": {
        "mobilesdk_app_id": "",
        "android_client_info": {
          "package_name": "YOUR_PACKAGE"
        }
      },
      "oauth_client": [
        {
          "client_id": "",
          "client_type": 1,
          "android_info": {
            "package_name": "YOUR_PACKAGE",
            "certificate_hash": "91032F56C31F349C83BDEA0BE379D1DA9BF94811"
          }
        },
        {
          "client_id": "",
          "client_type": 3
        }
      ],
      "api_key": [
        {
          "current_key": ""
        }
      ],
      "services": {
        "analytics_service": {
          "status": 1
        },
        "appinvite_service": {
          "status": 2,
          "other_platform_oauth_client": []
        },
        "ads_service": {
          "status": 2
        }
      }
    }
  ],
  ....
```
and if you use 2 build variant u can add 2 client inside array client with different package name
## Acknowledgments
**This framework inspired from there:**

* **com.google.firebase:firebase-core**
* **com.google.gms.google-services**
* **com.android.volley:volley**
* **net.gotev:uploadservice**
* **com.fasterxml.jackson.core:jackson-annotations**
* **com.fasterxml.jackson.core:jackson-databind**
* **com.github.bumptech.glide:glide**
* **jp.wasabeef:glide-transformations**
* **jp.co.cyberagent.android.gpuimage:gpuimage-library**
* **com.github.Ilhasoft:data-binding-validator**