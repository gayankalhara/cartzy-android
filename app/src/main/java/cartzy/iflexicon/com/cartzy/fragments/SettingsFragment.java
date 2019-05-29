package cartzy.iflexicon.com.cartzy.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;

import cartzy.iflexicon.com.cartzy.Cartzy;
import cartzy.iflexicon.com.cartzy.R;

public class SettingsFragment extends PreferenceFragment {

    private Preference logoutPreference, itemContactUs, aboutPreference, changePassword, itemServices, itemTerms, itemThanks, itemBlackList, itemNotifications, itemDeactivateAccount;
    private CheckBoxPreference allowMessages;

    private ProgressDialog pDialog;

    int mAllowMessages;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.settings);

        Preference pref = findPreference("settings_version");

        pref.setTitle(getString(R.string.app_name) + " v" + getString(R.string.app_version));

        pref = findPreference("settings_logout");

        pref.setSummary("gayankalhara");

        logoutPreference = findPreference("settings_logout");
        aboutPreference = findPreference("settings_version");
        changePassword = findPreference("settings_change_password");
        itemDeactivateAccount = findPreference("settings_deactivate_account");
        itemServices = findPreference("settings_services");
        itemTerms = findPreference("settings_terms");
        itemThanks = findPreference("settings_thanks");
        itemBlackList = findPreference("settings_blocked_list");
        itemNotifications = findPreference("settings_push_notifications");
        itemContactUs = findPreference("settings_contact_us");


            PreferenceCategory headerGeneral = (PreferenceCategory) findPreference("header_general");
            headerGeneral.removePreference(itemServices);


        itemNotifications.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

            public boolean onPreferenceClick(Preference arg0) {

                return true;
            }
        });

        itemBlackList.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

            public boolean onPreferenceClick(Preference arg0) {

                return true;
            }
        });

    }
}