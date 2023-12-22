package com.quathar.psp.image.child;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;

/**
 * <h1>Image Processor</h1>
 * <br>
 * <p>
 *     Class to convert images to black and white (B&W).<br>
 *     Class-dependent process for module(parent).Main<br>
 *     <br>
 *     PD: It's necessary to download ImageMagick to be able to use this class. See the pdf for more information.
 * </p>
 *
 * @since 2022-10-03
 * @version 1.0
 * @author Q
 */
public class ImageProcessor {

    // <<-CONSTANT->>
    private static final String IMAGE_MAGICK = Path.of("D:", "Program Files", "ImageMagick-7.1.0-Q16-HDRI").toString();
    private static final String LOREM_DST_PATH = Path.of(System.getProperty("user.dir"), "lorem.gray", "%s").toString();

    // <<-METHODS->>
    private static void convert(String srcImageFilePath, String dstImageFilePath) throws IOException, InterruptedException, IM4JavaException {
        org.im4java.process.ProcessStarter.setGlobalSearchPath(IMAGE_MAGICK);
        ConvertCmd cmd = new ConvertCmd();
        IMOperation op = new IMOperation();
        op.addImage(srcImageFilePath);
        op.colorspace("Gray");
        op.addImage(dstImageFilePath);
        cmd.run(op);
    }

    private static String getDstPath(String srcImageFilePath) {
        String fileName = new java.io.File(srcImageFilePath).getName();
        String fileWithoutExtension = fileName.substring(0, fileName.lastIndexOf("."));
        return String.format(
                LOREM_DST_PATH,
                fileWithoutExtension.concat(".gray.jpg")
        );
    }

    public static void main(String[] args) throws IOException, InterruptedException, IM4JavaException {
        switch (args[0]) {
            case "-n" -> {
                String srcImageFilePath = args[1];
                String dstImageFilePath = ImageProcessor.getDstPath(srcImageFilePath);
                convert(srcImageFilePath, dstImageFilePath);
                System.out.println(dstImageFilePath);
            }
            case "-a" -> {
                try (BufferedReader br = new BufferedReader(new java.io.InputStreamReader(System.in))) {
                    while (true) {
                        String srcImageFilePath = br.readLine();
                        String dstImageFilePath = ImageProcessor.getDstPath(srcImageFilePath);
                        convert(srcImageFilePath, dstImageFilePath);
                        System.out.println(dstImageFilePath);
                    }
                }
            }
        }
    }

}
