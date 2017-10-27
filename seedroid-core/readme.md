# Seedroid Core

Seedroid Core is a diferent part of Seedroid framework and in this module is just a skeleton frame for build some application with MVP pattern with data binding, just base class without helper and custom activity, so u can use any REST Client for calling service

## Installing to Project

###**Add and configure your gradle project :**
- add maven repository
```
allprojects {
    repositories {
        jcenter()
        maven {
            url "http://jfrog.cips.stg.codigo.id/artifactory/libs-release-local"
            credentials {
                username 'consumer'
                password 'AP4dLfQtyxBwLNTE5kmHPgBCsfV'
            }
        }
    }
}
```

###**Add and configure your gradle application :**
- enable data binding and packaging option
```
android {
    ...
    dataBinding {
            enabled = true
        }
    ...
}
```
- add dependencies seedroid core
```
dependencies {
    ...
    compile(group: 'id.codigo.seedroid', name: 'seedroid-core', version: '0.0.2', ext: 'aar')
    ...
}
```
###**example :**
- u can see project sample on here
https://github.com/672009095/invoker