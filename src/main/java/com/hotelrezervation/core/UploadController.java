/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotelrezervation.core;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.http.Part;

/**
 *
 * @author deneme
 */
@ManagedBean
@RequestScoped
public class UploadController implements Serializable {

    private Part image;
    private boolean uploaded;
    private String imageName;

    public void doUpload() {
        try {
            InputStream in = image.getInputStream();
            File f = new File("/home/deneme/NetBeansProjects/hotel-rezervation/src/main/webapp/resources/upload/" + image.getSubmittedFileName());
            f.createNewFile();
            FileImageOutputStream out = new FileImageOutputStream(f);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            out.close();
            in.close();
            imageName=image.getSubmittedFileName();
            System.out.println(image.getSubmittedFileName());
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Part getImage() {
        return image;
    }

    public void setImage(Part image) {
        this.image = image;
    }

    public boolean isUploaded() {
        return uploaded;
    }

    public void setUploaded(boolean uploaded) {
        this.uploaded = uploaded;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
    
    

}
