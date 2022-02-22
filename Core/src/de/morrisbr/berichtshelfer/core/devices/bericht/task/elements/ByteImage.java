package de.morrisbr.berichtshelfer.core.devices.bericht.task.elements;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.*;

public class ByteImage {

    private byte[] bytes;
    private InputStream inputStream;

    public ByteImage(byte[] bytes) {
        this.bytes = bytes;
    }

    public ByteImage(InputStream inputStream) {
        this.inputStream = inputStream;
        try {
            this.bytes = inputStream.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage convertToImage() {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            return ImageIO.read(bis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static byte[] extractBytes (String imagePath) throws IOException {
        // open image
        File imgPath = new File(imagePath);
        BufferedImage bufferedImage = ImageIO.read(imgPath);

        // get DataBufferBytes from Raster
        WritableRaster raster = bufferedImage .getRaster();
        DataBufferByte data   = (DataBufferByte) raster.getDataBuffer();

        return ( data.getData() );
    }

    public static byte[] toByteArray(BufferedImage bi, String format)
            throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, format, baos);
        byte[] bytes = baos.toByteArray();
        return bytes;

    }

    // convert byte[] to BufferedImage
    public static BufferedImage toBufferedImage(byte[] bytes)
            throws IOException {

        InputStream is = new ByteArrayInputStream(bytes);
        BufferedImage bi = ImageIO.read(is);
        return bi;

    }

    //public static byte[] convertImageToBytes(Image image) throws IOException {
//
     //   ImageInputStream imageInputStream = image.t
     //   BufferedImage bufferedImage = ImageIO.read(imageInputStream);
     //   ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
     //   ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
     //   InputStream is = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

     //   return is.readAllBytes();
   // }

    public void setImageAsBytes(Image image) throws IOException {
        ImageInputStream imageInputStream = ImageIO.createImageInputStream(image);
        BufferedImage bufferedImage = ImageIO.read(imageInputStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
        InputStream is = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

        this.bytes = is.readAllBytes();
    }

    public byte[] convertToBytes() {
        try {
            return inputStream.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveImage(String outputPath) {
        File outputfile = new File(outputPath);
        try {
            ImageIO.write(toBufferedImage(bytes), "png", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
