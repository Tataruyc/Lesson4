package ru.mirea.seyfetdinov.r.n.cryptoloader;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MyLoader extends AsyncTaskLoader<String>{

    private String firstName;
    public static final String ARG_WORD = "word";

    public byte[] cryptText;
    public byte[] key;
    public SecretKey originalKey;
    private String result;

    public MyLoader(@NonNull Context context, Bundle args) {
        super(context);
        if (args != null) {
            firstName = args.getString(ARG_WORD);

            cryptText = args.getByteArray(ARG_WORD);
            key = args.getByteArray("key");
            originalKey = new SecretKeySpec(key, 0, key.length, "AES");

            //result = decryptMsg(cryptText, originalKey);
        }
    }


    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
        result = decryptMsg(cryptText, originalKey);
    }

    @Nullable
    @Override
    public String loadInBackground() {
        //result = decryptMsg(cryptText, originalKey);
        SystemClock.sleep(5000);
        return result;
    }

    public static String decryptMsg(byte[] cipherText, SecretKey secret){
        try{
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secret);
            return new String(cipher.doFinal(cipherText));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
                 | BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

}
