==
Creating an Android Application

- Create an Application Class.
The application class MUST extend from karyon.Android.Applications.Application

The application class MUST declare an inner class like the following:
public static class AndroidApplication extend Application.AndroidApplication
{
}

This declaration is just until a better way can be determined to link up the
karyon application and the android application

- Set up the AndroidManifest to load the inner application class by
setting the android:name attribute to the inner class.  This is done by using the
'$' to denote the inner class.  e.g. <fully qualified outer class>$<inner class>

Set the activity to kayron.Android.Activities.SplashActivity, and run the app.


To install the required libraries for robolectric 2.4:

mvn install:install-file -DgroupId=com.google.android.maps \
  -DartifactId=maps \
  -Dversion=18_r3 \
  -Dpackaging=jar \
  -Dfile="$ANDROID_HOME/add-ons/addon-google_apis-google-18/libs/maps.jar"

mvn install:install-file -DgroupId=com.android.support \
  -DartifactId=support-v4 \
  -Dversion=19.0.1 \
  -Dpackaging=jar \
  -Dfile="$ANDROID_HOME/extras/android/m2repository/com/android/support/support-v4/19.0.1/support-v4-19.0.1.jar"