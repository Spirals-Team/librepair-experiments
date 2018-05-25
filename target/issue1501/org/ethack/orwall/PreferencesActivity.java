package org.ethack.orwall;


public class PreferencesActivity extends android.preference.PreferenceActivity {
    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @java.lang.Override
    public void onBuildHeaders(java.util.List<org.ethack.orwall.Header> target) {
        loadHeadersFromResource(R.xml.preferences_header, target);
    }

    @java.lang.Override
    protected boolean isValidFragment(java.lang.String fragmentName) {
        java.lang.String prepend = "org.ethack.orwall.PreferencesActivity$";
        java.lang.String[] fragments = new java.lang.String[]{ prepend + "ScriptPrefs", prepend + "SpecialApps", prepend + "NetworkPrefs", prepend + "ProxyPorts" };
        return java.util.Arrays.asList(fragments).contains(fragmentName);
    }

    @java.lang.Override
    public void onDestroy() {
        onDestroy();
    }

    public static class ScriptPrefs extends android.preference.PreferenceFragment {
        private SharedPreferences.OnSharedPreferenceChangeListener listener = new android.content.SharedPreferences.OnSharedPreferenceChangeListener() {
            @java.lang.Override
            public void onSharedPreferenceChanged(android.content.SharedPreferences sharedPreferences, java.lang.String s) {
                org.ethack.orwall.iptables.InitializeIptables iptables = new org.ethack.orwall.iptables.InitializeIptables(getActivity());
                if ((sharedPreferences.getBoolean(s, true)) && (s.equals("enforce_init_script"))) {
                    iptables.installInitScript(getActivity());
                }
                if (((sharedPreferences.getBoolean(s, true)) && (s.equals("deactivate_init_script"))) && (!(sharedPreferences.getBoolean("enforce_init_script", true)))) {
                    iptables.removeIniScript();
                }
            }
        };

        @java.lang.Override
        public void onCreate(android.os.Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            android.preference.PreferenceManager.setDefaultValues(getActivity(), R.xml.network_preference, true);
            addPreferencesFromResource(R.xml.fragment_init_pref);
        }

        @java.lang.Override
        public void onResume() {
            onResume();
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(listener);
        }

        @java.lang.Override
        public void onPause() {
            onPause();
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(listener);
        }
    }

    public static class SpecialApps extends android.preference.PreferenceFragment {
        private SharedPreferences.OnSharedPreferenceChangeListener listener = new android.content.SharedPreferences.OnSharedPreferenceChangeListener() {
            @java.lang.Override
            public void onSharedPreferenceChanged(android.content.SharedPreferences sharedPreferences, java.lang.String s) {
                return;
            }
        };

        @java.lang.Override
        public void onCreate(android.os.Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.fragment_apps_prefs);
        }

        @java.lang.Override
        public void onResume() {
            onResume();
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(listener);
        }

        @java.lang.Override
        public void onPause() {
            onPause();
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(listener);
        }
    }

    public static class NetworkPrefs extends android.preference.PreferenceFragment {
        private SharedPreferences.OnSharedPreferenceChangeListener listener = new android.content.SharedPreferences.OnSharedPreferenceChangeListener() {
            @java.lang.Override
            public void onSharedPreferenceChanged(android.content.SharedPreferences sharedPreferences, java.lang.String s) {
                org.ethack.orwall.iptables.InitializeIptables iptables = new org.ethack.orwall.iptables.InitializeIptables(getActivity());
                if (s.equals("enable_lan")) {
                    iptables.LANPolicy(sharedPreferences.getBoolean(s, false));
                }
                if (s.equals("enable_adb")) {
                    iptables.enableADB(sharedPreferences.getBoolean(s, false));
                }
                if (s.equals("enable_captive_portal")) {
                    android.content.Context context = getActivity();
                    android.content.Intent bgpProcess = new android.content.Intent(context, org.ethack.orwall.BackgroundProcess.class);
                    bgpProcess.putExtra(PARAM_ACTIVATE, sharedPreferences.getBoolean(s, false));
                    bgpProcess.putExtra(ACTION, ACTION_PORTAL);
                    context.startService(bgpProcess);
                }
            }
        };

        @java.lang.Override
        public void onCreate(android.os.Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            android.preference.PreferenceManager.setDefaultValues(getActivity(), R.xml.other_preferences, true);
            addPreferencesFromResource(R.xml.fragment_network_prefs);
        }

        @java.lang.Override
        public void onResume() {
            onResume();
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(listener);
        }

        @java.lang.Override
        public void onPause() {
            onPause();
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(listener);
        }
    }

    public static class ProxyPorts extends android.preference.PreferenceFragment {
        @java.lang.Override
        public void onCreate(android.os.Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.fragment_proxy_ports);
        }
    }
}

