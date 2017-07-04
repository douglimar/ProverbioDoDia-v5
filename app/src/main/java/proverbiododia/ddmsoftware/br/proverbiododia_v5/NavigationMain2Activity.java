package proverbiododia.ddmsoftware.br.proverbiododia_v5;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
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
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import java.util.Locale;
import java.util.Random;

public class NavigationMain2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


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

    ImageView imageViewNav;
    List<Quote> lstQuote;
    List<Quote> lstQuotesPerAuthor;
    String strQuotesPerAuthor;

    int iCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Quote quotes = new Quote();

        lstQuotesPerAuthor = new ArrayList<>();

        String language = Locale.getDefault().getLanguage();
        strQuotesPerAuthor = "";

        switch (language) {
            case "pt":
                lstQuote = quotes.initializePortugueseData();
                break;
            case "es":
                lstQuote = quotes.initializeSpanishData();
                break;
            default:
                lstQuote = quotes.initializeEnglishData();
                break;

        }


        initializeActivity();
        loadNewQuote();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.loading, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                shareImage(0);
            }
        });

        /*

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this); */
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_main2, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        switch (id) {

            case R.id.nav_all: {
                strQuotesPerAuthor = "";
                b_filtered = false;
                this.setTitle(getText(R.string.title_activity_navigation_main2));
                loadNewQuote();

                break;
            }

            case R.id.nav_adamsmith: {

                strQuotesPerAuthor = (String) getText(R.string.Smith);
                this.setTitle(strQuotesPerAuthor);
                loadNewQuote();

                break;
            }
            case R.id.nav_aristoteles: {

                strQuotesPerAuthor = (String) getText(R.string.Aristeles);
                this.setTitle(strQuotesPerAuthor);
                loadNewQuote();

                break;
            }
            case R.id.nav_aurelio: {

                strQuotesPerAuthor = (String) getText(R.string.Aurelio);
                this.setTitle(strQuotesPerAuthor);
                loadNewQuote();

                break;
            }
            case R.id.nav_Buda: {

                strQuotesPerAuthor = (String) getText(R.string.Buda);
                this.setTitle(strQuotesPerAuthor);
                loadNewQuote();

                break;
            }
            case R.id.nav_cervantes: {

                strQuotesPerAuthor = (String) getText(R.string.Cervantes);
                this.setTitle(strQuotesPerAuthor);
                loadNewQuote();

                break;
            }
            case R.id.nav_chaplin: {

                strQuotesPerAuthor = (String) getText(R.string.Caplin);
                this.setTitle(strQuotesPerAuthor);
                loadNewQuote();

                break;
            }
            case R.id.nav_churchill: {

                strQuotesPerAuthor = (String) getText(R.string.Churchill);
                this.setTitle(strQuotesPerAuthor);
                loadNewQuote();

                break;
            }
            case R.id.nav_coelho: {

                strQuotesPerAuthor = (String) getText(R.string.Coelho);
                this.setTitle(strQuotesPerAuthor);
                loadNewQuote();

                break;
            }
            case R.id.nav_confucio: {

                strQuotesPerAuthor = (String) getText(R.string.Confúcio);
                this.setTitle(strQuotesPerAuthor);
                loadNewQuote();

                break;

            }
            case R.id.nav_Dali: {

                strQuotesPerAuthor = (String) getText(R.string.Dali);
                this.setTitle(strQuotesPerAuthor);
                loadNewQuote();

                break;

            }
            case R.id.nav_dickens: {

                strQuotesPerAuthor = (String) getText(R.string.Dickens);
                this.setTitle(strQuotesPerAuthor);
                loadNewQuote();

                break;

            }
            case R.id.nav_einstein: {

                strQuotesPerAuthor = (String) getText(R.string.Eistein);
                this.setTitle(strQuotesPerAuthor);
                loadNewQuote();

                break;

            }
            case R.id.nav_exupery: {

                strQuotesPerAuthor = (String) getText(R.string.Exupery);
                this.setTitle(strQuotesPerAuthor);
                loadNewQuote();

                break;
            }
            case R.id.nav_Ford: {

                strQuotesPerAuthor = (String) getText(R.string.Ford);
                this.setTitle(strQuotesPerAuthor);
                loadNewQuote();

                break;
            }
            case R.id.nav_freud: {

                strQuotesPerAuthor = (String) getText(R.string.Freud);
                this.setTitle(strQuotesPerAuthor);
                loadNewQuote();

                break;
            }
            case R.id.nav_gandhi: {

                strQuotesPerAuthor = (String) getText(R.string.Gandhi);
                this.setTitle(strQuotesPerAuthor);
                loadNewQuote();

                break;

            }
            case R.id.nav_Jobs: {

                strQuotesPerAuthor = (String) getText(R.string.Jobs);
                this.setTitle(strQuotesPerAuthor);
                loadNewQuote();

                break;

            }
            case R.id.nav_kardec: {

                strQuotesPerAuthor = (String) getText(R.string.Kardec);
                this.setTitle(strQuotesPerAuthor);
                loadNewQuote();

                break;

            }
            case R.id.nav_Lama: {

                strQuotesPerAuthor = (String) getText(R.string.Lama);
                this.setTitle(strQuotesPerAuthor);
                loadNewQuote();

                break;

            }
            case R.id.nav_lispector: {

                strQuotesPerAuthor = (String) getText(R.string.Lispector);
                this.setTitle(strQuotesPerAuthor);
                loadNewQuote();

                break;

            }
            case R.id.nav_LutherKing: {

                strQuotesPerAuthor = (String) getText(R.string.LutherKing);
                this.setTitle(strQuotesPerAuthor);
                loadNewQuote();

                break;
            }
            case R.id.nav_malcolmX: {

                strQuotesPerAuthor = (String) getText(R.string.MalcolmX);
                this.setTitle(strQuotesPerAuthor);
                loadNewQuote();

                break;

            }
            case R.id.nav_mandela: {

                strQuotesPerAuthor = (String) getText(R.string.Mandela);
                this.setTitle(strQuotesPerAuthor);
                loadNewQuote();

                break;

            }
            case R.id.nav_marley: {
                strQuotesPerAuthor = (String) getText(R.string.Marley);
                this.setTitle(strQuotesPerAuthor);
                loadNewQuote();

                break;

            }
            case R.id.nav_Marx: {
                strQuotesPerAuthor = (String) getText(R.string.Marx);
                this.setTitle(strQuotesPerAuthor);
                loadNewQuote();

                break;
            }

            case R.id.nav_maslow: {

                strQuotesPerAuthor = (String) getText(R.string.Maslow);
                this.setTitle(strQuotesPerAuthor);
                loadNewQuote();

                break;
            }

            case R.id.nav_monroe: {
                strQuotesPerAuthor = (String) getText(R.string.Monroe);
                this.setTitle(strQuotesPerAuthor);
                loadNewQuote();

                break;
            }
            case R.id.nav_neruda: {

                strQuotesPerAuthor = (String) getText(R.string.Neruda);
                this.setTitle(strQuotesPerAuthor);
                loadNewQuote();

                break;
            }

            case R.id.nav_nietzsche: {
                strQuotesPerAuthor = (String) getText(R.string.Nietzsche);
                this.setTitle(strQuotesPerAuthor);
                loadNewQuote();

                break;
            }
            case R.id.nav_pessoa: {
                strQuotesPerAuthor = (String) getText(R.string.Pessoa);
                this.setTitle(strQuotesPerAuthor);
                loadNewQuote();

                break;
            }
            case R.id.nav_pitagoras: {
                strQuotesPerAuthor = (String) getText(R.string.Pitagoras);
                this.setTitle(strQuotesPerAuthor);
                loadNewQuote();

                break;
            }
            case R.id.nav_platao: {
                strQuotesPerAuthor = (String) getText(R.string.Platao);
                this.setTitle(strQuotesPerAuthor);
                loadNewQuote();

                break;
            }
            case R.id.nav_schopenhauer: {
                strQuotesPerAuthor = (String) getText(R.string.hauer);
                this.setTitle(strQuotesPerAuthor);
                loadNewQuote();

                break;
            }
            case R.id.nav_shakespeare: {
                strQuotesPerAuthor = (String) getText(R.string.Shakespeare);
                this.setTitle(strQuotesPerAuthor);
                loadNewQuote();

                break;
            }
            case R.id.nav_StephenKing: {
                strQuotesPerAuthor = (String) getText(R.string.King);
                this.setTitle(strQuotesPerAuthor);
                loadNewQuote();

                break;
            }
            case R.id.nav_tatcher: {
                strQuotesPerAuthor = (String) getText(R.string.Tatcher);
                this.setTitle(strQuotesPerAuthor);
                loadNewQuote();

                break;
            }
            case R.id.nav_TseTung: {
                strQuotesPerAuthor = (String) getText(R.string.TzeTung);
                this.setTitle(strQuotesPerAuthor);
                loadNewQuote();

                break;
            }
            case R.id.nav_twain: {
                strQuotesPerAuthor = (String) getText(R.string.Twain);
                this.setTitle(strQuotesPerAuthor);
                loadNewQuote();

                break;
            }
            case R.id.nav_Tzu: {
                strQuotesPerAuthor = (String) getText(R.string.SunTzu);
                this.setTitle(strQuotesPerAuthor);
                loadNewQuote();

                break;
            }
            case R.id.nav_Vinci: {
                strQuotesPerAuthor = (String) getText(R.string.Vinci);
                this.setTitle(strQuotesPerAuthor);
                loadNewQuote();

                break;
            }
            case R.id.nav_voltaire: {
                strQuotesPerAuthor = (String) getText(R.string.Voltaire);
                this.setTitle(strQuotesPerAuthor);
                loadNewQuote();

                break;
            }
            case R.id.nav_Wilde: {
                strQuotesPerAuthor = (String) getText(R.string.Wilde);
                this.setTitle(strQuotesPerAuthor);
                loadNewQuote();

                break;
            }

            case R.id.nav_close: {
                System.exit(0);

                break;
            }
        }
