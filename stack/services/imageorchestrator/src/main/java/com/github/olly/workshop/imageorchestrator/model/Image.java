package com.github.olly.workshop.imageorchestrator.model;

public class Image {
    private byte[] data;
    private String mimeType;

    public Image(byte[] data, String mimeType) {
        this.data = data;
        this.mimeType = mimeType;
    }

    public byte[] getData() {
        return data;
    }

    public String getMimeType() {
        return mimeType;
    }

    public boolean hasData() {
        return data != null && data.length > 0;
    }

//    public static Image from(ImageDto imageDto) {
//        // retrieve from imageholder
//        if (!StringUtils.isEmpty(imageDto.getReference())) {
//
//
//        // decode from base64
//        } else {
//            asimageDto.getImageBase64()
//        }
//    }
}
