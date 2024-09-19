package com.nhom6.shopn6.uploadimage;

import android.os.AsyncTask;
import android.util.Log;

import com.nhom6.shopn6.database.DBconnect;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class UploadTask extends AsyncTask<Void, Void, String> {
    private File file;

    public UploadTask(File file) {
        this.file = file;
    }

    @Override
    protected String doInBackground(Void... params) {
        String response = "";
        HttpURLConnection connection = null;
        DataOutputStream outputStream = null;
        FileInputStream fileInputStream = null;

        try {
            DBconnect db = new DBconnect();
            URL url = new URL("http://"+db.getIP()+":3000/upload");
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=*****");

            outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes("--*****\r\n");
            outputStream.writeBytes("Content-Disposition: form-data; name=\"image\";filename=\"" + file.getName() + "\"\r\n");
            outputStream.writeBytes("\r\n");

            fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.writeBytes("\r\n");
            outputStream.writeBytes("--*****--\r\n");
            outputStream.flush();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                response = "Upload thành công!";
            } else {
                response = "Lỗi: " + responseCode;
            }

        } catch (Exception e) {
            response = "Lỗi: " + e.getMessage();
        } finally {
            try {
                if (fileInputStream != null) fileInputStream.close();
                if (outputStream != null) outputStream.close();
                if (connection != null) connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            Log.d("Upload", "Upload thành công: " + result);
        } else {
            Log.d("Upload", "Upload thất bại");
        }
    }
}

