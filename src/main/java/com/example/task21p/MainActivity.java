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

        // 绑定 UI 控件，ID 与 XML 中一致
        sourceUnit = findViewById(R.id.sourceUnit);
        destinationUnit = findViewById(R.id.destinationUnit);
        inputValue = findViewById(R.id.inputValue);
        convertButton = findViewById(R.id.convertButton);
        resultText = findViewById(R.id.resultText);

        // 单位列表
        String[] units = {
                "Inch", "Foot", "Yard", "Mile", "Kilometer",
                "Pound", "Ounce", "Ton", "Kilogram",
                "Celsius", "Fahrenheit", "Kelvin"
        };

        // 设置 Spinner 的适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceUnit.setAdapter(adapter);
        destinationUnit.setAdapter(adapter);

        // 设置按钮点击事件
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertUnits();
            }
        });
    }

    private void convertUnits() {
        String fromUnit = sourceUnit.getSelectedItem().toString();
        String toUnit = destinationUnit.getSelectedItem().toString();
        String inputText = inputValue.getText().toString();

        if (inputText.isEmpty()) {
            resultText.setText("Please enter a value.");
            return;
        }

        double value = Double.parseDouble(inputText);
        double result = 0;

        if (fromUnit.equals(toUnit)) {
            resultText.setText("Same unit selected. Result: " + value);
            return;
        }

        // 部分转换逻辑示例（建议后续封装函数处理）
        if (fromUnit.equals("Inch") && toUnit.equals("Foot")) result = value / 12;
        else if (fromUnit.equals("Foot") && toUnit.equals("Inch")) result = value * 12;
        else if (fromUnit.equals("Yard") && toUnit.equals("Foot")) result = value * 3;
        else if (fromUnit.equals("Mile") && toUnit.equals("Kilometer")) result = value * 1.60934;
        else if (fromUnit.equals("Pound") && toUnit.equals("Kilogram")) result = value * 0.453592;
        else if (fromUnit.equals("Celsius") && toUnit.equals("Fahrenheit")) result = (value * 1.8) + 32;
        else if (fromUnit.equals("Fahrenheit") && toUnit.equals("Celsius")) result = (value - 32) / 1.8;
        else if (fromUnit.equals("Celsius") && toUnit.equals("Kelvin")) result = value + 273.15;
        else if (fromUnit.equals("Kelvin") && toUnit.equals("Celsius")) result = value - 273.15;
        else {
            resultText.setText("Conversion not implemented.");
            return;
        }

        resultText.setText("Result: " + result);
    }
}
