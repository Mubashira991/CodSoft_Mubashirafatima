package com.example.currencyconvertor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView ConvertFromdropdown,ConvertTodropdown,convertRate,showConvRate;
    EditText amountToConvert;
    Button convertButton;
    ArrayList<String> arrayList;
    Dialog fromDialog, toDialog;
    String convertFromValue, convertToValue,conversionValue;
    String[] country = {"AFN","EUR","ALL","DZD","USD","AOA","XCD","ARS","AMD","AWG","AUD","AZN","BSD","BHD","BDT","BBD","BYN","BZD","XOF","BMD","INR","BTN"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConvertFromdropdown = findViewById(R.id.convertFromDropdown);
        ConvertTodropdown = findViewById(R.id.convertToDropdown);
        convertButton = findViewById(R.id.Btnconvert);
        convertRate = findViewById(R.id.Conversion_rate);
        amountToConvert = findViewById(R.id.amount_toConvertText);
        showConvRate = findViewById(R.id.showConvRate);

        arrayList = new ArrayList<>();
        for(String i: country)
        {
            arrayList.add(i);
        }

        ConvertFromdropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromDialog = new Dialog(MainActivity.this);
                fromDialog.setContentView(R.layout.spinner_from);
                fromDialog.getWindow().setLayout(650,800);
                fromDialog.show();

                EditText editText = fromDialog.findViewById(R.id.searchFrom);
                ListView listView = fromDialog.findViewById(R.id.listFrom);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1,arrayList);
                listView.setAdapter(adapter);

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                        adapter.getFilter().filter(s);

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        ConvertFromdropdown.setText(adapter.getItem(i));
                        fromDialog.dismiss();
                        convertFromValue = adapter.getItem(i);
                    }
                });


            }
        });

        ConvertTodropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              toDialog = new Dialog(MainActivity.this);
              toDialog.setContentView(R.layout.spinner_to);
              toDialog.getWindow().setLayout(650,800);
              toDialog.show();

              EditText editText = toDialog.findViewById(R.id.searchTo);
              ListView listView = toDialog.findViewById(R.id.listTo);

              ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1,arrayList);
              listView.setAdapter(adapter);

              editText.addTextChangedListener(new TextWatcher() {
                  @Override
                  public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                  }

                  @Override
                  public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                      adapter.getFilter().filter(s);

                  }

                  @Override
                  public void afterTextChanged(Editable editable) {

                  }
              });

              listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                  @Override
                  public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                      ConvertTodropdown.setText(adapter.getItem(i));
                      toDialog.dismiss();
                      convertToValue = adapter.getItem(i);
                  }
              });
            }
        });

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              try{
                  Double amountToConvert = Double.valueOf(MainActivity.this.amountToConvert.getText().toString());
                  getConversionRate(convertFromValue,convertToValue,amountToConvert);
              }
              catch (Exception e){

              }
            }
        });

    }

    public String getConversionRate(String convertFrom,String convertTo,Double amount){
        RequestQueue queue = Volley.newRequestQueue(this);
//         access_key
        String url = " https://free.currconv.com/api/v7/convert?q="+convertFrom+"_"+convertTo+"&compact=ultra&apiKey=480a0a9b58c7892187f3";
//        String url = " https://api.exchangeratesapi.io/v1/latest?q="+convertFrom+" "+convertTo+"&compact=ultra&apiKey=ab70d6644b80a100d87b99387365b544";
        // Log the URL
        Log.d("RequestURL", "Request URL: " + url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    Double conversionRate = round(((Double) jsonObject.get(convertFrom + "_" + convertTo)), 2);
                    conversionValue = "" + round(conversionRate*amount, 2);
                    showConvRate.setText(conversionValue);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);
        return  null;
    }
    public static  double round(double value, int places){
        if(places<0) throw new IllegalArgumentException();
        BigDecimal bd = BigDecimal.valueOf(value);
        bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();

    }
}