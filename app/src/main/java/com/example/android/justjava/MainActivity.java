package com.example.android.justjava;



import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100){
            //Show an error message as toast
            Toast.makeText(this, "You cannot have more than 100 coffee",Toast.LENGTH_SHORT).show();
            //Exit the method early if nothing to do.
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);

    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1){
            // Show an error message as toast
            Toast.makeText(this, "You cannot less than 1 coffee", Toast.LENGTH_SHORT)
            .show();
            // Exit method early if there is nothing to do
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);

    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        // Figure out if the user wants whipped cream
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_box);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        // Figure out if the user wants chocolate topping
        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckbox.isChecked();
        // Get text From the edit text Field
        EditText yourName = (EditText) findViewById(R.id.Customers_name);
        String name = yourName.getText().toString();

        int price = calculatePrice(hasChocolate, hasWhippedCream);
        String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, "profread025@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just JAVA order for" + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        displayMessage(priceMessage);
    }
    /**
     * calculate price of the order
     * @ return total price
     */
    private int calculatePrice(boolean addChocolate, boolean addWhippedCream){
        int basePrice = 5;
        // Add $2 for Toppings on a cup
        if (addChocolate)
        {basePrice = basePrice + 2;
        }
        // Add $1 for toppings on a cup
        if (addWhippedCream) {basePrice = basePrice + 1;
        }
        return quantity * basePrice;

    }
    /**
     * This method create order summary
     * @param hasWhippedCream indicate whether the customer want whipped cream or not
     * @param hasChocolate indicate whether the customer want whipped cream or not
     * @ priceMessage shows the summary
     * @ param price of the order
     * @ return text summary
     * @param name of the customer
     * */

    @SuppressLint("StringFormatInvalid")
    private String createOrderSummary(String name, int price, boolean hasWhippedCream, boolean hasChocolate) {
        String PriceMessage = getString(R.string.order_summary_name,name);
        PriceMessage += "\n" + getString(R.string.Add_WhippedCream, hasWhippedCream);
        PriceMessage += "\n"+ getString(R.string.add_chocolate, hasChocolate);
        PriceMessage += "\n" + getString(R.string.quantity_main, quantity);
        PriceMessage += "\n"+ getString(R.string.total,
                NumberFormat.getCurrencyInstance().format(price));
        PriceMessage +=  "\n" + getString(R.string.thank_you);
        return PriceMessage;
    }



    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);

    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView= (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}
