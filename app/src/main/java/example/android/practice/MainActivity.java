package example.android.practice;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    private int calculatePrice(boolean addChocoChips, boolean addCream) {
        int toppingPrice = 5;

        if (addChocoChips) {
            toppingPrice += 2;
        }
        if (addCream) {
            toppingPrice += 1;
        }

        return quantity * toppingPrice;
    }

    @SuppressLint("ShowToast")
    public void decrement(View view) {
        quantity -= 1;
        if(quantity < 0){
            quantity = 0;
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
        }
        displayQuantity(quantity);
    }

    @SuppressLint("ShowToast")
    public void increment(View view) {
        quantity += 1;
        if(quantity > 100){
            quantity = 100;
            Toast.makeText(this, "You can not have more than 100 Ice Cream at a time.", Toast.LENGTH_SHORT).show();
        }
        displayQuantity(quantity);
    }

    @SuppressLint("SetTextI18n")
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = findViewById(R.id.quantity_textview);
        quantityTextView.setText("" + numberOfCoffees);
    }

    public void submitOrder(View view) {
        EditText customerName = findViewById(R.id.customer_name);
        String name = customerName.getText().toString();

        CheckBox chocoChipsCheckbox = findViewById(R.id.choco_chips_checkbox);
        boolean hasChocoChips = chocoChipsCheckbox.isChecked();

        CheckBox creamCheckbox = findViewById(R.id.cream_checkbox);
        boolean hasCream = creamCheckbox.isChecked();

        int price = calculatePrice(hasChocoChips, hasCream);
        String priceMessage = createOrderSummary(name, price, hasChocoChips, hasCream);

        Intent mail = new Intent(Intent.ACTION_SENDTO);
        mail.setData(Uri.parse("mailto:"));
        mail.putExtra(Intent.EXTRA_SUBJECT, "Ice-Cream Order for "+name);
        mail.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if(mail.resolveActivity(getPackageManager())!=null){
            startActivity(mail);
        }
        displayMessage(priceMessage);
    }

    private void displayMessage(String message) {
        TextView orderSummaryTextView = findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    private String createOrderSummary(String name, int price, boolean addChocoChips, boolean addCream) {
        String priceMessage = "Welcome " + name + " !";
        priceMessage += "\nQuantity : " + quantity;
        priceMessage += "\nAdd Choco Chips : " + addChocoChips;
        priceMessage += "\nAdd Cream : " + addCream;
        priceMessage += "\nTotal price : " + price + " INR";
        priceMessage += "\nThank You..!!!";

        return priceMessage;
    }

}
