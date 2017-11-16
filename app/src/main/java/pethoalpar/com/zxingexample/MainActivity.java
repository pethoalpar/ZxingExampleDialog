package pethoalpar.com.zxingexample;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private TextView textViewGlobal;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayout = (LinearLayout) this.findViewById(R.id.linearLayout);

        final Activity activity = this;

        for(int i =0; i<5; ++i){
            LinearLayout linearLayout = new LinearLayout(this);

            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));//width height
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            TextView textView = new TextView(this);
            textView.setText(""+i);
            textView.setTextColor(Color.parseColor("#000000"));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            linearLayout.addView(textView);


            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick( View view ) {
                    final Dialog dialog = new Dialog(activity);
                    dialog.setContentView(R.layout.dialog_layout);
                    dialog.setTitle("Update dialog");
                    textViewGlobal = (TextView) dialog.findViewById(R.id.textViewDialog);
                    dialog.findViewById(R.id.buttonDialog).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            IntentIntegrator integrator = new IntentIntegrator(activity);
                            integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                            integrator.setPrompt("Scan the barcode");
                            integrator.setCameraId(0);  // Use a specific camera of the device
                            integrator.setBeepEnabled(false);
                            integrator.initiateScan();
                        }
                    });

                    dialog.findViewById(R.id.buttonExitDialog).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick( View v ) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }
            });

            this.linearLayout.addView(linearLayout);
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                //bealliton a szoveget
                textViewGlobal.setText(result.getContents());
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}
