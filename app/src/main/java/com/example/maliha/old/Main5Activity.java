package com.example.maliha.old;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class Main5Activity extends Activity {
    private LinearLayout llContainer;
    private EditText etSearch;
    private ListView lvProducts;
    DatabaseHelper helper;
    private ArrayList<String> mProductArrayList = new ArrayList<>();
    private MyAdapter adapter1;
    private static final int PERMISSION_REQUEST_CODE = 1;
    String str;
    int f=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        initialize();

        helper=new DatabaseHelper(this);

        // Add Text Change Listener to EditText
        etSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Call back the Adapter with current character to Filter
                adapter1.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void initialize() {
        etSearch = (EditText) findViewById(R.id.etSearch);
        lvProducts = (ListView)findViewById(R.id.lvOS);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (f==0){
        Cursor data = helper.getData();
        while (data.moveToNext()) {
            //get the value from the database in column 1
            //then add it to the ArrayList
            mProductArrayList.add(data.getString(1));
        }
        Collections.sort(mProductArrayList, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        });


        adapter1 = new MyAdapter(this, mProductArrayList);
        lvProducts.setAdapter(adapter1);
        }
    }


    // Adapter Class
    public class MyAdapter extends BaseAdapter implements Filterable {

        private ArrayList<String> mOriginalValues; // Original Values
        private ArrayList<String> mDisplayedValues;    // Values to be displayed
        LayoutInflater inflater;

        public MyAdapter(Context context, ArrayList<String> mProductArrayList) {
            this.mOriginalValues = mProductArrayList;
            this.mDisplayedValues = mProductArrayList;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mDisplayedValues.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        private class ViewHolder {
            LinearLayout llContainer;
            TextView tvName,tvPrice;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;

            if (convertView == null) {

                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.row, null);
                holder.llContainer = (LinearLayout)convertView.findViewById(R.id.llContainer);
                holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
                holder.tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvName.setText(mDisplayedValues.get(position).toString());
            Cursor d = helper.getPhone(mDisplayedValues.get(position).toString()); //get the id associated with that name
            d.moveToFirst();
            String p = d.getString(d.getColumnIndex("phone"));
            holder.tvPrice.setText(p+"");

            holder.llContainer.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {

                    Toast.makeText(Main5Activity.this, mDisplayedValues.get(position).toString(), Toast.LENGTH_SHORT).show();
                    String name = mDisplayedValues.get(position).toString();

                    Cursor data = helper.getItemID(name); //get the id associated with that name
                        Cursor d = helper.getPhone(name); //get the id associated with that name
                        d.moveToFirst();
                        str = d.getString(d.getColumnIndex("phone"));

                            makeCall(str);

                }
            });

            return convertView;
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {

                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence constraint,FilterResults results) {

                    mDisplayedValues = (ArrayList<String>) results.values; // has the filtered values
                    notifyDataSetChanged();  // notifies the data with new filtered values
                }

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                    ArrayList<String> FilteredArrList = new ArrayList<>();

                    if (mOriginalValues == null) {
                        mOriginalValues = new ArrayList<>(mDisplayedValues); // saves the original data in mOriginalValues
                    }

                    /********
                     *
                     *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                     *  else does the Filtering and returns FilteredArrList(Filtered)
                     *
                     ********/
                    if (constraint == null || constraint.length() == 0) {

                        // set the Original result to return
                        results.count = mOriginalValues.size();
                        results.values = mOriginalValues;
                    } else {
                        constraint = constraint.toString().toLowerCase();

                        for (int i = 0; i < mProductArrayList.size(); i++) {
                            String data = mOriginalValues.get(i).toString();
                            if (data.toLowerCase().startsWith(constraint.toString())) {

                               // FilteredArrList.add(new Product(mOriginalValues.get(i).name,mOriginalValues.get(i).price));
                                Cursor d=helper.get2Data(data);
                                while(d.moveToNext()) {
                                    FilteredArrList.add(d.getString(1));
                                }
                                //toastMessage("1");
                            }


                        }
                        // set the Filtered result to return
                        results.count = FilteredArrList.size();
                        results.values = FilteredArrList;
                    }
                    return results;
                }
            };
            return filter;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    makeCall(str);
                }
                break;
        }


    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void makeCall(String s) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + s));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            requestForCallPermission();

        } else {
            f++;
            startActivity(intent);

        }
    }

    public void requestForCallPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
        } else {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_REQUEST_CODE);

        }
    }
}