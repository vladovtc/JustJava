package com.example.android.justjava;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    @SuppressLint("StringFormatInvalid")
    public void submitOrder(View view) {

        EditText editText = findViewById(R.id.editTextName);
        String name = editText.getText().toString();

        CheckBox checkBoxWhippedCream = findViewById(R.id.checkBoxWhippedCream);
        boolean hasWhippedCream = checkBoxWhippedCream.isChecked();

        CheckBox checkBoxChocolate = findViewById(R.id.checkBoxChocolate);
        boolean hasChocolate = checkBoxChocolate.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.just_java_order_for) + " " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void submitOrderViber(View view) {

        EditText editText = findViewById(R.id.editTextName);
        String name = editText.getText().toString();

        CheckBox checkBoxWhippedCream = findViewById(R.id.checkBoxWhippedCream);
        boolean hasWhippedCream = checkBoxWhippedCream.isChecked();

        CheckBox checkBoxChocolate = findViewById(R.id.checkBoxChocolate);
        boolean hasChocolate = checkBoxChocolate.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, name);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setPackage("com.viber.voip");
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


    }


    private String createOrderSummary(int price, boolean addWhippedCream,
                                      boolean addChocolate, String name) {

        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += "\n" + getString(R.string.order_whipped_cream, addWhippedCream);
        priceMessage += "\n" + getString(R.string.order_chocolade, addChocolate);
        priceMessage += "\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage += "\n" + getString(R.string.order_summary_total,
                NumberFormat.getCurrencyInstance().format(price));
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;

    }

    /**
     * This method displays the given quantity value on the screen.
     *
     * @param broj kolicine narudbe
     */
    private void displayQuantity(int broj) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText(R.string.space + broj);

    }

    /**
     * Calculates the price of the order.
     *
     * @return total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {

        int cupOfCoffe = 5;

        if (addWhippedCream) {
            cupOfCoffe += 1;
        }

        if (addChocolate) {
            cupOfCoffe += 2;
        }

        return cupOfCoffe * quantity;
    }

    public void increment(View view) {

        if (quantity == 100) {
            Toast.makeText(this, "You reach maximum", Toast.LENGTH_SHORT).show();
        }

        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    public void decrement(View view) {

        if (quantity <= 1) {
            Toast.makeText(this, "Minimum 1 cup of coffe", Toast.LENGTH_SHORT).show();
            return;
        }

        quantity = quantity - 1;
        displayQuantity(quantity);


    }


}
