package tr.edu.mu.ceng.gui.primecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A time consuming prime calculator to demonstrate background thread usage.
 * Searches the greatest prime number less than the given number.
 */
public class MainActivity extends AppCompatActivity {


    Button btnSearch;
    EditText txtNum;
    TextView txtPrime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNum = findViewById(R.id.txtNum);
        btnSearch = findViewById(R.id.btnSearch);
        txtPrime = findViewById(R.id.txtPrime);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = txtNum.getText().toString();
               // PrimeFinder pf  =new PrimeFinder(Integer.parseInt(num));
               // pf.start();

                AsyncPrimeNumber asyncPrimeNumber = new AsyncPrimeNumber();
                asyncPrimeNumber.execute(Integer.parseInt(num));
            }
        });
    }


    class AsyncPrimeNumber extends AsyncTask<Integer,Void, Integer>{

        @Override
        protected Integer doInBackground(Integer... integers) {
            return findPrimeLessThan(integers[0]);
        }


        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            txtPrime.setText(integer.toString());
        }

        private Integer findPrimeLessThan(int num) {

            for (int i = num; i> 0; i--){
                if (isPrime(i)){

                    return i;
                }
            }
            return null;
        }

        private boolean isPrime(int num) {
            for (int i =num-1; i> 1; i--){
                if(num % i == 0){
                    return false;
                }
            }
            return true;
        }
    }




    class PrimeFinder extends Thread{

        int limit;

        public PrimeFinder(int number){
            limit = number;
        }

        public void run(){
            findPrimeLessThan(limit);
        }

        private void findPrimeLessThan(int num) {

            for (int i = num; i> 0; i--){
                if (isPrime(i)){
                    final int result = i;
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            txtPrime.setText(result + "");
                        }
                    });

                    return;
                }
            }
        }

        private boolean isPrime(int num) {
            for (int i =num-1; i> 1; i--){
                if(num % i == 0){
                    return false;
                }
            }
            return true;
        }
    }

}
