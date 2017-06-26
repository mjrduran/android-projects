package com.tozny.crypto.android.sample;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tozny.crypto.android.AesCbcWithIntegrity;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.tozny.crypto.android.AesCbcWithIntegrity.decrypt;
import static com.tozny.crypto.android.AesCbcWithIntegrity.decryptString;
import static com.tozny.crypto.android.AesCbcWithIntegrity.encrypt;
import static com.tozny.crypto.android.AesCbcWithIntegrity.generateKey;
import static com.tozny.crypto.android.AesCbcWithIntegrity.generateKeyFromPassword;
import static com.tozny.crypto.android.AesCbcWithIntegrity.generateSalt;
import static com.tozny.crypto.android.AesCbcWithIntegrity.keyString;
import static com.tozny.crypto.android.AesCbcWithIntegrity.keys;
import static com.tozny.crypto.android.AesCbcWithIntegrity.saltString;

/**
 * Sample shows password based key gen
 */
public class MainActivity extends Activity {
    public static final String TAG = "Tozny";

    private static boolean PASSWORD_BASED_KEY = true;
    private static String EXAMPLE_PASSWORD = "LeighHunt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.crypto_execute);
        final TextView beforeCrypto = (TextView) findViewById(R.id.before_crypto);
        final TextView afterCrypto = (TextView) findViewById(R.id.after_crypto);

        final TextView decrypted = (TextView) findViewById(R.id.decrypted);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    AesCbcWithIntegrity.SecretKeys key;
                    if (PASSWORD_BASED_KEY) {//example for password based keys
                        String salt = saltString(generateSalt());
                        //If you generated the key from a password, you can store the salt and not the key.
                        Log.i(TAG, "Salt: " + salt);
                        key = generateKeyFromPassword(EXAMPLE_PASSWORD, salt);
                    } else {
                        key = generateKey();
                        //Note: If you are generating a random key, you'll probably be storing it somewhere
                    }

                    // The encryption / storage & display:

                    String keyStr = keyString(key);
                    key = null; //Pretend to throw that away so we can demonstrate converting it from str

                    String textToEncrypt = "We, the Fairies, blithe and antic,\n" +
                            "Of dimensions not gigantic,\n" +
                            "Though the moonshine mostly keep us,\n" +
                            "Oft in orchards frisk and peep us. ";
                    Log.i(TAG, "Before encryption: " + textToEncrypt);

                    beforeCrypto.setText(textToEncrypt);


                    // Read from storage & decrypt
                    key = keys(keyStr); // alternately, regenerate the key from password/salt.
                    AesCbcWithIntegrity.CipherTextIvMac civ = encrypt(textToEncrypt, key);
                    Log.i(TAG, "Encrypted: " + civ.toString());

                    afterCrypto.setText(civ.toString());

                    String decryptedText = decryptString(civ, key);

                    decrypted.setText(decryptedText);

                    Log.i(TAG, "Decrypted: " + decryptedText);
                    //Note: "String.equals" is not a constant-time check, which can sometimes be problematic.
                    Log.i(TAG, "Do they equal: " + textToEncrypt.equals(decryptedText));
                } catch (GeneralSecurityException e) {
                    Log.e(TAG, "GeneralSecurityException", e);
                } catch (UnsupportedEncodingException e) {
                    Log.e(TAG, "UnsupportedEncodingException", e);
                }
            }
        });

        final ImageView decryptedImageView = (ImageView) findViewById(R.id.image_decrypted);

        Button cryptoImage = (Button) findViewById(R.id.crypt_img_button);
        cryptoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Resources res = getResources();
                    Drawable tree = res.getDrawable(R.drawable.tree);
                    Bitmap bitmap = ((BitmapDrawable)tree).getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] bitMapData = stream.toByteArray();


                    AesCbcWithIntegrity.SecretKeys key;
                    if (PASSWORD_BASED_KEY) {//example for password based keys
                        String salt = saltString(generateSalt());
                        //If you generated the key from a password, you can store the salt and not the key.
                        Log.i(TAG, "Salt: " + salt);
                        key = generateKeyFromPassword(EXAMPLE_PASSWORD, salt);
                    } else {
                        key = generateKey();
                        //Note: If you are generating a random key, you'll probably be storing it somewhere
                    }

                    // The encryption / storage & display:

                    String keyStr = keyString(key);
                    key = null; //Pretend to throw that away so we can demonstrate converting it from str

                    // Read from storage & decrypt
                    key = keys(keyStr); // alternately, regenerate the key from password/salt.
                    AesCbcWithIntegrity.CipherTextIvMac civ = encrypt(bitMapData, key);
                    Log.i(TAG, "Encrypted: " + civ.toString());

                    byte[] decryptedImage = decrypt(civ, key);

                    Bitmap bmp = BitmapFactory.decodeByteArray(decryptedImage, 0, decryptedImage.length);
                    decryptedImageView.setImageBitmap(bmp);

                } catch (GeneralSecurityException e) {
                    Log.e(TAG, "GeneralSecurityException", e);
                }
            }
        });


        Button downloadAndEncript = (Button) findViewById(R.id.crypt_download_button);
        ImageView imageView = (ImageView) findViewById(R.id.downloaded_image);
        downloadAndEncript.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("http://www.freeiconspng.com/uploads/tree-icon-0.png").build();

                try {
                    Response response = client.newCall(request).execute();

                    byte[] imageBytes = response.body().bytes();
                    Bitmap bmp = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);


                } catch (IOException e) {
                    Log.e("Error", e.getMessage());
                }
            }
        });
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
