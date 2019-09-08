package com.example.newdiplwm;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreferenceCompat;

public class SettingsClass extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        final Context myContext = this.getActivity();
        //set your theme
        myContext.setTheme(R.style.settingsTheme);
        addPreferencesFromResource(R.xml.settings_prefs);


        final SwitchPreferenceCompat switchPreferenceCompat = (SwitchPreferenceCompat) getPreferenceManager().findPreference("rememberME");
        switchPreferenceCompat.setSummaryProvider(new Preference.SummaryProvider() {
            @Override
            public CharSequence provideSummary(Preference preference) {
                if (switchPreferenceCompat.isChecked()) {
                    return "Ενεργό";
                }
                return "Ανενεργό";


            }
        });

        final SwitchPreferenceCompat switchPreferenceCompat1 = (SwitchPreferenceCompat) getPreferenceManager().findPreference("testing");
        switchPreferenceCompat1.setSummaryProvider(new Preference.SummaryProvider() {
            @Override
            public CharSequence provideSummary(Preference preference) {
                if (switchPreferenceCompat1.isChecked()) {
                    return "Απενεργοποιημένοι";
                }
                return "Ενεργοποιημένοι";


            }
        });


       // switchPreferenceCompat.setIcon(R.drawable.arrowdown24);
//        android:summaryOn="Ενεργό"
//        android:summaryOff="Ανενεργό"
    }
}