/*

        if (id == R.id.nav_adamsmith) {
            strQuotesPerAuthor = (String) getText(R.string.adam_smith);
            this.setTitle(strQuotesPerAuthor);
            loadNewQuote();
        } else if (id == R.id.nav_aristoteles) {
            strQuotesPerAuthor = (String) getText(R.string.Aristeles);
            this.setTitle(strQuotesPerAuthor);
            loadNewQuote();

        } else if (id == R.id.nav_antes) {

        } else if (id == R.id.nav_tcher) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_all) {
            strQuotesPerAuthor = "";
            b_filtered = false;
            this.setTitle(getText(R.string.title_activity_navigation_main2));
            loadNewQuote();

        } */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
    private void shareImage(int iRepeat) {

        Bitmap bitmap;

        OutputStream output;

        if (bFirstRun) {
            verifyStoragePermissions(NavigationMain2Activity.this);
            bFirstRun = false;
        }

        // Retrieve the image selected in ImageView component
        imageViewNav.buildDrawingCache(true);

        bitmap = imageViewNav.getDrawingCache();

        imageViewNav.buildDrawingCache(false);

        // Find the SD Card path
        File filepath = Environment.getExternalStorageDirectory();
        //File filepath = getExternalCacheDir(); //Environment.geteDownloadCacheDirectory(); //ExternalStorageDirectory();

        // Create a new folder in SD Card
        File dir = new File(filepath.getAbsolutePath() + "/Pictures/");
        dir.mkdirs();


        // Create a name for the saved image
        full_filename = dir.toString() + "/proverb" + System.currentTimeMillis() + ".png";
        file = new File(dir, "proverb" + System.currentTimeMillis() + ".png");

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
                //startActivity(Intent.createChooser(intShare, getString(R.string.compartilhar)));
                startActivityForResult(Intent.createChooser(intShare, "Compartilhar"), 1);

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block

            Toast toast = Toast.makeText(getApplicationContext(), "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT);
            toast.show();

            e.printStackTrace();
        }
    }

    // Function called after OnCreateChooser execute
    protected void onActivityResult(int aRequestCode, int aResultCode, Intent aData) {
        super.onActivityResult(aRequestCode, aResultCode, aData);

        if (aRequestCode == 1) {

            //setContentView(R.layout.content_navigation_main2);

            initializeActivity();

            imageViewNav = (ImageView) findViewById(R.id.imageView1);

            // Reload Last Image before erase the image sent
            if (iLastImageLoaded >= 0) {

                BitmapDrawable d = new BitmapDrawable(getResources(), file.getAbsolutePath());
                //jpgView.setImageDrawable(d);

                //imageView.setImageResource(iLastImageLoaded);
                imageViewNav.setImageDrawable(d);
                imageViewNav.setScaleType(ImageView.ScaleType.FIT_XY);

            }

            // Delete Image sent from the SDCard (Clear buffer)
            if (file.exists() == true)
                file.delete();

            //initializeActivity();
        }
    }

    // Thread to Create a Loading ProgressDialog
    public void shareImageWithProgress(View v) {

        progressDialog = ProgressDialog.show(NavigationMain2Activity.this, "Carregadno...", "Carregando", true);

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
     * <p>
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

    public void initializeActivity() {

        setContentView(R.layout.activity_navigation_main2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        strQuotesPerAuthor = "";

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                shareImage(0);
                Snackbar.make(view, R.string.loading, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        Button buttonNav = (Button) findViewById(R.id.btnQuote1);

        imageViewNav = (ImageView) findViewById(R.id.imageView1);


        buttonNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadNewQuote();

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // Create a AdView
        // Load Advertisement Banner
        AdView mAdView = (AdView) findViewById(R.id.adViewMainActivity1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public void loadNewQuote() {

        Random random = new Random();

        int i = random.nextInt(lstQuote.size());

        int iCount = 0;
        //System.out.println(lstQuote.get(i).author); //prints element i
        //Toast.makeText(getBaseContext(),lstQuote.get(i).author,Toast.LENGTH_SHORT).show();

//        if (lstQuote.get(i).author.equals(strQuotesPerAuthor)) {
        if (strQuotesPerAuthor != "") {

            lstQuotesPerAuthor.clear();

            for (int x = 0; x < lstQuote.size(); x++) {

                if (lstQuote.get(x).author.equals(strQuotesPerAuthor)) {
                    lstQuotesPerAuthor.add(lstQuote.get(x));
                    iCount++;
                }

                //System.out.println(lstQuote.get(x)); //prints element i
                b_filtered = true;

            }
        }

        if ((b_filtered) && (iCount>0)){
            i = random.nextInt(lstQuotesPerAuthor.size());
            imageViewNav.setImageBitmap(drawTextOnBitmap(getApplication().getBaseContext(), lstQuotesPerAuthor.get(i).authorBackground, lstQuotesPerAuthor.get(i).quoteFontColor, lstQuotesPerAuthor.get(i).quote + "\n\n" + lstQuotesPerAuthor.get(i).author));
            //linearLayout.setBackground( new BitmapDrawable(drawTextOnBitmap(getApplication().getBaseContext(), lstQuotesPerAuthor.get(i).authorBackground, lstQuotesPerAuthor.get(i).quoteFontColor, lstQuotesPerAuthor.get(i).quote+ "\n\n" + lstQuotesPerAuthor.get(i).author)));
            imageViewNav.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        else
            if ((strQuotesPerAuthor != "") && (iCount == 0))
                Toast.makeText(getApplicationContext(), "Desculpe... Nao existem provérbios para esse autor.", Toast.LENGTH_LONG).show();
            else {
                //linearLayout.setBackground(new BitmapDrawable(drawTextOnBitmap(getApplication().getBaseContext(), lstQuote.get(i).authorBackground, lstQuote.get(i).quoteFontColor, lstQuote.get(i).quote + "\n\n" + lstQuote.get(i).author)));
                imageViewNav.setImageBitmap(drawTextOnBitmap(getApplication().getBaseContext(), lstQuote.get(i).authorBackground, lstQuote.get(i).quoteFontColor, lstQuote.get(i).quote + "\n\n" + lstQuote.get(i).author));
                imageViewNav.setScaleType(ImageView.ScaleType.FIT_XY);
            }

    }

}
