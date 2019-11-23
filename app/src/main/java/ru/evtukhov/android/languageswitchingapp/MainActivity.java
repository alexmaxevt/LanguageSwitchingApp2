package ru.evtukhov.android.languageswitchingapp;

import androidx.annotation.IntDef;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Spinner language;
    private Spinner margins;
    private Button ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_main);
        initViews();
        initSpinnerLanguage();
    }

    private void initViews() {
        language = findViewById(R.id.spinner);
        margins = findViewById(R.id.spinnerMargin);
        ok = findViewById(R.id.button);
    }

    @IntDef({Language.RUSSIAN, Language.ENGLISH})
    @Retention(RetentionPolicy.SOURCE)
    private @interface Language {
        int RUSSIAN = 0;
        int ENGLISH = 1;
    }

    @IntDef({Margin.SMALL, Margin.AVERAGE, Margin.LARGE})
    @Retention(RetentionPolicy.SOURCE)
    private @interface Margin {
        int SMALL = 0;
        int AVERAGE = 1;
        int LARGE = 2;
    }

    private void initSpinnerLanguage() {
        ArrayAdapter<CharSequence> spinnerLanguage = ArrayAdapter.createFromResource(this, R.array.language, android.R.layout.simple_spinner_item);
        spinnerLanguage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        language.setAdapter(spinnerLanguage);
        language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                initSpinnerMargin(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initSpinnerMargin(final int languageNumber) {
        ArrayAdapter<CharSequence> spinnerColors = ArrayAdapter.createFromResource(this, R.array.margins, android.R.layout.simple_spinner_item);
        spinnerColors.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        margins.setAdapter(spinnerColors);
        margins.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int margin, long id) {
                btnOk(languageNumber, margin);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void btnOk(@Language final int lang, @Margin final int margin) {
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Locale locale = setLocale(lang);
                setLanguage(locale);
                switch (margin) {
                    case Margin.SMALL:
                        Utils.changeToTheme(MainActivity.this, Utils.THEME_SMALL);
                        break;
                    case Margin.AVERAGE:
                        Utils.changeToTheme(MainActivity.this, Utils.THEME_AVERAGE);
                        break;
                    case Margin.LARGE:
                        Utils.changeToTheme(MainActivity.this, Utils.THEME_LARGE);
                        break;
                }
            }
        });
    }

    private Locale setLocale(@Language final int lang) {
        String language = "";
        switch (lang) {
            case Language.RUSSIAN:
                language = "ru";
                break;
            case Language.ENGLISH:
                language = "en";
                break;
        }
        return new Locale(language);
    }

    private void setLanguage(final Locale language) {
        Configuration configuration = new Configuration();
        configuration.setLocale(language);
        getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
        recreate();
    }
}
