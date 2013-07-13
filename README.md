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