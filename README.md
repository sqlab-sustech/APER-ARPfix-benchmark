# ARPfix Benchmark

The ARPfix benchmark of Android runtime permission (ARP) misuse bugs.

[![GitHub license](https://img.shields.io/github/license/sqlab-sustech/APER-ARPfix-benchmark)](https://github.com/sqlab-sustech/APER-ARPfix-benchmark/blob/master/LICENSE)
[![DOI](https://zenodo.org/badge/DOI/10.5281/zenodo.5699434.svg)](https://doi.org/10.5281/zenodo.5699434)

## Overview

There are two types of ARP bugs:

+ __Type-1 (Missing Permission Check)__: Adangerous API is called without a permission check on the target Android version.
+ __Type-2 (Incompatible Permission Usage)__: A dangerous API is called on incompatible platforms, or the evolution of permission specification is not fully handled.

The benchmark consists of 60 apps, including **35 Type-1** bugs and **25 Type-2** bugs.
Each app has a buggy version and a fixed version. Thus ther are **120 APKs** in total.

## File Formats

This repo contains each app's reduced source code, and two corresponding built APKs. For example, in [Type-1](https://github.com/aper-project/arpfix-benchmark/tree/master/Type-1):

+ [Androidcommons1530](https://github.com/aper-project/arpfix-benchmark/tree/master/Type-1/Androidcommons1530): The reduced source code of the benchmarking app *AndroidCommons*
+ [AndroidCommons1530_Buggy.apk](https://github.com/aper-project/arpfix-benchmark/blob/master/Type-1/AndroidCommons1530_Buggy.apk): The app's buggy version of this issue
+ [AndroidCommons1530_Fixed.apk](https://github.com/aper-project/arpfix-benchmark/blob/master/Type-1/AndroidCommons1530_Fixed.apk): The app's fixed (bug-free) version of this issue

For this app's source code, it is the buggy version by default. In the [ContributionsListFragment.java](https://github.com/aper-project/arpfix-benchmark/blob/master/Type-1/Androidcommons1530/app/src/main/java/com/example/androidcommons3ec9/ContributionsListFragment.java) file:

```java
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //See http://stackoverflow.com/questions/33169455/onrequestpermissionsresult-not-being-called-in-dialog-fragment
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
                return false;
            } else {*/
                Intent nearbyIntent = new Intent(getActivity(), NearbyActivity.class);
                startActivity(nearbyIntent);
//            }
//        }
        return true;
    }
```

By uncommenting the code, we can obtain the fixed version of this app.

## Issues Details

### Type-1 issues

| Package ID                | Issue URL                                                                                                                                                                |
| ------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| AndroidCommons\_1530      | https://github.com/commons-app/apps-android-commons/commit/1530ddc969453451217f0240c5822acc35818d6a                                                                     |
| AndroidCommons\_Da38      | https://github.com/commons-app/apps-android-commons/commit/da3895342b51459918fbbde6d0ec21c1bdce6c29                                                                     |
| CameraviewDemo            | https://github.com/natario1/CameraView/issues/129                                                                                                                       |
| CollectDemo               | https://github.com/getodk/collect/issues/644                                                                                                                            |
| ConsoleLauncher           | https://github.com/fAndreuzzi/TUI-ConsoleLauncher/issues/1                                                                                                              |
| CreatePDF                 | https://github.com/Swati4star/Images-to-PDF/issues/1                                                                                                                    |
| Dashboard                 | https://github.com/databits3883/Android-Dashboard/commit/97780f0c465f6538fffb5fb2f625ce34bf59802b                                                                       |
| Evercam                   | [https://github.com/evercam/evercam-android/issues/124](https://github.com/evercam/evercam-android/issues/124)                                                          |
| GetBackGPS                | [https://github.com/ruleant/getback\_gps/commit/f935cf8d72e29cf8f0ae336c12757aebbc16f510](https://github.com/k9mail/k-9/issues/2110)                                    |
| GoodWeather\_2b5e         | [https://github.com/qqq3/good-weather/commit/2b5e9597c14da895bc448da793d3686c063c43a](https://github.com/commons-app/apps-android-commons/issues/173)                   |
| GoodWeather               | [https://github.com/qqq3/good-weather/commit/81eab554bae5299e33a4ce9babb690359647115c](https://github.com/owncloud/android/issues/1343)                                 |
| GoodWeather\_e1be         | [https://github.com/qqq3/good-weather/commit/e1bebcad61b8625bfcb0b9284b30d75e1dc3a079](https://github.com/vishesh/sealnote/pull/28)                                     |
| ImageCipher               | [https://github.com/SKocur/Image-Cipher/issues/4](https://github.com/hrydgard/ppsspp/pull/8225)                                                                         |
| K9Mail                    | [https://github.com/k9mail/k-9/issues/2110](https://github.com/daneren2005/Subsonic/issues/732)                                                                         |
| KaspatContacts            | [https://github.com/arshadkazmi42/android-contacts-scifiui/issues/2](https://github.com/freeotp/freeotp-android/pull/138)                                               |
| LandScapeViewoCaptureDemo | [https://github.com/JeroenMols/LandscapeVideoCamera/pull/70](https://github.com/ankidroid/Anki-Android/pull/3644)                                                       |
| LocationSample            | [https://github.com/android/location-samples/commit/f01b254b87dc61a8dedca6e8a46b881d8ad7dac4](https://github.com/Neamar/KISS/issues/549)                                |
| MapBoxEvent               | [https://github.com/mapbox/mapbox-events-android/commit/ab6e7ca3d47e8ff05339d91e1a567053961e46c4#](https://github.com/android/ndk-samples/issues/90)                    |
| mapbox                    | [https://github.com/mapbox/mapbox-android-demo/commit/514f84e5b8d175ec7f35b693a41a2c814905464f](https://github.com/OneBusAway/onebusaway-android/issues/330)            |
| Mapswithme                | https://github.com/mapsme/omim/commit/74f987743234c6fdef9970a15ea38d049dc2bc63                                                                                          |
| Muzei                     | [https://github.com/romannurik/muzei/commit/687917ad04e8c5241dd892cb4b697f7e9c85e1af](https://github.com/signalapp/Signal-Android/issues/3983)                          |
| MyPlaceDemo               | https://github.com/warren-bank/Android-MyPlaces/commit/b422393e9ae2139e2600106f86c660ed0118e755                                                                         |
| Navit                     | [https://github.com/navit-gps/navit/pull/440](https://github.com/VREMSoftwareDevelopment/WiFiAnalyzer/issues/23)                                                        |
| NearbyPlaces              | https://github.com/Esri/nearby-android/issues/76                                                                                                                        |
| PanicTrigger              | [https://github.com/0x5ECF4ULT/PanicTrigger/issues/25](https://github.com/0x5ECF4ULT/PanicTrigger/issues/25)                                                            |
| Protesttracker            | [https://github.com/LucasG234/ProtestTracker/issues/25](https://github.com/chrisboyle/sgtpuzzles/issues/276)                                                            |
| QKSMS                     | https://github.com/moezbhatti/qksms/commit/6385a5707020e6fd64e8d47c8e73dbd7cfd48073                                                                                     |
| SkyMap                    | [https://github.com/sky-map-team/stardroid/pull/90/files](https://github.com/SufficientlySecure/document-viewer/issues/302)                                             |
| SMS\_Parsing              | [https://github.com/JoaquimLey/sms-parsing/pull/2](https://github.com/erickok/transdroid/issues/463)                                                                    |
| TransDroid                | [https://github.com/erickok/transdroid/commit/78dd4bdc57cfe3894e6537ffa3fb4ec90a357945](https://github.com/VREMSoftwareDevelopment/WiFiAnalyzer/issues/250)             |
| Ventivader                | [https://github.com/ventivader/ventivader-android/commit/ece98305917858309ed490cbbd14430e4dc95706](https://github.com/BramBonne/privacypolice/issues/30)                |
| VREM                      | [https://github.com/VREMSoftwareDevelopment/WiFiAnalyzer/commit/ea6ac4503783067b3ce94275e4219d250707fd0e](https://github.com/fAndreuzzi/TUI-ConsoleLauncher/issues/234) |
| WeeChatAndroid            | [https://github.com/ubergeek42/weechat-android/commit/1c4197232bc8b4a1f67c01438c502ab9aaf8b13c](https://github.com/VREMSoftwareDevelopment/WiFiAnalyzer/issues/23)      |
| Wiglenet                  | [https://github.com/wiglenet/wigle-wifi-wardriving/commit/ad2d5a74beaee3447a97718774f1cfa87732bfac](https://github.com/VREMSoftwareDevelopment/WiFiAnalyzer/issues/201) |
| WordPress                 | [https://github.com/wordpress-mobile/WordPress-Android/pull/3328](https://github.com/wordpress-mobile/WordPress-Android/pull/3328)                                      |

### Type-2 issues

| Package ID            | Issue URL                                                                                                                                |
| --------------------- | ---------------------------------------------------------------------------------------------------------------------------------------- |
| AndroidClient         | https://github.com/kontalk/androidclient/issues/1201                                                                                     |
| Android-Homeassistant | https://github.com/home-assistant/android/issues/630                                                                                     |
| Android-testdpc       | https://github.com/googlesamples/android-testdpc/commit/bd422f67c42999954cd72fd798525f681a005aa3                                         |
| Ayanda                | https://github.com/bantucracy/ayanda/issues/17                                                                                           |
| Campus-android        | https://github.com/TUM-Dev/Campus-Android/issues/569                                                                                     |
| CheesecakeAppUpdater  | https://github.com/itachi1706/CheesecakeAppUpdater/issues/22                                                                             |
| Connectivity-sample   | https://github.com/android/connectivity-samples/issues/13                                                                                |
| Device-info           | https://github.com/react-native-community/react-native-device-info/issues/595                                                            |
| Glide                 | https://github.com/bumptech/glide/commit/1c51b24b5af61a73267e36e486abe7e5157002e7#diff-975e5536d3b668c60ceda8b160789feb                  |
| Lawnchair             | [https://github.com/LawnchairLauncher/lawnchair/issues/982](https://github.com/LawnchairLauncher/lawnchair/issues/982)                   |
| Mapbox-Events-Android | https://github.com/mapbox/mapbox-events-android/issues/482                                                                               |
| MobileCamera          | https://github.com/wxson7282/MobileCamera/issues/1                                                                                       |
| Photomanager          | https://github.com/CaiJingLong/flutter\_photo\_manager/issues/169                                                                        |
| RNDeviceInfo          | https://github.com/react-native-community/react-native-device-info/issues/268                                                            |
| RNWIFIP2P             | https://github.com/kirillzyusko/react-native-wifi-p2p/issues/13#issuecomment-550215269                                                   |
| Robolectric           | https://github.com/robolectric/robolectric/pull/5355/files                                                                               |
| Secure-preferences    | https://github.com/scottyab/secure-preferences/issues/72                                                                                 |
| SingleSignOn          | https://github.com/nextcloud/Android-SingleSignOn/pull/62                                                                                |
| Skunkworks-crow       | https://github.com/GetODK/skunkworks-crow/issues/284                                                                                     |
| SmsPlugin             | https://github.com/pbakondy/cordova-plugin-sim/issues/21                                                                                 |
| Syncthing-android     | https://github.com/Catfriend1/syncthing-android/issues/514                                                                               |
| Transdroid            | https://github.com/erickok/transdroid/issues/463                                                                                         |
| VpnHotSpot            | [https://github.com/LawnchairLauncher/lawnchair/issues/982](https://github.com/LawnchairLauncher/lawnchair/issues/982)                   |
| WiFiAnalyzer          | [https://github.com/VREMSoftwareDevelopment/WiFiAnalyzer/issues/250](https://github.com/VREMSoftwareDevelopment/WiFiAnalyzer/issues/250) |
| WifiFlutterDemo       | https://github.com/alternadom/WiFiFlutter/issues/29                                                                                      |

---

The results of *Aper* comparing with other tools can be found on our [website](https://aper-project.github.io/benchmarks).
