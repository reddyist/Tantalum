/*
 Copyright (c) 2013, Paul Houghton and Futurice Oy
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions are met:
 - Redistributions of source code must retain the above copyright notice, this
 list of conditions and the following disclaimer.
 - Redistributions in binary form must reproduce the above copyright notice,
 this list of conditions and the following disclaimer in the documentation
 and/or other materials provided with the distribution.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 POSSIBILITY OF SUCH DAMAGE.
 */
package org.tantalum.jme;

import javax.microedition.lcdui.Image;
import org.tantalum.Task;
import org.tantalum.util.L;

/**
 * This is a helper class for creating an image class. It automatically converts
 * the byte[] to an Image as the data is loaded from the network or cache.
 *
 * @author tsaa
 */
public final class JMEImageTypeHandler extends org.tantalum.storage.ImageCacheView {

    public Object convertToUseForm(final Object key, final byte[] bytes) {
        final Image img;
        final int alg;
        final boolean aspect;
        final int w, h;

        synchronized (this) {
            alg = this.algorithm;
            aspect = this.preserveAspectRatio;
            w = this.maxWidth;
            h = this.maxHeight;
        }

        try {
            if (w == -1) {
                //#debug
                L.i("convert image", "length=" + bytes.length);
                img = Image.createImage(bytes, 0, bytes.length);
            } else {
                synchronized (Task.LARGE_MEMORY_MUTEX) {
                    final int tempW;
                    final int tempH;
                    final int[] argb;
                    {
                        final Image tempImage = Image.createImage(bytes, 0, bytes.length);
                        tempW = tempImage.getWidth();
                        tempH = tempImage.getHeight();
                        argb = new int[tempW * tempH];
                        tempImage.getRGB(argb, 0, tempW, 0, 0, tempW, tempH);
                    }
                    img = JMEImageUtils.scaleImage(argb, argb, tempW, tempH, w, h, aspect, alg);
                }
            }
        } catch (IllegalArgumentException e) {
            //#debug
            L.e("Exception converting bytes to image", "image byte length=" + bytes.length, e);
            throw e;
        }

        return img;
    }
}
