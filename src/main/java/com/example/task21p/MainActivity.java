package com.example.task21p;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Spinner sourceUnit, destinationUnit;
    private EditText inputValue;
    private TextView resultText;
    private Button convertButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 绑定控件
        sourceUnit = findViewById(R.id.sourceUnit);
        destinationUnit = findViewById(R.id.destinationUnit);
        inputValue = findViewById(R.id.inputValue);
        convertButton = findViewById(R.id.convertButton);
        resultText = findViewById(R.id.resultText);

        // 支持的单位列表
        String[] units = {
                "Inch", "Foot", "Yard", "Mile", "Centimeter", "Kilometer",
                "Pound", "Ounce", "Ton", "Kilogram", "Gram",
                "Celsius", "Fahrenheit", "Kelvin"
        };

        // 适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceUnit.setAdapter(adapter);
        destinationUnit.setAdapter(adapter);

        // 点击转换按钮
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertUnits();
            }
        });
    }

    // 主转换逻辑
    private void convertUnits() {
        String fromUnit = sourceUnit.getSelectedItem().toString();
        String toUnit = destinationUnit.getSelectedItem().toString();
        String inputText = inputValue.getText().toString();

        if (inputText.isEmpty()) {
            resultText.setText("Please enter a value.");
            return;
        }

        double value;
        try {
            value = Double.parseDouble(inputText);
        } catch (NumberFormatException e) {
            resultText.setText("Invalid number format.");
            return;
        }

        if (fromUnit.equals(toUnit)) {
            resultText.setText("Same unit selected. Result: " + value);
            return;
        }

        double result = 0;

        if (isLengthUnit(fromUnit) && isLengthUnit(toUnit)) {
            result = convertLength(value, fromUnit, toUnit);
        } else if (isWeightUnit(fromUnit) && isWeightUnit(toUnit)) {
            result = convertWeight(value, fromUnit, toUnit);
        } else if (isTempUnit(fromUnit) && isTempUnit(toUnit)) {
            result = convertTemperature(value, fromUnit, toUnit);
        } else {
            resultText.setText("Incompatible units.");
            return;
        }

        resultText.setText("Result: " + result);
    }

    // 类型判断
    private boolean isLengthUnit(String unit) {
        return unit.equals("Inch") || unit.equals("Foot") || unit.equals("Yard") ||
                unit.equals("Mile") || unit.equals("Centimeter") || unit.equals("Kilometer");
    }

    private boolean isWeightUnit(String unit) {
        return unit.equals("Pound") || unit.equals("Ounce") || unit.equals("Ton") ||
                unit.equals("Kilogram") || unit.equals("Gram");
    }

    private boolean isTempUnit(String unit) {
        return unit.equals("Celsius") || unit.equals("Fahrenheit") || unit.equals("Kelvin");
    }

    // 长度转换（中间单位：厘米）
    private double convertLength(double value, String from, String to) {
        if (from.equals("Inch")) value *= 2.54;
        else if (from.equals("Foot")) value *= 30.48;
        else if (from.equals("Yard")) value *= 91.44;
        else if (from.equals("Mile")) value *= 160934;
        else if (from.equals("Kilometer")) value *= 100000;
        else if (from.equals("Centimeter")) value *= 1;

        if (to.equals("Inch")) value /= 2.54;
        else if (to.equals("Foot")) value /= 30.48;
        else if (to.equals("Yard")) value /= 91.44;
        else if (to.equals("Mile")) value /= 160934;
        else if (to.equals("Kilometer")) value /= 100000;
        else if (to.equals("Centimeter")) value /= 1;

        return value;
    }

    // 重量转换（中间单位：千克）
    private double convertWeight(double value, String from, String to) {
        if (from.equals("Pound")) value *= 0.453592;
        else if (from.equals("Ounce")) value *= 0.0283495;
        else if (from.equals("Ton")) value *= 907.185;
        else if (from.equals("Gram")) value *= 0.001;
        else if (from.equals("Kilogram")) value *= 1;

        if (to.equals("Pound")) value /= 0.453592;
        else if (to.equals("Ounce")) value /= 0.0283495;
        else if (to.equals("Ton")) value /= 907.185;
        else if (to.equals("Gram")) value /= 0.001;
        else if (to.equals("Kilogram")) value /= 1;

        return value;
    }

    // 温度转换
    private double convertTemperature(double value, String from, String to) {
        double tempCelsius;

        if (from.equals("Celsius")) tempCelsius = value;
        else if (from.equals("Fahrenheit")) tempCelsius = (value - 32) / 1.8;
        else if (from.equals("Kelvin")) tempCelsius = value - 273.15;
        else return 0;

        if (to.equals("Celsius")) return tempCelsius;
        else if (to.equals("Fahrenheit")) return (tempCelsius * 1.8) + 32;
        else if (to.equals("Kelvin")) return tempCelsius + 273.15;

        return 0;
    }
}
