package proverbiododia.ddmsoftware.br.proverbiododia_v5;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    //ImageView imageView;
    Button btnShare;
    Button btnNewProverb;
    ProgressDialog progressDialog;
    String language;
    //LoadPortugueseImages lpi;
    //LoadEnglishImages lei;
    public String full_filename;
    File file;
    Integer iLastImageLoaded = 0;
    boolean b_filtered = false;
    boolean bFirstRun = true;

    ImageView imageView;
    List<Quote> lstQuote;
    List<Quote> lstQuotesPerAuthor;

    int iCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Quote quotes = new Quote();

        lstQuotesPerAuthor = new ArrayList<>();
        lstQuote = quotes.initializeData();

        initializeActivity();
        loadNewQuote();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public Bitmap drawTextOnBitmap(Context context, int resId, String text) {

        // prepare canvas c
        Resources resources = context.getResources();
        float scale = resources.getDisplayMetrics().density;
        Bitmap bitmap = BitmapFactory.decodeResource(resources, resId);

        android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();
        // set default bitmap config if none
        if (bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
        }
        // resource bitmaps are immutable, so we need to convert it to mutable one
        bitmap = bitmap.copy(bitmapConfig, true);
        Canvas canvas = new Canvas(bitmap);

        // new antialiased Paint
        TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        // text color - #3D3D3D
        paint.setColor(Color.rgb(61, 61, 61));
        // text size in pixels
        // Original Code --
        //paint.setTextSize((int) (bitmap.getHeight() / 10 * scale));
        paint.setTextSize((int) (bitmap.getHeight() / 50 * scale));
        // text shadow
        paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);

        // set text width to canvas width minus 16dp padding
        int textWidth = canvas.getWidth() - (int) (24 * scale);

        // init StaticLayout for text
        StaticLayout textLayout = new StaticLayout(text, paint, textWidth,
                Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);

        // get height of multiline text
        int textHeight = textLayout.getHeight();

        // get position of text's top left corner
        float x = (bitmap.getWidth() - textWidth) / 2;
        // Original Y position
        //float y = (bitmap.getHeight() - textHeight) / 2;
        float y = (bitmap.getHeight() - textHeight) / 5;

        // draw text to the Canvas center
        canvas.save();
        canvas.translate(x, y);
        textLayout.draw(canvas);
        canvas.restore();

        return bitmap;
    }

    public Bitmap drawTextOnBitmap(Context context, int resId, int pColors[], String text) {

        // prepare canvas
        Resources resources = context.getResources();
        float scale = resources.getDisplayMetrics().density;
        Bitmap bitmap = BitmapFactory.decodeResource(resources, resId);

        android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();
        // set default bitmap config if none
        if (bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
        }
        // resource bitmaps are immutable, so we need to convert it to mutable one
        bitmap = bitmap.copy(bitmapConfig, true);
        Canvas canvas = new Canvas(bitmap);

        // new antialiased Paint
        TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);

        // text color - #3D3D3D
        // Original Color
        //paint.setColor(Color.rgb(61, 61, 61));
        paint.setColor(Color.rgb(pColors[0], pColors[1], pColors[2]));
        //paint.setColor(pColors[0]);
        // text size in pixels
        // Original Code --
        //paint.setTextSize((int) (bitmap.getHeight() / 10 * scale));
        //paint.setTextSize((int) (bitmap.getHeight() / 50 * scale));

        int iTextSize = (int) (bitmap.getHeight() / 100 * scale);

        if (iTextSize < 80)
            paint.setTextSize(80);
        else
            paint.setTextSize(iTextSize);

        //Toast.makeText(getBaseContext(), "OLHA O TAMANHO DO MEU TEXTO: " + iTextSize, Toast.LENGTH_SHORT).show();
        // text shadow
        //paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);

        // set text width to canvas width minus 16dp padding
        int textWidth = canvas.getWidth() - (int) (16 * scale);

        // init StaticLayout for text
        /*StaticLayout textLayout = new StaticLayout(text, paint, textWidth,
                Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
        StaticLayout textLayout = new StaticLayout(text, paint, textWidth,
                Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
        */

        StaticLayout textLayout = new StaticLayout(text, paint, textWidth,
                Layout.Alignment.ALIGN_OPPOSITE, 1.0f, 0.0f, false);

        // get height of multiline text
        int textHeight = textLayout.getHeight();

        // get position of text's top left corner
        float x = (bitmap.getWidth() - textWidth) / 2;
        // Original Y position
        //float y = (bitmap.getHeight() - textHeight) / 2;
        float y = (bitmap.getHeight() - textHeight) / 5;

        // draw text to the Canvas center
        canvas.save();
        canvas.translate(x, y);
        textLayout.draw(canvas);
        canvas.restore();

        return bitmap;
    }

    /*Function that share image contained in a ImageView on Social Media Network*/
    private void shareImage(int iRepeat){

        Bitmap bitmap;

        OutputStream output;

        if (bFirstRun) {
            verifyStoragePermissions(MainActivity.this);
            bFirstRun = false;
        }


        // Retrieve the image selected in ImageView component
        imageView.buildDrawingCache(true);

        bitmap = imageView.getDrawingCache();

        imageView.buildDrawingCache(false);

        // Find the SD Card path
        File filepath = Environment.getExternalStorageDirectory();
        //File filepath = getExternalCacheDir(); //Environment.geteDownloadCacheDirectory(); //ExternalStorageDirectory();

        // Create a new folder in SD Card
        File dir = new File(filepath.getAbsolutePath() + "/Pictures/");
        dir.mkdirs();


        // Create a name for the saved image
        full_filename = dir.toString() +"/proverb"+ System.currentTimeMillis() + ".png";
        file = new File(dir, "proverb"+ System.currentTimeMillis() + ".png");

        try {

            // Create a New instance of a Share Intent
            Intent intShare = new Intent(Intent.ACTION_SEND);

            // Type of file to share
            intShare.setType("image/jpeg");

            output = new FileOutputStream(file);

            // Workaround - in the first execution, bitmap is null
            if (iRepeat <= 0) {
                shareImage(1);
            } else {

                // Compress into png format image from 0% - 100%
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
                output.flush();
                output.close();

                // Locate the image to Share
                Uri uri = Uri.fromFile(file);

                // Pass the image into an Intnet
                intShare.putExtra(Intent.EXTRA_STREAM, uri);
                // temp permission for receiving app to read this file
                intShare.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                // Show the social share chooser list
                //startActivityForResult(Intent.createChooser(share, "Roberval")),1);
                //startActivity(Intent.createChooser(share,"Compartilhar"));
                startActivity(Intent.createChooser(intShare, "Compartilhar"));
                startActivityForResult(Intent.createChooser(intShare,"Compartilhar"),1);

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block

            Toast toast = Toast.makeText(getApplicationContext(), "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT);
            toast.show();

            e.printStackTrace();
        }
    }

    // Function called after OnCreateChooser execute
    protected void onActivityResult(int aRequestCode, int aResultCode, Intent aData){
        super.onActivityResult(aRequestCode,aResultCode, aData);

        if (aRequestCode == 1) {

            setContentView(R.layout.activity_main);
            imageView = (ImageView) findViewById(R.id.imageView);

            // Reload Last Image before erase the image sent
            if (iLastImageLoaded >= 0 ) {

                BitmapDrawable d = new BitmapDrawable(getResources(), file.getAbsolutePath());
                //jpgView.setImageDrawable(d);

                //imageView.setImageResource(iLastImageLoaded);
                imageView.setImageDrawable(d);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            }

            // Delete Image sent from the SDCard (Clear buffer)
            if (file.exists() == true)
                file.delete();

            initializeActivity();
        }
    }

    // Thread to Create a Loading ProgressDialog
    public void shareImageWithProgress(View v) {

        progressDialog = ProgressDialog.show(MainActivity.this, "Carregadno...", "Carregando", true);

        new Thread(new Runnable() {
            @Override
            public void run() {

                // Function Share Image
                shareImage(0);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                });

            }
        }).start();
    }


    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }


    public void initializeActivity(){


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                shareImage(0);
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        Button button = (Button) findViewById(R.id.btnQuote);

        imageView = (ImageView) findViewById(R.id.imageView);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadNewQuote();

            }
        });


        // Create a AdView
        // Load Advertisement Banner
        AdView mAdView = (AdView) findViewById(R.id.adViewMainActivity);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



    }

    public void loadNewQuote() {

        Random random = new Random();

        int i = random.nextInt(lstQuote.size());
        //System.out.println(lstQuote.get(i).author); //prints element i
        //Toast.makeText(getBaseContext(),lstQuote.get(i).author,Toast.LENGTH_SHORT).show();

        if (lstQuote.get(i).author.equals("NOME_DO_AUTOR_A_SER_FILTRADO")) {

            for(int x = 0; x < lstQuote.size(); x++) {

                if (lstQuote.get(x).author.equals("NOME_DO_AUTOR_A_SER_FILTRADO"))
                    lstQuotesPerAuthor.add(lstQuote.get(x));

                //System.out.println(lstQuote.get(x)); //prints element i
                b_filtered = true;

            }
        }

        if (b_filtered) {
            i = random.nextInt(lstQuotesPerAuthor.size());
            imageView.setImageBitmap(drawTextOnBitmap(getApplication().getBaseContext(), lstQuotesPerAuthor.get(i).authorBackground, lstQuotesPerAuthor.get(i).quoteFontColor, lstQuotesPerAuthor.get(i).quote+ "\n\n" + lstQuotesPerAuthor.get(i).author));
            //linearLayout.setBackground( new BitmapDrawable(drawTextOnBitmap(getApplication().getBaseContext(), lstQuotesPerAuthor.get(i).authorBackground, lstQuotesPerAuthor.get(i).quoteFontColor, lstQuotesPerAuthor.get(i).quote+ "\n\n" + lstQuotesPerAuthor.get(i).author)));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        else {
            //linearLayout.setBackground(new BitmapDrawable(drawTextOnBitmap(getApplication().getBaseContext(), lstQuote.get(i).authorBackground, lstQuote.get(i).quoteFontColor, lstQuote.get(i).quote + "\n\n" + lstQuote.get(i).author)));
            imageView.setImageBitmap(drawTextOnBitmap(getApplication().getBaseContext(), lstQuote.get(i).authorBackground, lstQuote.get(i).quoteFontColor, lstQuote.get(i).quote  + "\n\n" + lstQuote.get(i).author));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }

    }

}
