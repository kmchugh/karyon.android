package karyon.os;

import karyon.DynamicEnum;

/**
 * Represents the OS used by the Android
 */
public class AndroidOS
    extends OS
{
    // Register the OS with the System
    static
    {
        DynamicEnum.register(new AndroidOS());
    }

    /**
     * Create a new instance of AndroidOS
     */
    public AndroidOS()
    {
        super("ANDROID");
    }

    @Override
    protected boolean test(String tcSystemName)
    {
        return tcSystemName.contains("android");
    }

    @Override
    public String getSharedLibraryExtension()
    {
        return "apklib";
    }

    @Override
    public String getName() {
        return "Android";
    }
}
