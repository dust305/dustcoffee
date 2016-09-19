package com.example.admin.myapp;




        import android.content.Intent;
        import android.net.Uri;
        import android.os.Bundle;
        import android.support.v7.app.ActionBarActivity;
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

    int numberOfCoffees = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (numberOfCoffees == 100){
            //Show an error message as a toast
            Toast.makeText(this,"You cant have more than 100 coffees", Toast.LENGTH_SHORT).show();
            //exit this method early because theres nothing more to do
            return;
        }
        numberOfCoffees = numberOfCoffees + 1;
        displayQuantity(numberOfCoffees);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (numberOfCoffees == 1){
            //Show an error message as a toast
            Toast.makeText(this,"You cant have less than 1 coffee", Toast.LENGTH_SHORT).show();
            //exit this method early because theres nothing more to do
            return;
        }
        numberOfCoffees = numberOfCoffees - 1;
        displayQuantity(numberOfCoffees);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        //code for text inputed
        EditText nameField = (EditText) findViewById(R.id.name_field) ;
        String name = nameField.getText().toString();
        Log.v("MainActivity", "Name: " + name);

        //figure if user wants whipped cream
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whippedCreamcheckBox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        //Log.v("MainActivity", "Has whipped cream: " + hasWhippedCream);

        //figure if user wants chocolate
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolatecheckBox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        //Log.v("MainActivity", "Has whipped cream: " + hasChocolate);

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto")); // only email app should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }

        displayMessage(priceMessage);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.Quantity_Text_View);
        quantityTextView.setText("" + number);
    }


    //return order summary
    private  String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate){
        String priceMessage = "Name: " + name;
        priceMessage = priceMessage + "\nAdd whipped cream?" + addWhippedCream;
        priceMessage = priceMessage + "\nAdd whipped cream?" + addChocolate;
        priceMessage = priceMessage + "\nQuantity: " + numberOfCoffees;
        priceMessage = priceMessage + "\nTotal: R" + price;
        priceMessage = priceMessage + "\nThank you!";
        return (priceMessage);
    }

    //return total price

    private int calculatePrice(boolean addWhippedCream, boolean addChocolate){
        //normal price for one cup
        int basePrice = 5;

        //add R1 if user wants whipped cream
        if (addWhippedCream) {
            basePrice = basePrice + 1;
        }

        //add R2 if user wants chocolate
        if (addChocolate) {
            basePrice = basePrice + 2;
        }

        return numberOfCoffees * basePrice;

    }

    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summarytextView);
        orderSummaryTextView.setText(message);
    }
}